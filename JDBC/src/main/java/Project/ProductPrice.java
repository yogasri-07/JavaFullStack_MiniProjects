package Project;

import java.sql.*;
import java.util.*;

public class ProductPrice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url="jdbc:mysql://localhost:3306/Product";
		String uname="root";
		String pword="root@localhost";
		
		Scanner sc= new Scanner(System.in);
		
		/*System.out.println("Enter product name: ");
		String name=sc.nextLine();*/
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url,uname,pword);
			
			System.out.println("\n---PRODUCT TALBE---\n");
			
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM Price");
			
			System.out.println("ID\t\tName\t\t\tPrice");
			System.out.println("---------------------------------------------");
			while(rs.next()) {
				System.out.println(
						rs.getInt("id")+"\t"+
				rs.getString("name")+"\t\t" +
				rs.getDouble("price")
				);
			}
			
			System.out.println("\nEnter id: ");
			int id=sc.nextInt();
			sc.nextLine();
			 
			
			System.out.println("Enter new price: ");
			double price=sc.nextDouble();
			
			
			String sql="UPDATE Price SET price=?  where id=?";
			
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setDouble(1, price);
			ps.setInt(2, id);
			
			int rows=ps.executeUpdate();
			if(rows>0) {
				System.out.println("Price updated successfully!");
			}
			else {
				System.out.println("Product ID not found!");
			}
			
			System.out.println("\n---Product Table After updation---\n");
			st=con.createStatement();
			String selectsql="SELECT * FROM Price";
		    rs= st.executeQuery(selectsql);
			
			System.out.println("ID\t Name\t\t\t Price");
			System.out.println("------------------------------------------");
			
			while(rs.next()) {
				System.out.println(
						       rs.getInt("id")+"\t"+
				               rs.getString("name")+"\t\t"+
							   rs.getDouble("price")
				);
			}
			con.close();
			sc.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

}
