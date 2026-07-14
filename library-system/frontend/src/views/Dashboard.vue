<template>
  <div>
    <!-- 统计卡片 -->
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card>
          <div style="display: flex; align-items: center;">
            <el-icon size="40" color="#409EFF"><Reading /></el-icon>
            <div style="margin-left: 15px;">
              <div style="font-size: 14px; color: #999;">图书总数</div>
              <div style="font-size: 24px; font-weight: bold;">{{ stats.bookCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="display: flex; align-items: center;">
            <el-icon size="40" color="#67C23A"><User /></el-icon>
            <div style="margin-left: 15px;">
              <div style="font-size: 14px; color: #999;">读者总数</div>
              <div style="font-size: 24px; font-weight: bold;">{{ stats.readerCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="display: flex; align-items: center;">
            <el-icon size="40" color="#E6A23C"><Collection /></el-icon>
            <div style="margin-left: 15px;">
              <div style="font-size: 14px; color: #999;">当前借阅</div>
              <div style="font-size: 24px; font-weight: bold;">{{ stats.borrowCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="display: flex; align-items: center;">
            <el-icon size="40" color="#F56C6C"><Timer /></el-icon>
            <div style="margin-left: 15px;">
              <div style="font-size: 14px; color: #999;">逾期未还</div>
              <div style="font-size: 24px; font-weight: bold;" :style="{color: stats.overdueCount > 0 ? '#F56C6C' : '#67C23A'}">
                {{ stats.overdueCount }}
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 逾期提醒（仅普通用户显示） -->
    <el-card v-if="user.utype === 2 && overdueList.length > 0" style="margin-top: 20px;">
      <template #header>
        <span style="color: #F56C6C;">⚠️ 逾期提醒</span>
      </template>
      <el-table :data="overdueList" border>
        <el-table-column prop="bname" label="书名" />
        <el-table-column prop="bdate" label="借阅日期" />
        <el-table-column prop="overdueStatus" label="逾期状态" />
        <el-table-column prop="overdueFine" label="罚金(元)" />
      </el-table>
    </el-card>

    <!-- 系统公告 -->
    <el-card style="margin-top: 20px;">
      <template #header>
        <span>📖 系统公告</span>
      </template>
      <div style="line-height: 2; color: #666;">
        <p>欢迎使用图书借阅管理系统！</p>
        <p>1. 管理员(utype=0)：拥有全部权限，可管理用户、图书、读者、借阅等所有模块。</p>
        <p>2. 馆员(utype=1)：可管理图书、读者、借阅，但不能管理用户账号。</p>
        <p>3. 普通用户(utype=2)：只能检索图书、查看自己的借阅记录、进行借阅和归还操作。</p>
        <p>4. 借阅规则：根据读者类型限制最大借阅数量和最长借阅天数，逾期将产生罚金。</p>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getBookList, getReaderList, getBorrowList, getAllBorrow } from '../api/api'

const stats = ref({ bookCount: 0, readerCount: 0, borrowCount: 0, overdueCount: 0 })
const overdueList = ref([])
const user = JSON.parse(localStorage.getItem('user') || '{}')

onMounted(async () => {
  try {
    const books = await getBookList()
    stats.value.bookCount = books.data?.length || 0

    const readers = await getReaderList()
    stats.value.readerCount = readers.data?.length || 0

    if (user.utype !== 2) {
      // 管理员/馆员：查全部借阅
      const borrows = await getBorrowList()
      stats.value.borrowCount = borrows.data?.length || 0

      const all = await getAllBorrow()
      const list = all.data || []
      // 用后端返回的 overdueStatus 判断逾期
      const overdue = list.filter(b => b.overdueStatus && b.overdueStatus.includes('逾期'))
      stats.value.overdueCount = overdue.length
    } else {
      // 普通用户：查我的借阅
      const my = await getBorrowList()
      const list = my.data || []
      stats.value.borrowCount = list.length

      // 用后端返回的 overdueStatus 判断逾期
      const overdue = list.filter(b => b.overdueStatus && b.overdueStatus.includes('逾期'))
      stats.value.overdueCount = overdue.length
      overdueList.value = overdue
    }
  } catch (e) {
    console.error(e)
  }
})
</script>