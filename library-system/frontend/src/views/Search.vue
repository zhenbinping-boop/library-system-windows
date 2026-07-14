<template>
  <div>
    <el-card>
      <template #header><span>🔍 图书检索</span></template>
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="ISBN"><el-input v-model="searchForm.bisbn" placeholder="精确匹配" /></el-form-item>
        <el-form-item label="书名"><el-input v-model="searchForm.bname" placeholder="模糊匹配" /></el-form-item>
        <el-form-item label="作者"><el-input v-model="searchForm.author" /></el-form-item>
        <el-form-item label="出版社"><el-input v-model="searchForm.publish" /></el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData" border v-if="tableData.length">
        <el-table-column prop="bisbn" label="ISBN" />
        <el-table-column prop="bname" label="书名" />
        <el-table-column prop="author" label="作者" />
        <el-table-column prop="publish" label="出版社" />
        <el-table-column prop="typename" label="类别" />
        <el-table-column prop="bprice" label="单价" />
        <el-table-column prop="num" label="库存" />
        <el-table-column prop="isChildren" label="少儿读物" />
      </el-table>
      <el-empty v-else description="请输入条件搜索" />
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { searchBooks } from '../api/api'

const searchForm = ref({ bisbn: '', bname: '', author: '', publish: '' })
const tableData = ref([])

const handleSearch = async () => {
  const res = await searchBooks(searchForm.value)
  tableData.value = res.data || []
}

const reset = () => {
  searchForm.value = { bisbn: '', bname: '', author: '', publish: '' }
  tableData.value = []
}
</script>
