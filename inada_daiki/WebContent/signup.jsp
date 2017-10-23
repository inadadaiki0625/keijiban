<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー新規登録</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">
	<div class="return"><a href="management">戻る</a></div>
	<div class="signUp">
		<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<c:forEach items="${errorMessages}" var="message"><br>
					<c:out value="${message}" />
				</c:forEach>
			</div>
			<c:remove var="errorMessages" scope="session"/>
		</c:if>
		<span class="signUpTitle">ユーザー新規登録</span>
		<form action="signup" method="post"><br />
			<div class="select">
				<label for="loginId">ログインID（半角英数字6～20文字）</label><br>
				<input name="loginId"value="${loginId}" id="loginId"size="25"/><br />
			</div>
			<div class="select">
				<label for="name">名前（1～10文字）</label><br>
				<input name="name" value="${name}" id="name"size="25"/><br />
			</div>
			<div class="select">
				<label for="password">パスワード（半角英数字記号6～20文字）</label><br>
				<input name="password" type="password" id="password" size="25"/> <br />
			</div>
			<div class="select">
				<label for="checkPassword">確認用パスワード（半角英数字記号6～20文字）</label><br>
				<input name="checkPassword" type="password" id="checkPassword" size="25"/><br />
			</div>
			<div class="select">
				<label for="branchId">支店名</label><br>
				<select name="branchId">
					<c:forEach items= "${branches}" var= "branch" >
							<option value="${branch.id}">${branch.name}</option>
					</c:forEach>
				</select><br />
			</div>
			<div class="select">
				<label for="departmentId">部署・役職</label><br>
				<select name="departmentId">
					<c:forEach items= "${departments}" var= "department">
							<option value="${department.id}">${department.name}</option>
					</c:forEach>
				</select><br /><br />
			</div>
			<input type="hidden" name="isWorking" value="${1}" id="isWorking"/>

			<input type="submit" value="登録" /> <br />
		</form>
	</div>
</div>
</body>
</html>
