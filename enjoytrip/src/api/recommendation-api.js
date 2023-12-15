import { datalabApiAxios, datalabSearchRangeApiAxios } from "@/utilities/http-commons";

const axios = datalabApiAxios();
const searchRangeAxios = datalabSearchRangeApiAxios();

const axiosPath = "getTempleteData.do";
const searchRangeAxiosPath = "getSrchDteDivInitVal.do";

const commonParameters = {
	"srchAreaDate": "1",
	"KTO_CATE_LCLS_CD": "",
	"KTO_CATE_MCLS_CD": "",
	"GEN_CD": "",
	"AGEG_DIV_CD": "",
	"contentIndex": "0",
	"pageIndex": "1",
	"orderValue": "desc",
	"qid": "LN_05_01_008"
};

export const getSearchDateRange = async () => {
	try {
		const response = await searchRangeAxios.post(
			searchRangeAxiosPath,
			{
				"menuGrpCd": "1",
				"menuCd": "10102030000002020091512"
			}
		);

		const info = response.data.list;
		return {
			"BASE_YM1": info[0]["bgngStngVal"],
			"BASE_YM2": info[0]["endStngVal"]
		};
	}
	catch(error) {
		console.debug(error);
		return {
			"BASE_YM1": "202211",
			"BASE_YM2": "202310"
		};
	}
};

export const getColumnName = (age, gender) => {
	let group = (age - (age % 10)) / 10;
	if(group < 1) group = 1;
	else if(group > 7) group = 7;

	return `VAL${(group - 1) * 2 + (gender === "female" ? 1 : 0)}`;
};

export const getRecommendationData = async (age, gender, areaCode, areaName) => {
	const orderSelect = getColumnName(age, gender);
	const { BASE_YM1, BASE_YM2 } = await getSearchDateRange();

	const response = await axios.post(
		axiosPath,
		{
			"SGG_CD": areaCode,
			"SGG_NM": areaName,
			BASE_YM1,
			BASE_YM2,
			...commonParameters,
			orderSelect
		}
	);

	return response.data.list;
};
