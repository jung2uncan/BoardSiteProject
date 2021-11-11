<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content= "text/html; charset=UTF-8">
<!-- 어느 기기에서도 맞춤으로 보이는 반응형 웹에 사용되는 기본 Meta Tag -->
<meta name="viewport" content="width-device-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>JSP 게시판 웹 사이트</title>
</head>
<body>
	<%
		String userID = null;
	
		//로그인한 사용자들이라면, userID에 값이 담기게될 것!
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
		}
	%>
	<!-- 전반적인 웹사이트 구성을 나타내는 네비게이션 -->
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" 
			data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp"> JSP 게시판 웹 사이트</a>
		</div>
		
		
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<!-- ul은 리스트를 보여줄 때 쓰는 tag -> 안에 원소는 li로 사용 -->
			<ul class="nav navbar-nav">
				<li class="active"><a href="main.jsp">메인</a>
				<li><a href="boardSite.jsp">게시판</a>
			</ul>
		<%
			//아무도 로그인 되어있지 않을 때만 로그인/회원가입 메뉴를 보이게 하기 위함.
			if(userID == null) { 
		%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"
					role="button" aria-haspopup="true" aria-expanded="false"> 접속하기 <span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul>
				</li>
			</ul>
		<%		
			} else { //로그인이 되어있는 사용가자 보는 메뉴
		%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"
					role="button" aria-haspopup="true" aria-expanded="false"> 회원관리 <span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="logoutAction.jsp">로그아웃</a></li>
					</ul>
				</li>
			</ul>
		<%
			}
		%>
		</div>
	</nav>
	
	<div class="container">
		<!-- 일반적으로 웹사이트를 소개하는 영역을 jumbotron이라 칭함. 부트스트랩에서 제공한다. -->
		<div class ="jumbotron">
			<div class="container">
				<h1>웹 사이트 소개</h1>
				<p>이 웹 사이트는 부트스트랩으로 만든 JSP 웹 사이트입니다. 최소한의 간단한 로직을 이용해서 개발했습니다. 디자인 템플릿으로는 부트스트랩을 사용했습니다.</p>
				<p><a class="btn btn-primary btn-pull" href="https://ee2ee2.tistory.com/" role="button">자세히 알아보기</a></p>
			</div>
		</div>
	</div>
	
	<div class="container">
		<div id="MyCarousel" class="carousel slide" data-ride="carousel">
			<ol class="carousel-indicators">
				<li data-target="#MyCarousel" data-slide-to="0"></li>
				<li data-target="#MyCarousel" data-slide-to="1"></li>
				<li data-target="#MyCarousel" data-slide-to="2"></li>
			</ol>
			<div class="carousel-inner">
				<div class="item active">
					<img src="images/1.jpg">
				</div>
				<div class="item">
					<img src="images/2.jpg">
				</div>
				<div class="item">
					<img src="images/3.jpg">
				</div>
			</div>
			<a class="left carousel-control" href="#MyCarousel" data-slide="prev">
				<span class="glyphicon glyphicon-chevron-left"></span>
			</a>
			<a class="right carousel-control" href="#MyCarousel" data-slide="next">
				<span class="glyphicon glyphicon-chevron-right"></span>
			</a>
		</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>