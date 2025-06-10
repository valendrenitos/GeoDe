package back;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GenerateInvoiceNumber {

	String BDD = "geode_bd";
	String url = "jdbc:mysql://192.168.1.85:3306/" + BDD;
	String user = "username";
	String passwd = "password";

	public String InvoiceNumber(int status) {
		String Query = "";
		String num = "";
		if (status == 0)
			Query = "SELECT * FROM  geode_bd.invoice WHERE invoiceStatus = '0' ORDER BY invoicenumber DESC";
		else
			Query = "SELECT * FROM  geode_bd.invoice WHERE invoiceStatus != '0' ORDER BY invoicenumber DESC";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(Query);
			if (!rs.next()) {
				num="00";
			}
			else {
			
				num = rs.getString("invoicenumber");
			}
	
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		switch (status) {
		case 0:
			num = String.valueOf(Integer.valueOf(num.substring(0, num.length() - 1)) + 1) + "T";
			break;
		case 1:
			num = String.valueOf(Integer.valueOf(num.substring(0, num.length() - 2)) + 1) + "MT";
			break;
		case 2: 
			num = String.valueOf(Integer.valueOf(num.substring(0, num.length() - 2)) + 1) + "AV";
		}

		return num;

	}

	public String InvoiceAvoirNumber(String num, int id) {

		num = num.substring(0, num.length() - 2);
		num = num + "AV";

		return num;

	}
}
