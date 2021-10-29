import router from './router'
import store from './store'

import { getToken } from '@/auth'


const whiteList = ['/login']

router.beforeEach((to, from, next) => {
  if (getToken()) {
    /* has token*/
    if (to.path === '/') {
      next({ path: '/login' })
    } else {
      //获取角色权限判定是否拥有权限在进入
      next()
    }
  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      console.log('toLogin');
      next(`/login?redirect=${encodeURI(to.fullPath)}`) // 否则全部重定向到登录页
    }
  }
})

