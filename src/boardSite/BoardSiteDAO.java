package boardSite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BoardSiteDAO {
	private Connection conn;
	private ResultSet rs;
	
	//�����ڴ� �ν��Ͻ��� ������ �� �ڵ����� ����Ǵ� �κ�
	public BoardSiteDAO() {
		try {
			String dbURL ="jdbc:mysql://localhost:3306/BOARDSITE";
			String dbID = "root";
			String dbPW = "root�����н�����";
			
			Class.forName("com.mysql.cj.jdbc.Driver");	 //MySQL�� ������ �� �ֵ��� �ϴ� �Ű�ü(���̺귯��)
			conn = DriverManager.getConnection(dbURL, dbID, dbPW); //�Ű������� ���� DB�� ������ �� �ֵ��� ��. ������ �Ϸ�Ǹ� conn��ü�� ���������� ���� ��.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//���� �ð��� ���� ���� �Լ� (�Խñ� �ۼ����ڸ� ������ �� �ʿ���)
	public String getDate() {
		String SQL = "SELECT NOW()";
		
		try {
			//�������� �Լ��� ���Ǳ� ������ �� DB ���ٿ� �־ ������ �Ͼ�� �ʵ��� �� �Լ� ���ο��� �����ϵ��� ��.
			PreparedStatement pstat = conn.prepareStatement(SQL); //SQL ���� �غ����
			rs = pstat.executeQuery();
			
			if(rs.next())
				return rs.getString(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return "";	//DB������ ���� �� ���ڿ��� ��ȯ
	}
	
	//���� �ֱ� �Խù��� ID���� �������� �Լ�
	public int getNext() {
		String SQL = "SELECT boardID from BOARD ORDER BY boardID DESC";
		
		try {
			PreparedStatement pstat = conn.prepareStatement(SQL); //SQL ���� �غ����
			rs = pstat.executeQuery();
			
			if(rs.next())
				return rs.getInt(1) + 1;
			
			return 1; // ù ��° �Խù��� ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return -1;	//DB ����
	}
	
	public int write(String boardTitle, String userID, String boardContent) {
		String SQL = "INSERT INTO BOARD VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pstat = conn.prepareStatement(SQL); //SQL ���� �غ����
			pstat.setInt(1, getNext());
			pstat.setString(2, boardTitle);
			pstat.setString(3, userID);
			pstat.setString(4, getDate());
			pstat.setString(5, boardContent);
			pstat.setInt(6, 1);

			return pstat.executeUpdate();	//�������� ��� 0�̻��� ���ڸ� ��ȯ
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1; //DB����
	}
	
	//���õ� �������� �ش�Ǵ� 10���� �Խñ��� �о������ ��
	public ArrayList<BoardSite> getList(int pageNumber) {
		//��ȸ�� ���� boardID�� ���������Ͽ� ���� 10���� �����ִ� ����
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
	
	//�Խñ� ���� ���� ����¡ ó���ϱ� ����.
	public boolean nextPage(int pageNumber) {
		//��ȸ�� ���� boardID�� ���������Ͽ� ���� 10���� �����ִ� ����
		String SQL = "SELECT * FROM BOARD WHERE boardID < ? AND boardAvailable = 1 ORDER BY boardID DESC LIMIT 10";
	
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - ((pageNumber-1) * 10));
			rs = pstmt.executeQuery();
			
			//���� ��ư�� �������� �Ǵ��ϴ� �κ�
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
			
			return pstmt.executeUpdate(); //�������� ��� 0�̻��� ���ڸ� ��ȯ
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
				
				return pstmt.executeUpdate(); //�������� ��� 0�̻��� ���ڸ� ��ȯ
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return -1; //������ ���̽� ����
		}

}