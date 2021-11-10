package boardSite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BoardSiteDAO {
	private Connection conn;
	private ResultSet rs;
	
	//생성자는 인스턴스를 생성할 때 자동으로 실행되는 부분
	public BoardSiteDAO() {
		try {
			String dbURL ="jdbc:mysql://localhost:3306/BOARDSITE";
			String dbID = "root";
			String dbPW = "root계정패스워드";
			
			Class.forName("com.mysql.cj.jdbc.Driver");	 //MySQL에 접속할 수 있도록 하는 매개체(라이브러리)
			conn = DriverManager.getConnection(dbURL, dbID, dbPW); //매개변수를 통해 DB에 접속할 수 있도록 함. 접속이 완료되면 conn객체에 접속정보가 담기게 됨.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//현재 시간을 갖고 오는 함수 (게시글 작성일자를 저장할 때 필요함)
	public String getDate() {
		String SQL = "SELECT NOW()";
		
		try {
			//여러개의 함수가 사용되기 때문에 각 DB 접근에 있어서 마찰이 일어나지 않도록 각 함수 내부에서 선언하도록 함.
			PreparedStatement pstat = conn.prepareStatement(SQL); //SQL 실행 준비상태
			rs = pstat.executeQuery();
			
			if(rs.next())
				return rs.getString(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return "";	//DB오류가 나면 빈 문자열을 반환
	}
	
	//가장 최근 게시물의 ID값을 가져오는 함수
	public int getNext() {
		String SQL = "SELECT boardID from BOARD ORDER BY boardID DESC";
		
		try {
			PreparedStatement pstat = conn.prepareStatement(SQL); //SQL 실행 준비상태
			rs = pstat.executeQuery();
			
			if(rs.next())
				return rs.getInt(1) + 1;
			
			return 1; // 첫 번째 게시물인 경우
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return -1;	//DB 오류
	}
	
	public int write(String boardTitle, String userID, String boardContent) {
		String SQL = "INSERT INTO BOARD VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pstat = conn.prepareStatement(SQL); //SQL 실행 준비상태
			pstat.setInt(1, getNext());
			pstat.setString(2, boardTitle);
			pstat.setString(3, userID);
			pstat.setString(4, getDate());
			pstat.setString(5, boardContent);
			pstat.setInt(6, 1);

			return pstat.executeUpdate();	//성공했을 경우 0이상의 숫자를 반환
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1; //DB오류
	}
	
	//선택된 페이지에 해당되는 10개의 게시글을 읽어오도록 함
	public ArrayList<BoardSite> getList(int pageNumber) {
		//조회된 기준 boardID로 내림차순하여 위에 10개만 보여주는 쿼리
		String SQL = "SELECT * FROM BOARD WHERE boardID < ? AND boardAvailable = 1 ORDER BY boardID DESC LIMIT 10";
		ArrayList<BoardSite> boardList = new ArrayList<BoardSite>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - ((pageNumber-1) * 10));
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardSite board = new BoardSite();
				board.setBoardID(rs.getInt(1));
				board.setBoardTitle(rs.getString(2));
				board.setUserID(rs.getString(3));
				board.setBoardDate(rs.getString(4));
				board.setBoardContent(rs.getString(5));
				board.setBoardAvailable(rs.getInt(6));
				boardList.add(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardList;
	}
	
	//게시글 수에 따라 페이징 처리하기 위함.
	public boolean nextPage(int pageNumber) {
		//조회된 기준 boardID로 내림차순하여 위에 10개만 보여주는 쿼리
		String SQL = "SELECT * FROM BOARD WHERE boardID < ? AND boardAvailable = 1 ORDER BY boardID DESC LIMIT 10";
	
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - ((pageNumber-1) * 10));
			rs = pstmt.executeQuery();
			
			//다음 버튼을 보여줄지 판단하는 부분
			while(rs.next()) {	
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public BoardSite getBoard(int boardID) {
		String SQL = "SELECT * FROM BOARD WHERE boardID = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, boardID);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				BoardSite board = new BoardSite();
				board.setBoardID(rs.getInt(1));
				board.setBoardTitle(rs.getString(2));
				board.setUserID(rs.getString(3));
				board.setBoardDate(rs.getString(4));
				board.setBoardContent(rs.getString(5));
				board.setBoardAvailable(rs.getInt(6));
				return board;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int modify(int boardID, String boardTitle, String boardContent) {
		String SQL = "UPDATE BOARD SET boardTitle = ? , boardContent = ? WHERE boardID = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setInt(3, boardID);
			
			return pstmt.executeUpdate(); //성공했을 경우 0이상의 숫자를 반환
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	 public int delete(int boardID) {
			String SQL = "UPDATE BOARD SET boardAvailable = 0 WHERE boardID = ?";
			
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, boardID);
				
				return pstmt.executeUpdate(); //성공했을 경우 0이상의 숫자를 반환
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return -1; //데이터 베이스 오류
		}

}