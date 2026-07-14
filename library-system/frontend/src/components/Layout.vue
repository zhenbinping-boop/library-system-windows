<template>
  <el-container style="height: 100vh;">
    <!-- 左侧菜单 -->
    <el-aside width="220px" style="background: #304156;">
      <div style="height: 60px; line-height: 60px; text-align: center; color: #fff; font-size: 18px; font-weight: bold;">
        <el-icon><Notebook /></el-icon>
        图书管理系统
      </div>
      <el-menu
        :default-active="$route.path"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        router
        style="border: none;"
      >
        <el-menu-item index="/admin/dashboard">
          <el-icon><HomeFilled /></el-icon>
          <span>首页</span>
        </el-menu-item>

        <!-- 图书管理（管理员和馆员可见） -->
        <el-sub-menu index="1" v-if="user.utype !== 2">
          <template #title>
            <el-icon><Reading /></el-icon>
            <span>图书管理</span>
          </template>
          <el-menu-item index="/admin/books">图书信息管理</el-menu-item>
          <el-menu-item index="/admin/booktype">图书类别设置</el-menu-item>
        </el-sub-menu>

        <!-- 读者管理（管理员和馆员可见） -->
        <el-sub-menu index="2" v-if="user.utype !== 2">
          <template #title>
            <el-icon><User /></el-icon>
            <span>读者管理</span>
          </template>
          <el-menu-item index="/admin/readers">读者信息管理</el-menu-item>
          <el-menu-item index="/admin/readertype">读者类别设置</el-menu-item>
        </el-sub-menu>

        <!-- 借阅管理（所有人可见） -->
        <el-sub-menu index="3">
          <template #title>
            <el-icon><Collection /></el-icon>
            <span>借阅管理</span>
          </template>
          <el-menu-item index="/admin/borrow">图书借阅</el-menu-item>
          <el-menu-item index="/admin/myborrow" v-if="user.utype === 2">我的借阅</el-menu-item>
          <el-menu-item index="/admin/borrow-all" v-if="user.utype !== 2">全部借阅记录</el-menu-item>
        </el-sub-menu>

        <!-- 图书检索（所有人可见） -->
        <el-menu-item index="/admin/search">
          <el-icon><Search /></el-icon>
          <span>图书检索</span>
        </el-menu-item>
        
         
        <!-- 系统管理（仅管理员可见） -->
        <el-sub-menu index="4" v-if="user.utype === 0">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>系统管理</span>
          </template>
          <el-menu-item index="/admin/users">用户管理</el-menu-item>
           <el-menu-item index="/admin/operation">操作日志</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
     
    <!-- 右侧内容区 -->
    <el-container>
      <!-- 顶部栏 -->
      <el-header style="background: #fff; box-shadow: 0 1px 4px rgba(0,0,0,.08); display: flex; align-items: center; justify-content: space-between;">
        <span style="font-size: 16px; color: #666;">欢迎，{{ user.uname }} ({{ typeText }})</span>
        <div>
          <el-button type="primary" size="small" @click="changePwd">修改密码</el-button>
          <el-button type="danger" size="small" @click="logout">退出登录</el-button>
        </div>
      </el-header>

      <!-- 主内容区 -->
      <el-main style="background: #f0f2f5; padding: 20px;">
        <router-view />
      </el-main>
    </el-container>
  </el-container>

  <!-- 修改密码弹窗 -->
  <el-dialog v-model="pwdVisible" title="修改密码" width="400px">
    <el-form :model="pwdForm" label-width="100px">
      <el-form-item label="新密码">
        <el-input v-model="pwdForm.upassword" type="password" show-password />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="pwdVisible = false">取消</el-button>
      <el-button type="primary" @click="confirmChangePwd">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { logout as apiLogout, updatePwd } from '../api/api'
import { Notebook } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const user = ref(JSON.parse(localStorage.getItem('user') || '{}'))
const pwdVisible = ref(false)
const pwdForm = ref({ upassword: '' })

const typeText = computed(() => {
  const map = { 0: '管理员', 1: '馆员', 2: '普通用户' }
  return map[user.value.utype] || '未知'
})

const logout = async () => {
try{
    await ElMessageBox.confirm('确定退出登录吗？', '提示', { type: 'warning' })
  await apiLogout()
  localStorage.removeItem('user')
  router.push('/login')
}catch{}
}

const changePwd = () => {
  pwdForm.value.upassword = ''
  pwdVisible.value = true
}

const confirmChangePwd = async () => {
  if (!pwdForm.value.upassword) {
    ElMessage.warning('请输入新密码')
    return
  }
  await updatePwd({ uid: user.value.uid, upassword: pwdForm.value.upassword })
  ElMessage.success('修改成功，请重新登录')
  pwdVisible.value = false
  localStorage.removeItem('user')
  router.push('/login')
}
</script>
