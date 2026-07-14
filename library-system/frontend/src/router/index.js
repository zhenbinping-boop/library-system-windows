import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../components/Layout.vue'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', name: 'Login', component: Login },
  {
    path: '/admin',
    component: Layout,
    children: [
      { path: 'dashboard', name: 'Dashboard', component: () => import('../views/Dashboard.vue') },
      { path: 'users', name: 'Users', component: () => import('../views/Users.vue') },
      { path: 'booktype', name: 'BookType', component: () => import('../views/BookType.vue') },
      { path: 'readertype', name: 'ReaderType', component: () => import('../views/ReaderType.vue') },
      { path: 'books', name: 'Books', component: () => import('../views/Books.vue') },
      { path: 'readers', name: 'Readers', component: () => import('../views/Readers.vue') },
      { path: 'borrow', name: 'Borrow', component: () => import('../views/Borrow.vue') },
      { path: 'borrow-all', name: 'BorrowAll', component: () => import('../views/BorrowAll.vue') },
      { path: 'myborrow', name: 'MyBorrow', component: () => import('../views/MyBorrow.vue') },
      { path: 'search', name: 'Search', component: () => import('../views/Search.vue') },
      {path:'operation',name:'Operation',component:()=>import('../views/Operation.vue')}
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫：未登录跳转到登录页
router.beforeEach((to, from, next) => {
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  if (to.path !== '/login' && !user.uid) {
    next('/login')
  } else {
    next()
  }
})

export default router
