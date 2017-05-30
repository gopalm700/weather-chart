package com.gopal.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.gopal.dto.Data;
import com.gopal.dto.HttpResponse;
import com.gopal.dto.Observation;
import com.gopal.dto.WeatherDto;
import com.gopal.dto.WeatherResponse;
import com.gopal.helper.DataComparatorTemp;
import com.gopal.helper.HttpCallHelper;
import com.gopal.service.IWeatherService;

@Service(value = "tempWeatherServiceImpl")
public class WeatherServiceImpl implements IWeatherService {

	private HttpCallHelper httpCallHelper = new HttpCallHelper();

	@Value("${url}")
	private String uri;

	@Value("${api}")
	private String api;

	@Value("${key}")
	private String key;

	private Gson gson = new Gson();

	public WeatherResponse getData(String date) {
		HttpResponse httpResponse = callApi(date);
		WeatherDto weatherDto = null;
		if (httpResponse.getHttpStatus() == HttpStatus.SC_OK) {
			weatherDto = gson.fromJson(httpResponse.getResponseBody(), WeatherDto.class);
		}
		return convertToWeatherResponse(weatherDto);
	}

	private HttpResponse callApi(String date) {
		String url = api + key + "/history_" + date + uri;
		return httpCallHelper.doGetCall(URI.create(url));
	}

	private WeatherResponse convertToWeatherResponse(WeatherDto weatherDto) {
		WeatherResponse weatherResponse = null;
		if (weatherDto != null && null != weatherDto.getHistory()) {
			weatherResponse = new WeatherResponse();
			weatherResponse.setMetric("C");
			List<Observation> observations = weatherDto.getHistory().getObservations();
			if (null != observations) {
				List<Data> datas = new ArrayList<Data>();
				for (Observation observation : observations) {
					String y = observation.getDate().getHour() + "" + observation.getDate().getMin();
					String x = observation.getTempm();
					datas.add(new Data(x, y));
				}
				Collections.sort(datas, new DataComparatorTemp());
				weatherResponse.setData(datas);
			}
		}
		return weatherResponse;
	}
}
