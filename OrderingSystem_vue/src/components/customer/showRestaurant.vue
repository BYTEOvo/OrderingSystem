<template>
  <div class="block text-center">
    <!-- 母版1 -->
    <!--    <el-carousel trigger="click" height="150px">-->
    <!--      <el-carousel-item v-for="item in dishes" :key="item">-->
    <!--        <h3 class="small justify-center" text="2xl">{{ item }}</h3>-->
    <!--      </el-carousel-item>-->
    <!--    </el-carousel>-->

    <!-- <el-carousel :interval="4000" type="card" height="600px" trigger="click"
      style="margin-top: 10px;margin-left: 10px;margin-right: 10px;">
      <el-carousel-item v-for="item in specialDishes" :key="item.price">
        <h3>{{ item.name }}</h3>
      </el-carousel-item>
    </el-carousel> -->

    <el-carousel trigger="click" height="950px">
      <el-carousel-item>
        <el-container style="width:50%;height: 950px;margin-bottom: 10px;">
          <!-- 背景 -->

          <h1 class="my-element">BEST<br>QUALITY<br>FOODS</h1>

          <el-button v-show="show2" class="my-button" @click="moveOn()"><i class="el-icon-arrow-right"></i>Get Start
          </el-button>

          <transition class="transition1">
            <div v-show="show1">
              <el-container class="my-start">
                <el-row>
                  <el-button v-if="!normal" @click="takeout">外卖</el-button>
                  <el-button v-if="!normal" @click="normal=true">堂食</el-button>
                </el-row>
                <el-row v-if="normal">
                  <el-select v-model="value" clearable :disabled="isConfirmdesk" placeholder="选择你的桌号">
                    <!--        <el-option-group-->
                    <!--          v-for="group in options"-->
                    <!--          :key="group.label"-->
                    <!--          :label="group.label">-->
                    <el-option v-for="item in table" :key="item.id" :label="item.name" :value="item.id" />
                    <!--        </el-option-group>-->
                  </el-select>
                  <el-button type="primary" @click="confrimDesk()" :disabled="button">确定</el-button>
                </el-row>
              <!-- 将要使用动画的内容放在transition里即可 -->
              <transition enter-active-class="animate__animated animate__fadeInDown"
                          leave-active-class="animate__animated animate__fadeOutUp">
                <div v-show="show">
                  <el-button @click="begin()" class="gogogo" type="primary">开始点餐!</el-button>
                </div>
              </transition>
            </el-container>
          </div>
          </transition>
        </el-container>
      </el-carousel-item>
    </el-carousel>

    <!--特色菜走马灯-->
    <!--
    <el-carousel trigger="click" height="800px">
      <el-carousel-item v-for="item in specialDishes" :key="item.price">
        <h3>{{ item.name }}</h3>
      </el-carousel-item>
    </el-carousel> -->

    <el-carousel trigger="click" height="400px" style="margin-bottom: 60px;">
      <el-carousel-item>
        <img src="../../assets/1.png">
      </el-carousel-item>
      <el-carousel-item>
        <img src="../../assets/2.png">
      </el-carousel-item>
      <el-carousel-item>
        <img src="../../assets/4.png">
      </el-carousel-item>
      <el-carousel-item>
        <img src="../../assets/5.png">
      </el-carousel-item>
      <el-carousel-item>
        <img src="../../assets/6.png">
      </el-carousel-item>
      <el-carousel-item>
        <img src="../../assets/7.png">
      </el-carousel-item>
    </el-carousel>

    <!-- <el-container>
      <el-aside style="width: 500px;height:490px">

        <el-row>
          <el-tabs type="border-card"
            style="width: 450px;height: 350px;margin-left:10px;border-radius: 4px;opacity: 0.9;">
            <el-tab-pane v-for="item in bulletins" :label="item.access" :key="item.id">
              {{item.content}}
            </el-tab-pane>
          </el-tabs>
        </el-row>
      </el-aside>
      <el-main>
      </el-main>
    </el-container> -->

    <el-footer>

    </el-footer>

  </div>

</template>

<script>
import { ref } from 'vue'
// eslint-disable-next-line no-unused-vars

