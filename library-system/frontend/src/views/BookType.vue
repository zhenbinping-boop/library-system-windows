<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getBookTypeList, addBookType, updateBookType, deleteBookType } from '../api/api'

const tableData = ref([])
const visible = ref(false)
const isEdit = ref(false)
const form = ref({ typeid: null, typename: '' })

const loadData = async () => {
  const res = await getBookTypeList()
  tableData.value = res.data || []
}

const showAdd = () => {
  isEdit.value = false
  form.value = { typeid: null, typename: '' }
  visible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  form.value = { ...row }
  visible.value = true
}

const confirm = async () => {
  if (!form.value.typename) {
    ElMessage.warning('请输入类别名称')
    return
  }
  if (isEdit.value) {
    await updateBookType(form.value)
  } else {
    await addBookType(form.value)
  }
  ElMessage.success(isEdit.value ? '修改成功' : '添加成功')
  visible.value = false
  loadData()
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定删除吗？', '提示', { type: 'warning' })
    await deleteBookType(id)
    ElMessage.success('删除成功')
    loadData()
  } catch {}
}

onMounted(loadData)
</script>
<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between;">
          <span>📑 图书类别设置</span>
          <el-button type="primary" @click="showAdd">新增类别</el-button>
        </div>
      </template>
      <el-table :data="tableData" border>
        <el-table-column prop="typeid" label="类别编号" width="100" />
        <el-table-column prop="typename" label="类别名称" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row.typeid)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="visible" :title="isEdit ? '编辑类别' : '新增类别'" width="400px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="类别名称">
          <el-input v-model="form.typename" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="confirm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>


