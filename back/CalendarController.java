package back;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import front.CalendarApp;
import front.HomePage;

public class CalendarController {

	String BDD = "geode_bd";
	String url = "jdbc:mysql://192.168.1.85:3306/" + BDD;
	String user = "username";
	String passwd = "password";
	public void addEvent(String eventName, Date date, int idvehi, Time eventtime) {

		String query = "INSERT INTO `geode_bd`.`calendar` (`eventname`, `eventdate`, `vehiculeId`)" + " VALUES ( '"
				+ eventName + "', '" + date + "', '" + idvehi + "')";

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

	public void UpdateEvent(String eventName, Date date, int idvehi, int eventid, Time eventtime) {

		String query = "UPDATE `geode_bd`.`calendar` SET `eventname` = '" + eventName + "', `eventdate` = '" + date
				+ "', `vehiculeId`= '" + idvehi + "' WHERE idCalendar ='" + eventid + "'";

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

	public void DeleteEvent(int eventid) {

		String query = "DELETE FROM `geode_bd`.`calendar` WHERE idCalendar = '" + eventid + "'";

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

	public void GetAllEvent(CalendarApp cal) {

		List<EventClass> eventList = new ArrayList<EventClass>();
		String query = " SELECT * FROM geode_bd.calendar";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
				eventList.add(new EventClass(rs.getDate("eventdate"), rs.getInt("idCalendar"), rs.getInt("vehiculeId"),
						rs.getString("eventname"), rs.getTime("eventtime")));

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		cal.allEvent = eventList;
	}

	public void GetAllUpcomingEvent(HomePage cal) {

		java.util.Date today = new java.util.Date();

		Date sqlDate = new Date(today.getTime());
		List<EventClass> eventList = new ArrayList<EventClass>();
		String query = " SELECT * FROM geode_bd.calendar WHERE eventdate >= '" + sqlDate + "' ORDER BY eventdate";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
				eventList.add(new EventClass(rs.getDate("eventdate"), rs.getInt("idCalendar"), rs.getInt("vehiculeId"),
						rs.getString("eventname"), rs.getTime("eventtime")));

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		cal.allEvent = eventList;
	}

	public int GetAllPastEvent() {
		int total = 0;
		java.util.Date today = new java.util.Date();

		Date sqlDate = new Date(today.getTime());

		String query = " SELECT * FROM geode_bd.calendar WHERE eventdate <= '" + sqlDate
				+ "' AND vehiculeId IS NOT NULL AND vehiculeId != '0'";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
				total++;

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return total;
	}
	
	public void GetAllrappelEvent(CalendarApp cal) {

		java.util.Date today = new java.util.Date();

		Date sqlDate = new Date(today.getTime());
		List<EventClass> eventList = new ArrayList<EventClass>();
		String query = " SELECT * FROM geode_bd.calendar WHERE eventdate <= '" + sqlDate + "' AND vehiculeId > '0'";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
				eventList.add(new EventClass(rs.getDate("eventdate"), rs.getInt("idCalendar"), rs.getInt("vehiculeId"),
						rs.getString("eventname"), rs.getTime("eventtime")));

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		cal.allRappelEvent = eventList;
	}
}
