<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
import { useRoute } from "vue-router";

const route = useRoute();

const { contentid } = route.params;
</script>

<template>
  <b-container>
    <b-row>
      <b-col class="text-center mt-4">
        <h1 v-if="contentid">후기 작성</h1>
        <h1 v-else>글 등록하기</h1>
      </b-col>
    </b-row>
    <br />
    <div>
      <BoardFormItem type="regist" :contentid="contentid" />
    </div>
  </b-container>
</template>

<style scoped></style>
