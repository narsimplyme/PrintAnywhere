<template>
  <main class="mdl-layout__content">
    <div class="mdl-card mdl-shadow--6dp">
      <div class="mdl-card__title mdl-card--expand">
        <h2 class="mdl-card__title-text">SingUp</h2>
      </div>
      <div class="mdl-card__supporting-text">
        <div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="username" v-model="username" />
            <label class="mdl-textfield__label" for="username">아이디</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="password" id="password" v-model="password" />
            <label class="mdl-textfield__label" for="password">비밀번호</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="password" id="passwordConfirm" v-model="passwordConfirm" v-on:change="onCheckPassword()" />
            <label class="mdl-textfield__label" id="passwordConfirmLabel" for="passwordConfirm">비밀번호 확인</label>
            <span class="mdl-textfield__error" id="passwordConfirmError" name="passwordConfirmError">비밀번호가 동일해야 합니다.</span>
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
          <button type="button" v-on:click="onSignUp ()" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect">SingUp</button>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import axios from 'axios'
export default {
  name: 'singup',
  data () {
    return {
      auth: false
    }
  },
  methods: {
    onCheckPassword () {
      if (this.password === '' || this.passwordConfirm === '') {
        return false
      }
      if (this.password !== this.passwordConfirm) {
        document.getElementById('passwordConfirmLabel').style.color = '#d50000'
        document.getElementById('passwordConfirmError').style.visibility = 'visible'
        return false
      } else {
        document.getElementById('passwordConfirmLabel').style.color = 'rgb(63,81,181)'
        document.getElementById('passwordConfirmError').style.visibility = 'hidden'
        return true
      }
    },
    onSignUp () {
      if (this.username === '' || this.password === '' || this.passwordConfirm === '' || this.name === '' || this.nickName === '' || this.phone === '' || this.email === '') {
        return
      }
      if (this.onCheckPassword() === false) {
        return
      }
      axios.post('http://xdkyu02.cafe24.com/signUp.do', {
        'userId': this.username,
        'userPw': this.password,
        'userName': this.name,
        'userMail': this.email,
        'userNickname': this.nickName,
        'userPhoneNumber': this.phone
      },
      {
        headers: {
          'Content-Type': 'application/json'
        }
      }
      ).then(response => {
        document.cookie = `accessToken=${(response.data.data.token)}`
        this.$router.push('/')
        location.reload()
      }).catch(errors => {

      })
    },
    setAuth () {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      if (token === '') {
        this.auth = false
        return
      }
      axios.get('http://xdkyu02.cafe24.com/authMe.do', {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        if (response.data.success === true) {
          this.auth = true
          this.$router.push('/')
          location.reload()
        }
      }).catch(errors => {
        this.auth = false
      })
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
