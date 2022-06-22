<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/resources/modal.css">
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
	<input type="hidden" name="keyword" value="${param.keyword }">
	<input type="hidden" name="searchType" value="${param.searchType }">
	<input type="hidden" name="page" value="${param.page }">
	<input type="submit" value="삭제하기">
</form>
<a href="/board/list?page=${param.page}&searchType=${param.searchType}&keyword=${param.keyword}"><button>목록으로 돌아가기</button></a>
<form action="/board/updateForm" method="post">
	<input type="hidden" name="bno" value="${board.bno }">
	<input type="hidden" name="keyword" value="${param.keyword }">
	<input type="hidden" name="searchType" value="${param.searchType }">
	<input type="hidden" name="page" value="${param.page }">
	<input type="submit" value="수정하기">
</form>
<div id="modDiv" style="display:none;">
	<div class="modal-title"></div>
	<div>
		<input type="text" id="replyText">
	</div>
	<div>
		<button type="button" id="replyModBtn">Modify</button>
		<button type="button" id="replyDelBtn">Delete</button>
		<button type="button" id="closeBtn">Close</button>
	</div>
</div>
<ul id="replies"></ul>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script type="text/javascript">
	// 댓글 전체 불러오기
	function getAllList() {
		let bno = ${board.bno};
		let str = "";
		// json 데이터를 얻어오는 로직 실행
		$.getJSON("/replies/all/" + bno, function(data) {
			$(data).each(
				function() {
					console.log(this);
					// 백틱(``) 문자열 사이에 변수를 넣고 싶다면 \${변수명}을 적습니다
					// 원래는 \를 왼쪽에 붙일 필요는 없지만
					// jsp에서는 el표기문법이랑 겹치기 때문에 el이 아님을 보여주기 위해
					// 추가로 왼쪽에 \를 붙입니다.
					str += `<li data-rno='\${this.rno}' class='replyLi'>\${this.rno}:\${this.reply}<button>수정/삭제</button></li>`;
			});
			$("#replies").html(str);
		});
	}
	getAllList();
	
	$("#replies").on("click", ".replyLi button", function() { // replies인 이유 : 클릭했을때 버튼을 포함하면서도가장 작은영역
		// 4. 콜백함수 내부의 this는 내가 클릭한 button이 됩니다.
		var reply = $(this).parent(); // this키워드를 쓰려면 화살표함수를 쓸수없으므로 function이라고 적어야함
		
		// .attr("태그 내 속성명") => 해당 속성에 부여된 값을 가져옵니다.
		// ex) <li data-rno="33"> => rno에 33을 저장해줍니다.
		var rno = reply.attr("data-rno");
		let replytext = reply.text();
		$(".modal-title").html(rno);
		$("#replyText").val(replytext);
		$("#modDiv").show("slow");
	});

	</script>
</body>
</html>