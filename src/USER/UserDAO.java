package USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL ="jdbc:mysql://localhost:3306/BOARDSITE";
			String dbID = "root";
			String dbPW = "mysqlpje31605!";
			
			Class.forName("com.mysql.jdbc.Driver");	 //MySQL에 접속할 수 있도록 하는 매개체(라이브러리)
			conn = DriverManager.getConnection(dbURL, dbID, dbPW); //매개변수를 통해 DB에 접속할 수 있도록 함. 접속이 완료되면 conn객체에 접속정보가 담기게 됨.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPW) {
		String SQL = "SELECT userPW FROM USER WHERE userID = ?";
		
		try {
			pstat = conn.prepareStatement(SQL);
			pstat.setString(1, userID);
			rs = pstat.executeQuery();	//쿼리 실행 결과는 담는 변수
			
			if(rs.next()) {	//쿼리 실행 결과 데이터가 존재하면 해당 영역이 실행
				if(rs.getString(1).equals(userPW)) {
					return 1;   //로그인 성공
				}
				else
					return 0;	//로그인 실패(비밀번호 불일치)
			} 
			
			return -1;	//ID가 없음
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -2; //-2는 데이터베이스 오류를 의미함.
	}
}
