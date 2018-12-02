<template>
  <div class="mdl-layout__content mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone" v-if="auth">
    <div class="mdl-card__title">
      <h2 class="mdl-card__title-text">즐겨찾기한 프린터</h2>
    </div>
    <div v-for="client in this.bookmarkedClientlist" :key="client.clientId" class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-shadow--4dp">
      <div class="mdl-card__title">
        <h2 class="mdl-card__title-text" v-text="client.clientName"></h2>
      </div>
      <div class="mdl-card__supporting-text">
        <div>IP 주소 : <span v-text="client.clientIp"></span></div>
        <div v-if="Math.floor(((new Date().getTime() - new Date(client.clientLastSeen).getTime())/1000)/60) < 60">
          <div>마지막으로 연결된 시간 : <span v-text="Math.floor(((new Date().getTime() - new Date(client.clientLastSeen).getTime())/1000)/60)"></span> 분전</div>
        </div>
        <div v-else-if="Math.floor(((new Date().getTime() - new Date(client.clientLastSeen).getTime())/1000)/60) < 1440">
          <div>마지막으로 연결된 시간 : <span v-text="Math.floor(((new Date().getTime() - new Date(client.clientLastSeen).getTime())/1000)/(60*60))"></span> 시간전</div>
        </div>
        <div v-else>
          <div>마지막으로 연결된 시간 : <span v-text="Math.floor(((new Date().getTime() - new Date(client.clientLastSeen).getTime())/1000)/(60*60*24))"></span> 일전</div>
        </div>
      </div>
      <div class="mdl-card__menu">
        <i v-on:click="onUnBookmark(client.clientId)" class="material-icons bookmark">favorite</i>
        <i v-if="Math.floor(((new Date().getTime() - new Date(client.clientLastSeen).getTime())/1000)/60) < 60" class="material-icons on">signal_cellular_4_bar</i>
        <i v-else class="material-icons off">signal_cellular_off</i>
      </div>
    </div>
    <div class="mdl-card__title">
      <h2 class="mdl-card__title-text">근처의 프린터</h2>
    </div>
    <div v-for="client in this.nearClientlist" :key="client.clientId" v-bind:class="{'mdl-color--teal-100' : client.isWorking}" class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-shadow--4dp">
      <div class="mdl-card__title">
        <h2 class="mdl-card__title-text" v-text="client.clientName"></h2>
      </div>
      <div class="mdl-card__supporting-text">
        <div>IP 주소 : <span v-text="client.clientIp"></span></div>
        <div v-if="Math.floor(((new Date().getTime() - new Date(client.clientLastSeen).getTime())/1000)/60) < 60">
          <div>마지막으로 연결된 시간 : <span v-text="Math.floor(((new Date().getTime() - new Date(client.clientLastSeen).getTime())/1000)/60)"></span> 분전</div>
        </div>
        <div v-else-if="Math.floor(((new Date().getTime() - new Date(client.clientLastSeen).getTime())/1000)/60) < 1440">
          <div>마지막으로 연결된 시간 : <span v-text="Math.floor(((new Date().getTime() - new Date(client.clientLastSeen).getTime())/1000)/(60*60))"></span> 시간전</div>
        </div>
        <div v-else>
          <div>마지막으로 연결된 시간 : <span v-text="Math.floor(((new Date().getTime() - new Date(client.clientLastSeen).getTime())/1000)/(60*60*24))"></span> 일전</div>
        </div>
      </div>
      <div class="mdl-card__menu">
        <i v-on:click="onBookmark(client.clientId)" class="material-icons bookmark">favorite_border</i>
        <i v-if="Math.floor(((new Date().getTime() - new Date(client.clientLastSeen).getTime())/1000)/60) < 60" class="material-icons on">signal_cellular_4_bar</i>
        <i v-else class="material-icons off">signal_cellular_off</i>
      </div>
    </div>
  </div>
</template>


<script>
import axios from 'axios'

export default {
  data () {
    return {
      auth: false,
      username: '',
      bookmarkedClientlist: [],
      nearClientlist: [],
      coordinates: ''
    }
  },
  methods: {
    onUnBookmark (clientId) {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      axios.delete('http://xdkyu02.cafe24.com/deleteBookMark.do', {
        data: {
          'clientId': clientId
        },
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        location.reload()
      }).catch(errors => {

      })
    },
    onBookmark (clientId) {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      axios.post('http://xdkyu02.cafe24.com/insertBookMark.do', {
        'userId': this.username,
        'clientId': clientId
      },
      {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        location.reload()
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
        this.$router.push('/')
        location.reload()
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
        document.cookie = `accessToken=`
        this.auth = false
        this.$router.push('/')
        location.reload()
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
        this.auth = false
        this.$router.push('/')
        location.reload()
      })
    }
  },
  beforeMount () {
    this.setAuth()
    this.onGetGeolocation()
  }
}
</script>


<style>
.mdl-card__menu i{
  font-size: 48px;
  cursor: pointer;
}
.mdl-card__menu i.bookmark{
  color: red;
}
.mdl-card__menu i.on{
  color: rgb(49, 230, 25);
}
.mdl-card__menu i.off{
  color: rgb(230, 44, 31);
}
</style>