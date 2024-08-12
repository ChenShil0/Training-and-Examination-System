<template>
  <div id="message">

    <div class="title" style="font-size: 24px;">
      <i class="iconfont icon-r-refresh" style="font-size: 30px;"></i>

      <h3 style="color: white;">错题本</h3>

    </div>
    <div class="wrapper">
      <div class="all">
        <ul class="msglist">
          <li v-for="(item, index) in items" :key="index" class="list">
            <p style="font-size: 20px;">{{ item.subject }}</p>
            <p class="content">{{ item.question }}</p><br>
            <p class="comment" v-if="item.answerA">A:{{ item.answerA }}</p>
            <p class="comment" v-if="item.answerB">B:{{ item.answerB }}</p>
            <p class="comment" v-if="item.answerC">C:{{ item.answerC }}</p>
            <p class="comment" v-if="item.answerD">D:{{ item.answerD }}</p>
            <p style="color: red;" v-if="item.rightAnswer">答案：{{ item.rightAnswer }}</p>
            <p style="color: red;" v-if="item.answer">答案：{{ item.answer }}</p>

          </li>

        </ul>

      </div>

    </div>
  </div>
</template>
<script>
export default {

  // name: 'message'
  data() {
    return {
      flag: false,
      current: 0,
      title: "",
      content: "",
      items: [],// 用于存储从服务器获取的数据
      wrongmultiset: new Set(), // 确保在创建时已经创建了一个Set
      wrongfillset: new Set(),
      wrongjudgeset: new Set(),

    }
  },
  created() {
    this.getwrongid()
  },
  // watch: {

  // },
  methods: {


    getwrongid() {
      let studentId = this.$cookies.get("cid")
      this.$axios(`/api/student/${studentId}`).then(res => {
        let status = res.data.code
        if (status == 200) {
          // 使用split方法分割字符串
          let wrongmultia = res.data.data.wrongmulti.split(",");
          let wrongfilla = res.data.data.wrongfill.split(",");
          let wrongjudgea = res.data.data.wrongjudge.split(",");

          // 使用map方法将字符串数组转换为数字数组
          let numbersmultia = wrongmultia.map(Number);
          let numbersfilla = wrongfilla.map(Number);
          let numbersjudgea = wrongjudgea.map(Number);

          // 使用filter方法排除数字0
          let nonZeromultia = numbersmultia.filter(num => num !== 0);
          let nonZerofilla = numbersfilla.filter(num => num !== 0);
          let nonZerojudgea = numbersjudgea.filter(num => num !== 0);

          // 使用Set构造函数将过滤后的数组转换为一个Set
          let multiset = new Set(nonZeromultia);
          let fillset = new Set(nonZerofilla);
          let judgeset = new Set(nonZerojudgea);

          //数据外置
          this.wrongmultiset = multiset;
          this.wrongfillset = fillset;
          this.wrongjudgeset = judgeset;


          this.wrongmultiset.forEach((QuestionId) => {
            console.log(QuestionId)
            this.$axios(`/api/multiQuestionId/${QuestionId}`).then(res => {
              let status = res.data.code
              if (status == 200) {
                this.items.push(res.data.data);
              }
            })
          })

          this.wrongfillset.forEach((QuestionId) => {
            console.log(QuestionId)
            this.$axios(`/api/fillQuestionId/${QuestionId}`).then(res => {
              let status = res.data.code
              if (status == 200) {
                this.items.push(res.data.data);
              }
            })
          })

          this.wrongjudgeset.forEach((QuestionId) => {
            console.log(QuestionId)
            this.$axios(`/api/judgeQuestionId/${QuestionId}`).then(res => {
              let status = res.data.code
              if (status == 200) {
                this.items.push(res.data.data);
              }
            })
          })

        }
      })
    },

  }
}
</script>

<style lang="less" scoped>
nav {
  background: #fff;
  border-radius: 50px;
  padding: 10px;
  box-shadow: 0 25px 20px -20px rgba(0, 0, 0, 0.4);
}

nav ul li {
  list-style: none;
  display: inline-block;
  padding: 13px 35px;
  margin: 10px;
  font-size: 18px;
  font-weight: 500;
  color: #777;
  cursor: pointer;
  position: relative;
  z-index: 2;
  transition: color 0.5s;

}

nav ul li::after {
  content: '';
  background: #f44566;
  width: 100%;
  height: 100%;
  border-radius: 30px;
  position: absolute;
  top: 100%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: -1;
  opacity: 0;
  transition: top 0.5s, opacity 0.5s;
}

nav ul li:hover {
  color: #fff;
}

nav ul li:hover::after {
  top: 50%;
  opacity: 1;
}

nav {
  text-align: center;
}

.custom-pagination {
  background-color: white;
  border-radius: 15px;
}

.html {
  height: auto;
}

.pagination {
  display: flex;
  justify-content: center;
}

#message {
  width: 980px;
  margin: 0 auto;
  height: 92vh
}

.title {
  margin: 20px;
}

.content {
  padding: 20px 0px;
}

#message {
  .btn {
    padding-bottom: 20px;
  }

  .all {
    .date {
      color: rgb(80, 157, 202);
      line-height: 45px;
      font-size: 13px;
    }

    .list {
      background-color: #ffffff;
      padding: 10px;
      border-radius: 4px;
      margin: 10px 0px;
      position: relative;
      transition: all .3s ease;

      .title {
        color: #000000;
        margin: 0px;
        font-size: 13px;
        line-height: 30px;
      }

      .content {
        padding: 0px;
      }

      .icon-untitled33 {
        font-size: 13px;
        margin-right: 4px;
      }

      .icon-date {
        font-size: 13px;
        margin-right: 4px;
        color: rgb(80, 157, 202);
      }

      .replay {
        position: absolute;
        right: 30px;
        bottom: 15px;
        color: tomato;
        cursor: pointer;
        transition: all .3s ease;
      }

      .comment {
        margin: -7px 0px;
        padding-bottom: 12px;
        font-size: 13px;
        color: #28b2b4;

        i {
          margin-right: 4px;
        }
      }
    }
  }
}

#message .wrapper {
  background-color: #ffffffbd;
  border-radius: 20px;
  padding: 20px;
  height: 80%;
  /* 高度可以根据需要调整 */
  overflow: auto;
  /* 添加滚动条 */
  border: 1px solid #ccc;
  /* 添加边框以示区分 */
}
</style>
