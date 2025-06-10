package back;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import front.Analitycs;

public class AnalitycsController {

	String BDD = "geode_bd";
	String url = "jdbc:mysql://192.168.1.85:3306/" + BDD;
	String user = "username";
	String passwd = "password";
	public void getVehiculesoveryear(Analitycs al) {

		List<Integer> totalvehilist = new ArrayList<Integer>();
		List<Integer> totalout = new ArrayList<Integer>();
		Date today = new Date();
		Calendar cl = Calendar.getInstance();
		Calendar clsql = Calendar.getInstance();
		cl.setTime(today);

		cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) - 12);
		java.sql.Date sqldate = java.sql.Date
				.valueOf(LocalDate.of(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH) + 1, 1));

		String Query = "SELECT* FROM geode_bd.vehicule WHERE dateEntree >= '" + sqldate + "' ORDER BY dateEntree";
		String Queryout = "SELECT* FROM geode_bd.vehicule WHERE dateSortie >= '" + sqldate
				+ "' AND dateSortie IS NOT NULL ORDER BY dateSortie";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(Query);
			cl.set(Calendar.DATE, 1);
			int j = 0;
			int buff = 0;

			while (rs.next()) {
				clsql.setTime(rs.getDate("dateEntree"));

				if (clsql.get(Calendar.MONTH) == cl.get(Calendar.MONTH)
						&& clsql.get(Calendar.YEAR) == cl.get(Calendar.YEAR)) {
					j++;

					// System.out.println(buff);
				} else {

					while ((cl.get(Calendar.MONTH) != clsql.get(Calendar.MONTH)
							| (cl.get(Calendar.MONTH) == clsql.get(Calendar.MONTH)
									&& clsql.get(Calendar.YEAR) != cl.get(Calendar.YEAR)))
							&& buff != 12) {

						totalvehilist.add(j);

						j = 0;
						buff++;
						cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) + 1);

					}

					if (buff != 12)
						j++;

				}
			}
			totalvehilist.add(j);
			while (totalvehilist.size() < 13) {
				totalvehilist.add(0);
			}

			rs = stmt.executeQuery(Queryout);

			cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) - 11);

			j = 0;
			buff = 0;
			while (rs.next()) {
				clsql.setTime(rs.getDate("dateSortie"));

				if (clsql.get(Calendar.MONTH) == cl.get(Calendar.MONTH)
						&& clsql.get(Calendar.YEAR) == cl.get(Calendar.YEAR)) {
					j++;

					// System.out.println(buff);
				} else {
					while ((cl.get(Calendar.MONTH) != clsql.get(Calendar.MONTH)
							| (cl.get(Calendar.MONTH) == clsql.get(Calendar.MONTH)
									&& clsql.get(Calendar.YEAR) != cl.get(Calendar.YEAR)))
							&& buff != 12) {

						totalout.add(j);

						j = 0;
						buff++;
						cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) + 1);

					}
					if (buff != 12)
						j++;
				}
			}
			totalout.add(j);
			while (totalout.size() < 13) {
				totalout.add(0);
			}
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		al.totalvehi = totalvehilist;
		al.totalvehiout = totalout;

	}

	public int getTotalFinishedInvoice() {
		int i = 0;
		String query = " SELECT COUNT(*) AS numInvoice FROM geode_bd.invoice  WHERE invoiceStatus = '1' ";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				i = rs.getInt("numInvoice");

			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return i;

	}

	public int getTotalPaidInvoice() {

		int i = 0;
		String query = " SELECT COUNT(*) AS numInvoice FROM geode_bd.invoice  WHERE invoiceStatus >= '3' ";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				i = rs.getInt("numInvoice");

			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return i;

	}

	public void GetCA(Analitycs al) {

		List<Float> income = new ArrayList<Float>();
		List<Float> outcome = new ArrayList<Float>();
		Date today = new Date();
		Calendar cl = Calendar.getInstance();

		Calendar clsql = Calendar.getInstance();
		cl.setTime(today);

		cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) - 12);
		java.sql.Date sqldate = java.sql.Date
				.valueOf(LocalDate.of(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH) + 1, 1));

		String Query = "SELECT* FROM geode_bd.paynadv WHERE paydate >= '" + sqldate + "' ORDER BY paydate";
		String Queryout = "SELECT* FROM geode_bd.paymentvehi WHERE 1stchequedate IS NOT NULL AND 1stchequedate >= '"
				+ sqldate + "'  ORDER BY 1stchequedate";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(Query);
			cl.set(Calendar.DATE, 1);
			float j = 0;
			int buff = 0;
			while (rs.next()) {
				clsql.setTime(rs.getDate("paydate"));

				if (clsql.get(Calendar.MONTH) == cl.get(Calendar.MONTH)
						&& clsql.get(Calendar.YEAR) == cl.get(Calendar.YEAR)) {
					j = j + rs.getFloat("payamount");

					// System.out.println(buff);
				}

				else {

					while ((cl.get(Calendar.MONTH) != clsql.get(Calendar.MONTH)
							| (cl.get(Calendar.MONTH) == clsql.get(Calendar.MONTH)
									&& clsql.get(Calendar.YEAR) != cl.get(Calendar.YEAR)))
							&& buff != 12) {

						income.add(j);

						j = 0;
						buff++;
						cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) + 1);

					}

					if (buff != 12)
						j++;
				}
			}
			income.add(j);

			while (income.size() < 13) {
				income.add(0f);
			}

			rs = stmt.executeQuery(Queryout);
			cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) - 11);

			j = 0;
			buff = 0;
			while (rs.next()) {

				clsql.setTime(rs.getDate("1stchequedate"));

				if (clsql.get(Calendar.MONTH) == cl.get(Calendar.MONTH)
						&& clsql.get(Calendar.YEAR) == cl.get(Calendar.YEAR)) {
					j = j + rs.getFloat("1stcheque");

					// System.out.println(buff);
				}

				//

				else {
					while ((cl.get(Calendar.MONTH) != clsql.get(Calendar.MONTH)
							| (cl.get(Calendar.MONTH) == clsql.get(Calendar.MONTH)
									&& clsql.get(Calendar.YEAR) != cl.get(Calendar.YEAR)))
							&& buff != 12) {

						outcome.add(j);

						j = 0;
						buff++;
						cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) + 1);

					}
					if (buff != 12)
						j = j + rs.getFloat("1stcheque");

				}
				outcome.add(j);

				while (outcome.size() < 13) {
					outcome.add(0f);
				}
				cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) - 11);
				for (int i = 0; i < 12; i++) {
					j = 0;

					if (rs.getDate("2ndchequedate") != null) {

						clsql.setTime(rs.getDate("2ndchequedate"));

						if (clsql.get(Calendar.MONTH) == cl.get(Calendar.MONTH)
								&& clsql.get(Calendar.YEAR) == cl.get(Calendar.YEAR)) {
							j = j + rs.getFloat("2ndcheque");

							// System.out.println(buff);
						}
					}
					if (rs.getDate("3rdchequedate") != null) {
						clsql.setTime(rs.getDate("3rdchequedate"));

						if (clsql.get(Calendar.MONTH) == cl.get(Calendar.MONTH)
								&& clsql.get(Calendar.YEAR) == cl.get(Calendar.YEAR)) {
							j = j + rs.getFloat("3rdcheque");

							// System.out.println(buff);
						}
					}
					outcome.set(i, outcome.get(i) + j);
					cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) + 1);
				}

			}

			while (outcome.size() < 13) {
				outcome.add(0f);
			}
			conn.close();

		}

		catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		al.income = income;
		al.outcome = outcome;

	}
}
