<template>
  <div class="mdl-layout__content mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone" v-if="auth">
    <div class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-shadow--4dp">
      <div class="mdl-card__supporting-text uploadFile-listing">
        <div class="mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone">
          <div v-for="(uploadFile, index) in this.uploadFileslist" :key="index" class="mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone">
            <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" v-on:click="onRemoveFile( index )">Remove</button>
            {{ uploadFile.name }}
          </div>
        </div>
      </div>
      <div class="mdl-card__actions mdl-card--border">
        <label class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored">
          Add Files
          <input type="file" ref="uploadFileInput" multiple v-on:change="onFileUpload()" />
        </label>
        <button v-on:click="onSubmitFiles()" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">Upload Files</button>
      </div>
    </div>
    <div v-for="file in this.filelist" :key="file.id" v-bind:class="{'mdl-color--teal-100' : file.isReserved}" class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-shadow--4dp">
      <div class="mdl-card__title">
        <h2 class=mdl-card__title-text v-text="file.fileName"></h2>
      </div>
      <div class="mdl-card__supporting-text file-info">
        <div>Size : <span v-text="file.fileSize"></span></div>
        <div>Uploaded : <span v-text="file.fileDate"></span></div>
      </div>
      <div class="mdl-card__actions mdl-card--border">
        <button v-on:click="onReserve(file.fileId)" v-if="file.isReserved" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored">UnReserve</button>
        <button v-on:click="onUnReserve(file.fileId)" v-else class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored">Reserve</button>
        <button v-on:click="onDelete(file.fileId)" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">Delete</button>
      </div>
      <div class="mdl-card__menu">
        <i v-on:click="onDownload(file.fileId)" class="material-icons">cloud_download</i>
      </div>
    </div>
  </div>
  <div class="mdl-layout__content" v-else>
    <img src="../assets/logo.png" alt="프린트애니웨어">
    <h1 v-text='msg'></h1>
  </div>
</template>
<script>
import axios from 'axios'
require('material-design-lite')
export default {
  name: 'home',
  data () {
    return {
      msg: '프린트애니웨어에 오신걸 환영합니다.',
      welcome: '님 환영합니다.',
      auth: false,
      token: null,
      filelist: [],
      uploadFileslist: []
    }
  },
  methods: {
    onReserve (fileId) {
      // TODO api/files/{fileId} [PUT]
    },
    onUnReserve (fileId) {
      // TODO api/files/{fileId} [PUT]
    },
    onDelete (fileId) {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      axios.delete('http://printaw.com/fileDelete.do', {
        data: {
          'fileId': fileId
        },
        headers: {
          'x-access-token': token,
          'Content-Type': 'application/json'
        }
      }).then(response => {
        this.onGetFileList()
      }).catch(errors => {

      })
    },
    onDownload (fildId) {
      // TODO api/files/{fileId} [GET]
    },
    onSubmitFiles () {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      let uploadFile = new FormData()
      for (var i = 0; i < this.uploadFileslist.length; i++) {
        uploadFile.append('uploadFile', this.uploadFileslist[i])
      }
      axios.post('http://printaw.com/fileUpload.do', uploadFile, {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        this.onGetFileList()
        this.uploadFileslist = []
      }).catch(errors => {

      })
    },
    onFileUpload () {
      let uploadedFiles = this.$refs.uploadFileInput.files
      for (var i = 0; i < uploadedFiles.length; i++) {
        this.uploadFileslist.push(uploadedFiles[i])
      }
    },
    onRemoveFile (index) {
      this.uploadFileslist.splice(index, 1)
    },
    onGetFileList () {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      axios.get('http://printaw.com/fileList.do?sizeOfList=10', {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        this.filelist = response.data.data.fileList
      }).catch(errors => {

      })
    },
    setAuth () {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      if (token === '') {
        this.auth = false
        return
      }
      axios.get('http://printaw.com/authMe.do', {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        if (response.data.success === false) {
          this.refreshToken()
        } else {
          this.auth = true
        }
      }).catch(errors => {
        this.auth = false
      })
    },
    refreshToken () {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      axios.get('http://printaw.com/refresh.do', {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        document.cookie = `accessToken=${(response.data.data.token)}`
        this.auth = true
      }).catch(errors => {
        document.cookie = `accessToken=`
      })
    }
  },
  beforeMount () {
    this.setAuth()
    this.onGetFileList()
  }
}
</script>

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
input[type="file"]{
  display: none;
}
div.uploadFile-listing{
  min-height: 120px;
}
div.file-info{
  min-height: 60px;
}
</style>
