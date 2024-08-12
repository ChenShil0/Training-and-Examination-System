<!-- 添加考试 -->
<template>
  <section class="add">
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="介绍">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="form.source"></el-input>
      </el-form-item>
      <el-form-item label="开始日期">
        <el-col :span="11">
          <el-date-picker
            placeholder="选择日期"
            v-model="form.startDate"
            style="width: 100%"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="结束日期">
        <el-col :span="11">
          <el-date-picker
            placeholder="选择日期"
            v-model="form.endDate"
            style="width: 100%"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="年级">
        <el-input v-model="form.grade"></el-input>
      </el-form-item>
      <el-form-item label="学期">
        <el-input v-model="form.term"></el-input>
      </el-form-item>
      <el-form-item label="主修课程">
        <el-input v-model="form.major"></el-input>
      </el-form-item>
      <el-form-item label="学院">
        <el-input v-model="form.institute"></el-input>
      </el-form-item>
      <el-form-item label="总分">
        <el-input v-model="form.totalScore"></el-input>
      </el-form-item>
      <el-form-item label="类型">
        <el-input v-model="form.type"></el-input>
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
        description: null,
        source: null,
        trainingId: null,
        startDate: null,
        endDate: null,
        grade: null,
        term: null,
        major: null,
        institute: null,
        totalScore: null,
        type: null,
        tips: null,
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
      if (this.form.description == null || this.form.description == "") {
        this.$message({
          message: "介绍不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.source == null || this.form.source == "") {
        this.$message({
          message: "来源不能为空",
          type: "error",
        });
        return;
      }

      if (this.form.startDate == null || this.form.startDate == "") {
        this.$message({
          message: "开始时间不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.endDate == null || this.form.endDate == "") {
        this.$message({
          message: "结束时间不能为空",
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
      if (this.form.term == null || this.form.term == "") {
        this.$message({
          message: "学期不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.major == null || this.form.major == "") {
        this.$message({
          message: "主修课程不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.institute == null || this.form.institute == "") {
        this.$message({
          message: "学院不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.totalScore == null || this.form.totalScore == "") {
        this.$message({
          message: "总分不能为空",
          type: "error",
        });
        return;
      }
      if (this.form.type == null || this.form.type == "") {
        this.$message({
          message: "类型不能为空",
          type: "error",
        });
        return;
      }
      this.$axios(`/api/practicalTrainingId`).then((res) => {
        console.log(res);
        if (res.data.data == null || res.data.data.trainingId == null) {
          res.data.data = {
            trainingId: 1000,
          };
        }
        this.form.trainingId = res.data.data.trainingId + 1; //实现paperId自增1
        this.$axios({
          url: "/api/practical",
          method: "post",
          data: {
            ...this.form,
          },
        }).then((res) => {
          console.log(res);
          if (res.data.code == 200) {
            this.$message({
              message: "数据添加成功",
              type: "success",
            });
            this.$router.push({ path: "/selectPractical" });
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
  
  