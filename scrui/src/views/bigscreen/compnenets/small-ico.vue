<template>
  <section>
    <div class="loader loader-7">
      <div class="line line1"></div>
      <div class="line line2"></div>
      <div class="line line3"></div>
    </div>
  </section>
</template>

<script>
export default {
  name: 'small-ico',
}
</script>

<style scoped lang="scss">
.loader {
  position: relative;
  width: 1.14rem;
  height: 1.14rem;
  border-radius: 50%;
  margin: 0 10px;
  display: inline-block;
  vertical-align: middle;
  &:hover .line {
    animation-play-state: paused;
  }
}

.loader-7 .line {
  width: 3px;
  position: absolute;
  border-radius: 2px;
  bottom: 0;
  background: -webkit-gradient(linear, left top, left bottom, from(#ff6535), to(#1aa1ff));
  background: -webkit-linear-gradient(top, #ff6535, #1aa1ff);
  background: linear-gradient(to bottom, #17ea1b, #1aa1ff);
}

.loader-7 .line1 {
  left: 0;
  -webkit-animation: line-grow 0.5s ease alternate infinite;
  animation: line-grow 0.5s ease alternate infinite;
}

.loader-7 .line2 {
  left: 7px;
  -webkit-animation: line-grow 0.5s 0.2s ease alternate infinite;
  animation: line-grow 0.5s 0.2s ease alternate infinite;
}

.loader-7 .line3 {
  left: 14px;
  -webkit-animation: line-grow 0.5s 0.4s ease alternate infinite;
  animation: line-grow 0.5s 0.4s ease alternate infinite;
}

@-webkit-keyframes line-grow {
  0% {
    height: 0;
  }
  100% {
    height: 75%;
  }
}

@keyframes line-grow {
  0% {
    height: 0;
  }
  100% {
    height: 75%;
  }
}
</style>
