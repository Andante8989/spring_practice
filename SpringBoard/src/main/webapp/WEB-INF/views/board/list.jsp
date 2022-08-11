<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table class="table table-hover table-primary">
		<thead>
			<tr>
				<th>글번호</th>
				<th>글제목</th>
				<th>글쓴이</th>
				<th>작성날짜</th>
				<th>수정날짜</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="board" items="${boardList }">
				<tr>
					<td>${board.bno }</td>
				    <td><a href="/board/detail?bno=${board.bno }&page=${pageMaker.cri.page }&searchType=${pageMaker.cri.searchType}&keyword=${pageMaker.cri.keyword}">${board.title }<span class="badge bg-dark">${board.replycount }</span></a></td>
					<td>${board.writer }</td>
					<td>${board.regDate }</td>
					<td>${board.updateDate }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<ul class="pagination">
		<c:if test="${pageMaker.prev }">
		    <li class="page-item">
		      <a class="page-link" href="/board/list?page=${pageMaker.startPage - 1 }&searchType=${pageMaker.cri.searchType}&keyword=${pageMaker.cri.keyword}" aria-label="Previous">
		        <span aria-hidden="true">&laquo;prev</span>
		      </a>
		    </li>
	   </c:if>
	   <c:forEach var="pNum" begin="${pageMaker.startPage }" end="${pageMaker.endPage }">
	   		<li class="page-item ${pNum eq pageMaker.cri.page ? 'active' : ''}  ">
		      <a class="page-link" href="/board/list?page=${pNum}&searchType=${pageMaker.cri.searchType}&keyword=${pageMaker.cri.keyword}" aria-label="Previous">
		        <span aria-hidden="true">${pNum }</span>
		      </a>
		    </li>
	   </c:forEach>
	   <c:if test="${pageMaker.next }">
	   		<li class="page-item">
	   		 <a class="page-link" href="/board/list?page=${pageMaker.endPage + 1 }&searchType=${pageMaker.cri.searchType}&keyword=${pageMaker.cri.keyword}" aria-label="next">
	   		 	<span aria-hidden="true">next&raquo;</span>
	   		 </a>
	   		</li>
	   </c:if>
    </ul>
 
	
	<form action="/board/insert" method="get">
		<input type="submit" value="글쓰기">
	</form>
	<form action="/board/list" method="get">
		<input type="submit" value="전체 글보기">
	</form>
	
	<select name="sarchType">
		<option value="n"
		<c:out value="${pageMaker.cri.searchType == null ? 'selected' : '' }" />>
		-
		</option>
		<option value="t"
		<c:out value="${pageMaker.cri.searchType eq 't' ? 'selected' : '' }" />>
		제목
		</option>
		<option value="c"
		<c:out value="${pageMaker.cri.searchType eq 'c' ? 'selected' : '' }" />>
		내용
		</option>
		<option value="w"
		<c:out value="${pageMaker.cri.searchType eq 'w' ? 'selected' : '' }" />>
		글쓴이
		</option>
		<option value="tc"
		<c:out value="${pageMaker.cri.searchType eq 'tc' ? 'selected' : '' }" />>
		제목 + 본문
		</option>
		<option value="cw"
		<c:out value="${pageMaker.cri.searchType eq 'cw' ? 'selected' : '' }" />>
		내용 + 글쓴이
		</option>
		<option value="tcw"
		<c:out value="${pageMaker.cri.searchType eq 'tcw' ? 'selected' : '' }" />>
		제목 + 내용 + 글쓴이
		</option>
	</select>
	<input type="text"
		name="keyword"
		id="keywordInput"
		value="${pageMaker.	cri.keyword }" >
	<button id="searchBtn">Search</button><br/>
	${pageMaker }
	
	<a href="/board/getMovie"><button id="movie">영화차트 보러가기</button></a>
	
	<script>
	<!--  검색버튼 작동 --> 
	$('#searchBtn' ).on("click", function(event){
	    
		self.location = "list"
			+ "?page=1"
			+ "&searchType="
			+ $('select option:selected').val()
			+ "&keyword=" + $("#keywordInput").val();
		
	})
	</script>
	
</body>
</html>