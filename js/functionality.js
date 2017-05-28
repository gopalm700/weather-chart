


$(document).ready(function(){
	$("#datepicker").datepicker({
		 dateFormat: 'yymmdd',
	
   		onClose: function(dateText) { 
        callApi(dateText)
    	}
	});
});

var callApi = function(date){
 		$.get("http://api.wunderground.com/api/26e456ccd29358f2/history_"+date+"/q/autoip.json", function(data, status){
 			if(status == 'success'){
        		var resp = populateData(data);
        		plotData(resp);
        		plotDataTempToTime(resp);
        	}
    	});
}

 var populateData = function (data){
 		var arr = [];
 		var info = {};
 		var i;
 		var obj = data.history;
 		for(i = 0; i < obj.observations.length; i++){
 			info = new Object();
 			info.x = parseInt(obj.observations[i].tempm);
 			var str = obj.observations[i].date.hour+obj.observations[i].date.min;
 			info.y = parseInt(str);
 			arr.push(info);
 		}
 		var resp = {};
 		resp.metric = String.fromCharCode(176)+"C ";
 		resp.data = arr;
 		return resp;
 }

 var plotData = function(resp){
 	if(resp.data == null || resp.data == undefined || resp.data.length < 1){
 		return;
 	}
 	resp.data.sort(function(a, b){
 		var x = a.x -b.x;
 		return x != 0 ? x : a.y - b.y;
 	});
 	$("#mindata").text(resp.data[0].x+resp.metric+resp.data[0].y + " hrs");
 	var length = resp.data.length-1;
 	$("#maxdata").text(resp.data[length].x+resp.metric+resp.data[length].y+ " hrs");
	var options = {
		title: {
			text: "Temperature time chart"
		},
        animationEnabled: true,
        axisX:{
 			 title : "Temperature",
 			 valueFormatString: "00"+resp.metric
 		},
 		axisY:{
  			title : "Time",
  			valueFormatString: "00:00 hrs"
 		},
		data: [
			{
				type: "line", //change it to line, area, column, pie, etc
				 xValueFormatString: "00:00 hrs",
				 yValueFormatString: "00"+resp.metric,
				dataPoints: resp.data
			}
		]
	};

	$("#chartContainer").CanvasJSChart(options);
 }




  var plotDataTempToTime = function(resp){
  	if(resp.data == null || resp.data == undefined || resp.data.length < 1){
 		return;
 	}

  	for(var i = 0; i< resp.data.length; i++){
  		var x  = resp.data[i].x;
  		resp.data[i].x = resp.data[i].y;
		resp.data[i].y =x;
  	}
  	resp.data.sort(function(a,b){
  		return a.x-b.x;
  	})
	var options = {
		title: {
			text: "Temperature time chart"
		},
        animationEnabled: true,
        axisX:{
 			 title : "Time",
 			 valueFormatString: "00:00 hrs"
 		},
 		axisY:{
  			title : "Temperature",
  			valueFormatString: "00"+resp.metric
 		},
		data: [
			{
				type: "line", //change it to line, area, column, pie, etc
				 xValueFormatString: "00:00 hrs",
				 yValueFormatString: "00"+resp.metric,
				dataPoints: resp.data
			}
		]
	};

	$("#chartContainer1").CanvasJSChart(options);
 }