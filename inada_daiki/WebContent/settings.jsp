<%@page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー情報編集</title>
	<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">
	<div class="return"><a href="management">戻る</a></div>
	<div class="settings">
		<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<c:forEach items="${errorMessages}" var="message">
					<c:out value="${message}" /><br>
				</c:forEach>
			</div>
		<c:remove var="errorMessages" scope="session"/>
		</c:if>
			<form action="settings" method="post"><br />
				<span class="settingsTitle">ユーザー情報編集</span><br>
				<input type="hidden" name="id" value="${editUser.id}" id="id"/>
				<div class="select">
					<label for="loginId">ログインID（半角英数字6～20文字）</label><br>
					<input name="loginId" value="${editUser.loginId}" /><br>
				</div>
				<div class="select">
					<label for="name">名前（1～10文字）</label><br>
					<input name="name" value="${editUser.name}" id="name"/><br>
				</div>
				<div class="select">
					<label for="password">パスワード（半角英数字記号6～20文字）</label><br>
					<input name="password" type="password" id="password" placeholder="変更しない場合は空欄"/><br>
				</div>
				<div class="select">
					<label for="checkPassword">確認用パスワード（半角英数字記号6～20文字）</label><br>
					<input name="checkPassword" type="password" id="checkPassword" placeholder="変更しない場合は空欄"/><br>
				</div>
				<div class="select">
				<label for="branchId">支店</label><br>
					<c:if test="${editUser.id == loginUser.id}">
						<select name="branchId">
							<c:forEach items= "${branches}" var= "branch">
								<c:if test="${editUser.branchId == branch.id}">
									<option value="${branch.id}" selected>${branch.name}</option>
								</c:if>
							</c:forEach>
						</select><br>
						<c:out value="変更できません"></c:out><br />
					</c:if>
					<c:if test="${editUser.id != loginUser.id}">
						<select name="branchId">
							<c:forEach items= "${branches}" var= "branch">
								<c:if test="${editUser.branchId == branch.id}">
									<option value="${branch.id}" selected>${branch.name}</option>
								</c:if>
								<c:if test="${editUser.branchId != branch.id}">
									<option value="${branch.id}">${branch.name}</option>
								</c:if>
							</c:forEach>
						</select><br>
					</c:if>
				</div>
				<div class="select">
				<label for="departmentId">部署・役職</label><br>
					<c:if test="${editUser.id == loginUser.id}">
						<select name="departmentId">
							<c:forEach items= "${departments}" var= "department">
								<c:if test="${editUser.departmentId == department.id}">
									<option value="${department.id}"selected>${department.name}</option>
								</c:if>
							</c:forEach>
						</select><br>
						<c:out value="変更できません"></c:out><br />
					</c:if>
					<c:if test="${editUser.id != loginUser.id}">
						<select name="departmentId">
							<c:forEach items= "${departments}" var= "department">
								<c:if test="${editUser.departmentId == department.id}">
									<option value="${department.id}"selected>${department.name}</option>
								</c:if>
								<c:if test="${editUser.departmentId != department.id}">
									<option value="${department.id}">${department.name}</option>
								</c:if>
							</c:forEach>
						</select><br>
					</c:if>
				</div>
			<input type="submit" value="登録" /><br />
		</form>
	</div>
</div>
</body>
</html>
