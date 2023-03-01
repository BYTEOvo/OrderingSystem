<template>
  <div>
    <el-descriptions title="用户信息" class="margin-top" :column="1" style="width: 1250px" border>
      <el-descriptions-item  >
        <template slot="label"><i class="el-icon-user"></i>用户名</template>
        {{userInfo.username}}
        <el-button style="float:right" disabled type="primary" icon="el-icon-edit" circle></el-button>
      </el-descriptions-item>
      <!--      <el-descriptions-item >-->
      <!--        <template slot="label"><i class="el-icon-phone-outline"></i>手机号</template>-->
      <!--        {{userInfo.phoneNum}}-->
      <!--        <el-button style="float:right" type="primary" icon="el-icon-edit" circle @click="editPhoneNumber"></el-button>-->
      <!--      </el-descriptions-item>-->
      <el-descriptions-item >
        <template slot="label"><i class="el-icon-lock"></i>密码</template>
        {{userInfo.password}}
        <el-button style="float:right" type="primary" icon="el-icon-edit" circle @click="editPassWord"></el-button>
      </el-descriptions-item>
      <el-descriptions-item >
        <template slot="label"><i class="el-icon-suitcase"></i>职业</template>
        {{userInfo.role}}
        <el-button disabled style="float:right" type="primary" icon="el-icon-edit" circle></el-button>
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
export default {
  name: 'CustomerInfo',
  data () {
    return {
      userInfo: []
    }
  },
  created () {
    this.loadUserInfo()
  },
  methods: {
    loadUserInfo () {
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      this.$axios.get('/user/' + username + '/info').then(resp => {
        if (resp && resp.status === 200) {
          this.userInfo = resp.data.result
        }
      })
    },
    editPassWord () {
      this.$prompt('请输入新密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({value}) => {
        this.userInfo.password = value
        this.$axios.post('/user/info/editPassWord', this.userInfo).then(resp => {
          if (resp && resp.data.code === 200) {
            this.$message({
              type: 'success',
              message: '你的新密码是: ' + value
            })
          } else {
            this.$message({
              type: 'warning',
              message: resp.data.message
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        })
      })
    },
    editPhoneNumber () {
      this.$prompt('请输入新手机号', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        this.$message({
          type: 'success',
          message: '你的新手机号是: ' + value
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        })
      })
    }
  }}

</script>
<style scoped>
.user-avatar{
  width:170px;
  height: 170px;
}
.user-main{
  text-align: left;
}
</style>
