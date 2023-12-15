<script setup>
	import { ref, onMounted } from "vue";
	import { retrievePlan } from "@/api/plan-api";
	import AttractionDetail from "@/components/map/AttractionDetail.vue";
	
	const prop = defineProps({ "plan": Number });

	const defaults = {
		"planNo": "",
		"userId": "",
		"title": "",
		"plan": [],
		"registerTime": "",
		"editTime": ""
	};
	const info = ref({ ...defaults });
	let counter = 0;

	const progress = ref(1);
	const load = async () => {
		progress.value = 1;

		try {
			const response = await retrievePlan(prop.plan);
			const data = response.data;
			data.plan = JSON.parse(
				data.plan
			).map(
				(item) => ({ "id": counter++, "value": item })
			);

			info.value = data;
			progress.value = 0;
		}
		catch(error) {
			progress.value = 2;
		}
	};
</script>

<template>
	<b-modal title="여행계획 상세정보" ok-only size="xl" @show="load">
		<template v-if="progress === 0">
			<h3>{{ info.title }}</h3>
			<div class="text-muted">등록: {{ info.registerTime }}</div>
			<div v-show="info.registerTime !== info.editTime" class="text-muted">
				수정: {{ info.editTime }}
			</div>

			<div id="plan-detail-map" class="my-3"></div>
			<b-container fluid>
				<b-row v-for="item in info.plan" :key="item.id" class="mt-2">
					<b-col cols="12">
						<attraction-detail :contentid="item.value" />
					</b-col>
				</b-row>
			</b-container>
			<span v-show="!info.plan.length">여행계획이 비었습니다</span>
		</template>
		<h4 v-else-if="progress === 1">상세정보 불러오는 중</h4>
		<h4 v-else>상세정보 불러오기 실패</h4>
	</b-modal>
</template>
