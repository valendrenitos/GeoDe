package back;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import front.App;
import front.StockPage;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;

public class DataTaker {

	String BDD = "geode_bd";
	String url = "jdbc:mysql://192.168.1.85:3306/" + BDD;
	String user = "username";
	String passwd = "password";

	vehiculePrev vehiprev = new vehiculePrev();

	public int stockVehiculeLength() {
		int vehiculeStock = 0;
		String query = " SELECT * FROM geode_bd.vehicule WHERE dateSortie IS NULL";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
				vehiculeStock++;

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return vehiculeStock;
	}

	public int dateTaker() {
		int result = 0;

		Date today = new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(today);
		cl.set(Calendar.DATE, 1);
		Date cldate = cl.getTime();
		java.sql.Date currentSqlDate = new java.sql.Date(cldate.getTime());

		String query = " SELECT * FROM geode_bd.vehicule WHERE dateEntree > '" + currentSqlDate + "'";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
				result++;

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return result;
	}

	public int datereminder3Taker() {
		int result = 0;

		Date today = new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(today);
		cl.set(Calendar.MONTH, cl.MONTH - 3);
		Date cldate = cl.getTime();
		java.sql.Date currentSqlDate = new java.sql.Date(cldate.getTime());

		String query = " SELECT * FROM geode_bd.vehicule WHERE dateEntree < '" + currentSqlDate
				+ "' AND dateSortie IS NULL";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
				result++;

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return result;
	}

	public int datereminderCG() {
		int result = 0;

		Date today = new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(today);
		cl.set(Calendar.MONTH, cl.MONTH - 3);
		Date cldate = cl.getTime();
		java.sql.Date currentSqlDate = new java.sql.Date(cldate.getTime());

		String query = " SELECT * FROM geode_bd.vehicule WHERE dateEntree < '" + currentSqlDate
				+ "' AND dateSortie IS NULL  AND hascg ='0'";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
				result++;

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return result;
	}

