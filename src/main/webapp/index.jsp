<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="flt" uri="WEB-INF/aviatags.tld"%>

<jsp:include page="/include/header.jsp">
	<jsp:param name="title" value="Home"/>
</jsp:include>

<div class="jumbotron">
	<div class="container">
		<h1>Kiss Air</h1>
		<p>
			<fmt:message key="index.motto" />
		</p>
	</div>
</div>

<jsp:include page="/nextflights" flush="true" />

<table class="table table-striped">
	<tr>
		<th><fmt:message key="index.departure" /></th>
		<th></th>
		<th><fmt:message key="index.arrival" /></th>
		<th></th>
		<th><fmt:message key="index.aircraft" /></th>
	</tr>
	<c:forEach items="${requestScope.flights}" var="flight">
		<tr>
			<td><flt:depLoc flight="${flight}" /></td>
			<td><flt:depTimestamp flight="${flight}" /></td>
			<td><flt:arrLoc flight="${flight}" /></td>
			<td><flt:arrTimestamp flight="${flight}" /></td>
			<td><flt:aircraft flight="${flight}" /></td>
		</tr>
	</c:forEach>
</table>

<jsp:include page="/include/footer.jsp"/>