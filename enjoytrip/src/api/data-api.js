import { dataApiAxios } from "@/utilities/http-commons";

const axios = dataApiAxios();
const key = import.meta.env.VITE_DATA_API_SERVICE_KEY;
const commonParameters = {
	"serviceKey": key,
	"MobileOS": "ETC",
	"MobileApp": "Enjoy Trip",
	"_type": "json"
};

export const getAreaList = () =>
	axios.get("areaCode1", {
		"params": {
			...commonParameters,
			"numOfRows": "32",
			"pageNo": "1"
		}
	}
);

export const getSigunguList = (areaCode) =>
	axios.get("areaCode1", {
		"params": {
			...commonParameters,
			"areaCode": areaCode,
			"numOfRows": "32",
			"pageNo": "1"
		}
	}
);

export const getAreaBasedList = (areaCode, sigunguCode, contentType, rows, pageNumber) =>
	axios.get("areaBasedList1", {
		"params": {
			...commonParameters,
			"areaCode": areaCode,
			"sigunguCode": sigunguCode,
			"contentTypeId": contentType,
			"pageNo": pageNumber,
			"numOfRows": rows
		}
	}
);

export const getSearchKeyword = (keyword, contentType, rows, pageNumber) =>
	axios.get("searchKeyword1", {
		"params": {
			...commonParameters,
			"keyword": keyword,
			"contentTypeId": contentType,
			"pageNo": pageNumber,
			"numOfRows": rows
		}
	}
);

export const getDetailCommon = (contentId) =>
	axios.get("detailCommon1", {
		"params": {
			...commonParameters,
			"contentId": contentId,
			"defaultYN": "Y",
			"firstImageYN": "Y",
			"areacodeYN": "N",
			"catcodeYN": "N",
			"addrinfoYN": "Y",
			"mapinfoYN": "Y",
			"overviewYN": "Y"
		}
	}
);
