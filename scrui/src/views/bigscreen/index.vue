<template>
  <section id="data-center" ref="maxRef">
    <div class="map-container">
      <div id="map-container" ref="mapContainer"></div>
    </div>
    <section class="data-contain">
      <section class="data-header">
        <div class="header-date">
          <div class="date-time">{{ nowDate.time }}</div>
          <ul class="date-right">
            <li class="date-d">{{ nowDate.day }}</li>
            <li class="date-w">{{ nowDate.week }}</li>
          </ul>
        </div>
        <div class="header-content">EM-BDA 数据大屏</div>
        <div class="header-btn-list">
          <div class="header-btn" @click="toggleFullscreen">
            {{ isFullscreen ? '半屏' : '全屏' }}
          </div>
          <div class="header-btn" @click="closeDataCenter">退出</div>
        </div>
      </section>
      <section class="data-contain-center">
        <section class="data-contain-left">
          <section class="left-item item-line left-one">
            <div class="item-title">
              <SmallIco />
              每小时预约峰值
            </div>
            <LkChart
              class="left-item-chat"
              key="omniHoraOption"
              :isLoading="isLoading"
              :option="omniHoraOption" />
          </section>
          <section class="left-item left-two">
            <div class="passage-left item-line">
              <div class="item-title"><span class="line"></span>校车资源使用占比</div>
              <LkChart
                class="age-item-chat"
                key="resProportOption"
                :isLoading="isLoading"
                :option="resProportOption" />
            </div>
            <div class="passage-right">
              <div class="passage-sex item-line">
                <div class="item-title"><span class="line"></span>座位使用情况</div>
                <LkChart
                  class="age-item-chat"
                  key="seatUsageOption"
                  :isLoading="isLoading"
                  :option="seatUsageOption" />
              </div>
            </div>
          </section>
          <section class="left-item item-line left-three">
            <div class="item-title">
              <SmallIco />
              运营效率评估
            </div>
            <div class="revenue">
              <LkChart
                class="age-item-chat"
                key="effEvalOption"
                :isLoading="isLoading"
                :chartCtx="effEvalChartCtx" />
            </div>
          </section>
        </section>
        <section class="data-contain-right">
          <section class="right-item item-line right-one">
            <div class="item-title">
              <SmallIco />
              基础信息统计
            </div>
            <section class="risk-body">
              <div class="risk-body-item">
                <span>校车预约总订单数:</span>
                <span>{{ panelData.order }}</span>
              </div>
              <div class="risk-body-item">
                <span>校车预约总线路数</span>
                <span>{{ panelData.route }}</span>
              </div>
              <div class="risk-body-item">
                <span>校车预约总用户数</span>
                <span>{{ panelData.user }}</span>
              </div>
              <div class="risk-body-item">
                <span>最后更新:</span>
                <span>{{ panelData.updateTime }}</span>
              </div>
            </section>
          </section>
          <section class="right-item item-line right-two">
            <div class="item-title">
              <span class="line"></span>
              热门站点 TOP10
            </div>
            <LkChart
              class="right-item-chat"
              key="popuStationOption"
              :isLoading="isLoading"
              :option="popuStationOption" />
          </section>
          <section class="right-item right-three">
            <section class="complain-body">
              <div class="complain-hot item-line">
                <div class="item-title"><span class="line"></span>热门路线</div>
                <LkChart
                  class="complain-chart"
                  key="popuRouteOption"
                  :isLoading="isLoading"
                  :option="popuRouteOption" />
              </div>
              <div class="item-line complain-up">
                <div class="item-title">
                  <span class="line"></span>
                  车辆出行情况
                </div>
                <LkChart
                  class="complain-chart"
                  key="carStatusOption"
                  :isLoading="isLoading"
                  :option="carStatusOption" />
              </div>
            </section>
          </section>
          <section class="right-item item-line right-four">
            <div class="item-title">
              <span class="line"></span>
              周预约峰值
              <div class="select-date"></div>
            </div>
            <LkChart
              class="right-item-chat"
              key="weekOrderOption"
              :isLoading="isLoading"
              :option="weekOrderOption" />
          </section>
        </section>
      </section>
      <section class="data-footer">
        <DataScrollCard key="DataScrollCard" />
      </section>
    </section>
  </section>
