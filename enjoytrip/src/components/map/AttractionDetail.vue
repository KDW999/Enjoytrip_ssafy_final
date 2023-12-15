<script setup>
import { ref, onMounted } from "vue";
import { getDetailCommon } from "@/api/data-api";

const prop = defineProps({ contentid: String, compact: Boolean });
const item = ref({
  contentid: prop.contentid,
  title: "관광지 정보 불러오는 중",
  firstimage: null,
  firstimage2: null,
  addr1: "",
  addr2: "",
  mapx: null,
  mapy: null,
  overview: null,
});

onMounted(() => {
  getDetailCommon(prop.contentid)
    .then((response) => {
      if (response.status !== 200) throw new Error();

      console.log(prop.contentid);
      item.value = response.data.response.body.items.item[0];
      if (!item.value.overview || item.value.overview === "-")
        item.value.overview = "";
    })
    .catch(() => {
      item.value.title = "관광지 정보 조회 실패";
    });
});
</script>

<template>
  <b-card :no-body="compact">
    <b-row>
      <b-col cols="12" md="4" class="card-body" v-if="item.firstimage && !compact">
        <b-card-img-lazy :src="item.firstimage" alt="관광지 사진" />
      </b-col>
      <b-col cols="12" :md="item.firstimage && !compact ? '8' : '12'">
        <b-card-body
          :title="item.title"
          :sub-title="`${item.addr1} ${item.addr2}`"
          :title-tag="compact ? 'h5' : 'h4'">
          <b-card-text v-if="!compact">
            <p>{{ item.overview }}</p>
          </b-card-text>
          <slot name="actions"></slot>
        </b-card-body>
      </b-col>
    </b-row>
  </b-card>
</template>
