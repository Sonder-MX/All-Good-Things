import echarts from 'echarts'

export const omniHoraOption = {
  tooltip: {
    trigger: 'axis',
    axisPointer: { type: 'shadow' },
  },
  grid: {
    left: '2%',
    right: '3%',
    bottom: '15%',
    top: '15%',
    containLabel: true,
  },
  legend: {
    data: ['每小时预约'],
    right: 10,
    top: 0,
    textStyle: {
      color: '#fff',
    },
    itemWidth: 10,
    itemHeight: 10,
  },
  xAxis: [
    {
      type: 'category',
      boundaryGap: false,
      axisLabel: {
        textStyle: {
          color: 'rgba(255,255,255,.8)',
          fontSize: 12,
        },
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(255,255,255,.3)',
        },
      },
      data: [
        '0时',
        '1时',
        '2时',
        '3时',
        '4时',
        '5时',
        '6时',
        '7时',
        '8时',
        '9时',
        '10时',
        '11时',
        '12时',
        '13时',
        '14时',
        '15时',
        '16时',
        '17时',
        '18时',
        '19时',
        '20时',
        '21时',
        '22时',
        '23时',
      ],
    },
    {
      axisPointer: { show: false },
      axisLine: { show: false },
      position: 'bottom',
      offset: 20,
    },
  ],
  yAxis: [
    {
      type: 'value',
      axisTick: { show: false },
      axisLine: {
        lineStyle: {
          color: 'rgba(255,255,255,.1)',
        },
      },
      axisLabel: {
        textStyle: {
          color: 'rgba(255,255,255,.6)',
          fontSize: 12,
        },
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(255,255,255,.3)',
          type: 'dotted',
        },
      },
    },
  ],
  series: [
    {
      name: '每小时预约',
      type: 'line',
      smooth: true,
      symbol: 'circle',
      symbolSize: 1,
      showSymbol: true,
      lineStyle: {
        normal: {
          color: 'rgba(228, 228, 126, 1)',
          width: 2,
        },
      },
      areaStyle: {
        normal: {
          color: new echarts.graphic.LinearGradient(
            0,
            0,
            0,
            1,
            [
              {
                offset: 0,
                color: 'rgba(228, 228, 126, .8)',
              },
              {
                offset: 0.8,
                color: 'rgba(228, 228, 126, 0.1)',
              },
            ],
            false
          ),
          shadowColor: 'rgba(0, 0, 0, 0.1)',
        },
      },
      itemStyle: {
        normal: {
          color: '#dddc6b',
          borderColor: 'rgba(221, 220, 107, .1)',
          borderWidth: 12,
        },
      },
      data: [],
    },
  ],
}

export const resProportOption = {
  grid: {
    left: '2%',
    right: '2%',
    bottom: '10%',
    top: '10%',
    containLabel: true,
  },
  tooltip: {
    trigger: 'item',
    right: 0,
    top: 0,
    formatter: '{b} : ({d}%)',
  },
  calculable: true,
  series: [
    {
      name: ' ',
      color: ['#ba97f9', '#bfbaf5'],
      type: 'pie',
      radius: [10, 35],
      center: ['50%', '40%'],
      roseType: 'radius',
      label: {
        normal: {
          show: true,
        },
        emphasis: {
          show: true,
        },
      },
      lableLine: {
        normal: {
          show: true,
        },
        emphasis: {
          show: true,
        },
      },
      data: [
        {
          value: 0,
          name: '已使用',
        },
        {
          value: 0,
          name: '未使用',
        },
      ],
    },
  ],
}

