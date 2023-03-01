<template>

   <el-image class="image" :src="url"></el-image>

</template>

<script>

export default {
  name: 'DashBoard',
  data () {
    return {
      url: 'https://640837.freep.cn/640837/retouch_2022082215541999.jpg'
    }
  }
}
</script>
<style scoped>
.image{
 width: 90%;
  height: 96%;
object-fit: cover;
}
</style>
