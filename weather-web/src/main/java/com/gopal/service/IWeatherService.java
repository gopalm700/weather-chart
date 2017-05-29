package com.gopal.service;

import com.gopal.dto.WeatherResponse;

public interface IWeatherService {

	public WeatherResponse getData(String date);
	
}
