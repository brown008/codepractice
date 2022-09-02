package Test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LoginTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//初始化一个界面
		Map<String, String> userLoginInfo = initUI();
		
		//验证用户名和密码
		boolean loginSuccess = login(userLoginInfo);
		System.out.println(loginSuccess ? "Success":"fail");

	}
	
	//定义一个用来输入用户名和密码的方法
	private static Map<String, String> initUI(){
		Scanner s = new Scanner(System.in);
		System.out.print("用户名：");
		String loginName = s.nextLine();
		System.out.print("密码：");
		String loginPassword = s.nextLine();
		
		Map<String, String> userLoginInfo = new HashMap<>();
		userLoginInfo.put("loginName", loginName);
		userLoginInfo.put("loginPassword", loginPassword);
		
		return userLoginInfo;
	}
	
	//定义一个验证用户名和密码的方法，利用JDBC来对用户名和密码进行验证
	private static boolean login(Map<String, String> userLoginInfo) {
		ResourceBundle bundle = ResourceBundle.getBundle("src/Test01/jdbc");
		String driver = bundle.getString("driver");
		String url = bundle.getString("url");
		String username = bundle.getString("username");
		String password = bundle.getString("password");
		
		//打一个标记
		boolean loginSuccess = false;
		
		//单独定义变量
		//因为userLoginInfor是一个map集合，所以可以通过get方法，以key得到value
		String loginName = userLoginInfo.get("loginName");
		String loginPassword = userLoginInfo.get("loginPassword");
		
		Connection conn = null;
		//Statement stmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			//注册驱动
			Class.forName(driver);
			//获取连接
			conn = DriverManager.getConnection(url, username, password);
			//创建数据库操作对象
			//stmt = conn.createStatement();
			
			//sql语句，输入值用占位符？取代
			String sql = "select * from t_login where username = ? and password = ?";
			//rs = stmt.executeQuery(sql);
			
			//对sql语句进行预编译
			ps = conn.prepareStatement(sql);
			
			//然后，给sql语句中的占位符？ 进行传值
			ps.setString(1, loginName);
			ps.setString(2, loginPassword);
			
			//然后，执行sql，进行查询
			rs = ps.executeQuery();
			
			if(rs.next()) {
				loginSuccess = true;
				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}if(ps != null) {
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
		
		return loginSuccess;
	}

}
