<script setup>
	import { computed, ref, watch, onMounted } from "vue";
	import { storeToRefs } from "pinia";
	import { useTripPlanStore } from "@/stores/trip-plan";
	import AttractionDetail from "@/components/map/AttractionDetail.vue";
	import PlanDetail from "@/components/trip/PlanDetail.vue";

	const planStore = useTripPlanStore();
	const currentUser = computed(
		() => JSON.parse(sessionStorage.getItem("user"))
	);

	const deleteCount = ref(0);
	const deleteIndex = ref(0);
	const newTitle = ref("");

	const { plans, planIndex, status, currentPlan, draft } = storeToRefs(planStore);
	const { refreshPlans, refreshPlan, clearDraft, commitDraft, removePlan, addPlan } = planStore;

	const expanded = computed(
		() => {
			const array = Array(plans.value.length).fill(false);
			if(planIndex.value in array) array[planIndex.value] = true;
			return array;
		}
	);

	const openPlan = async (index, discard = false) => {
		if(planIndex.value === index && !discard) {
			if(!draft.value.title) return;

			plans.value[planIndex.value].title = draft.value.title;
			await commitDraft();

			await refreshPlans();
		}
		else {
			planIndex.value = (planIndex.value !== index ? index : null);
		}

		if(typeof planIndex.value === "number") {
			clearDraft();
			await refreshPlan();
		}
	};

	const deleteAttraction = (id) => {
		draft.value.plan = draft.value.plan.filter(
			(item) => item.id !== id
		);
	};

	let deleteTimeout = null;
	const tryDelete = async (planNumber, index) => {
		if(deleteIndex.value !== index) {
			clearTimeout(deleteTimeout);
			deleteCount.value = 1;
		}
		else
			++deleteCount.value;

		deleteIndex.value = index;

		if(deleteCount.value === 1) {
			deleteTimeout = setTimeout(
				() => { deleteCount.value = 0; },
				2000
			);
		}
		else {
			deleteCount.value = 0;
			planIndex.value = null;
			await removePlan(planNumber);
			await refreshPlans();
		}
	};

	const addNewPlan = async () => {
		await addPlan(newTitle.value);
		newTitle.value = "";
		planIndex.value = null;
		await refreshPlans();
	};

	const openedPlan = ref(null);
	const openDetail = (planNo) => {
		openedPlan.value = planNo;
	};

	watch(
		currentPlan,
		() => {
			draft.value = {
				"title": currentPlan.value.title,
				"plan": [...currentPlan.value.plan]
			};
		}
	);

	onMounted(refreshPlans);
</script>

<template>
	<b-container fluid>
		<b-row class="mb-3">
			<b-col>
				<h1>My Trip</h1>
			</b-col>
		</b-row>
		<template v-if="currentUser?.id">
			<b-row class="trip-plan-list">
				<b-col>
					<div class="accordion" role="tablist">
						<b-card v-for="(plan, index) in plans" :key="plan.planNo" header-bg-variant="white" no-body>
							<template #header>
								<span v-if="!expanded[index]" class="d-block mb-1">{{ plan.title }}</span>
								<b-form-input class="mb-1" v-if="expanded[index]" :state="!!draft.title" type="text" placeholder="제목" size="sm" v-model="draft.title" />
								<div class="plan-actions">
									<b-button v-show="!expanded[index]" variant="outline-info" size="sm" class="plan-action mr-1" v-b-modal.plan-detail v-b-tooltip.hover title="상세정보" @click="openDetail(plan.planNo)">
										<b-icon-list-ul />
									</b-button>
									<b-button v-show="!expanded[index]" variant="outline-secondary" size="sm" class="plan-action mr-1" v-b-tooltip.hover title="수정" @click="openPlan(index)">
										<b-icon-pencil-square />
									</b-button>
									<span v-show="expanded[index]">
										<b-button variant="outline-success" size="sm" class="plan-action mr-1" v-b-tooltip.hover title="저장" @click="openPlan(index)">
											<b-icon-check2 />
										</b-button>
										<b-button variant="outline-warning" size="sm" class="plan-action mr-1" v-b-tooltip.hover title="취소" @click="openPlan(index, true)">
											<b-icon-x-lg />
										</b-button>
									</span>
									<b-button v-show="!expanded[index]" variant="outline-danger" size="sm" class="plan-action" v-b-tooltip.hover title="삭제" @click="tryDelete(plan.planNo, index)">
										<b-icon-trash />
										<span v-show="deleteCount === 1 && deleteIndex === index">삭제하기</span>
									</b-button>
								</div>
							</template>
							<b-collapse v-model="expanded[index]">
								<b-card-body class="px-3">
									<div v-show="status.currentPlan === 1">
										<b-icon-arrow-clockwise animation="spin-pulse" />
									</div>
									<div v-show="draft">
										<b-row v-for="item in draft.plan" :key="item.id">
											<b-col>
												<attraction-detail class="plan-attraction-detail mb-1" :contentid="item.value" :compact="true">
													<template #actions>
														<b-button class="plan-detail-action" variant="link" @click="deleteAttraction(item.id)">
															삭제
														</b-button>
													</template>
												</attraction-detail>
											</b-col>
										</b-row>
										<div v-if="!draft.plan.length">
											여행계획이 비었습니다
										</div>
									</div>
								</b-card-body>
							</b-collapse>
							<b-card-body class="p-0"></b-card-body>
						</b-card>
						<div v-show="status.plans === 1">
							<b-icon-arrow-clockwise animation="spin-pulse" font-scale="2" />
						</div>
					</div>
				</b-col>
			</b-row>
			<b-row v-show="status.plans === 0" class="mt-3">
				<b-col>
					<label for="add-plan-title">여행계획 추가</label>
					<div class="input-group">
						<b-form-input id="add-plan-title" type="text" name="title" placeholder="제목" size="sm" v-model="newTitle" />
						<div class="input-group-append">
							<b-button variant="outline-primary" size="sm" :disabled="!newTitle" @click="addNewPlan">
								<b-icon-plus />
							</b-button>
						</div>
					</div>
				</b-col>
			</b-row>
		</template>
		<template v-else>
			<b-row>
				<b-col>
					<span class="d-block h6">로그인 후 사용 가능합니다</span>
					<b-link :to="{ name: 'member-login' }">로그인</b-link>
				</b-col>
			</b-row>
		</template>

		<plan-detail id="plan-detail" :plan="openedPlan" />
	</b-container>
</template>

<style scoped>
	.plan-actions .plan-action {
		font-size: 0.7rem;
	}

	.plan-attraction-detail {
		font-size: 0.25rem;
	}

	.plan-detail-action {
		padding: 0;
	}
</style>
