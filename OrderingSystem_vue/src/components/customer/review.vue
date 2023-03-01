<template>

    <div class="rating-wrapper">
     <el-container >
       <el-aside width="80%">
     <el-container>
         <el-aside width="3%">

         </el-aside>
         <el-main>
                   <el-row >
             <el-col :span="2">
   <el-avatar class="avatar"   :size=40 ><img src="../../assets/a.jpg" /></el-avatar>
             </el-col>
   <el-col :span="2">
     <h1 class="name">{{item.user.username}}</h1>
     <div class="time">{{item.payTime}}</div>

   </el-col>

           </el-row>

       <div class="text" >{{item.comments}}</div>
<!--
        <div class="recommend" >
                   <span class="el-icon-fork-spoon"></span>
                   <span class="item">红烧排骨</span>
                   <span class="item">红烧排骨</span>
   </div> -->

         </el-main>
       </el-container>

       </el-aside>
    <el-main >
     <div class="star-wrapper">

                 <el-row>

   </el-row>
                 <el-row  >
                  <el-col :span="4">  菜品:</el-col>
               <el-col :span="18">
                 <el-rate v-model="item.urate" disabled show-score text-color="#ff9900" />
               </el-col>
             </el-row>
             <el-row  >
                  <el-col :span="4">  位置:</el-col>
               <el-col :span="18">
                 <el-rate v-model="item.r1" disabled show-score text-color="#ff9900" />
               </el-col>
             </el-row>
             <el-row  >
                  <el-col :span="4">  服务:</el-col>
               <el-col :span="18">
                 <el-rate v-model="item.r2" disabled show-score text-color="#ff9900" />
               </el-col>
             </el-row>
             <el-row  >
                  <el-col :span="4">  候时:</el-col>
               <el-col :span="18">
                 <el-rate v-model="item.r3" disabled show-score text-color="#ff9900" />
               </el-col>
             </el-row>
             <el-row  >
                  <el-col :span="4">  坏境:</el-col>
               <el-col :span="18">
                 <el-rate v-model="item.r4" disabled show-score text-color="#ff9900" />
               </el-col>
             </el-row>

                 </div>
    </el-main>
               <div class="content">
               </div>
     </el-container>
         </div>
   </template>

<script>
export default {
  name: 'review',
  props: {
    item: Object

  },
  data () {
    return {
      circleUrl: '../../assets/a.png'

    }
  },
  methods: {

  }
}
</script>

   <style>
     .rating-wrapper{

       border-bottom:1px solid rgba(7,17,27,0.1);
           margin:0 16px;
           padding:8px 0;
     }

     .avatar{
       flex:0 0 28px;
             width:28px;
             height:28px;
             margin-right:12px
     }

   .content{

             flex:1;
             position:relative
   }
   .name{
    text-align: left;
               line-height:0px;
               font-size:10px;
               color:rgb(7,17,27);
               margin-bottom:4px
   }
    .star-wrapper{
               font-size:10px;
               margin-bottom:6px
   }
   .item{
                 display:inline-block;
                 font-size:9px;
                 padding:0 6px;
                 border:1px solid rgba(7,17,27,0.1);
                 border-radius:1px;
                 background-color:#fff;
                 margin-right:8px
   }
   .text{
    margin-left:8%;
    text-align: left;
               font-size:15px;
               color:rgb(92, 97, 101);
       margin-top:5px;
               margin-bottom:8px;
   }
   .recommend{

               font-size:0px;
               line-height:16px;
   }
   .el-icon-fork-spoon{
                 display:inline-block;
                 margin:0 8px 4px 0;
                 font-size:18px;
                 color:rgb(0,160,220)
   }
   .time{
    margin-top: 10px;

               font-size:10px;
               font-weight:200;
               color:rgb(147,153,159);
               line-height:10px
   }
   .el-row{
     margin-bottom: 3px;
   }
   </style>
