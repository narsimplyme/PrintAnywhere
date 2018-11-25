<template>
  <div class="mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone">
    <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone">
      <thead>
        <th>Point</th>
        <th class="mdl-data-table__cell--non-numeric">Date</th>
        <th class="mdl-data-table__cell--non-numeric">Description</th>
      </thead>
      <tbody>
        <tr v-for="point in this.pointlist" :key="point.pointId">
          <td v-text="point.pointChange"></td>
          <td v-text="point.pointChangedDate" class="mdl-data-table__cell--non-numeric"></td>
          <td v-text="point.pointDescription" class="mdl-data-table__cell--non-numeric"></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>



<script>
import axios from 'axios'
import points from '../temp/points'

export default {
  name: 'points',
  data () {
    return {
      auth: false,
      'pointlist': points.pointData
    }
  },
  methods: {
    setAuth () {
      var token = document.cookie.match('(^|;) ?' + 'accessToken' + '=([^;]*)(;|$)')[2]
      if (token === '') {
        this.auth = false
        this.$router.push('/')
        location.reload()
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
        document.cookie = `accessToken=`
        this.auth = false
        this.$router.push('/')
        location.reload()
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
        this.auth = false
        this.$router.push('/')
        location.reload()
      })
    }
  },
  beforeMount () {
    this.setAuth()
  }
}
</script>


<style>

</style>