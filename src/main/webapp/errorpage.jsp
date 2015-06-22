<%@ page isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<title>Error</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href='<c:url value="/css/error-pages.css"/>' rel="stylesheet" type="text/css">
	<!--[if lte IE 7]><link rel="stylesheet" href="/web/20111215021944/http://l-stat.livejournal.com/framework/error-pages-ie.css"><![endif]-->
</head>
<body class="error-page">
	<div class="header">
		<img src='<c:url value="/img/frank-404.png"/>' alt="" class="pic" height="260" width="250">
		<div class="desc">
			<h1>${pageContext.errorData.statusCode}</h1>
			<p>
				<c:choose>
				<c:when test="${pageContext.errorData.statusCode == 404}">
					Page not found.
				</c:when>
				<c:otherwise>
					There was something wrong.<br>
					${pageContext.errorData.throwable.message}
				</c:otherwise>
				</c:choose>
			</p>
		</div>
	</div>
</body>
</html>