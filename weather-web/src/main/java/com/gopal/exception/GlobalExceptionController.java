package com.gopal.exception;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;

import org.omg.CORBA.portable.ApplicationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

	
	@ExceptionHandler(value = ApplicationException.class)
	public void handleException(Exception ex, HttpServletResponse resp){
		resp.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
		ex.printStackTrace();
	}
}
