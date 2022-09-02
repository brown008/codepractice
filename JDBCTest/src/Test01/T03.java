package Test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class T03 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//首先，通过资源绑定器，绑定属性配置文件，从而完成jvm和数据库的连接
		//创建资源绑定器对象
		ResourceBundle bundle = ResourceBundle.getBundle("src/Test01/jdbc");
		String driver = bundle.getString("driver");
		String url = bundle.getString("url");
		String username = bundle.getString("username");
		String password = bundle.getString("password");
		
		Connection conn = null;
		Statement stmt = null;
		try {
			//注册驱动
			Class.forName(driver);
			//连接数据库
			conn = DriverManager.getConnection(url,username, password);
			//创建数据库对象
			stmt = conn.createStatement();
			//编写sql语句

			/*
			 * String sql3 =
			 * "create table emp_bonus(emp_no int not null, btype smallint not null)";
			 * String sql2 = "insert into emp_bonus values(10001, 1)"; int count0 =
			 * stmt.executeUpdate(sql3); int count2 = stmt.executeUpdate(sql2);
			 * 
			 * System.out.println(count0);
			 */
			
			String sql4 = "drop table if exists 'salaries'";
			String sql5 = "create table salaries (emp_no int(11) not null,"
					+ "salary float(11,1) default null,"
					+ "from_date date not null,"
					+ "to_date date not null,"
					+ "primary key(emp_no))";
			String sql6 = "insert into salaries values(10001,85097,'2001-06-22','2002-06-22'),"
					+ "(10002,88958, '2002-06-22', '9999-01-01')";
			
			int count3 = stmt.executeUpdate(sql6);
			System.out.println(count3);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
