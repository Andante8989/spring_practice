<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/board/update" method="post">
	글 제목 : <input type="text" name="title" value="${board.title }" readonly><br/>
	<p>글 내용</p><textarea cols="50" rows="12" name="content" >${board.content }</textarea><br/>
	작성자 : <input type="text" name="writer" value="${board.writer }" readonly><br/>
	<input type="hidden" name="bno" value="${board.bno }">
	<input type="hidden" name="keyword" value="${param.keyword }">
	<input type="hidden" name="searchType" value="${param.searchType }">
	<input type="hidden" name="page" value="${param.page }">
	<input type="submit" value="수정하기">
</form>
</body>
</html>