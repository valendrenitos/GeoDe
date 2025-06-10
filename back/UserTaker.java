package back;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import front.ParametersScene;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserTaker {
	String BDD = "geode_bd";
	String url = "jdbc:mysql://192.168.1.85:3306/" + BDD;
	String user = "username";
	String passwd = "password";
InvoiceInput invi = new InvoiceInput();
	public User ValidateUser(String username, String password) {
username = invi.FormattingString(username);
password=invi.FormattingString(password);
		User utilisateur = new User("0", -1, null, 0);
		String query = " SELECT * FROM geode_bd.users WHERE `username`= '" + username + "' AND `userpassword`= '"
				+ password + "'";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {

				utilisateur.setUsername(username);
				utilisateur.setUserrole(rs.getInt("userrole"));
				utilisateur.setPassword(rs.getString("userpassword"));
				utilisateur.setUserid(rs.getInt("idusers"));

			}
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return utilisateur;

	}

	public void getAllUsers(ParametersScene pams) {
		TableView<User> stockTable = new TableView<User>();

		TableColumn<User, String> immatCollumn = new TableColumn<>("Utilisateur");
		immatCollumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		TableColumn<User, Integer> marqueCollumn = new TableColumn<>("Role");
		marqueCollumn.setCellValueFactory(new PropertyValueFactory<>("nameRole"));
		stockTable.getColumns().add(immatCollumn);
		stockTable.getColumns().add(marqueCollumn);

		TableViewSelectionModel<User> selectionModel = stockTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		ObservableList<User> selectedItems = selectionModel.getSelectedItems();

		selectedItems.addListener(new ListChangeListener<User>() {
			@Override
			public void onChanged(Change<? extends User> change) {
				pams.del.setVisible(true);
				pams.modif.setVisible(true);
				pams.listener = selectedItems.get(0);
			}
		});

		String query = " SELECT * FROM geode_bd.users";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {

				String role = "";
				if (rs.getInt("userrole") == 0)
					role = "Admin";
				else if (rs.getInt("userrole") == 1)
					role = "Bureau";
				else
					role = "Terrain";
				stockTable.getItems().add(new User(rs.getString("username"), rs.getInt("userrole"),
						rs.getString("userpassword"), rs.getInt("idusers"), role));
			}

			conn.close();
			pams.listUtilisateur = stockTable;
		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

	}
}
