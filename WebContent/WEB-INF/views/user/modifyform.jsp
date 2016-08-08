<%@page import="kr.ac.sungkyul.mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String result = request.getParameter( "res" );
	UserVo userVo = (UserVo)request.getAttribute( "userVo" );
%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/user.css" rel="stylesheet" type="text/css">
<script>
	<%
		if( "success".equals( result ) ) {
	%>
		alert( "성공적으로 수정하였습니다." );
	<%
		}
	%>
</script>
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/include/header.jsp"/>
		<div id="content">
			<div id="user">
				<form id="join-form" name="modifyForm" method="post" action="/mysite/user">
					<input type="hidden" name="a" value="modify"/>
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="<%=userVo.getName() %>">
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<%
							if( "FEMALE".equals(userVo.getGender()) ){
						%>
						<label>여</label> <input type="radio" name="gender" value="FEMALE" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="MALE">
						<%
							} else {
						%>
						<label>여</label> <input type="radio" name="gender" value="FEMALE">
						<label>남</label> <input type="radio" name="gender" value="MALE"  checked="checked">
						<%
							}
						%>
					</fieldset>
					
					<input type="submit" value="수정하기">
					
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/include/navi.jsp"/>
		<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
	</div>
</body>
</html>