export default {
  name: 'showRestaurant',
  data () {
    return {
      // bulletins: [],
      options: [
        {
          label: '十二人桌',
          options: [
            {
              value: '601',
              label: '601'
            },
            {
              value: '602',
              label: '602'
            }
          ]
        },
        {
          label: '小桌(4人桌)',
          options: [
            {
              value: '401',
              label: '401'
            },
            {
              value: '402',
              label: '402'
            },
            {
              value: '403',
              label: '403'
            },
            {
              value: '404',
              label: '404'
            }
          ]
        }
      ],
      isConfirmdesk: false,
      button: false,
      specialDishes: [],
      table: [],
      value: ref(''),
      show: false,
      show1: false,
      show2: true,
      normal: false
    }
  },
  mounted: function () {
    this.loadSpecialDishes()
    this.loadTables()
    // this.loadBulletins()
  },

  methods: {
    takeout () {
      // 生成外卖订单
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      this.$axios.get('/order/' + username + '/delivery/creat').then(resp => {
        if (resp && resp.data.code === 200) {
          this.$router.replace('/menu')
        } else {
          this.$alert(resp.data.message, '提示', {
            confirmButtonText: '确定'
          })
        }
      })
    },
    loadSpecialDishes () {
      var _this = this
      this.$axios.get('/dishes/special').then(resp => {
        if (resp && resp.status === 200) {
          _this.specialDishes = resp.data.result
        }
      })
    },
    // loadBulletins() {
    //   var _this = this
    //   this.$axios.get('/customer/bulletins').then(resp => {
    //     if (resp && resp.status === 200) {
    //       _this.bulletins = resp.data.result
    //     }
    //   })
    // },
    loadTables () {
      var _this = this
      this.$axios.get('/table/ready').then(resp => {
        if (resp && resp.status === 200) {
          _this.table = resp.data.result
        }
      })
    },
    begin () {
      this.$router.replace('/menu')
    },
    confrimDesk () {
      this.$confirm('确定选择此桌？').then(_ => {
        console.log(this.value)
        var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
        this.$axios.get('/order/' + username + '/creat/' + this.value).then(resp => {
          if (resp && resp.data.code === 200) {
            this.isConfirmdesk = true
            this.button = true
            this.show = true
            this.show1 = true
          } else {
            this.$alert(resp.data.message, '提示', {
              confirmButtonText: '确定'
            })
          }
        })
      })
    },
    moveOn () {
      this.show1 = true
      this.show2 = false
    }
  }
}
</script>

<style scoped>
.el-carousel__item {
  background-image: url(../../assets/start.jpg);
  background-size: cover;
}

.el-footer {
  height: 100px;
  background-image: url("../../assets/bottom.png");
  background-size: contain;
}

.my-element {
  height: 1000px;
  background-image: linear-gradient(to right, black, rgba(255, 255, 0, 0));
  font-family: myFont;
  font-size: 180px;
  color: rgb(255, 255, 255);
  display: inline-block;
  margin: 0 0.5rem;
  line-height: 1.5;
  animation: fadeInLeft;
  /* referring directly to the animation's @keyframe declaration */
  animation-duration: 4s;
  /* don't forget to set a duration! */
  position: absolute;
  left: -7px;
  top: 0;
}

.my-button {

  font-family: "PingFang SC";
  font-size: 40px;
  color: rgb(255, 255, 255);
  width: 300px;
  background-color: #ffffffaa;
  display: inline-block;
  margin: 0 0.5rem;
  line-height: 1.5;
  animation: fadeInRight;
  /* referring directly to the animation's @keyframe declaration */
  animation-duration: 4s;
  /* don't forget to set a duration! */
  position: absolute;
  right: 100px;
  bottom: 150px;
  display: block;

  text-transform: uppercase;

  border: 1px solid rgb(255, 255, 255);
  background: -webkit-linear-gradient(90deg, #18053e, #18053e, rgb(115, 0, 0), rgb(115, 0, 0));
  background: -moz-linear-gradient(90deg, #18053e, #18053e, rgb(115, 0, 0), rgb(115, 0, 0));
  background: -o-linear-gradient(90deg, #18053e, #18053e, rgb(115, 0, 0), rgb(115, 0, 0));
  background: linear-gradient(90deg, #18053e, #18053e, rgb(115, 0, 0), rgb(115, 0, 0));
  background-size: 300% 300%;
  background-position: 1% 50%;
  transition: all 4s ease;
  -webkit-transition: background 0.3s ease;
  -moz-transition: background 0.3s ease;
  -o-transition: background 0.3s ease;
  outline: none;
  cursor: pointer;

}

.my-button:hover {
  color: #fff;
  background-position: 99% 50%;
}

.my-start {
  font-family: "PingFang SC";
  font-size: 40px;
  color: rgb(0, 67, 125);
  display: inline-block;
  margin: 0 0.5rem;
  line-height: 1.5;

  position: absolute;
  right: 100px;
  bottom: 100px;
}

/* 更改element-UI按钮样式 */
.el-button--primary {
  color: #fff;
  background-color: #18053e;
  border-color: #18053e;
}

.el-button--primary:hover {
  background-color: #18053e;
  border-color: #18053e;
}

.el-button--primary:focus {
  background-color: rgb(115, 0, 0);
  border-color: rgb(115, 0, 0);
}

.gogogo {
  background-color: #180042;
  border-color: #001643;
  font-style: oblique;
  border-radius: 50px;
  width: 300px;
  height: 100px;
  font-size: 40px;
}

.gogogo:focus {
  background-color: rgb(115, 0, 0);
  border-color: rgb(115, 0, 0);
}

.gogogo:hover {
  background-color: rgb(115, 0, 0);
  border-color: rgb(115, 0, 0);
}

img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
