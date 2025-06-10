package front;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.function.UnaryOperator;

import javax.imageio.ImageIO;

import back.CalendarController;
import back.DataTaker;
import back.FileInputer;
import back.PicInput;
import back.User;
import back.dataInput;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

public class NewVehicule extends BorderPane {
	CalendarController cal = new CalendarController();
	private App app;
	int klms;
	int hp;
	int cp;
	int idvehi;
	java.sql.Date mcdate;
	java.sql.Date cadate;
	float c1prix;
	float c2prix;
	float c3prix;
	float pa;
	float cd;
	float frais;
	float soldeprix;
	File selectedFile;
	List<File> selectedimgFileList;
	ObservableList<File> selectedFileList;
	private User utilisateur;

	// DataBase section
	String BDD = "geode_bd";
	String url = "jdbc:mysql://192.168.1.85:3306/" + BDD;
	String user = "username";
	String passwd = "password";

	public NewVehicule(App app, User utilisateur) {
		this.app = app;
		this.utilisateur = utilisateur;
	}

	public Scene createVehiculeInput(String immatch, boolean verifpage) {
		selectedFileList = FXCollections.observableArrayList();
		selectedimgFileList = FXCollections.observableArrayList();
		TopNavBar topnav = new TopNavBar(app, "AllVehiScene", utilisateur);
		VBox topScreen = new VBox();
		Label Title = new Label();
		ParamButton pambut = new ParamButton(app);
		this.setBottom(pambut);

		if (immatch != "")
			Title.setText("Modifier un véhicule");
		else
			Title.setText("Ajouter un véhicule");
		Title.getStylesheets().add("/styles.css");
		Title.getStyleClass().add("Title");
		topScreen.getChildren().addAll(topnav);
		this.setTop(topScreen);
		DataTaker datataker = new DataTaker();
		FileInputer filein = new FileInputer();
		PicInput picin = new PicInput();
		Image image = new Image("/vehiculecst.png", 400, 400, false, false);
		Image image2 = new Image("/vehiculecst.png", 400, 400, false, false);
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

		VBox canvasbox = new VBox();
		canvasbox.setStyle("-fx-background-color: #d9d6d0 ;" + "-fx-border-radius : 15px;"
				+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

		Canvas canvas = new Canvas(400, 400);
		Label canvasTitle = new Label("Constat");
		canvasTitle.getStylesheets().add("/styles.css");
		canvasTitle.getStyleClass().add("label");
		// canvasTitle.setAlignment(Pos.CENTER);
		canvasbox.getChildren().addAll(canvasTitle, new Separator(Orientation.HORIZONTAL), canvas);
		canvasbox.setMaxSize(500, 500);
		canvasbox.setPadding(new Insets(10, 0, 0, 0));
		canvasbox.setSpacing(15);
		canvasbox.setAlignment(Pos.BASELINE_CENTER);
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.drawImage(image, 0, 400);

		initDraw(graphicsContext);

		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				graphicsContext.beginPath();
				graphicsContext.moveTo(event.getX(), event.getY());
				graphicsContext.stroke();
			}
		});

		canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				graphicsContext.lineTo(event.getX(), event.getY());
				graphicsContext.stroke();
			}
		});

		canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

			}
		});

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setAlignment(Pos.BASELINE_CENTER);
		grid.add(Title, 1, 0);
		VBox generalBox = new VBox();
		generalBox.setPadding(new Insets(40));
		generalBox.setSpacing(40);

		///////////// DATA VEHIUCULE
		HBox vehiinputhbox = new HBox();
		vehiinputhbox.setPadding(new Insets(20));
		vehiinputhbox.getStylesheets().add("/styles.css");
		vehiinputhbox.getStyleClass().add("main-box");
		vehiinputhbox.setSpacing(40);

		VBox vehineedhbox = new VBox();
		vehineedhbox.setSpacing(20);

		VBox vehistathbox = new VBox();
		vehistathbox.setSpacing(20);

		VBox vehicosmvbox = new VBox();
		vehicosmvbox.setSpacing(20);

		VBox vehidatevbox = new VBox();
		vehidatevbox.setSpacing(20);
		// Marque du véhicule
		VBox marqueBox = new VBox();
		Label marqueLabel = new Label("Marque du véhicule :");
		marqueLabel.setFont(Font.font("Arial", 14));
		marqueLabel.setStyle("-fx-text-fill: #000000");
		TextField marqueField = new TextField();
		marqueField.setPromptText("Marque");
		// marqueField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		marqueBox.getChildren().addAll(marqueLabel, marqueField);

		// Type du véhicule
		VBox typeBox = new VBox();
		Label typeLabel = new Label("Type du véhicule :");
		typeLabel.setFont(Font.font("Arial", 14));
		typeLabel.setStyle("-fx-text-fill: #000000");
		TextField typeField = new TextField();
		typeField.setPromptText("Type");
		// typeField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		typeBox.getChildren().addAll(typeLabel, typeField);

		// Immatriculation
		VBox immatBox = new VBox();
		Label immatLabel = new Label("Immatriculation :");
		immatLabel.setFont(Font.font("Arial", 14));
		immatLabel.setStyle("-fx-text-fill: #000000");
		TextField immatField = new TextField();
		immatField.setPromptText("Immatriculation");
		// immatField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		immatBox.getChildren().addAll(immatLabel, immatField);

		// Carrosserie
		VBox carrosBox = new VBox();
		Label carrosLabel = new Label("Carrosserie :");
		carrosLabel.setFont(Font.font("Arial", 14));
		carrosLabel.setStyle("-fx-text-fill: #000000");
		ChoiceBox<String> carroschoiceBox = new ChoiceBox<String>();
		carroschoiceBox.getItems().add("Solo");
		carroschoiceBox.getItems().add("deux portes");
		carroschoiceBox.getItems().add("4 portes");
		carrosBox.getChildren().addAll(carrosLabel, carroschoiceBox);

		carroschoiceBox.setOnAction((event) -> {
			graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			if (carroschoiceBox.getValue() == "Solo")
				graphicsContext.drawImage(image, 50, 50);
			if (carroschoiceBox.getValue() == "deux portes")
				graphicsContext.drawImage(image2, 0, 50);
			if (carroschoiceBox.getValue() == "4 portes")
				graphicsContext.drawImage(image, 50, 50);
		});
		carroschoiceBox.setValue("4 portes");

		// Numero de Serie
		VBox numSerBox = new VBox();
		Label numSerLabel = new Label("Numero de Série :");
		numSerLabel.setFont(Font.font("Arial", 14));
		numSerLabel.setStyle("-fx-text-fill: #000000");
		TextField numSerField = new TextField();
		numSerField.setPromptText("XXXXXXXX");
		// numSerField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		numSerBox.getChildren().addAll(numSerLabel, numSerField);

		// Destination
		VBox DestBox = new VBox();
		Label DestLabel = new Label("Destination :");
		DestLabel.setFont(Font.font("Arial", 14));
		DestLabel.setStyle("-fx-text-fill: #000000");
		ChoiceBox<String> destchoiceBox = new ChoiceBox<String>();
		destchoiceBox.getItems().add("Occasion");
		destchoiceBox.getItems().add("Assurance");
		destchoiceBox.getItems().add("Casse");
		destchoiceBox.getItems().add("Dépot");
		destchoiceBox.setValue("Occasion");
		DestBox.getChildren().addAll(DestLabel, destchoiceBox);

		// Puissance
		VBox hpBox = new VBox();
		Label hpLabel = new Label("Puissance :");
		hpLabel.setFont(Font.font("Arial", 14));
		hpLabel.setStyle("-fx-text-fill: #000000");
		TextField hpField = new TextField();

		hpField.setTextFormatter(new TextFormatter<>(filter));
		hpField.setPromptText("Puissance");
		// hpField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		hpBox.getChildren().addAll(hpLabel, hpField);

		// klms

		VBox klmsBox = new VBox();
		Label klmsLabel = new Label("Kilométrage :");
		klmsLabel.setFont(Font.font("Arial", 14));
		klmsLabel.setStyle("-fx-text-fill: #000000");
		TextField klmsField = new TextField();
		klmsField.setTextFormatter(new TextFormatter<>(filter));

		klmsField.setPromptText("klms");
		// klmsField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		klmsBox.getChildren().addAll(klmsLabel, klmsField);

		// couleur
		VBox couleurBox = new VBox();
		Label couleurLabel = new Label("Couleur :");
		couleurLabel.setFont(Font.font("Arial", 14));
		couleurLabel.setStyle("-fx-text-fill: #000000");
		TextField couleurField = new TextField();
		couleurField.setPromptText("Couleur");
		// couleurField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		couleurBox.getChildren().addAll(couleurLabel, couleurField);

		// MiseEnCirculation

		VBox mecBox = new VBox();
		Label mecLabel = new Label("Mise en Circulation :");
		mecLabel.setFont(Font.font("Arial", 14));
		mecLabel.setStyle("-fx-text-fill: #000000");
		DatePicker mecdateField = new DatePicker();
		mecdateField.getEditor().setDisable(true);
		mecdateField.getEditor().setOpacity(1);
		mecdateField.setPromptText("Mise en Circulation");
		// mecdateField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		mecBox.getChildren().addAll(mecLabel, mecdateField);

		// Certification Actuelle

		VBox caBox = new VBox();
		Label caLabel = new Label("Certif Actuel:");
		caLabel.setFont(Font.font("Arial", 14));
		caLabel.setStyle("-fx-text-fill: #000000");

		DatePicker cadateField = new DatePicker();
		cadateField.getEditor().setDisable(true);
		cadateField.getEditor().setOpacity(1);
		cadateField.setPromptText("Certif Actuel");
		// cadateField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		caBox.getChildren().addAll(caLabel, cadateField);

		// button submit to database
		Button submitButton = new Button("Ajouter le véhicule");

		submitButton.setEffect(new DropShadow(10, Color.BLACK));

		HBox btnBox = new HBox(10);
		btnBox.getChildren().addAll(submitButton);

		vehineedhbox.getChildren().addAll(marqueBox, typeBox, immatBox);
		vehineedhbox.setStyle(
				"-fx-background-color: #e6dfdf; " + "-fx-border-radius : 15px;" + "-fx-background-radius : 15px;");
		vehineedhbox.setPadding(new Insets(20));

		vehistathbox.setPadding(new Insets(20));
		vehidatevbox.getChildren().addAll(DestBox, mecBox, caBox);
		vehidatevbox.setPadding(new Insets(20));
		if (utilisateur.getUserrole() == 2) {
			vehineedhbox.getChildren().addAll(numSerBox);

			vehicosmvbox.getChildren().addAll(couleurBox, carrosBox);
		} else
			vehicosmvbox.getChildren().addAll(couleurBox, carrosBox, numSerBox);
		vehicosmvbox.setPadding(new Insets(20));

		vehiinputhbox.getChildren().addAll(vehineedhbox, vehicosmvbox, vehistathbox, vehidatevbox);

		//////////////////// DATAVHEICULE FIN //////////////////////

		///////////////////// INFO DOC //////////////////////////////
		HBox infodoc = new HBox();
		infodoc.setPadding(new Insets(20));
		infodoc.getStylesheets().add("/styles.css");
		infodoc.getStyleClass().add("main-box");
		infodoc.setSpacing(80);

		VBox lieuvbox = new VBox();
		lieuvbox.setSpacing(20);

		VBox dossvbox = new VBox();
		dossvbox.setSpacing(20);

		VBox depdatepref = new VBox();
		depdatepref.setSpacing(20);
		// Date enlèvement
		VBox datenelBox = new VBox();
		Label datenelLabel = new Label("Date d'enlèvement:");
		datenelLabel.setFont(Font.font("Arial", 14));
		datenelLabel.setStyle("-fx-text-fill: #000000");
		DatePicker datenelField = new DatePicker();
		datenelField.getEditor().setDisable(true);
		datenelField.getEditor().setOpacity(1);
		datenelField.setPromptText("Date d'enlèvement");
		// datenelField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		datenelBox.getChildren().addAll(datenelLabel, datenelField);

		// Cause
		VBox causeBox = new VBox();
		Label causeLabel = new Label("Cause:");
		causeLabel.setFont(Font.font("Arial", 14));
		causeLabel.setStyle("-fx-text-fill: #000000");
		TextField causeField = new TextField();
		causeField.setPromptText("Cause");
		// causeField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		causeBox.getChildren().addAll(causeLabel, causeField);

		// Depaneur
		VBox depBox = new VBox();
		Label depLabel = new Label("Dépanneur:");
		depLabel.setFont(Font.font("Arial", 14));
		depLabel.setStyle("-fx-text-fill: #000000");
		TextField depField = new TextField();
		depField.setPromptText("Dépanneur");
		// depField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		depBox.getChildren().addAll(depLabel, depField);

		// Préfecture
		VBox prefBox = new VBox();
		Label prefLabel = new Label("Pays:");
		prefLabel.setFont(Font.font("Arial", 14));
		prefLabel.setStyle("-fx-text-fill: #000000");
		TextField prefField = new TextField();
		prefField.setPromptText("Pays");
		prefField.setText("France");
		// prefField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		prefBox.getChildren().addAll(prefLabel, prefField);

		// Addresse
		VBox addresseBox = new VBox();
		Label addresseLabel = new Label("Addresse:");
		addresseLabel.setFont(Font.font("Arial", 14));
		addresseLabel.setStyle("-fx-text-fill: #000000");
		TextField addresseField = new TextField();
		addresseField.setPromptText("Adresse");
		addresseField.setStyle("-fx-max-width: 300px;");
		addresseBox.getChildren().addAll(addresseLabel, addresseField);

		// Code Postal
		VBox cpBox = new VBox();
		Label cpLabel = new Label("Code Postal :");
		cpLabel.setFont(Font.font("Arial", 14));
		cpLabel.setStyle("-fx-text-fill: #000000");
		TextField cpField = new TextField();
		cpField.setTextFormatter(new TextFormatter<>(filter));

		cpField.setPromptText("Code Postal");
		cpField.setStyle("-fx-max-width: 120px");
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

		// Addresse
		VBox nomPartBox = new VBox();
		Label nomPartLabel = new Label("Nom");
		nomPartLabel.setFont(Font.font("Arial", 14));
		nomPartLabel.setStyle("-fx-text-fill: #000000");
		TextField nomPartField = new TextField();
		nomPartField.setPromptText("Nom");
		nomPartField.setStyle("-fx-max-width: 300px;");
		nomPartBox.getChildren().addAll(nomPartLabel, nomPartField);
		nomPartBox.setVisible(false);

		// dossier
		VBox dossBox = new VBox();
		Label dossLabel = new Label("Dossier/CI:");
		dossLabel.setFont(Font.font("Arial", 14));
		dossLabel.setStyle("-fx-text-fill: #000000");
		TextField dossField = new TextField();
		dossField.setPromptText("Dossier/CI");
		// dossField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		dossBox.getChildren().addAll(dossLabel, dossField);

		// origine enlèvement
		VBox orienlBox = new VBox();
		Label orienlLabel = new Label("Origine :");
		orienlLabel.setFont(Font.font("Arial", 14));
		orienlLabel.setStyle("-fx-text-fill: #000000");
		ChoiceBox<String> orienlchoiceBox = new ChoiceBox<String>();
		orienlchoiceBox.getItems().add("Assurance");
		orienlchoiceBox.getItems().add("Particulier");
		orienlchoiceBox.setValue("Assurance");
		orienlchoiceBox.setOnAction((event) -> {
			int selectedIndex = orienlchoiceBox.getSelectionModel().getSelectedIndex();
			if (selectedIndex == 1)
				nomPartBox.setVisible(true);
			else {
				nomPartBox.setVisible(false);
			}
		});
		orienlBox.getChildren().addAll(orienlLabel, orienlchoiceBox);

		HBox villebox = new HBox();
		villebox.getChildren().addAll(cpBox, cityBox);
		lieuvbox.getChildren().addAll(nomPartBox, addresseBox, villebox);
		dossvbox.getChildren().addAll(orienlBox, dossBox, causeBox);
		depdatepref.getChildren().addAll(depBox, datenelBox, prefBox);
		infodoc.getChildren().addAll(dossvbox, lieuvbox, depdatepref);

		////////////////// FIN INFO DOC ///////////////////////////

		//////////////////// INFO FRAIS ET PAIEMENT//////////////////////

		HBox paymentdoc = new HBox();
		paymentdoc.setPadding(new Insets(20));
		paymentdoc.getStylesheets().add("/styles.css");
		paymentdoc.getStyleClass().add("main-box");
		paymentdoc.setSpacing(80);

		VBox checkersVbox = new VBox();
		checkersVbox.setSpacing(20);

		VBox paiementVbox = new VBox();
		paiementVbox.setSpacing(20);

		VBox payedVbox = new VBox();
		payedVbox.setSpacing(20);

		// Date enlèvement
		VBox datedestBox = new VBox();
		Label datedestLabel = new Label("Date de destruction:");
		datedestLabel.setFont(Font.font("Arial", 14));
		datedestLabel.setStyle("-fx-text-fill: #000000");
		DatePicker datedestField = new DatePicker();
		datedestField.getEditor().setDisable(true);
		datedestField.getEditor().setOpacity(1);
		datedestField.setPromptText("Date de destruction");
		// datedestField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		datedestBox.getChildren().addAll(datedestLabel, datedestField);
		// Date CessaTion
		VBox datecessBox = new VBox();
		Label datecessLabel = new Label("Date de cession:");
		datecessLabel.setFont(Font.font("Arial", 14));
		datecessLabel.setStyle("-fx-text-fill: #000000");
		DatePicker datecessField = new DatePicker();
		datecessField.getEditor().setDisable(true);
		datecessField.getEditor().setOpacity(1);
		datecessField.setPromptText("Date de cession");
		// datedestField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		datedestBox.getChildren().addAll(datecessLabel, datecessField);

		// CG RSV
		VBox checkers = new VBox();
		HBox checkbut = new HBox();
		HBox cgBox = new HBox();
		Label cgLabel = new Label("Brulé :");
		cgLabel.setFont(Font.font("Arial", 14));
		cgLabel.setStyle("-fx-text-fill: #000000");
		CheckBox cgcheck = new CheckBox();
		cgBox.getChildren().addAll(cgLabel, cgcheck);

		HBox RSVBox = new HBox();
		Label rsvLabel = new Label("RSV :");
		rsvLabel.setFont(Font.font("Arial", 14));
		rsvLabel.setStyle("-fx-text-fill: #000000");
		CheckBox rsvcheck = new CheckBox();
		RSVBox.getChildren().addAll(rsvLabel, rsvcheck);
		checkers.getChildren().addAll(cgBox, RSVBox);
		Button hide = new Button("Afficher Paiement");
		checkbut.getChildren().addAll(checkers, hide);
		// Prix d'achat
		VBox paBox = new VBox();
		Label paLabel = new Label("Prix d'achat :");
		paLabel.setFont(Font.font("Arial", 14));
		paLabel.setStyle("-fx-text-fill: #000000");
		TextField paField = new TextField();
		paField.setTextFormatter(new TextFormatter<>(filter));

		paField.setPromptText("Prix d'achat");
		// paField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		paBox.getChildren().addAll(paLabel, paField);

		// Frais
		VBox fraisBox = new VBox();
		Label fraisLabel = new Label("Frais :");
		fraisLabel.setFont(Font.font("Arial", 14));
		fraisLabel.setStyle("-fx-text-fill: #000000");
		TextField fraisField = new TextField();
		fraisField.setTextFormatter(new TextFormatter<>(filter));

		fraisField.setPromptText("Frais");
		// fraisField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		fraisBox.getChildren().addAll(fraisLabel, fraisField);

		// Cout Dépanage
		VBox cdBox = new VBox();
		Label cdLabel = new Label("Cout Dépannage :");
		cdLabel.setFont(Font.font("Arial", 14));
		cdLabel.setStyle("-fx-text-fill: #000000");
		TextField cdField = new TextField();
		cdField.setTextFormatter(new TextFormatter<>(filter));

		cdField.setPromptText("Cout Dépannage");
		// cdField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		cdBox.getChildren().addAll(cdLabel, cdField);

		// Solde
		VBox soldeBox = new VBox();
		Label soldeLabel = new Label("Solde:");
		soldeLabel.setFont(Font.font("Arial", 14));
		soldeLabel.setStyle("-fx-text-fill: #000000");
		TextField soldeField = new TextField();
		soldeField.setTextFormatter(new TextFormatter<>(filter));

		soldeField.setPromptText("Solde");
		// soldeField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		soldeBox.getChildren().addAll(soldeLabel, soldeField);

		HBox reg1 = new HBox();

		Label reg1Label = new Label("Règlement :");
		reg1Label.setFont(Font.font("Arial", 14));
		reg1Label.setStyle("-fx-text-fill: #000000");
		TextField reg1Field = new TextField();
		reg1Field.setTextFormatter(new TextFormatter<>(filter));
		reg1Field.setPromptText("Montant");
		// reg1Field.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

		TextField reg1numField = new TextField();
		reg1numField.setPromptText("Numero chèque");
		// reg1numField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		DatePicker datereg1Field = new DatePicker();
		datereg1Field.getEditor().setDisable(true);
		datereg1Field.getEditor().setOpacity(1);
		datereg1Field.setPromptText("Date de règlement");
		// datereg1Field.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

		reg1.getChildren().addAll(reg1Field, reg1numField);
		VBox reg1vbox = new VBox();
		reg1vbox.getChildren().addAll(reg1Label, reg1, datereg1Field);
		/// Règlement 2
		HBox reg2 = new HBox();

		Label reg2Label = new Label("Règlement :");
		reg2Label.setFont(Font.font("Arial", 14));
		reg2Label.setStyle("-fx-text-fill: #000000");
		TextField reg2Field = new TextField();
		reg2Field.setTextFormatter(new TextFormatter<>(filter));
		reg2Field.setPromptText("Montant");
		// reg2Field.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

		TextField reg2numField = new TextField();
		reg2numField.setPromptText("Numero chèque");
		// reg2numField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		DatePicker datereg2Field = new DatePicker();
		datereg2Field.getEditor().setDisable(true);
		datereg2Field.getEditor().setOpacity(1);
		datereg2Field.setPromptText("Date de règlement");
		// datereg2Field.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

		reg2.getChildren().addAll(reg2Field, reg2numField);
		VBox reg2vbox = new VBox();
		reg2vbox.getChildren().addAll(reg2Label, reg2, datereg2Field);
		/// Règlement 3
		HBox reg3 = new HBox();

		Label reg3Label = new Label("Règlement :");
		reg3Label.setFont(Font.font("Arial", 14));
		reg3Label.setStyle("-fx-text-fill: #000000");
		TextField reg3Field = new TextField();
		reg3Field.setTextFormatter(new TextFormatter<>(filter));

		reg3Field.setPromptText("Montant");
		// reg3Field.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

		TextField reg3numField = new TextField();
		reg3numField.setPromptText("Numero chèque");
		// reg3numField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		DatePicker datereg3Field = new DatePicker();
		datereg3Field.getEditor().setDisable(true);
		datereg3Field.getEditor().setOpacity(1);
		datereg3Field.setPromptText("Date de règlement");
		// datereg3Field.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

		cdField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (oldValue != "") {
					if (soldeField.getText() != "" && newValue != "") {
						float total = Float.parseFloat(soldeField.getText()) - Float.parseFloat(oldValue)
								+ Float.parseFloat(newValue);
						String values = total + "";
						soldeField.setText(values);
					} else if (soldeField.getText() != "" && newValue == "") {
						float total = Float.parseFloat(soldeField.getText()) - Float.parseFloat(oldValue);
						String values = total + "";
						soldeField.setText(values);
					} else {
						soldeField.setText(newValue);
					}

				} else {
					if (soldeField.getText() != "") {
						float total = Float.parseFloat(soldeField.getText()) + Float.parseFloat(newValue);
						String values = total + "";
						soldeField.setText(values);
					} else {
						soldeField.setText(newValue);
					}

				}
			}
		});
		fraisField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (oldValue != "") {
					if (soldeField.getText() != "" && newValue != "") {
						float total = Float.parseFloat(soldeField.getText()) - Float.parseFloat(oldValue)
								+ Float.parseFloat(newValue);
						String values = total + "";
						soldeField.setText(values);
					} else if (soldeField.getText() != "" && newValue == "") {
						float total = Float.parseFloat(soldeField.getText()) - Float.parseFloat(oldValue);
						String values = total + "";
						soldeField.setText(values);
					} else {
						soldeField.setText(newValue);
					}

				} else {
					if (soldeField.getText() != "") {
						float total = Float.parseFloat(soldeField.getText()) + Float.parseFloat(newValue);
						String values = total + "";
						soldeField.setText(values);
					} else {
						soldeField.setText(newValue);
					}

				}
			}
		});
		paField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (oldValue != "") {
					if (soldeField.getText() != "" && newValue != "") {
						float total = Float.parseFloat(soldeField.getText()) - Float.parseFloat(oldValue)
								+ Float.parseFloat(newValue);
						String values = total + "";
						soldeField.setText(values);
					} else if (soldeField.getText() != "" && newValue == "") {
						float total = Float.parseFloat(soldeField.getText()) - Float.parseFloat(oldValue);
						String values = total + "";
						soldeField.setText(values);
					} else {
						soldeField.setText(newValue);
					}

				} else {
					if (soldeField.getText() != "") {
						float total = Float.parseFloat(soldeField.getText()) + Float.parseFloat(newValue);
						String values = total + "";
						soldeField.setText(values);
					} else {
						soldeField.setText(newValue);
					}

				}
			}
		});
		reg1Field.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (oldValue != "") {
					if (soldeField.getText() != "" && newValue != "") {
						float total = Float.parseFloat(soldeField.getText()) + Float.parseFloat(oldValue)
								- Float.parseFloat(newValue);
						String values = total + "";
						soldeField.setText(values);
					} else if (soldeField.getText() != "" && newValue == "") {
						float total = Float.parseFloat(soldeField.getText()) + Float.parseFloat(oldValue);
						String values = total + "";
						soldeField.setText(values);
					} else {
						soldeField.setText(newValue);
					}

				} else {
					if (soldeField.getText() != "") {
						float total = Float.parseFloat(soldeField.getText()) - Float.parseFloat(newValue);
						String values = total + "";
						soldeField.setText(values);
					} else {
						soldeField.setText(newValue);
					}

				}
			}
		});
		reg2Field.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (oldValue != "") {
					if (soldeField.getText() != "" && newValue != "") {
						float total = Float.parseFloat(soldeField.getText()) + Float.parseFloat(oldValue)
								- Float.parseFloat(newValue);
						String values = total + "";
						soldeField.setText(values);
					} else if (soldeField.getText() != "" && newValue == "") {
						float total = Float.parseFloat(soldeField.getText()) + Float.parseFloat(oldValue);
						String values = total + "";
						soldeField.setText(values);
					} else {
						soldeField.setText(newValue);
					}

				} else {
					if (soldeField.getText() != "") {
						float total = Float.parseFloat(soldeField.getText()) - Float.parseFloat(newValue);
						String values = total + "";
						soldeField.setText(values);
					} else {
						soldeField.setText(newValue);
					}

				}
			}
		});
		reg3Field.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (oldValue != "") {
					if (soldeField.getText() != "" && newValue != "") {
						float total = Float.parseFloat(soldeField.getText()) + Float.parseFloat(oldValue)
								- Float.parseFloat(newValue);
						String values = total + "";
						soldeField.setText(values);
					} else if (soldeField.getText() != "" && newValue == "") {
						float total = Float.parseFloat(soldeField.getText()) + Float.parseFloat(oldValue);
						String values = total + "";
						soldeField.setText(values);
					} else {
						soldeField.setText(newValue);
					}

				} else {
					if (soldeField.getText() != "") {
						float total = Float.parseFloat(soldeField.getText()) - Float.parseFloat(newValue);
						String values = total + "";
						soldeField.setText(values);
					} else {
						soldeField.setText(newValue);
					}

				}
			}
		});

		///////////////////////////////////////////////////// File
		///////////////////////////////////////////////////// input/////////////////////////////////////////////////////////
		// CG input
		FileChooser fileChooser = new FileChooser();
		Label cgchooser = new Label("Carte Grise");
		cgchooser.setFont(Font.font("Arial", 14));
		cgchooser.setStyle("-fx-text-fill: #000000");

		Button button = new Button("Carte Grise");
		button.setStyle("-fx-max-width:130px; -fx-min-width: 130px;");
		button.setEffect(new DropShadow(10, Color.BLACK));
		TextField choosedfile = new TextField();
		choosedfile.setPromptText("Carte Grise");

		choosedfile.setOpacity(1);
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Pdf files", "*.pdf"));
		button.setOnAction(e -> {
			selectedFile = fileChooser.showOpenDialog(app.getStage());
			choosedfile.setText(selectedFile.getName());
		});

		HBox vBox = new HBox(button, choosedfile);
		vBox.setSpacing(10);
		// autre doc
		FileChooser otherfileChooser = new FileChooser();
		Label otherdoc = new Label("Autre(s) Doc(s)");
		otherdoc.setFont(Font.font("Arial", 14));
		otherdoc.setStyle("-fx-text-fill: #000000");

		Button otherdocbutton = new Button("Autre(s) Doc(s)");

		otherdocbutton.setEffect(new DropShadow(10, Color.BLACK));
		TextField otherchoosedfile = new TextField();
		otherchoosedfile.setPromptText("Autre document(s)");
		otherdocbutton.setStyle("-fx-max-width:130px; -fx-min-width: 130px;");
		otherchoosedfile.setOpacity(1);
		otherfileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Pdf files", "*.pdf"));
		otherdocbutton.setOnAction(e -> {

			List<File> bufferList = otherfileChooser.showOpenMultipleDialog(app.getStage());
			selectedFileList.addAll(bufferList);

			if (selectedFileList != null) {
				for (int i = 0; i < selectedFileList.size(); i++) {
					otherchoosedfile.setText(otherchoosedfile.getText() + " " + selectedFileList.get(i).getName());
				}

			}

		});

		HBox hboxBox = new HBox(otherdocbutton, otherchoosedfile);
		hboxBox.setSpacing(10);
		FileChooser imgfileChooser = new FileChooser();
		Button imgdocbutton = new Button("Photo(s)");

		imgdocbutton.setEffect(new DropShadow(10, Color.BLACK));
		imgdocbutton.setStyle("-fx-max-width:130px; -fx-min-width: 130px;");
		TextField imgchoosedfile = new TextField();
		imgchoosedfile.setPromptText("Image");

		imgchoosedfile.setOpacity(1);
		imgfileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
		imgdocbutton.setOnAction(e -> {

			if (selectedimgFileList != null) {
				List<File> bufferList = imgfileChooser.showOpenMultipleDialog(app.getStage());
				selectedimgFileList.addAll(bufferList);
			} else {

				selectedimgFileList = imgfileChooser.showOpenMultipleDialog(app.getStage());
			}

			for (int i = 0; i < selectedimgFileList.size(); i++) {
				imgchoosedfile.setText(imgchoosedfile.getText() + " " + selectedimgFileList.get(i).getName());
			}

		});

		HBox himgboxBox = new HBox(imgdocbutton, imgchoosedfile);
		himgboxBox.setSpacing(10);

