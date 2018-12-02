<template>
  <div class="mdl-layout__content mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone" v-if="auth">
    <div class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-shadow--4dp">
      <div class="mdl-card__supporting-text uploadFile-listing">
        <div class="mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone">
          <div v-for="(uploadFile, index) in this.uploadFileslist" :key="index" class="mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone">
            <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" v-on:click="onRemoveFile( index )">취소</button>&nbsp;&nbsp;&nbsp;&nbsp;<span>{{ uploadFile.name }}</span>
          </div>
        </div>
      </div>
      <div class="mdl-card__actions mdl-card--border">
        <label class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored" :disabled="this.fileUploadProgress == true" >
          업로드할 파일 선택
          <input type="file" ref="uploadFileInput" multiple v-on:change="onFileUpload()" />
        </label>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button v-on:click="onSubmitFiles()" :disabled="this.fileUploadProgress == true" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">파일 업로드</button>
      </div>
    </div>
    <div v-for="file in this.filelist" :key="file.id" v-bind:class="{'mdl-color--teal-100' : file.isReserved}" class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-shadow--4dp">
      <div class="mdl-card__title">
        <h2 class=mdl-card__title-text v-text="file.fileName"></h2>
      </div>
      <div class="mdl-card__supporting-text file-info">
        <div v-if="(file.fileSize < 1024)">파일 크기 : <span v-text="file.fileSize"></span> b</div>
        <div v-else-if="(file.fileSize < 1024*1024)">파일 크기 : <span v-text="(Math.floor(file.fileSize / 1024))"></span> kb</div>
        <div v-else-if="(file.fileSize < 1024*1024*1024)">파일 크기 : <span v-text="(Math.floor(file.fileSize / (1024*1024)))"></span> mb</div>
        <div v-else-if="(file.fileSize < 1024*1024*1024*1024)">파일 크기 : <span v-text="(Math.floor(file.fileSize / (1024*1024*1024)))"></span> gb</div>
        <div v-else>파일 크기 : <span v-text="(Math.floor(file.fileSize / (1024*1024*1024*1024)))"></span> tb</div>
        <div>업로드 시간 : <span v-text="(file.fileDate.replace('.0', ''))"></span></div>
      </div>
      <div class="mdl-card__actions mdl-card--border">
        <button v-on:click="onUnReserve(file.reserveId)" v-if="file.isReserved" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored">예약취소</button>
        <button v-on:click="onSetReserveFileId(file.fileId)" v-else class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored">예약하기</button>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button v-on:click="onDelete(file.fileId)" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">파일삭제</button>
      </div>
      <div class="mdl-card__menu">
        <i v-on:click="onDownload(file.fileId, file.fileName)" class="material-icons">cloud_download</i>
      </div>
    </div>
    <modal name="reserve-modal-client" :scrollable="true">
      <div class="mdl-card mdl-cell mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-shadow--4dp div-center">
        <div class="mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone">
          <date-picker v-model="reserveTime" lang="kr" type="datetime" format="YYYY-MM-DD HH:mm:ss"></date-picker>
        </div>
        <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp mdl-grid--no-spacing mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone">
          <thead class="client-list">
            <th class="mdl-data-table__cell--non-numeric">예약하기</th>
            <th class="text-align-left">주변 프린터</th>
          </thead>
          <tbody>
            <tr v-for="client in this.bookmarkedClientlist" :key="client.clientId">
              <td class="mdl-data-table__cell--non-numeric">
                <button v-on:click="onSetReserveClientId(client.clientId)" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored">예약하기</button>
              </td>
              <td v-text="client.clientName" class="text-align-left">
              </td>
            </tr>
            <tr v-for="client in this.nearClientlist" :key="client.clientId">
              <td class="mdl-data-table__cell--non-numeric">
                <button v-on:click="onSetReserveClientId(client.clientId)" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored">예약하기</button>
              </td>
              <td v-text="client.clientName" class="text-align-left">
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </modal>
  </div>
  <div v-else>
    <video :poster="poster" autoplay muted playsinline loop class="background-vid">
      <source :src="videoSrcWebm" type="video/webm" />
      <source :src="videoSrcMp4" type="video/mp4">
      <source :src="videoSrcOgg" type="video/ogg">
    </video>
    <div class="text-overlay">
      <h1><img src="../assets/logo.png" alt="프린트애니웨어"></h1>
      <h2>프린트애니웨어는 공용 프린터를 손쉽게 이용할 수 있도록 맞춤 서비스를 제공합니다.</h2>
  </div>
  </div>