</template>

<script>
import LkChart from 'comps/common/lk-chart.vue'
import autoLoad from '@/libs/util.autoLoad'
import DataScrollCard from './compnenets/data-scroll-card.vue'
import SmallIco from './compnenets/small-ico.vue'
import CountTo from 'vue-count-to'
import {
  omniHoraOption,
  popuStationOption,
  seatUsageOption,
  resProportOption,
  popuRouteOption,
  carStatusOption,
  weekOrderOption,
} from './utils/config'

export default {
  name: 'index',
  components: { SmallIco, CountTo, DataScrollCard, LkChart },
  data() {
    return {
      isFullscreen: false,
      omniHoraOption,
      resProportOption,
      seatUsageOption,
      popuStationOption,
      popuRouteOption,
      carStatusOption,
      weekOrderOption,
      panelData: {
        order: 0,
        route: 0,
        user: 0,
        updateTime: 'null',
      },
      liquidData: 0,
      ws: null,
      isLoading: false,
      // 左上角时间
      nowDate: {
        time: new Date().toLocaleTimeString(),
        day: new Date().toLocaleDateString(),
        week: '星期' + '日一二三四五六'.charAt(new Date().getDay()),
      },
      // 地图相关
      centerMap: null,
      dynamicList: [
        {
          b0: 103.731761,
          b1: 36.111419,
          address: '兰州交通大学',
        },
        {
          b0: 103.823213,
          b1: 36.064768,
          address: '西关十字',
        },
      ],
      staticList: [],
      staticMarkers: [],
      dynamicMarkers: [],
    }
  },
  methods: {
    toggleFullscreen() {
      const maxRef = this.$refs.maxRef
      this.isFullscreen = !this.isFullscreen
      let ele = maxRef
      if (this.isFullscreen) {
        // 进入全屏
        if (ele.requestFullscreen) {
          ele.requestFullscreen()
        } else if (ele.mozRequestFullScreen) {
          ele.mozRequestFullScreen()
        } else if (ele.webkitRequestFullscreen) {
          ele.webkitRequestFullscreen()
        } else if (ele.msRequestFullscreen) {
          ele.msRequestFullscreen()
        }
      } else {
        // 退出全屏
        if (document.exitFullscreen) {
          document.exitFullscreen()
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen()
        } else if (document.webkitExitFullscreen) {
          document.webkitExitFullscreen()
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen()
        }
      }
    },
    // 点击标记点
    clickMarker(visitor) {
      // 创建 infoWindow 实例
      let infoWindow = new AMap.InfoWindow({
        content: `
        <section class="map-info-box">
          <div class="map-info-title">站点信息</div>
          <div class="map-info-body">
            <div class="risk-right">
              <div class="info-body">
                <div class="info-item">
                  <span class="info-item-title">站点名称：</span>
                  <span
                    class="info-item-content"
                    style="color: #fec65d; font-weight: bold; font-size: 1.1rem">
                    ${visitor.address || '未知'}</span
                  >
                </div>
                <div class="info-item">
                  <span class="info-item-title">所属线路：</span>
                  <span class="info-item-content">A/B/C</span>
                </div>
              </div>
            </div>
          </div>
        </section>
        `,
        offset: new AMap.Pixel(0, -40), // 设置信息窗口的偏移量，使其在标记点上方显示
      })

      // 打开信息窗体
      infoWindow.open(this.centerMap, new AMap.LngLat(visitor.b0, visitor.b1))
    },
    // 处理静态标记点
    drawStaticPoint() {
      let markers = []
      if (this.staticMarkers.length > 2000) {
        let removeMarkers = this.staticMarkers.slice(0, 1000)
        this.staticMarkers = this.staticMarkers.slice(1000, this.staticMarkers.length - 1)
        this.centerMap.remove(removeMarkers)
        this.staticMarkers = []
      }
      for (const car of this.staticList) {
        if (car.b0 && car.b1) {
          let marker = new AMap.Marker({
            position: [car.b0, car.b1],
            content: '<div class="newStaticMarker"></div>',
          })
          this.centerMap.add(marker)
          markers.push(marker)
          this.staticMarkers.push(marker)
        }
      }
      this.centerMap.add(markers)
    },
    // 处理动态标记点
    drawDynamicPoint(data) {
      let that = this
      if (data) {
        for (const car of data) {
          if (car.b0 && car.b1) {
            let marker = new AMap.Marker({
              position: [car.b0, car.b1],
              content: '<div class="newMarkerClass"></div>',
            })
            // 添加点击事件
            marker.on('click', function () {
              that.clickMarker(car)
            })
            this.centerMap.add(marker)
            this.dynamicMarkers.concat(marker)
          }
        }
      } else {
        this.dynamicMarkers = []
        for (const car of this.dynamicList) {
          if (car.b0 && car.b1) {
            let marker = new AMap.Marker({
              position: [car.b0, car.b1],
              content: '<div class="newMarkerClass"></div>',
            })
            // 添加点击事件
            marker.on('click', function () {
              that.clickMarker(car)
            })
            this.centerMap.add(marker)
            this.dynamicMarkers.push(marker)
          }
        }
      }
    },
    // 初始化地图
    initMap() {
      let centerLng = 103.734512
      let centerLat = 36.1221079
      const AMap = window.AMap
      this.centerMap = new AMap.Map(this.$refs.mapContainer, {
        resizeEnable: true,
        zoom: 13,
        center: new AMap.LngLat(centerLng, centerLat),
        mapStyle: 'amap://styles/darkblue',
      })
      if (this.centerMap) {
        this.drawStaticPoint()
        this.drawDynamicPoint()
      }
    },
    closeDataCenter() {
      this.cancelFullScreen()
      this.$router.push({ path: '/' })
    },
    //关闭全屏
    cancelFullScreen() {
      if (document.exitFullscreen) {
        document.exitFullscreen()
      } else if (document.webkitCancelFullScreen) {
        document.webkitCancelFullScreen()
      } else if (document.mozCancelFullScreen) {
        document.mozCancelFullScreen()
      } else if (document.msExitFullscreen) {
        document.msExitFullscreen()
      }
    },
    // 获取运营效率chart上下文
    effEvalChartCtx(chart) {
      const that = this
      let angle = 0 //角度，用来做简单的动画效果的
      function getCirlPoint(x0, y0, r, angle) {
        let x1 = x0 + r * Math.cos((angle * Math.PI) / 180)
        let y1 = y0 + r * Math.sin((angle * Math.PI) / 180)
        return {
          x: x1,
          y: y1,
        }
      }

      let option = {
        series: [
          {
            name: '内线',
            type: 'custom',
            coordinateSystem: 'none',
            renderItem: function (params, api) {
              return {
                type: 'arc',
                shape: {
                  cx: api.getWidth() / 2,
                  cy: api.getHeight() / 2,
                  r: Math.min(api.getWidth(), api.getHeight()) / 2.5,
                  startAngle: ((0 + angle) * Math.PI) / 180,
                  endAngle: ((90 + angle) * Math.PI) / 180,
                },
                style: {
                  stroke: '#0ff',
                  fill: 'transparent',
                  lineWidth: 3.5,
                },
                silent: true,
              }
            },
            data: [0],
          },
          {
            name: '内线',
            type: 'custom',
            coordinateSystem: 'none',
            renderItem: function (params, api) {
              return {
                type: 'arc',
                shape: {
                  cx: api.getWidth() / 2,
                  cy: api.getHeight() / 2,
                  r: Math.min(api.getWidth(), api.getHeight()) / 2.5,
                  startAngle: ((180 + angle) * Math.PI) / 180,
                  endAngle: ((270 + angle) * Math.PI) / 180,
                },
                style: {
                  stroke: '#0ff',
                  fill: 'transparent',
                  lineWidth: 3.5,
                },
                silent: true,
              }
            },
            data: [0],
          },
          {
            name: '外线',
            type: 'custom',
            coordinateSystem: 'none',
            renderItem: function (params, api) {
              return {
                type: 'arc',
                shape: {
                  cx: api.getWidth() / 2,
                  cy: api.getHeight() / 2,
                  r: Math.min(api.getWidth(), api.getHeight()) / 2.3,
                  startAngle: ((270 + -angle) * Math.PI) / 180,
                  endAngle: ((40 + -angle) * Math.PI) / 180,
                },
                style: {
                  stroke: '#0ff',
                  fill: 'transparent',
                  lineWidth: 3.5,
                },
                silent: true,
              }
            },
            data: [0],
          },
          {
            name: '外线',
            type: 'custom',
            coordinateSystem: 'none',
            renderItem: function (params, api) {
              return {
                type: 'arc',
                shape: {
                  cx: api.getWidth() / 2,
                  cy: api.getHeight() / 2,
                  r: Math.min(api.getWidth(), api.getHeight()) / 2.3,
                  startAngle: ((90 + -angle) * Math.PI) / 180,
                  endAngle: ((220 + -angle) * Math.PI) / 180,
                },
                style: {
                  stroke: '#0ff',
                  fill: 'transparent',
                  lineWidth: 3.5,
                },
                silent: true,
              }
            },
            data: [0],
          },
          {
            name: '线头点',
            type: 'custom',
            coordinateSystem: 'none',
            renderItem: function (params, api) {
              let x0 = api.getWidth() / 2
              let y0 = api.getHeight() / 2
              let r = Math.min(api.getWidth(), api.getHeight()) / 2.3
              let point = getCirlPoint(x0, y0, r, 90 + -angle)
              return {
                type: 'circle',
                shape: {
                  cx: point.x,
                  cy: point.y,
                  r: 5,
                },
                style: {
                  stroke: '#0ff', //绿
                  fill: '#0ff',
                },
                silent: true,
              }
            },
            data: [0],
          },
          {
            name: '线头点',
            type: 'custom',
            coordinateSystem: 'none',
            renderItem: function (params, api) {
              let x0 = api.getWidth() / 2
              let y0 = api.getHeight() / 2
              let r = Math.min(api.getWidth(), api.getHeight()) / 2.3
              let point = getCirlPoint(x0, y0, r, 270 + -angle)
              return {
                type: 'circle',
                shape: {
                  cx: point.x,
                  cy: point.y,
                  r: 5,
                },
                style: {
                  stroke: '#0ff', //绿
                  fill: '#0ff',
                },
                silent: true,
              }
            },
            data: [0],
          },
          {
            type: 'liquidFill',
            radius: '75%',
            center: ['50%', '50%'],
            color: ['#0ff'],
            data: [0],
            label: {
              normal: {
                show: true,
                textStyle: {
                  color: '#0ff',
                  insideColor: '#0ff',
                  fontSize: 30,
                },
                formatter: function (param) {
                  return (param.value * 100).toFixed(2) + '%'
                },
              },
            },
            backgroundStyle: {
              borderWidth: 1,
              color: 'transparent',
            },

            outline: {
              show: true,
              itemStyle: {
                borderColor: '#0ff',
                borderWidth: 2,
              },
              borderDistance: 3,
            },
          },
        ],
      }

      function draw() {
        angle = angle + 2.8
        option.series[6].data = [that.liquidData]
        chart.setOption(option)
      }
      setInterval(() => {
        draw()
      }, 100)
    },
    setChartData(data) {
      this.omniHoraOption.series[0].data = data.ctHourOrder
      this.resProportOption.series[0].data = data.ctResPropu
      this.seatUsageOption.series[0].data = data.ctSeatUsage
      this.liquidData = data.ctLiquid
      this.weekOrderOption.series[0].data = data.ctWeekOrder
      this.panelData = data.ctPanelData
      this.popuStationOption.xAxis.data = data.ctPopuStation.xdata
      this.popuStationOption.series[0].data = data.ctPopuStation.ydata
      this.popuRouteOption.series[0].data.forEach((item, index) => {
        item.name = data.ctPopuRoute[index]
      })
      this.carStatusOption.xAxis[0].data = data.ctCarStatus[0]
      this.carStatusOption.series[0].data = data.ctCarStatus[1]
    },
  },
  async created() {
    await autoLoad('https://webapi.amap.com/maps?v=1.4.13&key=956fa8426889423864f4840af45f1b25')
    await autoLoad('https://webapi.amap.com/loca?v=1.2.0&key=5fbfab5b6f6d13a9bff742af384c7615')
    await autoLoad('https://webapi.amap.com/ui/1.0/main.js?v=1.0.11')
    this.initMap()
  },
  mounted() {
    this.toggleFullscreen()
    this.isLoading = true
    try {
      this.ws = new WebSocket(import.meta.env.VITE_BIG_SCREEN)
      this.ws.onmessage = (e) => {
        const data = JSON.parse(e.data)
        this.setChartData(data.data)
      }
    } catch (_) {
      console.log('init ws error')
    } finally {
      this.isLoading = false
    }

    setInterval(() => {
      this.nowDate.time = new Date().toLocaleTimeString()
    }, 1000)

    window.onbeforeunload = () => {
      console.log('onbeforeunload')
      this.ws && this.ws.close()
    }
  },
  beforeDestroy() {
    this.ws && this.ws.close()
    this.ws = null
  },
}
</script>

