<template>
  <container>
    <el-tabs type="border-card">
      <el-tab-pane>
        <span slot="label"><i class="el-icon-date"></i> 订单结账</span>
        <el-row>
          <el-card v-for="item in orders" :key="item.id"
            :style="{'background-color':item.state?'rgba(144,238,144,0.5)':'rgba(255,185,230,0.964)'} " class="orders"
            bodyStyle="padding:10px" shadow="hover">
            <div style="padding: 30px;">
              <div>{{"订单"+item.oid}}</div>
              <div>{{"桌号"+item.tid}}</div>
              <div class="bottom clearfix">
                <el-button type="text" class="button" :disabled="item.state == true">结账</el-button>
              </div>
              <br>
            </div>
          </el-card>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </container>
</template>

<script>
export default {
  name: 'AdminPay',
  data () {
    return {
      currentDate: new Date(),
      url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      srcList: [
        'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png'
      ],
      orders: [
        {
          oid: 1,
          tid: 10,
          state: false
        }, {
          oid: 2,
          tid: 12,
          state: true
        },
        {
          oid: 3,
          tid: 22,
          state: true
        }
      ]
    }
  }
}

</script>

<style>
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

  .orders {
    width: 100px;
    margin-bottom: 10px;
    height: 150px;
    float: left;
    margin-right: 15px;
    border-radius: 20px;
    border-style: solid;
    border-width: 10px;
    box-shadow: 10px 10px 10px #b0b0b0f0;
  }
</style>
