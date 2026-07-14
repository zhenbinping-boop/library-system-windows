<template>
  <div>
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between;">
          <span>👤 读者信息管理</span>
          <el-button type="primary" @click="showAdd">新增读者</el-button>
        </div>
      </template>
      <el-form :inline="true" :model="searchForm" style="margin-bottom: 20px;">
        <el-form-item label="读者编号"><el-input v-model="searchForm.rid" /></el-form-item>
        <el-form-item label="姓名"><el-input v-model="searchForm.rname" /></el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="loadData">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData" border>
        <el-table-column prop="rid" label="读者编号" width="120" />
        <el-table-column prop="rname" label="姓名" />
        <el-table-column prop="gender" label="性别" width="80" />
        <el-table-column prop="age" label="年龄" width="80" />
        <el-table-column prop="tele" label="电话" />
        <el-table-column prop="major" label="所在系部" />
        <el-table-column prop="typename" label="读者类型" />
        <el-table-column prop="indate" label="注册日期" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row.rid)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="visible" :title="isEdit ? '编辑读者' : '新增读者'" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="读者编号"><el-input v-model="form.rid" :disabled="isEdit" /></el-form-item>
        <el-form-item label="姓名"><el-input v-model="form.rname" /></el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.gender">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄"><el-input-number v-model="form.age" :min="1" /></el-form-item>
        <el-form-item label="电话"><el-input v-model="form.tele" /></el-form-item>
        <el-form-item label="所在系部"><el-input v-model="form.major" /></el-form-item>
        <el-form-item label="读者类型">
          <el-select v-model="form.rtype" style="width: 100%;">
            <el-option v-for="t in typeList" :key="t.tid" :label="t.typename" :value="t.tid" />
          </el-select>
        </el-form-item>
        <el-form-item label="注册日期"><el-date-picker v-model="form.indate" type="date" value-format="YYYY-MM-DD" style="width: 100%;" /></el-form-item>
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
import { getReaderList, searchReaders, addReader, updateReader, deleteReader, getReaderTypeList } from '../api/api'

const tableData = ref([])
const typeList = ref([])
const visible = ref(false)
const isEdit = ref(false)
const form = ref({ rid: '', rname: '', gender: '男', age: 18, tele: '', major: '', rtype: null, indate: '' })
const searchForm = ref({ rid: '', rname: '' })

const loadData = async () => {
  const res = await getReaderList()
  tableData.value = res.data || []
  searchForm.value = { rid: '', rname: '' }
}

const handleSearch = async () => {
  const res = await searchReaders(searchForm.value)
  tableData.value = res.data || []
}

const showAdd = () => {
  isEdit.value = false
  form.value = { rid: '', rname: '', gender: '男', age: 18, tele: '', major: '', rtype: typeList.value[0]?.tid, indate: '' }
  visible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  form.value = { ...row }
  visible.value = true
}

const confirm = async () => {
  if (!form.value.rid || !form.value.rname) {
    ElMessage.warning('请填写读者编号和姓名')
    return
  }
  if (isEdit.value) {
    await updateReader(form.value)
  } else {
    await addReader(form.value)
  }
  ElMessage.success(isEdit.value ? '修改成功' : '添加成功')
  visible.value = false
  loadData()
}

const handleDelete = async (rid) => {
  try {
    await ElMessageBox.confirm('确定删除吗？', '提示', { type: 'warning' })
    await deleteReader(rid)
    ElMessage.success('删除成功')
    loadData()
  } catch {}
}

onMounted(async () => {
  loadData()
  const res = await getReaderTypeList()
  typeList.value = res.data || []
})
</script>
