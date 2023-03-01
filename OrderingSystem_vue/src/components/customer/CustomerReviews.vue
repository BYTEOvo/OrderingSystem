<template>
  <div class="totalback">
    <el-carousel trigger="click" height="350px" style="margin-bottom: 60px;margin-top: 60px;">
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

    <el-tabs v-model="activeName" @tab-click="handleClick" :stretch="false"
             style=" min-height: 100%; width: 100%;caret-color:white;">

      <el-tab-pane name='5'>
        <span slot="label"><i class="el-icon-star-off"></i> 五星评价</span>
      </el-tab-pane >
      <block v-for="item in userGrades.filter(obj=>{return obj.urate===5} )"  :key="item.urate">
        <review :item="item" ></review>
      </block>
      <el-tab-pane name='4'>
        <span slot="label"><i class="el-icon-star-off"></i> 四星评价</span>
      </el-tab-pane>
      <block v-for="item in userGrades.filter(obj=>{return obj.urate===4} )"  :key="item.urate">
        <review :item="item" ></review>
      </block>
      <el-tab-pane name='3'>
        <span slot="label"><i class="el-icon-star-off"></i> 三星评价</span>
      </el-tab-pane>
      <block v-for="item in userGrades.filter(obj=>{return obj.urate===3} )"  :key="item.urate">
        <review :item="item" ></review>
      </block>
      <el-tab-pane name='2'>
        <span slot="label"><i class="el-icon-star-off"></i> 二星评价</span>
        <block v-for="item in userGrades.filter(obj=>{return obj.urate===2} )"  :key="item.urate">
          <review :item="item" ></review>
        </block>
      </el-tab-pane>
      <el-tab-pane name='1'>
        <span slot="label"><i class="el-icon-star-off"></i> 一星评价</span>
        <block v-for="item in userGrades.filter(obj=>{return obj.urate===1} )"  :key="item.urate">
          <review :item="item" ></review>
        </block>

      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import review from '../customer/review'
export default {
  name: 'CustomerReviews',
  components: {review},
  data () {
    return {
      userGrades: [],
      activeName: 1
    }
  },
  created () {
    this.loadUserGrades()
  },
  mounted: function () {
    this.loadUserGrades()
  },
  methods: {
    // 需要加载用户评分信息
    handleClick () {
      var _this = this
      _this.userGrades = []
      console.log(this.activeName)
      if (this.activeName === '1') {
        this.$axios.get('/comment/findAllOneComment').then(resp => {
          if (resp && resp.status === 200) {
            _this.userGrades = resp.data.result
          }
        })
      } else if (this.activeName === 'all') { // 后端没有做所有的状态分类，前端处理就是直接删掉然后显示所有数据。
        this.loadUserGrades()
      } else if (this.activeName === '2') {
        this.$axios.get('/comment/findAllTwoComment').then(resp => {
          if (resp && resp.status === 200) {
            _this.userGrades = resp.data.result
          }
        })
      } else if (this.activeName === '3') {
        this.$axios.get('/comment/findAllThreeComment').then(resp => {
          if (resp && resp.status === 200) {
            _this.userGrades = resp.data.result
          }
        })
      } else if (this.activeName === '4') {
        this.$axios.get('/comment/findAllFourComment').then(resp => {
          if (resp && resp.status === 200) {
            _this.userGrades = resp.data.result
          }
        })
      } else if (this.activeName === '5') {
        this.$axios.get('/comment/findAllFiveComment').then(resp => {
          if (resp && resp.status === 200) {
            _this.userGrades = resp.data.result
            console.log(this.userGrades)
          }
        })
      }
      console.log(this.userGrades)
    },
    loadUserGrades () {
      var _this = this
      this.$axios.get('/comment/findAll').then(resp => {
        if (resp && resp.status === 200) {
          _this.userGrades = resp.data.result
          console.log(this.userGrades)
          // var u = JSON.stringify(this.userGrades[0].user)
          var u = this.userGrades[0].user
          console.log(u)
          console.log(typeof (u))
          console.log(u.username)
        }
      })
    }
  }
}
</script>

<style scoped>
.totalback {
  background: rgb(255, 255, 255);

}

.back {
  margin-left: 0%;
  width: auto;
  height: 450px;
  background: rgba(255 ,255 ,255);
  margin-top: 100px;
  margin-bottom: 100px;
}

p {
  color: cornflowerblue;
  font-size: 15px;
  text-align: left;
  font-style: italic;
  text-decoration: underline #222222;
}

.rateContainer {
  display: grid;
  grid-template-columns: auto auto;
}
.comments{
  border: 2px solid #000000;
  font-size: 20px;
}

img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.item {
  position: relative;
  width: auto;
  font-size: large;
  margin-top: 10px;
  display: grid;
  grid-template-columns: auto auto;
}

.box-card {
  width: 400px;
}

.itemm {
  width: auto;
  margin-left: 40px;
  font-size: 15px;
}
</style>
