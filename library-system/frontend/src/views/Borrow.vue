<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="12" v-if="user.utype !== 2">
        <el-card>
          <template #header><span>图书借阅</span></template>
          <el-form :model="borrowForm" label-width="100px">
            <el-form-item label="用户账号"><el-input v-model="borrowForm.rid" /></el-form-item>
            <el-form-item label="图书ISBN"><el-input v-model="borrowForm.bisbn" /></el-form-item>
            <el-form-item><el-button type="primary" @click="handleBorrow">确认借阅</el-button></el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="user.utype !== 2 ? 12 : 24">
        <el-card>
          <template #header><span>图书归还</span></template>
          <el-form :model="returnForm" label-width="100px">
            <el-form-item label="用户账号" ><el-input v-model="returnForm.rid" /></el-form-item>
            <el-form-item label="图书ISBN"><el-input v-model="returnForm.bisbn" /></el-form-item>
            <el-form-item label="每日罚金" v-if="user.utype !== 2"><el-input-number v-model="returnForm.finePerDay" :precision="1" :min="0" :step="0.5" /></el-form-item>
            <el-form-item><el-button type="success" @click="handleReturn">确认归还</el-button></el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 20px;">
      <template #header><span>当前借阅列表</span></template>
      <el-table :data="borrowList" border>
        <el-table-column prop="rid" label="读者编号" />
        <el-table-column prop="rname" label="读者姓名" />
        <el-table-column prop="bisbn" label="ISBN" />
        <el-table-column prop="bname" label="书名" />
        <el-table-column prop="author" label="作者" />
        <el-table-column prop="bdate" label="借阅日期" />
        <!-- 新增：逾期状态列 -->
        <el-table-column label="逾期状态" width="150">
          <template #default="scope">
            <el-tag :type="getOverdueType(scope.row)">
              {{ scope.row.overdueStatus || '未知' }}
            </el-tag>
          </template>
        </el-table-column>
        <!-- 新增：罚金列 -->
        <el-table-column label="罚金(元)" width="100">
          <template #default="scope">
            <span :style="{color: (scope.row.overdueFine > 0) ? '#f56c6c' : '#67c23a', fontWeight: 'bold'}">
              {{ scope.row.overdueFine !== undefined ? scope.row.overdueFine : '0.00' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.isReturned === 1 ? 'success' : 'warning'">
              {{ scope.row.isReturned === 1 ? '已归还' : '借阅中' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="罚金二维码" width="120" align="center">
          <template #default="scope">
            <img :src="getQrUrl(scope.row)" style="width:80px;height:80px;cursor:pointer" @click="showQr(scope.row)" />
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 普通用户：可借书籍列表 -->
    <el-card style="margin-top: 20px;" v-if="user.utype === 2">
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>可借书籍列表</span>
          <el-input v-model="bookKeyword" placeholder="搜索书名/作者/ISBN" style="width: 250px;" clearable @input="filterBooks" />
        </div>
      </template>
      <el-table :data="filteredBooks" border>
        <el-table-column prop="bisbn" label="ISBN" width="150" />
        <el-table-column prop="bname" label="书名" />
        <el-table-column prop="author" label="作者" width="120" />
        <el-table-column prop="publish" label="出版社" width="150" />
        <el-table-column prop="typename" label="类别" width="100" />
        <el-table-column prop="num" label="库存" width="80" />
        <el-table-column label="操作" width="100" align="center">
          <template #default="scope">
            <el-button type="primary" size="small" :disabled="scope.row.num <= 0" @click="quickBorrow(scope.row.bisbn)">借阅</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="qrDialog" title="扫码查看罚金" width="320px" align-center>
      <div style="text-align:center">
        <img :src="currentQr" style="width:240px;height:240px" />
        <p style="color:#999;margin-top:10px">微信扫一扫查看应付金额</p>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { getBorrowList, borrowBook, returnBook, getBookList } from '../api/api'
import QRCode from 'qrcode'

const user = ref(JSON.parse(localStorage.getItem('user') || '{}'))
const borrowForm = ref({ rid: '', bisbn: '' })
const returnForm = ref({ rid: '', bisbn: '', finePerDay: 1.0 })
const borrowList = ref([])
const qrCache = ref({})
const qrDialog = ref(false)
const currentQr = ref('')

const bookList = ref([])
const bookKeyword = ref('')

const filteredBooks = computed(() => {
  if (!bookKeyword.value) return bookList.value
  const kw = bookKeyword.value.toLowerCase()
  return bookList.value.filter(b =>
    b.bname?.toLowerCase().includes(kw) ||
    b.author?.toLowerCase().includes(kw) ||
    b.bisbn?.toLowerCase().includes(kw)
  )
})

// 新增：根据逾期状态返回 tag 类型
const getOverdueType = (row) => {
  if (row.isReturned === 1) return 'info'
  if (!row.overdueStatus) return 'info'
  if (row.overdueStatus.includes('逾期')) return 'danger'
  return 'success'
}

const getFineUrl = (row) => {
  // 优先使用后端返回的逾期信息
  const status = row.overdueStatus || '未知'
  const fine = row.overdueFine !== undefined ? row.overdueFine : '0.00'

  return `借阅:${row.bname||''}\n读者:${row.rname||row.rid}\n状态:${status}\n罚金:${fine}元`
}

const getQrUrl = (row) => {
  const key = `${row.rid}_${row.bisbn}`
  if (!qrCache.value[key]) {
    QRCode.toDataURL(getFineUrl(row), { width: 240, margin: 1 }).then(url => {
      qrCache.value[key] = url
    })
    return ''
  }
  return qrCache.value[key]
}

const showQr = (row) => {
  const key = `${row.rid}_${row.bisbn}`
  if (qrCache.value[key]) {
    currentQr.value = qrCache.value[key]
    qrDialog.value = true
  } else {
    QRCode.toDataURL(getFineUrl(row), { width: 240, margin: 1 }).then(url => {
      qrCache.value[key] = url
      currentQr.value = url
      qrDialog.value = true
    })
  }
}

const loadData = async () => {
  const res = await getBorrowList()
  borrowList.value = res.data || []
}

const loadBooks = async () => {
  const res = await getBookList()
  bookList.value = res.data || []
}

const quickBorrow = async (bisbn) => {
  const res = await borrowBook({ rid: user.value.uname, bisbn })
  ElMessage.success(res.data || '借阅成功')
  loadData()
  loadBooks()
}

const handleBorrow = async () => {
  if (!borrowForm.value.rid || !borrowForm.value.bisbn) {
    ElMessage.warning('请填写完整信息')
    return
  }
  const res = await borrowBook(borrowForm.value)
  ElMessage.success(res.data || '借阅成功')
  borrowForm.value = { rid: '', bisbn: '' }
  qrCache.value = {}
  loadData()
}

const handleReturn = async () => {
  if (!returnForm.value.bisbn) {
    ElMessage.warning('请填写图书ISBN')
    return
  }
  if (user.utype !== 2 && !returnForm.value.rid) {
    ElMessage.warning('请填写用户账号')
    return
  }
  const params = { ...returnForm.value }
  if (user.utype === 2) params.rid = user.value.uname
  const res = await returnBook(params)
  ElMessage.success(res.data || '归还成功')
  returnForm.value = { rid: '', bisbn: '', finePerDay: 1.0 }
  qrCache.value = {}
  loadData()
  loadBooks()
}

onMounted(() => {
  loadData()
  if (user.value.utype === 2) loadBooks()
})
</script>