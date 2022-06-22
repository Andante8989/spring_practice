<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!--  .css, .js, 그림파일 등은 src/main/webapp/resources폴더 아래에 저장한다음
/resources/경로 형식으로 적으면 가져올 수 있습니다
이렇게 경로가 자동으로 잡히는 이유는 servlet-context.xml에 설정이 잡혀있기 때문입니다. -->
	<link rel="stylesheet" href="/resources/modal.css">
	
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h2>Ajax 테스트</h2>
	<!-- 댓글 표시되는 영역 -->
	<ul id="replies"></ul>
	
	<div>
		<div>
			REPLYER <input type="text" name="replyer" id="newReplyWriter">
		</div>
		<div>
			REPLY TEXT <input type="text" name="replytext" id="newReplyText">
		</div>
		<button id="replyAddBtn">ADD REPLY</button>
		<button id="replyInfo">Reply Info</button>
	</div>
	
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
	
	
	<!-- jquery는 이곳에 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script type="text/javascript">
		var bno = 124;
		
		
		// 댓글 전체 불러오기
		function getAllList() {
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
		
		////////////////////////////////////////
		//// 댓글 수정하기 이벤트 연결
		////////////////////////////////////////
		// 이벤트 위임
		
		// 1. ul#replies가 이벤트를 걸고 싶은 버튼 전체의 집합이므로 먼저 집합 전체에 이벤트를 겁니다
		// 2. #replies의 하위 항목중 최종 목표 태그를 기입해줍니다.
		// 3. 단, 여기서 #replies와 button 사이에 다른 태그가 끼어있다면 경유하는 형식으로 호출해도 됩니다.
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

		// 댓글 전체 조회버튼 이벤트 
		$("#replyInfo").on("click", function() {
			getAllList();
		});
		
		
		
		// 수정 버튼 이벤트
		
		

	</script>
		<!-- 댓글 삭제 이벤트 resources폴더로 이동후 링크로 보내기 
		css파일의 경우는 link태그의 href로 경로지정, js파일은 script로 경로지정을 합니다 -->
		<script src="/resources/delete.js"></script>
		<script src="/resources/modify.js"></script>
	
</body>
</html>