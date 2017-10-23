<%@page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ログイン</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="loginContents">
	<div class = "login">
		<c:if test="${ not empty errorMessages}">
			<div class="errorMessages">
				<c:forEach items="${errorMessages}" var="message">
					<c:out value="${message}" />
				</c:forEach>
			</div>
			<c:remove var="errorMessages" scope="session"/>
		</c:if>
		<span class="loginTitle">ログイン</span>
			<form action="login" method="post"><br />
				<label for="loginId">ログインID</label><br>
				<input name="loginId"value="${loginId}" id="loginId"/> <br><br />

				<label for="password">パスワード</label><br>
				<input name="password" type="password" id="password"/> <br><br />

				<input type="submit" value="ログイン" /> <br />
			</form>
		<br />
	</div>
</div>
</body>
</html>
