<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>사용자 생성 로그인 폼</h1>
	<h2><c:out value="${error }" /></h2>
	<h2><c:out value="${logout }" /></h2>
	
	<form action="/login" method="post">
		아이디 : <input type="text" name="username" ></br>
		비밀번호 : <input type="text" name="password" ></br>
		<input type="submit" value="로그인하기">
		<!-- 삭제하면 admin/admin 입력해도 로그인 실패함 -->
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
	</form>
</body>
</html>