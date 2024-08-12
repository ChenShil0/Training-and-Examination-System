<!-- 用户登录界面 -->
<template>
  <div id="login">
    <div class="bg"></div>
    <el-row class="main-container">
      <el-col :lg="8" :xs="16" :md="10" :span="10">
        <div class="top" style="color: black; font-size: 32px">
          <i class="iconfont icon-kaoshi"></i
          ><span class="title">校园智能实训系统</span>
        </div>
        <br />

        <div class="container">
          <div class="login-box">
            <h2>账号登录</h2>
            <form>
              <div class="user-box">
                <input
                  type="text"
                  name=""
                  required=""
                  v-model.number="formLabelAlign.username"
                />
                <label>用户名</label>
              </div>
              <div class="user-box">
                <input
                  type="password"
                  name=""
                  required=""
                  v-model="formLabelAlign.password"
                />
                <label>密码</label>
              </div>
              <div class="button-container" type="success" @click="login()">
                <a href="#"> 登录 </a>
              </div>
            </form>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row class="footer">
      <el-col> </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "login",
  data() {
    return {
      role: 2,
      labelPosition: "left",
      formLabelAlign: {
        username: "",
        password: "",
      },
    };
  },
  methods: {
    //用户登录请求后台处理
    login() {
      if (
        this.formLabelAlign.username == undefined ||
        this.formLabelAlign.username == ""
      ) {
        this.$message("请输入用户名");
        return;
      }
      if (
        !/^\d+$/.test(this.formLabelAlign.username) ||
        this.formLabelAlign.username.toString().length > 10
      ) {
        this.$message("用户名有误");
        return;
      }
      if (this.formLabelAlign.password == "") {
        this.$message("请输入密码");
        return;
      }
      this.$axios({
        url: `/api/login`,
        method: "post",
        data: {
          ...this.formLabelAlign,
        },
      })
        .then((res) => {
          let resData = res.data.data;
          if (resData != null) {
            switch (resData.role) {
              case "0": //管理员
                this.$cookies.set("cname", resData.adminName);
                this.$cookies.set("cid", resData.adminId);
                this.$cookies.set("role", 0);
                this.$router.push({ path: "/index" }); //跳转到首页
                break;
              case "1": //教师
                this.$cookies.set("cname", resData.teacherName);
                this.$cookies.set("cid", resData.teacherId);
                this.$cookies.set("role", 1);
                this.$router.push({ path: "/index" }); //跳转到教师用户
                break;
              case "2": //学生
                this.$cookies.set("cname", resData.studentName);
                this.$cookies.set("cid", resData.studentId);
                this.$router.push({ path: "/student" });
                break;
            }
          }
          if (resData == null) {
            //错误提示
            this.$message({
              showClose: true,
              type: "error",
              message: "用户名或者密码错误",
            });
          }
        })
        .catch((e) => {
          console.log(e);
          if (e.response == undefined || e.response.data == undefined) {
            this.$message({
              showClose: true,
              message: e,
              type: "error",
              duration: 5000,
            });
          } else {
            this.$message({
              showClose: true,
              message: e.response.data,
              type: "error",
              duration: 5000,
            });
          }
        });
    },
    clickTag(key) {
      this.role = key;
    },
  },
  computed: mapState(["userInfo"]),
  mounted() {},
};
</script>

<style lang="less" scoped>
.remind {
  border-radius: 4px;
  padding: 10px 20px;
  display: flex;
  position: fixed;
  right: 20px;
  bottom: 50%;
  flex-direction: column;
  color: #000000;
  background-color: #fff;
  border-left: 4px solid #409eff;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}

.container {
  margin-bottom: 32px;
}

.container .el-radio-group {
  margin: 30px 0px;
}

a:link {
  color: #ff962a;
  text-decoration: none;
}

#login {
  font-size: 14px;
  color: #000;
  background-color: #fff;
}

#login .bg {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  overflow-y: auto;
  height: 100%;
  background: url("../../assets/img/loginbg.png") center top / cover no-repeat;
}

#login .main-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

#login .main-container .top {
  margin-top: 100px;
  font-size: 30px;
  color: #ff962a;
  display: flex;
  justify-content: center;
}

#login .top .icon-kaoshi {
  font-size: 80px;
}

#login .top .title {
  margin-top: 20px;
}

