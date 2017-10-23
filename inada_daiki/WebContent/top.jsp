<%@page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ホーム</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
	<!--
		function check(){
		if(window.confirm('削除してよろしいですか？')){ // 確認ダイアログを表示
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

<div class="header">
	<c:if test="${ not empty loginUser }">
		<c:if test="${loginUser.departmentId == 1}">
			<a href="management">ユーザー管理</a>
		</c:if>
		<a href="message">新規投稿</a>
		<a href="logout">ログアウト</a>
	</c:if>
</div>
	<div class="error">
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
	</div>
<c:if test="${ not empty loginUser}">
	<div class="profile">
		<div class="name">ログインユーザー：<c:out value="${loginUser.name}" /></div>
	</div>
</c:if>
</div>

<div class = "search">
	<form action="./" method="get">
		<div class="select">
			<input type="date" name="start" value="${start}"> ～
			<input type="date" name="end" value="${end}"><br>
		</div>
		<div class="select">
			<input type="text" name="category" value="${category}" list="categoryList"  placeholder="カテゴリーを入力or選択"
			 class="categorySearch">
			<datalist id="categoryList">
				<c:forEach items="${categorys}" var="category">
					<option value="${category.category}">${category.category}</option>
				</c:forEach>
			</datalist>
		</div>
		<div class="select">
			<input type="submit" value="検索">
		</div>
	</form>
	<form action="./">
		<input type="submit" value="検索リセット"><br>
	</form>
</div>

<div class="messages">

	<c:forEach items="${messages}" var="message">
		<div class="message">
		<div class="subject">　件名　　　：<c:out value="${message.subject}" /></div>
			<div class="category"> 　カテゴリー：<c:out value="${message.category}" /></div>
		<%--改行処理 --%>
		<div class="text">
		<c:forEach var="text" items="${ fn:split(message.text,'
		') }" >
			<c:out value="${text}"/><br>
		</c:forEach>
		</div>
		<div class="user">
			<c:forEach items= "${branches}" var= "branch">
				<c:if test="${message.branch_id == branch.id}">
					<c:out value="${branch.name}"></c:out>
				</c:if>
			</c:forEach>/
			<c:forEach items= "${departments}" var= "department">
				<c:if test="${message.department_id == department.id}">
					<c:out value="${department.name}"></c:out>
				</c:if>
			</c:forEach>/
	    	<c:out value="${message.name}" />
		</div>
		<div class="date"><fmt:formatDate value="${message.created_at}" pattern="yyyy/MM/dd HH:mm" /></div>
		<%--人事担当者、社員 --%>
		<c:if test="${loginUser.departmentId == 1 || loginUser.departmentId ==4}">
			<c:if test="${loginUser.id == message.user_id}">
				<form action="deletemessage" method="post" onSubmit="return check()">
					<input type="hidden" name="contributions_id" value="${message.contributions_id}">
					<input type="submit" value="投稿削除" class="delete" />
				</form>
			</c:if>
		</c:if>
		<%--情報管理担当者 --%>
		<c:if test="${loginUser.departmentId == 2}">
			<form action="deletemessage" method="post"  onSubmit="return check()" >
				<input type="hidden" name="contributions_id" value="${message.contributions_id}">
				<input type="submit" value="投稿削除"class="delete" />
			</form>
		</c:if>
		<%-- 支店長--%>
		<c:if test="${loginUser.departmentId == 3}">
			<c:if test="${loginUser.branchId == message.branch_id}">
				<form action="deletemessage" method="post"  onSubmit="return check()">
					<input type="hidden" name="contributions_id" value="${message.contributions_id}">
					<input type="submit" value="投稿削除" class="delete"/>
				</form>
			</c:if>
		</c:if>
		</div>

		<c:forEach items="${comments}" var="comment">
			<c:if test="${message.contributions_id == comment.contributionId}">
				<div class="comments">
					<div class="comment">
						<c:forEach var="text" items="${fn:split(comment.text,'
						')}">
						<c:out value="${text}"/><br>
						</c:forEach>
					</div>
					<div class="user">
						<c:forEach items= "${branches}" var= "branch">
							<c:if test="${comment.branchId == branch.id}">
								<c:out value="${branch.name}"></c:out>
							</c:if>
						</c:forEach>/
						<c:forEach items= "${departments}" var= "department">
							<c:if test="${comment.departmentId == department.id}">
								<c:out value="${department.name}"></c:out>
							</c:if>
						</c:forEach>/
						<span class="user_id"><c:out value="${comment.name}"/></span>
					</div>
					<div class="date">
						<fmt:formatDate value="${comment.createdAt}" pattern="yyyy/MM/dd HH:mm" />
					</div>
					<%--人事担当者、社員 --%>
					<c:if test="${loginUser.departmentId == 1 || loginUser.departmentId ==4}">
						<c:if test="${loginUser.id == comment.userId}">
							<form action="deletecomment" method="post" onSubmit="return check()">
								<input type="hidden" name="commentId" value="${comment.commentId}">
								<input type="submit" value="コメント削除"class="deleteComment" /><br />
							</form>
						</c:if>
					</c:if>
					<%--情報管理担当者 --%>
					<c:if test="${loginUser.departmentId == 2}">
						<form action="deletecomment" method="post" onSubmit="return check()">
							<input type="hidden" name="commentId" value="${comment.commentId}">
							<input type="submit" value="コメント削除"class="deleteComment" /><br />
						</form>
					</c:if>
					<%-- 支店長--%>
					<c:if test="${loginUser.departmentId == 3}">
						<c:if test="${loginUser.branchId == comment.branchId}">
							<form action="deletecomment" method="post" onSubmit="return check()">
								<input type="hidden" name="commentId" value="${comment.commentId}">
								<input type="submit" value="コメント削除"class="deleteComment" /><br />
							</form>
						</c:if>
					</c:if>
				</div>
			</c:if>
		</c:forEach>
		<div class="commentSubmit">
			<form action="comments" method="post">
				<label for="text">コメント（500文字以内で入力）</label> <br />
				<textarea name="text"rows="5" cols="40" id="text" class="comment"></textarea> <br />
				<div class="select">
					<input type="hidden" name="contributions_id" value="${message.contributions_id}">
					<input type="submit" value="コメント投稿"/><br>
				</div>
			</form>
		</div>
	</c:forEach>
</div>
</body>
</html>
