import Vue from 'vue'
import Router from 'vue-router'
import HomeView from '@/components/HomeView'
import SigninView from '@/components/SigninView'
import SignupView from '@/components/SignupView'
import ProfileView from '@/components/ProfileView'
import PointView from '@/components/PointView'
import ClientView from '@/components/ClientView'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/signin',
      name: 'signin',
      component: SigninView
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignupView
    },
    {
      path: '/profile',
      name: 'profile',
      component: ProfileView
    },
    {
      path: '/point',
      name: 'point',
      component: PointView
    },
    {
      path: '/client',
      name: 'client',
      component: ClientView
    }
  ]
})
