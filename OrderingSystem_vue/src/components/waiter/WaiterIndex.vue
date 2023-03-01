<template>
  <el-container class="background">
    <!-- background-color:antiquewhite -->
    <el-aside width="40%">
      <Aside ref="aside"></Aside>
    </el-aside>
    <el-container>
      <el-header>
        <TopMenu @indexSelect="listByDishType" ref="TopMenu" id="TopMenu"></TopMenu>
      </el-header>
      <el-main style="height: calc(100vh - 8.5vh); ">

        <WDish ref="DishCart"></WDish>

      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import WDish from './WDish.vue'
import TopMenu from './TopMenu.vue'
import Aside from './Aside.vue'

export default {
  name: 'WaiterIndex',
  components: {
    WDish,
    TopMenu,
    Aside

  },
  data () {
    return {
      dishTypeList: [],
      dishTypeId: '',
      table: false,
      dialog: false,
      loading: false,
      cart: {
        count: 0, // 商品数量
        total: 0 // 商品总价
      },
      dishes: [],
      dishesByDishType: [],
      formLabelWidth: '80px',
      timer: null
    }
  },
  created: function () {
    this.getDishTypeList()
  },
  methods: {
    handleSelect (key, keyPath) {
      this.dishTypeId = key
      this.$emit('indexSelect')
    },
    getDishTypeList () {
      this.$axios.get('/dishType').then(resp => {
        if (resp && resp.status === 200) {
          this.dishTypeList = resp.data
        }
      })
    },
    listByDishType () {
      this.$refs['DishCart'].dishesByDishType = []
      var dishTypeId = this.$refs['TopMenu'].dishTypeId
      if (dishTypeId !== '1') {
        this.$refs['DishCart'].dishes.forEach(item => {
          if (item.dish.dishType.id.toString() === dishTypeId) {
            this.$refs['DishCart'].dishesByDishType.push(item)
          }
        })
      } else {
        this.$refs['DishCart'].dishesByDishType = this.$refs['DishCart'].dishes
      }
    }
  }
}
</script>

<style>
html,body,#app{
  margin: 0;
  padding: 0;
  height:100%;
  background-image:url(../../assets/pp.jpg);
  /* background-position: right bottom,left top; */
  background-size: cover;

}

</style>
