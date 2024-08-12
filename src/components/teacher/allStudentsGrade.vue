// 所有学生
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
        <el-button type="primary" @click="getStudentGrade" style="font-size: 20px;"> 查询</el-button>
        <el-button type="primary" @click="StudentGrade" style="font-size: 20px;"> 导出成绩</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="studentName" label="姓名" width="200"></el-table-column>
      <el-table-column prop="institute" label="学院" width="220"></el-table-column>
      <el-table-column prop="major" label="专业" width="240"></el-table-column>
      <el-table-column prop="grade" label="年级" width="200"></el-table-column>
      <el-table-column prop="clazz" label="班级" width="120"></el-table-column>
      <el-table-column prop="sex" label="性别" width="120"></el-table-column>
      <el-table-column prop="tel" label="联系方式" width="160"></el-table-column>
      <el-table-column fixed="right" label="查看成绩" width="180">
        <template slot-scope="scope">
          <el-button @click="checkGrade(scope.row.studentId)" type="primary"> 查看成绩</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current-page="pagination.current" :page-sizes="[6, 10]" :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper" :total="pagination.total" class="page"></el-pagination>
  </div>
</template>


<script>
import xlsx from "xlsx-js-style";
import XLSX from "xlsx";
export default {
  data() {
    return {
      pagination: {
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6 //每页条数
      },
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
      }).catch(error => { });

    },
    StudentGrade() {
      this.$axios(`/api/scores`).then(res => {
        let data = res.data.data;
        let sources = data.filter(item => item.studentId);
        let sourceValues = [...new Set(sources.map(item => item.studentId))];

        // 用于存储合并后的数据
        let mergedData = [];

        // 创建一个 Promise 数组，等待所有学生详细信息请求完成
        let promises = sourceValues.map((studentId) => {
          return this.$axios(`/api/student/${studentId}`).then(res => {
            let sdata = res.data.data;
            return sdata;
          });
        });

        // 等待所有 Promise 完成
        Promise.all(promises).then(sdataArray => {
          // 合并数据
          sdataArray.forEach(sdata => {
            let studentScores = data.filter(d => d.studentId === sdata.studentId);
            studentScores.forEach(score => {
              mergedData.push({
                studentName: sdata.studentName,
                studentId: sdata.studentId,
                institute: sdata.institute,
                major: sdata.major,
                grade: sdata.grade,
                clazz: sdata.clazz,
                subjectId: score.examCode,
                subjectName: score.subject,
                score: score.etScore
              });
            });
          });

          // 将数据源转成我们需要的二维数组
          const body = mergedData.map(x => ([x.studentName, x.studentId, x.institute, x.major, x.grade, x.clazz, x.subjectId, x.subjectName, x.score]));

          const header = [
            ['学生姓名', '学号', '学院', '专业', '年级', '班级', '科目编号', '科目名称', '分数']
          ];

          body.unshift(...header); // 将定义好的表头添加到 body 中
          const sheet = xlsx.utils.aoa_to_sheet(body); // aoa_to_sheet 将二维数组转成 sheet

          // 设置列宽
          const cols = [{ wch: 10 }, { wch: 10 }, { wch: 15 }, { wch: 15 }, { wch: 10 }, { wch: 10 }, { wch: 15 }, { wch: 15 }, { wch: 10 }];
          sheet['!cols'] = cols;

          // 设置行高
          const rows = [{ hpx: 20 }, { hpx: 16 }, { hpx: 18 }];
          sheet['!rows'] = rows;

          // 创建工作簿并导出
          const workbook = xlsx.utils.book_new();
          xlsx.utils.book_append_sheet(workbook, sheet, '成绩');
          xlsx.writeFile(workbook, '学生成绩单.xlsx');
        }).catch(error => {
          console.error('Error fetching student data:', error);
        });
      }).catch(error => {
        console.error('Error fetching scores:', error);
      });
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
    checkGrade(studentId) {
      this.$router.push({ path: "/grade", query: { studentId: studentId } });
    }
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
