package Project;

import java.sql.*;
import java.util.*;

public class StudentForm {
	
	public static void main(String[] args) {	
		
		String url="jdbc:mysql://localhost:3306/Form";
		String uname="root";
		String pword="root@localhost";
		Scanner sc=new Scanner(System.in);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,uname,pword);
			
			/*System.out.println("Enter ID: ");
			int id=sc.nextInt();
			
			
			sc.nextLine();*/

			
			System.out.println("Enter Name: ");
			String name=sc.nextLine();
			
			System.out.println("Enter RollNo: ");
			int roll=sc.nextInt();
			
			sc.nextLine();
			
			System.out.println("Enter Department: ");
			String dept=sc.nextLine();
			
			String sql="INSERT INTO Students1 (name,roll,dept) VALUES (?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			
			//ps.setInt(1,id);
			ps.setString(1,name);
			ps.setInt(2,roll);
			ps.setString(3,dept);
			
			System.out.println("Inserting: " + name + ", " + roll + ", " + dept);

			
			int rows=ps.executeUpdate();
			
			if(rows>0) {
				System.out.println("Data inserted successfully!");
				
			}
			// Query to see all rows in the table
			String selectSql = "SELECT * FROM Students1";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(selectSql);

			System.out.println("\nCurrent Students1 Table:");
			while(rs.next()) {
			    int id = rs.getInt("id");          // or acc_no if your column is named that
			    String sName = rs.getString("name");
			    int sRoll = rs.getInt("roll");
			    String sDept = rs.getString("dept");
			    
			    System.out.println(id + " | " + sName + " | " + sRoll + " | " + sDept);
			}

			rs.close();
			stmt.close();

			
			ps.close();
			con.close();
			sc.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		
			
			
		
		
		
	}

}
}

