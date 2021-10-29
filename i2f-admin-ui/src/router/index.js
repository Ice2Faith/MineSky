import Vue from 'vue'
import Router from 'vue-router'
import Login from "@/views/Login";
import HttpClient from "../views/HttpClient";
import Main from "../views/Main";
import RichText from "../views/RichText";
import FileUploadDownload from "../views/FileUploadDownload";
import FilesManage from "../views/FilesManage";

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
      path: '/main',
      name:'Main',
      component: Main
    },
    {
      path:'/http/client',
      name:'HttpClient',
      component: HttpClient
    },
    {
      path: '/rich/text',
      name:'RichText',
      component: RichText
    },
    {
      path: '/file/upload',
      name:'FileUploadDownload',
      component: FileUploadDownload
    },
    {
      path: '/files/manage',
      name:'FilesManage',
      component: FilesManage
    },
  ]
})
