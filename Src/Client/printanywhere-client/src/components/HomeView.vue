<template>
  <div class="main">
    <img src="../assets/logo.png" alt="프린트애니웨어">
    <h1>{{ msg }}</h1>
  </div>
</template>

<script>
export default {
  name: 'main',
  data () {
    return {
      msg: '프린트애니웨어에 오신걸 환영합니다.'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #35495E;
}
main {
  text-align: center;
  margin-top: 40px;
}

</style>
