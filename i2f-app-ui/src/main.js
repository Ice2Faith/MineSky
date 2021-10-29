// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Cookies from 'js-cookie'
import store from './store'
import request from './request'
import {getToken,setToken,removeToken} from "./auth";
import './permission'
import './server'
import B64 from "./utils/base64";
import AES from "./utils/aes";

Vue.config.productionTip = false

Vue.prototype.$cookies=Cookies;
Vue.prototype.$axios=request;
Vue.prototype.$token={
  get:getToken,
  set:setToken,
  remove:removeToken
};
Vue.prototype.$b64=B64;
Vue.prototype.$aes=AES;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
