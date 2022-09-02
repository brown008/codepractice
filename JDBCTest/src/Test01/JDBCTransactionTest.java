package Test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JDBCTransactionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//用资源绑定器绑定属性配置文件
		ResourceBundle bundle = ResourceBundle.getBundle("src/Test01/jdbc");
		String driver = bundle.getString("driver");
		String url = bundle.getString("url");
		String username = bundle.getString("username");
		String password = bundle.getString("password");
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			//注册驱动
			Class.forName(driver);
			//获取连接
			conn = DriverManager.getConnection(url, username, password);
			//将自动提交机制修改为手动
			conn.setAutoCommit(false);//标志着，手动开启事务
			
			//获取预编译的数据库操作对象
			String sql = "insert into t_login values (?, ?, ?)";
			ps = conn.prepareStatement(sql);
			//给占位符？传值
			//第一次传值
			ps.setInt(1, 3);
			ps.setString(2, "brown");
			ps.setInt(3, 333);
			int count = ps.executeUpdate();
			
			String s = null;
			s.toString();
					
			
			//第二次传值
			ps.setInt(1, 4);
			ps.setString(2, "pitt");
			ps.setInt(3, 555);
			count += ps.executeUpdate();
			System.out.println(count == 2 ? "success":"fail");
			
			//提交事务
			conn.commit();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			//释放资源
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}if(conn != null) {
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
