import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'animate.css/animate.min.css'
import './assets/myCSS/myFont.css'

import * as echarts from 'echarts'
Vue.prototype.$echarts = echarts
var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8443/api'
// axios.defaults.baseURL = 'http://123.60.210.166:8443/api'
Vue.prototype.$axios = axios
Vue.config.productionTip = false
axios.defaults.withCredentials = true

Vue.use(ElementUI)

router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    if (store.state.user.username) {
      next()
    } else {
      next({
        path: 'login',
        query: {redirect: to.fullPath}
      })
    }
  } else {
    next()
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store,
  components: { App },
  template: '<App/>'
})
