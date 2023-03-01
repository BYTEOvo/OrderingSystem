<template>
  <div class="user_skills" style="">
    <div aria-setsize="auto">
      <h1 style="font-size:20px">桌号</h1>
      <a class="info">红色为占用座位，绿色为空闲桌位，A开头为单人座，B开头为双人座，C开头为4人座，D开头为6人座</a>
      <el-divider></el-divider>
      <el-card  v-for="item in tables.filter(item => item.name !== 'DELIVERY')" style="background-color:rgb(233,233,233,0.1)"
                :key="item.id" :style="{'border-color':item.tableState === 'ready'?'rgba(9, 133, 79, 0.964)':'rgba(230, 70, 38, 0.964)'} " class="tables"
                bodyStyle="padding:30px" shadow="hover" @click.native="gotoOrderOrCash(item)">
        <div class="info">
          <div class="title">
            <h1>{{item.name}}</h1>
          </div>
        </div>
        <br>
      </el-card>
      <el-dialog
        append-to-body
        title="支付信息"
        center
        :visible.sync="dialogVisible"

        width="40%"
        :before-close="handleClose">
        <el-table :data="orderDetail.filter(item => item.count > 0)" >
          <el-table-column prop="dish.name" label="菜品名" />
          <el-table-column prop="count" label="数量"/>
          <el-table-column prop="dish.price" label="单价"/>
          <el-table-column label="总价"><template
            #default="scope"><span>{{scope.row.dish.price*scope.row.count}}</span></template>
          </el-table-column>
          <!-- 菜品单价乘数量 -->
        </el-table>
        <el-input style="width:40%" placeholder="请输入折扣" v-model="input1">
          <template slot="prepend">折扣:</template>
        </el-input>

     <span style="margin-left:10%">合计：{{order.price}}</span>
        <!-- 订单打折后的总价 -->
        <br/>
        <br/>
        <el-button  size="small" style="text-decoration-color: white;width: 20%;margin-left: 40%;" type="info" @click="AliPay(order)">支付</el-button>
    </el-dialog>
    </div>

  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Display',
  inject: ['reload'],
  data () {
    return {
      total: 0,
      tableData: [],
      loading: true,
      details: [],
      orderDetail: [],
      order: [],
      currentTable: [],
      CurrentPage: 1,
      childLoading: true,
      tables: [],
      dialogVisible: false,
      input1: 1
    }
  },
  created () {
    this.loadTables()
  },
  methods: {
    loadTables () {
      var _this = this
      // 通过后端得到每一桌的状况
      this.$axios.get('/waiter/tables').then(resp => {
        if (resp && resp.data.code === 200) {
          _this.tables = resp.data.result
        }
      })
    },
    gotoOrderOrCash (item) {
      this.loadOrder(item.id)
      // 通过桌号得到对应订单状态，为待结账可以进行结账，否则出现该桌还未下单提示，未完成
      // if(){}
      // else{
      //     this.$alert('该桌号还未下单')
      //   .then(_ => {
      //     done();
      //   })
      //   .catch(_ => {});
      // }
      var _this = this
      // 通过调取桌号得到当前桌的订单状况
      this.$axios.get('admin/' + item.id + '/order/detail').then(resp => {
        if (resp && resp.data.code === 200) {
          _this.orderDetail = resp.data.result
        }
      })
      _this.dialogVisible = !_this.dialogVisible
    },
    AliPay (row) {
      axios.post('/create/' + this.input1, row).then(resp => {
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
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {
        })
    },
    loadOrder (id) {
      this.input1 = 1
      const _this = this
      axios.get('/admin/order/' + id).then(function (resp) {
        if (resp && resp.data.code === 200) {
          _this.order = resp.data.result
        }
      })
    }
  }
}

</script>

<style scoped>

.el-dialog {
        margin: 0 auto !important;
        position: relative;}
        .el-dialog__header{
            position: absolute;
            left: 0;
            top: 0;
            right: 0;
            width: 100%;
            height: 60px;
            z-index: 1;
        }
        .el-dialog__body {
            width: 100%;
            overflow: hidden;
            overflow-y: auto;
            max-height: 90vh;
            padding-top: 80px;
            padding-bottom: 100px;
            z-index: 1;
        }
        .el-dialog__footer{
            position: absolute;
            left: 0;
            bottom: 0;
            right: 0;
            width: 100%;
            height: 80px;
            z-index: 1;
        }

.tables{
  width: 100px;
  margin-bottom: 20px;
  height: 150px;
  float: left;
  margin-right: 30px;
  border-radius: 20px;
  border-style: solid;
  border-width: 10px;
  box-shadow: 10px 10px 10px #b0b0b0f0;
}
/*::v-deep .el-table__expanded-cell {*/
/*  background-color: transparent !important;*/
/*}*/

/*::v-deep .el-table th{*/
/*  background-color: transparent;*/
/*}*/
/*::v-deep .el-table tr{*/
/*  background-color: transparent;*/
/*}*/
/*::v-deep .el-table td {*/
/*  background-color: transparent;*/
/*}*/
/*!*表头*!*/
/*::v-deep .el-table__header th {*/
/*  padding: 0;*/
/*  background: transparent;*/
/*  line-height: 30px;*/
/*}*/
.user_skills >>> .el-table--fit{
  padding: 10px;
}
.user_skills >>> .el-table, .el-table__expanded-cell {
  background-color: transparent;
}

.user_skills >>> .el-table tr {
  background-color: transparent!important;
}
.user_skills >>> .el-table th {
  background-color: transparent!important;
}
.user_skills >>> .el-table--enable-row-transition .el-table__body td, .el-table .cell{
  background-color: transparent;
}
.el-table::before {
  left: 0;
  bottom: 0;
  width: 100%;
  height: 0px;
}
</style>
