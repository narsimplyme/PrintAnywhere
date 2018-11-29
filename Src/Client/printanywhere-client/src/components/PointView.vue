<template>
  <div class="mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone">
    <button v-on:click="onShowModal()" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-shadow--4dp">Charge Point</button>
    <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone">
      <thead>
        <th>Point</th>
        <th class="mdl-data-table__cell--non-numeric">Date</th>
        <th class="mdl-data-table__cell--non-numeric">Description</th>
      </thead>
      <tbody>
        <tr v-for="point in this.pointlist" :key="point.payId">
          <td v-text="point.payCash"></td>
          <td v-text="point.payDate" class="mdl-data-table__cell--non-numeric"></td>
          <td v-if="(point.payType == 1)" class="mdl-data-table__cell--non-numeric">포인트 충전</td>
        </tr>
      </tbody>
    </table>
    <modal name="point-change-modal">
      <div class="mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone center-div">
        <div class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-shadow--4dp mdl-grid--no-spacing">
          <div class="mdl-card__title mdl-card--expand">
            <h2 class="mdl-card__title-text">Charge Point</h2>
          </div>
          <div class="mdl-card__supporting-text">
            <div>
              <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" id="chargePoint" name="chargePoint" v-model="chargePoint" placeholder="포인트"/>
              </div>
            </div>
            <button v-on:click="onChargePoint()" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect">Charge</button>
          </div>
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
.center-div{
  width: calc(100%) !important;
  margin: 0px !important;
}
.v--modal-box{
  height: 350px !important;
  background-color: transparent !important;
  box-shadow: 0 0 0 0 !important;
}
</style>