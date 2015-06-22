<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="/include/header-empty.jsp">
	<jsp:param name="title" value="Sign In" />
</jsp:include>

<div class="container">
	<form class="form-signin" method="post" action="j_security_check">
		<h2 class="form-signin-heading">
			<c:choose>
			<c:when test="${param.error == true}">
				<span class="text-danger"><fmt:message key="signinpage.failedsigninmessage"/></span>
			</c:when>
			<c:otherwise>
				<fmt:message key="signinpage.signinmessage"/>
			</c:otherwise>
			</c:choose>
		</h2>
		<div class="form-group">
			<label for="username"><fmt:message key="signinpage.username"/></label>
			<input type="text" class="form-control" id="username" name="j_username"
			placeholder='<fmt:message key="signinpage.username"/>' required autofocus>
		</div>
		<div class="form-group">
			<label for="password"><fmt:message key="signinpage.password"/></label>
			<input type="password" class="form-control" id="password" name="j_password"
			placeholder='<fmt:message key="signinpage.password"/>' required>
		</div>
		<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
	</form>
</div>