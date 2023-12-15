<script setup>
	import { computed, ref, watch } from "vue";
	import { getRecommendationData } from "@/api/recommendation-api";
	import { sidos, sigungus } from "@/utilities/area-code";
	import AttractionDetail from "@/components/map/AttractionDetail.vue";

	const statusText = [
		"선택", "선택", "목록 업데이트 중", "목록 업데이트 실패"
	];

	const sido = ref("*");
	const sigungu = ref("*");

	const groups = [
		{ "value": 10, "text": "10대 이하" },
		{ "value": 20, "text": "20대" },
		{ "value": 30, "text": "30대" },
		{ "value": 40, "text": "40대" },
		{ "value": 50, "text": "50대" },
		{ "value": 60, "text": "60대" },
		{ "value": 70, "text": "70대 이상" }
	];

	const age = ref(null);
	const gender = ref(null);

	const properties = computed(
		() => (
			{
				"sido": sido.value,
				"sigungu": sigungu.value,
				"age": age.value,
				"gender": gender.value
			}
		)
	);

	const list = ref([]);
	const loading = ref(false);

	const loadAttractions = async () => {
		if(
			!(
				sido.value !== "*" &&
				sigungu.value !== "*" &&
				age.value !== null &&
				gender.value !== null
			)
		) return;

		loading.value = true;

		const province = sidos.find(
			(item) => item.value === sido.value
		);
		const city = sigungus[sido.value].find(
			(item) => item.value === sigungu.value
		);

		const data = await getRecommendationData(
			age.value,
			gender.value,
			`${province.value}${city.value}`,
			`${province.text} ${city.text}`
		);
		console.debug(data);

		list.value = data.map(
			(item) => item.CONT_ID
		).slice(0, 5);

		loading.value = false;
	};

	watch(sido, () => { sigungu.value = "*"; });
	watch(properties, loadAttractions);
</script>

<template>
	<b-container class="my-5">
		<b-row>
			<b-col cols="auto">
				<h3 class="d-inline-block align-middle">연령별 인기 관광지</h3>
			</b-col>
			<b-col cols="auto">
				<b-link :to="{ name: 'map' }" class="d-inline-block align-middle recommendation-link">더보기</b-link>
			</b-col>
		</b-row>
		<b-row class="mt-3">
			<b-col cols="12" lg="5">
				<b-card>
					<b-form-group label="지역 선택" label-size="lg">
						<b-form-group label="시/도" label-for="recommendation-sido">
							<b-form-select id="recommendation-sido" v-model="sido" :options="sidos">
								<template #first>
									<b-form-select-option value="*" disabled>시/도 선택</b-form-select-option>
								</template>
							</b-form-select>
						</b-form-group>
						<b-form-group label="시/군/구" label-for="recommendation-sigungu">
							<b-form-select id="recommendation-sigungu" v-model="sigungu" :options="sigungus[sido]" :disabled="sido === '*'">
								<template #first>
									<b-form-select-option value="*" disabled>시/군/구 {{ sigunguText }}</b-form-select-option>
								</template>
							</b-form-select>
						</b-form-group>
					</b-form-group>
					<b-form-group label="사용자 정보" label-size="lg">
						<b-form-group label="연령" label-for="recommendation-age-group">
							<b-form-select id="recommendation-age-group" v-model="age" :options="groups">
								<template #first>
									<b-form-select-option :value="null" disabled>연령 선택</b-form-select-option>
								</template>
							</b-form-select>
						</b-form-group>
						<b-form-group label="성별">
							<b-form-radio-group v-model="gender">
								<b-form-radio value="male">남성</b-form-radio>
								<b-form-radio value="female">여성</b-form-radio>
							</b-form-radio-group>
						</b-form-group>
					</b-form-group>
				</b-card>
			</b-col>
			<b-col cols="12" lg="7">
				<b-row v-show="loading">
					<b-col cols="auto">
						<b-icon-arrow-clockwise animation="spin-pulse" font-scale="1.45" />
						<span style="font-size: 1.45rem;">정보 조회 중</span>
					</b-col>
				</b-row>
				<b-row v-for="item in list" :key="item" class="mb-2">
					<b-col>
						<attraction-detail :contentid="item" />
					</b-col>
				</b-row>
			</b-col>
		</b-row>
	</b-container>
</template>
