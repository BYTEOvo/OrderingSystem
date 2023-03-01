<template>
  <el-container>
    <el-aside style="width: 200px;margin-top: 20px; margin-left:10px">
      <el-row>
        <SideMenu @indexSelect="listByDishType" ref="SideMenu"></SideMenu>
      </el-row>
    </el-aside>

    <el-main>
      <Dishes ref="DishCart"></Dishes>
    </el-main>

    <el-aside style="width: 200px;margin-top: 20px">

      <el-row>

        <!-- <div id="music" @click="music" :class="{'cilcle' : iscilcle}">
          <img src="src\assets\retouch_2022082422031030.jpg" alt="">
          <audio id="audio" loop ref="audio" autoplay :src="musicUrl"></audio>
        </div> -->

        <el-button @click="openTable" class="checkButton" type="primary">
          <div class="text">
            <i class="el-icon-shopping-cart-full"></i>
            <span><br> <br>查<br> <br>看<br> <br>购<br> <br>物<br> <br>车<br> <br></span>
          </div>
        </el-button>

        <el-drawer class="drawer" title="购物车" :visible.sync="table" direction="rtl" :append-to-body="true" size="30%">
          <div class="table">

            <el-table :data="dishes.filter((item) => item.orderDetail.count > 0)" show-summary :summary-method="getSum">
              <el-table-column property="orderDetail.dish.name" label="菜品名" width="150"></el-table-column>
              <el-table-column property="orderDetail.count" label="数量" width="100"></el-table-column>
              <!--            <el-table-column  property="dish.price" label="价格" width="150"></el-table-column>-->
              <el-table-column label="价格"><template
                #default="scope"><span>{{scope.row.orderDetail.dish.price*scope.row.orderDetail.count}}</span></template></el-table-column>
              <el-table-column label="操作" width="200">
                <template v-slot:default="scope">
                  <el-button size="middle" icon="el-icon-circle-plus-outline" circle @click="addToCart(scope.row.orderDetail.dish)">
                  </el-button>
                  <el-button size="middle" icon="el-icon-remove-outline" circle @click="reduceCart(scope.row.orderDetail.dish)">
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-container>
              <el-footer>
                <el-button @click="clearCart" class="clearButton">清空购物车</el-button>
                <el-button type="primary" @click="handleClose" :loading="loading">
                  <div class="text">
                    {{ loading ? '提交中 ...' : '提交订单' }}
                  </div>
                </el-button>
              </el-footer>
            </el-container>
          </div>
          <el-dialog
            append-to-body
            title="提示"
            center
            :visible.sync="delivery"
            width="30%"
            :before-close="handleClose2">
            <el-form class="login-container" label-position="left"
                     label-width="0px" v-loading="loading">
              <h3 class="login_title">收货地址</h3>
              <el-form-item>
                <el-input type="text" v-model="address"
                          auto-complete="off" placeholder="收货地址"></el-input>
              </el-form-item>
              <el-form-item>
                <el-input type="text" v-model="phoneNumber"
                          auto-complete="off" placeholder="请填写收货人手机号"></el-input>
              </el-form-item>
              <el-form-item style="width: 100%">
                <!--      下面的按钮代表发送菜品信息与收货地址信息          -->
                <el-button type="primary" style="width: 100%;background: #ffffff;border: none" @click="takeOutOrder">确认信息</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>
        </el-drawer>
      </el-row>
      <!--      <switch></switch>-->
    </el-aside>
  </el-container>
</template>

<script>

import SideMenu from './SideMenu'
// eslint-disable-next-line import/no-duplicates
import Dishes from './Dishes'
// eslint-disable-next-line import/no-duplicates
import { ref } from 'vue'
import axios from 'axios'
// eslint-disable-next-line no-unused-vars

