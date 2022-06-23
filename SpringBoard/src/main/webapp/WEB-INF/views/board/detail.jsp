<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
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
		<button type="button" id="replyModBtn">수정하기</button>
		<button type="button" id="replyDelBtn">삭제하기</button>
		<button type="button" id="closeBtn">닫기</button>
	</div>
</div>
<ul id="replies"></ul>
	
	<!-- 댓글 달리는 영역 -->
	<div class="row">
		<h3 class="text-primary">댓글</h3>
		<div id="replies">
			
		</div> 
	</div>
	
	<!-- 댓글 쓰기 -->
	<div>
		<div>
			REPLYER <input type="text" name="replyer" id="newReplyWriter">
		</div>
		<div>
			REPLY TEXT <input type="text" name="replytext" id="newReplyText">
		</div>
		<button id="replyAddBtn">ADD REPLY</button>
	</div>
	
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script type="text/javascript">
	
	let bno = ${board.bno};
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
					
					// UNIX 시간을 우리가 알고 있는 형식으로 바꿔보겠습니다
					let timestamp = this.updatDate;
					// UNIX 시간이 저장된 timestamp를 Date 생성자로 변환합니다
					let date = new Date(timestamp);
					// 변수 formateedTime에 변환된 시간을 저장해 출력해보겠습니다
					let formattedTime = `게시일 : \${date.getFullYear()}/\${(date.getMonth()+1)}/\${date.getDate()}`;
					
					str += `<div class='replyLi' data-rno='\${this.rno}'>
								<strong>@\${this.replyer}</strong> - \${formattedTime}<br/>
								<div class='replytext'>\${this.reply}</div>
								<button type='button' class='btn btn-info'>수정/삭제</button>
							</div>`;
			});
			$("#replies").html(str);
		});
	}
	getAllList();
	
	// 모달창 이벤트 위임
	$("#replies").on("click", ".replyLi button", function() { // replies인 이유 : 클릭했을때 버튼을 포함하면서도가장 작은영역
		// 4. 콜백함수 내부의 this는 내가 클릭한 button이 됩니다.
		// 선택 요소와 연관된 태그 고르기
		// 1. prev().prev() ... 등과 같이 연쇄적으로 prev, next를 걸어서 고르기
		// 2. prev("태그 선택자") 를 써서 뒤쪽이나 앞쪽 형제 중 조건에 맞는것만 선택
		// 3. siblings("태그선택자")는 next, prev 모두를 범위로 조회합니다.
		var reply = $(this).siblings(".replytext"); // this키워드를 쓰려면 화살표함수를 쓸수없으므로 function이라고 적어야함
		
		// .attr("태그 내 속성명") => 해당 속성에 부여된 값을 가져옵니다.
		// ex) <li data-rno="33"> => rno에 33을 저장해줍니다.
		var rno = reply.attr("data-rno");
		let replytext = reply.text();
		$(".modal-title").html(rno);
		$("#replyText").val(replytext);
		$("#modDiv").show("slow");
	});
	
	// 글 등록 로직
	$("#replyAddBtn").on("click", function() {
		var replyer = $("#newReplyWriter").val();
		var reply = $("#newReplyText").val();
		
		$.ajax({
			type : 'post',
			url : '/replies',
			headers: {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "POST"
			},
			dataType : 'text',
			data : JSON.stringify({
				bno : bno,
				replyer : replyer,
				reply : reply
			}),
			success : function(result) {
				if(result == 'SUCCESS'){  // replyController에 47열 SUCCESS와 대소문자 맞춰야함
					alert("등록 되었습니다.");
					getAllList();
					$("#newReplyWriter").val('');  // 등록후 빈칸으로 바꾸기
					$("#newReplyText").val('');
				}
			}
		});
	});

	
	
	</script>
	<script src="/resources/delete.js"></script>
	<script src="/resources/modify.js"></script>
	<script src="/resources/modalclose.js"></script>
</body>
</html>