<template>
  <div id="msg">
    <div class="title">
      <span :style="{ fontWeight: 'bold', fontSize: '25px' }">实训列表</span>
      <span :style="{ fontWeight: 'bold', fontSize: '25px' }"
        >/ {{ practicalData.source }}</span
      >
    </div>
    <div class="wrapper">
      <ul class="top">
        <li class="example">{{ practicalData.source }}</li>
        <li><i class="iconfont icon-pen-"></i></li>
        <li><i class="iconfont icon-share"></i></li>
        <li class="right">
          <div>
            <span class="count">总分</span>
            <span class="score">{{ practicalData.totalScore }}</span>
          </div>
        </li>
      </ul>
      <ul class="bottom">
        <li>更新于{{ practicalData.practicalDate }}</li>
        <li>来自 {{ practicalData.institute }}</li>
        <li class="btn">{{ practicalData.type }}</li>
        <li class="right">
          <el-button
            style="font-size: 22px"
             @click="startTraining"
            >开始实训</el-button
          >
        </li>
      </ul>
      <ul class="info">
        <li @click="dialogVisible = true">
          <a href="javascript:;"><i class="iconfont"></i>实训须知</a>
        </li>
      </ul>
    </div>
    <div class="content">
      <el-collapse v-model="activeName">
        <el-collapse-item v-for="(item, index) in train" :key="index">
          <template v-slot:title>
            <div class="titlei">{{ item.title }}</div>
          </template>
          <!-- 这里可以添加更多信息 -->
          <!-- <div>Training ID: {{ item.id }}</div>-->
          <div class="right">
            <el-button type="primary" @click="openPDF(item.id)">查看</el-button>
            <el-button type="success" @click="downloadPDF(item.id)"
              >下载</el-button
            >
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
    <!--实训须知对话框-->
    <el-dialog title="实训须知" :visible.sync="dialogVisible" width="30%">
      <span>虚拟机账号：root，密码：Sx123456</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false"> 确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>
  
  <script>
export default {
  data() {
    return {
      dialogVisible: false, //对话框属性
      activeName: "0", //默认打开序号
      topicCount: [], //每种类型题目的总数
      score: [], //每种类型分数的总数
      practicalData: {
        source: "",
        practicalDate: "",
        institute: "",
        type: "",
        practicalCode: "",
        totalScore: "",
        // 确保所有可能用到的属性都进行了初始化
      },
      train: {}, //实训题目信息
    };
  },
  mounted() {
    this.init();
  },

  methods: {
    init() {
      let practicalCode = this.$route.query.practicalCode;
      this.$axios(`/api/practical/${practicalCode}`)
        .then((res) => {
          this.practicalData = res.data.data;
          this.practicalData.practicalDate =
            this.practicalData.practicalDate.substr(0, 10);
        })
        .catch((error) => {
          console.error("Failed to fetch practical data:", error);
        });

      this.$axios(`/api/trains/${practicalCode}`)
        .then((res) => {
          this.train = res.data.data; // 确保这里直接赋值返回的数组
        })
        .catch((error) => {
          console.error("Failed to fetch train data:", error);
        });
    },
    openPDF(id) {
      // 使用 Axios 请求后端接口获取 PDF 文件的 URL
      this.$axios(`/api/files/${id}`)
        .then((response) => {
          console.log(response);
          const relativePath = response.data.data.path; // 假设服务器返回的响应中包含文件的相对路径
          console.log(relativePath);
          const fullPath = `C:/Users/22146/Desktop/${relativePath}`;
          console.log(fullPath); // 输出完整路径到控制台

          // 使用 Local Explorer 插件打开本地文件
          window.open(`localexplorer://${fullPath}`, "_blank");
        })
        .catch((error) => {
          this.$message.error("Failed to load PDF file");
          console.error("Error loading PDF file:", error);
        });
    },
    downloadPDF(id) {
      // 使用 Axios 请求后端接口下载 PDF 文件
      this.$axios({
        url: `/api/download/${id}`,
        method: "GET",
        responseType: "blob", // 必须指定 blob 响应类型
      })
        .then((response) => {
          const url = window.URL.createObjectURL(new Blob([response.data]));
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", "file.pdf"); // 设置下载文件名
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
        })
        .catch((error) => {
          this.$message.error("Failed to download PDF file");
          console.error("Error downloading PDF file:", error);
        });
    },
    toPractice(id) {
      if (this.$store.state.isPractice) {
        this.$confirm(
          `将开始练习题库中所有科目为【${this.practicalData.source}】的题目`,
          "友情提示",
          {
            confirmButtonText: "确认开始",
            cancelButtonText: "取消",
            type: "warning",
          }
        )
          .then(() => {
            this.$router.push({
              path: "/practice",
              query: { practicalCode: id },
            });
          })
          .catch(() => {});
      } else {
        this.$router.push({ path: "/practice", query: { practicalCode: id } });
      }
    },
    startTraining() {
      window.location.href = 'http://8.130.112.171:6080/vnc.html';
    },

    toPractice(id) {
      if (this.$store.state.isPractice) {
        this.$confirm(
          `将开始练习题库中所有科目为【${this.practicalData.source}】的题目`,
          "友情提示",
          {
            confirmButtonText: "确认开始",
            cancelButtonText: "取消",
            type: "warning",
          }
        )
          .then(() => {
            this.$router.push({
              path: "/practice",
              query: { practicalCode: id },
            });
          })
          .catch(() => {});
      } else {
        this.$router.push({ path: "/practice", query: { practicalCode: id } });
      }
    },
  },
};
</script>

