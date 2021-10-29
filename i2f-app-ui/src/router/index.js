import Vue from 'vue'
import Router from 'vue-router'
import Login from "@/views/Login";
import HttpClient from "../views/HttpClient";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect:'/login'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path:'/http/client',
      name:'HttpClient',
      component: HttpClient
    }
  ]
})
