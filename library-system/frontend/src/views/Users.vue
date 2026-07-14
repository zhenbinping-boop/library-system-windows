<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>👤 用户管理</span>
          <el-button type="primary" @click="showAdd">新增用户</el-button>
        </div>
      </template>
      <el-table :data="tableData" border>
        <el-table-column prop="uid" label="用户编号" width="100" />
        <el-table-column prop="uname" label="用户名" />
        <el-table-column prop="utype" label="用户类型">
          <template #default="scope">
            <el-tag v-if="scope.row.utype === 0" type="danger">管理员</el-tag>
            <el-tag v-else-if="scope.row.utype === 1" type="warning">馆员</el-tag>
            <el-tag v-else type="info">普通用户</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button type="danger" size="small" @click="handleDelete(scope.row.uid)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增用户弹窗 -->
    <el-dialog v-model="visible" title="新增用户" width="400px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="form.uname" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.upassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="用户类型">
          <el-select v-model="form.utype" style="width: 100%;">
            <el-option label="管理员" :value="0" />
            <el-option label="馆员" :value="1" />
            <el-option label="普通用户" :value="2" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="confirmAdd">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserList, addUser, deleteUser } from '../api/api'

const tableData = ref([])
const visible = ref(false)
const form = ref({ uname: '', upassword: '', utype: 2 })

const loadData = async () => {
  const res = await getUserList()
  tableData.value = res.data || []
}

const showAdd = () => {
  form.value = { uname: '', upassword: '', utype: 2 }
  visible.value = true
}

const confirmAdd = async () => {
  if (!form.value.uname || !form.value.upassword) {
    ElMessage.warning('请填写完整信息')
    return
  }
  await addUser(form.value)
  ElMessage.success('添加成功')
  visible.value = false
  loadData()
}

const handleDelete = async (uid) => {
 try{
   await ElMessageBox.confirm('确定删除该用户吗？', '提示', { type: 'warning' })
  await deleteUser(uid)
  ElMessage.success('删除成功')
  loadData()
 }catch{}
}

onMounted(loadData)
</script>
