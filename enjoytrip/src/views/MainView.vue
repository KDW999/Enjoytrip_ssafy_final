<script setup>
import RecommendationOverview from "@/components/recommendation/RecommendationOverview.vue";
import MainSlide from "../components/common/MainSlide.vue";
</script>

<template>
  <div class="d-flex" style="background-color: rgb(237, 249, 255)">
    <div style="width: 640px; height: 600px; margin: 30px">
      <div
        class="main-text"
        style="
          font-size: 30px;
          margin-top: 60px;
          margin-left: 40px;
          display: flex;
          justify-content: center;
        ">
        바람 따라 떠나는
        <br />
        여행지
        <br />
      </div>
      <b-img
        src="src/assets/image/free-icon-nature-4773919.png"
        fluid
        alt="Responsive image"
        style="width: 78px; margin-left: 224px; margin-top: 12px"></b-img>
    </div>

    <div style="width: 930px; height: 500px">
      <main-slide />
    </div>
  </div>
  <v-container>
    <v-row>
      <v-col>
        <recommendation-overview />
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.parallax-main {
  height: 80dvh;
}

.parallax-text-blend {
  mix-blend-mode: difference;
}

.image-blur {
  filter: blur(2px);
}

.main-text {
  color: black;
  font-family: "TAEBAEKfont";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2310@1.0/TAEBAEKfont.woff2")
    format("woff2");
  font-weight: 700;
  font-style: normal;
}
</style>
