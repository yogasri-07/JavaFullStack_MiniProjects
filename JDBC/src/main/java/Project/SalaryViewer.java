package Project;

import java.sql.*;
import java.util.*;

public class SalaryViewer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url="jdbc:mysql://localhost:3306/Employee";
		String uname="root";
		String pword="root@localhost";
		
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter employee ID: ");
		int id=sc.nextInt();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,uname,pword);
			
			String sql="SELECT * from Salary where id=?";
			
			PreparedStatement ps=con.prepareStatement(sql);
			
			ps.setInt(1,id);
			ResultSet rs=ps.executeQuery();
			
			System.out.println("ID\t\tName\t\tSalary");
			while(rs.next()) {
				System.out.println(
						rs.getInt("id")+"\t\t"+
						rs.getString("name")+"\t\t"+
						rs.getDouble("salary")
						);
			}
			con.close();
			sc.close();
		}
		
		catch(Exception e) {
			System.out.println("Exception occured!");
			e.printStackTrace();
			
			
		}
			
		}
		
	

}
