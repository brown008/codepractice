package Test01;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class T01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		try {
			//注册驱动
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);
			//获取连接
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqllearning", "root", "mysql");
			//创建数据库操作对象
			stmt = conn.createStatement();
			//执行sql
			String sql = "insert into employee values(7, 'buzhang', 7888, 9)";
			int count = stmt.executeUpdate(sql);
			System.out.println(count == 1 ? "success" : "fail");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		

	}

}