<style lang="less" scoped>
.bottom {
  .right {
    .el-button {
      color: #409eff;
      border-color: #c6e2ff;
      background-color: #ecf5ff;
    }
  }
}

.right {
  margin-left: auto;
}

.inner .contenti .question {
  margin-left: 40px;
  color: #9a9a9a;
  font-size: 14px;
}

.content .inner .titlei {
  margin-left: 20px;
  font-size: 16px;
  color: #88949b;
  font-weight: bold;
}
.titlei {
  margin-left: 20px;
  font-size: 16px;
  color: #88949b;
  font-weight: bold;
}

.content .title .time {
  font-size: 16px;
  margin-left: 420px;
  color: #999;
}

.content .stitle {
  background-color: #0195ff;
}

.content .title span {
  margin-right: 10px;
}

#msg .content .title {
  font-size: 20px;
  margin: 0px;
  display: flex;
  align-items: center;
}

.content {
  margin-top: 20px;
  background-color: #fff;
}

.content .header {
  padding: 10px 30px;
}

.wrapper .info {
  margin: 20px 0px 0px 20px;
  border-top: 1px solid #eee;
  padding: 20px 0px 10px 0px;
}

.wrapper .info a {
  color: #88949b;
  font-size: 14px;
}

.wrapper .info a:hover {
  color: #0195ff;
}

.wrapper .bottom .btn {
  cursor: pointer;
  padding: 5px 10px;
  border: 1px solid #88949b;
  border-radius: 4px;
}

.wrapper .bottom {
  display: flex;
  margin-left: 20px;
  color: #999;
  font-size: 14px;
  align-items: center;
}

.wrapper .bottom li {
  margin-right: 14px;
}

#msg {
  background-color: #eee;
  width: 980px;
  margin: 0 auto;
}

#msg .title {
  margin: 20px;
}

#msg .wrapper {
  background-color: #fff;
  padding: 10px;
}

.wrapper .top {
  display: flex;
  margin: 20px;
  align-items: center;
}

.wrapper .top .right {
  margin-left: auto;
}

.wrapper .top .example {
  color: #333;
  font-size: 22px;
  font-weight: 700;
}

.wrapper .top li i {
  margin-left: 20px;
  color: #88949b;
}

.wrapper .right .count {
  margin-right: 60px;
  color: #fff;
  padding: 4px 10px;
  background-color: #88949b;
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
  border: 1px solid #88949b;
}

.wrapper .right .score {
  position: absolute;
  left: 53px;
  top: -5px;
  padding: 1px 12px;
  font-size: 20px;
  color: #88949b;
  border: 1px dashed #88949b;
  border-left: none;
  border-top-right-radius: 4px;
  border-bottom-right-radius: 4px;
  font-weight: bold;
}

.wrapper .right div {
  position: relative;
}
</style>
