<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
</script>

<template>
  <b-container>
    <b-row>
      <b-col class="text-center mt-4">
        <h1>글 수정하기</h1>
      </b-col>
    </b-row>
    <br />
    <div>
      <BoardFormItem type="modify" />
    </div>
  </b-container>
</template>

<style scoped></style>
