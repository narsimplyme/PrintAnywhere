// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import vmodal from 'vue-js-modal'
import geolocation from 'vue-browser-geolocation'
Vue.config.productionTip = false

/* eslint-disable no-new */
Vue.use(vmodal, { dynamic: true })
Vue.use(geolocation)
new Vue({
  el: '#printaw',
  router,
  template: '<App/>',
  components: { App }
})
