<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="/include/header.jsp">
	<jsp:param name="title" value="Dispatcher Page"/>
</jsp:include>

	<div id="flights-container" class="pull-left col-md-7">
	</div>

	<div id="flights-menu" class="pull-right col-md-5">
		<h2 id="flight-num"><fmt:message key="flights-menu.noFlightsSelected"/></h2>
		<div id="crew-list">
		</div>
		<form name="flights-menu-form" method="post">
			<button id="btn-clear-crew" class="btn btn-primary">
				<fmt:message key="flights-menu.clear"/>
			</button>
			<button id="btn-revert-crew" class="btn btn-primary">
				<fmt:message key="flights-menu.revert"/>
			</button>
			<button id="submit-crew" class="btn btn-primary">
				<fmt:message key="flights-menu.save"/>
			</button>
		</form>
	</div>

	<script type="text/javascript" src="/js/flightcentre.js"></script>	
	
<jsp:include page="/include/footer.jsp"/>