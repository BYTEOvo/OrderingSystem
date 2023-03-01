<template>
  <div>
    <el-carousel trigger="click" height="350px" style="margin-bottom: 60px;">
      <el-carousel-item>
        <img src="../../assets/1.png">
      </el-carousel-item>
      <el-carousel-item>
        <img src="../../assets/2.png">
      </el-carousel-item>
      <el-carousel-item>
        <img src="../../assets/4.png">
      </el-carousel-item>
      <el-carousel-item>
        <img src="../../assets/5.png">
      </el-carousel-item>
      <el-carousel-item>
        <img src="../../assets/6.png">
      </el-carousel-item>
      <el-carousel-item>
        <img src="../../assets/7.png">
      </el-carousel-item>
    </el-carousel>
    <el-button @click="grade" type="primary">评价菜品</el-button>
  </div>

</template>

<script>
import axios from 'axios'

export default {
  name: 'CustomerPay',
  data () {
    return {
      bulletins: [{access: '开学',
        content: '开学真的很早'}],
      button: false,
      specialDishes: [],
      table: []
    }
  },
  mounted: function () {
    this.loadSpecialDishes()
  },
  methods: {
    returnMenu () {
      this.$router.replace('/menu')
    },
    grade () {
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      // 若是订单已支付则进入评分界面，若是订单未支付，则留在此处，返回未支付信息。
      axios.get('/customer/' + username + '/paid').then(resp => {
        if (resp && resp.data.code === 200) {
          this.$router.replace('/customer/grade')
        } else {
          this.$message('您尚未支付或外卖订单未送达！')
        }
      })
    },

    loadSpecialDishes () {
      var _this = this
      this.$axios.get('/dishes/special').then(resp => {
        if (resp && resp.data.code === 200) {
          _this.specialDishes = resp.data.result
        }
      })
    }
  }
}
</script>

<style scoped>
/* 更改element-UI按钮样式 */
.el-button--primary {
  color: #fff;
  background-color: #002f8e;
  border-color: #002f8e;
}

.el-button--primary:hover {
  background-color: #002f8e;
  border-color: #002f8e;
}

.el-button--primary:focus {
  background-color: #002f8e;
  border-color: #002f8e;
}

.backButton:hover {
  background-color: rgba(0, 0, 71, 0.284);
  color: aliceblue
}

img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

</style>
