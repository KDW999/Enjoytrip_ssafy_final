import { reactive, ref } from "vue";
import { defineStore } from "pinia";
import { listPlans, retrievePlan, modifyPlan, deletePlan, createPlan } from "@/api/plan-api";

export const useTripPlanStore = defineStore(
	"trip-plan",
	() => {
		// States
		const plans = ref([]);
		const planIndex = ref(null);
		const currentPlan = ref(null);
		const status = reactive(
			{
				"plans": 0,
				"currentPlan": 0
			}
		);

		const draft = ref({ "title": "", "plan": [] });
		const insertId = ref(0);

		// Getters
		const getUserId = () => JSON.parse(sessionStorage.getItem("user"))?.id;
		const getPlan = (index) => plans.value[index];

		// Actions
		const clearPlans = () => {
			planIndex.value = null;
			plans.value.length = 0;
		};
		const clearDraft = () => {
			draft.value = { "title": "", "plan": [] };
			insertId.value = 0;
		};
		const refreshPlans = async (noprogress = false) => {
			const id = getUserId();
			if(!noprogress) clearPlans();
			else planIndex.value = null;
			status.plans = 1;

			try {
				const response = await listPlans(id);
				plans.value = response.data;

				for(const p of plans.value)
					p.plan = JSON.parse(p.plan);

				status.plans = 0;
			}
			catch(error) {
				status.plans = 2;
				throw error;
			}

			status.plans = 0;
		};
		const refreshPlan = async () => {
			if(!(plans.value[planIndex.value])) return;

			const response = await retrievePlan(plans.value[planIndex.value].planNo);
			currentPlan.value = response.data;

			const data = [];
			for(const item of JSON.parse(response.data.plan)) {
				data.push({ "id": insertId.value++, "value": item });
			}

			currentPlan.value.plan = data;
			draft.value = {
				"title": currentPlan.value.title,
				"plan": [...currentPlan.value.plan]
			};
		};
		const removePlan = async (planNo) => {
			await deletePlan(planNo);
		};
		const addPlan = async (title) => {
			const id = getUserId();
			await createPlan(id, title);
		};
		const addToDraft = (contentid) => {
			draft.value.plan.push(
				{ "id": insertId.value++, "value": contentid }
			);
		};
		const commitDraft = async () => {
			const id = currentPlan.value.planNo;
			const title = draft.value.title;
			const planarray = [];

			for(const p of draft.value.plan)
				planarray.push(p.value);
			
			await modifyPlan(id, JSON.stringify(planarray), title);
		};

		// Return
		return {
			plans,
			planIndex,
			status,
			currentPlan,
			draft,
			insertId,
			getPlan,
			clearPlans,
			clearDraft,
			refreshPlans,
			refreshPlan,
			removePlan,
			addPlan,
			addToDraft,
			commitDraft
		};
	}
);
