package com.gopal.helper;


import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.gopal.dto.HttpResponse;

public class HttpCallHelper {
	public HttpResponse doGetCall(URI uri) {
		HttpResponse httpResponse = new HttpResponse();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpGet http = new HttpGet(uri);
			long startTime = System.currentTimeMillis();
			response = httpclient.execute(http);
			httpResponse = populateIvHttpResponse(uri.getHost() + uri.getPath(), response, startTime);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			finallyMethod(httpclient, response);
		}
		return httpResponse;
	}


	

	private HttpResponse populateIvHttpResponse(String endPoint, CloseableHttpResponse response, long startTime1) throws IOException {
		HttpResponse ivHttpResponse = new HttpResponse();
		HttpEntity entity = response.getEntity();
		int statusCode = response.getStatusLine().getStatusCode();
		ivHttpResponse.setHttpStatus(statusCode);
		if (statusCode >= HttpStatus.SC_BAD_REQUEST) {
		}
		if (entity != null) {
			ivHttpResponse.setResponseBody(EntityUtils.toString(entity));
		} else {
		}
		return ivHttpResponse;
	}

	
	private void finallyMethod(CloseableHttpClient httpclient, CloseableHttpResponse response) {
		if (response != null) {
			try {
				response.close();
			} catch (IOException e) {
			}
		}
		try {
			httpclient.close();
		} catch (IOException e) {
		}
	}
}