<style lang="scss">
.newStaticMarker {
  width: 2px;
  height: 2px;
  border-radius: 100%;
  background-image: radial-gradient(#fffaa9, rgb(190, 166, 255));
}

.newMarkerClass {
  width: 10px;
  height: 10px;
  position: relative;
  outline: none;
  //background-image: radial-gradient(yellow, rgb(253, 3, 9));
  background-color: #e3ad0b;

  border-radius: 100%;
  transform-origin: 0 0;
  display: block;
  opacity: 0.7;
}

.newMarkerClass::after {
  content: '';
  -webkit-border-radius: 100%;
  border-radius: 100%;
  height: 400%;
  width: 400%;
  position: absolute;
  margin: -150% 0 0 -150%;
  box-shadow: inset 0 0 10px 8px #ffd237;
  animation: pulsate 1s ease-out;
  animation-iteration-count: infinite; /*无穷反复*/
  animation-delay: 1.1s;
}

@keyframes pulsate {
  0% {
    transform: scale(0.1, 0.1);
    opacity: 0;
    filter: alpha(opacity=0);
  }
  50% {
    opacity: 1;
    filter: none;
  }
  100% {
    transform: scale(1.2, 1.2);
    opacity: 0;
    filter: alpha(opacity=0);
  }
}

.map-info-box {
  .map-info-title {
    font-weight: bold;
    font-size: 1rem;
    margin-bottom: 10px;
  }
}

.map-info-body {
  height: 130px;
  width: 400px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.right-four {
  position: relative;

  .select-date {
    position: absolute;
    right: 0;
    top: 2px;
    width: 130px;
    overflow: hidden;

    .el-date-editor input {
      width: 130px !important;
      background-color: #132a72;
      color: #eeeeee;
    }
  }
}
</style>
<style lang="scss" scoped>
@import 'dataCenter';
</style>
