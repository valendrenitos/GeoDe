package front;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import back.CalendarController;
import back.DataTaker;
import back.EventClass;
import back.InvoiceTaker;
import back.User;
import back.vehicule;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class HomePage extends BorderPane {

	private App app;
	public List<EventClass> allEvent;
	private User utilisateur;

	public HomePage(App app, User utilisateur) {
		this.app = app;
		this.utilisateur = utilisateur;
	}

	public Scene createHomePage() {
		CalendarController calcon = new CalendarController();
		Separator sep = new Separator(Orientation.HORIZONTAL);
		calcon.GetAllUpcomingEvent(this);
		StockPage stock = new StockPage(app, utilisateur);
		ParamButton pambut = new ParamButton(app);
		this.setBottom(pambut);

		VBox topScreen = new VBox();
		Label Title = new Label();

		Title.setText("Accueil");
		Title.getStylesheets().add("/styles.css");
		Title.getStyleClass().add("Title");

		TopNavBar topnav = new TopNavBar(app, "HomePage", utilisateur);
		// topScreen.getChildren().addAll(topnav, Title);
		this.setTop(topnav);

		DataTaker datataker = new DataTaker();
		InvoiceTaker invoicetaker = new InvoiceTaker();
		// Setup plan
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setAlignment(Pos.CENTER);
		grid.add(Title, 1, 0);
		HBox bighbox = new HBox();

		bighbox.setSpacing(40);
		VBox bigCardBox = new VBox();

		bigCardBox.setSpacing(40);
		// Resume du mois

		VBox resumBox = new VBox();
		resumBox.setPadding(new Insets(40));
		resumBox.setSpacing(40);
		resumBox.getStylesheets().add("/styles.css");
		resumBox.getStyleClass().add("main-box");
		Label resumLabel = new Label("Resumé du mois");
		HBox cardbox = new HBox();
		cardbox.setSpacing(40);

		// card total stock
		VBox stockCard = new VBox();
		stockCard.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
				+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
		stockCard.setPadding(new Insets(10));
		stockCard.setAlignment(Pos.CENTER);
		Image userImg = new Image(getClass().getResource("/voiture(2).png").toExternalForm());
		ImageView imageView = new ImageView(userImg);
		imageView.setFitWidth(64);
		imageView.setPreserveRatio(true);

		int stocklength = datataker.stockVehiculeLength();
		Label totalStock = new Label("" + stocklength + "");
		Label stockLabel = new Label("en stock");
		stockCard.getChildren().addAll(imageView, totalStock, stockLabel);

		// entry card
		VBox entryCard = new VBox();
		entryCard.setPadding(new Insets(10));
		entryCard.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
				+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
		entryCard.setAlignment(Pos.CENTER);
		Image entryImg = new Image(getClass().getResource("/entrer(1).png").toExternalForm());
		ImageView entryView = new ImageView(entryImg);
		entryView.setFitWidth(64);
		entryView.setPreserveRatio(true);

		int entrylength = datataker.dateTaker();

		Label entryStock = new Label("" + entrylength + "");
		Label entryLabel = new Label("entrées");
		entryCard.getChildren().addAll(entryView, entryStock, entryLabel);

		// leave card
		VBox leftCard = new VBox();
		leftCard.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
				+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
		leftCard.setPadding(new Insets(10));
		leftCard.setAlignment(Pos.CENTER);
		Image leftImg = new Image(getClass().getResource("/se-deconnecter(1).png").toExternalForm());
		ImageView leftView = new ImageView(leftImg);
		leftView.setFitWidth(64);
		leftView.setPreserveRatio(true);

		int leftlength = datataker.leftTaker();

		Label leftStock = new Label("" + leftlength + "");
		Label leftLabel = new Label("sorties");
		leftCard.getChildren().addAll(leftView, leftStock, leftLabel);

		cardbox.getChildren().addAll(stockCard, entryCard, leftCard);
		resumBox.getChildren().addAll(resumLabel, sep, cardbox);

		// Action à venir
		Separator sep2 = new Separator(Orientation.HORIZONTAL);
		Separator sep3 = new Separator(Orientation.HORIZONTAL);
		VBox actionBox = new VBox();
		actionBox.setPadding(new Insets(40));
		actionBox.setSpacing(40);
		actionBox.getStylesheets().add("/styles.css");
		actionBox.getStyleClass().add("main-box");
		Label actionLabel = new Label("Actions à valider et Rappels");
		HBox actionCardbox = new HBox();
		cardbox.setSpacing(40);

		// Evenement et rappel

		VBox eventBox = new VBox();
		eventBox.setPadding(new Insets(40));
		eventBox.setSpacing(40);
		eventBox.getStylesheets().add("/styles.css");
		eventBox.getStyleClass().add("main-box");
		Label eventLabel = new Label("Evenement");
		HBox eventCardbox = new HBox();
		eventBox.getChildren().addAll(eventLabel, sep3);
		for (int j = 0; j < allEvent.size(); j++) {

			if (j < 6) {

				HBox card = new HBox();
				Separator sepdate = new Separator(Orientation.VERTICAL);
				card.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
						+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
						+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
				card.setPadding(new Insets(10));
				card.setAlignment(Pos.CENTER);
				card.setSpacing(10);
				card.getChildren().addAll(new Text(String.valueOf(allEvent.get(j).getDate())), sepdate,
						new Text((allEvent.get(j).getName())));
				eventBox.getChildren().addAll(card);
			}

		}

		cardbox.setSpacing(40);

		// card total stock
		VBox rappelCard = new VBox();
		rappelCard.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
				+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
		rappelCard.setPadding(new Insets(10));
		rappelCard.setAlignment(Pos.CENTER);

		imageView.setFitWidth(64);
		imageView.setPreserveRatio(true);

		int rappellength = datataker.datereminder3Taker();
		Label rappelStock = new Label("" + rappellength + "");
		Label rappelLabel = new Label("en Stock");
		Label rappelLabel2 = new Label("depuis plus de 3 mois");
		rappelCard.getChildren().addAll(rappelStock, rappelLabel, rappelLabel2);
		// card total stock
		VBox invoiceCard = new VBox();
		invoiceCard.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
				+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
		invoiceCard.setPadding(new Insets(10));
		invoiceCard.setAlignment(Pos.CENTER);

		imageView.setFitWidth(64);
		imageView.setPreserveRatio(true);

		int invoicelength = invoicetaker.openInvoiceTaker();
		Label invoiceStock = new Label("" + invoicelength + "");
		Label invoiceTextLabel = new Label("Facture(s)");
		Label invoiceTextLabel2 = new Label("Temporaire(s)");
		invoiceCard.getChildren().addAll(invoiceStock, invoiceTextLabel, invoiceTextLabel2);

		VBox actCard = new VBox();
		actCard.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
				+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
		actCard.setPadding(new Insets(10));
		actCard.setAlignment(Pos.CENTER);

		imageView.setFitWidth(64);
		imageView.setPreserveRatio(true);

		int actlength = calcon.GetAllPastEvent();
		Label actStock = new Label("" + actlength + "");
		Label actTextLabel = new Label("Action(s)");
		Label actTextLabel2 = new Label("à valider");
		actCard.getChildren().addAll(actStock, actTextLabel, actTextLabel2);

		VBox cgRappelCard = new VBox();
		cgRappelCard.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
				+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
		cgRappelCard.setPadding(new Insets(10));
		cgRappelCard.setAlignment(Pos.CENTER);

		imageView.setFitWidth(64);
		imageView.setPreserveRatio(true);

		int cglength = datataker.datereminderCG();
		Label cgStock = new Label("" + cglength + "");
		Label cgTextLabel = new Label("Sans CG depuis");

		Label cgTextLabel2 = new Label("plus de 3 mois");
		cgRappelCard.getChildren().addAll(cgStock, cgTextLabel, cgTextLabel2);

		// Card on click
		stockCard.setOnMouseClicked(e -> {
			app.showStockScene();
		});
		leftCard.setOnMouseClicked(e -> {
			app.showCardVehiculeScene(0);
		});
		rappelCard.setOnMouseClicked(e -> {
			app.showCardVehiculeScene(1);
		});
		entryCard.setOnMouseClicked(e -> {
			app.showCardVehiculeScene(2);
		});
		invoiceCard.setOnMouseClicked(e -> {
			app.showInvoiceTableScene("", "", "Temporaire", null, null);
		});
		actCard.setOnMouseClicked(e -> {
			app.showCalendarRappelScene();
		});

		cgRappelCard.setOnMouseClicked(e -> {
			app.showCardVehiculeScene(3);
		});

		if (utilisateur.getUserrole() == 2) {
			eventCardbox.getChildren().addAll(rappelCard, cgRappelCard);
		} else
			eventCardbox.getChildren().addAll(rappelCard, invoiceCard, actCard, cgRappelCard);
		eventCardbox.setSpacing(20);
		actionBox.getChildren().addAll(actionLabel, sep2, eventCardbox);
		bigCardBox.getChildren().addAll(resumBox, actionBox);
		bighbox.getChildren().addAll(bigCardBox, eventBox);
		grid.add(bighbox, 1, 1);

		ScrollPane stackPane = new ScrollPane();
		stackPane.setContent(grid);
		stackPane.setFitToWidth(true);
		this.setCenter(stackPane);

		return new Scene(this);
	}

}
