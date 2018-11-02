<template>
  <main class="mdl-layout__content">
    <div class="mdl-card mdl-shadow--6dp">
      <div class="mdl-card__supporting-text">
        <form action="#">
          <div class="mdl-textfield mdl-js-textfield">
            <input class="mdl-textfield__input" type="text" id="username" />
            <label class="mdl-textfield__label" for="username">{{ username }}</label>
          </div>
          <div class="mdl-textfield mdl-js-textfield">
            <input class="mdl-textfield__input" type="password" id="userpass" />
            <label class="mdl-textfield__label" for="userpass">{{ password }}</label>
          </div>
        </form>
      </div>
    </div>
    <div class="mdl-card__actions mdl-card--border">
      <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">{{ login }}</button>
    </div>
  </main>
</template>

<script>
export default {
  name: 'login',
  data () {
    return {
      login: '로그인',
      password: '비밀번호',
      username: '아이디'
    }
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
</style>
