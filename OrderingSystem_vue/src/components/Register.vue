<template>
  <div id="background" class="poster">
    <el-form class="login-container" label-position="left"
             label-width="0px" v-loading="loading">
      <h3 class="login_title">用户注册</h3>
      <el-form-item>
        <el-input type="text" v-model="loginForm.username"
                  auto-complete="off" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input type="password" v-model="loginForm.password"
                  auto-complete="off" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="register">注册并登录</el-button>
      </el-form-item>
      <el-form-item>
        <a href="login">已有帐号？登录</a>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>

export default{
  name: 'register',
  data () {
    return {
      checked: true,
      loginForm: {
        username: '',
        password: ''
      },
      loading: false
    }
  },
  methods: {
    register () {
      var _this = this
      this.$axios
        .post('/register', {
          username: this.loginForm.username,
          password: this.loginForm.password
        })
        .then(resp => {
          if (resp.data.code === 200) {
            this.$alert('注册成功', '提示', {
              confirmButtonText: '确定'
            })
            _this.$router.replace('/login')
          } else {
            this.$alert(resp.data.message, '提示', {
              confirmButtonText: '确定'
            })
          }
        })
        .catch(failResponse => {})
    }
  }
}
</script>
<style>

/*@media screen and (min-width: 840px) {*/
/*  #background {*/
/*    position: absolute;*/
/*    left: 0;*/
/*    top: 0;*/
/*    width: 100%;*/
/*    height: 100%;*/
/*    background-image: url(../assets/background.jpg);*/
/*    background-size: cover;*/
/*    z-index: -1;*/
/*  }*/
/*}*/
a{font-size:15px}
.poster {
  margin:-5px  0px;

}

.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 15% auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background:#ffffffec;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
::-webkit-scrollbar {
  width: 10px;
}

::-webkit-scrollbar-thumb {
  background-color: #eaecf1;
  border-radius: 100px;
}
</style>
