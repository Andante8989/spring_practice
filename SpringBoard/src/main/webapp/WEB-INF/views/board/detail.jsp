<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
${board }<br/>
글번호 : ${board.bno }<br/>
글 제목 : <input type="text" value="${board.title }" readonly> 글쓴이 : ${board.writer }<br/>
글 내용 : <textarea style="height:100px;">${board.content }</textarea></br>
작성날짜 : ${board.regDate }<br/>
수정날짜 : ${board.updateDate }
<form action="/board/delete" method="post">
	<input type="hidden" name="bno" value="${board.bno }">
	<input type="submit" value="삭제하기">
</form>
<form action="/board/list" method="get">
	<input type="submit" value="목록으로 돌아가기">
</form>
<form action="/board/updateForm" method="post">
	<input type="hidden" name="bno" value="${board.bno }">
	<input type="submit" value="수정하기">
</form>
</body>
</html>