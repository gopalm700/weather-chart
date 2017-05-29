$(document).ready(function() {
	$("#datepicker").datepicker({
		dateFormat : 'yymmdd',

		onClose : function(dateText) {
			$(".clearable").html('');
			callApi(dateText)
		}
	});
});

var callApi = function(date) {
	var resp;
	$.get("http://localhost:8080/rest/weather/temp/?date=" + date, function(data,
			status) {
		if (status == 'success') {
			resp = populateData(data);
			plotData(resp);
			var newObject = jQuery.extend(true, {}, resp);
			plotDataTempToTime(newObject);
		}
	});
}


var populateData = function(resp) {
	var arr = [];
	var info = {};
	var i;
	for (i = 0; i < resp.data.length; i++) {
		info = new Object();
		info.x = parseInt(resp.data[i].x);
		info.y = parseInt(resp.data[i].y);
		arr.push(info);
	}
	var response = {};
	response.metric = String.fromCharCode(176) + resp.metric;
	response.data = arr;
	return response;
}

var padding = function(val) {
  var temp = val;
  for(var  i =val.length; i<4;i++ ){
	  temp = '0'+temp;
  }
  return temp;
}

var plotData = function(resp) {
	if (resp.data == null || resp.data == undefined || resp.data.length < 1) {
		return;
	}
	$("#mindata").text(
			resp.data[0].x + resp.metric + " " + padding(resp.data[0].y.toString()) + " hrs");
	var length = resp.data.length - 1;
	$("#maxdata").text(
			resp.data[length].x + resp.metric + " " + padding(resp.data[length].y.toString())
					+ " hrs");
	var options = {
		title : {
			text : "Temperature time chart"
		},
		animationEnabled : true,
		axisX : {
			title : "Temperature",
			valueFormatString : "00" + resp.metric
		},
		axisY : {
			title : "Time",
			valueFormatString : "00:00 hrs"
		},
		data : [ {
			type : "line", // change it to line, area, column, pie, etc
			yValueFormatString : "00:00 hrs",
			xValueFormatString : "00" + resp.metric,
			dataPoints : resp.data
		} ]
	};

	$("#chartContainer").CanvasJSChart(options);
}

var plotDataTempToTime = function(resp) {
	if (resp.data == null || resp.data == undefined || resp.data.length < 1) {
		return;
	}

	for (var i = 0; i < resp.data.length; i++) {
		var x = resp.data[i].x;
		resp.data[i].x = resp.data[i].y;
		resp.data[i].y = x;
	}
	resp.data.sort(function(a, b) {
		return a.x - b.x;
	})
	var options = {
		title : {
			text : "Time Temperature chart"
		},
		animationEnabled : true,
		axisX : {
			title : "Time",
			valueFormatString : "00:00 hrs"
		},
		axisY : {
			title : "Temperature",
			valueFormatString : "00" + resp.metric
		},
		data : [ {
			type : "line", // change it to line, area, column, pie, etc
			xValueFormatString : "00:00 hrs",
			yValueFormatString : "00" + resp.metric,
			dataPoints : resp.data
		} ]
	};

	$("#chartContainer1").CanvasJSChart(options);
}