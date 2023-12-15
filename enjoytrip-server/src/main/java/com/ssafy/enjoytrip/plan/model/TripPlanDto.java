package com.ssafy.enjoytrip.plan.model;

public class TripPlanDto {
	/**
	 * 여행계획 번호
	 */
	private Integer planNo;
	/**
	 * 작성자 ID
	 */
	private String userId;
	/**
	 * 여행계획 이름
	 */
	private String title;
	/**
	 * 여행계획 데이터 (직렬화된 관광지 ID의 배열)
	 */
	private String plan;
	/**
	 * 작성일자
	 */
	private String registerTime;
	/**
	 * 수정일자
	 */
	private String editTime;

	public TripPlanDto() {}
	public TripPlanDto(Integer planNo, String userId, String title, String plan, String registerTime, String editTime) {
		this.planNo = planNo;
		this.userId = userId;
		this.title = title;
		this.plan = plan;
		this.registerTime = registerTime;
		this.editTime = editTime;
	}

	public Integer getPlanNo() { return planNo; }
	public void setPlanNo(Integer planNo) { this.planNo = planNo; }
	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getPlan() { return plan; }
	public void setPlan(String plan) { this.plan = plan; }
	public String getRegisterTime() { return registerTime; }
	public void setRegisterTime(String registerTime) { this.registerTime = registerTime; }
	public String getEditTime() { return editTime; }
	public void setEditTime(String editTime) { this.editTime = editTime; }

	@Override
	public String toString() {
		return "TripPlanDto [planNo=" + planNo + ", userId=" + userId + ", title=" + title + ", plan="
				+ plan + ", registerTime=" + registerTime + ", editTime=" + editTime + "]";
	}
}
