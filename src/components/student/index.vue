<!--学生考试首页-->

<template>
  <div id="student">
    <el-row class="padding-50">
      <el-col :span="24">
        <ul class="list">
          <img
            src="@/assets/033.png"
            style="
              width: 40px;
              height: 40px;
              margin: 13px -15px 0 10px;
              -webkit-user-drag: none;
              -khtml-user-drag: none;
              -moz-user-drag: none;
              user-drag: none;
            "
          />

          <li class="logo"><span>校园智能实训系统</span></li>
          <li @click="practical()"><a href="javascript:;">实训中心</a></li>
          <li @click="exam()"><a href="javascript:;">考试中心</a></li>
          <li @click="practice()">
            <a href="javascript:;">试卷练习</a>
          </li>
          <li>
            <router-link to="/scoreTable">我的分数</router-link>
          </li>
          <li>
            <router-link to="/message">交流区</router-link>
          </li>
          <li>
            <router-link to="/wrong?">错题本</router-link>
          </li>

          <li
            class="right"
            @mouseenter="flag = !flag"
            @mouseleave="flag = !flag"
          >
            <el-row :gutter="24">
              <el-col :span="8" style="text-align: left">
                <el-avatar
                  shape="square"
                  :size="50"
                  :src="require('@/assets/img/avatar.jpg')"
                  style="margin: 5px 5px 0 0"
                ></el-avatar>
              </el-col>
              <el-col :span="16">
                <a href="javascript:;" style="font-size: 22px; font-weight: 600"
                  ><i class="ren"></i>{{ user.userName }}</a
                >
                <div class="msg" v-if="flag">
                  <p @click="manage()">修改密码</p>
                  <p class="exit" @click="exit()">退出</p>
                </div>
              </el-col>
            </el-row>
          </li>
        </ul>
      </el-col>
    </el-row>
    <!--路由区域-->
    <div class="main">
      <router-view></router-view>
    </div>
    <v-footer></v-footer>
  </div>
</template>

<script>
import myFooter from "@/components/student/myFooter";
import { mapState } from "vuex";
//import { flowerDrops } from '../../../src/assets/js/yinghua'
import VueParticles from "vue-particles";
export default {
  components: {
    "v-footer": myFooter,
  },
  data() {
    return {
      flag: false,
      user: {},
    };
  },
  created() {
    this.userInfo();
  },
  methods: {
    exit() {
      //退出登录
      this.$router.push({ path: "/" }); //跳转到登录页面
      this.$cookies.remove("cname"); //清除cookie
      this.$cookies.remove("cid");
      this.$cookies.remove("rb_token"); //清除cookie
      this.$cookies.remove("rb_role");
    },
    manage() {
      //跳转到修改密码页面
      this.$router.push({ path: "/manager" });
    },
    userInfo() {
      let studentName = this.$cookies.get("cname");
      let studentId = this.$cookies.get("cid");
      console.log(`studentId${studentId}`);
      console.log(`studentName ${studentName}`);
      this.user.userName = studentName;
      this.user.studentId = studentId;
    },
    practice() {
      //跳转练习模式
      let isPractice = true;
      this.$store.commit("practice", isPractice);
      this.$router.push({ path: "/startExam" });
    },
    exam() {
      //跳转考试模式
      let isPractice = false;
      this.$store.commit("practice", isPractice);
      this.$router.push({ path: "/student" });
    },
    practical() {
      //跳转考试模式
      let isPractice = false;
      this.$store.commit("practice", isPractice);
      this.$router.push({ path: "/myPractical" });
    },
  },
  computed: mapState(["isPractice"]),
};
</script>

<style scoped>
.ren {
  margin: 5px;
}

#student .padding-50 {
  margin: 0 auto;
  padding: 0 50px;
  box-shadow: 0 0 10px 4px rgba(1, 149, 255, 0.1);
  background-color: #ffffff69;
}

#student {
  background: url("../../assets/img/concrete-texture.jpg") center top / 100%
    auto no-repeat;
  background-size: cover;
  left: 0;
  top: 0;
  /* 向上移动10像素 */
}

.list a {
  text-decoration: none;
  color: #334046;
  font-size: 18px;
}

li {
  list-style: none;
  height: 60px;
  line-height: 60px;
}

#student .list {
  display: flex;
}

#student .list li {
  padding: 0 20px;
  cursor: pointer;
}

#student .list li:hover {
  /* background-color: #0195ff; */
  transition: all 2s ease;
}

#student .list li:hover a {
  color: #0195ff;
}

#student .list .right {
  margin-left: auto;
  position: relative;
}

#student .list li.right :hover a {
  color: #000;
}

#student .list .logo {
  display: flex;
  font-weight: bold;
  color: black;
  font-size: 28px;
}

#student .list .logo i {
  font-size: 50px;
}

.right .msg {
  text-align: center;
  position: absolute;
  top: 60px;
  left: 0px;
  display: flex;
  flex-direction: column;
  border-radius: 2px;
  border-bottom: 3px solid #0195ff;
  background-color: #fff;
}

.right .msg p {
  height: 40px;
  line-height: 40px;
  width: 105px;
}

.right .msg p:hover {
  background-color: #0195ff;
}
</style>
