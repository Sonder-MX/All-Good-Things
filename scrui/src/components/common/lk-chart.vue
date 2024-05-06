<template>
  <div ref="chartBox" style="width: 100%; height: 100%">
    <div ref="lkChart" :id="id" class="lk-chart"></div>
  </div>
</template>

<script>
import('echarts-liquidfill')
import echarts from 'echarts'
import { markRaw } from 'vue'

export default {
  name: 'lk-chart',
  props: {
    //echarts选项
    option: {
      type: Object,
      default() {
        return {}
      },
    },
    //接受盒子高度 实现高度自适应
    changeHeight: 0,
    id: {
      type: String,
      default: 'chart',
    },
    chartCtx: {
      type: Function,
      default: (c) => {},
    },
    width: {
      type: String,
      default: '200px',
    },
    height: {
      type: String,
      default: '200px',
    },
    isLoading: {
      type: Boolean,
      default: false,
    },
  },

  async mounted() {
    this.init()
    //监视窗口变化实现自适应
    window.addEventListener('resize', this.resizeChart)
  },
  beforeDestroy() {
    this.chart && this.chart.dispose()
  },
  destroyed() {
    //  及时销毁 防止内存泄露
    window.removeEventListener('resize', this.resizeChart)
  },
  methods: {
    init() {
      this.chart = markRaw(echarts.init(this.$refs.lkChart))
      this.chart.setOption(this.option, true)
      this.chartCtx(this.chart)
      if (this.isLoading) {
        this.chart.showLoading({
          text: 'loading...',
          color: '#5b8ff9',
          textColor: '#fff',
          maskColor: 'rgba(6, 16, 49, 0.5)',
        })
      }
    },
    resizeChart() {
      this.chart ? this.chart.resize() : ''
    },
  },
  watch: {
    //监听对象 实现和父组件数据同步
    option: {
      handler(n) {
        this.chart && this.chart.setOption(n, true)
      },
      deep: true,
    },
    isLoading() {
      this.chart && this.chart.hideLoading()
    },
    changeHeight() {
      this.resizeChart()
    },
  },

  data() {
    return {
      chart: null,
    }
  },
}
</script>

<style scoped lang="scss">
.lk-chart {
  width: 100%;
  height: 100%;
}
</style>
