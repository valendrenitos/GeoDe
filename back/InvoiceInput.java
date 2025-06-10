package back;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class InvoiceInput {

	String BDD = "geode_bd";
	String url = "jdbc:mysql://192.168.1.85:3306/" + BDD;
	String user = "username";
	String passwd = "password";
	CalendarController calcon = new CalendarController();
	InvoicePrev invp = new InvoicePrev();
	InvoiceTaker invt = new InvoiceTaker();

	public int AddInvoice(String clienttype, String name, String addresse, String garage, String city, String cp,
			String textfac, int facttype, String paytype, double totalht, double totalttc, int idvehi,
			String invoicenum, String country, String tva) {
		Date currentSqlDate = new Date(System.currentTimeMillis());
		int invoiceid = 0;
		addresse = FormattingString(addresse);
		garage = FormattingString(garage);
		city = FormattingString(city);
		textfac = FormattingString(textfac);
		tva = FormattingString(tva);

		String query = "INSERT INTO `geode_bd`.`invoice` (`invoiceClientType`, `invoiceClient`, `invoicenumber`, `invoiceAdress`, `invoicecp`, `invoiceCity`, `invoicegar`,`invoiceStatus`, `invoicevehicule`, `invoiceText`, `invoicePayType`,`invoicedate`, `invoiceTotalTTC`, `invoiceTotalHT`, `invoicecountry`, `invoicetva` )"
				+ " VALUES ( '" + clienttype + "', '" + name + "'" + ", '" + invoicenum + "','" + addresse + "', '" + cp
				+ "','" + city + "', '" + garage + "', '" + facttype + "', '" + idvehi + "','" + textfac + "','"
				+ paytype + "', '" + currentSqlDate + "', '" + totalttc + "', '" + totalht + "', '" + country + "', '"
				+ tva + "')";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			stmt.execute(query);
			query = " SELECT * FROM geode_bd.invoice WHERE invoicenumber ='" + invoicenum + "'";
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			invoiceid = rs.getInt("idinvoice");
			conn.close();
			if (facttype != 0 && idvehi != 0) {
				calcon.addEvent("Vente du véhicule " + idvehi, currentSqlDate, idvehi, null);
			}

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		return invoiceid;
	}

	public int UpdateInvoice(int idinvoice, String clienttype, String name, String addresse, String garage, String city,
			String cp, String textfac, int facttype, String paytype, double totalht, double totalttc, int idvehi,
			String numinv, String country, String tva) {
		addresse = FormattingString(addresse);
		garage = FormattingString(garage);
		city = FormattingString(city);
		textfac = FormattingString(textfac);
		tva = FormattingString(tva);

		Date currentSqlDate = new Date(System.currentTimeMillis());
		String query = "UPDATE `geode_bd`.`invoice` SET `invoiceClientType` = '" + clienttype
				+ "' , `invoiceClient` = '" + name + "' , `invoiceAdress`='" + addresse + "', `invoicecp`='" + cp
				+ "', `invoiceCity`='" + city + "', `invoicegar`='" + garage + "', `invoicevehicule` ='" + idvehi
				+ "', `invoiceText`='" + textfac + "', `invoicePayType`='" + paytype + "', `invoiceTotalTTC` ='"
				+ totalttc + "', `invoiceTotalHT`='" + totalht + "', `invoicenumber`='" + numinv
				+ "', `invoiceStatus`='" + facttype + "', `invoicecountry`='" + country + "', `invoicetva`='" + tva
				+ "' WHERE (`idinvoice` = '" + idinvoice + "')";

		Invoice inv = invt.getSpecifiedInvoice(idinvoice);
		if (facttype != 0 && idvehi != 0) {
			calcon.addEvent("Vente du véhicule " + idvehi, inv.getInvoicedatecreation(), idvehi, null);
			query = "UPDATE `geode_bd`.`invoice` SET `invoiceClientType` = '" + clienttype + "' , `invoiceClient` = '"
					+ name + "' , `invoiceAdress`='" + addresse + "', `invoicecp`='" + cp + "', `invoiceCity`='" + city
					+ "', `invoicegar`='" + garage + "', `invoicevehicule` ='" + idvehi + "', `invoiceText`='" + textfac
					+ "', `invoicePayType`='" + paytype + "', `invoiceTotalTTC` ='" + totalttc + "', `invoiceTotalHT`='"
					+ totalht + "', `invoicenumber`='" + numinv + "', `invoiceStatus`='" + facttype
					+ "', `invoicecountry`='" + country + "', `invoicetva`='" + tva + "', `invoicedate`= '"
					+ currentSqlDate + "' WHERE (`idinvoice` = '" + idinvoice + "')";
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			stmt.execute(query);

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		return idinvoice;
	}

	public void AddInvoiceLine(int invoiceID, String label, int qte, float puht, float total) {
		label = FormattingString(label);
		String query = "INSERT INTO `geode_bd`.`invoicelines` (`invoiceId`, `invoicelinesLib`, `invoicelinesTotal`, `invoicelinesqte`,`invoicelinespuht`)"
				+ " VALUES ( '" + invoiceID + "', '" + label + "'" + ", '" + total + "', '" + qte + "', '" + puht
				+ "')";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			stmt.execute(query);

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

	}

	public void DeleteInvocieLines(int id) {
		String query = "DELETE FROM `geode_bd`.`invoicelines` WHERE (`invoiceId` = '" + id + "')";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			stmt.execute(query);

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
	}

	public void AddPayOrAddLines(String value, String methode, Date paydate, boolean payoradv, int id) {
		int pay = 0;
		if (payoradv)
			pay = 1;

		String query = "INSERT INTO `geode_bd`.`paynadv`  (`paytype`, `payamount`,`invoiceidtopay`,`payadvorpay`,`paydate`) VALUES ('"
				+ methode + "', '" + Float.parseFloat(value) + "', '" + id + "', '" + pay + "','" + paydate + "')";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();

			stmt.execute(query);

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

	}

	public void ClearPayOrAdd(int id) {
		String query2 = "DELETE FROM `geode_bd`.`paynadv` WHERE (`invoiceidtopay` = '" + id + "')";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			stmt.execute(query2);

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
	}

	public void linkAVtoInvoice(int id, int idinvoice) {

		String query = "UPDATE `geode_bd`.`invoice` SET `invoiceavid` = '" + idinvoice + "' WHERE idinvoice ='" + id
				+ "'";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			stmt.execute(query);

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

	}

	public void paidInvoice(int id) {

		String query = "UPDATE `geode_bd`.`invoice` SET `invoicestatus` = '3' WHERE idinvoice ='" + id + "'";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			stmt.execute(query);

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

	}

	public void ListInvoice(int id, String listint) {

		String query = "UPDATE `geode_bd`.`invoice` SET `invoicelistvehi` = '" + listint + "' WHERE idinvoice ='" + id
				+ "'";
		Invoice inv = invt.getSpecifiedInvoice(id);
		ObservableList<Integer> listids = invp.getIds(listint);
		if (inv.getInvoiceStatus() == "Définitive")
			for (int i = 0; i < listids.size(); i++)
				calcon.addEvent("Vente du véhicule " + listids.get(i), inv.getInvoicedatecreation(), listids.get(i),
						null);

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			stmt.execute(query);

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

	}

	public String FormattingString(String text) {
		String formatted = "";
		if (text == null) 
			text = " ";
		for (int i = 0; i < text.length(); i++) {
			// If character is not a letter, digit, or space,
			// it's a special character
			if (!Character.isLetterOrDigit(text.charAt(i)) && !Character.isWhitespace(text.charAt(i))) {
				formatted = formatted + "\\" + text.charAt(i);

			} else {
				formatted = formatted + text.charAt(i);
			}
		}

		return formatted;
	}

}