/////////////////////////////////////////////////////////////////////////////END FILE INPUT //////////////////////////////////////////////////////////////////////////
		reg3.getChildren().addAll(reg3Field, reg3numField);
		VBox reg3vbox = new VBox();
		reg3vbox.getChildren().addAll(reg3Label, reg3, datereg3Field);
		checkers.setStyle("-fx-max-width:130px; -fx-min-width: 130px;");
		checkbut.setSpacing(10);
		if (verifpage)
			checkersVbox.getChildren().addAll(datedestBox, checkbut, vBox, hboxBox, himgboxBox);
		else
			checkersVbox.getChildren().addAll(datedestBox, checkers, vBox, hboxBox, himgboxBox);
		paiementVbox.getChildren().addAll(paBox, fraisBox, cdBox, soldeBox);

		payedVbox.getChildren().addAll(reg1vbox, reg2vbox, reg3vbox);

		paymentdoc.getChildren().addAll(checkersVbox, paiementVbox, payedVbox);

		/////////////////////////////// ENERGY BOX STAY LAST INPUT
		/////////////////////////////// ///////////////////////////////////////////////////
		hide.setOnAction(e -> {

			paiementVbox.setVisible(!paiementVbox.isVisible());
			payedVbox.setVisible(!payedVbox.isVisible());

		});
		if (verifpage) {
			paiementVbox.setVisible(false);
			payedVbox.setVisible(false);
		}
		// type d'énergie
		VBox energyBox = new VBox();
		Label energyLabel = new Label("Energie :");
		energyLabel.setFont(Font.font("Arial", 14));
		energyLabel.setStyle("-fx-text-fill: #000000");
		ChoiceBox<String> energyhoiceBox = new ChoiceBox<String>();
		// Query selection des energies CG
		String query = " SELECT * FROM geode_bd.energytable";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn;
			conn = DriverManager.getConnection(url, user, passwd);

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				energyhoiceBox.getItems().add(rs.getString("EnergyType"));
			}
			if (verifpage) {

				submitButton.setText("Valider la modification");
				String vehiQuery = "  SELECT * FROM geode_bd.vehicule WHERE immatriculation = '" + immatch + "'";

				ResultSet rs1 = stmt.executeQuery(vehiQuery);
				rs1.next();

				idvehi = rs1.getInt("idvehicule");
				String vehiinforemoveQuery = "  SELECT * FROM geode_bd.inforemove WHERE inforemoveidVehi = '" + idvehi
						+ "'";
				System.out.println(vehiQuery);
				System.out.println(vehiinforemoveQuery);
				marqueField.setText(rs1.getString("marque"));
				typeField.setText(rs1.getString("type"));
				numSerField.setText(rs1.getString("numeroSerie"));
				hpField.setText(String.valueOf(rs1.getInt("puissance")));
				klmsField.setText(String.valueOf(rs1.getInt("klms")));
				immatField.setText(rs1.getString("immatriculation"));
				couleurField.setText(rs1.getString("couleur"));

				destchoiceBox.setValue(rs1.getString("destination"));
				energyhoiceBox.setValue(rs1.getString("energie"));
				carroschoiceBox.setValue(rs1.getString("carrosserie"));

				Image imgget = picin.GetBlob(idvehi);
				if (imgget != null) {
					System.out.println("test");
					carroschoiceBox.getItems().add("Constat Actuel");
					carroschoiceBox.setOnAction((event) -> {
						graphicsContext.clearRect(0, 400, canvas.getWidth(), canvas.getHeight());
						if (carroschoiceBox.getValue() == "Solo")
							graphicsContext.drawImage(image, 0, 400);
						if (carroschoiceBox.getValue() == "deux portes")
							graphicsContext.drawImage(image2, 0, 400);
						if (carroschoiceBox.getValue() == "4 portes")
							graphicsContext.drawImage(image, 0, 400);
						if (carroschoiceBox.getValue() == "Constat Actuel")
							graphicsContext.drawImage(imgget, 0, 400);
					});
					carroschoiceBox.setValue("Constat Actuel");

				}

				if (rs1.getDate("miseEnCircul") != null)
					mecdateField.setValue(rs1.getDate("miseEnCircul").toLocalDate());
				if (rs1.getDate("certifActuel") != null)
					cadateField.setValue(rs1.getDate("certifActuel").toLocalDate());

				ResultSet rs2 = stmt.executeQuery(vehiinforemoveQuery);
				while (rs2.next()) {

					prefField.setText(rs2.getString("infoRemovePref"));
					depField.setText(rs2.getString("infoRemoveDep"));
					cityField.setText(rs2.getString("infoRemoveVille"));
					addresseField.setText(rs2.getString("infoRemoveLieu"));
					causeField.setText(rs2.getString("infoRemoveCause"));
					orienlchoiceBox.setValue(rs2.getString("infoRemoveOrigin"));
					cpField.setText(String.valueOf(rs2.getInt("infoRemoveCP")));
					if (rs2.getDate("infoRemoveDateRem") != null) {
						datenelField.setValue(rs2.getDate("infoRemoveDateRem").toLocalDate());
					}
				}
				String vehiinfopaymentQuery = "  SELECT * FROM geode_bd.paymentvehi WHERE idvehicule = '" + idvehi
						+ "'";
				ResultSet rs3 = stmt.executeQuery(vehiinfopaymentQuery);
				boolean boolcheckbbox = false;
				while (rs3.next()) {
					if (rs3.getInt("paymentcg") == 1)
						boolcheckbbox = true;
					cgcheck.setSelected(boolcheckbbox);

					if (rs3.getInt("paymentrsv") == 1)
						boolcheckbbox = true;
					else
						boolcheckbbox = false;

					rsvcheck.setSelected(boolcheckbbox);

					paField.setText(rs3.getFloat("paymentbuycost") + "");
					fraisField.setText(rs3.getFloat("paymentaddcost") + "");
					cdField.setText(rs3.getFloat("paymentdepcost") + "");
					reg1Field.setText(rs3.getFloat("1stcheque") + "");
					reg1numField.setText(rs3.getString("1stchequenum"));

					reg2Field.setText(rs3.getFloat("2ndcheque") + "");
					reg2numField.setText(rs3.getString("2ndchequenum"));

					reg3Field.setText(rs3.getFloat("3rdcheque") + "");
					reg3numField.setText(rs3.getString("3rdchequenum"));

					if (rs3.getDate("paymentdatedest") != null)
						datedestField.setValue(rs3.getDate("paymentdatedest").toLocalDate());

					if (rs3.getDate("1stchequedate") != null)
						datereg1Field.setValue(rs3.getDate("1stchequedate").toLocalDate());

					if (rs3.getDate("2ndchequedate") != null)
						datereg2Field.setValue(rs3.getDate("2ndchequedate").toLocalDate());

					if (rs3.getDate("3rdchequedate") != null)
						datereg3Field.setValue(rs3.getDate("3rdchequedate").toLocalDate());
					if (rs3.getDate("dateCessation") != null)
						datecessField.setValue(rs3.getDate("dateCessation").toLocalDate());
				}

			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();

			System.exit(0);
		}

		// Input dans la fenetre
		energyBox.getChildren().addAll(energyLabel, energyhoiceBox);
		vehistathbox.getChildren().addAll(energyBox, hpBox, klmsBox);
		infodoc.setAlignment(Pos.BASELINE_CENTER);
		vehiinputhbox.setAlignment(Pos.BASELINE_CENTER);
		paymentdoc.setAlignment(Pos.BASELINE_CENTER);
		btnBox.setAlignment(Pos.TOP_CENTER);
		btnBox.setPadding(new Insets(100, 0, 0, 0));
		btnBox.getStylesheets().add("/styles.css");
		submitButton.setStyle("-fx-min-width: 200px; -fx-min-height: 50px; -fx-alignment: CENTER;");

		generalBox.getChildren().addAll(vehiinputhbox, infodoc, paymentdoc);
		grid.add(generalBox, 1, 2);

		///////////////////////////////////////////////// ENERGY BOX END
		///////////////////////////////////////////////// ///////////////////////////////////////////

		submitButton.setOnAction(e -> {
			String marque = marqueField.getText();
			String type = typeField.getText();
			String immat = immatField.getText();
			String carros = carroschoiceBox.getValue();
			String numSer = numSerField.getText();
			String energy = energyhoiceBox.getValue();
			String dest = destchoiceBox.getValue();
			String pref = prefField.getText();
			String dep = depField.getText();
			String city = cityField.getText();
			String address = addresseField.getText();
			String cause = causeField.getText();
			String origin = orienlchoiceBox.getValue();

			String num1c = reg1numField.getText();
			String num2c = reg2numField.getText();
			String num3c = reg3numField.getText();

			boolean cg = cgcheck.isSelected();
			boolean rsv = rsvcheck.isSelected();

			// Alert for necessery data
			if (((immat == "" | marque == "" | type == "") && utilisateur.getUserrole() != 2)
					| ((immat == "" | marque == "" | type == "" | numSer == "") && utilisateur.getUserrole() == 2)) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Données manquantes");
				alert.setHeaderText(null);
				if (utilisateur.getUserrole() != 2)
					alert.setContentText("Veuillez renseigner l'immatriculation, la marque et le type");
				else
					alert.setContentText(
							"Veuillez renseigner l'immatriculation, la marque, le type et le numéro de série");
				alert.showAndWait();

			} else {

				// Forced input for date
				LocalDate temp = LocalDate.of(1970, 1, 1);
				java.sql.Date mcdate = java.sql.Date.valueOf(temp);
				java.sql.Date cadate = java.sql.Date.valueOf(temp);
				java.sql.Date daterem = java.sql.Date.valueOf(temp);
				java.sql.Date datedest = java.sql.Date.valueOf(temp);

				java.sql.Date datec1 = java.sql.Date.valueOf(temp);

				java.sql.Date datec2 = java.sql.Date.valueOf(temp);

				java.sql.Date datec3 = java.sql.Date.valueOf(temp);
				java.sql.Date datecess = java.sql.Date.valueOf(temp);

				// Normalize data
				if (hpField.getText() != "") {
					hp = Integer.parseInt(hpField.getText());

				}
				if (klmsField.getText() != "") {
					klms = Integer.parseInt(klmsField.getText());
				}

				String couleur = couleurField.getText();

				if (mecdateField.getValue() != null) {
					mcdate = java.sql.Date.valueOf(mecdateField.getValue());
				}
				if (cadateField.getValue() != null) {
					cadate = java.sql.Date.valueOf(cadateField.getValue());
				}
				if (datenelField.getValue() != null) {
					daterem = java.sql.Date.valueOf(datenelField.getValue());
				}
				if (datedestField.getValue() != null) {
					datedest = java.sql.Date.valueOf(datedestField.getValue());
				}
				if (datereg1Field.getValue() != null) {
					datec1 = java.sql.Date.valueOf(datereg1Field.getValue());
				}
				if (datereg2Field.getValue() != null) {
					datec2 = java.sql.Date.valueOf(datereg2Field.getValue());
				}
				if (datereg3Field.getValue() != null) {
					datec3 = java.sql.Date.valueOf(datereg3Field.getValue());
				}
				if (datecessField.getValue() != null) {
					datecess = java.sql.Date.valueOf(datecessField.getValue());
				}
				if (cpField.getText() != "") {
					cp = Integer.parseInt(cpField.getText());
				}
				if (reg1Field.getText() != "") {
					c1prix = Float.parseFloat(reg1Field.getText());
				}
				if (reg2Field.getText() != "") {
					c2prix = Float.parseFloat(reg2Field.getText());
				}
				if (reg3Field.getText() != "") {
					c3prix = Float.parseFloat(reg3Field.getText());
				}
				if (paField.getText() != "") {
					pa = Float.parseFloat(paField.getText());
				}
				if (cdField.getText() != "") {
					cd = Float.parseFloat(cdField.getText());
				}
				if (fraisField.getText() != "") {
					frais = Float.parseFloat(fraisField.getText());
				}
				if (soldeField.getText() != "") {
					soldeprix = Float.parseFloat(soldeField.getText());
				}
				int hascg = 0;
				if (choosedfile.getText() != "")
					hascg = 1;
				if (!verifpage) {
					ResultSet rsvef = datataker.GetVehiculeByImmatandOutDate(immat);
					try {
						if (!rsvef.next()) {

							dataInput datainput = new dataInput();

							datainput.AddVehicule(marque, type, immat, carros, numSer, energy, hp, klms, couleur,
									mcdate, cadate, dest, hascg);

							ResultSet rs = datataker.GetVehiculeByImmat(immat);

							try {
								rs.next();

								datainput.AddInfoToRemoval(origin, cause, address, cp, city, dep, pref, daterem,
										rs.getInt("idvehicule"));
								datainput.AddInfoToPay(datedest, cg, rsv, pa, frais, cd, soldeprix, c1prix, c2prix,
										c3prix, datec1, datec2, datec3, num1c, num2c, num3c, rs.getInt("idvehicule"),
										datecess);
								if (selectedFile != null) {
									System.out.println(selectedFile.getName());
									filein.AddCG(rs.getInt("idvehicule"), selectedFile);
								}
								if (selectedFileList != null) {
									filein.Addotherdoc(rs.getInt("idvehicule"), selectedFileList);
								}
								if (selectedimgFileList != null) {
									filein.Addpicdoc(rs.getInt("idvehicule"), selectedimgFileList);
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							String filename = "M:Cst\\" + String.valueOf(idvehi) + "Cst.PNG";

							File file = new File(filename);
							System.out.println("is file null ? " + file);
							if (file != null) {
								try {
									WritableImage writableImage = new WritableImage(400, 400);
									canvas.snapshot(null, writableImage);
									RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
									ImageIO.write(renderedImage, "png", file);
									System.out.println();
									picin.InputBlob(file.getCanonicalPath().replace("\\", "\\\\"), idvehi);
								} catch (IOException ex) {
									ex.printStackTrace();

								}
							}
							HBox Validationbox = new HBox();
							Validationbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
									+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;"
									+ "-fx-border-color: #3b703d;"
									+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

							Label ValidationLabel = new Label(
									"Le véhicule immatriculé " + immat + " a bien été enregistré !");
							ValidationLabel.setFont(Font.font("Arial", 20));
							ValidationLabel.setStyle("-fx-text-fill: #000000");
							Validationbox.setPadding(new Insets(20));
							Validationbox.setAlignment(Pos.BASELINE_CENTER);
							Validationbox.getChildren().addAll(ValidationLabel);
							grid.add(Validationbox, 1, 1);

						} else {
							HBox Deniedbox = new HBox();
							Deniedbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
									+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;"
									+ "-fx-border-color: #ed7777;"
									+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

							Label DeniedLabel = new Label(
									"Le véhicule immatriculé " + immat + " est déjà dans le parc");
							DeniedLabel.setFont(Font.font("Arial", 20));
							DeniedLabel.setStyle("-fx-text-fill: #000000");
							Deniedbox.setPadding(new Insets(20));
							Deniedbox.setAlignment(Pos.BASELINE_CENTER);
							Deniedbox.getChildren().addAll(DeniedLabel);
							grid.add(Deniedbox, 1, 1);
						}
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {

					dataInput datainput = new dataInput();
					datainput.UpdateVehicule(idvehi, marque, type, immat, carros, numSer, energy, hp, klms, couleur,
							mcdate, cadate, dest, hascg);
					datainput.UpdateInfoToRemoval(origin, cause, address, cp, city, dep, pref, daterem, idvehi);
					datainput.AddInfoToPay(datedest, cg, rsv, pa, frais, cd, soldeprix, c1prix, c2prix, c3prix, datec1,
							datec2, datec3, num1c, num2c, num3c, idvehi, datecess);

					HBox Validationbox = new HBox();
					Validationbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
							+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;" + "-fx-border-color: #3b703d;"
							+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
					if (selectedFile != null) {
						System.out.println(selectedFile.getName());
						filein.AddCG(idvehi, selectedFile);
					}
					if (selectedFileList != null) {
						filein.Addotherdoc(idvehi, selectedFileList);
					}
					if (selectedimgFileList != null) {
						filein.Addpicdoc(idvehi, selectedimgFileList);
					}
					String filename = String.valueOf(idvehi) + "Cst.PNG";
					FileChooser savefile = new FileChooser();
					FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files", "*.PNG");
					savefile.setTitle("Save File");
					savefile.getExtensionFilters().add(extFilter);
					File file = new File(filename);
					System.out.println("is file null ? " + file);
					if (file != null) {
						try {
							WritableImage writableImage = new WritableImage(400, 400);
							canvas.snapshot(null, writableImage);
							RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
							ImageIO.write(renderedImage, "png", file);
							System.out.println();
							picin.InputBlob(file.getCanonicalPath().replace("\\", "\\\\"), idvehi);
						} catch (IOException ex) {
							ex.printStackTrace();
							System.out.println("Error!");
						}
					}
					Label ValidationLabel = new Label("Le véhicule immatriculé " + immat + " a bien été modifié !");
					// ValidationLabel.setTextAlignment(Pos.CENTER);
					ValidationLabel.setFont(Font.font("Arial", 20));
					ValidationLabel.setStyle("-fx-text-fill: #000000");
					Validationbox.setPadding(new Insets(20));
					Validationbox.setAlignment(Pos.BASELINE_CENTER);
					Validationbox.getChildren().addAll(ValidationLabel);
					grid.add(Validationbox, 1, 1);
				}

			}

		});
		// canvasbox.getChildren().addAll(btnBox);
		grid.add(btnBox, 2, 2);
		GridPane.setHalignment(btnBox, HPos.CENTER);
		GridPane.setValignment(btnBox, VPos.TOP);
		grid.add(canvasbox, 2, 2);
		ScrollPane stackPane = new ScrollPane();
		stackPane.setContent(grid);
		stackPane.setFitToWidth(true);
		this.setCenter(stackPane);

		return new Scene(this, 600, 400);
	}

	private void initDraw(GraphicsContext gc) {
		double canvasWidth = gc.getCanvas().getWidth();
		double canvasHeight = gc.getCanvas().getHeight();

		gc.setFill(Color.LIGHTGRAY);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(5);

		gc.fill();
		gc.strokeRect(0, // x of the upper left corner
				0, // y of the upper left corner
				canvasWidth, // width of the rectangle
				canvasHeight); // height of the rectangle

		gc.setFill(Color.RED);
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(1);

	}
}
