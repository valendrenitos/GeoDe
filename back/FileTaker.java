package back;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FileTaker {

	String BDD = "geode_bd";
	String url = "jdbc:mysql://192.168.1.85:3306/" + BDD;
	String user = "username";
	String passwd = "password";
	Connection conn;

	public String RetrieveCG(int idvehi) {
		String access = "";

		String query = " SELECT * FROM geode_bd.documents WHERE idvehiculedoc = '" + idvehi + "' AND docType = '0'";
		ResultSet rs;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next())
				access = rs.getString("docfulllink").replace("\\", "\\\\");

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		File file = new File(access);
		System.out.println(access);
		return access;
	}

	public List<String> RetrieveotherDoc(int idvehi) {
		List<String> listdoc = new ArrayList<String>();

		String query = " SELECT * FROM geode_bd.documents WHERE idvehiculedoc = '" + idvehi + "' AND docType = '1'";
		ResultSet rs;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next())
				listdoc.add(rs.getString("docfulllink").replace("\\", "\\\\"));

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return listdoc;
	}

	public List<String> Retrievepic(int idvehi) {
		List<String> listdoc = new ArrayList<String>();

		String query = " SELECT * FROM geode_bd.documents WHERE idvehiculedoc = '" + idvehi + "' AND docType = '2'";
		ResultSet rs;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next())
				listdoc.add(rs.getString("docfulllink").replace("\\", "\\\\"));

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return listdoc;
	}

	public void fileOpener(String access) {
		File file = new File(access);
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
