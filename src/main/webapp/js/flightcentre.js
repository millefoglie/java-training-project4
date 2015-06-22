$( document ).ready(function() {
	var ADMINPAGE = "adminpage.jsp";
	var DISPATCHERPAGE = "dispatcherpage.jsp";
	var URL = "ws://localhost:8080/restricted/flightcentre";

	/*
	 * SET UP LOCALISATION
	 */

	// shorthand for the localisation function
	var l = function(string) {
    	return string.toLocaleString();
	};

	var locale = $("html").attr("lang");

	if (locale.length) {
		String.locale = locale;
		String.toLocaleString("../../js/localisations.json");
	}

	/*
	 * SET UP CONTAINER DIMENSIONS
	 */
	 var windowHeight = $(window).height();

	$("#flights-container").height(windowHeight * 0.66);
	$("#flights-menu").height(windowHeight * 0.66);
	$("#crew-list").height(windowHeight * 0.66 - 150);
	 
	/*
	 * SET UP NICE SCROLL
	 */
	$("#flights-container").niceScroll();
	$("#crew-list").niceScroll();
	
	/*
	 * SET UP DATETIMEPICKER
	 */
	var dtpFrom = $("#dtpFrom");
	var dtpTo = $("#dtpTo");

	if (dtpFrom.length && dtpTo.length) {
		dtpFrom.datetimepicker({
			locale: navigator.browserLanguage
		});
		
		dtpTo.datetimepicker({
			locale: navigator.browserLanguage
		});
		
		dtpFrom.on("dp.change", function (e) {
			dtpTo.data("DateTimePicker").minDate(e.date);
		});
		
		dtpTo.on("dp.change", function (e) {
			dtpFrom.data("DateTimePicker").maxDate(e.date);
		});
		
		dtpFrom.data("DateTimePicker").enable();
		dtpFrom.data("DateTimePicker").useCurrent(true);
		dtpTo.data("DateTimePicker").enable();
		dtpTo.data("DateTimePicker").useCurrent(true);
	}

	/*
	 * SET UP WEBSOCKET
	 */
	var socket = new WebSocket(URL);

	// handler function for processing ws messages
	var processFlight;
	var processAirport;
	var processAircraft;
	var processEmployee;
	var processEmployeeForFlight;
	var flightOnClickListener;

	/*
	 * Open the websocket. Deferred is required so that
	 * messages are not sent before the socket is ready
	 */
	ws = (function($) {
		var readyPromise = $.Deferred();

		socket.onopen = function() {
			console.log("WebSocket was opened!");
			readyPromise.resolve(socket.send);
		}

		return readyPromise;
	})(jQuery);
	
	socket.onclose = function(event) {
		console.log("Websocket was closed. " + event.data);
	}
	
	socket.onerror = function(event) {
		console.log("Error: " + event.data);
	}

	socket.onmessage = function(event) {
		var obj = $.parseJSON(event.data);

		switch (obj.msg) {
		case "sendFlight":
			console.log("new flight obj received from server");
			processFlight(obj.flight);
			break;
		case "sendAirport":
			console.log("new airport obj received from server");
			processAirport(obj.airport);
			break;
		case "sendAircraft":
			console.log("new aircraft obj received from server");
			processAircraft(obj.aircraft);
			break;
		case "sendEmployee":
			console.log("new employee obj received from server");
			processEmployee(obj.employee);
			break;
		case "sendEmployeeForFlight":
			console.log("new employee obj for flight");
			processEmployeeForFlight(obj.employee);
			break;
		default:
			console.log("Could not process message: unknown message");
		}
	}
	
	// get initial data from ws
	ws.then(function() {

		// find which page we are on (are we admin or dispatcher)
		var path = window.location.href;
		var page = path.substring(path.lastIndexOf("/") + 1);

		processFlight = addFlightToPage;

		socket.send(makeMsg("getFlights"));
		console.log("sent getFlights");

		// different pages have different functionalities
		switch (page) {
		case ADMINPAGE:
			flightOnClickListener = flightOnClickListenerAdminPage;
			processAirport = addAirportToPage;
			processAircraft = addAircraftToPage;

			socket.send(makeMsg("getAirports"));
			socket.send(makeMsg("getAircrafts"));

			$("button#btn-new-flight").click();
			break;
		case DISPATCHERPAGE:
			flightOnClickListener = flightOnClickListenerDispatcherPage;
			processEmployee = addEmployeeToPage;
			processEmployeeForFlight = markEmployeeForFlight;

			socket.send(makeMsg("getEmployees"));
		}
	});

	/*
	 * SET UP EVENT LISTENERS FOR BUTTONS
	 */

	// New Flight on Adminpage
	$("button#btn-new-flight").click(function(event) {
		event.preventDefault();

		$("#flights-container").find(".selected").removeClass("selected");
		$("#flight-num").text(l("New Flight"));

		var form = $("form[name='flights-menu-form']");

		form.find("select#depAirport").val(-1);
		form.find("select#arrAirport").val(-1);
		form.find("select#aircraft").val(-1);

		form.find("#dtpFrom").data("DateTimePicker")
			.useCurrent(true);
		form.find("#dtpFrom").data("DateTimePicker").enable();
		form.find("#dtpTo").data("DateTimePicker")
			.useCurrent(true);
		form.find("#dtpTo").data("DateTimePicker").enable();
	});

	// Save on Adminpage
	$("button#submit-flight").click(function(event) {
		event.preventDefault();

		var form = $("form[name='flights-menu-form']");
		var flight = {};

		flight._id = $("#flights-container")
			.find(".selected").attr("id");

		if (flight._id) {
			flight._id =  parseInt(flight._id.replace(/[^\d]*/,""));
		} else {
			flight._id = -1;
		}

		flight.depAirportId = parseInt(
			form.find("select#depAirport").val());
		flight.arrAirportId = parseInt(
			form.find("select#arrAirport").val());
		flight.aircraftId = parseInt(
			form.find("select#aircraft").val());
		flight.depTimestamp = convertToMysqlDateTime(
			form.find("#dtpFrom").data("DateTimePicker").date().toDate());
		flight.arrTimestamp = convertToMysqlDateTime(
			form.find("#dtpTo").data("DateTimePicker").date().toDate());

		var obj = {
			msg : "sendFlight",
			flight : flight
		}

		ws.then(function() {
			socket.send(JSON.stringify(obj));
			console.log("sent a flight to server");
		});
	});

	// Clear on Dispatcherpage
	$("button#btn-clear-crew").click(function(event) {
		event.preventDefault();

		$("#crew-list").find("li").removeClass("selected");
	});

	// Revert on Dispatcherpage
	$("button#btn-revert-crew").click(function(event) {
		event.preventDefault();

		// same action as clicking on a flight entry
		$("#flights-container").find(".selected>a").click();
	});

	// Save on Dispatcherpage
	$("button#submit-crew").click(function(event) {
		event.preventDefault();

		var crew = [];

		var flightId = $("#flights-container")
			.find(".selected").attr("id");

		if (flightId) {
			flightId =  parseInt(flightId.replace(/[^\d]*/,""));
		} else {
			flightId = -1;
		}

		var selected = $("#crew-list").find(".selected");

		for (var i = 0; i < selected.length; i++) {
			var employeeId = $(selected.get(i)).attr("id");

			crew[i] = {
				_id: parseInt(employeeId.replace(/[^\d]*/, ""))
			};
		}

		var obj = {
			msg : "sendEmployeesForFlight",
			flightId : flightId,
			crew : crew
		}

		ws.then(function() {
			socket.send(JSON.stringify(obj));
			console.log("sent a flight to server");
		});
	});

	/*
	 * SET UP HANDLERS FOR PROCESSING WS MESSAGES
	 */
	var addFlightToPage = function(flight) {
		var div = $("div#flight" + flight._id);

		if (div.length) {
			$("div#flight" + flight._id).replaceWith(createNewFlightDiv(flight));
		} else {
			$("#flights-container").append(createNewFlightDiv(flight));
		}
	}

	var createNewFlightDiv = function(flight) {
		var oldDiv = $("div#flight" + flight._id);

		var a = $("<a href=''>");
		var outerDiv = $("<div>");

		outerDiv.attr("id", "flight" + flight._id);
		outerDiv.addClass("panel panel-default entry");

		var innerDiv = $("<div>");

		innerDiv.addClass("panel-body");

		var heading = $("<h4>" +l("Flight") + " #" + flight._id + "</h4>");

		var table = $("<table>");

		table.addClass("table");

		var depLoc = flight.depAirport.country + ", "
			+ flight.depAirport.city + ", "
			+ flight.depAirport.name + " "
			+ "(" + flight.depAirport.iataFaa + ")";
		var rowFrom = $("<tr><th>" + l("from") + ":</th><td>" 
			+ depLoc + "</td></tr>");
		var rowDepTS = $("<tr><td></td><td>" 
			+ flight.depTimestamp + "</td></tr>");

		var arrLoc = flight.arrAirport.country + ", "
			+ flight.arrAirport.city + ", "
			+ flight.arrAirport.name + " "
			+ "(" + flight.arrAirport.iataFaa + ")";
		var rowTo = $("<tr><th>" + l("to") + ":</th><td>" 
			+ arrLoc + "</td></tr>");
		var rowArrTS = $("<tr><td></td><td>" 
			+ flight.arrTimestamp + "</td></tr>");

		var aircraft = flight.aircraft.manufacturer + " "
			+ flight.aircraft.model + " "
			+ "(" + flight.aircraft.regName + ")";
		var rowAircraft = $("<tr><th>" + l("aircraft") 
			+ ":</th><td>" + aircraft + "</td></tr>");

		var crewInfo = "";
		var crewCountMap = flight.crewCountMap;

		for(var i = 0, end = crewCountMap.length; i < end; i++) {
			crewInfo += (crewCountMap[i].key
				+ " <span class='badge'>" 
				+ crewCountMap[i].value 
				+ "</span> ");
		}

		var rowCrew = $("<tr><th>" + l("crew") 
			+ ":</th><td>" + crewInfo + "</td></tr>");

		table.append(rowFrom);
		table.append(rowDepTS);
		table.append(rowTo);
		table.append(rowArrTS);
		table.append(rowAircraft);
		table.append(rowCrew);


		innerDiv.append(heading);
		innerDiv.append(table);
		a.append(innerDiv);
		outerDiv.append(a);

		a.click(flightOnClickListener(flight));

		return outerDiv;
	}

	var addAircraftToPage = function(aircraft) {
		var id = aircraft._id;
		var manufacturer = aircraft.manufacturer;
		var model = aircraft.model;
		var regName = aircraft.regName;

		var select = $("select#aircraft");
		var optgroup = select.find("optgroup[label='" + manufacturer + "']");

		var optionEntry = $("<option value=" + id + ">");

		optionEntry.text(model + " (" + regName + ")");

		if (!optgroup.length) {
			optgroup = $("<optgroup label='" + manufacturer + "'>");
		
			select.append(optgroup);
		}

		var option = optgroup.find("option[value='" + id + "']");
		
		if (option.length) {
			option.replaceWith(optionEntry);
		} else {
			optgroup.append(optionEntry);
		}
	}

	var addAirportToPage = function(airport) {
		var id = airport._id;
		var name = airport.name;
		var city = airport.city;
		var country = airport.country;
		var iataFaa = airport.iataFaa;

		var selectDep = $("select#depAirport");
		var selectArr = $("select#arrAirport");
		var optgroupDep = selectDep.find("optgroup[label='" + country + "']");
		var optgroupArr = selectArr.find("optgroup[label='" + country + "']");

		if (!optgroupDep.length) {
			optgroupDep = $("<optgroup label='" + country + "'>");
			optgroupArr = $("<optgroup label='" + country + "'>");
		
			selectDep.append(optgroupDep);
			selectArr.append(optgroupArr);
		}

		var option = $("<option value=" + id + ">");
		
		option.text(city + ", " + name + " (" + iataFaa + ")");

		var oldOption = optgroupDep.find("option[value='" + id + "']");

		if (oldOption.length) {
			optgroupDep.find("option[value='" + id + "']").replaceWith(option);
			optgroupArr.find("option[value='" + id + "']").replaceWith(option.clone());
		} else {
			optgroupDep.append(option);
			optgroupArr.append(option.clone());
		}
	}

	var addEmployeeToPage = function(employee) {
		var id = employee._id;
		var name = employee.name;
		var surname = employee.surname;
		var regCode = employee.regCode;
		var position = employee.position;
		var positionIdStr = position.replace(/\s/,"");

		var container = $("#crew-list");
		var positionDiv = container.find("#" + positionIdStr);

		if (!positionDiv.length) {
			positionDiv = $("<div id='" + positionIdStr + "'>");

			positionDiv.append($("<h3>" + position + "</h3>"));
			positionDiv.append($("<ul>"));
			container.append(positionDiv);
		}

		var list = positionDiv.find("ul");
		var listEntry = $("<li id='employee" + id + "'>");
		var link = $("<a href=''>");
		link.text(name + " " + surname + " (" + regCode + ") ");

		listEntry.append(link);
		list.append(listEntry);

		link.click(function(event) {
			event.preventDefault();
			listEntry.toggleClass("selected");
		});
	}


	var flightOnClickListenerAdminPage = function(flight) {
		return function(event) {
			event.preventDefault();

			$("#flights-container").find(".selected").removeClass("selected");

			// add class to the outerDiv, not to <a> element
			$(this).parent().addClass("selected");
			$("#flight-num").text(l("Flight") + " #" + flight._id);

			var form = $("form[name='flights-menu-form']");

			form.find("select#depAirport").val(flight.depAirport._id);
			form.find("select#arrAirport").val(flight.arrAirport._id);
			form.find("select#aircraft").val(flight.aircraft._id);

			form.find("#dtpFrom").data("DateTimePicker")
				.date(parseMysqlDateTime(flight.depTimestamp));
			form.find("#dtpFrom").data("DateTimePicker").enable();
			form.find("#dtpTo").data("DateTimePicker")
				.date(parseMysqlDateTime(flight.arrTimestamp));
			form.find("#dtpTo").data("DateTimePicker").enable();
		}
	}

	var flightOnClickListenerDispatcherPage = function(flight) {
		return function(event) {
			event.preventDefault();

			$("#flights-container").find(".selected").removeClass("selected");

			// add class to the outerDiv, not to <a> element
			$(this).parent().addClass("selected");
			$("#flight-num").text(l("Flight") + " #" + flight._id);

			var container = $("#crew-list");
			
			container.find("li.selected").removeClass("selected");

			// request employees list for the flight
			ws.then(function() {
				var request = {
					msg : "getEmployeesForFlight",
					flightId : flight._id
				};

				socket.send(JSON.stringify(request));
				console.log("sent a request");
			});
		}
	}

	var markEmployeeForFlight = function(employee) {
		$("#crew-list").find("li#employee" + employee._id)
			.addClass("selected");
	}

	/*
	 * UTILITY FUNCTIONS
	 */

	var parseMysqlDateTime = function(dt) {
		var t = dt.split(/[- :]/);
	 	var d = new Date(t[0], t[1]-1, t[2], t[3], t[4], t[5]);
	 	return d;
	}

	var convertToMysqlDateTime = function(d) {
	 	return (d.getFullYear() + "-"
	 		+ (d.getMonth() + 1) + "-"
	 		+ d.getDate() + " "
	 		+ d.getHours() + ":"
	 		+ d.getMinutes() + ":"
	 		+ d.getSeconds() + "."
	 		+ 0);
	}

	var makeMsg = function(msg) {
	 	return JSON.stringify({
	 		msg : msg
	 	});
	}
});