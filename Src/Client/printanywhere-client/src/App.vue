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
        <router-link class="mdl-navigation__link" to="/" @click.native="hideMenu" replace>파일 업로드</router-link>
        <router-link class="mdl-navigation__link" to="/profile" @click.native="hideMenu" replace>회원정보</router-link>
        <router-link class="mdl-navigation__link" to="/point" @click.native="hideMenu" replace>포인트 충전</router-link>
        <router-link class="mdl-navigation__link" to="/client" @click.native="hideMenu" replace>주변 프린터</router-link>
        <router-link class="mdl-navigation__link"  to="/" @click.native="unAuth" replace>로그아웃</router-link>
      </nav>
      <nav class="mdl-navigation" v-else>
        <router-link class="mdl-navigation__link" to="/" @click.native="hideMenu" replace>홈</router-link>
        <router-link class="mdl-navigation__link" to="/signin" @click.native="hideMenu" replace>로그인</router-link>
        <router-link class="mdl-navigation__link" to="/signup" @click.native="hideMenu" replace>회원가입</router-link>
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
  methods: {
    hideMenu: function () {
      document.getElementsByClassName('mdl-layout__drawer')[0].classList.remove('is-visible')
      document.getElementsByClassName('mdl-layout__obfuscator')[0].classList.remove('is-visible')
      location.reload()
    },
    unAuth: function () {
      document.cookie = `accessToken=`
      document.getElementsByClassName('mdl-layout__drawer')[0].classList.remove('is-visible')
      document.getElementsByClassName('mdl-layout__obfuscator')[0].classList.remove('is-visible')
      location.reload()
    },
    setAuth () {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      if (token === '' || token === 'undefined') {
        this.auth = false
      } else {
        this.auth = true
      }
    }
  },
  beforeMount () {
    this.setAuth()
  }
}
</script>

<style>
body {
  margin: 0;
}

</style>
