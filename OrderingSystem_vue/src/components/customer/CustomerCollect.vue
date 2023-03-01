<template>
  <div>
    <el-tooltip effect="light" placement="right"
                v-for="item in CollectDishes"
                :key="item.id">
      <p slot="content" style="font-size: 14px;margin-bottom: 6px;">{{item.dish.name}}</p>
      <p slot="content" style="font-size: 13px;margin-bottom: 6px">
        <span>{{ "价格："+item.dish.price }}</span>
        <!--          <span>{{item.date}}</span> /-->
      </p>
      <p slot="content" style="width: 300px" class="description">{{item.dish.description}}</p>
      <el-card style="width: 250px;margin-bottom: 20px;height: 300px;float: left;margin-right: 15px" class="dishes"
               bodyStyle="padding:10px" shadow="hover">
        <div class="cover">
          <img :src="item.dish.imgurl" alt="封面">
        </div>
        <br>
        <div class="info" style="margin-top:3px;">
          <div class="title" style="margin-left :10px">
            <a href="">{{item.dish.name}}</a>
          </div>
        </div>
        <br>
        <div class="price" style="float: right;">{{ "价格："+item.dish.price }}</div>
      </el-card>
    </el-tooltip>
  </div>
</template>

<script>
export default {
  name: 'CustomerCollect',
  created () {
    this.loadDishCollect()
  },
  data () {
    return {
      CollectDishes: []
    }
  },
  methods: {
    loadDishCollect () {
      var _this = this
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      this.$axios.get('/dish/' + username + '/collect').then(resp => {
        if (resp && resp.status === 200) {
          _this.CollectDishes = resp.data.result
        }
      })
    }
  }
}
</script>

<style scoped>
.cover {
  width: 250px;
  height: 200px;
  margin-bottom: 7px;
  overflow: hidden;
  cursor: pointer;
}
.score{
  color: #333;
  width: 102px;
  font-size: 13px;
  margin-bottom: 6px;
  text-align: left;
}

img {
  width: 250px;
  height: 200px;
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

</style>
