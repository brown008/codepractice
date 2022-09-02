package Test01;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class T02 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//通过资源绑定器的方法导入配置文件，实现JVM和数据库进程的连接
		//注册驱动
		
		/*
		 * String path = Thread.currentThread().getContextClassLoader()
		 * .getResource("jdbc.properties.txt").getPath(); System.out.println(path);
		 */
		//使用资源绑定器 绑定属性配置文件
		ResourceBundle bundle = ResourceBundle.getBundle("src/Test01/jdbc");
		String driver = bundle.getString("driver");
		//System.out.println(driver);
		String url = bundle.getString("url");
		//System.out.println(url);
		String username = bundle.getString("username");
		//System.out.println(username);
		String password = bundle.getString("password");
		//System.out.println(password);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			//注册驱动
			Class.forName(driver);
			//System.out.println("ok");
			//建立连接
			conn = DriverManager.getConnection(url, username, password);
			//System.out.println("ok2");
			//创建数据库操作对象
			stmt = conn.createStatement();
			//执行sql语句
			String sql = "update employee set name = 'mary', salary = 0 where id = 3";
			int count = stmt.executeUpdate(sql);
			System.out.println(count == 1 ? "success" : "fail");
			
			//执行select语句
			String sql2 = "select * from employee";
			rs = stmt.executeQuery(sql2);
			//处理查询结果集
			boolean flag = rs.next();
			if(flag) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				String salary = rs.getString(3);
				String mid = rs.getString(4);
				System.out.println(id + "," + name +"," + salary+"," + mid);
			}
			System.out.println("----------------");
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString(2);
				String salary = rs.getString(3);
				int mid = rs.getInt("managerid");
				System.out.println(id + "," + name +"," + salary+"," + mid);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//关闭资源
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