#login .bottom {
  display: flex;
  justify-content: center;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}

#login .bottom .title {
  text-align: center;
  font-size: 30px;
}

.bottom .container .title {
  margin: 30px 0px;
}

.bottom .submit .row-login {
  width: 100%;
  background-color: #04468b;
  border-color: #04468b;
  margin: 20px 0px 10px 0px;
  padding: 15px 20px;
}

.bottom .submit {
  display: flex;
  justify-content: center;
}

.footer {
  margin-top: 50px;
  text-align: center;
}

.footer .msg1 {
  font-size: 18px;
  color: #fff;
  margin-bottom: 15px;
}

.footer .msg2 {
  font-size: 14px;
  color: #e3e3e3;
  margin-top: 120px;
}

.bottom .options {
  margin-bottom: 40px;
  color: #ff962a;
  display: flex;
  justify-content: space-between;
}

.bottom .options > a {
  color: #ff962a;
}

.bottom .options .register span:nth-child(1) {
  color: #8c8c8c;
}

.login-box {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 400px;
  padding: 40px;
  transform: translate(-50%, 50%);
  background: rgba(0, 0, 0, 0.5);
  box-sizing: border-box;
  box-shadow: 0 15px 25px rgba(0, 0, 0, 0.6);
  border-radius: 10px;
}

.login-box h2 {
  margin: 0 0 30px;
  padding: 0;
  color: #fff;
  text-align: center;
}

.login-box .user-box {
  position: relative;
}

.login-box .user-box input {
  width: 100%;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  margin-bottom: 30px;
  border: none;
  border-bottom: 1px solid #fff;
  outline: none;
  background: transparent;
}

.login-box .user-box label {
  position: absolute;
  top: 0;
  left: 0;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  pointer-events: none;
  transition: 0.5s;
}

.login-box .user-box input:focus ~ label,
.login-box .user-box input:valid ~ label {
  top: -20px;
  left: 0;
  color: #03e9f4;
  font-size: 12px;
}

.login-box form a {
  position: relative;
  display: inline-block;
  padding: 10px 20px;
  color: #03e9f4;
  font-size: 16px;
  text-decoration: none;
  text-transform: uppercase;
  overflow: hidden;
  transition: 0.5s;
  margin-top: 40px;
  letter-spacing: 4px;
}

.login-box a:hover {
  background: #03e9f4;
  color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 5px #03e9f4, 0 0 25px #03e9f4, 0 0 50px #03e9f4,
    0 0 100px #03e9f4;
}

.login-box a span {
  position: absolute;
  display: block;
}

.login-box a span:nth-child(1) {
  top: 0;
  left: -100%;
  width: 100%;
  height: 2px;
  background: linear-gradient(90deg, transparent, #03e9f4);
  animation: btn-anim1 1s linear infinite;
}

@keyframes btn-anim1 {
  0% {
    left: -100%;
  }

  50%,
  100% {
    left: 100%;
  }
}

.login-box a span:nth-child(2) {
  top: -100%;
  right: 0;
  width: 2px;
  height: 100%;
  background: linear-gradient(180deg, transparent, #03e9f4);
  animation: btn-anim2 1s linear infinite;
  animation-delay: 0.25s;
}

@keyframes btn-anim2 {
  0% {
    top: -100%;
  }

  50%,
  100% {
    top: 100%;
  }
}

.login-box a span:nth-child(3) {
  bottom: 0;
  right: -100%;
  width: 100%;
  height: 2px;
  background: linear-gradient(270deg, transparent, #03e9f4);
  animation: btn-anim3 1s linear infinite;
  animation-delay: 0.5s;
}

@keyframes btn-anim3 {
  0% {
    right: -100%;
  }

  50%,
  100% {
    right: 100%;
  }
}

.login-box a span:nth-child(4) {
  bottom: -100%;
  left: 0;
  width: 2px;
  height: 100%;
  background: linear-gradient(360deg, transparent, #03e9f4);
  animation: btn-anim4 1s linear infinite;
  animation-delay: 0.75s;
}

@keyframes btn-anim4 {
  0% {
    bottom: -100%;
  }

  50%,
  100% {
    bottom: 100%;
  }
}

.button-container {
  display: flex;
  margin-top: 20px;
  justify-content: center;
}
</style>
