<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between;">
          <span>👥 读者类别设置</span>
          <el-button type="primary" @click="showAdd">新增类别</el-button>
        </div>
      </template>
      <el-table :data="tableData" border>
        <el-table-column prop="tid" label="类别编号" width="100" />
        <el-table-column prop="typename" label="类别名称" />
        <el-table-column prop="maxbnum" label="最大借阅量" />
        <el-table-column prop="limitdays" label="最长借阅天数" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row.tid)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="visible" :title="isEdit ? '编辑类别' : '新增类别'" width="400px">
      <el-form :model="form" label-width="120px">
        <el-form-item label="类别名称">
          <el-input v-model="form.typename" />
        </el-form-item>
        <el-form-item label="最大借阅量">
          <el-input-number v-model="form.maxbnum" :min="1" />
        </el-form-item>
        <el-form-item label="最长借阅天数">
          <el-input-number v-model="form.limitdays" :min="1" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="confirm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getReaderTypeList, addReaderType, updateReaderType, deleteReaderType } from '../api/api'

const tableData = ref([])
const visible = ref(false)
const isEdit = ref(false)
const form = ref({ tid: null, typename: '', maxbnum: 5, limitdays: 30 })

const loadData = async () => {
  const res = await getReaderTypeList()
  tableData.value = res.data || []
}

const showAdd = () => {
  isEdit.value = false
  form.value = { tid: null, typename: '', maxbnum: 5, limitdays: 30 }
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
    await updateReaderType(form.value)
  } else {
    await addReaderType(form.value)
  }
  ElMessage.success(isEdit.value ? '修改成功' : '添加成功')
  visible.value = false
  loadData()
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定删除吗？', '提示', { type: 'warning' })
    await deleteReaderType(id)
    ElMessage.success('删除成功')
    loadData()
  } catch {}
}

onMounted(loadData)
</script>
