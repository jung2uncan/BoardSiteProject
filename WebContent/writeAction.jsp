<%@page import="boardSite.BoardSite"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="boardSite.BoardSiteDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%
request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="board" class="boardSite.BoardSite" scope="page"/>
<jsp:setProperty name="board" property="boardTitle"/>
<jsp:setProperty name="board" property="boardContent"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content= "text/html; charset=UTF-8">
<title>JSP 게시판 웹 사이트</title>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){	
			userID = (String) session.getAttribute("userID");
		}
		
		if(userID == null){	
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인 이후 이용가능한 서비스 입니다.')");
			script.println("location.href = 'login.jsp'");
			script.println("</script>");	
		}
		else {
			if(board.getBoardTitle() == null || board.getBoardContent() == null) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('모든 칸을 입력해주십시오.')");
				script.println("history.back()");
				script.println("</script>");	
			}
			else {
				//실제 Data 저장을 위해 저장 함수를 호출하는 부분
				BoardSiteDAO boardrDao = new BoardSiteDAO();
				int result = boardrDao.write(board.getBoardTitle(), userID, board.getBoardContent()); 
				
				if(result >= 0){
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("location.href = 'boardSite.jsp'");
					script.println("</script>");	
				} else if(result == -1){
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('글 등록에 실패하였습니다.')");
					script.println("history.back()");
					script.println("</script>");	
				}
			}
		}

	%>
</body>
</html>