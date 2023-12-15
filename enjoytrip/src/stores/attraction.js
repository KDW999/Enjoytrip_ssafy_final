import { ref } from "vue";
import { defineStore } from "pinia";
import { getAreaBasedList, getSearchKeyword } from "@/api/data-api";

export const useAttractionStore = defineStore(
	"attraction",
	() => {
		// States
		const attractions = ref([]);
		const totalCount = ref(0);
		const perPage = ref("10");
		const pageNo = ref(1);

		// Getters

		// Actions
		const getAttractions = async (callee, ...parameters) => {
			const response = await callee(...parameters);
			attractions.value = response.data.response.body.items.item ? response.data.response.body.items.item : [];
			totalCount.value = response.data.response.body.totalCount;
		};
		const getAreaBasedAttractions = async (areaCode, sigunguCode, contentType) => {
			await getAttractions(getAreaBasedList, areaCode, sigunguCode, contentType, perPage.value, pageNo.value);
		};
		const getKeywordBasedAttractions = async (keyword, contentType) => {
			await getAttractions(getSearchKeyword, keyword, contentType, perPage.value, pageNo.value);
		};
		const clearResults = () => {
			attractions.value = [];
			totalCount.value = 0;
			pageNo.value = 1;
		};

		const states = {
			attractions,
			totalCount,
			perPage,
			pageNo
		};
		const getters = {};
		const actions = {
			getAreaBasedAttractions,
			getKeywordBasedAttractions,
			clearResults
		};

		return { ...states, ...getters, ...actions };
	}
);