	public int leftTaker() {
		int result = 0;

		Date today = new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(today);
		cl.set(Calendar.DATE, 1);
		Date cldate = cl.getTime();
		java.sql.Date currentSqlDate = new java.sql.Date(cldate.getTime());

		String query = " SELECT * FROM geode_bd.vehicule WHERE dateSortie > '" + currentSqlDate + "'";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next())
				result++;

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return result;
	}

	public TableView<vehicule> getStockVehicules(App app, StockPage stock) {
		TableView<vehicule> stockTable = new TableView<vehicule>();

		stockTable.setRowFactory(t -> new TableRow<vehicule>() {
			@Override
			public void updateItem(vehicule item, boolean empty) {
				super.updateItem(item, empty);

				if (item == null) {
					setStyle("");
				} else if (item.getHascg() == 1) {
					setStyle("-fx-background-color:#aef5bb;");

				} else if (getBurnt(item.getId())) {
					setStyle("-fx-background-color:#f593a3;");
				} else
					setStyle("");
				setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!isEmpty())) {
						vehicule rowData = getItem();

						System.out.println("Double click on: " + rowData.getMarque());
						app.showModifyVehiculeScene(rowData.getImmatriculation());
					}
				});
			}
		});

		// row.
		// return row;

		TableColumn<vehicule, String> immatCollumn = new TableColumn<>("Immatriculation");
		immatCollumn.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
		TableColumn<vehicule, String> marqueCollumn = new TableColumn<>("Marque");
		marqueCollumn.setCellValueFactory(new PropertyValueFactory<>("marque"));
		TableColumn<vehicule, String> modeleCollumn = new TableColumn<>("Type");
		modeleCollumn.setCellValueFactory(new PropertyValueFactory<>("type"));

		TableColumn<vehicule, Date> dateEntreeCollumn = new TableColumn<>("Date d'entrée");
		dateEntreeCollumn.setCellValueFactory(new PropertyValueFactory<>("dateEntree"));
		TableColumn<vehicule, String> couleurCollumn = new TableColumn<>("Couleur");
		couleurCollumn.setCellValueFactory(new PropertyValueFactory<>("couleur"));
		TableColumn<vehicule, String> destCollumn = new TableColumn<>("Destination");
		destCollumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
		TableColumn<vehicule, String> numSerCollumn = new TableColumn<>("Numéro de Série");
		numSerCollumn.setCellValueFactory(new PropertyValueFactory<>("numeroSerie"));

		TableColumn<vehicule, Integer> idCollumn = new TableColumn<>("ID ");
		idCollumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		stockTable.getColumns().add(idCollumn);
		stockTable.getColumns().add(immatCollumn);
		stockTable.getColumns().add(marqueCollumn);
		stockTable.getColumns().add(modeleCollumn);
		stockTable.getColumns().add(numSerCollumn);
		stockTable.getColumns().add(couleurCollumn);
		stockTable.getColumns().add(destCollumn);
		stockTable.getColumns().add(dateEntreeCollumn);

		TableViewSelectionModel<vehicule> selectionModel = stockTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);

		ObservableList<vehicule> selectedItems = selectionModel.getSelectedItems();

		selectedItems.addListener(new ListChangeListener<vehicule>() {
			@Override
			public void onChanged(Change<? extends vehicule> change) {
				vehiprev.prevVehicule(change.getList().get(0), stock);

			}
		});

		String query = " SELECT * FROM geode_bd.vehicule WHERE dateSortie IS NULL ORDER BY dateEntree  DESC ";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int count = 0;
			while (rs.next() && count < 500) {

				stockTable.getItems()
						.add(new vehicule(rs.getString("marque"), rs.getString("type"), rs.getString("carrosserie"),
								rs.getString("numeroSerie"), rs.getString("energie"), rs.getInt("puissance"),
								rs.getInt("klms"), rs.getDate("miseEnCircul"), rs.getString("immatriculation"),
								rs.getString("couleur"), rs.getString("destination"), rs.getDate("certifActuel"),
								rs.getDate("dateEntree"), rs.getDate("dateSortie"), rs.getInt("idvehicule"),
								rs.getInt("hascg")));
				// if (rs.getInt("hascg")==0)
				// TableRow<vehicule> row
				// =stockTable.getItems(stockTable.getItems().size()-1).setStyle("-fx-background-color:
				// #ff2bb1;");
				count++;
			}

			// boolean empty = rs2.next();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return stockTable;
	}

	// Table de données après recherche
	public TableView<vehicule> getFilteredRequest(App app, String marque, String type, String immat, String numSerie,
			String couleur, String dest, String id, boolean verifpage, StockPage stock, String hascg, boolean brulee) {
		String query;
		TableView<vehicule> stockFilteredTable = new TableView<vehicule>();
		// Lecture ligne double click
		stockFilteredTable.setRowFactory(t -> new TableRow<vehicule>() {
			@Override
			public void updateItem(vehicule item, boolean empty) {
				super.updateItem(item, empty);

				if (item == null) {
					setStyle("");
				} else if (item.getHascg() == 1) {
					setStyle("-fx-background-color:#aef5bb;");

				} else if (getBurnt(item.getId())) {
					setStyle("-fx-background-color:#f593a3;");
				} else
					setStyle("");
				setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!isEmpty())) {
						vehicule rowData = getItem();

						System.out.println("Double click on: " + rowData.getMarque());
						app.showModifyVehiculeScene(rowData.getImmatriculation());
					}
				});
			}
		});

		// ajout colonne sur le tableau
		TableColumn<vehicule, String> immatCollumn = new TableColumn<>("Immatriculation");
		immatCollumn.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
		TableColumn<vehicule, String> marqueCollumn = new TableColumn<>("Marque");
		marqueCollumn.setCellValueFactory(new PropertyValueFactory<>("marque"));
		TableColumn<vehicule, String> modeleCollumn = new TableColumn<>("Type");
		modeleCollumn.setCellValueFactory(new PropertyValueFactory<>("type"));

		TableColumn<vehicule, Date> dateEntreeCollumn = new TableColumn<>("Date d'entrée");
		dateEntreeCollumn.setCellValueFactory(new PropertyValueFactory<>("dateEntree"));
		TableColumn<vehicule, String> couleurCollumn = new TableColumn<>("Couleur");
		couleurCollumn.setCellValueFactory(new PropertyValueFactory<>("couleur"));
		TableColumn<vehicule, String> destCollumn = new TableColumn<>("Destination");
		destCollumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
		TableColumn<vehicule, String> numSerCollumn = new TableColumn<>("Numéro de Série");
		numSerCollumn.setCellValueFactory(new PropertyValueFactory<>("numeroSerie"));
		TableColumn<vehicule, Integer> idCollumn = new TableColumn<>("ID ");
		idCollumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		stockFilteredTable.getColumns().add(idCollumn);
		stockFilteredTable.getColumns().add(immatCollumn);
		stockFilteredTable.getColumns().add(marqueCollumn);
		stockFilteredTable.getColumns().add(modeleCollumn);
		stockFilteredTable.getColumns().add(numSerCollumn);
		stockFilteredTable.getColumns().add(couleurCollumn);
		stockFilteredTable.getColumns().add(destCollumn);
		stockFilteredTable.getColumns().add(dateEntreeCollumn);
		if (verifpage) {
			TableColumn<vehicule, Date> dateSortieCollumn = new TableColumn<>("Date de Sortie");
			dateSortieCollumn.setCellValueFactory(new PropertyValueFactory<>("dateSortie"));
			stockFilteredTable.getColumns().add(dateSortieCollumn);
		}

		TableViewSelectionModel<vehicule> selectionModel = stockFilteredTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);

		ObservableList<vehicule> selectedItems = selectionModel.getSelectedItems();

		selectedItems.addListener(new ListChangeListener<vehicule>() {
			@Override
			public void onChanged(Change<? extends vehicule> change) {
				vehiprev.prevVehicule(change.getList().get(0), stock);

			}
		});

		// Creation query

		if (verifpage)
			query = " SELECT * FROM geode_bd.vehicule v , geode_bd.paymentvehi a WHERE v.idvehicule IS NOT NULL AND v.idvehicule = a.idvehicule ";
		else
			query = "SELECT * FROM geode_bd.vehicule v, geode_bd.paymentvehi a WHERE v.dateSortie IS NULL AND v.idvehicule = a.idvehicule ";
		if (marque != "") {
			query = query + "AND v.marque LIKE '%" + marque + "%'";
		}
		if (type != "") {
			query = query + "AND v.type LIKE '%" + type + "%'";
		}
		if (immat != "") {
			query = query + "AND v.immatriculation LIKE '%" + immat + "%'";
		}
		if (numSerie != "") {
			query = query + "AND v.numeroSerie LIKE '%" + numSerie + "%'";
		}
		if (!couleur.isEmpty()) {
			query = query + "AND v.couleur LIKE '%" + couleur + "%'";
		}
		if (!id.isEmpty()) {
			query = query + "AND v.idvehicule LIKE '%" + id + "%'";
		}
		if (dest == "Occasion" | dest == "Assurance" | dest == "Casse" | dest == "Dépot") {
			query = query + "AND v.destination = '" + dest + "'";
		}
		if (hascg == "Avec CG") {
			query = query + "AND v.hascg = '1'";
		} else if (hascg == "Sans CG")
			query = query + "AND v.hascg = '0'";
		if (brulee) {
			query = query + "AND a.paymentcg = '1'";
		}

		query = query + " ORDER BY v.idvehicule";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);
			System.out.println(query);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int count = 0;
			while (rs.next() && count < 500) {

				stockFilteredTable.getItems()
						.add(new vehicule(rs.getString("marque"), rs.getString("type"), rs.getString("carrosserie"),
								rs.getString("numeroSerie"), rs.getString("energie"), rs.getInt("puissance"),
								rs.getInt("klms"), rs.getDate("miseEnCircul"), rs.getString("immatriculation"),
								rs.getString("couleur"), rs.getString("destination"), rs.getDate("certifActuel"),
								rs.getDate("dateEntree"), rs.getDate("dateSortie"), rs.getInt("idvehicule"),
								rs.getInt("hascg")));
				count++;
			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return stockFilteredTable;
	}

	public ResultSet GetVehiculeByImmat(String immat) {
		ResultSet rs = null;
		String query = " SELECT * FROM geode_bd.vehicule WHERE immatriculation = '" + immat + "'";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		return rs;

	}

	public ResultSet GetVehiculeByImmatandOutDate(String immat) {
		ResultSet rs = null;
		String query = " SELECT * FROM geode_bd.vehicule WHERE immatriculation = '" + immat
				+ "' AND dateSortie IS NULL";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		return rs;

	}

	public ResultSet GetVehiculeById(int id) {
		ResultSet rs = null;
		String query = " SELECT * FROM geode_bd.vehicule WHERE idvehicule = '" + id + "'";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		return rs;

	}

	public TableView<vehicule> getAllVehicules(App app, StockPage stock) {
		TableView<vehicule> stockTable = new TableView<vehicule>();
		stockTable.setRowFactory(t -> new TableRow<vehicule>() {
			@Override
			public void updateItem(vehicule item, boolean empty) {
				super.updateItem(item, empty);

				if (item == null) {
					setStyle("");
				} else if (item.getHascg() == 1) {
					setStyle("-fx-background-color:#aef5bb;");

				} else if (getBurnt(item.getId())) {
					setStyle("-fx-background-color:#f593a3;");
				} else
					setStyle("");
				setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!isEmpty())) {
						vehicule rowData = getItem();

						System.out.println("Double click on: " + rowData.getMarque());
						app.showModifyVehiculeScene(rowData.getImmatriculation());
					}
				});
			}
		});
		TableColumn<vehicule, String> immatCollumn = new TableColumn<>("Immatriculation");
		immatCollumn.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
		TableColumn<vehicule, String> marqueCollumn = new TableColumn<>("Marque");
		marqueCollumn.setCellValueFactory(new PropertyValueFactory<>("marque"));
		TableColumn<vehicule, String> modeleCollumn = new TableColumn<>("Type");
		modeleCollumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		TableColumn<vehicule, Date> dateEntreeCollumn = new TableColumn<>("Date d'entrée");
		dateEntreeCollumn.setCellValueFactory(new PropertyValueFactory<>("dateEntree"));
		TableColumn<vehicule, String> couleurCollumn = new TableColumn<>("Couleur");
		couleurCollumn.setCellValueFactory(new PropertyValueFactory<>("couleur"));
		TableColumn<vehicule, String> destCollumn = new TableColumn<>("Destination");
		destCollumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
		TableColumn<vehicule, String> numSerCollumn = new TableColumn<>("Numéro de Série");
		numSerCollumn.setCellValueFactory(new PropertyValueFactory<>("numeroSerie"));

		TableColumn<vehicule, Integer> idCollumn = new TableColumn<>("ID ");
		idCollumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		stockTable.getColumns().add(idCollumn);
		stockTable.getColumns().add(immatCollumn);
		stockTable.getColumns().add(marqueCollumn);
		stockTable.getColumns().add(modeleCollumn);
		stockTable.getColumns().add(numSerCollumn);
		stockTable.getColumns().add(couleurCollumn);
		stockTable.getColumns().add(destCollumn);
		stockTable.getColumns().add(dateEntreeCollumn);

		TableColumn<vehicule, Date> dateSortieCollumn = new TableColumn<>("Date de Sortie");
		dateSortieCollumn.setCellValueFactory(new PropertyValueFactory<>("dateSortie"));
		stockTable.getColumns().add(dateSortieCollumn);

		TableViewSelectionModel<vehicule> selectionModel = stockTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);

		ObservableList<vehicule> selectedItems = selectionModel.getSelectedItems();
		selectedItems.addListener(new ListChangeListener<vehicule>() {
			@Override
			public void onChanged(Change<? extends vehicule> change) {
				vehiprev.prevVehicule(change.getList().get(0), stock);

			}
		});

		String query = " SELECT * FROM geode_bd.vehicule ORDER BY dateEntree  DESC";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
