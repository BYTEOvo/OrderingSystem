<template>
  <div>
    <div class="tablenum" >
      <el-card style="width:40%; background-color: rgb(233,233,233,0.5);border-radius:30px;float: left;font-family:cursive;font-size: large;" shadow="never">
        桌号：
        {{num}}
      </el-card>
      <i style="float: left;color:  #6EB5E7;width: 60px;font-size: 30px;margin-top: 20px;" class="el-icon-food"></i>
      <div  @click="gettable()">
        <el-card style="width:40%;border-radius:30px;color: white;background-color: #6EB5E7;" shadow="never">
          重新选择桌号
        </el-card> </div>
    </div>

    <!-- 传输桌号，以桌号为索引建立订单 -->
    <div class="Home_box" >
      <div class="Home_list" v-for="(item,index) in dishCart.filter(obj=>{return obj.count!==0})" :key="index"
      >
        <!-- 筛选出桌号为num的菜品 -->
        <div class="home_name" >{{item.dish.name}}</div>
        <div class="home_price">￥{{item.dish.price}}</div>
        <div class="home_button">
          <div class="home_button_one"  @click="sub(item)">-</div>
          <div>{{item.count}}</div>
          <div class="home_button_one" @click="add(item)">+</div>
        </div>
        <div class="home_zojia">￥{{item.dish.price*item.count}}</div>
        <div class="home_sc" @click="del(item)" style="color:#409EFF" >删除</div>
      </div>
    </div>
    <!-- 底部 -->
    <div class="footer">
      <span style="margin-right:20px;">总价:￥{{total}}</span>
      <el-button @click="sendOrder()">发送订单</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'WaiterOrder',
  computed: {
    dishCart () {
      // eslint-disable-next-line vue/no-side-effects-in-computed-properties
      this.cart = this.$store.state.dishCart
      return this.cart
    },
    //  这是保存到本地的数据
    total () {
      let totals = 0
      this.dishCart.forEach(obj => {
        totals += obj.count * obj.dish.price
      })
      return totals
    }
  },
  data () {
    return {
      num: '',
      cart: [],
      timer: null
    }
  },
  created () {
    this.num = this.$route.query.key
  },
  methods: {
    // // 减商品
    sub (item) {
      item.count--
      this.$store.commit('loadDishCart', this.cart)
    },
    // // 加商品
    add (item) {
      item.count++
      this.$store.commit('loadDishCart', this.cart)
    },
    // // 删除商品
    del (item) {
      item.count = 0
      this.$store.commit('loadDishCart', this.cart)
    },
    gettable () {
      this.$router.push('/table')
    },
    sendOrder () {
      this.$axios.post('/waiter/order/' + this.num + '/send', this.$store.state.dishCart).then(resp => {
        if (resp && resp.data.code === 200) {
          this.$alert('提交成功！', '提示', {
            confirmButtonText: '确定'
          })
          this.$router.push('/table')
        }
      })
    }
  }
}

</script>

<style>

.Home_box {
  width: 100%;
  height: 100%;

}
.Home_list {
  width: 100%;
  height: 80px;
  border: 1px solid #eeeeee;
  display: flex;
  margin-top: 10px;
}
.home_check {
  flex: 0.3;
}

.home_name {
  flex: 2;
  text-indent: 10px;
  text-align: left;
  line-height: 80px;
}
.home_price {
  flex: 0.5;
  text-indent: 10px;
  line-height: 80px;
  margin-right: 6%
}
.home_button {
  flex: 1;
  display: flex;
  align-items: center;
}
.home_button_one {
  width: 20px;
  height: 20px;
  border: 1px solid #eeeeee;
  text-align: center;
  line-height: 20px;
  margin: 0 5px;
  background-color:rgba(248, 252, 255, 0.5)

}
.home_zojia {
  flex: 1;
  line-height: 80px;
}
.home_sc {
  flex: 1;
  line-height: 80px;

}
.footer{

  margin-top:10px

}

</style>
