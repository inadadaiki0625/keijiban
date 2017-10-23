<%@page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新規投稿</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="contributionContents">
	<a href="./">戻る</a>
	<div class="contribution">
		<c:if test="${ not empty errorMessages}">
			<div class="errorMessages">
				<c:forEach items="${errorMessages}" var="message">
					<c:out value="${message}" /><br>
				</c:forEach>
			</div>
			<c:remove var="errorMessages" scope="session"/>
		</c:if>
		<span class="contributionTitle">新規投稿</span>
		<form action="message" method="post">
			<div class="select">
				<label for="subject">件名（30文字以内で入力）</label><br>
				<input name="subject" value="${subject}" id="subject" size="30"/><br>
			</div>
			<div class="select">
				<label for="category">カテゴリー（10文字以内で入力）</label><br>
				<input name="category" value="${category}" id="category" size="30"><br>
			</div>
			<div class="select">
				<label for="text">本文（1000文字以内で入力）</label><br />
				<textarea name="text" rows="10" cols="50" id="text">${text}</textarea>
			</div>
			<div class="select">
				<input type="submit" value="投稿">
			</div>
		</form>
	</div>
</div>
</body>
</html>