int count =0;
			while (rs.next() && count<500) {
				stockTable.getItems()
						.add(new vehicule(rs.getString("marque"), rs.getString("type"), rs.getString("carrosserie"),
								rs.getString("numeroSerie"), rs.getString("energie"), rs.getInt("puissance"),
								rs.getInt("klms"), rs.getDate("miseEnCircul"), rs.getString("immatriculation"),
								rs.getString("couleur"), rs.getString("destination"), rs.getDate("certifActuel"),
								rs.getDate("dateEntree"), rs.getDate("dateSortie"), rs.getInt("idvehicule"),
								rs.getInt("hascg")));
count++;
			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return stockTable;
	}

	public TableView entryFilterRequest(App app, StockPage stock) {

		TableView<vehicule> stockTable = new TableView<vehicule>();

		stockTable.setRowFactory(t -> new TableRow<vehicule>() {
			@Override
			public void updateItem(vehicule item, boolean empty) {
				super.updateItem(item, empty);

				if (item == null) {
					setStyle("");
				} else if (item.getHascg() == 1) {
					setStyle("-fx-background-color: #aef5bb;");

				} else if (getBurnt(item.getId())) {
					setStyle("-fx-background-color:#f593a3;");
				} else
					setStyle("");
				setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!isEmpty())) {
						vehicule rowData = getItem();

						System.out.println("Double click on: " + rowData.getMarque());
						app.showModifyVehiculeScene(rowData.getImmatriculation());
					}
				});
			}
		});

		TableColumn<vehicule, String> immatCollumn = new TableColumn<>("Immatriculation");
		immatCollumn.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
		TableColumn<vehicule, String> marqueCollumn = new TableColumn<>("Marque");
		marqueCollumn.setCellValueFactory(new PropertyValueFactory<>("marque"));
		TableColumn<vehicule, String> modeleCollumn = new TableColumn<>("Type");
		modeleCollumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		TableColumn<vehicule, Date> dateEntreeCollumn = new TableColumn<>("Date d'entrée");
		dateEntreeCollumn.setCellValueFactory(new PropertyValueFactory<>("dateEntree"));
		TableColumn<vehicule, String> couleurCollumn = new TableColumn<>("Couleur");
		couleurCollumn.setCellValueFactory(new PropertyValueFactory<>("couleur"));
		TableColumn<vehicule, String> destCollumn = new TableColumn<>("Destination");
		destCollumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
		TableColumn<vehicule, String> numSerCollumn = new TableColumn<>("Numéro de Série");
		numSerCollumn.setCellValueFactory(new PropertyValueFactory<>("numeroSerie"));

		TableColumn<vehicule, Integer> idCollumn = new TableColumn<>("ID ");
		idCollumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		stockTable.getColumns().add(idCollumn);
		stockTable.getColumns().add(immatCollumn);
		stockTable.getColumns().add(marqueCollumn);
		stockTable.getColumns().add(modeleCollumn);
		stockTable.getColumns().add(numSerCollumn);
		stockTable.getColumns().add(couleurCollumn);
		stockTable.getColumns().add(destCollumn);
		stockTable.getColumns().add(dateEntreeCollumn);

		TableColumn<vehicule, Date> dateSortieCollumn = new TableColumn<>("Date de Sortie");
		dateSortieCollumn.setCellValueFactory(new PropertyValueFactory<>("dateSortie"));
		stockTable.getColumns().add(dateSortieCollumn);

		TableViewSelectionModel<vehicule> selectionModel = stockTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);

		ObservableList<vehicule> selectedItems = selectionModel.getSelectedItems();

		selectedItems.addListener(new ListChangeListener<vehicule>() {
			@Override
			public void onChanged(Change<? extends vehicule> change) {
				vehiprev.prevVehicule(change.getList().get(0), stock);

			}
		});

		Date today = new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(today);
		cl.set(Calendar.DATE, 1);
		Date cldate = cl.getTime();
		java.sql.Date currentSqlDate = new java.sql.Date(cldate.getTime());

		String query = " SELECT * FROM geode_bd.vehicule WHERE dateEntree > '" + currentSqlDate + "'";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				stockTable.getItems()
						.add(new vehicule(rs.getString("marque"), rs.getString("type"), rs.getString("carrosserie"),
								rs.getString("numeroSerie"), rs.getString("energie"), rs.getInt("puissance"),
								rs.getInt("klms"), rs.getDate("miseEnCircul"), rs.getString("immatriculation"),
								rs.getString("couleur"), rs.getString("destination"), rs.getDate("certifActuel"),
								rs.getDate("dateEntree"), rs.getDate("dateSortie"), rs.getInt("idvehicule"),
								rs.getInt("hascg")));
			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return stockTable;
	}

	public TableView exitFilterRequest(App app, StockPage stock) {

		TableView<vehicule> stockTable = new TableView<vehicule>();

		stockTable.setRowFactory(t -> new TableRow<vehicule>() {
			@Override
			public void updateItem(vehicule item, boolean empty) {
				super.updateItem(item, empty);

				if (item == null) {
					setStyle("");
				} else if (item.getHascg() == 1) {
					setStyle("-fx-background-color: #aef5bb;");

				} else if (getBurnt(item.getId())) {
					setStyle("-fx-background-color:#f593a3;");
				} else
					setStyle("");
				setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!isEmpty())) {
						vehicule rowData = getItem();

						System.out.println("Double click on: " + rowData.getMarque());
						app.showModifyVehiculeScene(rowData.getImmatriculation());
					}
				});
			}
		});

		TableColumn<vehicule, String> immatCollumn = new TableColumn<>("Immatriculation");
		immatCollumn.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
		TableColumn<vehicule, String> marqueCollumn = new TableColumn<>("Marque");
		marqueCollumn.setCellValueFactory(new PropertyValueFactory<>("marque"));
		TableColumn<vehicule, String> modeleCollumn = new TableColumn<>("Type");
		modeleCollumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		TableColumn<vehicule, Date> dateEntreeCollumn = new TableColumn<>("Date d'entrée");
		dateEntreeCollumn.setCellValueFactory(new PropertyValueFactory<>("dateEntree"));
		TableColumn<vehicule, String> couleurCollumn = new TableColumn<>("Couleur");
		couleurCollumn.setCellValueFactory(new PropertyValueFactory<>("couleur"));
		TableColumn<vehicule, String> destCollumn = new TableColumn<>("Destination");
		destCollumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
		TableColumn<vehicule, String> numSerCollumn = new TableColumn<>("Numéro de Série");
		numSerCollumn.setCellValueFactory(new PropertyValueFactory<>("numeroSerie"));

		TableColumn<vehicule, Integer> idCollumn = new TableColumn<>("ID ");
		idCollumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		stockTable.getColumns().add(idCollumn);
		stockTable.getColumns().add(immatCollumn);
		stockTable.getColumns().add(marqueCollumn);
		stockTable.getColumns().add(modeleCollumn);
		stockTable.getColumns().add(numSerCollumn);
		stockTable.getColumns().add(couleurCollumn);
		stockTable.getColumns().add(destCollumn);
		stockTable.getColumns().add(dateEntreeCollumn);

		TableColumn<vehicule, Date> dateSortieCollumn = new TableColumn<>("Date de Sortie");
		dateSortieCollumn.setCellValueFactory(new PropertyValueFactory<>("dateSortie"));
		stockTable.getColumns().add(dateSortieCollumn);

		TableViewSelectionModel<vehicule> selectionModel = stockTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		ObservableList<vehicule> selectedItems = selectionModel.getSelectedItems();

		selectedItems.addListener(new ListChangeListener<vehicule>() {
			@Override
			public void onChanged(Change<? extends vehicule> change) {
				vehiprev.prevVehicule(change.getList().get(0), stock);

			}
		});

		Date today = new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(today);
		cl.set(Calendar.DATE, 1);
		Date cldate = cl.getTime();
		java.sql.Date currentSqlDate = new java.sql.Date(cldate.getTime());

		String query = " SELECT * FROM geode_bd.vehicule WHERE dateSortie > '" + currentSqlDate
				+ "' AND dateSortie IS NOT NULL";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				stockTable.getItems()
						.add(new vehicule(rs.getString("marque"), rs.getString("type"), rs.getString("carrosserie"),
								rs.getString("numeroSerie"), rs.getString("energie"), rs.getInt("puissance"),
								rs.getInt("klms"), rs.getDate("miseEnCircul"), rs.getString("immatriculation"),
								rs.getString("couleur"), rs.getString("destination"), rs.getDate("certifActuel"),
								rs.getDate("dateEntree"), rs.getDate("dateSortie"), rs.getInt("idvehicule"),
								rs.getInt("hascg")));
			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return stockTable;
	}

	public TableView MonthsFilterRequest(App app, StockPage stock, int choice) {

		TableView<vehicule> stockTable = new TableView<vehicule>();
		stockTable.setRowFactory(t -> new TableRow<vehicule>() {
			@Override
			public void updateItem(vehicule item, boolean empty) {
				super.updateItem(item, empty);

				if (item == null) {
					setStyle("");
				} else if (item.getHascg() == 1) {
					setStyle("-fx-background-color:#aef5bb;");

				} else if (getBurnt(item.getId())) {
					setStyle("-fx-background-color:#f593a3;");
				} else
					setStyle("");
				setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!isEmpty())) {
						vehicule rowData = getItem();

						System.out.println("Double click on: " + rowData.getMarque());
						app.showModifyVehiculeScene(rowData.getImmatriculation());
					}
				});
			}
		});

		TableColumn<vehicule, String> immatCollumn = new TableColumn<>("Immatriculation");
		immatCollumn.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
		TableColumn<vehicule, String> marqueCollumn = new TableColumn<>("Marque");
		marqueCollumn.setCellValueFactory(new PropertyValueFactory<>("marque"));
		TableColumn<vehicule, String> modeleCollumn = new TableColumn<>("Type");
		modeleCollumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		TableColumn<vehicule, Date> dateEntreeCollumn = new TableColumn<>("Date d'entrée");
		dateEntreeCollumn.setCellValueFactory(new PropertyValueFactory<>("dateEntree"));
		TableColumn<vehicule, String> couleurCollumn = new TableColumn<>("Couleur");
		couleurCollumn.setCellValueFactory(new PropertyValueFactory<>("couleur"));
		TableColumn<vehicule, String> destCollumn = new TableColumn<>("Destination");
		destCollumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
		TableColumn<vehicule, String> numSerCollumn = new TableColumn<>("Numéro de Série");
		numSerCollumn.setCellValueFactory(new PropertyValueFactory<>("numeroSerie"));

		TableColumn<vehicule, Integer> idCollumn = new TableColumn<>("ID ");
		idCollumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		stockTable.getColumns().add(idCollumn);
		stockTable.getColumns().add(immatCollumn);
		stockTable.getColumns().add(marqueCollumn);
		stockTable.getColumns().add(modeleCollumn);
		stockTable.getColumns().add(numSerCollumn);
		stockTable.getColumns().add(couleurCollumn);
		stockTable.getColumns().add(destCollumn);
		stockTable.getColumns().add(dateEntreeCollumn);

		TableColumn<vehicule, Date> dateSortieCollumn = new TableColumn<>("Date de Sortie");
		dateSortieCollumn.setCellValueFactory(new PropertyValueFactory<>("dateSortie"));
		stockTable.getColumns().add(dateSortieCollumn);

		TableViewSelectionModel<vehicule> selectionModel = stockTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		ObservableList<vehicule> selectedItems = selectionModel.getSelectedItems();

		selectedItems.addListener(new ListChangeListener<vehicule>() {
			@Override
			public void onChanged(Change<? extends vehicule> change) {
				vehiprev.prevVehicule(change.getList().get(0), stock);

			}
		});

		Date today = new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(today);
		cl.set(Calendar.MONTH, cl.MONTH - 3);
		Date cldate = cl.getTime();
		java.sql.Date currentSqlDate = new java.sql.Date(cldate.getTime());

		String query = " SELECT * FROM geode_bd.vehicule WHERE dateEntree < '" + currentSqlDate
				+ "' AND dateSortie IS NULL";
		if (choice == 1)
			query = query + " AND hascg='0'";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				stockTable.getItems()
						.add(new vehicule(rs.getString("marque"), rs.getString("type"), rs.getString("carrosserie"),
								rs.getString("numeroSerie"), rs.getString("energie"), rs.getInt("puissance"),
								rs.getInt("klms"), rs.getDate("miseEnCircul"), rs.getString("immatriculation"),
								rs.getString("couleur"), rs.getString("destination"), rs.getDate("certifActuel"),
								rs.getDate("dateEntree"), rs.getDate("dateSortie"), rs.getInt("idvehicule"),
								rs.getInt("hascg")));
			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return stockTable;
	}

	public TableView invoiceListRequest(App app, StockPage stock) {

		TableView<vehicule> stockTable = new TableView<vehicule>();
		stockTable.setRowFactory(t -> new TableRow<vehicule>() {
			@Override
			public void updateItem(vehicule item, boolean empty) {
				super.updateItem(item, empty);

				if (item == null) {
					setStyle("");
				} else if (item.getHascg() == 1) {
					setStyle("-fx-background-color:#aef5bb;");

				} else if (getBurnt(item.getId())) {
					setStyle("-fx-background-color:#f593a3;");
				} else
					setStyle("");

			}
		});

		TableColumn<vehicule, String> immatCollumn = new TableColumn<>("Immatriculation");
		immatCollumn.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
		TableColumn<vehicule, String> marqueCollumn = new TableColumn<>("Marque");
		marqueCollumn.setCellValueFactory(new PropertyValueFactory<>("marque"));
		TableColumn<vehicule, String> modeleCollumn = new TableColumn<>("Type");
		modeleCollumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		TableColumn<vehicule, Date> dateEntreeCollumn = new TableColumn<>("Date d'entrée");
		dateEntreeCollumn.setCellValueFactory(new PropertyValueFactory<>("dateEntree"));
		TableColumn<vehicule, String> couleurCollumn = new TableColumn<>("Couleur");
		couleurCollumn.setCellValueFactory(new PropertyValueFactory<>("couleur"));
		TableColumn<vehicule, String> destCollumn = new TableColumn<>("Destination");
		destCollumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
		TableColumn<vehicule, String> numSerCollumn = new TableColumn<>("Numéro de Série");
		numSerCollumn.setCellValueFactory(new PropertyValueFactory<>("numeroSerie"));

		TableColumn<vehicule, Integer> idCollumn = new TableColumn<>("ID ");
		idCollumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		stockTable.getColumns().add(idCollumn);
		stockTable.getColumns().add(immatCollumn);
		stockTable.getColumns().add(marqueCollumn);
		stockTable.getColumns().add(modeleCollumn);
		stockTable.getColumns().add(numSerCollumn);
		stockTable.getColumns().add(couleurCollumn);
		stockTable.getColumns().add(destCollumn);
		stockTable.getColumns().add(dateEntreeCollumn);

		TableColumn<vehicule, Date> dateSortieCollumn = new TableColumn<>("Date de Sortie");
		dateSortieCollumn.setCellValueFactory(new PropertyValueFactory<>("dateSortie"));
		stockTable.getColumns().add(dateSortieCollumn);

		TableViewSelectionModel<vehicule> selectionModel = stockTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

		ObservableList<vehicule> selectedItems = selectionModel.getSelectedItems();

		selectedItems.addListener(new ListChangeListener<vehicule>() {
			@Override
			public void onChanged(Change<? extends vehicule> change) {
				for (int i = 0; i < selectedItems.size(); i++)
					stock.multiSelect = selectedItems;
				// System.out.println(stockTable.getSelectionModel().getSourceIndex());

			}
		});

		Date today = new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(today);
		cl.set(Calendar.MONTH, cl.MONTH - 3);
		Date cldate = cl.getTime();
		java.sql.Date currentSqlDate = new java.sql.Date(cldate.getTime());

		String query = " SELECT * FROM geode_bd.vehicule WHERE  dateSortie IS NULL";

		query = query + " AND hascg='1'";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				stockTable.getItems()
						.add(new vehicule(rs.getString("marque"), rs.getString("type"), rs.getString("carrosserie"),
								rs.getString("numeroSerie"), rs.getString("energie"), rs.getInt("puissance"),
								rs.getInt("klms"), rs.getDate("miseEnCircul"), rs.getString("immatriculation"),
								rs.getString("couleur"), rs.getString("destination"), rs.getDate("certifActuel"),
								rs.getDate("dateEntree"), rs.getDate("dateSortie"), rs.getInt("idvehicule"),
								rs.getInt("hascg")));

			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return stockTable;
	}

	public int retrieveIdWithImmat(String immat) {
		String query = " SELECT * FROM geode_bd.vehicule WHERE immatriculation = '" + immat + "'";
		ResultSet rs;
		int id = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			id = rs.getInt("idvehicule");

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		return id;
	}

	public vehicule retrieveVehiWithId(int id) {
		vehicule vehi = new vehicule();
		String query = " SELECT * FROM geode_bd.vehicule WHERE idvehicule = '" + id + "'";
		ResultSet rs;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			vehi = new vehicule(rs.getString("marque"), rs.getString("type"), rs.getString("carrosserie"),
					rs.getString("numeroSerie"), rs.getString("energie"), rs.getInt("puissance"), rs.getInt("klms"),
					rs.getDate("miseEnCircul"), rs.getString("immatriculation"), rs.getString("couleur"),
					rs.getString("destination"), rs.getDate("certifActuel"), rs.getDate("dateEntree"),
					rs.getDate("dateSortie"), rs.getInt("idvehicule"), rs.getInt("hascg"));

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		return vehi;
	}

	public String retrievePriceWithId(int id) {
		String price = "";
		vehicule vehi = new vehicule();
		String query = " SELECT * FROM geode_bd.vehicule WHERE idvehicule = '" + id + "'";
		ResultSet rs;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			price = String.valueOf(rs.getFloat("proposedprice"));

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		return price;
	}

	public boolean getBurnt(int id) {
		boolean price = false;
		vehicule vehi = new vehicule();
		String query = " SELECT * FROM geode_bd.paymentvehi WHERE idvehicule = '" + id + "' AND paymentcg ='1'";
		ResultSet rs;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next())
				price = true;

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		return price;
	}

	public ObservableList<vehicule> getnewMulti(ObservableList<vehicule> multi, int id) {

		vehicule vehi = new vehicule();
		String query = " SELECT * FROM geode_bd.vehicule WHERE idvehicule = '" + id + "'";
		ResultSet rs;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			vehi = new vehicule(rs.getString("marque"), rs.getString("type"), rs.getString("carrosserie"),
					rs.getString("numeroSerie"), rs.getString("energie"), rs.getInt("puissance"), rs.getInt("klms"),
					rs.getDate("miseEnCircul"), rs.getString("immatriculation"), rs.getString("couleur"),
					rs.getString("destination"), rs.getDate("certifActuel"), rs.getDate("dateEntree"),
					rs.getDate("dateSortie"), rs.getInt("idvehicule"), rs.getInt("hascg"));

			multi.add(vehi);
		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return multi;
	}

}
