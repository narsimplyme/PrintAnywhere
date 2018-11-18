<template>
  <div class="mdl-layout__content mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone" v-if="auth">
    <h1>{{ uid }} {{ welcome }}</h1>
    <div v-for="file in this.filelist" :key="file.id" v-bind:class="{'mdl-color--teal-100' : file.isReserved}" class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-shadow--4dp">
      <div class="mdl-card__title">
        <h2 class=mdl-card__title-text v-text="file.fileName"></h2>
      </div>
      <div class="mdl-card__supporting-text">
        <div>Size : <span v-text="file.fileSize"></span></div>
        <div>Uploaded : <span v-text="file.fileUploadDate"></span></div>
      </div>
      <div class="mdl-card__actions mdl-card--border">
        <a @click.native="onReserve(file.fileId)" v-if="file.isReserved" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored ">UnReserve</a>
        <a @click.native="onUnReserve(file.fileId)" v-else class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored ">Reserve</a>
        <a @click.native="onDelete(file.fileId)" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">Delete</a>
      </div>
      <div class="mdl-card__menu">
        <i @click.native="onDownload(file.fileId)" class="material-icons">cloud_download</i>
      </div>
    </div>
  </div>
  <div class="mdl-layout__content" v-else>
    <img src="../assets/logo.png" alt="프린트애니웨어">
    <h1 v-text='msg'></h1>
  </div>
</template>
<script>
import files from '../temp/files'
require('material-design-lite')
export default {
  name: 'home',
  data () {
    return {
      msg: '프린트애니웨어에 오신걸 환영합니다.',
      welcome: '님 환영합니다.',
      auth: false,
      uid: null,
      'filelist': files.fileData
    }
  },
  methods: {
    setAuth () {
      var value = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')
      if (value[2] === '') {
        this.auth = false
      } else {
        this.auth = true
        this.uid = document.cookie.match('(^|;) ?' + 'uid' + '=([^;]*)(;|$)')[2]
      }
    },
    onReserve (fileId) {
      // TODO api/files/{fileId} [PUT]
    },
    onUnReserve (fileId) {
      // TODO api/files/{fileId} [PUT]
    },
    onDelete (fileId) {
      // TODO api/files/{fileId} [DELETE]
    },
    onDownload (fildId) {
      // TODO api/files/{fileId} [GET]
    }
  },
  beforeMount () {
    this.setAuth()
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
h1, h2 {
  font-weight: normal;
}
main {
  text-align: center;
}
.mdl-card--border{
  text-align: left;
}
.mdl-card__supporting-text{
  text-align: left;
}
.mdl-card__menu i{
  font-size: 48px;
  cursor: pointer;
}
</style>
