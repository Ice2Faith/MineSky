import axios from 'axios'
import { getToken } from '@/auth'
import server from '@/server'
import AES from "../utils/aes";
import B64 from "../utils/base64";
import {Base64} from "js-base64";
// import {Message,MessageBox} from 'element-ui';
import router from '@/router';


axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: server.baseUrl,
  // 超时
  timeout: 60000
})
// request拦截器
service.interceptors.request.use(config => {
  config.method=config.method.toLowerCase();
  // 是否需要设置 token
  const isToken = (config.headers || {}).isToken === false
  if (getToken() && !isToken) {
    config.headers['Authorization'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
  }
  // get请求映射params参数
  if (config.method === 'get' && config.params) {
    let url = config.url + '?';
    for (const propName of Object.keys(config.params)) {
      const value = config.params[propName];
      var part = encodeURIComponent(propName) + "=";
      if (value !== null && typeof(value) !== "undefined") {
        if (typeof value === 'object') {
          for (const key of Object.keys(value)) {
            let params = propName + '[' + key + ']';
            var subPart = encodeURIComponent(params) + "=";
            url += subPart + encodeURIComponent(value[key]) + "&";
          }
        } else {
          url += part + encodeURIComponent(value) + "&";
        }
      }
    }
    url = url.slice(0, -1);
    config.params = {};
    config.url = url;

  }

  console.log('headers:',config.headers);
  if(config.headers['secure-body']){
    if(config.method=='post'){
      console.log('requestInSecure');
      let AES_KEY=AES.genKey('ltb12315');
      config.data=AES.encryptObj(config.data,AES_KEY);
      console.log('secReqData:',config.data);
    }
  }

  console.log('reqUrl:',config.url);
  // if(config.url.indexOf('login')<0){
  //
  // }

  let encUrl=Base64.encodeURI(config.url);
  config.url='/enc/'+encUrl;

  return config
}, error => {
  console.log(error)
  Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(res => {
    console.log('res:',res);
    if(res.headers['secure-data'] && res.headers['secure-data']=='true'){
      console.log('sec:data:',res.data);
      let AES_KEY=AES.genKey('ltb12315');
      res.data.data=AES.decryptObj(res.data.data,AES_KEY);
      let kvs={}
      for(let key in res.data.kvs){
        kvs[key]=AES.decryptObj(res.data.kvs[key],AES_KEY);
      }
      res.data.kvs=kvs;
      console.log('sec:rec:',res.data);
    }
    // 未设置状态码则默认成功状态
    const code = res.data.code || 200;
    // 获取错误信息
    const msg =  res.data.msg
    if (code === 401 ) {
      alert('登录状态已过期，您可以继续留在该页面，或者重新登录');
      // MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录','登录过期',{
      //   confirmButtonText: '确定',
      //   cancelButtonText: '取消',
      //   type: 'warning'
      // }).then(() => {
      //   router.push('/login?redirect='+encodeURIComponent(router.path));
      // }).catch(() => {
      //   Message.error('继续停留！');
      // });

      router.push('/login?redirect='+encodeURIComponent(router.path));

      // location.href = '/login';
    } else if (code === 500) {
      // Message.error('后端异常：'+msg);
      console.error(msg);
      return Promise.reject(new Error(msg))
    } else if (code !== 200) {
      // Message.error('响应异常：'+msg);
      console.warn(msg);
      return Promise.reject('error')
    } else {
      return res.data
    }
  },
  error => {
    console.log('err' , error)
    let { message } = error;
    if (message == "Network Error") {
      message = "后端接口连接异常";
    }
    else if (message.includes("timeout")) {
      message = "系统接口请求超时";
    }
    else if (message.includes("Request failed with status code")) {
      message = "系统接口" + message.substr(message.length - 3) + "异常";
    }
    // Message.error('错误：'+message);
    alert('错误：'+message);
    return Promise.reject(error)
  }
)

export default service
