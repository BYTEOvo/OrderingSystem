<template>
  <div class="back">
    <el-row style="margin-top:15px;margin-left:15px;margin-bottom:50px; float:left;font-size: xx-large;color: #001571;
      font-family:'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;text-shadow: 5px 5px 5px #969696;
      width:100%">
      <span style="float:left;">--请评价此次用餐--</span>
    </el-row>

    <!-- <div class="rate"> -->
    <br>
    <el-row :gutter="20">
      <el-col :span="6" :offset="0"> <span class="demonstration">对这次菜品评分</span>
        <el-rate v-model="rates.urate" :colors="colors" />
      </el-col>
      <el-col :span="6" :offset="0">
        <div class="grid-content bg-purple"></div>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="6" :offset="0">
        <span class="demonstration">对这次位置评分</span>
        <el-rate v-model="rates.r1" :colors="colors" />
      </el-col>
      <el-col :span="6" :offset="0">
        <span class="demonstration">对这次服务评分</span>
        <el-rate v-model="rates.r2" :colors="colors" />
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="6" :offset="0">
        <span class="demonstration">对这次候时评分</span>
        <el-rate v-model="rates.r3" :colors="colors" />
      </el-col>
      <el-col :span="6" :offset="0">
        <span class="demonstration">对这次环境评分</span>
        <el-rate v-model="rates.r4" :colors="colors" />
      </el-col>
    </el-row>

    <!-- <el-row class="dRate">
        <span class="demonstration">对这次菜品评分</span>
        <el-rate v-model="rates.urate" :colors="colors" />
      </el-row>
      <el-row class="dRate">
        <span class="demonstration">对这次位置评分</span>
        <el-rate v-model="rates.r1" :colors="colors" />

        <span class="demonstration">对这次服务评分</span>
        <el-rate v-model="rates.r2" :colors="colors" />
      </el-row>
      <div class="dRate">
        <span class="demonstration">对这次候时评分</span>
        <el-rate v-model="rates.r3" :colors="colors" />
      </div>
      <div class="dRate">
        <span class="demonstration">对这次环境评分</span>
        <el-rate v-model="rates.r4" :colors="colors" />
      </div> -->
    <!-- </div> -->
    <div class="right">
        <el-input placeholder="请写出您的评价" :show-word-limit="true" maxlength="50" class="comment" v-model="rates.comments"
          type="textarea" :autosize="{ minRows: 4, maxRows: 4}" />
      <br>
      <el-button @click="grade()" type="primary" style="color:white;margin-top: 20px;">提交评价</el-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { ref } from 'vue'

export default {
  name: 'CustomerGrade',
  data () {
    return {
      dishes: [],
      order: [],
      colors: ref(['#99A9BF', '#415bcc', '#00146c']), // same as { 2: '#99A9BF', 4: { value: '#F7BA2A', excluded: true }, 5: '#FF9900' }
      rates: {
        urate: 5,
        r1: 5,
        r2: 5,
        r3: 5,
        r4: 5,
        comments: ''
      }
    }
  },
  methods: {
    load () {
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      const _this = this
      _this.loading = true
      axios.get('/order/' + username + '/history').then(function (resp) {
        _this.order = resp.data.result
        let id = _this.order.reverse()[0].id
        // 请求当次结账dishes信息
        axios.get('/order/' + id + '/history').then(function (resp) {

        })
      })
    },
    grade () {
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      // const _this = this
      // _this.loading = true
      axios.post('/' + username + '/comment', this.rates).then(function (resp) { // 存储评价数据到数据库
      })
      this.$message({
        showClose: true,
        message: '感谢您的评价，祝您生活愉快！',
        type: 'success'
      })
      this.$router.replace('/customer/index')
    }
  }
}
</script>

<style scoped>
  .back {
    width: 99%;
    height: 90%;
    background-image: url(../../assets/321.jpg);
    background-size: cover;
    margin-top: 50px;
  }

  .comment {
    width: 500px;
    height: 100px;
    margin-top: 15px;
  }

  .rate {
    float: left;
    margin-top: 100px;
    margin-left: 100px;
  }

  .demonstration {
    font-size: large;
  }

  .right {
    float: left;
    margin-left: 90px;
    margin-bottom: 50px;
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
    background-color: #021b4da5;
    border-color: #ffffff;
  }

  .el-row {
    margin-bottom: 20px;
  }

  .el-col {
    border-radius: 4px;
  }

  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>
