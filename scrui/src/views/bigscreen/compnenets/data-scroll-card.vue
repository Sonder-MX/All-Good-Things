<template>
  <section style="overflow: hidden">
    <ul class="scroll-title">
      <li>请求路径</li>
      <li>请求方法</li>
      <li>请求IP</li>
      <li>请求时间</li>
      <li>请求耗时</li>
      <li>响应状态</li>
      <li>备注</li>
    </ul>

    <Vue3SeamlessScroll :list="listData" hover :class-option="classOption" class="warp">
      <ul class="item">
        <li v-for="(item, index) in listData" :key="index">
          <span class="title">{{ item.apiPath }}</span>
          <span class="date" :style="{ color: item.httpMethod === 'GET' ? '#87d068' : '#f50' }">
            {{ item.httpMethod }}
          </span>
          <span class="date">{{ item.remoteAddr }}</span>
          <span class="date">{{ item.startTime }}</span>
          <span class="date">{{ item.spendTime }}ns</span>
          <span class="date">{{ item.status }}</span>
          <span class="date">无</span>
        </li>
      </ul>
    </Vue3SeamlessScroll>
  </section>
</template>

<script>
import { Vue3SeamlessScroll } from 'vue3-seamless-scroll'
import { apiLogList } from 'api/modules/api.logs'

export default {
  name: 'data-scroll-card',
  components: {
    Vue3SeamlessScroll,
  },
  data() {
    return {
      classOption: {
        direction: 0,
      },
      listData: [],
    }
  },
  methods: {
    getData() {
      apiLogList({ current: 1, pageSize: 50 }).then(({ data }) => {
        this.listData = data.result
      })
    },
  },
  mounted() {
    this.getData()
  },
}
</script>

<style lang="scss" scoped>
$primary-color: #4c71ea;
$secondary-color: #00d8ff;
$background-color: #686868;
$border-color: #686868;
$font-color: #b4b3b3;

$scroll-title-height: 2rem;
$scroll-title-font-size: 1rem;
$scroll-title-text-shadow: 0 0 25px $secondary-color;

$warp-height: calc(100% - #{$scroll-title-height});
$warp-font-size: 0.9rem;
$warp-border-color: #686868;
$warp-margin-bottom: 5px;

.scroll-title {
  list-style: none;
  padding: 0;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  box-shadow: 0 0 25px $primary-color inset;

  li {
    width: 25%;
    height: $scroll-title-height;
    line-height: $scroll-title-height;
    font-size: $scroll-title-font-size;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #eeeeee;
    font-weight: bold;
    font-family: 'Adobe 楷体 Std R';
    text-shadow: $scroll-title-text-shadow;
  }
}

.warp {
  height: $warp-height;
  width: 100%;
  margin: 0 auto;
  overflow: hidden;
  color: $background-color;

  ul {
    list-style: none;
    padding: 0;
    margin: 0 auto;

    li {
      height: 2.14rem;
      line-height: 2.14rem;
      display: flex;
      text-align: center;
      justify-content: space-between;
      font-size: $warp-font-size;
      border-bottom: 1px solid $warp-border-color;
      margin-bottom: $warp-margin-bottom;

      span {
        display: inline-block;
        width: 25%;
        color: $font-color;
      }
    }
  }
}
</style>
