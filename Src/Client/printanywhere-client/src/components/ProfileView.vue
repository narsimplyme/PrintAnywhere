<template>
  <main class="mdl-layout__content">
    <div class="mdl-card mdl-shadow--6dp">
      <div class="mdl-card__title mdl-card--expand">
        <h2 class="mdl-card__title-text">Profile</h2>
      </div>
      <div class="mdl-card__supporting-text">
        <form @submit="onChangeProfile">
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="username" v-model="username" disabled/>
            <label class="mdl-textfield__label" for="username">아이디</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="point" v-model="point" disabled/>
            <label class="mdl-textfield__label" for="point">포인트</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="password" id="password" v-model="password" />
            <label class="mdl-textfield__label" for="password">기존 비밀번호</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="password" id="newPassword" v-model="newPassword" />
            <label class="mdl-textfield__label" for="newPassword">새 비밀번호</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="password" id="newPasswordConfirm" v-model="newPasswordConfirm" />
            <label class="mdl-textfield__label" for="newPasswordConfirm">새 비밀번호 확인</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="name" v-model="name" required/>
            <label class="mdl-textfield__label" for="name">이름*</label>
            <span class="mdl-textfield__error">필수 입력란 입니다.</span>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="nickName" v-model="nickName" required/>
            <label class="mdl-textfield__label" for="nickName">닉네임*</label>
            <span class="mdl-textfield__error">필수 입력란 입니다.</span>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" pattern="-?[0-9]*(\.[0-9]+)?" id="phone" v-model="phone" required/>
            <label class="mdl-textfield__label" for="phone">연락처*</label>
            <span class="mdl-textfield__error">숫자만 입력해 주세요.</span>
          </div>
          <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="email" id="email" v-model="email" required/>
            <label class="mdl-textfield__label" for="email">이메일*</label>
            <span class="mdl-textfield__error">올바른 이메일 형식을 입력해 주세요.</span>
          </div>
          <button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect">Change</button>
        </form>
      </div>
    </div>
  </main>
</template>

<script>
import testuser from '../temp/testuser'

export default {
  name: 'profile',
  data () {
    return {
      auth: false,
      username: testuser.test_user.userId,
      point: testuser.test_user.userPoint,
      name: testuser.test_user.userName,
      nickName: testuser.test_user.userNickName,
      phone: testuser.test_user.userPhoneNumber,
      email: testuser.test_user.userMail
    }
  },
  methods: {
    onChangeProfile () {
      if (this.name === '' || this.nickName === '' || this.phone === '' || this.email === '') {
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
      if (!this.auth) {
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
