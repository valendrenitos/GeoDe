package back;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class dataInput {
	CalendarController cl = new CalendarController();
	String BDD = "geode_bd";
//	String url = "jdbc:mysql://192.168.1.85:3306/" + BDD;
	String url = "jdbc:mysql://192.168.1.85:3306/" + BDD;
	String user = "username";
	String passwd = "password";
	InvoiceInput invi = new InvoiceInput();
	// String query = "SELECT marque FROM geode_bd.vehicule";

	public void Startup() {

		String query = " SELECT * FROM geode_bd.mytable";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int id = 0;
			
//
			while (rs.next()) {
				id=rs.getInt("CODVEHIC");
				Statement stmt2 = conn.createStatement();
				Date currentSqlDate = converttodate(rs.getString("DATENTR"));

				String dest = "Occasion";
				if (rs.getString("DESTIN") == "C")
					dest = "Casse";
				else if (rs.getString("DESTIN") == "O")
					dest = "Occasion";
				else if (rs.getString("DESTIN") == "T")
					dest = "Commisariat";

				String marque = rs.getString("MARQUE");
				if (marque == null) {
					marque = "ND";
				}
				marque = invi.FormattingString(marque);

				String type = rs.getString("TYPE");
				if (type == null) {
					type = "ND";
				}

				type = invi.FormattingString(type);

				String immat = rs.getString("IMMAT");
				if (immat == null)
					immat = "ND";
				immat = invi.FormattingString(immat);

				String carros = rs.getString("CARROSS");
				if (carros == "SOLO") {
					carros = "Solo";
				} else
					carros = "4 portes";

				String numSer = rs.getString("NUMSERIE");
				if (numSer == null) {
					numSer = "ND";
				}
				numSer = invi.FormattingString(numSer);

				String energy = rs.getString("ENERGIE");
				if (energy != null)
					energy = invi.FormattingString(energy);

				int hp = rs.getInt("PUISSANCE");
				int klms = rs.getInt("NBKILOM");

				String couleur = rs.getString("COULEUR");
				if (couleur != null)
					couleur = invi.FormattingString(couleur);

				String tempdate = rs.getString("ANNEE1");
				Date mecdate = converttodate("1970-01-01");

				if (tempdate == null) {
					mecdate = converttodate("1970-01-01");

				} else if (tempdate.length() < 8) {
					mecdate = converttodate("1970-01-01");
				} else {
					mecdate = converttodate(tempdate);
				}

				String query2 = "INSERT INTO `geode_bd`.`vehicule` (`idvehicule`,`destination`, `marque`, `type`, `immatriculation`, `dateEntree`, `carrosserie`,`numeroSerie`, `energie`, `puissance`, `klms`, `couleur`, `miseEnCircul`,  `hascg`) "
						+ "VALUES ('" + id + "', '" + dest + "', '" + marque + "', '" + type + "', '" + immat + "','"
						+ currentSqlDate + "', '" + carros + "', '" + numSer + "', '" + energy + "','" + hp + "','"
						+ klms + "','" + couleur + "','" + mecdate + "','0')";

				String origin = rs.getString("ORIGINE");
				if (origin == "A")
					origin = "Assurance";
				else
					origin = "Particulier";

				String cause = rs.getString("CAUSE");
				if (cause != null)
					cause = invi.FormattingString(cause);

				String city = rs.getString("LIEU_ENLEV");
				if (city != null)
					city = invi.FormattingString(city);

				String dep = rs.getString("DEPANNEUR");
				if (dep != null)
					dep = invi.FormattingString(dep);

				tempdate = rs.getString("DATE_ENLEV");
				Date daterem = converttodate("1970-01-01");

				if (tempdate == null) {
					daterem = converttodate("1970-01-01");

				} else if (tempdate.length() != 8) {
					daterem = converttodate("1970-01-01");
				} else {
					daterem = converttodate(tempdate);
				}

				String query3 = "INSERT INTO `geode_bd`.`inforemove` (`infoRemoveOrigin`,`infoRemoveCause`,  `infoRemoveVille`, `infoRemoveDep`,`infoRemoveDateRem`,  `infoRemoveidVehi`) "
						+ "VALUES ( '" + origin + "', '" + cause + "', '" + city + "', '" + dep + "', '" + daterem
						+ "', '" + id + "')";

				float buycost = Float.valueOf(removestoopid(rs.getString("PRIXACHA")));
				float frais = Float.valueOf(removestoopid(rs.getString("FRAIS")));
				float depcost = Float.valueOf(removestoopid(rs.getString("COUTDEPAN")));
				float solde = Float.valueOf(removestoopid(rs.getString("SOLDE")));
				int cg = 0;
				if (rs.getString("CGVGA") == "CG")
					cg = 1;

				String query4 = "INSERT INTO `geode_bd`.`paymentvehi` ( `paymentcg`, `paymentrsv`, `paymentbuycost`, `paymentaddcost`, `paymentdepcost`,`paymentsolde`, `idvehicule`) "
						+ "VALUES (  '" + cg + "', '0', '" + buycost + "','" + frais + "', '" + depcost + "', '" + solde
						+ "','" + id + "')";
				stmt2.execute(query2);
				stmt2.execute(query3);
				stmt2.execute(query4);

				id++;

			}

			query = " SELECT * FROM geode_bd.sorties";
			rs = stmt.executeQuery(query);
			id = 1;
			while (rs.next()) {
				Statement stmt2 = conn.createStatement();
				String tempdate = rs.getString("DATSORTI");
				Date dateSortie = converttodate("19700101");

				if (tempdate == null) {
					dateSortie = converttodate("19700101");

				} else if (tempdate.length() != 8) {
					dateSortie = converttodate("19700101");
				} else {
					dateSortie = converttodate(tempdate);
				}

				String query2 = "UPDATE `geode_bd`.`vehicule` SET dateSortie = '" + dateSortie + "' WHERE idvehicule ='"
						+ rs.getInt("CODVEHIC") + "'";
				String name = rs.getString("NOM");
				if (name != null)
					name = invi.FormattingString(name);
				else
					name = "ND";
//
//				String invoicenum = getNumber(rs.getString("FACTURE"));
//				invoicenum = invi.FormattingString(invoicenum);
//
//				String addresse = rs.getString("RUE");
//				if (addresse == null) {
//					addresse = "ND";
//				}
//				addresse = invi.FormattingString(addresse);
//
//				int cp = rs.getInt("CPOST");
//
//				String city = rs.getString("VILLE");
//				if (city == null)
//					city = "ND";
//				city = invi.FormattingString(city);
//
//				String textfac = rs.getString("TEXTE");
//				if (textfac == null)
//					textfac = "ND";
//				textfac = invi.FormattingString(textfac);
//
//				float totalttc = Float.valueOf(removestoopid(rs.getString("PRIXVTE")));
//
//				String query3 = "INSERT INTO `geode_bd`.`invoice` (`invoiceClientType`, `invoiceClient`, `invoicenumber`, `invoiceAdress`, `invoicecp`, `invoiceCity`, `invoiceStatus`, `invoicevehicule`, `invoiceText`, `invoicedate`, `invoiceTotalTTC`, `invoiceTotalHT`, `invoicecountry`)"
//						+ " VALUES ( 'Monsieur', '" + name + "'" + ", '" + invoicenum + "','" + addresse + "', '" + cp
//						+ "','" + city + "', '3', '" + rs.getInt("CODVEHIC") + "','" + textfac + "','" + dateSortie
//						+ "', '" + totalttc + "', '" + totalttc + "', 'France')";
//
//				String query4 = "INSERT INTO `geode_bd`.`invoicelines` (`invoiceId`, `invoicelinesLib`, `invoicelinesTotal`, `invoicelinesqte`,`invoicelinespuht`)"
//						+ " VALUES ( '" + id + "', 'ND'" + ", '" + totalttc + "', '1', '" + totalttc + "')";
//
//				float montant1 = Float.valueOf(removestoopid(rs.getString("MONTANT1")));
//				float montant2 = Float.valueOf(removestoopid(rs.getString("MONTANT2")));
//
//				float montant3 = Float.valueOf(removestoopid(rs.getString("ARRHES")));
//				float montant4 = Float.valueOf(removestoopid(rs.getString("ARRHES2")));
//
//				String query5 = "INSERT INTO `geode_bd`.`paynadv`  (`paytype`, `payamount`,`invoiceidtopay`,`payadvorpay`,`paydate`) VALUES ('ND', '"
//						+ montant1 + "', '" + id + "', '1','" + dateSortie + "')";
//
//				String query6 = "INSERT INTO `geode_bd`.`paynadv`  (`paytype`, `payamount`,`invoiceidtopay`,`payadvorpay`,`paydate`) VALUES ('ND', '"
//						+ montant2 + "', '" + id + "', '1','" + dateSortie + "')";
//				String query7 = "INSERT INTO `geode_bd`.`paynadv`  (`paytype`, `payamount`,`invoiceidtopay`,`payadvorpay`,`paydate`) VALUES ('ND', '"
//						+ montant3 + "', '" + id + "', '1','" + dateSortie + "')";
//				String query8 = "INSERT INTO `geode_bd`.`paynadv`  (`paytype`, `payamount`,`invoiceidtopay`,`payadvorpay`,`paydate`) VALUES ('ND', '"
//						+ montant4 + "', '" + id + "', '1','" + dateSortie + "')";
//
//				stmt2.execute(query2);
//				stmt2.execute(query3);
//				stmt2.execute(query4);
//				stmt2.execute(query5);
//				stmt2.execute(query6);
//				stmt2.execute(query7);
//				stmt2.execute(query8);
//
//				id++;

			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

	}

	public void AddVehicule(String marque, String type, String immat, String carros, String numSer, String energy,
			int hp, int klms, String couleur, Date mecdate, Date cadate, String dest, int hascg) {
		Date currentSqlDate = new Date(System.currentTimeMillis());
		marque = invi.FormattingString(marque);
		type = invi.FormattingString(type);
		immat = invi.FormattingString(immat);
		numSer = invi.FormattingString(numSer);
		couleur = invi.FormattingString(couleur);
		String query = "INSERT INTO `geode_bd`.`vehicule` (`destination`, `marque`, `type`, `immatriculation`, `dateEntree`, `carrosserie`,`numeroSerie`, `energie`, `puissance`, `klms`, `couleur`, `miseEnCircul`, `certifActuel`, `hascg`) VALUES ( '"
				+ dest + "', '" + marque + "', '" + type + "', '" + immat + "','" + currentSqlDate + "', '" + carros
				+ "', '" + numSer + "', '" + energy + "','" + hp + "','" + klms + "','" + couleur + "','" + mecdate
				+ "', '" + cadate + "', '" + hascg + "')";
		System.out.println(query);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			stmt.execute(query);
			String UpdateQuery = "";
			if (hp == 0) {
				UpdateQuery = " `puissance` = NULL";
			}
			if (klms == 0) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + " `klms` = NULL";
			}
			if (numSer.isEmpty()) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + " `numeroSerie` = NULL";
			}
			if (energy == null) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + " `energie` = NULL";
			}
			if (carros == null) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + " `carrosserie` = NULL";
			}
			if (couleur.isEmpty()) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + " `couleur` = NULL";
			}
			if (mecdate.compareTo(java.sql.Date.valueOf(LocalDate.of(1970, 1, 1))) == 0) {
				System.out.println("true");
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + " `miseEnCircul` = NULL";
			}
			if (cadate.compareTo(java.sql.Date.valueOf(LocalDate.of(1970, 1, 1))) == 0) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + " `certifActuel` = NULL";
			}
			if (UpdateQuery != "") {
				UpdateQuery = "UPDATE `geode_bd`.`vehicule` SET" + UpdateQuery + " WHERE (`immatriculation` = '" + immat
						+ "')";
				System.out.println(UpdateQuery);
				stmt.execute(UpdateQuery);
			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
	}

	public void UpdateVehicule(int id, String marque, String type, String immat, String carros, String numSer,
			String energy, int hp, int klms, String couleur, Date mecdate, Date cadate, String dest, int hascg) {
		marque = invi.FormattingString(marque);
		type = invi.FormattingString(type);
		immat = invi.FormattingString(immat);
		numSer = invi.FormattingString(numSer);
		couleur = invi.FormattingString(couleur);
		String query = "UPDATE `geode_bd`.`vehicule` SET `marque` = '" + marque + "', `type` ='" + type
				+ "', `destination`='" + dest + "',`immatriculation` ='" + immat + "', `carrosserie` = '" + carros
				+ "', `numeroSerie` ='" + numSer + "', `energie` ='" + energy + "', `puissance` = '" + hp
				+ "', `klms` ='" + klms + "', `couleur` ='" + couleur + "', `miseEnCircul` ='" + mecdate
				+ "', `certifActuel` ='" + cadate + "', `hascg` = '" + hascg + "' WHERE `idvehicule` = '" + id + "'";

		System.out.println(query);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			stmt.execute(query);
			String UpdateQuery = "";
			if (hp == 0) {
				UpdateQuery = " `puissance` = NULL";
			}
			if (numSer == "") {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + " `numeroSerie` = NULL";
			}
			if (klms == 0) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + " `klms` = NULL";
			}
			if (energy == null) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + " `energie` = NULL";
			}
			if (carros == null) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + " `carrosserie` = NULL";
			}
			if (couleur == "") {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + " `couleur` = NULL";
			}
			if (mecdate.compareTo(java.sql.Date.valueOf(LocalDate.of(1970, 1, 1))) == 0) {

				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + " `miseEnCircul` = NULL";
			}
			if (cadate.compareTo(java.sql.Date.valueOf(LocalDate.of(1970, 1, 1))) == 0) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + " `certifActuel` = NULL";
			}
			if (UpdateQuery != "") {
				UpdateQuery = "UPDATE `geode_bd`.`vehicule` SET" + UpdateQuery + " WHERE (`idvehicule` = '" + id + "')";
				System.out.println(UpdateQuery);
				stmt.execute(UpdateQuery);
			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
	}

	public void AddInfoToRemoval(String origin, String cause, String address, int cp, String city, String dep,
			String pref, Date daterem, int vehi) {
		cause = invi.FormattingString(cause);
		address = invi.FormattingString(address);
		city = invi.FormattingString(city);
		dep = invi.FormattingString(dep);
		pref = invi.FormattingString(pref);
		String query = "INSERT INTO `geode_bd`.`inforemove` (`infoRemoveOrigin`, `infoRemoveCause`, `infoRemoveLieu`, `infoRemoveCP`, `infoRemoveVille`, `infoRemoveDep`,`infoRemoveDateRem`, `infoRemovePref`, `infoRemoveidVehi`) "
				+ "VALUES ( '" + origin + "', '" + cause + "', '" + address + "', '" + cp + "','" + city + "', '" + dep
				+ "', '" + daterem + "','" + pref + "', '" + vehi + "')";

		Connection conn;
		try {
			conn = DriverManager.getConnection(url, user, passwd);
			Statement stmt = conn.createStatement();
			stmt.execute(query);

			if (daterem.compareTo(java.sql.Date.valueOf(LocalDate.of(1970, 1, 1))) == 0) {
				System.out.println("true");

				String UpdateQuery = "UPDATE `geode_bd`.`inforemove` SET `infoRemoveDateRem` = NULL WHERE (`infoRemoveidVehi` = '"
						+ vehi + "')";
				stmt.execute(UpdateQuery);

			}
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void UpdateInfoToRemoval(String origin, String cause, String address, int cp, String city, String dep,
			String pref, Date daterem, int vehi) {
		cause = invi.FormattingString(cause);
		address = invi.FormattingString(address);
		city = invi.FormattingString(city);
		dep = invi.FormattingString(dep);
		pref = invi.FormattingString(pref);
		String query = "UPDATE `geode_bd`.`inforemove` SET `infoRemoveOrigin` = '" + origin + "' , `infoRemoveCause`='"
				+ cause + "', `infoRemoveLieu`='" + address + "', " + "`infoRemoveCP`='" + cp + "', `infoRemoveVille`='"
				+ city + "', `infoRemoveDep`='" + dep + "',`infoRemoveDateRem`='" + daterem + "', `infoRemovePref`='"
				+ pref + "' WHERE (`infoRemoveidVehi` = '" + vehi + "')";

		Connection conn;
		try {
			conn = DriverManager.getConnection(url, user, passwd);
			Statement stmt = conn.createStatement();
			stmt.execute(query);

			if (daterem.compareTo(java.sql.Date.valueOf(LocalDate.of(1970, 1, 1))) == 0) {

				String UpdateQuery = "UPDATE `geode_bd`.`inforemove` SET `infoRemoveDateRem` = NULL WHERE (`infoRemoveidVehi` = '"
						+ vehi + "')";
				stmt.execute(UpdateQuery);

			}
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void AddInfoToPay(Date dest, boolean cg, boolean rsv, float buycost, float frais, float depcost, float solde,
			float cheque1, float cheque2, float cheque3, Date dcheque1, Date dcheque2, Date dcheque3, String num1,
			String num2, String num3, int vehi, Date datecess) {
		int cgv = 0;
		int rsvv = 0;
		if (cg)
			cgv = 1;
		if (rsv)
			rsvv = 1;
		String UpdateQuery = "";
		String deleteInfo = "DELETE FROM `geode_bd`.`paymentvehi` WHERE (`idvehicule` = '" + vehi + "')";
		String query = "INSERT INTO `geode_bd`.`paymentvehi` (`paymentdatedest`, `paymentcg`, `paymentrsv`, `paymentbuycost`, `paymentaddcost`, `paymentdepcost`,`paymentsolde`, `1stcheque`, `2ndcheque`,`3rdcheque`, `1stchequenum`,`2ndchequenum`,`3rdchequenum`,`1stchequedate`,`2ndchequedate`,`3rdchequedate`,`idvehicule`, `dateCessation`) "
				+ "VALUES ( '" + dest + "', '" + cgv + "', '" + rsvv + "', '" + buycost + "','" + frais + "', '"
				+ depcost + "', '" + solde + "','" + cheque1 + "','" + cheque2 + "','" + cheque3 + "','" + num1 + "','"
				+ num2 + "','" + num3 + "','" + dcheque1 + "','" + dcheque2 + "','" + dcheque3 + "','" + vehi + "', '"
				+ datecess + "')";

		Connection conn;
		try {
			conn = DriverManager.getConnection(url, user, passwd);
			Statement stmt = conn.createStatement();
			stmt.execute(deleteInfo);
			stmt.execute(query);

			if (dest.compareTo(java.sql.Date.valueOf(LocalDate.of(1970, 1, 1))) == 0) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + "`paymentdatedest` = NULL";

			} else {
				cl.addEvent("Destruction du véhicule " + vehi, dest, vehi, null);
			}
			if (dcheque1.compareTo(java.sql.Date.valueOf(LocalDate.of(1970, 1, 1))) == 0) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + "`1stchequedate` = NULL";
			}
			if (dcheque2.compareTo(java.sql.Date.valueOf(LocalDate.of(1970, 1, 1))) == 0) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + "`2ndchequedate` = NULL";
			}
			if (dcheque3.compareTo(java.sql.Date.valueOf(LocalDate.of(1970, 1, 1))) == 0) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + "`3rdchequedate` = NULL";
			}
			if (datecess.compareTo(java.sql.Date.valueOf(LocalDate.of(1970, 1, 1))) == 0) {
				if (UpdateQuery != "")
					UpdateQuery = UpdateQuery + ",";
				UpdateQuery = UpdateQuery + "`dateCessation` = NULL";
			}

			if (UpdateQuery != "") {
				UpdateQuery = "UPDATE `geode_bd`.`paymentvehi` SET " + UpdateQuery + " WHERE (`idvehicule` = '" + vehi
						+ "')";
				System.out.println(UpdateQuery);
				stmt.execute(UpdateQuery);
			}

			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addExitDate(int id) {

		java.util.Date today = new java.util.Date();
		Date sqldate = new Date(today.getTime());

		String query = "UPDATE `geode_bd`.`vehicule` SET `dateSortie` ='" + sqldate + "' WHERE `idvehicule` = '" + id
				+ "'";
		Connection conn;
		try {
			conn = DriverManager.getConnection(url, user, passwd);
			Statement stmt = conn.createStatement();
			stmt.execute(query);

			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addPrice(int id, String price) {

		String query = "UPDATE `geode_bd`.`vehicule` SET `proposedprice` ='" + price + "' WHERE `idvehicule` = '" + id
				+ "'";
		Connection conn;
		try {
			conn = DriverManager.getConnection(url, user, passwd);
			Statement stmt = conn.createStatement();
			stmt.execute(query);

			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public java.sql.Date converttodate(String date) {
Date datetorep =java.sql.Date.valueOf(LocalDate.of(2025, 1, 1));
		String years = "";
		if (date.length() >8) {
			
		
		for (int i = 0; i < 4; i++) {
			if (date.charAt(i) == ' ') {
				years = years + '0';
			} else
				years = years + date.charAt(i);
		}

		int year = Integer.valueOf(years);

		String months = "";
		for (int i = 5; i < 7; i++) {
			if (date.charAt(i) == ' ') {
				months = months + '0';
			} else
				months = months + date.charAt(i);
		}

		int month = Integer.valueOf(months);

		String days = "";
		
		for (int i = 8; i < 10; i++) {
			if (date.charAt(i) == ' ') {
				days = days + '0';
			} else
				days = days + date.charAt(i);
		}
		
		int day = Integer.valueOf(days);

		if (year < 1950) {
			years = "20" + years.charAt(2) + years.charAt(3);
			year = Integer.valueOf(years);
		
		}
		
	if (day==31)
		day=30;
	if (day >=29 && month ==2)
		day=28;
		 datetorep = java.sql.Date.valueOf(LocalDate.of(2025, month, day));
		}
		else {
			for (int i = 0; i < 4; i++) {
				if (date.charAt(i) == ' ') {
					years = years + '0';
				} else
					years = years + date.charAt(i);
			}

			int year = Integer.valueOf(years);

			String months = "";
			for (int i = 4; i < 6; i++) {
				if (date.charAt(i) == ' ') {
					months = months + '0';
				} else
					months = months + date.charAt(i);
			}

			int month = Integer.valueOf(months);

			String days = "";
			
			for (int i = 6; i < 8; i++) {
				if (date.charAt(i) == ' ') {
					days = days + '0';
				} else
					days = days + date.charAt(i);
			}
			
			int day = Integer.valueOf(days);

			if (year < 1950) {
				years = "20" + years.charAt(2) + years.charAt(3);
				year = Integer.valueOf(years);
			
			}
			
		if (day==31)
			day=30;
		if (day >=29 && month ==2)
			day=28;
		datetorep = java.sql.Date.valueOf(LocalDate.of(year, month, day));
		}
			
		return datetorep;
	}

	public String removestoopid(String price) {
		String properprice = "";
		for (int i = 0; i < price.length(); i++) {
			if (price.charAt(i) != ',')
				properprice = properprice + price.charAt(i);
			else
				properprice = properprice + '.';

		}

		return properprice;
	}

	public String getNumber(String num) {
		String number = "";
		int count = 0;

		for (int i = 0; i < num.length(); i++) {
			if (num.charAt(i) != 'E' && count == 0)
				number = number + num.charAt(i);
			else
				count++;

		}

		return number;
	}
}
