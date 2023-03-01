<template>
  <div style="position: fixed;">
    <div id="background" class="animate__animated animate__zoomIn" style="margin-top: 180px;margin-left:500px; ">
      <el-form class="login-container" label-position="left" label-width="0px">
        <h1 class="login_title">红烧码铃薯餐厅</h1>
        <el-form-item>
          <el-input type="text" v-model="loginForm.user.username" auto-complete="off" placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input type="password" v-model="loginForm.user.password" auto-complete="off" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-col :span="11">
            <el-input placeholder="验证码" v-model="loginForm.code"></el-input>
          </el-col>
          <el-col class="line" :span="1">&nbsp;</el-col>
          <el-col :span="11">
            <!--加载验证码-->
            <img width="160px" onclick="this.src='http://localhost:8443/api/creatCodeImg?d='+new Date()*1" :src="url" />
          </el-col>
        </el-form-item>
        <el-form-item style="width: 100%">
          <el-button type="primary" style="width: 100%;background: #001f3f;border: none" v-on:click="login">登录
          </el-button>
        </el-form-item>
        <el-form-item>
          <a href="register">注册</a>
        </el-form-item>
      </el-form>
    </div>
    <div class="yun">
      <div style="float: right;height:100px;width:800px;position: absolute  ;right:400px; ">
        <img src="../assets/yunn.png">
        <img src="../assets/yun.png">
      </div>
    </div>
    <div class="he">
      <div style="float: right;height:300px;width:400px;position: absolute  ;right:-500px; ">
        <img src="../assets/he.png">
      </div>
    </div>
  </div>
</template>
<script>

export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        user: {
          username: '',
          password: ''
        },
        code: ''
      },
      url: 'http://localhost:8443/api/creatCodeImg',
      // url: 'http://123.60.210.166:8443/api/creatCodeImg',
      responseResult: [],
      role: ''
    }
  },
  methods: {
    login () {
      var _this = this
      this.$axios
        .post('/login', this.loginForm)
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            _this.$store.commit('login', successResponse.data.result)
            this.role = successResponse.data.result.role
            console.log(this.role)
            if (this.role === 'customer') {
              this.$router.replace('/show')
            } else if (this.role === 'cook') {
              this.$router.replace('/cook/main')
            } else {
              this.$router.replace('/' + this.role + '/index')
            }
          } else {
            this.$alert(successResponse.data.message, '提示', {
              confirmButtonText: '确定'
            })
            this.refreshCode()
            this.loginForm.code = ''
          }
        })
        .catch(failResponse => {
        })
    },
    refreshCode () {
      // this.url = 'http://123.60.210.166:8443/api/creatCodeImg?d=' + new Date() * 1
      this.url = 'http://localhost:8443/api/creatCodeImg?d=' + new Date() * 1
    }
  }
}

</script>
<style>
/*    @media screen and (min-width: 840px) {*/
/*        #background {*/
/*            position: fixed;*/
/*            left: 0;*/
/*            top: 0;*/
/*            width: 100%;*/
/*            height: 100%;*/
/*            background-image: url(https://img0.baidu.com/it/u=706369925,2175503806&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800);*/
/*            background-size: cover;*/
/*            z-index: -1;*/
/*        }*/
/*}*/
a {
  font-size: 15px
}

/* .poster {
margin:-5px  0px;

} */

.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 15% auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #ffffffec;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;

}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #00274d;
}

::-webkit-scrollbar {
  width: 10px;

}

::-webkit-scrollbar-thumb {
  background-color: #eaecf1;
  border-radius: 100px;
}

img {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.he{
  animation:fadeInBottomRight; /* referring directly to the animation's @keyframe declaration */
  animation-duration: 2s; /* don't forget to set a duration! */
}
.yun{
  animation:fadeInBottomLeft; /* referring directly to the animation's @keyframe declaration */
  animation-duration: 1.5s; /* don't forget to set a duration! */
}
</style>
