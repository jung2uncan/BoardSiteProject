package USER;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	//�����ڴ� �ν��Ͻ��� ������ �� �ڵ����� ����Ǵ� �κ�
	public UserDAO() throws IOException {
		String filePath = "D:/XXXXXX/eclipse-workspace/BoardSite/config/db.properties"; 
		Properties properties = new Properties();

		try {
			properties.load(new FileReader(filePath));
			Class.forName(properties.getProperty("driver"));	 //MySQL�� ������ �� �ֵ��� �ϴ� �Ű�ü(���̺귯��)
			conn = DriverManager.getConnection(properties.getProperty("dbURL"), properties.getProperty("dbID"), properties.getProperty("dbPW")); //�Ű������� ���� DB�� ������ �� �ֵ��� ��. ������ �Ϸ�Ǹ� conn��ü�� ���������� ���� ��.
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
	
	public int join(User user) {
		String SQL ="INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		
		try {
			pstat = conn.prepareStatement(SQL);
			pstat.setString(1, user.getUserID());
			pstat.setString(2, user.getUserPW());
			pstat.setString(3, user.getUserName());
			pstat.setString(4, user.getUserGender());
			pstat.setString(5, user.getUserEmail());
			
			//INSERT���� ���, ������ ��� 0 �̻��� ���ڰ� ��ȯ��.
			return pstat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;	//������ ���̽� ������ ��
	}
}