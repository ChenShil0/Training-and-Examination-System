// 学生管理页面
<template>
  <div class="all">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="condition.name" placeholder="姓名"></el-input>
      </el-form-item>
      <el-form-item label="学院">
        <el-input v-model="condition.institute" placeholder="学院"></el-input>
      </el-form-item>
      <el-form-item label="专业">
        <el-input v-model="condition.major" placeholder="专业"></el-input>
      </el-form-item>
      <el-form-item label="年级">
        <el-input v-model="condition.grade" placeholder="年级"></el-input>
      </el-form-item>
      <el-form-item label="班级">
        <el-input v-model="condition.clazz" placeholder="班级"></el-input>
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input v-model="condition.tel" placeholder="联系方式"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getStudentGrade"  style="font-size: 20px;"> 查询</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="pagination.records" border>
      <el-table-column prop="studentId" label="ID" width="120"></el-table-column>
      <el-table-column prop="studentName" label="姓名" width="120"></el-table-column>
      <el-table-column prop="institute" label="学院" width="120"></el-table-column>
      <el-table-column prop="major" label="专业" width="140"></el-table-column>
      <el-table-column prop="grade" label="年级" width="200"></el-table-column>
      <el-table-column prop="clazz" label="班级" width="100"></el-table-column>
      <el-table-column prop="sex" label="性别" width="120"></el-table-column>
      <el-table-column prop="tel" label="联系方式" width="240"></el-table-column>
      <el-table-column fixed="right" label="操作" width="260">
        <template slot-scope="scope">
          <el-button @click="checkGrade(scope.row.studentId)" type="primary" > 编辑</el-button>
          <el-button @click="deleteById(scope.row.studentId)" type="danger" > 删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[6, 10]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      class="page">
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑学生信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="姓名">
            <el-input v-model="form.studentName"></el-input>
          </el-form-item>
          <el-form-item label="学院">
            <el-input v-model="form.institute"></el-input>
          </el-form-item>
          <el-form-item label="专业">
            <el-input v-model="form.major"></el-input>
          </el-form-item>
          <el-form-item label="年级">
            <el-input v-model="form.grade"></el-input>
          </el-form-item>
          <el-form-item label="班级">
            <el-input v-model="form.clazz"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-input v-model="form.sex"></el-input>
          </el-form-item>
          <el-form-item label="电话号码">
            <el-input v-model="form.tel"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" style="font-size: 20px;"> 取 消</el-button>
        <el-button type="primary" @click="submit()"> 确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pagination: {
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6, //每页条数
      },
      dialogVisible: false, //对话框
      form: {}, //保存点击以后当前试卷的信息,
      condition: {
        name: "",
        tel: "",
        grade: "",
        clazz: "",
        major: "",
        institute: "",
      }
    };
  },
  created() {
    this.getStudentGrade();
  },
  methods: {
    getStudentGrade() {
      // 根据条件获取学生成绩
      var name = (this.condition.name.trim() == "" ? "@" : this.condition.name);
      var grade = (this.condition.grade.trim() == "" ? "@" : this.condition.grade);
      var tel = (this.condition.tel.trim() == "" ? "@" : this.condition.tel);
      var institute = (this.condition.institute.trim() == "" ? "@" : this.condition.institute);
      var major = (this.condition.major.trim() == "" ? "@" : this.condition.major);
      var clazz = (this.condition.clazz.trim() == "" ? "@" : this.condition.clazz);
      this.$axios(`/api/students/${this.pagination.current}/${this.pagination.size}/${name}/${grade}/${tel}/${institute}/${major}/${clazz}`).then(res => {
        this.pagination = res.data.data;
        console.log(this.pagination)
      }).catch(error => {});
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getStudentGrade();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getStudentGrade();
    },
    checkGrade(studentId) { //修改学生信息
      this.dialogVisible = true
      this.$axios(`/api/student/${studentId}`).then(res => {
        this.form = res.data.data
      })
    },
    deleteById(studentId) { //删除当前学生
      var _this = this;
      this.$confirm("确定删除当前学生吗？删除后无法恢复","Warning",{
        confirmButtonText: '确定删除',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(()=> { //确认删除
        this.$axios({
          url: `/api/student/${studentId}`,
          method: 'delete',
        }).then(res => {
          this.getStudentGrade()
        })
      }).catch(() => {

      })
    },
    submit() { //提交更改
      this.dialogVisible = false
      this.$axios({
        url: '/api/student',
        method: 'put',
        data: {
          ...this.form
        }
      }).then(res => {
        if(res.data.code ==200) {
          this.$message({
            message: '更新成功',
            type: 'success'
          })
        }
        this.getStudentGrade()
      })
    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {});
    },
  }
};
</script>
<style lang="less" scoped>
.all {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit {
    margin-left: 20px;
  }
  .el-table tr {
    background-color: #dd5862 !important;
  }
}
.el-table .warning-row {
  background: #000 !important;
}

.el-table .success-row {
  background: #dd5862;
}
</style>
