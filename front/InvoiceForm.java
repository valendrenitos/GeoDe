package front;

import java.awt.Dimension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import java.util.List;
import java.util.function.UnaryOperator;

import back.CalendarController;
import back.DataTaker;
import back.GenerateInvoiceNumber;
import back.Invoice;
import back.InvoiceInput;
import back.InvoiceTaker;
import back.User;
import back.vehicule;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.*;

public class InvoiceForm extends BorderPane {
	private static final DecimalFormat df = new DecimalFormat("0.00");
	private App app;
	private int idvehicule;
	private String invoicenumgen;
	private User utilisateur;
	public ObservableList<TextField> txtlabel;
	public ObservableList<TextField> txtqte;
	public ObservableList<TextField> txttotal;
	public ObservableList<TextField> txtpuht;
	public TextField totalhtField;
	public VBox totalvbox;
	public VBox puhtvbox;
	public VBox qtevbox;
	public VBox labelvbox;
	public ObservableList<vehicule> multiSelect;

	public InvoiceForm(App app, User utilisateur) {
		this.app = app;
		this.utilisateur = utilisateur;
	}

	public Scene createInvoicePage(boolean verifpage, boolean avoir, int id) {
		txtlabel = FXCollections.observableArrayList();
		txtqte = FXCollections.observableArrayList();
		txttotal = FXCollections.observableArrayList();
		txtpuht = FXCollections.observableArrayList();
		DataTaker datataker = new DataTaker();
		InvoiceTaker invoicetaker = new InvoiceTaker();
		InvoiceInput invoiceinput = new InvoiceInput();
		GenerateInvoiceNumber geninv = new GenerateInvoiceNumber();

		Label Title = new Label();

		if (verifpage)
			Title.setText("Créer une facture");
		else if (!verifpage && !avoir) {
			Title.setText("Modifier une facture");
			Invoice invoice = invoicetaker.getSpecifiedInvoice(id);
			if (invoice.getListvehi() != null && multiSelect == null) {
				multiSelect = FXCollections.observableArrayList();
				ObservableList<Integer> listids = getIds(invoice.getListvehi());
				for (int i = 0; i < listids.size(); i++) {
					multiSelect = datataker.getnewMulti(multiSelect, listids.get(i));
				}
			}
		}

		else
			Title.setText("Générer un avoir");
		Title.getStylesheets().add("/styles.css");
		Title.getStyleClass().add("Title");

		TopNavBar topnav = new TopNavBar(app, "InvoicePage", utilisateur);
		ParamButton pambut = new ParamButton(app);
		this.setBottom(pambut);

		this.setTop(topnav);
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth() / 2;
		double height = 3 * screenSize.getHeight() / 4;
		Separator separator = new Separator(Orientation.HORIZONTAL);
		Separator separator2 = new Separator(Orientation.HORIZONTAL);
		// Filter

		UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {

			@Override
			public TextFormatter.Change apply(TextFormatter.Change t) {

				if (t.isReplaced())
					if (t.getText().matches("[^0-9]"))
						t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

				if (t.isAdded()) {
					if (t.getControlText().contains(".")) {
						if (t.getText().matches("[^0-9]")) {
							t.setText("");
						}
					} else if (t.getText().matches("[^0-9.]")) {
						t.setText("");
					}
				}

				return t;
			}
		};

		//

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setAlignment(Pos.CENTER);
		grid.add(Title, 1, 0);
		VBox generalVbox = new VBox();
		generalVbox.setPadding(new Insets(40));
		generalVbox.setSpacing(40);
		Text numFact = new Text();
		HBox topHbox = new HBox();
		topHbox.setSpacing(20);
		topHbox.setPadding(new Insets(20));

		VBox infoclient = new VBox();
		infoclient.setStyle(
				"-fx-background-color: #e6dfdf; " + "-fx-border-radius : 15px;" + "-fx-background-radius : 15px;");
		infoclient.setSpacing(20);
		infoclient.setPadding(new Insets(20));
		HBox clientname = new HBox();
		clientname.setSpacing(20);
		// Type Client
		VBox typeclientBox = new VBox();
		Label typeclientLabel = new Label("Denomination:");
		typeclientLabel.setFont(Font.font("Arial", 14));
		typeclientLabel.setStyle("-fx-text-fill: #000000");
		ChoiceBox<String> typeclientchoiceBox = new ChoiceBox<String>();
		typeclientchoiceBox.getItems().add("Monsieur");
		typeclientchoiceBox.getItems().add("Madame");
		typeclientchoiceBox.getItems().add("EI");
		typeclientchoiceBox.getItems().add("EURL");
		typeclientchoiceBox.getItems().add("SARL");
		typeclientchoiceBox.getItems().add("SA");
		typeclientchoiceBox.getItems().add("SAS");
		typeclientchoiceBox.getItems().add("SASU");
		typeclientchoiceBox.getItems().add("SCI");
		typeclientchoiceBox.getItems().add("SNC");
		typeclientchoiceBox.getItems().add("Scop");
		typeclientchoiceBox.getItems().add("Association");
		typeclientchoiceBox.getItems().add("Société");
		typeclientchoiceBox.setValue("Monsieur");
		typeclientBox.getChildren().addAll(typeclientLabel, typeclientchoiceBox);

		// Nom du Client
		VBox clientBox = new VBox();
		Label clientLabel = new Label("Client :");
		clientLabel.setFont(Font.font("Arial", 14));
		clientLabel.setStyle("-fx-text-fill: #000000");
		TextField clientField = new TextField();
		clientField.setPromptText("Nom du Client");
		// clientField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		clientBox.getChildren().addAll(clientLabel, clientField);

		clientname.getChildren().addAll(typeclientBox, clientBox);

		// Addresse
		VBox addresseBox = new VBox();
		Label addresseLabel = new Label("Adresse:");
		addresseLabel.setFont(Font.font("Arial", 14));
		addresseLabel.setStyle("-fx-text-fill: #000000");
		TextField addresseField = new TextField();
		addresseField.setPromptText("Adresse");
		// addresseField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		addresseBox.getChildren().addAll(addresseLabel, addresseField);
		addresseField.setMinWidth(width / 3.4);
		HBox cityname = new HBox();
		clientname.setSpacing(20);

		// Code Postal
		VBox cpBox = new VBox();
		Label cpLabel = new Label("Code Postal :");
		cpLabel.setFont(Font.font("Arial", 14));
		cpLabel.setStyle("-fx-text-fill: #000000");
		TextField cpField = new TextField();
		cpField.setPromptText("Code Postal");
		cpField.setStyle("-fx-max-width: 120px;");
		cpBox.getChildren().addAll(cpLabel, cpField);

		// Ville
		VBox cityBox = new VBox();
		Label cityLabel = new Label("Ville:");
		cityLabel.setFont(Font.font("Arial", 14));
		cityLabel.setStyle("-fx-text-fill: #000000");
		TextField cityField = new TextField();
		cityField.setPromptText("Ville");
		// cityField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		cityBox.getChildren().addAll(cityLabel, cityField);

		cityname.getChildren().addAll(cpBox, cityBox);
		// Ville
		VBox PaysBox = new VBox();
		Label PaysLabel = new Label("Pays:");
		PaysLabel.setFont(Font.font("Arial", 14));
		PaysLabel.setStyle("-fx-text-fill: #000000");
		TextField PaysField = new TextField();
		PaysField.setPromptText("Pays");
		PaysField.setText("France");
		VBox tvaBox = new VBox();
		Label tvaLabel = new Label("TVA :");
		PaysLabel.setFont(Font.font("Arial", 14));
		PaysLabel.setStyle("-fx-text-fill: #000000");
		TextField tvaField = new TextField();
		PaysField.setPromptText("TVA");
		tvaBox.getChildren().addAll(tvaLabel, tvaField);
		PaysField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (!PaysField.getText().equals("France")) {

					if (infoclient.getChildren().size() == 4) {

						infoclient.getChildren().addAll(tvaBox);
					}

				}

			}
		});
		PaysBox.getChildren().addAll(PaysLabel, PaysField);

		infoclient.getChildren().addAll(clientname, addresseBox, cityname, PaysBox);

		// texte Facture
		VBox textfaBox = new VBox();
		Label textfaLabel = new Label("Texte facture :");
		textfaLabel.setFont(Font.font("Arial", 14));
		textfaLabel.setStyle("-fx-text-fill: #000000");
		TextField textfaField = new TextField();
		textfaField.setPromptText("Texte Facture");

		textfaBox.getChildren().addAll(textfaLabel, textfaField);
		textfaField.setMinWidth(width / 1.2);

		// Garage
		VBox garBox = new VBox();
		Label garLabel = new Label("Garage :");
		garLabel.setFont(Font.font("Arial", 14));
		garLabel.setStyle("-fx-text-fill: #000000");
		TextField garField = new TextField();
		garField.setPromptText("Garage");

		garBox.getChildren().addAll(garLabel, garField);

		// Type Facture
		VBox typefaBox = new VBox();
		Label typefaLabel = new Label("Type de Facture:");
		typefaLabel.setFont(Font.font("Arial", 14));
		typefaLabel.setStyle("-fx-text-fill: #000000");
		ChoiceBox<String> typefachoiceBox = new ChoiceBox<String>();
		typefachoiceBox.getItems().add("Temporaire");
		typefachoiceBox.getItems().add("Definitive");
		typefachoiceBox.setValue("Temporaire");
		typefaBox.getChildren().addAll(typefaLabel, typefachoiceBox);

		// type du véhicule
		VBox vehitypeBox = new VBox();
		Label vehitypeLabel = new Label("Marque :");
		vehitypeLabel.setFont(Font.font("Arial", 14));
		vehitypeLabel.setStyle("-fx-text-fill: #000000");
		TextField vehitypeField = new TextField();
		vehitypeField.setPromptText("Marque");

		vehitypeField.setEditable(false);
		vehitypeBox.getChildren().addAll(vehitypeLabel, vehitypeField);

		// Immatriculation
		VBox immatBox = new VBox();
		Label immatLabel = new Label("Immatriculation :");
		immatLabel.setFont(Font.font("Arial", 14));
		immatLabel.setStyle("-fx-text-fill: #000000");
		TextField immatField = new TextField();
		immatField.setPromptText("Immatriculation");
		immatBox.getChildren().addAll(immatLabel, immatField);

		// Destination
		VBox DestBox = new VBox();
		Label DestLabel = new Label("Destination :");
		DestLabel.setFont(Font.font("Arial", 14));
		DestLabel.setStyle("-fx-text-fill: #000000");
		TextField destchoiceBox = new TextField();
		destchoiceBox.setEditable(false);
		DestBox.getChildren().addAll(DestLabel, destchoiceBox);

		// Paiment
		VBox payBox = new VBox();
		Label payLabel = new Label("Paiement :");
		payLabel.setFont(Font.font("Arial", 14));
		payLabel.setStyle("-fx-text-fill: #000000");
		ChoiceBox<String> paychoiceBox = new ChoiceBox<String>();
		paychoiceBox.getItems().add("CB");
		paychoiceBox.getItems().add("Chèque");
		paychoiceBox.getItems().add("Espèce");
		paychoiceBox.getItems().add("Virement");
		paychoiceBox.setValue("Espèce");
		payBox.getChildren().addAll(payLabel, paychoiceBox);

		// Entrée vehicule

		VBox entryBox = new VBox();
		Label entryLabel = new Label("Date d'entrée:");
		entryLabel.setFont(Font.font("Arial", 14));
		entryLabel.setStyle("-fx-text-fill: #000000");

		DatePicker entrydateField = new DatePicker();

		entrydateField.getEditor().setDisable(true);
		entrydateField.getEditor().setOpacity(1);
		entrydateField.setPromptText("Date d'entrée");

		entryBox.getChildren().addAll(entryLabel, entrydateField);

		VBox infovehi = new VBox();
		infovehi.setStyle(
				"-fx-background-color: #e6dfdf; " + "-fx-border-radius : 15px;" + "-fx-background-radius : 15px;");
		infovehi.setSpacing(20);
		infovehi.setPadding(new Insets(20));
		HBox immatbut = new HBox();
		immatbut.setSpacing(20);
		Button submitButton = new Button("Confirmer");
		if (!verifpage && !avoir) {
			submitButton.setText("Modifier");
		} else if (!verifpage && avoir)
			submitButton.setText("Générer l'avoir");

		submitButton.setEffect(new DropShadow(10, Color.BLACK));

		Button Searchbtn = new Button("Rechercher");

		Searchbtn.setEffect(new DropShadow(10, Color.BLACK));

		HBox btnSearchBox = new HBox(10);
		btnSearchBox.getChildren().addAll(Searchbtn);

		HBox btnBox = new HBox(10);
		btnBox.getChildren().addAll(submitButton);

		immatbut.getChildren().addAll(immatBox, btnSearchBox);
		btnSearchBox.setAlignment(Pos.BOTTOM_CENTER);
		immatbut.setAlignment(Pos.BOTTOM_LEFT);
		HBox typvehi = new HBox();
		typvehi.setSpacing(20);
		typvehi.getChildren().addAll(vehitypeBox, DestBox);

		VBox miscinfo = new VBox();
		miscinfo.setStyle(
				"-fx-background-color: #e6dfdf; " + "-fx-border-radius : 15px;" + "-fx-background-radius : 15px;");
		miscinfo.setSpacing(20);
		miscinfo.setPadding(new Insets(20));

		miscinfo.getChildren().addAll(typefaBox, payBox, garBox);
		topHbox.getChildren().addAll(infoclient, infovehi, miscinfo);

		HBox invoicelines = new HBox();

		labelvbox = new VBox();
		Label libelleLabel = new Label("Libellé :");

		labelvbox.getChildren().addAll(libelleLabel);

		qtevbox = new VBox();
		Label qteLabel = new Label("Quantité :");

		qtevbox.getChildren().addAll(qteLabel);

		puhtvbox = new VBox();
		Label puhtLabel = new Label("Prix unitaire :");

		puhtvbox.getChildren().addAll(puhtLabel);

		totalvbox = new VBox();
		Label totalLabel = new Label("Total :");

		totalvbox.getChildren().addAll(totalLabel);

		invoicelines.getChildren().addAll(labelvbox, qtevbox, puhtvbox, totalvbox);
		//////////////////////////////////// Condition
		//////////////////////////////////// box//////////////////////////////////////
		totalhtField = new TextField();

		cpField.setTextFormatter(new TextFormatter<>(filter));

		totalhtField.setTextFormatter(new TextFormatter<>(filter));

		///////////////////////////////////////////////////////////////////////////////////////
		VBox totalhtl = new VBox();
		totalhtl.setSpacing(10);
		Label totalhtLabel = new Label("Total :");

		totalhtl.getChildren().addAll(totalhtLabel, totalhtField);

		VBox totalttcl = new VBox();
		totalttcl.setSpacing(10);
		Label totalttcLabel = new Label("Total :");
		TextField totalttcField = new TextField();
		totalttcl.getChildren().addAll(totalttcLabel, totalttcField);
		totalttcField.setTextFormatter(new TextFormatter<>(filter));
		totalhtField.setEditable(false);
		totalttcField.setEditable(false);
		addLines();
		totalhtField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				double value = Double.parseDouble(newValue);
				String ttc = df.format(value) + "";
				ttc = ttc.replace(",", ".");
				totalttcField.setText(ttc);
			}
		});

		HBox totals = new HBox();
		totals.setSpacing(20);
		totals.getChildren().addAll(new Label("T.V.A payée sur la marge"), totalttcl);
		totals.setAlignment(Pos.BASELINE_RIGHT);

		generalVbox.getChildren().addAll(topHbox, separator, textfaBox, invoicelines, separator2, totals, btnBox);
		if (multiSelect != null) {
			textfaField.setText("Vente de ... selon la liste ci-jointe");
			Button listitems = new Button("Nouvelle Liste");
			Button seeList = new Button("Afficher la liste");
			infovehi.getChildren().addAll(listitems, seeList);
			infovehi.setAlignment(Pos.BASELINE_CENTER);

			listitems.setOnAction(e -> {
				if (id == 0) {

					String clientype = typeclientchoiceBox.getValue();
					String client = clientField.getText();
					String adresse = addresseField.getText();
					String ville = cityField.getText();
					String garage = garField.getText();
					String cp = cpField.getText();
					String textfac = textfaField.getText();
					String facty = typefachoiceBox.getValue();
					String totalht = totalhtField.getText();
					String totalttc = totalttcField.getText();
					String country = PaysField.getText();
					String tva = tvaField.getText();
					int facttype = 0;
					if (facty != "Temporaire") {
						facttype = 1;
					}
					String paytype = paychoiceBox.getValue();
					invoicenumgen = geninv.InvoiceNumber(facttype);
					if (client == "" | adresse == "" | ville == "" | cp == "") {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Données manquantes");
						alert.setHeaderText(null);
						alert.setContentText("Facture incomplète");
						alert.showAndWait();
					} else {
						int idtemp = invoiceinput.AddInvoice(clientype, client, adresse, garage, ville, cp, textfac,
								facttype, paytype, 0, 0, idvehicule, invoicenumgen, country, tva);
						String addList = String.valueOf(multiSelect.get(0).getId());
						for (int i = 1; i < multiSelect.size(); i++)
							addList = addList + "." + multiSelect.get(i).getId();

						invoiceinput.ListInvoice(idtemp, addList);
						app.invoiceListScene(multiSelect, idtemp);
					}

				} else

					app.invoiceListScene(multiSelect, id);

			});
			seeList.setOnAction(e -> {
				ScrollPane listpane = new ScrollPane();
				listpane.getStylesheets().add("/styles.css");
				listpane.getStyleClass().add("main-box");
				VBox listbox = new VBox(new Label("Listes des véhicules sélectionnés"),
						new Separator(Orientation.HORIZONTAL));
				listbox.getStylesheets().add("/styles.css");

				listbox.setSpacing(10);
				listbox.setPadding(new Insets(10));
				for (int i = 0; i < multiSelect.size(); i++) {
					listbox.getChildren()
							.addAll(new Text(multiSelect.get(i).getImmatriculation() + " "
									+ multiSelect.get(i).getMarque() + " " + multiSelect.get(i).getType()),
									new Separator(Orientation.HORIZONTAL));
				}
				listpane.setContent(listbox);
				grid.getChildren()
						.removeIf(node -> GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 2);
				grid.add(listpane, 2, 2);
			});

		} else
			infovehi.getChildren().addAll(immatbut, typvehi, entryBox);
		generalVbox.setAlignment(Pos.CENTER);
		generalVbox.getStylesheets().add("/styles.css");
		generalVbox.getStyleClass().add("main-box");
		grid.add(generalVbox, 1, 2);
		Searchbtn.setOnAction(e -> {

			String immat = immatField.getText();
			ResultSet rs = datataker.GetVehiculeByImmat(immat);

			try {
				rs.next();
				entrydateField.setValue(rs.getDate("dateEntree").toLocalDate());
				vehitypeField.setText(rs.getString("marque"));
				destchoiceBox.setText(rs.getString("destination"));
				idvehicule = rs.getInt("idvehicule");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		if (!verifpage) {
			Invoice invoice = invoicetaker.getSpecifiedInvoice(id);
			ResultSet rs1 = datataker.GetVehiculeById(invoice.getInvoicevehiculeid());
			try {
				while (rs1.next()) {
					entrydateField.setValue(rs1.getDate("dateEntree").toLocalDate());
					vehitypeField.setText(rs1.getString("marque"));
					destchoiceBox.setText(rs1.getString("destination"));
					immatField.setText(rs1.getString("immatriculation"));
				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			numFact.setText(invoice.getInvoiceNum());
			typeclientchoiceBox.setValue(invoice.getInvoiceClientType());
			clientField.setText(invoice.getInvoiceClient());
			cpField.setText(String.valueOf(invoice.getInvoiceCp()));
			addresseField.setText(invoice.getInvoiceAddress());
			cityField.setText(invoice.getInvoiceCity());
			garField.setText(invoice.getInvoicegar());
			textfaField.setText(invoice.getInvoiceText());
			Title.setText(Title.getText() + " " + invoice.getInvoiceNum());
			if (invoice.getInvoiceStatus() == "Avoir" | invoice.getInvoiceStatus() == "Payée") {
				typefachoiceBox.getItems().add("Avoir");
				typefachoiceBox.getItems().add("Payée");
				entrydateField.getEditor().setEditable(false);
				vehitypeField.setEditable(false);
				destchoiceBox.setEditable(false);
				immatField.setEditable(false);

				typeclientchoiceBox.setDisable(false);
				clientField.setEditable(false);
				cpField.setEditable(false);
				addresseField.setEditable(false);
				cityField.setEditable(false);
				garField.setEditable(false);
				textfaField.setEditable(false);
				if (invoice.getInvoiceStatus() == "Payée") {
					submitButton.setVisible(false);
				}
			}

			typefachoiceBox.setValue(invoice.getInvoiceStatus());

			if (avoir) {
				entrydateField.getEditor().setDisable(true);
				vehitypeField.setDisable(true);
				destchoiceBox.setDisable(true);
				immatField.setDisable(true);

				typeclientchoiceBox.setDisable(true);
				clientField.setDisable(true);
				cpField.setDisable(true);
				addresseField.setDisable(true);
				cityField.setDisable(true);
				garField.setDisable(true);

				typefachoiceBox.getItems().add("Avoir");
				typefachoiceBox.setValue("Avoir");
				typefachoiceBox.setDisable(true);

			}
			List<String[]> orderlineslist = invoicetaker.getInvoicelinetotal(id);

			for (int i = 0; i < orderlineslist.get(0).length; i++) {
				if (!(orderlineslist.get(0)[i] == null)) {

					txtlabel.get(i).setText(orderlineslist.get(1)[i]);

					txtqte.get(i).setText(orderlineslist.get(3)[i]);

					txtpuht.get(i).setText(orderlineslist.get(4)[i]);
				}

			}

		}

		/////////////////////// SUBMIT BUTTON ////////////////////////////////////
		submitButton.setOnAction(e -> {
			if (idvehicule == 0 && immatField.getText() != "") {
				String immat = immatField.getText();
				ResultSet rs = datataker.GetVehiculeByImmat(immat);
				try {
					rs.next();
					idvehicule = rs.getInt("idvehicule");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			String clientype = typeclientchoiceBox.getValue();
			String client = clientField.getText();
			String adresse = addresseField.getText();
			String ville = cityField.getText();
			String garage = garField.getText();
			String cp = cpField.getText();
			String textfac = textfaField.getText();
			String facty = typefachoiceBox.getValue();
			String totalht = totalhtField.getText();
			String totalttc = totalttcField.getText();
			String country = PaysField.getText();
			String tva = tvaField.getText();
			int facttype = 0;
			if (facty != "Temporaire") {
				facttype = 1;

			}
			String paytype = paychoiceBox.getValue();

			if (client == "" | adresse == "" | ville == "" | cp == "" | textfac == "" | totalht == "" | paytype == "") {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Données manquantes");
				alert.setHeaderText(null);
				alert.setContentText("Facture incomplète");
				alert.showAndWait();
			} else {
				int compteur = 0;
				for (int i = 0; i < txttotal.size(); i++) {

					if (txttotal.get(i).getText() != "" && txtlabel.get(i).getText().isEmpty()) {
						System.out.println(txtlabel.get(i).getText());
						System.out.println(txttotal.get(i).getText());
						System.out.println(i);
						compteur++;
					}

				}
				if (compteur != 0) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Données manquantes");
					alert.setHeaderText(null);
					alert.setContentText("Lignes de prestation incomplètes");
					alert.showAndWait();
				} else {
					if (verifpage) {

						invoicenumgen = geninv.InvoiceNumber(facttype);

						int idinvoice = invoiceinput.AddInvoice(clientype, client, adresse, garage, ville, cp, textfac,
								facttype, paytype, Double.parseDouble(totalht), Float.parseFloat(totalttc), idvehicule,
								invoicenumgen, country, tva);

						if (multiSelect != null) {
							String addList = String.valueOf(multiSelect.get(0).getId());
							for (int i = 1; i < multiSelect.size(); i++)
								addList = addList + "." + multiSelect.get(i).getId();

							invoiceinput.ListInvoice(idinvoice, addList);
						}

						for (int i = 0; i < txttotal.size(); i++) {
							if (txttotal.get(i).getText() != "") {
								// input into db
								invoiceinput.AddInvoiceLine(idinvoice, txtlabel.get(i).getText(),
										Integer.parseInt(txtqte.get(i).getText()),
										Float.parseFloat(txtpuht.get(i).getText()),
										Float.parseFloat(txttotal.get(i).getText()));
							}

						}
						HBox Validationbox = new HBox();
						Validationbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
								+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;"
								+ "-fx-border-color: #3b703d;"
								+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

						Label ValidationLabel = new Label("La facture a bien été enregistrée !");
						ValidationLabel.setFont(Font.font("Arial", 20));
						ValidationLabel.setStyle("-fx-text-fill: #000000");
						Validationbox.setPadding(new Insets(20));
						Validationbox.setAlignment(Pos.BASELINE_CENTER);
						Validationbox.getChildren().addAll(ValidationLabel);
						grid.add(Validationbox, 1, 1);
					} else if (!avoir) {

						if (facty != "Temporaire") {
							facttype = 1;
						}
						if (!numFact.getText().contains("MT") && facttype == 1)
							invoicenumgen = geninv.InvoiceNumber(facttype);
						else
							invoicenumgen = numFact.getText();

						invoiceinput.UpdateInvoice(id, clientype, client, adresse, garage, ville, cp, textfac, facttype,
								paytype, Double.parseDouble(totalht), Double.parseDouble(totalttc), idvehicule,
								invoicenumgen, country, tva);
						if (multiSelect != null) {
							String addList = String.valueOf(multiSelect.get(0).getId());
							for (int i = 1; i < multiSelect.size(); i++)
								addList = addList + "." + multiSelect.get(i).getId();

							invoiceinput.ListInvoice(id, addList);
						}
						invoiceinput.DeleteInvocieLines(id);
						for (int i = 0; i < txttotal.size(); i++) {
							if (txttotal.get(i).getText() != "") {
								// input into db
								invoiceinput.AddInvoiceLine(id, txtlabel.get(i).getText(),
										Integer.parseInt(txtqte.get(i).getText()),
										Float.parseFloat(txtpuht.get(i).getText()),
										Float.parseFloat(txttotal.get(i).getText()));
							}

						}
						HBox Validationbox = new HBox();
						Validationbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
								+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;"
								+ "-fx-border-color: #3b703d;"
								+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

						Label ValidationLabel = new Label("La facture a bien été modifiée !");
						ValidationLabel.setFont(Font.font("Arial", 20));
						ValidationLabel.setStyle("-fx-text-fill: #000000");
						Validationbox.setPadding(new Insets(20));
						Validationbox.setAlignment(Pos.BASELINE_CENTER);
						Validationbox.getChildren().addAll(ValidationLabel);
						grid.add(Validationbox, 1, 1);

					} else {

						invoicenumgen = geninv.InvoiceNumber(facttype);

						int idinvoice = invoiceinput.AddInvoice(clientype, client, adresse, garage, ville, cp, textfac,
								2, paytype, Double.parseDouble(totalht), Float.parseFloat(totalttc), idvehicule,
								invoicenumgen, country, tva);

						if (multiSelect != null) {
							String addList = String.valueOf(multiSelect.get(0).getId());
							for (int i = 1; i < multiSelect.size(); i++)
								addList = addList + "." + multiSelect.get(i).getId();

							invoiceinput.ListInvoice(idinvoice, addList);
						}
						invoiceinput.linkAVtoInvoice(idinvoice, id);
						for (int i = 0; i < txttotal.size(); i++) {
							if (txttotal.get(i).getText() != "") {
								// input into db

								invoiceinput.AddInvoiceLine(idinvoice, txtlabel.get(i).getText(),
										Integer.parseInt(txtqte.get(i).getText()),
										Float.parseFloat(txtpuht.get(i).getText()),
										Float.parseFloat(txttotal.get(i).getText()));
							}

						}

					}
				}
			}

		});

		ScrollPane stackPane = new ScrollPane();
		stackPane.setContent(grid);
		stackPane.setFitToWidth(true);
		this.setCenter(stackPane);

		return new Scene(this);
	}

	public void addLines() {
		txtlabel = this.txtlabel;
		txtqte = this.txtqte;
		txttotal = this.txttotal;
		txtpuht = this.txtpuht;
		TextField txtlabField = new TextField();
		txtlabField.setStyle(" -fx-min-width: 500px;");
		TextField txtqteField = new TextField();
		txtqteField.setStyle("-fx-max-width: 100px;");
		TextField txttotalField = new TextField();
		TextField txtpuhtField = new TextField();
		txttotalField.setEditable(false);
		txtqteField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
					if (txtpuhtField.getText() != "" && newValue != "") {
						double value = Integer.parseInt(txtqteField.getText());
						float total = (float) (Double.parseDouble(txtpuhtField.getText()) * value);
						String complete = total + "";
						txttotalField.setText(complete);
					}
				} else {
					txtqteField.setText(oldValue);
				}
			}
		});

		txttotalField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (oldValue != "") {
					float total = Float.parseFloat(totalhtField.getText()) - Float.parseFloat(oldValue)
							+ Float.parseFloat(newValue);
					String values = total + "";
					totalhtField.setText(values);
				} else if (newValue == "") {
					float total = Float.parseFloat(totalhtField.getText()) + Float.parseFloat(oldValue);
					String values = total + "";
					totalhtField.setText(values);
				} else {
					float total = 0;
					if (totalhtField.getText() != "")
						total = Float.parseFloat(totalhtField.getText()) + Float.parseFloat(newValue);

					else
						total = Float.parseFloat(newValue);

					String values = total + "";
					totalhtField.setText(values);
					addLines();
				}

			}
		});
		txtpuhtField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (txtqteField.getText() != "" && newValue != "") {
					double value = Double.parseDouble(txtpuhtField.getText());
					float total = (float) (Integer.parseInt(txtqteField.getText()) * value);
					String complete = total + "";
					txttotalField.setText(complete);

				}

			}
		});
		txtlabel.add(txtlabField);
		txtqte.add(txtqteField);
		txtpuht.add(txtpuhtField);
		txttotal.add(txttotalField);
		totalvbox.getChildren().addAll(txttotalField);
		puhtvbox.getChildren().addAll(txtpuhtField);
		qtevbox.getChildren().addAll(txtqteField);
		labelvbox.getChildren().addAll(txtlabField);

	}

	public ObservableList<Integer> getIds(String listvehi) {
		ObservableList<Integer> listids = FXCollections.observableArrayList();
		String temp = "";
		for (int i = 0; i < listvehi.length(); i++) {

			if (listvehi.charAt(i) != '.') {
				temp = temp + listvehi.charAt(i);
			} else {
				listids.add(Integer.parseInt(temp));
				temp = "";
			}

		}
		listids.add(Integer.parseInt(temp));
		return listids;

	}
}
