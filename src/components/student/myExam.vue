// 我的试卷页面
<template>

  <div id="myExam">
    <div class="title" style="font-size: 24px;">
      <i class="iconfont" style="font-size: 32px;"></i>
      <h3 style="color: white;">考试中心</h3>
    </div>
    <div class="wrapper">
      <ul class="top">
        <li style="font-size: 20px;"><i style="font-size: 26px;margin-right: 10px;" class="iconfont icon-r-list"></i>
          试卷列表</li>
        <li class="search-li">
          <div class="icon">
            <input type="text" placeholder="试卷名称" class="search" v-model="key" /><i class="el-icon-search"></i>
          </div>
        </li>
        <li>
          <el-button type="primary" @click="search()"> 搜索试卷</el-button>
        </li>
      </ul>
      <ul class="paper" v-loading="loading">
        <li @click="toExamMsg(item)" class="item" v-for="(item, index) in pagination.records" :key="index">
          <div style="font-size: 22px;">
            <i class="iconfont" style="font-size: 28px;"></i>
            {{ item.source }}
          </div>
          <p class="name">{{ item.source }}-{{ item.description }}</p>
          <div class="info">
            <span class="iconfont icon-kaoshishijian">考试时间：{{ item.examDate.substr(0, 10) }}</span>
            <span class="iconfont icon-xianshi" v-if="item.totalTime != null">限时{{ item.totalTime
              }}分钟</span>
            <span>满分{{ item.totalScore }}分</span>
          </div>
        </li>
      </ul>
      <div class="pagination">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
          :current-page="pagination.current" :page-sizes="[6, 10, 20, 40]" :page-size="pagination.size"
          layout="total, sizes, prev, pager, next, jumper" :total="pagination.total" class="custom-pagination">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import '../../assets/css/iconfont.css';
export default {
  // name: 'myExam'
  data() {
    return {
      loading: false,
      key: null, //搜索关键字
      allExam: null, //所有考试信息
      pagination: {
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6, //每页条数
      },
    };
  },
  created() {
    this.getExamInfo();
    this.loading = true;
  },
  // watch: {

  // },
  methods: {
    //获取当前所有考试信息
    getExamInfo() {
      this.$axios(
        `/api/exams/${this.pagination.current}/${this.pagination.size}`
      )
        .then((res) => {
          this.pagination = res.data.data;
          this.loading = false;
          console.log(this.pagination);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getExamInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getExamInfo();
    },
    //搜索试卷
    search() {
      this.$axios("/api/exams").then((res) => {
        if (res.data.code == 200) {
          let allExam = res.data.data;
          let newPage = allExam.filter((item) => {
            return item.source.includes(this.key);
          });
          this.pagination.records = newPage;
        }
      });
    },
    //跳转到试卷详情页
    toExamMsg(exam) {
      if (exam.examDate > this.formatDateNow()) {
        this.$message({
          message: "考试时间未到",
          type: "error",
        });
        return;
      }
      this.$router.push({
        path: "/examMsg",
        query: { examCode: exam.examCode },
      });
    },
    formatDateNow() {
      var date = new Date(); // 当前日期时间
      var year = date.getFullYear(); // 年份
      var month = ("0" + (date.getMonth() + 1)).slice(-2); // 月份+1
      var day = ("0" + date.getDate()).slice(-2); // 日
      return year + "-" + month + "-" + day;
    },
  },
};
</script>

<style lang="less" scoped>
.custom-pagination {
  background-color: white;
  border-radius: 15px;
}



.iconfont.icon-kaoshishijian::before {
  color: rgb(81, 196, 245);
  /* 或者您想要的颜色 */
}

.iconfont.icon-xianshi::before {
  color: rgb(175, 82, 28);
}


.pagination {
  padding: 20px 0px 30px 0px;

  .el-pagination {
    display: flex;
    justify-content: center;
  }
}

.paper {
  h4 {
    cursor: pointer;
  }
}

.paper .item a {
  color: #000;
}

.wrapper .top .order {
  cursor: pointer;
}

.wrapper .top .order:hover {
  color: #0195ff;
  border-bottom: 2px solid #0195ff;
}

.wrapper .top .order:visited {
  color: #0195ff;
  border-bottom: 2px solid #0195ff;
}

.item .info i {
  margin-right: 5px;
  color: #0195ff;
}

.item .info span {
  margin-right: 14px;
}

.paper .item {
  width: 380px;
  border-radius: 4px;
  padding: 20px 30px;
  border: 1px solid #eee;
  box-shadow: 0 0 4px 2px rgba(217, 222, 234, 0.3);
  transition: all 0.6s ease;
  background-color: white;
  border-radius: 10px;
}

.item {
  cursor: pointer;
}

.paper .item:hover {
  box-shadow: 0 0 4px 2px rgba(140, 193, 248, 0.45);
  transform: scale(1.03);
}

.paper .item .info {
  font-size: 14px;
  color: #88949b;
}

.paper .item .name {
  font-size: 14px;
  color: #88949b;
}

.paper * {
  margin: 20px 0;
}

.wrapper .paper {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.top .el-icon-search {
  position: absolute;
  right: 10px;
  top: 10px;
}

.top .icon {
  position: relative;
}

.wrapper .top {
  border-bottom: 1px solid #eee;
  margin-bottom: 20px;
}

#myExam .search-li {
  margin-left: auto;
}

.top .search-li {
  margin-left: auto;
}

.top li {
  display: flex;
  align-items: center;
}

.top .search {
  margin-left: auto;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #eee;
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
}

.top .search:hover {
  color: #0195ff;
  border-color: #0195ff;
}

.wrapper .top {
  display: flex;
}

.wrapper .top li {
  margin: 20px;
}

#myExam {
  width: 980px;
  margin: 0 auto;
}

#myExam .title {
  margin: 20px;
}

#myExam .wrapper {
  background-color: #ffffffbd;
  border-radius: 20px;
}
</style>
