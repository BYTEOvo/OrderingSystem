<template>
    <el-container style="height:100%">
        <el-tabs style="width:100%;height:100%" type="border-card">
            <el-tab-pane>
                <span slot="label"><i class="el-icon-date"></i> 顾客公告</span>
              <el-button type="primary" round  icon="el-icon-edit" @click="seen = !seen">编辑新公告</el-button>
              <el-container v-if="seen">
                <!--                  <i class="el-icon-upload"></i>-->
                <el-input type="textarea" :rows="10" placeholder="请输入顾客公告内容" v-model="textarea1" maxlength="100" show-word-limit>
                </el-input>
                <el-button type="primary" style="margin-top: 0px;" @click="pullCustomer"><i class="el-icon-plus el-icon--left"> 发布</i></el-button>
              </el-container>
              <el-table :data="customerData" stripe style="width: 100%" max-height="500">
                <el-table-column prop="id" label="Id" width="130" />
                <el-table-column prop="content" label="内容" width="800" />
                <!--      <el-table-column prop="quantity" label="库存数量" width="120" />-->
                <el-table-column fixed="right" label="删除" width="150">
                  <template #default="scope">
                    <!-- <el-button @click="editData(scope.row)" type="text" size="small">编辑</el-button> -->
                    <el-button @click="delBulletin(scope.row)" type="text" size="small">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
<!--                <el-row>-->
<!--                    <el-col :span="6" v-for="(o, index) in 6" :key="o" :offset="index > 0 ? 2 : 0">-->
<!--                        <el-card :body-style="{ padding: '0px' }">-->
<!--                            <div class="demo-image__preview">-->
<!--                                &lt;!&ndash; preview-src-list为公告图片 &ndash;&gt;-->
<!--                                <el-image style="width: 250px; height: 200px" :src="url" :preview-src-list="srcList">-->
<!--                                </el-image>-->
<!--                            </div>-->
<!--                            <div style="padding: 30px;">-->
<!--                                &lt;!&ndash; v-if &ndash;&gt;-->
<!--                                <el-tag type="success">空闲</el-tag>-->

<!--                                <span>{{"公告"+o}}</span>-->
<!--                                <div class="bottom clearfix">-->
<!--                                    <el-button type="text" class="button" @click="delBulletin1">删除</el-button>-->
<!--                                </div>-->
<!--                            </div>-->
<!--                        </el-card>-->
<!--                    </el-col>-->
<!--                </el-row>-->
            </el-tab-pane>

            <el-tab-pane>
                <span slot="label"><i class="el-icon-date"></i> 员工公告</span>
              <el-button type="primary" round  icon="el-icon-edit" @click="ySeen = !ySeen">编辑新公告</el-button>
              <el-container v-if="ySeen">
                <div style="margin: 20px 0;"></div>
                <el-input type="textarea" :rows="10" placeholder="请输入员工公告内容" v-model="textarea2" maxlength="100" show-word-limit>
                </el-input>
                <el-button type="primary" style="margin-top: 0px;" @click="pullY"><i class="el-icon-plus el-icon--left"> 发布</i></el-button>
              </el-container>
                <el-table :data="staffData" stripe style="width: 100%" max-height="500">
                    <el-table-column prop="id" label="Id" width="130" />
                    <el-table-column prop="content" label="内容" width="800" />
                    <!--      <el-table-column prop="quantity" label="库存数量" width="120" />-->
                    <el-table-column fixed="right" label="删除" width="150">
                        <template #default="scope">
                            <!-- <el-button @click="editData(scope.row)" type="text" size="small">编辑</el-button> -->
                            <el-button @click="delBulletin(scope.row)" type="text" size="small">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-tab-pane>
        </el-tabs>
    </el-container>
</template>

<script>
import axios from 'axios'

export default {
  name: 'ViewBulletin',
  inject: ['reload'],
  data () {
    return {
      currentDate: new Date(),
      url: 'https://640837.freep.cn/640837/retouch_2022082408570759.jpg',
      srcList: [
        'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png'
      ],
      customerData: [],
      staffData: [],
      seen: false,
      ySeen: false,
      textarea1: '',
      textarea2: ''
    }
  },
  created () {
    this.loadCostumerMsg()
    this.loadStaffMsg()
  },
  methods: {
    loadStaffMsg () {
      this.$axios.get('/cook/bulletins').then((res) => {
        if (res && res.data.code === 200) { this.staffData = res.data.result }
      })
    },
    loadCostumerMsg () {
      this.$axios.get('/customer/bulletins').then((res) => {
        if (res && res.data.code === 200) { this.customerData = res.data.result }
      })
    },
    // 删除员工公告
    delBulletin (row) {
      this.$confirm('此操作将永久删除该条目, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const _this = this
        axios.get('/bulletin/delete/' + row.id)
          .then(res => {
            console.log(res)
            _this.$message({
              showClose: true,
              message: '修改成功',
              type: 'success'
            })
            this.reload()
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 发布顾客公告
    pullCustomer () {
      axios.post('/bulletin/add', {access: 'customer', content: this.textarea1}).then()
      this.reload()
    },
    // 发布员工公告
    pullY () {
      axios.post('/bulletin/add', {access: 'staff', content: this.textarea2}).then()
      this.reload()
    }
  }
}

</script>

<style>
    .time {
        font-size: 13px;
        color: #999;
    }

    .bottom {
        margin-top: 13px;
        line-height: 12px;
    }

    .button {
        padding: 0;
        float: right;
    }

    .image {
        width: 100%;
        display: block;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }
</style>
