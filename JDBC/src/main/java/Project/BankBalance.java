package Project;

import java.sql.*;
import java.util.*;

public class BankBalance {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url="jdbc:mysql://localhost:3306/Bank";
		String uname="root";
		String pword="root@localhost";
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter Account number: ");
		int num=sc.nextInt();
		
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection(url,uname,pword);
			
			String sql="SELECT name,balance FROM Account WHERE acc_no=?";
			 ps = con.prepareStatement(sql);
			
			ps.setInt(1, num);
			
			rs=ps.executeQuery();
			
			if(rs.next()) {
				String name=rs.getString("name");
				double balance=rs.getDouble("balance");
				
				System.out.println("Account Holder: "+name);
				System.out.println("Account Balance: "+balance);
				
				
			}
			else {
				System.out.println("Account number not found");
				
			}
		}
			catch(ClassNotFoundException e) {
				System.out.println("MySQL JDBC Driver not found.");
				e.printStackTrace();
				
		}
		catch(SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
			finally {
				try {if (rs!=null) rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {if (ps !=null) ps.close();} catch (SQLException e) {e.printStackTrace();}
				try {if (con !=null) con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			sc.close();
			
			
		

	}

}
