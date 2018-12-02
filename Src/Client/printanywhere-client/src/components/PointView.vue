<template>
  <div class="mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone">
    <div class="mdl-cell mdl-cell--6-col-desktop mdl-cell--4-col-tablet mdl-cell--2-col-phone div-center">
      <button v-on:click="onShowModal()" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-shadow--4dp">포인트 충전하기</button>
    </div>
    <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp mdl-cell mdl-cell--6-col-desktop mdl-cell--4-col-tablet mdl-cell--2-col-phone text-center div-center">
      <thead>
        <th>포인트</th>
        <th class="mdl-data-table__cell--non-numeric">내역</th>
        <th class="mdl-data-table__cell--non-numeric">날짜</th>
      </thead>
      <tbody>
        <tr v-for="point in this.pointlist" :key="point.payId">
          <td v-text="point.payCash"></td>
          <td v-if="(point.payType == 1)" class="mdl-data-table__cell--non-numeric">포인트 충전</td>
          <td v-text="(point.payDate.replace('.0',''))" class="mdl-data-table__cell--non-numeric"></td>
        </tr>
      </tbody>
    </table>
    <modal name="point-change-modal">
      <div class="mdl-card mdl-cell mdl-cell--8-col-desktop mdl-cell--6-col-tablet mdl-cell--3-col-phone mdl-shadow--4dp mdl-grid--no-spacing div-center">
        <div class="mdl-card__title mdl-card--expand">
          <h2 class="mdl-card__title-text">포인트 충전</h2>
        </div>
        <div class="mdl-card__supporting-text">
          <div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
              <input class="mdl-textfield__input" type="text" id="chargePoint" name="chargePoint" v-model="chargePoint" placeholder="포인트"/>
            </div>
          </div>
          <button v-on:click="onChargePoint()" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect">충전하기</button>
        </div>
      </div>
    </modal>
  </div>
</template>



<script>
import axios from 'axios'

export default {
  name: 'points',
  data () {
    return {
      auth: false,
      pointlist: []
    }
  },
  methods: {
    onShowModal () {
      this.$modal.show('point-change-modal')
    },
    onGetPointList () {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      axios.get('http://xdkyu02.cafe24.com/selectPayList.do?startPoint=0&endPoint=30', {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        this.pointlist = response.data.data.payList
      }).catch(errors => {

      })
    },
    onChargePoint () {
      if (this.chargePoint < 0) {
        return
      }
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      axios.post('http://xdkyu02.cafe24.com/insertPay.do', {
        'payCash': this.chargePoint
      },
      {
        headers: {
          'x-access-token': token
        }
      }).then(response => {
        this.$modal.hide('point-change-modal')
        this.onGetPointList()
        alert('포인트가 충전되었습니다.')
      }).catch(errors => {

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
      axios.get('http://xdkyu02.cafe24.com/authMe.do', {
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
    this.onGetPointList()
  }
}
</script>


<style>
.mdl-card__title{
  height: 200px;
  color: #fff;
  background: url('../assets/point.png') bottom right 15% no-repeat #46B6AC;
}
.text-center thead th {
  text-align: center !important;
}
.text-center tbody tr td{
  text-align: center !important;
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
</style>