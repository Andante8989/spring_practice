<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
	.uploadResult {
		width:100%;
		background-color: gray;
	}
	.uploadResult ul {
		display:flex;
		flex-flow:row;
		justify-content:center;
		align-items: center;
	}
	.uploadResult ul li {
		list-style: none;
		padding : 10px;
	}
	.uploadResult ul li img {
		width: 20px;
	}
</style>
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


<h1>Upload with Ajax</h1>
	
	<div class="uploadDiv">
		<input type="file" name="uploadFile" multiple>
	</div>
	
	<div class="uploadResult">
		<ul>
			<!--  업로드된 파일들이 여기 나열됨 -->
		</ul>
	</div>
	
	<button id="uploadBtn">Upload</button>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	
	<script>
	
	var csrfHeaderName = "${_csrf.headerName}"
	var csrfTokenValue="${_csrf.token}"
		$(document).ready(function() {
			// 정규표현식 : 예).com 끝나는 문장 등의 조건이 복잡한 문장을 컴퓨터에게 이해시키기 위한 구문
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
			
			let uploadResult = $(".uploadResult ul");
			
			function showUploadedFile(uploadResultArr) {
				let str = "";
				
				$(uploadResultArr).each(function(i, obj) {
					
					if(!obj.image) {
						
						let fileCallPath = encodeURIComponent(
								obj.uploadPath + "/"
							+   obj.uuid + "_" + obj.fileName);
						str += `<li data-path='\${obj.uploadPath}' data-uuid='\${obj.uuid}' data-filename='\${obj.fileName}' data-type='\${obj.image}'>
								<a href='/board/download?fileName=\${fileCallPath}'>
								<img src='/resources/attach.png'>\${obj.fileName}</a>
								<span data-file='\${fileCallPath}' data-type='file'>X<span>
								</li>`; 
					} else {
						//str += `<li>\${obj.fileName}</li>`;
						let fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" +
															obj.uuid + "_" + obj.fileName);
						
						let fileCallPath2 = encodeURIComponent(obj.uploadPath + obj.uuid + "_" + obj.fileName);
						
						console.log(fileCallPath);
						console.log(fileCallPath2);
						str += `<li data-path='\${obj.uploadPath}' data-uuid='\${obj.uuid}' data-filename='\${obj.fileName}' data-type='\${obj.image}'><a href='/board/download?fileName=\${fileCallPath2}'>
						<img src='/board/display?fileName=\${fileCallPath}'>\${obj.fileName}</a>
						<span data-file='\${fileCallPath}' data-type='image'>X<span>
						</li>`;
					}
					
				});
				uploadResult.append(str);
			} // showUploadedFile

			
			
		}); // documnet ready
		
		$(".uploadResult").on("click", "span", function(e) {
			// 파일이름을 span태그 내부의 data-file에서 얻어와서 저장
			let targetFile = $(this).data("file");
			// 이미지 여부를 span태그 내부의 data-type에서 얻어와서 저장
			let type= $(this).data("type");
			
			// 클릭한 span태그와 엮여잇는 li를 tagetLi에 저장
			let targetLi = $(this).closest("li");
			
			
			$.ajax({
				 beforeSend : function(xhr) {
					 xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
					 },
				url : '/board/deleteFile',
				data: {fileName : targetFile, type : type},
				dataType : 'text',
				type : 'POST',
				success: function(result) {
					alert(result);
					// 클릭한 li요소를 화면에서 삭제함(파일삭제 후 화면에서도 삭제)
					targetLi.remove();
				}
			}); // ajax 끝
		}); // click span


	
	
	</script>








</body>
</html>