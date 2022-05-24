<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/bmi" method="post">
		<input type="number" placeholder="체중을 입력하세요" name="kg" required><br/>
		<input type="number" placeholder="키를 입력하세요" name="height" required><br/>
		<input type="submit" value="BMI 측정하기">
	</form>
</body>
</html>