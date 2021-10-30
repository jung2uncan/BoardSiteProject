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
			
			Class.forName("com.mysql.jdbc.Driver");	 //MySQL�� ������ �� �ֵ��� �ϴ� �Ű�ü(���̺귯��)
			conn = DriverManager.getConnection(dbURL, dbID, dbPW); //�Ű������� ���� DB�� ������ �� �ֵ��� ��. ������ �Ϸ�Ǹ� conn��ü�� ���������� ���� ��.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPW) {
		String SQL = "SELECT userPW FROM USER WHERE userID = ?";
		
		try {
			pstat = conn.prepareStatement(SQL);
			pstat.setString(1, userID);
			rs = pstat.executeQuery();	//���� ���� ����� ��� ����
			
			if(rs.next()) {	//���� ���� ��� �����Ͱ� �����ϸ� �ش� ������ ����
				if(rs.getString(1).equals(userPW)) {
					return 1;   //�α��� ����
				}
				else
					return 0;	//�α��� ����(��й�ȣ ����ġ)
			} 
			
			return -1;	//ID�� ����
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -2; //-2�� �����ͺ��̽� ������ �ǹ���.
	}
}