export default {
  name: 'MenuIndex',
  components: {SideMenu, Dishes},
  data () {
    return {
      table: false,
      dialog: false,
      loading: false,
      cart: {
        count: 0, // 商品数量
        total: 0 // 商品总价
      },
      order: [],
      dishes: [],
      dishesByDishType: [],
      formLabelWidth: '80px',
      value: ref(''),
      options: [],
      isConfirmdesk: false,
      button: false,
      delivery: false,
      address: '',
      phoneNumber: ''
    }
  },
  methods: {
    AliPay (row) {
      axios.post('/create/', row).then(resp => {
        if (resp && resp.status === 200) {
          console.log(row)
          this.userInfo = resp.data.result
          window.localStorage.removeItem('callbackHTML')
          window.localStorage.setItem('callbackHTML', resp.data.result)
          // 读取本地保存的html数据，使用新窗口打开
          var newWin = window.open('')
          newWin.document.write(localStorage.getItem('callbackHTML'))
          // 关闭输出流
          newWin.document.close()
        }
      })
    },
    takeOutOrder () {
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      this.$axios.post('/customer/order/' + username + '/delivery/address', {
        address: this.address,
        phoneNumber: this.phoneNumber
      }).then(resp => {
        this.delivery = false
        if (resp && resp.data.code === 200) {
          this.$alert('地址信息提交成功！', '提示', {
            confirmButtonText: '确定'
          })
        }
      }).then(() => {
        this.$confirm('确定要提交表单吗？')
          .then(_ => {
            this.loading = true
            var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
            this.$axios.post('/' + username + '/order', this.dishes
            ).then(resp => {
              if (resp && resp.data.code === 200) {
                this.$alert('下单成功', '提示', {
                  confirmButtonText: '确定'
                })
                this.loading = false
                this.clearCart()
                this.table = false
                this.AliPay(this.order)
                this.$router.replace('/customer/pay')
              } else {
                this.loading = false
                this.table = false
                this.$alert(resp.data.message, '提示', {
                  confirmButtonText: '确定'
                })
              }
            })
          })
          .catch(_ => {})
      })
    },
    handleClose2 (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    },
    getSum (param) {
      const sums = []
      sums[0] = '合计'
      sums[1] = this.$refs['DishCart'].cart.count
      sums[2] = this.$refs['DishCart'].cart.total
      return sums
    },
    confrimDesk () {
      this.$confirm('确定选择此桌？').then(_ => {
        this.isConfirmdesk = true
        this.button = true
      })
    },
    listByDishType () {
      this.$refs['DishCart'].dishesByDishType = []
      var dishTypeId = this.$refs['SideMenu'].dishTypeId
      if (dishTypeId !== '1') {
        this.$refs['DishCart'].dishes.forEach(item => {
          if (item.orderDetail.dish.dishType.id.toString() === dishTypeId) {
            this.$refs['DishCart'].dishesByDishType.push(item)
          }
        })
        this.$refs['DishCart'].currentData = this.$refs['DishCart'].dishesByDishType.slice((this.$refs['DishCart'].currentPage - 1) * 8, this.$refs['DishCart'].currentPage * 8)
      } else {
        this.$refs['DishCart'].dishesByDishType = this.$refs['DishCart'].dishes
        this.$refs['DishCart'].currentData = this.$refs['DishCart'].dishesByDishType.slice((this.$refs['DishCart'].currentPage - 1) * 8, this.$refs['DishCart'].currentPage * 8)
      }
    },
    openTable () {
      this.cart = this.$refs['DishCart'].cart
      this.dishes = this.$refs['DishCart'].dishes
      this.dishesByDishType = this.$refs['DishCart'].dishesByDishType
      this.table = true
    },
    handleClose () {
      if (this.loading) {
        return
      }
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      this.$axios.get('/customer/' + username + '/order/now').then(resp => {
        if (resp && resp.data.code === 200) {
          if (resp.data.result.tables.name === 'DELIVERY') {
            this.delivery = true
            this.order = resp.data.result
            console.log(1)
          } else {
            this.$confirm('确定要提交表单吗？')
              .then(_ => {
                this.loading = true
                var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
                this.$axios.post('/' + username + '/order', this.dishes
                ).then(resp => {
                  if (resp && resp.data.code === 200) {
                    this.$alert('下单成功', '提示', {
                      confirmButtonText: '确定'
                    })
                    this.loading = false
                    this.clearCart()
                    this.table = false
                    this.$router.replace('/customer/pay')
                  } else {
                    this.loading = false
                    this.table = false
                    this.$alert(resp.data.message, '提示', {
                      confirmButtonText: '确定'
                    })
                  }
                })
              })
              .catch(_ => {})
          }
        }
      })
    },
    addToCart: function (dish) {
      this.$refs['DishCart'].cart.count++
      this.$refs['DishCart'].cart.total += dish.price
      this.$refs['DishCart'].dishes.forEach(item => {
        if (item.orderDetail.dish.name === dish.name) { // 表示list里存在当前添加商品
          item.orderDetail.count++
        }
      })
    },
    // 减去商品
    reduceCart: function (dish) {
      this.$refs['DishCart'].cart.count--
      this.$refs['DishCart'].cart.total -= dish.price
      this.$refs['DishCart'].dishes.forEach(item => {
        if (item.orderDetail.dish.name === dish.name) {
          if (item.orderDetail.count > 0) { // 为0时删除商品
            item.orderDetail.count--
          }
        }
      })
    },
    clearCart: function () {
      this.$refs['DishCart'].cart.count = 0
      this.$refs['DishCart'].cart.total = 0
      this.$refs['DishCart'].dishes.forEach(item => {
        item.orderDetail.count = 0
      })
    }
  }
}
</script>

<style scoped>
/* #music {
  width: 100px;
  height: 100px;
}

.cilcle {
  animation: myfirst 5s infinite linear;
}

@keyframes myfirst {
  from {
    transform: rotate(0deg);
  }

  to {
    transform: rotate(360deg);
  }
}

#music>img {
  width: 100%;
  height: 100%;
} */

.drawer>>>.el-drawer.rtl {
  background-image: url(https://640850.freep.cn/640850/2.jpg);
  background-size: contain;
}

.table>>>.el-table,
.el-table__expanded-cell {
  background-color: transparent;
}

.table>>>.el-table tr {
  background-color: transparent !important;
}

.table>>>.el-table th {
  background-color: transparent !important;
}

.table>>>.el-table--enable-row-transition .el-table__body td,
.el-table .cell {
  background-color: transparent;
}

.checkButton {
  position: fixed;
  margin-right: 0%;
  right: 18px;
  width: 190px;
  border-radius: 12px;
  height: 700px;
  width: 50px;
  float: right;
}

.clearButton:hover {
  background-color: rgba(0, 0, 71, 0.284);
  color: aliceblue
}

.text {
  color: white;
}

/* 更改element-UI按钮样式 */
.el-button--primary {
  color: #002f8e5e;
  background-color: #002f8e;
  border-color: #002f8e;
}

.el-button--primary:hover {
  background-color: #002f8e91;
  border-color: #ffffff;
}

.el-button--primary:focus {
  background-color: rgba(243, 244, 245, 0.65);
  border-color: #171616;
}
</style>
