package com.lambdaschool.schools.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AdviceSlip {
	private String advice;
	private int id;

	public AdviceSlip() {}

	public AdviceSlip(
			String advice,
			int id
	) {
		this.advice = advice;
		this.id     = id;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "AdviceSlip{" + "advice='" + advice + '\'' + ", id=" + id + '}';
	}

}
