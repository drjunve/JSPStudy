<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
$(function(){
	#('#btnLogin2').click(function(){
		var s_url = "../realproject/PostLogin.do";
		var s_params = $('#loginFrm').serialize();
		$.post(
			s_url,
			s_params,
			
			function(resData){
				var d = JSON.parse(resData);
				if(d.result==1){
					console.log(d.message);
					$('#loginFrm').html(d.html);
					$('#btnLogin2').hide();
					alert(d.message);
				}
				else{
					alert(d.message);
				}
			}
		);
	});
	
	$('#btnLogin3').click(function(){
	$.ajax({
		url : "./realproject/PostLogin.do",
		type : "post",
		data : $('loginFrm').serialize(),
		dataType : "json",
		success : function(d){
			console.log(d);
			if(d.result==1){
				console.leg(d.message);
				$('#loginFrm').html(d.html);
				$('#btnLogin2').hide();
				alert(d.message);
			}
			else{
				alert(d.message);
			}
		},
		error : function(e){
			alert("실패:"+e.status);
		}
	})
});

function checkFrm(){
	var f = document.getElementById("loginFrm");
	f.method = "post";
	f.action = "../realproject/PostLogin.do";
}
</script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="container">
		<h2>$.post() : 비동기 로그인 구현하기</h2>
		<div class="row" id="loginTable">
			<form id="loginFrm" method="post"
				action="../realproject/PostLogin.do">
				<!-- onsubmit="return checkFrm();"> -->
				<table class="table table-bordered">
					<tr>
						<td>아이디</td>
						<td><input type="text" name="user_id" value="" /></td>
					</tr>
					<tr>
						<td>패스워드</td>
						<td><input type="password" name="user_pw" value="" /></td>
					</tr>
				</table>
				<div>
					<button type="submit" class="btn btn-danger" id="btnLogin1">
						로그인하기(HTML의submit사용)</button>
					<button type="button" class="btn btn-success" id="btnLogin2">
						로그인하기(jQuery의 $.post()사용)</button>
					<button type="button" class="btn btn-primary" id="btnLogin3">
						로그인하기(jQuery의 $.post()사용)</button>
				</div>
			</form>
		</div>
	</div>
</body>

</html>