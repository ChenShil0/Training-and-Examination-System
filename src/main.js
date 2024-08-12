// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import echarts from 'echarts'
import axios from 'axios'
//import './assets/font/iconfont.css'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueCookies from 'vue-cookies'

import store from '@/vuex/store'
import VueParticles from 'vue-particles'
Vue.use(VueParticles)


Vue.use(ElementUI)
Vue.use(VueCookies)

Vue.config.productionTip = false
Vue.prototype.bus = new Vue()
Vue.prototype.$echarts = echarts




Vue.prototype.$axios = axios

// this.$message.warning("用户信息已过期，请重新登录");
axios.defaults.baseURL = 'http://36.138.114.105:32261/'
//请求在到达服务器之前，先会调用use中的这个回调函数来添加请求头信息
// axios.interceptors.request.use(config=>{
//   //为请求头对象，添加token验证的Authorization字段
//   config.headers.Authorization = localStorage.getItem("token")
//   return config
// })
//
//
// // 配置axios全局拦截器
// axios.interceptors.response.use(
//   response => response,
//   async error => {
//     const status = error.response ? error.response.status : null;
//
//     console.log(status)
//     if (status === 401) {
//       // Token失效，跳转到登录页面
//       // window.location.href = '/login';
//       await app.$router.push('/login');
//       // 显示提示框
//       Message.error('Token无效或已过期，请重新登录');
//     }
//     return Promise.reject(error);
//   }
// );


new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
  components: { App },
  template: '<App/>'
})
// 请求在到达服务器之前，先会调用use中的这个回调函数来添加请求头信息
axios.interceptors.request.use(config => {
  // 从cookie中获取需要的值
  const cname = Vue.$cookies.get("cname");
  const cid = Vue.$cookies.get("cid");
  const role = Vue.$cookies.get("role");

  console.log(cname, cid, role)

  // 添加请求头信息
  config.headers["X-Cookie-Cname"] = encodeURIComponent(cname);
  config.headers["X-Cookie-Cid"] = encodeURIComponent(cid);
  config.headers["X-Cookie-Role"] = encodeURIComponent(role);

  return config;
});

// 配置axios全局拦截器
axios.interceptors.response.use(
  response => response,
  async error => {
    const status = error.response ? error.response.status : null;

    console.log(status);
    if (status === 401) {
      // Token失效，跳转到登录页面
      // window.location.href = '/login';
      await Vue.$router.push('/login');
      // 显示提示框
      Message.error('Token无效或已过期，请重新登录');
    }
    return Promise.reject(error);
  }
);

