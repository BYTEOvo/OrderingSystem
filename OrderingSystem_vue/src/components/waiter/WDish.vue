<template>
  <div >
    <el-row >
      <!--<search-bar><arch-bar>-->
      <div   v-for="item in dishesByDishType"
             :key="item.id" class="Demo">
        <!-- <p slot="content" style="font-size: 14px;margin-bottom: 6px;">{{item.dish.name}}</p>
        <p slot="content" style="font-size: 13px;margin-bottom: 6px">
          <span>{{ "价格："+item.dish.price }}</span>
          <span>{{item.date}}</span> /-->
        <!-- </p>
        <p slot="content" style="width: 300px" class="description">{{item.dish.description}}</p>
   -->
        <div >
          <div class="Before" :class="isTop ? 'contain-Before':''"
          >
            <div class="card2">
              <div class="cover"  @click="handleBefore()">
                <el-avatar :size="130" :src="item.dish.imgurl" alt="封面"></el-avatar>
              </div>
              <div class="title" >
                <a >{{item.dish.name}}</a>
              </div>
              <div  >
                <span class="price">{{ "价格："+item.dish.price }}</span>
                <el-button :disabled="getByOrder()" class="buttoncss" size="small" circle  @click="addToCart(item.dish)">+</el-button>

              </div>

            </div>

          </div >
          <div class="After" :class="isTop ? 'contain-After':''" @click="handleAfter" >
            <p slot="content" style="font-size: 14px;margin-bottom: 6px;">{{item.dish.name}}</p>
            <p slot="content" style="font-size: 13px;margin-bottom: 6px;">
              <span>{{ "价格："+item.dish.price }}</span>
              <!--          <span>{{item.date}}</span> /-->
            </p>
            <p slot="content"  class="description">{{item.dish.description}}</p>
          </div>
        </div>
      </div>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'Dishes',
  data () {
    return {
      isTop: false,
      dishesByDishType: [],
      dishes: [],
      cartItem: {
        count: 0, // 商品数量
        total: 0 // 商品总价
      },
      tableId: ''
    }
  },
  computed: {
    // eslint-disable-next-line vue-dupe-keys
  },
  mounted: function () {
    this.loadDishes()
  },
  methods: {
    handleBefore () {
      if (!this.isTop) {
        this.isTop = true
      }
    },
    handleAfter () {
      if (this.isTop) {
        this.isTop = false
      }
    },
    addToCart (dish) {
      var cart = []
      cart = this.$store.state.dishCart
      cart.forEach(item => {
        if (item.dish.name === dish.name) { // 表示list里存在当前添加商品
          item.count++
        }
      })
      this.$store.commit('loadDishCart', cart)
    },
    // 触发adddish函数
    getByOrder () {
      let path = this.$route.path
      if (path === '/waiter/order') {
        return false
      } else {
        return true
      }
    },
    loadDishes () {
      var _this = this
      this.$axios.get('/waiter/dishes/dishCart').then(resp => {
        if (resp && resp.status === 200) {
          _this.dishes = resp.data.result
          _this.dishesByDishType = resp.data.result
        }
      })
    }
    // addToCart (dish) {
    //   this.dishes.forEach(item => {
    //     if (item.dish.name === dish.name) { // 表示list里存在当前添加商品
    //       item.count++
    //     }
    //   })
    //
    //   this.$store.dispatch('Add_Dish', dish)
    // }, // 触发adddish函数

    // 减去商品
  }
}
</script>

<style scoped>

  .cover {
    width: 140;
    height: 142px;
    margin-bottom: 7px;
    overflow: hidden;
    cursor: pointer;
  }
  .card2{
    margin-top: 20%;
  }
  img {
    width: 115px;
    height: 152px;
    /*margin: 0 auto;*/

  }
  .dishes{
    width: 160px;
    /* border-color: orange; */

    /* background-color:rgba(255, 254, 254, 0.8) ;   */
    opacity: 0;
    margin-bottom: 20px;
    height: 270px;
    border-radius: 20px;
    float: left;
    margin-right: 15px
  }

  .title {
    font-size: 20px;
    text-align: center;
    color:rgb(21, 80, 114);
    margin-bottom: 5px;
    font-family:cursive;
  }

  .price {
    color: rgb(33, 29, 29);

    font-size: 13px;

    text-align: left;
  }

  .description {
    display: block;
    line-height: 17px;
    color:#3377aa;
    font-size: large;
    font-family: cursive;
  }

  a {
    text-decoration: none;

  }

  a:link, a:visited, a:focus {
    color: #3377aa;
  }

  .el-icon-circle-plus-outline {
    cursor: pointer;
    float: right;
  }

  .Demo{
    width: 205px;
    height: 300px;
    margin-top: 10px;
    /* margin-left: 500px; */
    /* position: relative; */
    perspective: 800px;
    box-sizing: border-box;
    float: left;

  }
  .Before{
    width: 160px;
    /* border-color: orange; */
    padding: 10px;
    background-color: rgba(255, 255, 255, 0.8);
    margin-bottom: 20px;
    height: 270px;
    border-radius: 20px;
    float: left;
    margin-right: 15px;
    position: absolute;
    backface-visibility: hidden;
    transition: 1.5s;

  }
  .After{
    width: 160px;
    /* border-color: orange; */

    background-color:rgba(255, 254, 254, 0.8) ;
    /* opacity: 0 inherit; */
    margin-bottom: 20px;
    height: 270px;
    border-radius: 20px;
    float: left;
    margin-right: 15px;
    position: absolute;
    transform: rotateY(-180deg);
    backface-visibility: hidden;
    transition: 1.5s;
    padding: 10px;
  }
  .Demo .contain-Before{
    transform: rotateY(180deg);

  }
  .Demo .contain-After{
    transform: rotateY(0deg);

  }

  </style>
