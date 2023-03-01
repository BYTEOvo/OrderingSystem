<template>
  <el-container class="DishBorder">
    <div>

      <el-row style="height: 580px;">
        <!--<search-bar></search-bar>-->
        <el-tooltip effect="light" placement="right" v-for="item in currentData" :key="item.id">
          <p slot="content" style="font-size: 14px;margin-bottom: 6px;color: rgb(0, 0, 99)">
            {{item.orderDetail.dish.name}}</p>
          <p slot="content" style="font-size: 13px;margin-bottom: 6px">
            <span>{{ "价格："+item.orderDetail.dish.price }}</span>
            <!--          <span>{{item.date}}</span> /-->
          </p>
          <p slot="content" style="width: 300px" class="description">{{item.orderDetail.dish.description}}</p>
          <el-card
            style="width: 240px;margin-bottom: 20px;height: 350px;float: left;margin-right: 15px;border-radius: 10px"
            class="animate__animated animate__flipInY" bodyStyle="padding:10px" shadow="hover">
            <div class="cover">
              <el-avatar :size="170" style="margin-top:4px;">
                <img :src="item.orderDetail.dish.imgurl" alt="封面">
              </el-avatar>
            </div>
            <br><br>
            <div class="info">
              <div class="title">
                <a href="" style="color: rgb(0, 50, 94)">{{item.orderDetail.dish.name}}</a>
              </div>
            </div>
            <br>
            <div>
              <el-button size="middle" icon="el-icon-remove-outline" circle @click="reduceCart(item.orderDetail.dish)">
              </el-button>
              <i>{{item.orderDetail.count}}</i>
              <el-button size="middle" icon="el-icon-circle-plus-outline" circle
                         @click="addToCart(item.orderDetail.dish)"></el-button>
              <!--        收藏-->
              <!--          已收藏  如果iscollect为true显示已收藏，绑定取消收藏功能-->
              <el-button v-if="item.collect" @click="del(item.orderDetail.dish)" circle style="margin-left: 0px">
                <i class="el-icon-star-on" style="background:rgba(43, 43, 43, 0); color: rgb(0, 65, 145)"></i>
              </el-button>
              <!--          未收藏  绑定点击收藏按钮-->
              <el-button v-else @click="add(item.orderDetail.dish)" icon="el-icon-star-off" circle
                         style="margin-left:0px">
              </el-button>
            </div>
            <!-- <span style="display:flex">
            <el-button size="middle" icon="el-icon-remove-outline" circle @click="reduceCart(item.dish)"
              style="margin-right:10px"></el-button>
            <i style="margin-top:15px">{{item.count}}</i>
            <el-button size="middle" icon="el-icon-circle-plus-outline" circle @click="addToCart(item.dish)"
              style="margin-left:10px">
            </el-button>
          </span> -->

            <div class="price" style="margin-top:15px;color: rgb(0, 50, 94)">{{ "价格："+item.orderDetail.dish.price +"元"}}</div>
          </el-card>
        </el-tooltip>
      </el-row>

      <el-row class="page">
        <el-pagination :currentPage.sync="currentPage" :page-size="8" :total="dishesByDishType.length"
                       @current-change="currentChange()">
        </el-pagination>
      </el-row>

    </div>
  </el-container>
</template>
<script>
import 'animate.css'
import axios from 'axios'

export default {
  name: 'Dishes',

  created () {
    this.getState()
  },

  data () {
    return {
      currentData: [],
      dishesByDishType: [],
      dishes: {
        orderDetail: {},
        collect: ''
      },
      cart: {
        count: 0, // 商品数量
        total: 0 // 商品总价
      },
      currentPage: 1,
      CollectDishes: [],
      isCollect: false// 默认是false则是可收藏，true的话就是已收藏
    }
  },
  computed: {
    // eslint-disable-next-line vue/no-dupe-keys
  },
  mounted: function () {
    this.loadDishes()
  },

  methods: {
    currentChange () {
      this.currentData = this.dishesByDishType.slice((this.currentPage - 1) * 8, this.currentPage * 8)
    },
    loadDishes () {
      var _this = this
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      this.$axios.get('/dishes/' + username + '/dishCart').then(resp => {
        if (resp && resp.status === 200) {
          _this.dishes = resp.data.result
          _this.dishesByDishType = resp.data.result
          _this.currentData = _this.dishesByDishType.slice((this.currentPage - 1) * 8, this.currentPage * 8)
        }
      })
    },
    addToCart: function (dish) {
      this.cart.count++
      this.cart.total += dish.price
      this.dishes.forEach(item => {
        if (item.orderDetail.dish.name === dish.name) { // 表示list里存在当前添加商品
          item.orderDetail.count++
        }
      })
    },
    // 减去商品
    reduceCart: function (dish) {
      this.cart.count--
      this.cart.total -= dish.price
      this.dishes.forEach(item => {
        if (item.orderDetail.dish.name === dish.name) { // 表示list里存在当前添加商品
          if (item.orderDetail.count > 0) { // 为0时删除商品
            item.orderDetail.count--
          }
        }
      })
    },
    add (dish) {
      var dishId = dish.id
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      axios.get('/dish/' + username + '/collect/add/' + dishId).then(res => {
        if (res.data.code === 200) {
          this.dishes.forEach(item => {
            if (item.orderDetail.dish.name === dish.name) { // 表示list里存在当前添加商品
              item.collect = true
            }
          })
          this.$message({
            message: '收藏成功',
            type: 'success'
          })
        } else {
          this.$message({
            message: '收藏失败',
            type: 'error'
          })
        }
      })
    },
    del (dish) {
      var dishId = dish.id
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      axios.get('/dish/' + username + '/collect/delete/' + dishId).then(res => {
        if (res.data.code === 200) {
          this.dishes.forEach(item => {
            if (item.orderDetail.dish.name === dish.name) { // 表示list里存在当前添加商品
              item.collect = false
            }
          })
          this.$message({
            message: '取消收藏',
            type: 'success'
          })
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          })
        }
      })
    },

    // 清空购物车
    // clearCart: function () {
    //   this.cart.count = 0
    //   this.cart.total = 0
    //   this.dishes.forEach(item => {
    //     item.count = 0
    //   })
    //   this.isDisable = true
    // },

    getState () {
      var _this = this
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      this.$axios.get('/dish/' + username + '/collect').then(resp => {
        if (resp && resp.status === 200) {
          _this.isCollect = true
        }
      })
    }

  }
}
</script>

<style scoped>
.cover {
  width: 220px;
  height: 175px;
  margin-bottom: 7px;
  cursor: pointer;
}

img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.DishBorder {
  border: 2px solid rgba(0, 0, 0, 0);
}

img {
  width: 250px;
  height: 172px;
  /*margin: 0 auto;*/
}

.title {
  font-size: 14px;
  text-align: left;
}

.price {
  color: #333;
  width: 102px;
  font-size: 13px;
  margin-bottom: 6px;
  text-align: left;
}

.description {
  display: block;
  line-height: 17px;
}

a {
  text-decoration: none;
}

a:link,
a:visited,
a:focus {
  color: #3377aa;
}

.el-icon-circle-plus-outline {
  cursor: pointer;
  float: right;
}

.el-card:hover {
  background-color: #9b9b9bcb;
}

.page {
  position: fixed;
  bottom: 60px;
  left: 600px;
}
</style>
