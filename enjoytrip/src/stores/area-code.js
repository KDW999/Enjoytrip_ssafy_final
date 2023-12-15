import { ref } from "vue";
import { defineStore } from "pinia";
import { getAreaList, getSigunguList } from "@/api/data-api";

export const useAreaCodeStore = defineStore(
	"area-code",
	() => {
		// States
		const sidos = ref([]);
		const sigungus = ref([]);

		// Getters

		// Actions
		const getSidos = async () => {
			const response = await getAreaList();
			if(!(response?.data?.response?.body?.items?.item))
				throw new Error("올바르지 않은 데이터");
			sidos.value = response.data.response.body.items.item;
		};
		const getSigungus = async (sidoCode) => {
			const response = await getSigunguList(sidoCode);
			if(!(response?.data?.response?.body?.items?.item))
				throw new Error("올바르지 않은 데이터");
			sigungus.value = response.data.response.body.items.item;
		};

		const states = {
			sidos,
			sigungus
		};
		const getters = {};
		const actions = {
			getSidos,
			getSigungus
		};

		return { ...states, ...getters, ...actions };
	}
);
