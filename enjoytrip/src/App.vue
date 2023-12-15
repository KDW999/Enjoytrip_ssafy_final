<script setup>
	import { RouterLink, RouterView } from "vue-router";
	import TheNavbar from "@/components/common/TheNavbar.vue";
</script>

<template>
	<the-navbar />

	<main>
		<router-view />
	</main>
</template>

<style scoped>
</style>
