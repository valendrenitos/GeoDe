package front;

import back.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class TopNavBar extends BorderPane {

	public TopNavBar(App app, String scene, User user) {
		setStyle("-fx-background-color:#D9D9D9");

		HBox navBar = new HBox();

		navBar.setPadding(new Insets(20));
		navBar.setSpacing(20);

		// home button
		Button homeButton = new Button("Accueil");
		// homeButton.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; "
		/// + "-fx-background-radius: 20; -fx-padding: 10 20;");
		homeButton.setEffect(new DropShadow(10, Color.BLACK));
		homeButton.getStylesheets().add("/styles.css");
		homeButton.getStyleClass().add("searchBtn ");
		homeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				app.showHomeScene(user);
			}

		});

		// facture
		Button invoiceButton = new Button("Facture");
		invoiceButton.getStylesheets().add("/styles.css");
		invoiceButton.getStyleClass().add("searchBtn ");
		invoiceButton.setEffect(new DropShadow(10, Color.BLACK));

		invoiceButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				app.showInvoiceTableScene();
			}

		});

		// Vehicule
		Button vehiculeButton = new Button("Véhicule");
		vehiculeButton.getStylesheets().add("/styles.css");
		vehiculeButton.getStyleClass().add("searchBtn ");
		vehiculeButton.setEffect(new DropShadow(10, Color.BLACK));

		vehiculeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				app.showAllVehiculeScene();
			}

		});

		// Calendrier
		Button calendarButton = new Button("Calendrier");
		calendarButton.getStylesheets().add("/styles.css");
		calendarButton.getStyleClass().add("searchBtn ");
		calendarButton.setEffect(new DropShadow(10, Color.BLACK));

		calendarButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				app.showCalendarScene();
			}

		});

		// stock
		Button stockButton = new Button("Stock");
		stockButton.getStylesheets().add("/styles.css");
		stockButton.getStyleClass().add("searchBtn ");
		stockButton.setEffect(new DropShadow(10, Color.BLACK));

		stockButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				app.showStockScene();
			}

		});

		// Stats
		Button alButton = new Button("Statistiques");
		alButton.getStylesheets().add("/styles.css");
		alButton.getStyleClass().add("searchBtn ");
		alButton.setEffect(new DropShadow(10, Color.BLACK));

		alButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				app.createAnalitycsPage();
			}

		});

		// Stats

		Button logout = new Button("Déconnexion");
		logout.getStylesheets().add("/styles.css");
		logout.getStyleClass().add("searchBtn ");
		logout.setEffect(new DropShadow(10, Color.BLACK));
		HBox logbtn = new HBox(logout);
		logbtn.setAlignment(Pos.BASELINE_LEFT);
		logbtn.setPadding(new Insets(20));
		logbtn.setSpacing(20);

		logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				app.showLoginScene();
			}

		});

		if ("HomePage".equals(scene)) {
			homeButton.getStyleClass().add("selected-button");
			invoiceButton.getStyleClass().remove("selected-button");
			vehiculeButton.getStyleClass().remove("selected-button");
			calendarButton.getStyleClass().remove("selected-button");
			stockButton.getStyleClass().remove("selected-button");
			alButton.getStyleClass().remove("selected-button");
		} else if ("InvoicePage".equals(scene)) {
			homeButton.getStyleClass().remove("selected-button");
			invoiceButton.getStyleClass().add("selected-button");
			vehiculeButton.getStyleClass().remove("selected-button");
			calendarButton.getStyleClass().remove("selected-button");
			stockButton.getStyleClass().remove("selected-button");
			alButton.getStyleClass().remove("selected-button");
		} else if ("StockPage".equals(scene)) {
			homeButton.getStyleClass().remove("selected-button");
			invoiceButton.getStyleClass().remove("selected-button");
			vehiculeButton.getStyleClass().remove("selected-button");
			calendarButton.getStyleClass().remove("selected-button");
			stockButton.getStyleClass().add("selected-button");
			alButton.getStyleClass().remove("selected-button");
		} else if ("AllVehiScene".equals(scene)) {
			homeButton.getStyleClass().remove("selected-button");
			invoiceButton.getStyleClass().remove("selected-button");
			vehiculeButton.getStyleClass().add("selected-button");
			calendarButton.getStyleClass().remove("selected-button");
			stockButton.getStyleClass().remove("selected-button");
			alButton.getStyleClass().remove("selected-button");
		} else if ("CalendarApp".equals(scene)) {
			homeButton.getStyleClass().remove("selected-button");
			invoiceButton.getStyleClass().remove("selected-button");
			vehiculeButton.getStyleClass().remove("selected-button");
			calendarButton.getStyleClass().add("selected-button");
			stockButton.getStyleClass().remove("selected-button");
			alButton.getStyleClass().remove("selected-button");
		} else if ("Analitycs".equals(scene)) {
			homeButton.getStyleClass().remove("selected-button");
			invoiceButton.getStyleClass().remove("selected-button");
			vehiculeButton.getStyleClass().remove("selected-button");
			calendarButton.getStyleClass().remove("selected-button");
			stockButton.getStyleClass().remove("selected-button");
			alButton.getStyleClass().add("selected-button");
		}

		if (user.getUserrole() == 2) {
			navBar.getChildren().addAll(homeButton, vehiculeButton, calendarButton, stockButton);

		} else if (user.getUserrole() == 1) {
			navBar.getChildren().addAll(homeButton, invoiceButton, vehiculeButton, calendarButton, stockButton);
		} else
			navBar.getChildren().addAll(homeButton, invoiceButton, vehiculeButton, calendarButton, stockButton,
					alButton);

		navBar.setAlignment(Pos.BASELINE_CENTER);

		navBar.getStylesheets().add("/styles.css");
		setCenter(navBar);
		setRight(logbtn);
	}
}