export const seatUsageOption = {
  tooltip: {
    trigger: 'item',
    right: 0,
    top: 0,
    formatter: '{b} : {c} 个',
    // 向右偏移
    position: function (point) {
      return [point[0] - 40, point[1] - 40]
    },
  },
  grid: {
    left: '8%',
    top: '5%',
    right: '2%',
    bottom: '2%',
    containLabel: true,
  },
  xAxis: {
    show: false,
  },
  yAxis: [
    {
      show: true,
      data: ['已使用', '未使用'],
      inverse: true,
      axisLine: { show: false },
      splitLine: { show: false },
      axisTick: { show: false },
      axisLabel: {
        textStyle: {
          color: '#fff',
        },
      },
    },
    {
      show: false,
      inverse: true,
      data: [1, 1],
      axisLabel: { textStyle: { color: '#fff' } },
      axisLine: { show: false },
      splitLine: { show: false },
      axisTick: { show: false },
    },
  ],
  series: [
    {
      name: '人',
      type: 'bar',
      yAxisIndex: 0,
      data: [0, 0],
      barWidth: 12,
      itemStyle: {
        normal: {
          color: function (params) {
            var colorList = ['#4591e3', '#b562e4', '#ff82e9', '#b562e4']
            return new echarts.graphic.LinearGradient(
              1,
              0,
              0,
              0,
              [
                {
                  offset: 0,
                  color: colorList[params.dataIndex],
                },
                {
                  offset: 0.8,
                  color: colorList[params.dataIndex + 1],
                },
              ],
              false
            )
          },
          barBorderRadius: 50,
        },
      },
    },
  ],
}

export const popuStationOption = {
  tooltip: {
    trigger: 'axis',
    axisPointer: { type: 'shadow' },
    formatter: function (params) {
      let marker = params[0].marker
      let address = params[0].axisValue
      let value = params[0].value
      return `${marker}<br/>站点: ${address}<br/>数量: ${value}`
    },
  },
  grid: {
    left: '2%',
    right: '2%',
    bottom: '15%',
    top: '10%',
    containLabel: true,
  },
  xAxis: {
    data: [],
    axisLine: { show: false },
    axisLabel: {
      color: 'rgba(255,255,255,.6)',
      fontSize: 12,
    },
  },
  yAxis: {
    splitNumber: 4,
    nameTextStyle: {
      color: 'rgba(255,255,255,.6)',
      fontSize: 12,
    },
    axisLine: { show: false },
    axisLabel: {
      color: 'rgba(255,255,255,.6)',
      fontSize: 12,
    },
    splitLine: {
      lineStyle: {
        color: 'rgba(255,255,255,.1)',
        type: 'dotted',
      },
    },
  },
  series: [
    {
      type: 'bar',
      barWidth: '25%',
      itemStyle: {
        normal: {
          barBorderRadius: 50,
          color: function (params) {
            var colorList = [
              '#ff639e',
              '#ff82e9',
              '#b562e4',
              '#e59e04',
              '#ff632d',
              '#4591e3',
              '#04b8e5',
              '#04dde5',
              '#04e5bd',
              '#04e57e',
              '#fedb5b',
            ]
            return colorList[params.dataIndex]
          },
          label: {
            show: true,
            position: 'top',
            formatter: '{c}',
            color: 'rgba(255,255,255,.4)',
            fontSize: 12,
          },
        },
      },
      data: [],
    },
  ],
}

export const popuRouteOption = {
  grid: {
    left: '2%',
    right: '2%',
    bottom: '10%',
    top: '10%',
    containLabel: true,
  },
  tooltip: {
    trigger: 'item',
    formatter: function (params) {
      let mark = params.marker
      return `${mark} ${params.name}`
    },
  },
  xAxis: [
    {
      gridIndex: 0,
      min: 0,
      show: false,
      max: 100,
      nameLocation: 'middle',
      nameGap: 30,
    },
  ],
  yAxis: [
    {
      gridIndex: 0,
      min: 0,
      show: false,
      max: 100,
      nameLocation: 'middle',
      nameGap: 30,
    },
  ],
  series: [
    {
      type: 'scatter',
      symbol: 'circle',
      symbolSize: 120,
      label: {
        normal: {
          show: true,
          formatter: '{b}',
          color: '#fff',
          textStyle: {
            fontSize: '12',
            fontWeight: 'bold',
          },
        },
      },
      data: [
        {
          name: '',
          value: [60, 70],
          symbolSize: 90,
          itemStyle: {
            normal: {
              color: '#ec1818',
            },
          },
        },
        {
          name: '',
          value: [10, 70],
          symbolSize: 70,
          itemStyle: {
            normal: {
              color: '#ef623a',
            },
          },
        },
        {
          name: '',
          value: [24, 30],
          symbolSize: 50,
          itemStyle: {
            normal: {
              color: '#94e03c',
            },
          },
        },
        {
          name: '',
          value: [83, 30],
          symbolSize: 40,
          itemStyle: {
            normal: {
              color: '#08ba79',
            },
          },
        },
      ],
    },
  ],
}

