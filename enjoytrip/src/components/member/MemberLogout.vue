<script setup>
import { onBeforeMount } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

onBeforeMount(() => {
  sessionStorage.removeItem("user");
  router.replace({ name: "main" });
});
</script>

<template>
  <div></div>
</template>

<style scoped></style>
