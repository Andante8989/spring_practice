<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>글쓰기</h2>
<form action="/board/insert" method="post">
	글 제목 : <input type="text" name="title" placeholder="글 제목을 적어주세요" required><br/>
	<p>글 내용</p><textarea cols="50" rows="12" name="content" required></textarea><br/>
	작성자 : <input type="text" name="writer" placeholder="작성자를 적어주세요" required><br/>
	<input type="submit" value="작성하기">
</form>
</body>
</html>