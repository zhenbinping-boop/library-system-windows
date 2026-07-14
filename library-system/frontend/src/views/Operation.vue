<script>
import { getLogs } from '../api/api'
export default {
  data() { return { logs: [] } },
  created() { this.load() },
  methods: {
    async load() {
        const res  = await getLogs()
        this.logs = res.data
    },
    getType(t) {
      return { INSERT: 'success', UPDATE: 'warning', DELETE: 'danger', LOGIN: 'primary' }[t] || ''
    }
  }
}
</script>
<template>
  <div>
    <h2>操作日志</h2>
    <el-table :data="logs" border>
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="type" label="类型" width="100">
        <template #default="scope">
          <el-tag :type="getType(scope.row.type)">{{ scope.row.type }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="tableName" label="表名" width="100" />
      <el-table-column prop="content" label="内容" />
      <el-table-column prop="operator" label="操作人" width="100" />
      <el-table-column prop="time" label="时间" width="180" />
    </el-table>
  </div>
</template>