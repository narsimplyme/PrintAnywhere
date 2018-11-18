<template>
  <main class="mdl-layout__content">
    <div class="mdl-card mdl-shadow--6dp">
      <div class="mdl-card__title">
        <h2 class="mdl-card__title-text">Welcome</h2>
      </div>
      <div class="mdl-card__supporting-text">
        <form @submit="onSignIn">
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="username" v-model="username" />
            <label class="mdl-textfield__label" for="username">아이디</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="password" id="password" v-model="password" />
            <label class="mdl-textfield__label" for="password">비밀번호</label>
          </div>
          <button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect">SignIn</button>
          <router-link to="/signup" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect">SignUp</router-link>
        </form>
      </div>
    </div>
  </main>
</template>

<script>
import axios from 'axios'
export default {
  name: 'singin',
  data () {
    return {
      username: '',
      password: '',
      auth: false
    }
  },
  methods: {
    onSignIn () {
      if (this.username === '' || this.password === '') {
        return
      }
      // TODO api/auth/signin [POST]
      document.cookie = `accessToken=${(this.username + this.password)}`
      document.cookie = `uid=${(this.username)}`
      axios.defaults.headers.common['x-access-token'] = this.username + this.password
      this.$router.push('/')
      location.reload()
    },
    setAuth () {
      var value = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')
      if (value[2] === '') {
        this.auth = false
      } else {
        this.auth = true
      }
      if (this.auth) {
        this.$router.push('/')
        location.reload()
      }
    }
  },
  beforeMount () {
    this.setAuth()
  }
}
</script>

<style>
  .mdl-layout {
    align-items: center;
  }
  .mdl-layout__content {
    padding: 24px;
    flex: none;
  }
  .mdl-card__actions{
    align-items: center;
  }
  .mdl-card__title{
    color: #fff;
    height: 170px;
    background: url('../assets/welcome_card.jpg') center / cover;
  }
</style>
