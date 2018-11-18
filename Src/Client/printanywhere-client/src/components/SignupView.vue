<template>
  <main class="mdl-layout__content">
    <div class="mdl-card mdl-shadow--6dp">
      <div class="mdl-card__title mdl-card--expand">
        <h2 class="mdl-card__title-text">SingUp</h2>
      </div>
      <div class="mdl-card__supporting-text">
        <form @submit="onSingUp">
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="username" v-model="username" />
            <label class="mdl-textfield__label" for="username">아이디</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="password" id="password" v-model="password" />
            <label class="mdl-textfield__label" for="password">비밀번호</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="password" id="passwordConfirm" v-model="passwordConfirm" />
            <label class="mdl-textfield__label" for="passwordConfirm">비밀번호 확인</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="name" v-model="name" />
            <label class="mdl-textfield__label" for="name">이름</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="nickName" v-model="nickName" />
            <label class="mdl-textfield__label" for="nickName">닉네임</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" pattern="-?[0-9]*(\.[0-9]+)?" id="phone" v-model="phone" />
            <label class="mdl-textfield__label" for="phone">연락처</label>
            <span class="mdl-textfield__error">숫자만 입력해 주세요.</span>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="email" id="email" v-model="email" />
            <label class="mdl-textfield__label" for="email">이메일</label>
            <span class="mdl-textfield__error">올바른 이메일 형식을 입력해 주세요.</span>
          </div>
          <button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect">SingUp</button>
        </form>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  name: 'singup',
  data () {
    return {
      auth: false
    }
  },
  methods: {
    onSignUp () {
      if (this.username === '' || this.password === '' || this.passwordConfirm === '' || this.name === '' || this.nickName === '' || this.phone === '' || this.email === '') {
        return
      }
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
  .mdl-card__title{
    height: 200px;
    color: #fff;
    background: url('../assets/dog.png') bottom right 15% no-repeat #46B6AC;
  }
</style>
