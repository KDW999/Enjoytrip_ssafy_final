<script>
export default {
  data() {
    return {
      slide: 0,
      sliding: null,
    };
  },
  methods: {
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },
  },
};
</script>

<template>
  <div style="width: 920px">
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      controls
      indicators
      background="#ababab"
      style="text-shadow: 1px 1px 2px #333"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd">
      <!-- Text slides with image -->
      <b-carousel-slide
        img-src="https://cdn.visitkorea.or.kr/img/call?cmd=VIEW&id=6bdff741-56df-4ea4-be3c-69c6c334c2df&mode=raw"></b-carousel-slide>

      <!-- Slides with custom text -->
      <b-carousel-slide>
        <template #img>
          <img
            class="d-block img-fluid w-100"
            width="1024"
            height="480"
            src="https://cdn.visitkorea.or.kr/img/call?cmd=VIEW&id=8c0d65df-0e1f-4db0-a168-c5247e4c1730&mode=raw"
            alt="image slot" />
        </template>

        <!-- <h1>안녕 세상아</h1> -->
      </b-carousel-slide>

      <!-- Slides with image only -->
      <b-carousel-slide
        img-src="https://cdn.visitkorea.or.kr/img/call?cmd=VIEW&id=e19def1f-c59d-4f36-b829-ae5d12f0cce7&mode=raw"></b-carousel-slide>

      <!-- Slides with img slot -->
      <!-- Note the classes .d-block and .img-fluid to prevent browser default image alignment -->
      <!-- <b-carousel-slide>
        <template #img>
          <img
            class="d-block img-fluid w-100"
            width="1024"
            height="480"
            src="https://image.news1.kr/system/photos/2020/5/18/4198953/article.jpg/dims/quality/80/optimize"
            alt="image slot" />
        </template>
      </b-carousel-slide> -->

      <!-- Slide with blank fluid image to maintain slide aspect ratio -->
      <!-- <b-carousel-slide caption="Blank Image" img-blank img-alt="Blank image">
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
          eros felis, tincidunt a tincidunt eget, convallis vel est. Ut
          pellentesque ut lacus vel interdum.
        </p>
      </b-carousel-slide> -->
    </b-carousel>

    <p class="mt-4">
      <!-- Slide #: {{ slide }}<br />
      Sliding: {{ sliding }} -->
    </p>
  </div>
</template>

<style scoped></style>
