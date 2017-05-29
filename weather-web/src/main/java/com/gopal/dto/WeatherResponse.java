package com.gopal.dto;

import java.util.List;

public class WeatherResponse {
	
	private List<Data> data;
	private String metric;

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}
	
}
