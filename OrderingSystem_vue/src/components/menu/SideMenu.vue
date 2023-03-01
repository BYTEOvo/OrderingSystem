<template>
  <div>

    <el-button class="bulletin" type="primary" @click="dialogVisible = true">
      <div class="text">
        <i class="el-icon-bell"></i>
        查看公告
      </div>
    </el-button>

    <el-dialog title="公告" :visible.sync="dialogVisible" class="dialog">
      <div class="tabs">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane v-for="item in bulletins" :label="item.access" :key="item.id">
            {{item.content}}
          </el-tab-pane>
        </el-tabs>
      </div>
      <div slot="footer" class="dialog-footer" X>
        <el-button @click="dialogVisible = false" type="primary" style="color:rgb(255, 255, 255) ;">关 闭</el-button>
      </div>
    </el-dialog>

    <el-menu class="dishTypes" default-active="1" @select="handleSelect" active-text-color="rgb(2, 0, 105)">
      <el-menu-item v-for="item in dishTypeList.slice().reverse()" :key="item.id" :index="item.id.toString()" :title="item.name">
        <i class="el-icon-food"></i>
        {{item.name}}
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'SideMenu',
  data () {
    return {
      dishTypeList: [],
      dishTypeId: '',
      dialogVisible: false,
      bulletins: [],
      activeName: 'second'
    }
  },
  mounted: function () {
    this.loadBulletins()
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
          this.dishTypeList = resp.data.result
          this.dishTypeList.push({
            id: 1,
            name: '全部'
          })
        }
      })
    },
    loadBulletins () {
      var _this = this
      this.$axios.get('/customer/bulletins').then(resp => {
        if (resp && resp.status === 200) {
          _this.bulletins = resp.data.result
        }
      })
    },
    handleClick (tab, event) {
      console.log(this.choseColor)
    }

    // viewBulletin() {
    //   const h = this.$createElement;
    //   this.$msgbox({
    //     title: '公告',
    //     message: 111,
    //     showCancelButton: true,
    //     showConfirmButton: false,
    //     cancelButtonText: '关闭',
    //   }).then(action => {
    //     this.$message({
    //       type: 'info',
    //       message: 'action: ' + action
    //     });
    //   });
    // }
  }
}

</script>

<style scoped>
.dishTypes {
  position: fixed;
  margin-top: 109px;
  margin-left: 47%;
  left: -700px;
  top: 140px;
  width: 190px;
  border-radius: 12px
}

.el-menu-item.is-active {
  border-radius: 12px;
  background-color: #00278191 !important;
  color: #fff;
}

.bulletin {
  position: fixed;
  margin-top: 50px;
  margin-left: 47%;
  left: -700px;
  top: 140px;
  width: 190px;
  border-radius: 12px;
}

.text {
  color: #ffffff;
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

.dialog>>>.el-dialog,

.el-dialog {
  background-image: url(../../assets/bulletin.jpg);
  background-size: cover;
}

.tabs {
  text-align: center;
}

.el-tabs {
  background-color: #00000000;
  color: rgb(0, 19, 82);
  position: relative;
  width: 500px;
  height: 400px;
  margin-left: 10px;
  border-radius: 4px;
  font-size: xx-large;
  font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
}

.tabs>>>.el-tabs,

  /* .el-tabs__active-bar {
    background-color: rgba(2, 26, 120, 0.771);
    z-index: 1;
  }

  .el-tabs__item.is-active {
    color: rgb(39, 184, 208);
    font-weight: bold;
  } */

  /* 去除灰色横条 */
.el-tabs__nav-wrap::after {
  position: static !important;
}

/* 设置滑块颜色 */
.el-tabs__active-bar {
  background-color: #e3ff11 !important;
}

/* 设置滑块停止位置 */
.el-tabs__active-bar.is-top {
  height: 37px;
  width: 104px ! important;
  border-radius: 17px;
  top: 0px !important;
  left: -18px !important;
  position: absolute !important;
  z-index: 1;
}

/* 设置当前选中样式 */
.el-tabs__item.is-active {
  color: #007f28 !important;
  z-index: 2;
}

/* 设置未被选中样式 */
.el-tabs__item {
  padding: 0 20px !important;
  width: 104px;
  box-sizing: border-box;
  display: inline-block;
  position: relative !important;
  color: #e61111 !important;
  z-index: 2;
}
</style>
