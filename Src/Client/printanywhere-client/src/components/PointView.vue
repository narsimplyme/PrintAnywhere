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

</style>