<template>
  <div class="part">
    <div class="charts-container">

      <div class="echart-box" ref="box1"></div>
      <div class="box" ref="box"></div>

    </div>
    <div v-if="isNull">
      <span>该门考试还没人参考哦,请提醒学生参加考试。</span>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isNull: false, // 是否有成绩标志位
      name: null,
      category: { // 保存分数段
        '90分及以上': 0,
        '80-89分': 0,
        '70-79分': 0,
        '60-69分': 0,
        '60分以下': 0,
      },
      categorys: { // 保存分数段
        '90分及以上': 0,
        '80-89分': 0,
        '70-79分': 0,
        '60-69分': 0,
        '60分以下': 0,
      },

    }
  },
  created() {
    this.getScoreInfo()
  },
  mounted() {
    this.initCharts()
  },
  methods: {
    getScoreInfo() {
      let examCode = this.$route.query.examCode
      console.log(examCode + "123")
      this.name = this.$route.query.source
      this.$axios(`/api/scores/${examCode}`).then(res => {
        let data = res.data.data
        if (data.length > 0) {
          let box = this.$refs['box']
          let charts = this.$echarts.init(box)
          data.forEach(element => {
            switch (Math.floor(element.etScore / 10)) {
              case 10:
              case 9:
                this.category["90分及以上"]++
                break
              case 8:
                this.category['80-89分']++
                break
              case 7:
                this.category["70-79分"]++
                break
              case 6:
                this.category['60-69分']++
                break
              default:
                this.category['60分以下']++
            }
          })
          var average = (this.category["90分及以上"] + this.category['80-89分'] + this.category["70-79分"] + this.category['60-69分'])
            / (this.category["90分及以上"] + this.category['80-89分'] + this.category["70-79分"] + this.category['60-69分'] + this.category['60分以下'])
          let option = {
            title: {
              text: `${this.name}科目及格率为：${average * 100}%`,
              subtext: '分数段饼图',
              x: 'center'
            },
            tooltip: {
              trigger: 'item',
              formatter: "{a}：{b} <br/> {c}人 ({d}%)"
            },
            legend: {
              orient: 'vertical',
              left: 'left',
              data: ['90分及以上', '80-89分', '70-79分', '60-69分', '60分以下']
            },
            series: [
              {
                name: '分数段',
                type: 'pie',
                radius: '35%',
                center: ['50%', '35%'],
                data: [
                  { value: this.category['90分及以上'], name: '90分及以上' },
                  { value: this.category['80-89分'], name: '80-89分' },
                  { value: this.category['70-79分'], name: '70-79分' },
                  { value: this.category['60-69分'], name: '60-69分' },
                  { value: this.category['60分以下'], name: '60分以下' }
                ],
                itemStyle: {
                  emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          }
          charts.setOption(option)
        } else {
          this.isNull = true
        }
      })
    },
    initCharts() {
      const userdom = this.$refs.box1;
      const mycart = this.$echarts.init(userdom, 'vintage');

      this.$axios(`/api/exams`).then(res => {
        let status = res.data.code;
        if (status == 200) {
          let sources = res.data.data.filter(item => item.source);
          let sourceValues = [...new Set(sources.map(item => item.source))];
          let sourcenum = sourceValues.length;
          let numbers = new Array(sourcenum).fill(null);

          let promises = sourceValues.map((examname, index) => {
            return this.$axios(`/api/Scores/${examname}`).then(res => {
              let statuss = res.data.code;
              if (statuss == 200) {
                let data = res.data.data;
                if (data.length > 0) {
                  data.forEach(element => {
                    switch (Math.floor(element.etScore / 10)) {
                      case 10:
                      case 9:
                        this.categorys["90分及以上"]++;
                        break;
                      case 8:
                        this.categorys['80-89分']++;
                        break;
                      case 7:
                        this.categorys["70-79分"]++;
                        break;
                      case 6:
                        this.categorys['60-69分']++;
                        break;
                      default:
                        this.categorys['60分以下']++;
                    }
                  });
                  var average = (this.categorys["90分及以上"] + this.categorys['80-89分'] + this.categorys["70-79分"] + this.categorys['60-69分']) / data.length;
                  numbers[index] = average;

                  // 重置类别计数器
                  this.categorys["90分及以上"] = 0;
                  this.categorys['80-89分'] = 0;
                  this.categorys["70-79分"] = 0;
                  this.categorys['60-69分'] = 0;
                  this.categorys['60分以下'] = 0;
                }
              }
            });
          });

          Promise.all(promises).then(() => {

            const option = {
              title: {
                text: '全科目及格率',
                left: 'center'
              },
              xAxis: {
                type: 'category',
                data: sourceValues,
                axisLabel: {
                  interval: 0,
                  rotate: 45
                },
                splitArea: {
                  show: false
                }
              },
              yAxis: {
                type: 'value'
              },
              series: [
                {
                  data: numbers,
                  type: 'bar',
                  showBackground: true,
                  backgroundStyle: {
                    color: 'rgba(39, 192, 243, 0.2)'
                  },
                  itemStyle: {
                    color: 'rgba(1, 246, 99)'
                  }
                }
              ]
            };
            mycart.setOption(option);
          }).catch(error => {
            console.error("Error fetching data: ", error);
          });
        }
      });
    }
  }
}

</script>

<style lang="less" scoped>
.part {
  .charts-container {
    display: flex;
  }

  .box {
    width: 50%;
    height: 500px;
    margin: 20px;
  }

  .echart-box {
    width: 50%;
    height: 500px;
    border: 3px solid rgba(39, 192, 243, 0.2);
    margin: 20px;
  }
}
</style>
