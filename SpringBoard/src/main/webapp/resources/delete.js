$("#replyDelBtn").on("click", function() {
	var rno = $(".modal-title").html();
		
	
	$.ajax({
		type : 'delete',
		url : '/replies/' + rno,
		header : {
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override" : "DELETE"
		},
		dataType : 'text',
		success : function(result) {
			console.log("result : " + result);
			if(result == 'SUCCESS') {
				alert("삭제 되었습니다");
				$("#modDiv").hide("slow");
				getAllList();
			}
		}
		
	});
	
});