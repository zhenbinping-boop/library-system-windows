<template>
  <div>
    <el-card>
      <template #header><span>我的借阅记录</span></template>
      <el-table :data="tableData" border>
        <el-table-column prop="bisbn" label="ISBN" />
        <el-table-column prop="bname" label="书名" />
        <el-table-column prop="author" label="作者" />
        <el-table-column prop="bdate" label="借阅日期" />
        <!-- 新增：逾期状态列 -->
        <el-table-column label="逾期状态" width="150">
          <template #default="scope">
            <el-tag :type="getOverdueType(scope.row)">
              {{ scope.row.overdueStatus || '-' }}
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
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyBorrow } from '../api/api'

const tableData = ref([])
onMounted(async () => {
  const res = await getMyBorrow()
  tableData.value = res.data || []
})

// 新增：根据逾期状态返回 tag 类型
const getOverdueType = (row) => {
  if (row.isReturned === 1) return 'info'
  if (!row.overdueStatus) return 'info'
  if (row.overdueStatus.includes('逾期')) return 'danger'
  return 'success'
}
</script>