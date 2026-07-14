<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getBookList, searchBooks, addBook, updateBook, deleteBook, getBookTypeList } from '../api/api'

const tableData = ref([])
const typeList = ref([])
const visible = ref(false)
const isEdit = ref(false)
const form = ref({ bisbn: '', bname: '', author: '', publish: '', pdate: '', ptimes: 1, bprice: 0, typeid: null, num: 1, isChildren: '否' })
const searchForm = ref({ bisbn: '', bname: '', author: '' })

const loadData = async () => {
  const res = await getBookList()
  tableData.value = res.data || []
  searchForm.value = { bisbn: '', bname: '', author: '' }
}

const handleSearch = async () => {
  const res = await searchBooks(searchForm.value)
  tableData.value = res.data || []
}

const showAdd = () => {
  isEdit.value = false
  form.value = { bisbn: '', bname: '', author: '', publish: '', pdate: '', ptimes: 1, bprice: 0, typeid: typeList.value[0]?.typeid, num: 1, isChildren: '否' }
  visible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  form.value = { ...row }
  visible.value = true
}

const confirm = async () => {
  if (!form.value.bisbn || !form.value.bname) {
    ElMessage.warning('请填写ISBN和书名')
    return
  }
  if (isEdit.value) {
    await updateBook(form.value)
  } else {
    await addBook(form.value)
  }
  ElMessage.success(isEdit.value ? '修改成功' : '添加成功')
  visible.value = false
  loadData()
}

const handleDelete = async (uid) => {
  try {
    await ElMessageBox.confirm('确定删除该用户吗？', '提示', { type: 'warning' })
    await deleteUser(uid)
    ElMessage.success('删除成功')
    loadData()
  } catch {}
}

onMounted(async () => {
  loadData()
  const res = await getBookTypeList()
  typeList.value = res.data || []
})
</script>

<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between;">
          <span>📚 图书信息管理</span>
          <el-button type="primary" @click="showAdd">新增图书</el-button>
        </div>
      </template>
      <el-form :inline="true" :model="searchForm" style="margin-bottom: 20px;">
        <el-form-item label="ISBN"><el-input v-model="searchForm.bisbn" placeholder="ISBN" /></el-form-item>
        <el-form-item label="书名"><el-input v-model="searchForm.bname" placeholder="书名" /></el-form-item>
        <el-form-item label="作者"><el-input v-model="searchForm.author" placeholder="作者" /></el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="loadData">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData" border>
        <el-table-column prop="bisbn" label="ISBN" width="150" />
        <el-table-column prop="bname" label="书名" />
        <el-table-column prop="author" label="作者" width="120" />
        <el-table-column prop="publish" label="出版社" width="150" />
        <el-table-column prop="typename" label="类别" width="100" />
        <el-table-column prop="bprice" label="单价" width="100" />
        <el-table-column prop="num" label="库存" width="80" />
        <el-table-column prop="isChildren" label="少儿读物" width="90">
          <template #default="scope">
            <el-tag :type="scope.row.isChildren === '是' ? 'success' : 'info'">{{ scope.row.isChildren }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row.bisbn)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="visible" :title="isEdit ? '编辑图书' : '新增图书'" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="ISBN"><el-input v-model="form.bisbn" :disabled="isEdit" /></el-form-item>
        <el-form-item label="图书名称"><el-input v-model="form.bname" /></el-form-item>
        <el-form-item label="作者"><el-input v-model="form.author" /></el-form-item>
        <el-form-item label="出版社"><el-input v-model="form.publish" /></el-form-item>
        <el-form-item label="出版日期"><el-date-picker v-model="form.pdate" type="date" value-format="YYYY-MM-DD" style="width: 100%;" /></el-form-item>
        <el-form-item label="印刷次数"><el-input-number v-model="form.ptimes" :min="1" /></el-form-item>
        <el-form-item label="单价"><el-input-number v-model="form.bprice" :precision="1" :min="0" /></el-form-item>
        <el-form-item label="图书类别">
          <el-select v-model="form.typeid" style="width: 100%;">
            <el-option v-for="t in typeList" :key="t.typeid" :label="t.typename" :value="t.typeid" />
          </el-select>
        </el-form-item>
        <el-form-item label="馆藏数量"><el-input-number v-model="form.num" :min="0" /></el-form-item>
        <el-form-item label="少儿读物">
          <el-radio-group v-model="form.isChildren">
            <el-radio label="是">是</el-radio>
            <el-radio label="否">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="confirm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

