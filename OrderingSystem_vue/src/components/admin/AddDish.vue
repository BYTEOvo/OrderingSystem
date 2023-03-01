<template>
    <el-form :model="dishInfo" :rules="rules" ref="dishInfo" label-width="120px" class="demo-dishInfo">
      <el-form-item label="特色菜" prop="id">
        <el-radio v-model="dishInfo.special" label="1">是特色菜</el-radio>
        <el-radio v-model="dishInfo.special" label="0">不是特色菜</el-radio>
      </el-form-item>
        <el-form-item label="上传图片" prop="imgurl">
        <el-upload
          ref="upload"
          class="upload-demo"
          action="http://localhost:8443/api/covers"
          drag
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          :on-success="handleSuccess"
          multiple
          :limit="1"
          :on-exceed="handleExceed"
          :file-list="fileList"
        >
          <el-icon class="el-icon--upload"><upload-filled /></el-icon>
          <div class="el-upload__text">
            拖拽图片至此处 或 <em>点击上传图片</em>
          </div>
          <template #tip>
            <div class="el-upload__tip">
              1张大小不超过3MB的png/jpg/jpeg文件
            </div>
          </template>
        </el-upload>
      </el-form-item>
    <el-form-item label="菜品名称" prop="name" placeholder="请输入菜品名称" >
        <el-input v-model="dishInfo.name"></el-input>
    </el-form-item>
    <el-form-item label="菜品价格" prop="price" placeholder="请输入菜品价格" >
        <el-input v-model="dishInfo.price"></el-input>
    </el-form-item>
    <el-form-item label="菜品描述" prop="description" placeholder="请输入菜品描述" >
        <el-input v-model="dishInfo.description"></el-input>
    </el-form-item>
      <el-form-item label="菜品种类" prop="type" style="display: flex ">
        <el-select v-model="dishInfo.type" placeholder="请选择" class="dishType">
          <el-option
            v-for="item in options"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
<!--    <el-form-item label="是否可借" prop="isaccessible">-->
<!--    <el-select v-model="bookInfo.isaccessible" placeholder="选择当前书籍是否可借阅">-->
<!--      <el-option label="是" value="是" ></el-option>-->
<!--      <el-option label="否" value="否"></el-option>-->
<!--    </el-select>-->
<!--    </el-form-item>-->
    <el-form-item>
        <el-button type="primary" @click="submitForm('dishInfo')">添加</el-button>
        <el-button @click="resetForm('dishInfo')">重置</el-button>
        <el-button @click="back('dishInfo')">返回</el-button>
    </el-form-item>
    </el-form>
</template>

<script>
import axios from 'axios'

export default{
  name: 'AddDish',
  data () {
    return {
      fileList: [],
      dishInfo: {
        name: '',
        price: '',
        special: '',
        description: '',
        imgurl: ''
        // quantity: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入菜品名称', trigger: 'blur' },
          { min: 1, max: 100, message: '1-100个字符', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入菜品价格', trigger: 'blur' },
          { min: 1, max: 1000, message: '1-100个字符', trigger: 'blur' }
        ],
        id: [
          { required: true, message: '请输入菜品ID', trigger: 'blur' },
          { min: 1, max: 25565, message: 'id范围1-25565', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入菜品描述', trigger: 'blur' },
          { min: 1, max: 25565, message: '不超过100字', trigger: 'blur' }
        ]
      },
      options: []
    }
  },
  created () {
    const _this = this
    /* axios.get("http://localhost:8089/book/findById/"+this.$route.query.id).then(function (resp){
            _this.bookInfo = resp.data;
        }) */
    _this.dishInfo.id = this.$route.query.id
    _this.dishInfo.name = this.$route.query.name
    _this.dishInfo.price = this.$route.query.price
    _this.dishInfo.description = this.$route.query.description
    this.getDishTypeList()
    // _this.dishInfo.quantity = this.$route.query.quantity
  },
  methods: {
    submitForm (formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.put('/dishes/update', _this.dishInfo).then(function (resp) {
            if (resp.data.message === '成功') {
              _this.$message({
                showClose: true,
                message: '修改成功',
                type: 'success'
              })
              _this.$router.push('/admin/dish')
            }
          })
          // alert('submit!');
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    handleRemove (file, fileList) {
    },
    handlePreview (file) {
    },
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    handleSuccess (response) {
      this.url = response
      this.dishInfo.imgurl = response
      this.$message.warning('上传成功')
    },
    clear () {
      this.$refs.upload.clearFiles()
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    back (formName) {
      this.$refs[formName].resetFields()
      this.$router.push('/admin/dish')
    },
    getDishTypeList () {
      this.$axios.get('/dishType').then(resp => {
        if (resp && resp.status === 200) {
          this.options = resp.data.result.reverse()
        }
      })
    }
  }
}

</script>

<style scoped>

.dishType {
  /*left: 10px;*/
}

</style>
