import axios from "axios";

export const dataApiAxios = () => axios.create(
	{
		"baseURL": import.meta.env.VITE_DATA_API_BASE_URL,
		"headers": {
			"Content-Type": "application/json",
		}
	}
);

export const localApiAxios = () => axios.create(
	{
		"baseURL": import.meta.env.VITE_TRIP_API_URL,
		"headers": {
			"Content-Type": "application/json",
		}
	}
);

export const datalabApiAxios = () => axios.create(
	{
		"baseURL": import.meta.env.VITE_VISITKOREA_DATALAB_BASE_URL,
		"headers": {
			"Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
		}
	}
);
export const datalabSearchRangeApiAxios = () => axios.create(
	{
		"baseURL": import.meta.env.VITE_VISITKOREA_DATALAB_SEARCH_RANGE_BASE_URL,
		"headers": {
			"Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
		}
	}
);
