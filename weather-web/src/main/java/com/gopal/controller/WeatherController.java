package com.gopal.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gopal.dto.WeatherResponse;
import com.gopal.service.IWeatherService;

@Component
@Path("/weather")
public class WeatherController {

	@Autowired
	private IWeatherService weatherService;

	@GET
	@Path("{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testHelloWorld(@PathParam("type") String type, @QueryParam("date") String date) {
		WeatherResponse weatherResponse = weatherService.getData(date);
		if (weatherResponse == null || weatherResponse.getData() == null || weatherResponse.getData().size() < 1) {
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.status(Status.OK).entity(weatherResponse).build();
		}
	}

}
