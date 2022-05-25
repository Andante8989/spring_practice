<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="http://localhost:8181/getVO" method="get">
		<input type="number" name="age" placeholder="나이를 입력하세요"></br>
		<input type="text" name="name" placeholder="이름을 입력하세요"></br>
		<input type="number" name="level" placeholder="레벨을 입력하세요"></br>
		<input type="text" name="address" placeholder="주소를 입력하세요"></br>
		<input type="submit" value="제출">
	</form>
</body>
</html>