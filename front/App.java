package front;

import java.awt.Dimension;
import java.time.LocalDate;

import back.DataTaker;
import back.Invoice;
import back.InvoiceTaker;
import back.User;
import back.dataInput;
import back.vehicule;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {
	private Stage stage;
	DataTaker datataker = new DataTaker();
	static dataInput di = new dataInput();
	InvoiceTaker invoicetaker = new InvoiceTaker();
	private User user;

	public static void main(String[] args) {

		

		launch(args);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		//di.Startup();
		showLoginScene();
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth() / 1.02;
		double height = screenSize.getHeight() / 1.02;
		stage.setHeight(height);
		stage.setWidth(width);

		try {

			stage.setScene(new Scene(new AnchorPane(), height, width));
			stage.show();
			showLoginScene();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showInvoiceTableScene() {
		AllInvoiceTable InvoiceTable = new AllInvoiceTable(this, user);
		Scene InvoiceTableScene = InvoiceTable.createInvoiceStockTables(this);
		stage.setScene(InvoiceTableScene);
		stage.setTitle("Liste des Factures");
		stage.centerOnScreen();
		stage.show();
	}

	public void createAnalitycsPage() {
		Analitycs al = new Analitycs(this, user);
		Scene alscene = al.createAnalitycsPage();
		stage.setScene(alscene);
		stage.setTitle("Statistiques");
		stage.centerOnScreen();
		stage.show();
	}

	public void showInvoiceTableScene(String num, String client, String dest, LocalDate startDate, LocalDate endDate) {
		AllInvoiceTable InvoiceTable = new AllInvoiceTable(this, user);

		InvoiceTable.stockTable = invoicetaker.getFilteredRequest(this, num, client, dest, startDate, endDate,
				InvoiceTable);
		Scene InvoiceTableScene = InvoiceTable.createInvoiceStockTables(this);
		stage.setScene(InvoiceTableScene);
		stage.setTitle("Liste des Factures");
		stage.centerOnScreen();
		stage.show();
	}

	// show form to add new invoice
	public void showInvoiceScene(boolean verifpage, boolean avoir, int id) {
		InvoiceForm InvoiceForm = new InvoiceForm(this, user);
		Scene InvoiceScene = InvoiceForm.createInvoicePage(verifpage, avoir, id);
		stage.setScene(InvoiceScene);
		stage.setTitle("Nouvelle Facture");
		stage.centerOnScreen();
		stage.show();
	}

	// Show account or payment
	public void showPaymentScene(Invoice invoice) {
		paymentoracc paymentoracc = new paymentoracc(this, user);
		Scene InvoicePayment = paymentoracc.createPayOrAcc(this, invoice);
		stage.setScene(InvoicePayment);
		stage.setTitle("Ajouter un paiement");
		stage.centerOnScreen();
		stage.show();
	}

	public void showCalendarScene() {
		CalendarApp cal = new CalendarApp(this, user);
		Scene calscen = cal.createCalendar();
		stage.setScene(calscen);
		stage.setTitle("Calendrier");
		stage.centerOnScreen();
		stage.show();
	}

	public void showCalendarRappelScene() {
		CalendarApp cal = new CalendarApp(this, user);
		cal.validevent = 1;
		Scene calscen = cal.createCalendar();
		stage.setScene(calscen);
		stage.setTitle("Calendrier");
		stage.centerOnScreen();
		stage.show();
	}

	// show form to add new vehicule
	public void showVehiculeScene(boolean verifpage, String immatch) {
		NewVehicule newvehi = new NewVehicule(this, user);

		Scene newVehiScene = newvehi.createVehiculeInput(immatch, verifpage);
		stage.setScene(newVehiScene);
		stage.setTitle("Ajout de véhicule");
		stage.centerOnScreen();
		stage.show();
	}

	// Show all vehicule page + filtered vehicule
	public void showAllVehiculeScene() {
		StockPage stockpage = new StockPage(this, user);
		Scene stockpageScene = stockpage.createStockTables(0);
		stage.setScene(stockpageScene);
		stage.setTitle("Liste de tout les véhicules");
		stage.centerOnScreen();
		stage.show();
	}

	public void showAllVehiculeScene(String marque, String type, String immat, String numSer, String couleur,
			String dest, String id, String hascg, boolean brulee) {
		StockPage stockpage = new StockPage(this, user);

		stockpage.stockTable = datataker.getFilteredRequest(this, marque, type, immat, numSer, couleur, dest, id, true,
				stockpage, hascg, brulee);
		Scene stockpageScene = stockpage.createStockTables(0);
		stage.setScene(stockpageScene);
		stage.setTitle("Liste de tout les véhicules");
		stage.centerOnScreen();
		stage.show();
	}

	public void showCardVehiculeScene(int choice) {
		StockPage stockpage = new StockPage(this, user);
		switch (choice) {
		case (0):
			stockpage.stockTable = datataker.exitFilterRequest(this, stockpage);
			break;
		case (1):
			stockpage.stockTable = datataker.MonthsFilterRequest(this, stockpage, 0);
			break;
		case (2):
			stockpage.stockTable = datataker.entryFilterRequest(this, stockpage);
			break;
		case (3):
			stockpage.stockTable = datataker.MonthsFilterRequest(this, stockpage, 1);
			break;
		}

		Scene stockpageScene = stockpage.createStockTables(0);
		stage.setScene(stockpageScene);
		stage.setTitle("Liste de tout les véhicules");
		stage.centerOnScreen();
		stage.show();
	}

	// Show page to modify vehicule data
	public void showModifyVehiculeScene(String immat) {
		showVehiculeScene(true, immat);
	}

	public void showHomeScene(User user) {
		this.user = user;
		HomePage homepage = new HomePage(this, user);
		Scene homepageScene = homepage.createHomePage();
		stage.setScene(homepageScene);
		stage.setTitle("Accueil");
		stage.centerOnScreen();

		stage.show();
	}

	public void showLoginScene() {
		LoginPage homepage = new LoginPage(this);
		Scene homepageScene = homepage.createLoginPage();
		stage.setScene(homepageScene);
		stage.setTitle("Connexion");
		stage.centerOnScreen();

		stage.show();
	}

	// Show vehicule STOCK
	public void showStockScene() {
		StockPage stockpage = new StockPage(this, user);
		Scene stockpageScene = stockpage.createStockTables(1);
		stage.setScene(stockpageScene);
		stage.setTitle("Stock");
		stage.centerOnScreen();
		stage.show();
	}

	public void showStockScene(String marque, String type, String immat, String numSer, String couleur, String dest,
			String id, String hascg, boolean brulee) {
		StockPage stockpage = new StockPage(this, user);

		stockpage.stockTable = datataker.getFilteredRequest(this, marque, type, immat, numSer, couleur, dest, id, false,
				stockpage, hascg, brulee);

		Scene stockpageScene = stockpage.createStockTables(1);
		stage.setScene(stockpageScene);
		stage.setTitle("Stock");
		stage.centerOnScreen();
		stage.show();
	}

	public void invoiceListScene() {
		StockPage stockpage = new StockPage(this, user);

		stockpage.stockTable = datataker.invoiceListRequest(this, stockpage);
		stockpage.tempid = 0;
		Scene stockpageScene = stockpage.createStockTables(2);
		stage.setScene(stockpageScene);
		stage.setTitle("Stock");
		stage.centerOnScreen();
		stage.show();
	}

	public void invoiceListScene(ObservableList<vehicule> multiselect, int id) {
		StockPage stockpage = new StockPage(this, user);

		stockpage.stockTable = datataker.invoiceListRequest(this, stockpage);
		stockpage.tempid = id;
		stockpage.multiSelect = multiselect;
		Scene stockpageScene = stockpage.createStockTables(2);
		stage.setScene(stockpageScene);
		stage.setTitle("Stock");
		stage.centerOnScreen();
		stage.show();
	}

	public void showInvoiceScene(ObservableList<vehicule> multiselect) {
		InvoiceForm InvoiceForm = new InvoiceForm(this, user);
		InvoiceForm.multiSelect = multiselect;
		Scene InvoiceScene = InvoiceForm.createInvoicePage(true, false, 0);
		stage.setScene(InvoiceScene);
		stage.setTitle("Nouvelle Facture");
		stage.centerOnScreen();
		stage.show();
	}

	public void showInvoiceScene(ObservableList<vehicule> multiselect, int id) {
		InvoiceForm InvoiceForm = new InvoiceForm(this, user);
		InvoiceForm.multiSelect = multiselect;
		Scene InvoiceScene = InvoiceForm.createInvoicePage(false, false, id);
		stage.setScene(InvoiceScene);
		stage.setTitle("Nouvelle Facture");
		stage.centerOnScreen();
		stage.show();
	}

	public void showParamScene() {
		ParametersScene paramForm = new ParametersScene(this, user);

		Scene paramScene = paramForm.createParameterScene();
		stage.setScene(paramScene);
		stage.setTitle("Paramètres");
		stage.centerOnScreen();
		stage.show();
	}

}
