<template>
  <div>
    <h1 style="font-size:20px">桌号</h1>
    <a class="info">红色为占用座位，绿色为空闲桌位，A开头为单人座，B开头为双人座，C开头为4人座，D开头为6人座</a>
    <el-divider></el-divider>
    <el-card  v-for="item in tables.filter(item => item.name !== 'DELIVERY')" style="background-color:rgb(233,233,233,0.1)"
              :key="item.id" :style="{'border-color':item.tableState === 'ready'?'rgba(9, 133, 79, 0.964)':'rgba(230, 70, 38, 0.964)'} " class="tables"
              bodyStyle="padding:10px" shadow="hover" @click.native="gotoOrderOrCash(item)">

      <div class="info">
        <div class="title">
          <h1>{{item.name}}</h1>
        </div>
      </div>
      <br>
    </el-card>

    <!--  <el-button-->
    <!--    plain-->
    <!--    @click="open4">-->
    <!--    左上角-->
    <!--  </el-button>-->
  </div>
</template>

<script>

import axios from 'axios'
import { Notification } from 'element-ui'
export default {
  name: 'Table',
  data () {
    return {
      orders: [],
      tables: [],
      orderDetail: [],
      doneTables: []
    }
  },
  created () {
    this.loadTables()
    this.loadOrders()
    axios.get('/ServiceGetOrder/findSubmit').then(res => {
      // this.doneTables = res.data.result
    })
  },
  mounted () {
    // eslint-disable-next-line no-unused-vars
    var a
    a = setInterval(() => {
      axios.get('/ServiceGetOrder/findSubmit').then(res => {
        if (res.data.result.toString() === this.doneTables.toString()) {
          console.log(1)
        } else {
          Notification.closeAll()
          this.doneTables = res.data.result
          this.doneTables.forEach(item => {
            this.$notify({
              duration: 0,
              title: '有新用户点单',
              message: '点单桌号为' + item.tables.name,
              position: 'bottom-left'
            })
          })
          console.log(2)
          console.log(res.data.result)
          console.log(this.doneTables)
        }
      })
    }, 3000)
  },
  methods: {
    open4 () {
      this.$notify({
        title: '自定义位置',
        message: '左上角弹出的消息',
        position: 'bottom-left'
      })
    },
    loadTables () {
      var _this = this
      this.$axios.get('/waiter/tables').then(resp => {
        if (resp && resp.data.code === 200) {
          _this.tables = resp.data.result
        }
      })
    },
    loadOrders () {
      var _this = this
      this.$axios.get('/ServiceGetOrder/findAll').then(resp => {
        if (resp && resp.data.code === 200) {
          _this.orders = resp.data.result
        }
      })
    },
    gotoOrderOrCash (item) {
      var _this = this
      this.$axios.get('/waiter/' + item.id + '/order/detail').then(resp => {
        if (resp && resp.data.code === 200) {
          _this.dishes = resp.data.result
          _this.$store.commit('loadDishCart', resp.data.result)
          this.$router.push({
            path: '/waiter/order',
            query: {
              key: item.name
              // 传输桌号自动填入点餐页面
            }
          })
        }
      })
    }
  }
}
</script>

<style>
.tables{
  width: 100px;
  margin-bottom: 10px;
  height: 150px;
  float: left;
  margin-right: 15px;
  border-radius: 20px;
  border-style: solid;
  border-width: 10px;
  box-shadow: 10px 10px 10px #b0b0b0f0;
}
.title{
  font-size: 40px;
  color:black;
  text-align: center;
}
.info{
  font-size: small;
  color:darkgray
}
</style>
