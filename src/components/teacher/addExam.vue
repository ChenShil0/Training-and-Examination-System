<!-- 添加考试 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="试卷名称">
        <el-input v-model="form.source"></el-input>
      </el-form-item>
      <el-form-item label="介绍">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
      <el-form-item label="所属学院">
        <el-input v-model="form.institute"></el-input>
      </el-form-item>
      <el-form-item label="所属专业">
        <el-input v-model="form.major"></el-input>
      </el-form-item>
      <el-form-item label="年级">
        <el-input v-model="form.grade"></el-input>
      </el-form-item>
      <el-form-item label="考试日期">
        <el-col :span="11">
          <el-date-picker
            placeholder="选择日期"
            v-model="form.examDate"
            style="width: 100%"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="持续时间（分钟）">
        <el-input
          v-model="form.totalTime"
          type="number"
          oninput="if(value<=0)value=''"
          :min="1"
        ></el-input>
      </el-form-item>
      <el-form-item label="考试类型">
        <el-input v-model="form.type"></el-input>
      </el-form-item>
      <el-form-item label="考生提示">
        <el-input type="textarea" v-model="form.tips"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit()"> 立即创建</el-button>
        <el-button type="danger" @click="cancel()"> 取消</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>

<script>
export default {
  data() {
    return {
      form: {
        //表单数据初始化
        source: null,
        description: null,
        institute: null,
        major: null,
        grade: null,
        examDate: null,
        totalTime: null,
        totalScore: null,
        type: null,
        tips: null,
        paperId: null,
      },
    };
  },
  methods: {
    formatTime(date) {
      //日期格式化
      let year = date.getFullYear();
      let month =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours =
        date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes =
        date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds =
        date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return (
        year +
        "-" +
        month +
        "-" +
        day +
        " " +
        hours +
        ":" +
        minutes +
        ":" +
        seconds
      );
    },
    onSubmit() {
      if (this.form.source == null || this.form.source == "") {
        this.$message({
          message: "试卷名称不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.description == null || this.form.description == "") {
        this.$message({
          message: "介绍不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.institute == null || this.form.institute == "") {
        this.$message({
          message: "所属学院不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.major == null || this.form.major == "") {
        this.$message({
          message: "所属专业不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.grade == null || this.form.grade == "") {
        this.$message({
          message: "年级不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.examDate == null || this.form.examDate == "") {
        this.$message({
          message: "考试日期不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.totalTime == null || this.form.totalTime == "") {
        this.$message({
          message: "持续时间不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.type == null || this.form.type == "") {
        this.$message({
          message: "考试类型不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.tips == null || this.form.tips == "") {
        this.$message({
          message: "考生提示不能为空",
          type: "error",
        });
        return;
      }
      this.$axios(`/api/examManagePaperId`).then((res) => {
        console.log(res);
        if (res.data.data == null || res.data.data.paperId == null) {
          res.data.data = {
            paperId: 1000,
          };
        }
        this.form.paperId = res.data.data.paperId + 1; //实现paperId自增1
        this.$axios({
          url: "/api/exam",
          method: "post",
          data: {
            ...this.form,
          },
        }).then((res) => {
          if (res.data.code == 200) {
            this.$message({
              message: "数据添加成功",
              type: "success",
            });
            this.$router.push({ path: "/selectExam" });
          }
        });
      });
    },
    cancel() {
      //取消按钮
      this.form = {};
    },
  },
};
</script>
<style lang="less" scoped>
.add {
  padding: 0px 40px;
  width: 400px;
}
</style>

