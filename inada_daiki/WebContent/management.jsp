<%@page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー管理</title>
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<script type="text/javascript">
	<!--
		function check(){
		if(window.confirm('状態を変更してよろしいですか？')){ // 確認ダイアログを表示
			return true; // 「OK」時は送信を実行
		}
		else{ // 「キャンセル」時の処理
			window.alert('キャンセルされました'); // 警告ダイアログを表示
			return false; // 送信を中止
			}
		}
	//-->
	</script>
</head>
<body>
<div class="contents">
	<a href="signup">新規登録</a>
	<a href="./">戻る</a><br>
	<div class="userList">
		<span class="userListTitle">ユーザー管理</span>
	<div class="errorMessages">
		<c:if test="${ not empty errorMessages}">
				<div class="errorMessages">
					<c:forEach items="${errorMessages}" var="message">
						<c:out value="${message}" />
					</c:forEach>
				</div>
			<c:remove var="errorMessages" scope="session"/>
		</c:if>
	</div>
	<h3>ユーザー情報一覧</h3>
		<table class="list" >
			<tr>
				<th scope="row">ID</th>
				<th scope="row">名前</th>
				<th scope="row">支店</th>
				<th scope="row">部署・役職</th>
				<th scope="row">状態</th>
				<th scope="row">編集</th>
			</tr>
			<tbody>
				<c:forEach items = "${users}" var = "user">
					<tr>
						<td><c:out value="${user.loginId}"/></td>
						<td><c:out value="${user.name}"/></td>
						<td class="short">
							<c:forEach items= "${branches}" var= "branch">
								<c:if test="${user.branchId == branch.id}">
									<c:out value="${branch.name}"></c:out>
								</c:if>
							</c:forEach>
						</td>
						<td class="short">
							<c:forEach items= "${departments}" var= "department">
								<c:if test="${user.departmentId == department.id}">
									<c:out value="${department.name}"></c:out>
								</c:if>
							</c:forEach>
						</td>
						<td class="short">
							<c:if test="${loginUser.id == user.id}">
								<c:out value="ログイン中"></c:out>
							</c:if>
							<c:if test="${loginUser.id != user.id}">
								<c:if test="${user.isWorking == 1}">
									<form action="management" method="post" onSubmit="return check()">
										<input type="hidden" name="isWorking" value="${0}">
										<input type="hidden" name="id" value="${user.id}">
										<input type="submit" value="利用可" style="WIDTH: 80px;"/>
									</form>
								</c:if>
								<c:if test="${user.isWorking != 1}">
									<form action="management" method="post" onSubmit="return check()">
										<input type="hidden" name="isWorking" value="${1}">
										<input type="hidden" name="id" value="${user.id}">
										<input type="submit" value="停止中" style="WIDTH: 80px;background-color:#ff8668"/>
									</form>
								</c:if>
							</c:if>
						</td>
						<td class="short">
							<form action="settings" method="get">
								<input type="hidden" name="id" value="${user.id}">
								<input type="submit" value="編集"style="WIDTH: 80px;" />
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</body>
</html>

