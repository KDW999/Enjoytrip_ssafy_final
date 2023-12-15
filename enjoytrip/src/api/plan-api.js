import { localApiAxios } from "@/utilities/http-commons";

const axios = localApiAxios();
const commonConfig = {
	"headers": {
		"Content-Type": "application/x-www-form-urlencoded"
	}
};

export const createPlan = (userId, title) => axios.post(
	`/plan/user/${encodeURIComponent(userId)}`,
	{ "title": title },
	commonConfig
);

export const listPlans = (userId) => axios.get(
	`/plan/user/${encodeURIComponent(userId)}`
);

export const retrievePlan = (id) => axios.get(
	`/plan/${encodeURIComponent(id)}`
);

export const modifyPlan = (id, plan, title) => axios.patch(
	`/plan/${encodeURIComponent(id)}`,
	{
		...(title && { title }),
		...(plan && { plan })
	},
	commonConfig
);

export const appendToPlan = (id, attractionId) => axios.post(
	`/plan/${encodeURIComponent(id)}`,
	{ "attraction-id": attractionId },
	commonConfig
);

export const deletePlan = (id) => axios.delete(
	`/plan/${encodeURIComponent(id)}`
);
