<template>
  <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <header class="mdl-layout__header">
      <div class="mdl-layout__header-row">
        <span class="mdl-layout-title">프린트애니웨어</span>
      </div>
    </header>
    <div class="mdl-layout__drawer">
      <span class="mdl-layout-title">프린트애니웨어</span>
      <nav class="mdl-navigation" v-if="auth">
        <router-link class="mdl-navigation__link" to="/" @click.native="hideMenu" replace>Home</router-link>
        <router-link class="mdl-navigation__link" to="/profile" @click.native="hideMenu" replace>Profile</router-link>
        <router-link class="mdl-navigation__link" to="/point" @click.native="hideMenu" replace>Point</router-link>
        <router-link class="mdl-navigation__link" to="/client" @click.native="hideMenu" replace>Client</router-link>
        <router-link class="mdl-navigation__link"  to="/" @click.native="unAuth" replace>Logout</router-link>
      </nav>
      <nav class="mdl-navigation" v-else>
        <router-link class="mdl-navigation__link" to="/" @click.native="hideMenu" replace>Home</router-link>
        <router-link class="mdl-navigation__link" to="/signin" @click.native="hideMenu" replace>SignIn</router-link>
        <router-link class="mdl-navigation__link" to="/signup" @click.native="hideMenu" replace>SignUp</router-link>
      </nav>
    </div>
    <main class="mdl-layout__content">
      <div class="page-content">
      <router-view></router-view>
      </div>
    </main>
  </div>
</template>

<script>
require('material-design-lite')
export default {
  name: 'app',
  data () {
    return {
      auth: false
    }
  },
  beforeMount () {
    this.setAuth()
  },
  methods: {
    hideMenu: function () {
      document.getElementsByClassName('mdl-layout__drawer')[0].classList.remove('is-visible')
      document.getElementsByClassName('mdl-layout__obfuscator')[0].classList.remove('is-visible')
      location.reload()
    },
    unAuth: function () {
      document.cookie = `accessToken=`
      document.cookie = `uid=`
      document.getElementsByClassName('mdl-layout__drawer')[0].classList.remove('is-visible')
      document.getElementsByClassName('mdl-layout__obfuscator')[0].classList.remove('is-visible')
      location.reload()
    },
    setAuth: function () {
      var value = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')
      if (value[2] === '') {
        this.auth = false
      } else {
        this.auth = true
      }
    }
  }
}
</script>

<style>
body {
  margin: 0;
}

</style>
