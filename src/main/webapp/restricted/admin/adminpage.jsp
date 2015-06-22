<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="/include/header.jsp">
	<jsp:param name="title" value="Admin Page"/>
</jsp:include>

	<div id="flights-container" class="pull-left col-md-7">
	</div>

	<div id="flights-menu" class="pull-right col-md-5">
		<h2 id="flight-num"></h2>
		<form name="flights-menu-form" method="post">
			<div class="form-group">
				<label for="depAirport"><fmt:message key="flights-menu.from"/></label>
				<select id="depAirport" class="form-control" name="depAirport"></select>
			</div>
			<div class="form-group">
				<div class='input-group date' id='dtpFrom'>
					<input type='text' class="form-control" />
					<span class="input-group-addon">
						<span class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
			</div>
			<div class="form-group">
				<label for="arrAirport"><fmt:message key="flights-menu.to"/></label>
				<select id="arrAirport" class="form-control" name="arrAirport"></select>
			</div>
			<div class="form-group">
				<div class='input-group date' id='dtpTo'>
					<input type='text' class="form-control" />
					<span class="input-group-addon">
						<span class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
			</div>
			<div class="form-group">
				<label for="aircraft"><fmt:message key="flights-menu.aircraft"/></label>
				<select id="aircraft" class="form-control" name="aircraft"></select>
			</div>
			<button id="btn-new-flight" class="btn btn-primary">
				<fmt:message key="flights-menu.newFlight"/>
			</button>
			<button id="submit-flight" class="btn btn-primary">
				<fmt:message key="flights-menu.save"/>
			</button>
		</form>
	</div>

	<script type="text/javascript" src="/js/flightcentre.js"></script>	
	
<jsp:include page="/include/footer.jsp"/>