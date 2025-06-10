package back;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import front.AllInvoiceTable;
import front.App;
import front.paymentoracc;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;

public class InvoiceTaker {

	String BDD = "geode_bd";
	String url = "jdbc:mysql://192.168.1.85:3306/" + BDD;
	String user = "username";
	String passwd = "password";
	InvoicePrev invoiceprev = new InvoicePrev();
	String status;

	public int openInvoiceTaker() {

		int result = 0;

		String query = " SELECT * FROM geode_bd.invoice WHERE invoiceStatus = '0'";

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

	public TableView getAllInvoice(App app, AllInvoiceTable invtb) {
		TableView<Invoice> stockTable = new TableView<Invoice>();

		stockTable.setRowFactory(tv -> {
			TableRow<Invoice> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Invoice rowData = row.getItem();

					app.showInvoiceScene(false, false, rowData.getInvoiceid());
				}
			});
			return row;
		});

		TableColumn<Invoice, String> immatCollumn = new TableColumn<>("Numéro de Facture");
		immatCollumn.setCellValueFactory(new PropertyValueFactory<>("invoiceNum"));
		TableColumn<Invoice, String> marqueCollumn = new TableColumn<>("Client");
		marqueCollumn.setCellValueFactory(new PropertyValueFactory<>("invoiceClient"));
		TableColumn<Invoice, String> modeleCollumn = new TableColumn<>("Status");
		modeleCollumn.setCellValueFactory(new PropertyValueFactory<>("invoiceStatus"));
		TableColumn<Invoice, String> totalttcCollumn = new TableColumn<>("Total TTC");
		totalttcCollumn.setCellValueFactory(new PropertyValueFactory<>("totalttc"));

		TableColumn<Invoice, Date> dateCollumn = new TableColumn<>("Date");
		dateCollumn.setCellValueFactory(new PropertyValueFactory<>("invoicedatecreation"));

		stockTable.getColumns().add(immatCollumn);
		stockTable.getColumns().add(marqueCollumn);
		stockTable.getColumns().add(modeleCollumn);
		stockTable.getColumns().add(totalttcCollumn);
		stockTable.getColumns().add(dateCollumn);
		TableViewSelectionModel<Invoice> selectionModel = stockTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);

		ObservableList<Invoice> selectedItems = selectionModel.getSelectedItems();
		selectedItems.addListener(new ListChangeListener<Invoice>() {
			@Override
			public void onChanged(Change<? extends Invoice> change) {
				invoiceprev.prevInvoice(change.getList().get(0), invtb, app);
			}
		});

		String query = " SELECT * FROM geode_bd.invoice";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {

				switch (rs.getInt("invoiceStatus")) {
				case 0:
					status = "Temporaire";
					break;
				case 1:
					status = "Définitive";
					break;
				case 2:
					status = "Avoir";
					break;
				case 3:
					status = "Payée";
					break;
				}

				stockTable.getItems()
						.add(new Invoice((rs.getString("invoicenumber") + String.valueOf(rs.getInt("invoicevehicule"))),
								rs.getInt("idinvoice"), rs.getString("invoiceClient"), rs.getString("invoiceAdress"),
								rs.getInt("invoicecp"), rs.getString("invoiceCity"), rs.getString("invoicegar"), status,
								rs.getInt("invoicevehicule"), rs.getString("invoicePayType"),
								rs.getString("invoiceText"), rs.getString("invoiceClientType"),
								rs.getDate("invoicedate"), rs.getFloat("invoiceTotalTTC"), rs.getString("invoicelistvehi"), rs.getString("invoicecountry"), rs.getString("invoicetva")));
			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return stockTable;
	}

	public TableView<Invoice> getFilteredRequest(App app, String invoicenumber, String invoiceClient, String dest,
			LocalDate startDate, LocalDate endDate, AllInvoiceTable invtb) {

		TableView<Invoice> stockTable = new TableView<Invoice>();
		String status;

		stockTable.setRowFactory(tv -> {
			TableRow<Invoice> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Invoice rowData = row.getItem();

					app.showInvoiceScene(false, false, rowData.getInvoiceid());
				}
			});
			return row;
		});

		TableColumn<Invoice, String> immatCollumn = new TableColumn<>("Numéro de Facture");
		immatCollumn.setCellValueFactory(new PropertyValueFactory<>("invoiceNum"));
		TableColumn<Invoice, String> marqueCollumn = new TableColumn<>("Client");
		marqueCollumn.setCellValueFactory(new PropertyValueFactory<>("invoiceClient"));
		TableColumn<Invoice, String> modeleCollumn = new TableColumn<>("Status");
		modeleCollumn.setCellValueFactory(new PropertyValueFactory<>("invoiceStatus"));
		TableColumn<Invoice, String> totalttcCollumn = new TableColumn<>("Total TTC");
		totalttcCollumn.setCellValueFactory(new PropertyValueFactory<>("totalttc"));
		TableColumn<Invoice, Date> dateCollumn = new TableColumn<>("Date");
		dateCollumn.setCellValueFactory(new PropertyValueFactory<>("invoicedatecreation"));

		stockTable.getColumns().add(immatCollumn);
		stockTable.getColumns().add(marqueCollumn);
		stockTable.getColumns().add(modeleCollumn);
		stockTable.getColumns().add(totalttcCollumn);
		stockTable.getColumns().add(dateCollumn);
		TableViewSelectionModel<Invoice> selectionModel = stockTable.getSelectionModel();
		selectionModel.setSelectionMode(SelectionMode.SINGLE);

		ObservableList<Invoice> selectedItems = selectionModel.getSelectedItems();
		selectedItems.addListener(new ListChangeListener<Invoice>() {
			@Override
			public void onChanged(Change<? extends Invoice> change) {
				invoiceprev.prevInvoice(change.getList().get(0), invtb, app);
			}
		});

		// Creation query

		String query = " SELECT * FROM geode_bd.invoice WHERE idinvoice IS NOT NULL ";
		if (invoiceClient != "") {
			query = query + "AND invoiceClient LIKE '%" + invoiceClient + "%'";
		}
		if (invoicenumber != "") {
			query = query + "AND invoicenumber LIKE '%" + invoicenumber + "%'";
		}

		if (dest == "Temporaire") {
			query = query + "AND invoiceStatus = '" + 0 + "'";
		}
		if (dest == "Définitive") {
			query = query + "AND invoiceStatus = '" + 1 + "'";
		}
		if (dest == "Avoir") {
			query = query + "AND invoiceStatus = '" + 2 + "'";
		}
		if (dest == "Payée") {
			query = query + "AND invoiceStatus = '" + 3 + "'";
		}
		if (startDate != null) {
			java.sql.Date start = java.sql.Date.valueOf(startDate);
			query = query + "AND invoicedate >= '" + start + "'";

		}
		if (endDate != null) {
			java.sql.Date end = java.sql.Date.valueOf(endDate);
			query = query + "AND invoicedate <= '" + end + "'";

		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {

				if (rs.getInt("invoiceStatus") == 0)
					status = "Temporaire";
				else if (rs.getInt("invoiceStatus") == 1)
					status = "Definitive";
				else if (rs.getInt("invoiceStatus") == 2)
					status = "Avoir";
				else
					status = "Payée";
				stockTable.getItems()
						.add(new Invoice((rs.getString("invoicenumber") + String.valueOf(rs.getInt("invoicevehicule"))),
								rs.getInt("idinvoice"), rs.getString("invoiceClient"), rs.getString("invoiceAdress"),
								rs.getInt("invoicecp"), rs.getString("invoiceCity"), rs.getString("invoicegar"), status,
								rs.getInt("invoicevehicule"), rs.getString("invoicePayType"),
								rs.getString("invoiceText"), rs.getString("invoiceClientType"),
								rs.getDate("invoicedate"), rs.getFloat("invoiceTotalTTC"), rs.getString("invoicelistvehi"),rs.getString("invoicecountry"),  rs.getString("invoicetva")));
			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		return stockTable;
	}

	public Invoice getSpecifiedInvoice(int idinvoice) {
		String status;
		Invoice invoice = null;
		String query = " SELECT * FROM geode_bd.invoice WHERE idinvoice ='" + idinvoice + "'";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {

				if (rs.getInt("invoiceStatus") == 0)
					status = "Temporaire";
				else if (rs.getInt("invoiceStatus") == 1)
					status = "Definitive";
				else if (rs.getInt("invoiceStatus") == 2)
					status = "Avoir";
				else
					status = "Payée";
				invoice = new Invoice(rs.getString("invoicenumber") + String.valueOf(rs.getInt("invoicevehicule")),
						rs.getInt("idinvoice"), rs.getString("invoiceClient"), rs.getString("invoiceAdress"),
						rs.getInt("invoicecp"), rs.getString("invoiceCity"), rs.getString("invoicegar"), status,
						rs.getInt("invoicevehicule"), rs.getString("invoicePayType"),
						rs.getString("invoiceText"), rs.getString("invoiceClientType"),
						rs.getDate("invoicedate"), rs.getFloat("invoiceTotalTTC"), rs.getString("invoicelistvehi"),rs.getString("invoicecountry"), rs.getString("invoicetva"));
			}
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		return invoice;
	}

	public List<String[]> getInvoicelinetotal(int idinvoice) {
		List<String[]> odrerlinesinvoice = new ArrayList<>();

		String query = " SELECT * FROM geode_bd.invoicelines WHERE invoiceId ='" + idinvoice + "'";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
		//	System.out.println(rs.getFetchSize());
			int i = 0;
			while (rs.next()) {
	
				i++;

			}
			
			
			String[] code = new String[i];
			String[] lib = new String[i];
			String[] base = new String[i];
			String[] qte = new String[i];
			String[] puht = new String[i];
			
			rs = stmt.executeQuery(query);
			i=0;
			while (rs.next()) {
				code[i] = rs.getString("invoicelinescode");
				lib[i] = rs.getString("invoicelinesLib");
				base[i] = rs.getString("invoicelinesBase");
				qte[i] = String.valueOf(rs.getInt("invoicelinesqte"));
				puht[i] = rs.getFloat("invoicelinespuht") + "";
				i++;
			}

		//	rs.
			
			odrerlinesinvoice.add(code);
			odrerlinesinvoice.add(lib);
			odrerlinesinvoice.add(base);
			odrerlinesinvoice.add(qte);
			odrerlinesinvoice.add(puht);

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}
		return odrerlinesinvoice;
	}

	public void acctaker(int id, paymentoracc payacc) {

		List<String> pay = new ArrayList<>();
		List<String> method = new ArrayList<>();
		List<LocalDate> date = new ArrayList<>();
		String query = " SELECT * FROM geode_bd.paynadv WHERE invoiceidtopay ='" + id + "'";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {

				pay.add(String.valueOf(rs.getFloat("payamount")));
				method.add(rs.getString("paytype"));
				date.add(rs.getDate("paydate").toLocalDate());

			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		payacc.amt = pay;
		payacc.mtd = method;
		payacc.datepaym = date;

	}

	public void prevtaker(int id, InvoicePrev inprev) {

		List<String> pay = new ArrayList<>();
		List<String> act = new ArrayList<>();
		List<Integer> qte = new ArrayList<>();
	
		String query1 = "SELECT * FROM geode_bd.invoicelines WHERE invoiceId ='" + id + "'";
		String query = " SELECT * FROM geode_bd.paynadv WHERE invoiceidtopay ='" + id + "'";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query1);

			while (rs.next()) {

				pay.add(String.valueOf(rs.getFloat("invoicelinesTotal")));
				act.add(rs.getString("invoicelinesLib"));
				qte.add(rs.getInt("invoicelinesqte"));

			}
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				pay.add(String.valueOf(-rs.getFloat("payamount")));
				act.add(rs.getString("paytype"));
			}
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		inprev.price = pay;
		inprev.act = act;
		inprev.listqte = qte;

	}

}
