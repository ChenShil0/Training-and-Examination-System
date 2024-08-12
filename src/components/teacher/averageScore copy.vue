<template>
  <div style="display: flex; justify-content: space-around;">
    <div class="echart-box" ref="box"></div>

  </div>
</template>
<script>


var sourceValueass = []
export default {
  mounted() {
    // 挂在完成dom后进行初始化
    this.showEcarts();
  },
  methods: {

    showEcarts() {

      // 基于准备好的dom，初始化echarts实例
      const userdom = this.$refs.box
      const mycart = this.$echarts.init(userdom, 'vintage')
      this.$axios(`/api/exams`).then(res => {
        let status = res.data.code
        if (status == 200) {
          // 使用filter方法来过滤数组，只保留包含source键的对象
          let sources = res.data.data.filter(item => item.source);

          // 使用map方法来提取每个对象的source值
          let sourceValues = sources.map(item => item.source);

          console.log(sourceValues[0]);
          //绘制图表
          const option = {
            xAxis: {
              type: 'category',
              data: sourceValues,
              axisLabel: {
                interval: 0, // 设置横坐标轴标签间隔为 0，以显示所有标签
                rotate: 45 // 旋转标签以避免重叠
              },
              splitArea: { // 隐藏横坐标轴的分割区域
                show: false
              }
            },
            yAxis: {
              type: 'value'
            },
            series: [
              {
                data: [120, 200, 150, 80, 70, 110, 130],
                type: 'bar',
                barwidth: 15,
                showBackground: true,
                backgroundStyle: {
                  color: 'rgba(39, 192, 243, 0.2)'
                },
                itemStyle: {
                  color: 'rgba(1, 246, 99)',

                }

              }
            ]

          };

          // 渲染图表
          mycart.setOption(option)

        }
      })

    },
  },
}
</script>
<style scoped>
.echart-box {
  width: 500px;
  height: 350px;
  border: 3px solid rgb(39, 192, 243, 0.2);
  margin: 20px;
}
</style>
