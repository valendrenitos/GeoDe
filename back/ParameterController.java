package back;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import front.ParametersScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class ParameterController {

	String BDD = "geode_bd";
	String url = "jdbc:mysql://192.168.1.85:3306/" + BDD;
	String user = "username";
	String passwd = "password";
	InvoiceInput invi = new InvoiceInput();
	public void comparePassword(User utilisateur, String passentered, ParametersScene param, String newpass) {
passentered=invi.FormattingString(passentered);
newpass=invi.FormattingString(newpass);
		if (utilisateur.getPassword() == passentered) {

			HBox Validationbox = new HBox();
			Validationbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
					+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;" + "-fx-border-color: #3b703d;"
					+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

			Label ValidationLabel = new Label("Le mot de Passe a bien été modifié ! ");
			ValidationLabel.setFont(Font.font("Arial", 20));
			ValidationLabel.setStyle("-fx-text-fill: #000000");
			Validationbox.setPadding(new Insets(20));
			Validationbox.setAlignment(Pos.BASELINE_CENTER);
			Validationbox.getChildren().addAll(ValidationLabel);
			param.grid.getChildren()
					.removeIf(node -> GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 1);
			param.grid.add(Validationbox, 1, 1);
			changePassword(utilisateur, newpass);

		} else {
			HBox Deniedbox = new HBox();
			Deniedbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
					+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;" + "-fx-border-color: #ed7777;"
					+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

			Label DeniedLabel = new Label("Mot de Passe incorrect ! ");
			DeniedLabel.setFont(Font.font("Arial", 20));
			DeniedLabel.setStyle("-fx-text-fill: #000000");
			Deniedbox.setPadding(new Insets(20));
			Deniedbox.setAlignment(Pos.BASELINE_CENTER);
			Deniedbox.getChildren().addAll(DeniedLabel);
			param.grid.getChildren()
					.removeIf(node -> GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 1);
			param.grid.add(Deniedbox, 1, 1);
		}

	}

	public void changePassword(User utilisateur, String password) {
password=invi.FormattingString(password);
		String query = "UPDATE `geode_bd`.`users` SET `userpassword` = '" + password + "' WHERE idusers ='"
				+ utilisateur.getUserid() + "'  ";
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

	public String[] GetInfo() {

		String[] info = new String[12];
		String query = "SELECT * FROM `geode_bd`.`infos` ";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int i = 0;
			while (rs.next()) {
				info[i] = (rs.getString("InfosColumns"));
				i++;
			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		return info;
	}

	public void modifyInfos(String[] info) {
		for (int i = 0; i < 12; i++) {
			String query = "UPDATE `geode_bd`.`infos`  SET `InfosColumns` = '" + info[i] + "' WHERE idinfos = '" + (i
					+ 1) + "' ";
			info[i]=invi.FormattingString(info[i]);
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(url, user, passwd);
				Statement stmt = conn.createStatement();

			
				stmt.execute(query);
				System.out.println(info[i]);

				conn.close();

			} catch (Exception e) {
				e.printStackTrace();

				System.exit(0);
			}
		}
	}

	public void addUser(String username, String userroles) {
username=invi.FormattingString(username);
		int roles = 2;
		if (userroles != "Terrain")
			roles = 1;

		String query = "INSERT INTO  `geode_bd`.`users` (`username`,`userpassword`,`userrole`) VALUES ('" + username
				+ "' , '123456', '" + roles + "')";
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

	public void ModifyUser(User Listener, String username, String userroles, String newpass) {

		username=invi.FormattingString(username);
		newpass=invi.FormattingString(newpass);
		
		int roles = 2;
		if (userroles != "Terrain")
			roles = 1;

		String query = "UPDATE `geode_bd`.`users` SET `username` = '" + username + "', `userrole`= '" + roles
				+ "', `userpassword`='" + newpass + "' WHERE `idusers` = '" + Listener.getUserid() + "'";
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

	public void DeleteUser(User deleted) {

		String query = "DELETE FROM `geode_bd`.`users` WHERE `idusers` = '" + deleted.getUserid() + "'";
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

}
