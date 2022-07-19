<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<!DOCTYPE html>
<html>
<head>
<style>
	#uploadResult {
		width : 100%;
		background-color : gray;
	}
	
	#uploadResult ul {
		display : flex;
		flex-flow : row;
		justify-content : center;
		align-item : center;
	}
	
	#uploadResult ul li {
		list-style : none;
		padding : 10px;
		align-content : center;
		text-align : center;
	}
	
	#uploadResult ul li img {
		width : 100px;
	}
</style>
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
	<input type="submit" id="submitBtn" value="수정하기" class="btn btn-primary">
	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
</form>

<div class="row">
	<h3 class="text-primary">첨부파일</h3>
	<div class="form-group uploadDiv">
		<input type="file" name="uploadFile" multiple>	
	</div>
	<button id="uploadBtn">등록</button>
	<div id="uploadResult">
		<ul>
			<!-- 첨부파일이 들어갈 위치 -->
			
		</ul>
	</div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script type="text/javascript">
	var csrfHeaderName = "${_csrf.headerName}"
		var csrfTokenValue="${_csrf.token}"
	
	var regex = new RegExp("(.*)\.(exe|sh|zip|alz)$");
	// 파일이름 . exe|sh|zip|alz인 경우를 체크함			
	var maxSize = 5242880; // 5Mb
	
	function checkExtension(fileName, fileSize) {
		console.log(fileName);
		if(fileSize >= maxSize) {
			alert("파일 사이즈 초과");
			return false;
		}
		if (regex.test(fileName)) {
			alert("해당 종류의 파일은 업로드할 수 없습니다");
			return false;
		}
		return true;
	}

$(document).ready(function() {
	
	let bno = ${board.bno};
(function() {
		$.getJSON("/board/getAttachList", {bno : bno}, function(arr) {
			console.log(arr);
			
			let str = "";
			
			$(arr).each(function(i, attach) {
				// 이미지파일인 경우
				if(attach.fileType) {
					let fileCallPath = encodeURIComponent(attach.uploadPath + "/s_" + 
							attach.uuid + "_" + attach.fileName);
					console.log(fileCallPath);
					str += `<li data-path='\${attach.uploadPath}' data-uuid='\${attach.uuid}' data-filename='\${attach.fileName}' data-fileType='\${attach.fileType}'>
								<div>
									<span>\${attach.fileName}</span>
									<button type='button' data-file='\${fileCallPath}' data-type='image' class='btn btn-warning btn-circle'>
									<i class='fa fa-times'></i></button><br>
									<img src='/board/display?fileName=\${fileCallPath}'>
								</div>
							</li>`;
				} else {
					let fileCallPath = encodeURIComponent(attach.uploadPath + "/" + 
							attach.uuid + "_" + attach.fileName);
					// 이미지가 아닌 파일의 경우
					str += `<li data-path='\${attach.uploadPath}' data-uuid='\${attach.uuid}' data-filename='\${attach.fileName}' data-type='\${attach.fileType}'>
								<div>
									<span>\${attach.fileName}</span><br/>
									<button type='button' data-file='\${fileCallPath}' data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>
									<img src='/resources/attach.png' width='100px' height='100px'>
								</div>
							</li>`;
				}
			});
			//위에서 str변수에 작성된 태그 형식을 화면에 끼워넣기
			$("#uploadResult ul").html(str);
		});
	})(); // 익명함수 종료
});

$("#uploadResult").on("click", "button", function(e) {
	if (confirm("선택한 파일을 삭제하시겠습니까?")) {
		let targetLi = $(this).closest("li");
		targetLi.remove();
	}
})


		let cloneObj = $(".uploadDiv").clone();
			$('#uploadBtn').on("click", function(e) {
				
				var formData = new FormData();
				
				var inputFile = $("input[name='uploadFile']");
				
				var files = inputFile[0].files;
				
				console.log(files);

				// 파일 데이터를 폼에 넣기
				for(var i = 0; i < files.length; i++) {
					if(!checkExtension(files[i].name, files[i].size)) {
						return false; // 조건에 맞지않은 파일 포함시 onclick 이벤트 함수자체를 종료시킴
					}
					formData.append("uploadFile", files[i]);
				}
				$.ajax({
					 beforeSend : function(xhr) {
						 xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
						 },

					url: '/board/uploadFormAction',
					processData: false,
					contentType: false,
					data: formData,
					dataType: 'json',
					type: 'POST',
					success: function(result) {
						alert("Uploaded");
						console.log(result);
						showUploadedFile(result);
						$(".uploadDiv").html(cloneObj.html());
					}
				}); // ajax 끝
				

			}); // uploadBtn onclick
			
		let uploadResult = $("#uploadResult ul");
			
			function showUploadedFile(uploadResultArr) {
				let str = "";
				
				$(uploadResultArr).each(function(i, obj) {
					
					if(!obj.image) {
						
						let fileCallPath = encodeURIComponent(
								obj.uploadPath + "/"
							+   obj.uuid + "_" + obj.fileName);
						str += `<li data-path='\${obj.uploadPath}' data-uuid='\${obj.uuid}' data-filename='\${obj.fileName}' data-type='\${obj.image}'>
								<span>\${obj.fileName}</span>
								<button type='button' data-file='\${fileCallPath}' data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>
								<img src='/resources/attach.png' width='100px' height='100px'>
								</li>`;
					} else {
						//str += `<li>\${obj.fileName}</li>`;
						let fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" +
															obj.uuid + "_" + obj.fileName);
						
						let fileCallPath2 = encodeURIComponent(obj.uploadPath + obj.uuid + "_" + obj.fileName);
						
						console.log(fileCallPath);
						console.log(fileCallPath2);
						str += `<li data-path='\${obj.uploadPath}' data-uuid='\${obj.uuid}' data-filename='\${obj.fileName}' data-type='\${obj.image}'>
							<div>
							<span>\${obj.fileName}</span>
							<button type='button' data-file='\${fileCallPath}' data-type='image' class='btn btn-warning btn-circle'>
							<i class='fa fa-times'></i></button><br>
							<img src='/board/display?fileName=\${fileCallPath}'>
							</div>
						</li>`;
					}
					
				});
				uploadResult.append(str);
			} // showUploadedFile

			$("#submitBtn").on("click", function(e){
				// 버튼의 제출기능 막기
				e.preventDefault();
				
				// let formObj = $("form";)으로 폼태그를 가져옵니다.
				let formObj = $("form");
				
				// 첨부파일과 관련된 정보를 hidden태그들로 만들어 문자로 먼저 저장합니다.
				let str = "";
				
				$("#uploadResult ul li").each(function(i, obj) {
					
					let jobj = $(obj);
					
					str += `<input type='hidden' name='attachList[\${i}].fileName' value='\${jobj.data("filename")}'>
						<input type='hidden' name='attachList[\${i}].uuid' value='\${jobj.data("uuid")}'>
						<input type='hidden' name='attachList[\${i}].uploadPath' value='\${jobj.data("path")}'>
						<input type='hidden' name='attachList[\${i}].fileType' value='\${jobj.data("type")}'>`
						console.log(str);
				});
				
				// formObj에 appen를 이용해 str을 끼워넣습니다
				formObj.append(str);
				
				// formObj.submit()을 이용해 제출기능이 실행되도록합니다
				formObj.submit();
				
			});
	</script>
</body>
</html>