</template>
<script>
import axios from 'axios'
import DatePicker from 'vue2-datepicker'
import backgroundVideo from '../assets/1.mp4'
import backgroundVideoWebm from '../assets/1.webm'
import backgroundVideoOgg from '../assets/1.ogg'
import backgroundPoster from '../assets/poster.png'
require('material-design-lite')
export default {
  components: {
    DatePicker
  },
  name: 'home',
  data () {
    return {
      auth: '',
      token: null,
      filelist: [],
      uploadFileslist: [],
      username: '',
      bookmarkedClientlist: [],
      nearClientlist: [],
      coordinates: '',
      reserveFileId: '',
      reserveClientId: '',
      reserveTime: '',
      fileUploadProgress: false,
      videoSrcMp4: backgroundVideo,
      videoSrcWebm: backgroundVideoWebm,
      videoSrcOgg: backgroundVideoOgg,
      poster: backgroundPoster
    }
  },
  methods: {
    onShowReserveClientModal () {
      this.$modal.show('reserve-modal-client')
    },
    onSetReserveFileId (fileId) {
      this.reserveFileId = fileId
      this.onShowReserveClientModal()
    },
    onSetReserveClientId (clientId) {
      this.reserveClientId = clientId
      if (Math.floor(((new Date().getTime() - new Date(this.reserveTime).getTime()) / 1000)) < 0) {
        this.onReserve()
      } else {
        alert('이전 시간은 선택할 수 없습니다.')
      }
    },
    onReserve () {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      let reserveDate = new Date(this.reserveTime)
      axios.post('http://xdkyu02.cafe24.com/insertReserve.do', {
        'userId': this.username,
        'fileId': this.reserveFileId,
        'clientId': this.reserveClientId,
        'reserveTime': ((reserveDate.getFullYear() + '-' + ((reserveDate.getMonth() + 1) < 10 ? '0' + (reserveDate.getMonth() + 1) : (reserveDate.getMonth() + 1)) + '-' + (reserveDate.getDate() < 10 ? '0' + reserveDate.getDate() : reserveDate.getDate()) + ' ' + (reserveDate.getHours() < 10 ? '0' + reserveDate.getHours() : reserveDate.getHours()) + ':' + (reserveDate.getMinutes() < 10 ? '0' + reserveDate.getMinutes() : reserveDate.getMinutes()) + ':' + (reserveDate.getSeconds() < 10 ? '0' + reserveDate.getSeconds() : reserveDate.getSeconds())))
      },
      {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        this.$modal.hide('reserve-modal-client')
        this.onGetFileList()
        this.onGetReservedFile()
        this.onGetGeolocation()
        alert('예약이 완료되었습니다.')
      }).catch(errors => {

      })
    },
    onGetReservedFile () {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      axios.get('http://xdkyu02.cafe24.com/selectReserveList.do', {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        for (var i = 0; response.data.data.reserveList.length; i++) {
          let file = this.filelist.find(f => f.fileId === response.data.data.reserveList[i].fileId)
          file.isReserved = true
          file.reserveId = response.data.data.reserveList[i].reserveId
        }
      }).catch(errors => {

      })
    },
    onUnReserve (reserveId) {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      axios.delete('http://xdkyu02.cafe24.com/deleteReserve.do', {
        data: {
          'reserveId': reserveId
        },
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        this.onGetFileList()
        this.onGetReservedFile()
        this.onGetGeolocation()
        alert('예약이 취소되었습니다.')
      }).catch(errors => {

      })
    },
    onDelete (fileId) {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      axios.delete('http://xdkyu02.cafe24.com/fileDelete.do', {
        data: {
          'fileId': fileId
        },
        headers: {
          'x-access-token': token,
          'Content-Type': 'application/json'
        }
      }).then(response => {
        this.onGetFileList()
        this.onGetReservedFile()
        this.onGetGeolocation()
        alert('파일이 삭제되었습니다.')
      }).catch(errors => {

      })
    },
    onDownload (fildId, fileName) {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      axios.get(`http://xdkyu02.cafe24.com/fileDownload.do?fileId=${(fildId)}`, {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        this.onFileDonwload(response.data.data.fileUrl, fileName)
      }).catch(errors => {

      })
    },
    onFileDonwload (url, fileName) {
      axios.get(url, {
        responseType: 'blob'
      }).then(response => {
        let link = document.createElement('a')
        link.href = window.URL.createObjectURL(new Blob([response.data]))
        link.setAttribute('download', fileName)
        document.body.appendChild(link)
        link.click()
        link.remove()
        window.URL.revokeObjectURL(url)
      })
    },
    onSubmitFiles () {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      let uploadFile = new FormData()
      for (var i = 0; i < this.uploadFileslist.length; i++) {
        uploadFile.append('uploadFile', this.uploadFileslist[i])
      }
      this.fileUploadProgress = true
      axios.post('http://xdkyu02.cafe24.com/fileUpload.do', uploadFile, {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        this.onGetFileList()
        this.onGetReservedFile()
        this.onGetGeolocation()
        this.uploadFileslist = []
        this.fileUploadProgress = false
        alert('파일이 업로드되었습니다.')
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
      axios.get('http://xdkyu02.cafe24.com/fileList.do?sizeOfList=10', {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        this.filelist = response.data.data.fileList
      }).catch(errors => {

      })
    },
    onSelectBookmarkList () {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      axios.get('http://xdkyu02.cafe24.com/selectBookMarkList.do', {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        for (var i = 0; response.data.data.bookMarkList.length; i++) {
          let client = this.nearClientlist.find(c => c.clientId === response.data.data.bookMarkList[i].clientId)
          this.bookmarkedClientlist.push(client)
          this.nearClientlist.splice(this.nearClientlist.indexOf(client), 1)
        }
      }).catch(errors => {

      })
    },
    onGetGeolocation () {
      this.$watchLocation(
      ).then(coordinates => {
        this.coordinates = coordinates
        var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
        axios.get(`http://xdkyu02.cafe24.com/selectNearClient.do?latitude=${(this.coordinates.lat)}&longitude=${(this.coordinates.lng)}`, {
          headers: {
            'x-access-token': token
          }
        }).then(response => {
          this.nearClientlist = response.data.data.nearClientList
          this.onSelectBookmarkList()
        }).catch(errors => {

        })
      }).catch(errors => {
        var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
        axios.get(`http://xdkyu02.cafe24.com/selectNearClient.do?latitude=37.2222222&longitude=127.1879819000000007`, {
          headers: {
            'x-access-token': token
          }
        }).then(response => {
          this.nearClientlist = response.data.data.nearClientList
          this.onSelectBookmarkList()
        }).catch(errors => {

        })
      })
    },
    setAuth () {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      if (token === '' || token === 'undefined') {
        this.auth = false
        return
      }
      axios.get('http://xdkyu02.cafe24.com/authMeFull.do', {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        if (response.data.success === false) {
          this.refreshToken()
        } else {
          this.auth = true
          this.username = response.data.data.user.userId
        }
      }).catch(errors => {
        this.auth = false
      })
    },
    refreshToken () {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      axios.get('http://xdkyu02.cafe24.com/refresh.do', {
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
    this.onGetReservedFile()
    this.onGetGeolocation()
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
.v--modal-box{
  left: 0px !important;
  width: calc(100%) !important;
  height: auto !important;
  background-color: transparent !important;
  box-shadow: 0 0 0 0 !important;
}
.div-center{
  margin: 0 auto !important;
}
.text-align-left{
  text-align: left !important;
}
.background-vid {
    position: absolute;
    top: 50%;
    left: 50%;
    min-width: 100%;
    min-height: 100%;
    width: auto;
    height: auto;
    z-index: -100;
    -webkit-transform: translateX(-50%) translateY(-50%);
       -moz-transform: translateX(-50%) translateY(-50%);
        -ms-transform: translateX(-50%) translateY(-50%);
    transform: translateX(-50%) translateY(-50%);
    background-size: cover;
}
.text-overlay {
  font-weight:100;
  background: rgba(0,0,0,0.3);
  color: white;
  padding: 2rem;
  margin: 2rem;
  width: auto;
  min-height: 410px;
}
.text-overlay h1 {
  color: #fff;
  font-size: 38px;
}
.text-overlay h2 {
  font-size: 28px;
  color: #fff;
}
</style>
