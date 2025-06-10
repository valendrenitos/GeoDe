package back;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class FileInputer {
	String BDD = "geode_bd";
	String url = "jdbc:mysql://192.168.1.85:3306/" + BDD;
	String user = "username";
	String passwd = "password";
	  Connection conn;
	  
	  public void AddCG(int vehiid, File cg) {
			int val = 0;

		  cg.renameTo(new File("M:\\cg\\" + vehiid+ ".pdf"));
		  String filelink = cg.getAbsolutePath().replace("\\","\\\\" );
		  String query = "INSERT INTO `geode_bd`.`documents` (`idvehiculedoc`, `docType`, `docfulllink`) VALUES ('"+vehiid+"', '"+val+"', '"+filelink+"')";
			try {
				conn = DriverManager.getConnection(url, user, passwd);
				Statement stmt = conn.createStatement();
				stmt.execute(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

	  }
	  
	  public void Addotherdoc(int vehiid, List<File> cg) {
		int val = 1;
			
		  String filelink;
		  
		  String query;
			try {
				conn = DriverManager.getConnection(url, user, passwd);
				Statement stmt = conn.createStatement();
				for(int i=0; i<cg.size();i++) {
					
			
				  cg.get(i).renameTo(new File("M:\\cg\\" + vehiid+ "doc"+i+".pdf"));
				filelink = cg.get(i).getAbsolutePath().replace("\\","\\\\" );
				query = "INSERT INTO `geode_bd`.`documents` (`idvehiculedoc`, `docType`, `docfulllink`) VALUES ('"+vehiid+"', '"+val+"', '"+filelink+"')";
				stmt.execute(query);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

	  }
	  public void Addpicdoc(int vehiid, List<File> cg) {
			int val = 2;
				
			  String filelink;
			  
			  String query;
				try {
					conn = DriverManager.getConnection(url, user, passwd);
					Statement stmt = conn.createStatement();
					for(int i=0; i<cg.size();i++) {
						  cg.get(i).renameTo(new File("M:\\cg\\" + vehiid+ "pic"+i+".pdf"));
							filelink = cg.get(i).getAbsolutePath().replace("\\","\\\\" );;	
					query = "INSERT INTO `geode_bd`.`documents` (`idvehiculedoc`, `docType`, `docfulllink`) VALUES ('"+vehiid+"', '"+val+"', '"+filelink+"')";
					stmt.execute(query);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

		  }
}
