<script setup>
import { ref, onMounted } from 'vue'
import { getAllBorrow } from '../api/api'

const tableData = ref([])
onMounted(async () => {
  const res = await getAllBorrow()
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
<template>
  <div>
    <el-card>
      <template #header><span>📋 全部借阅记录</span></template>
      <el-table :data="tableData" border>
        <el-table-column prop="rid" label="读者编号" />
        <el-table-column prop="rname" label="读者姓名" />
        <el-table-column prop="bisbn" label="ISBN" />
        <el-table-column prop="bname" label="书名" />
        <el-table-column prop="bdate" label="借阅日期" />
        <el-table-column prop="rdate" label="归还日期" />
        <!-- 新增：逾期状态列 -->
        <el-table-column label="逾期状态" width="150">
          <template #default="scope">
            <el-tag :type="getOverdueType(scope.row)">
              {{ scope.row.overdueStatus || '-' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="fine" label="罚金" />
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