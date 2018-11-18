<template>
  <div class="mdl-layout__content mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone" v-if="auth">
    <h1>Bookmark</h1>
    <div v-for="client in this.bookmarkedClientlist" :key="client.id" v-bind:class="{'mdl-color--teal-100' : client.isWorking}" class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-shadow--4dp">
      <div class="mdl-card__title">
        <h2 class=mdl-card__title-text v-text="client.clientName"></h2>
      </div>
      <div class="mdl-card__supporting-text">
        <div>Latitude : <span v-text="client.clientLatitude"></span></div>
        <div>Longitude : <span v-text="client.clientLongitude"></span></div>
      </div>
      <div class="mdl-card__actions mdl-card--border">
        <a @click.native="onUnBookmark(client.id)" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent ">UnBookmark</a>
      </div>
    </div>
    <h1>Near</h1>
    <div v-for="client in this.nearClientlist" :key="client.id" v-bind:class="{'mdl-color--teal-100' : client.isWorking}" class="mdl-card mdl-cell mdl-cell--12-col-desktop mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-shadow--4dp">
      <div class="mdl-card__title">
        <h2 class=mdl-card__title-text v-text="client.clientName"></h2>
      </div>
      <div class="mdl-card__supporting-text">
        <div>Latitude : <span v-text="client.clientLatitude"></span></div>
        <div>Longitude : <span v-text="client.clientLongitude"></span></div>
      </div>
      <div class="mdl-card__actions mdl-card--border">
        <a @click.native="onBookmark(client.id)" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored ">Bookmark</a>
      </div>
    </div>
  </div>
</template>


<script>
import clients from '../temp/clients'

export default {
  data () {
    return {
      auth: false,
      'bookmarkedClientlist': clients.bookmarkedClients,
      'nearClientlist': clients.nearClients
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
    },
    onBookmark (clientId) {
      // TODO api/clients/{clientId} [PUT]
    },
    onUnBookmark (clientId) {
      // TODO api/clients/{clientId} [PUT]
    }
  },
  beforeMount () {
    this.setAuth()
  }
}
</script>


<style>

</style>