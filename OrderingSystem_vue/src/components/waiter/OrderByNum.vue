<template>
  <div>
  <!-- 传输桌号，以桌号为索引建立订单 -->
         <el-table :data="dishes" title="84">
            <el-table-column property="name" label="菜品名" width="160"></el-table-column>
            <el-table-column property="count" label="数量" width="150"></el-table-column>
            <el-table-column property="price" label="价格" width="100"></el-table-column>
          </el-table>
          <el-container>
            <el-footer>
                总价<span>{{1}}</span>
            <el-button  @click="handleorder" :loading="loading">{{ loading ? '提交中 ...' : '派单' }}</el-button>
            </el-footer>
          </el-container>
  </div>

</template>

<script>
export default {
  props: {
    num: String,
    // 获取桌号,根据桌号获取订单
    required: true
  },
  name: 'OrderByNum',
  data () {
    return {
      dishes: [{
        name: '红烧排骨',
        count: 3,
        price: 30,
        total: 30
      }],

      loading: false
    }
  },
  methods: {
    handleorder () {
      // 发送订单到后厨
    }
  }
}
</script>

<style>

</style>
