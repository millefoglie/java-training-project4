<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header-empty.jsp"/>

	<nav id="header-nav" class="navbar navbar-default navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href="/index.jsp">Kiss Air</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
          	<c:if test='${pageContext.request.isUserInRole("admin")}'>
				<li><a href="/restricted/admin/adminpage.jsp">
				<fmt:message key="flightsCentre"/>
				</a></li>
			</c:if>
			<c:if test='${pageContext.request.isUserInRole("dispatcher")}'>
				<li><a href="/restricted/dispatcher/dispatcherpage.jsp"><fmt:message key="assignCrews"/></a></li>
			</c:if>
          </ul>

            <div class="nav navbar-nav navbar-right">
			<c:choose>
			<c:when test="${empty pageContext.request.userPrincipal}">
				<form class="navbar-form navbar-right" method="post" action="/j_security_check">
					<div class="form-group form-group-sm">
						<input type="text" name="j_username" class="form-control" id="username" placeholder='<fmt:message key="username"/>'>
					</div>
					<div class="form-group form-group-sm">
						<input type="password" name="j_password" class="form-control" id="password"
						placeholder='<fmt:message key="password"/>'>
					</div>
					<button id="sign-in" type="submit" class="btn btn-primary btn-sm"><fmt:message key="signIn"/></button>
				</form>
			</c:when>
			<c:otherwise>
				<div class="navbar-form navbar-right">
					<div class="btn-group">
						<button type="button" class="btn btn-primary btn-sm dropdown-toggle"
							data-toggle="dropdown" aria-expanded="false">
							<c:out value="${pageContext.request.userPrincipal.name}"/> 
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="/signout"><fmt:message key="signOut"/></a></li>
						</ul>
					</div>
				</div>
			</c:otherwise>
			</c:choose>
		</div>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

    <div id="lang-select" class="pull-right">
		<a href="/settings?locale=uk-UA">Українська</a>
		<a href="/settings?locale=en-GB">English</a>
	</div>

	<div id="wrapper" class="container">
