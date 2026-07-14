<script setup>
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login, register } from '../api/api'

const router = useRouter()
const formRef = ref()
const loading = ref(false)
const isRegister = ref(false)
const form = ref({
  rid: '', upassword: '', confirmPasswd: '',
  rname: '', age: 18, tele: '', major: '', rtype: 2, indate: ''
})

watch(() => form.value.rid, (val) => {
  if (typeof val === 'string' && /[\u4e00-\u9fa5]/.test(val)) {
    form.value.rid = val.replace(/[\u4e00-\u9fa5]/g, '')
  }
})
const rules = {
  rid: [
    { required: true, message: '请输入账号', trigger: 'blur' }
  ],
  upassword: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  confirmPasswd: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: (rule, value, callback) => {
      if (isRegister.value && value !== form.value.upassword) {
        callback(new Error('两次密码不一致'))
      } else {
        callback()
      }
    }, trigger: 'blur' }
  ],
  rname: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  tele: [{ required: true, message: '请输入电话', trigger: 'blur' }],
  major: [{ message: '请输入所在系部', trigger: 'blur' }]
}

const handleSubmit = () => {
  formRef.value.validate(async valid => {
    if (!valid) return
    loading.value = true
    try {
      if (isRegister.value) {
        await register({
          rid: form.value.rid,
          upassword: form.value.upassword,
          rname: form.value.rname,
          age: form.value.age,
          tele: form.value.tele,
          major: form.value.major,
          rtype: form.value.rtype,
          indate: form.value.indate
        })
        ElMessage.success('注册成功，请登录')
        isRegister.value = false
        form.value = {
          rid: '', upassword: '', confirmPasswd: '',
          rname: '', age: 18, tele: '', major: '', rtype: 2, indate: ''
        }
      } else {
        const res = await login({ uname: form.value.rid, upassword: form.value.upassword })
        localStorage.setItem('user', JSON.stringify(res.data))
        ElMessage.success('登录成功')
        router.push('/admin/dashboard')
      }
    } catch (e) {
      console.error(e)
    } finally {
      loading.value = false
    }
  })
}
</script>

<template>
  <div class="login-container">
    <el-card class="login-box" shadow="always":class="{ 'register-mode': isRegister }">
      <div class="login-wrapper">
        <!-- 左侧图片区域 -->
          <div class="left-panel" v-show="!isRegister">
          <img
            src="https://pic.pngsucai.com/00/92/14/00ab60f4861fa4aa.webp"
            alt="图书馆"
            class="login-image"
          />

          <div class="image-overlay">
            <h2>欢迎使用图书馆管理系统</h2>
            
          </div>
        </div>

        <!-- 右侧表单区域 -->
        <div class="right-panel">
          <h2 style="text-align: center; margin-bottom: 10px; color: #409EFF;">图书馆管理系统</h2>
          <p style="text-align: center; color: #999; margin-bottom: 25px;">
            {{ isRegister ? '注册新账号' : '请登录您的账号' }}
          </p>

          <el-form :model="form" :rules="rules" ref="formRef" label-width="0">
            <el-form-item prop="rid">
              <el-input v-model="form.rid" :placeholder="isRegister ? '请输入账号' : '请输入账号'" size="large">
                <template #prefix><el-icon><User /></el-icon></template>
              </el-input>
            </el-form-item>
            <el-form-item prop="upassword">
              <el-input v-model="form.upassword" type="password" placeholder="请输入密码" size="large" show-password>
                <template #prefix><el-icon><Lock /></el-icon></template>
              </el-input>
            </el-form-item>
            <template v-if="isRegister">
              <el-form-item prop="confirmPasswd">
                <el-input v-model="form.confirmPasswd" type="password" placeholder="请再次输入密码" size="large" show-password>
                  <template #prefix><el-icon><Lock /></el-icon></template>
                </el-input>
              </el-form-item>
              <el-form-item prop="rname">
                <el-input v-model="form.rname" placeholder="请输入姓名" size="large" />
              </el-form-item>
              <el-form-item style="width: 100%;" label-width="40px" label="年龄">
                <el-input-number v-model="form.age" :min="1" :max="150" style="width: 100%;" />
              </el-form-item>
              <el-form-item prop="tele">
                <el-input v-model="form.tele" placeholder="请输入电话号码" size="large" />
              </el-form-item>
              <el-form-item prop="major">
                <el-input v-model="form.major" placeholder="请输入所在系部" size="large" />
              </el-form-item>
              <el-form-item style="width: 100%;">
                <el-select v-model="form.rtype" style="width: 70%;" size="middle">
                  <el-option label="学生" :value="2" />
                  <el-option label="教师" :value="1" />
                </el-select>
              </el-form-item>
            </template>
            <el-form-item>
              <el-button type="primary" size="large" style="width: 100%;" @click="handleSubmit" :loading="loading">
                {{ isRegister ? '注 册' : '登 录' }}
              </el-button>
            </el-form-item>
            <div style="text-align: center;">
              <el-link type="primary" :underline="false" @click="isRegister = !isRegister">
                {{ isRegister ? '已有账号？去登录' : '没有账号？去注册' }}
              </el-link>
            </div>
          </el-form>
        </div>
      </div>
    </el-card>
  </div>
</template>

<style scoped>.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #371504 0%, #c3cfe2 100%);
}

.login-box {
  width: 800px;
  max-width: 90vw;
  padding: 0;
  overflow: hidden;
  border-radius: 12px;
  transition: width 0.4s ease;
}

.login-box.register-mode {
  width: 450px;
}

.login-wrapper {
  display: flex;
  flex-wrap: nowrap;
  height: 100%;
}

/* 左侧图片区域 */
.left-panel {
  flex: 0 0 45%;
  position: relative;
  background: #f0f2f5;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  transition: flex 0.4s ease, opacity 0.3s ease;
}

.login-image {
  width: 120%;
  height: 120%;
  object-fit: cover;
  display: block;
}

.image-overlay {
  position: absolute;
  bottom: 30px;
  left: 0;
  right: 0;
  text-align: center;
  color: #fff;
  text-shadow: 0 2px 4px rgba(0,0,0,0.5);
  pointer-events: none;
}

.image-overlay h2 {
  font-size: 24px;
  margin-bottom: 5px;
  color: #fff;
}

.image-overlay p {
  font-size: 16px;
  opacity: 0.9;
  color: #fff;
}

/* 右侧表单区域 */
.right-panel {
  flex: 1;
  padding: 40px 30px;
  background: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  min-width: 0;
}

/* 适应小屏幕（手机）时改为上下排列 */
@media (max-width: 768px) {
  .login-box {
    width: 95vw;
  }
  .login-wrapper {
    flex-wrap: wrap;
  }
  .left-panel {
    flex: 0 0 200px;
    width: 100%;
  }
  .login-image {
    height: 200px;
    object-fit: cover;
  }
  .right-panel {
    padding: 20px;
  }
}
</style>