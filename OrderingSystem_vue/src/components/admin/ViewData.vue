<template>
  <div>
    <el-row>
      <el-col :span="12">
        <div ref="chart1" style="width:100%;height:376px"></div>
      </el-col>
      <el-col :span="12">
        <div ref="chart2" style="width:100%;height:376px"></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div ref="chart3" style="width:100%;height:376px"></div>
      </el-col>
      <el-col :span="12">
        <div ref="chart4" style="width:100%;height:376px"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'ViewData',
  data () {
    return {
      data1: [],
      data2: [],
      data3: [],
      data4: [],
      dishType: []
    }
  },
  mounted () {
    this.getEchartData1()
    this.getEchartData2()
    this.getEchartData3()
    this.getEchartData4()
  },
  methods: {
    getEchartData1 () {
      this.$axios.get('/order/eachDishSale/yesterdayAndToday').then(resp => {
        if (resp && resp.status === 200) {
          this.data1 = resp.data.result
          const chart1 = this.$refs.chart1
          let myChart = ''
          if (chart1) {
            myChart = this.$echarts.init(chart1)
            const option = {
              legend: {},
              title: [
                {
                  // left: 'center',
                  text: '菜品类今昨销售数量对比'
                }
              ],
              tooltip: {},
              dataset: {
                source: this.data1
              },
              xAxis: { type: 'category' },
              yAxis: {},

              series: [{ type: 'bar' }, { type: 'bar' }]
            }
            myChart.setOption(option)
            window.addEventListener('resize', function () {
              myChart.resize()
            })
          }
          this.$on('hook:destroyed', () => {
            window.removeEventListener('resize', function () {
              myChart.resize()
            })
          })
        }
      })
    },

    getEchartData2 () {
      let data = []
      this.$axios.get('/order/eachDaySalesOfMonth').then(resp => {
        if (resp && resp.status === 200) {
          data = resp.data.result
          const chart2 = this.$refs.chart2
          let myChart = ''
          if (chart2) {
            myChart = this.$echarts.init(chart2)

            // prettier-ignore
            // const data = [['2022-06-05', 116], ['2022-06-06', 129], ['2022-06-07', 135], ['2022-06-08', 86], ['2022-06-09', 73], ['2022-06-10', 85], ['2022-06-11', 73], ['2022-06-12', 68], ['2022-06-13', 92], ['2022-06-14', 130], ['2022-06-15', 245], ['2022-06-16', 139], ['2022-06-17', 115], ['2022-06-18', 111], ['2022-06-19', 309], ['2022-06-20', 206], ['2022-06-21', 137], ['2022-06-22', 128], ['2022-06-23', 85], ['2022-06-24', 94], ['2022-06-25', 71], ['2022-06-26', 106], ['2022-06-27', 84], ['2022-06-28', 93], ['2022-06-29', 85], ['2022-06-30', 73], ['2022-07-01', 83], ['2022-07-02', 125], ['20022-07-03', 107], ['2022-07-04', 82], ['2022-07-05', 44], ['2022-07-06', 72], ['2022-07-07', 106], ['2022-07-08', 107], ['2022-07-09', 66], ['2022-07-10', 91], ['2022-07-11', 92], ['2022-07-12', 113], ['2022-07-13', 107], ['2022-07-14', 131], ['2022-07-15', 111], ['2022-07-16', 64], ['2022-07-17', 69], ['2022-07-18', 88], ['2022-07-19', 77], ['2022-07-20', 83], ['2022-07-21', 111], ['2022-07-22', 57], ['2022-07-23', 55], ['2022-07-24', 60]]
            const dateList = data.map(function (item) {
              return item.date
            })
            const valueList = data.map(function (item) {
              return item.sale
            })

            const option = {
              // Make gradient line here
              visualMap: [
                {
                  show: false,
                  type: 'continuous',
                  seriesIndex: 0,
                  min: 0,
                  max: 400
                }
              ],
              title: [
                {
                  text: '销售总额折线图'
                }

              ],
              tooltip: {
                trigger: 'axis'
              },
              xAxis: [
                {
                  data: dateList
                }
              ],
              yAxis: [
                {}
              ],
              grid: [
                {
                  bottom: '18%'
                }
              ],
              series: [
                {
                  type: 'line',
                  showSymbol: false,
                  data: valueList
                }
              ]
            }
            myChart.setOption(option)
            window.addEventListener('resize', function () {
              myChart.resize()
            })
          }
          this.$on('hook:destroyed', () => {
            window.removeEventListener('resize', function () {
              myChart.resize()
            })
          })
        }
      })
    },

    getEchartData3 () {
      this.$axios.get('/order/eachDishSales/yesterdayAndToday').then(resp => {
        if (resp && resp.status === 200) {
          this.data3 = resp.data.result
          this.$axios.get('/dishType').then(resp => {
            this.dishType = resp.data.result
            const chart3 = this.$refs.chart3
            let myChart = ''
            if (chart3) {
              myChart = this.$echarts.init(chart3)
              console.log(this.dishType)
              const option = {
                title: {
                  text: '菜品类今昨日总销售额'
                },
                legend: {
                  bottom: 0,
                  data: ['昨天', '今天']
                },
                radar: {
                // shape: 'circle',
                  indicator: this.dishType.reverse()
                },
                series: [
                  {
                    name: '昨天 vs 今天',
                    type: 'radar',
                    data: [
                      {
                        value: this.data3[0],
                        name: '昨天'
                      },
                      {
                        value: this.data3[1],
                        name: '今天'
                      }
                    ]
                  }
                ]
              }
              myChart.setOption(option)
              window.addEventListener('resize', function () {
                myChart.resize()
              })
            }
            this.$on('hook:destroyed', () => {
              window.removeEventListener('resize', function () {
                myChart.resize()
              })
            })
          })
        }
      })
    },
    getEchartData4 () {
      this.$axios.get('/order/eachDishSale/today').then(resp => {
        if (resp && resp.status === 200) {
          this.data4 = resp.data.result
          let myChart = ''
          const chart4 = this.$refs.chart4
          if (chart4) {
            myChart = this.$echarts.init(chart4)
            const option = {
              title: {
                text: '菜品类今日销售占额'
              },
              tooltip: {
                trigger: 'item'
              },
              legend: {
                bottom: '0%',
                left: 'center'
              },
              series: [
                {
                  name: 'Access From',
                  type: 'pie',
                  radius: ['40%', '70%'],
                  avoidLabelOverlap: false,
                  itemStyle: {
                    borderRadius: 10,
                    borderColor: '#fff',
                    borderWidth: 2
                  },
                  label: {
                    show: false,
                    position: 'center'
                  },
                  emphasis: {
                    label: {
                      show: true,
                      fontSize: '40',
                      fontWeight: 'bold'
                    }
                  },
                  labelLine: {
                    show: false
                  },
                  data: this.data4
                }
              ]
            }
            myChart.setOption(option)
            window.addEventListener('resize', function () {
              myChart.resize()
            })
          }
          this.$on('hook:destroyed', () => {
            window.removeEventListener('resize', function () {
              myChart.resize()
            })
          })
        }
      })
    }
  },
  watch: {}
}
</script>

<style>
.el-row {
  margin-bottom: 20px;
}

.el-row :last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}
</style>
