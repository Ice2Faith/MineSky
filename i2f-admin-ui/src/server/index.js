import Vue from 'vue'

const _server={
  baseUrl:'http://localhost:8888/',
  loginUrl:'login'
};

Vue.prototype.$server=_server;

export default _server;