export const carStatusOption = {
  tooltip: {
    trigger: 'axis',
    axisPointer: { type: 'shadow' },
  },
  legend: {
    data: ['车辆'],
    right: 0,
    top: 0,
    textStyle: {
      color: '#fff',
    },
    itemWidth: 10,
    itemHeight: 10,
  },
  grid: {
    left: '2%',
    right: '2%',
    bottom: '15%',
    top: '20%',
    containLabel: true,
  },
  xAxis: [
    {
      type: 'category',
      data: [],
      axisLine: { show: false },
      axisLabel: {
        color: 'rgba(255,255,255,.6)',
        fontSize: 12,
      },
    },
  ],
  yAxis: [
    {
      axisLabel: {
        color: 'rgba(255,255,255,.6)',
        fontSize: 12,
      },
      axisLine: {
        show: false,
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(255,255,255,.1)',
          type: 'dotted',
        },
      },
    },
  ],
  series: [
    {
      name: '车辆',
      type: 'bar',
      data: [],
      barWidth: '11px',
      itemStyle: {
        normal: {
          color: new echarts.graphic.LinearGradient(
            0,
            1,
            0,
            0,
            [
              {
                offset: 0,
                color: '#4033F9',
              },
              {
                offset: 0.8,
                color: '#ee870a',
              },
            ],
            false
          ),
          barBorderRadius: [30, 30, 30, 30],
          shadowColor: 'rgba(0,160,221,1)',
          shadowBlur: 4,
        },
      },
    },
  ],
}

export const weekOrderOption = {
  tooltip: {
    trigger: 'axis',
    axisPointer: { type: 'shadow' },
    formatter: '{b} : {c}条',
  },
  grid: {
    left: '2%',
    right: '2%',
    bottom: '15%',
    top: '20%',
    containLabel: true,
  },
  xAxis: [
    {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
      axisLine: { show: false },
      axisLabel: {
        color: 'rgba(255,255,255,.6)',
        fontSize: 12,
      },
    },
  ],
  yAxis: [
    {
      axisLabel: {
        color: 'rgba(255,255,255,.6)',
        fontSize: 12,
      },

      axisLine: {
        show: false,
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(255,255,255,.1)',
          type: 'dotted',
        },
      },
    },
  ],
  series: [
    {
      type: 'bar',
      data: [25, 10, 34, 45, 21, 31, 15],
      barWidth: '11px',
      itemStyle: {
        normal: {
          color: new echarts.graphic.LinearGradient(
            0,
            1,
            0,
            0,
            [
              {
                offset: 0,
                color: '#4033F9',
              },
              {
                offset: 0.8,
                color: '#BA97F9',
              },
            ],
            false
          ),
          barBorderRadius: [30, 30, 30, 30],
          shadowColor: 'rgba(0,160,221,1)',
          shadowBlur: 4,
        },
      },
      label: {
        normal: {
          show: true,
          lineHeight: 20,
          width: 50,
          height: 20,
          backgroundColor: 'rgba(0,160,221,0.1)',
          borderRadius: 200,
          position: ['-12', '-20'],
          distance: 0,
          formatter: ['    {d|●}', ' {a|{c}}     \n', '    {b|}'].join(','),
          rich: {
            d: {
              color: '#BA97F9',
            },
            a: {
              color: '#fff',
              align: 'center',
            },
            b: {
              width: 1,
              height: 10,
              borderWidth: 1,
              borderColor: '#234e6c',
              align: 'left',
            },
          },
        },
      },
    },
  ],
}
