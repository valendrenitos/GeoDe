package front;

import java.awt.Dimension;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.UnaryOperator;

import back.CalendarController;

import back.EventClass;
import back.User;
import back.dataInput;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class CalendarApp extends BorderPane {
	private App app;
	Date cldate;
	public List<EventClass> allEvent;
	public List<EventClass> allRappelEvent;
	Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	public int validevent = 0;
private User utilisateur;
	public CalendarApp(App app, User utilisateur) {
		this.app = app;
		this.utilisateur=utilisateur;
	}

	public Scene createCalendar() {
		// "-fx-background-color: #C4C0C0;"

		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth() / 1.5;
		double height = 10 * screenSize.getHeight() / 12;

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
					} else if (t.getText().matches("[^0-9]")) {
						t.setText("");
					}
				}

				return t;
			}
		};

		VBox topScreen = new VBox();
		Label Title = new Label();

		Title.setText("Calendrier");
		Title.getStylesheets().add("/styles.css");
		Title.getStyleClass().add("Title");

		TopNavBar topnav = new TopNavBar(app, "CalendarApp", utilisateur);
		topScreen.getChildren().addAll(topnav);
		this.setTop(topScreen);

		ParamButton pambut = new ParamButton(app);	
		this.setBottom(pambut);
		
		CalendarController calcon = new CalendarController();
		// General Grid for more organisation
		GridPane BIGGRID = new GridPane();
		BIGGRID.setAlignment(Pos.CENTER);
		calcon.GetAllEvent(this);
		BIGGRID.add(Title, 1, 0);
		HBox bighbox = new HBox();
		bighbox.setMinWidth(width);
		// bighbox.setMaxWidth(width);
		bighbox.getStylesheets().add("/styles.css");
		bighbox.getStyleClass().add("main-box");
		bighbox.setSpacing(40);
		// Region filler = new Region(); HBox.setHgrow(filler, Priority.ALWAYS);
		bighbox.setPadding(new Insets(60));
		bighbox.setAlignment(Pos.CENTER);
		// bighbox. fillHeightProperty();
		// bighbox.setMaxHeight(screenSize.getHeight() / 2);
		bighbox.setMinHeight(screenSize.getHeight() / 2);
		// Calendar grid
		GridPane grid = new GridPane();

		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20));
		grid.setAlignment(Pos.CENTER);
		grid.setStyle(
				"-fx-background-color: #e6dfdf; " + "-fx-border-radius : 15px;" + "-fx-background-radius : 15px;");
		
		grid.setMinHeight(bighbox.getMinHeight() / 1.01);
		bighbox.getChildren().addAll(grid);
		// Hbox for calendar, inspection and modify

		// Set card for inspection of day
		VBox bigCardBox = new VBox();
		bigCardBox.setMinWidth(bighbox.getMinWidth() / 4);
		bigCardBox.setMinWidth(bighbox.getMaxWidth() / 3.5);
		bigCardBox.setMaxHeight(bighbox.getMaxHeight() / 1.01);
		bigCardBox.setMinHeight(bighbox.getMinHeight() / 1.01);

		bigCardBox.setStyle(
				"-fx-background-color: #e6dfdf; " + "-fx-border-radius : 15px;" + "-fx-background-radius : 15px;");
		bigCardBox.setPadding(new Insets(10));
		bigCardBox.setAlignment(Pos.CENTER);
		bigCardBox.setSpacing(40);

		// detect todays day

		// Modify card Box
		VBox CorrectCardBox = new VBox();
		CorrectCardBox.setMinWidth(bighbox.getMinWidth() / 3);
		CorrectCardBox.setStyle(
				"-fx-background-color: #e6dfdf; " + "-fx-border-radius : 15px;" + "-fx-background-radius : 15px;");
		CorrectCardBox.setPadding(new Insets(20));
		CorrectCardBox.setAlignment(Pos.CENTER);
		CorrectCardBox.setSpacing(40);
		Text eventid = new Text();
		Label evNameC = new Label("Nom de l'évènement :");
		TextField eventNameC = new TextField();
		Label evDateC = new Label("Date :");
		DatePicker eventDateC = new DatePicker();
		Label VehiculeidC = new Label("Numéro du véhicule :");
		TextField idvehiC = new TextField();
		idvehiC.setTextFormatter(new TextFormatter<>(filter));
		Button Modifier = new Button("Modifier");

		Modifier.setEffect(new DropShadow(10, Color.BLACK));
		Button Suppr = new Button("Supprimer l'évènement");

		CorrectCardBox.getChildren().addAll(evNameC, eventNameC, evDateC, eventDateC, VehiculeidC, idvehiC, Modifier,
				Suppr);

		// Set today instance
		Date today = new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(today);

		Calendar cl1 = Calendar.getInstance();
		Calendar cl8 = Calendar.getInstance();
		cl8.setTime(today);
		// Set up text variables for cards
		Text intmonth = new Text();
		Text nameofmonth = new Text();

		////////// DAYS /////////////

		Text lundi = new Text("Lundi");
		GridPane.setHalignment(lundi, HPos.CENTER);
		lundi.setTextAlignment(TextAlignment.CENTER);
		grid.add(lundi, 1, 2);

		Text mardi = new Text("Mardi");
		GridPane.setHalignment(mardi, HPos.CENTER);
		grid.add(mardi, 2, 2);
		Text mercredi = new Text("Mercredi");
		GridPane.setHalignment(mercredi, HPos.CENTER);
		grid.add(mercredi, 3, 2);
		Text jeudi = new Text("Jeudi");
		GridPane.setHalignment(jeudi, HPos.CENTER);
		grid.add(jeudi, 4, 2);
		Text ven = new Text("Vendredi");
		GridPane.setHalignment(ven, HPos.CENTER);
		grid.add(ven, 5, 2);
		Text sam = new Text("Samedi");
		GridPane.setHalignment(sam, HPos.CENTER);
		grid.add(sam, 6, 2);

		Text dim = new Text("Dimanche");
		GridPane.setHalignment(dim, HPos.CENTER);
		grid.add(dim, 7, 2);
		grid.setMinWidth(bighbox.getMinWidth() / 2);

		////// MONTHS//////
		GridPane.setHalignment(nameofmonth, HPos.CENTER);

		grid.add(nameofmonth, 4, 1);

		Button prev = new Button("prev");
		Button next = new Button("next");
		cl.setTime(today);

		// get Previous Month
		prev.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				cl.set(Calendar.DATE, 1);
				int month = cl.get(Calendar.MONTH) - 1;

				cl.set(Calendar.MONTH, month);

				intmonth.setText(String.valueOf(cl.get(Calendar.MONTH)));

			}

		});
		// get Next Month
		next.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				cl.set(Calendar.DATE, 1);
				int month = cl.get(Calendar.MONTH) + 1;

				cl.set(Calendar.MONTH, month);

				intmonth.setText(String.valueOf(cl.get(Calendar.MONTH)));
			}

		});

		grid.add(prev, 1, 1);
		grid.add(next, 7, 1);

		// Generate Calendar
		intmonth.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (grid.getChildren().size() != 10) {
					grid.getChildren().remove(10, grid.getChildren().size());
				}
				cl.set(Calendar.MONTH, Integer.parseInt(newValue));
				String name = "";

				cl1.set(Calendar.MONTH, cl.get(Calendar.MONTH));
				switch (cl.get(Calendar.MONTH)) {

				case 0:
					name = "Janvier";
					break;
				case 1:
					name = "Février";
					break;
				case 2:
					name = "Mars";
					break;
				case 3:
					name = "Avril";

					break;
				case 4:
					name = "Mai";
					break;
				case 5:
					name = "Juin";
					
					break;
				case 6:
					name = "Juillet";
					break;
				case 7:
					name = "Aout";
					break;
				case 8:
					name = "Septembre";
					break;
				case 9:
					name = "Octobre";
					break;
				case 10:
					name = "Novembre";
					break;
				case 11:
					name = "Décembre";
					break;
				}
				nameofmonth.setText(name + " " + String.valueOf(cl.get(Calendar.YEAR)));

				cl1.set(Calendar.DATE, 1);
				int dayOfWeek = cl1.get(Calendar.DAY_OF_WEEK);
				cl1.getActualMaximum(Calendar.DAY_OF_MONTH);
				int lines = 3;

				// card total stock

				int count = 0;
				for (int i = 1; i <= cl1.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
					cl.set(Calendar.DATE, i);

					VBox rappelCard = new VBox();
					rappelCard.setMinHeight(80);
					rappelCard.setMinWidth(80);
					Text dayText = new Text(String.valueOf(i));

					rappelCard.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
							+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
							+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

					rappelCard.setPadding(new Insets(10));
					rappelCard.setAlignment(Pos.CENTER);
					rappelCard.getChildren().addAll(dayText);
					cldate = cl1.getTime();

					for (int j = 0; j < allEvent.size(); j++) {
						if (allEvent.get(j).getDate().compareTo(java.sql.Date
								.valueOf(LocalDate.of(cl1.get(Calendar.YEAR), cl1.get(Calendar.MONTH) + 1, i))) == 0)
							count++;

					}

					if (java.sql.Date.valueOf(LocalDate.of(cl.get(Calendar.YEAR), cl1.get(Calendar.MONTH) + 1, i))
							.compareTo(today) == -1)
						rappelCard.setStyle("-fx-background-color: #d9d4d4 ;" + "-fx-border-radius : 15px;"
								+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;"
								+ "-fx-border-color: #D9D9D9;"
								+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
					if (i == cl8.get(Calendar.DATE) && cl.get(Calendar.MONTH) == cl8.get(Calendar.MONTH)
							&& cl.get(Calendar.YEAR) == cl8.get(Calendar.YEAR)) {
						rappelCard.setStyle("-fx-background-color: #8389cc ;" + "-fx-border-radius : 15px;"
								+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;"
								+ "-fx-border-color: #0a1ac7;"
								+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
					}

					if (count != 0) {
						if (java.sql.Date.valueOf(LocalDate.of(cl1.get(Calendar.YEAR), cl1.get(Calendar.MONTH) + 1, i))
								.compareTo(today) == -1) {
							rappelCard.getChildren().addAll(new Text(String.valueOf(count) + " passé(s)"));

						} else {
							rappelCard.getChildren().addAll(new Text(String.valueOf(count) + " prévu(s)"));

						}
					}

					rappelCard.setOnMouseClicked(e -> {
						if (bighbox.getChildren().size() != 1) {
							bighbox.getChildren().remove(1, bighbox.getChildren().size());
							bigCardBox.getChildren().remove(0, bigCardBox.getChildren().size());
						}

						Label date = new Label(String.valueOf(java.sql.Date.valueOf(LocalDate.of(cl1.get(Calendar.YEAR),
								cl1.get(Calendar.MONTH) + 1, Integer.parseInt(dayText.getText())))));
						bigCardBox.getChildren().addAll(date);
						bigCardBox.setPadding(new Insets(60, 30, 20, 30));
						bigCardBox.setAlignment(Pos.TOP_CENTER);
						int counters = 0;
						for (int j = 0; j < allEvent.size(); j++) {

							if (allEvent.get(j).getDate()
									.compareTo(java.sql.Date.valueOf(LocalDate.of(cl1.get(Calendar.YEAR),
											cl1.get(Calendar.MONTH) + 1, Integer.parseInt(dayText.getText())))) == 0) {
								counters++;
								VBox card = new VBox();

								card.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
										+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;"
										+ "-fx-border-color: #D9D9D9;"
										+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
								card.setPadding(new Insets(10));
								card.setAlignment(Pos.CENTER);
								Text eventNameCard = new Text(allEvent.get(j).getName());
								card.getChildren().addAll(eventNameCard);
								DatePicker datebuff = new DatePicker(allEvent.get(j).getDate().toLocalDate());
								datebuff.setVisible(false);
								Text idvehiCard = new Text();
								Text idevent = new Text(String.valueOf(allEvent.get(j).getId()));
								if (allEvent.get(j).getIdvehi() != 0) {
									idvehiCard.setText(String.valueOf(allEvent.get(j).getIdvehi()));
								}

								bigCardBox.getChildren().addAll(card, datebuff);

								card.setOnMouseClicked(h -> {

									eventNameC.setText(eventNameCard.getText());
									eventDateC.setValue(datebuff.getValue());

									idvehiC.setText(idvehiCard.getText());
									eventid.setText(idevent.getText());

									bighbox.getChildren().addAll(CorrectCardBox);

								});

							}

						}
						if (counters == 0) {
							Label fillers = new Label("Aucun évènement ce jour-ci");
							bigCardBox.getChildren().addAll(fillers);
						}
						bighbox.getChildren().addAll(bigCardBox);
					});
					if (dayOfWeek == 1) {

						grid.add(rappelCard, 7, lines);
						lines++;
						dayOfWeek++;

					}

					else {
						grid.add(rappelCard, dayOfWeek - 1, lines);
						dayOfWeek++;

					}
					if (dayOfWeek == 8)
						dayOfWeek = 1;
					count = 0;

				}

			}
		});

		// generate 1sst instance of calendar
		intmonth.setText(String.valueOf(cl.get(Calendar.MONTH)));

		if (validevent == 1) {
			calcon.GetAllrappelEvent(this);
			dataInput di = new dataInput();
			for (int i = 0; i < allRappelEvent.size(); i++) {
				HBox rappelCard = new HBox();
				rappelCard.setMinHeight(80);
				rappelCard.setMinWidth(80);
				rappelCard.setSpacing(10);
				Text datetext = new Text(String.valueOf(allRappelEvent.get(i).getDate()));
				Button accept = new Button();
				int buffpos = i;
				int buff = allRappelEvent.get(i).getIdvehi();
				int eventbuffid = allRappelEvent.get(i).getId();
				accept.setOnMouseClicked(f -> {

					di.addExitDate(buff);
					calcon.DeleteEvent(eventbuffid);
					HBox Validationbox = new HBox();
					Validationbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
							+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;" + "-fx-border-color: #3b703d;"
							+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

					Label ValidationLabel = new Label("Le véhicule " + buff + " à bien été marqué comme sorti !");
					ValidationLabel.setFont(Font.font("Arial", 20));
					ValidationLabel.setStyle("-fx-text-fill: #000000");
					Validationbox.setPadding(new Insets(20));
					Validationbox.setAlignment(Pos.BASELINE_CENTER);
					Validationbox.getChildren().addAll(ValidationLabel);
					BIGGRID.add(Validationbox, 1, 0);
					bigCardBox.getChildren().remove(buff);

				});
				Image searchIcon = new Image(getClass().getResource("/cross-23.png").toExternalForm());
				ImageView iconView = new ImageView(searchIcon);
				iconView.setFitWidth(25);
				iconView.setPreserveRatio(true);
				
				Image searchIcon1 = new Image(getClass().getResource("/3388530.png").toExternalForm());
				ImageView iconView1 = new ImageView(searchIcon1);
				iconView1.setFitWidth(25);
				iconView1.setPreserveRatio(true);
				accept.setGraphic(iconView1);
				
				Button deny = new Button();
				deny.setGraphic(iconView);
				deny.setOnMouseClicked(f -> {

					calcon.DeleteEvent(eventbuffid);

					bigCardBox.getChildren().remove(buff);

				});

				Separator sepv = new Separator(Orientation.VERTICAL);
				Separator sepv2 = new Separator(Orientation.VERTICAL);
				Text dayText = new Text(allRappelEvent.get(i).getName());
				rappelCard.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
						+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
						+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

				rappelCard.setPadding(new Insets(10));
				rappelCard.setAlignment(Pos.CENTER);
				rappelCard.getChildren().addAll(datetext, sepv, dayText, sepv2, accept, deny);
				bigCardBox.getChildren().addAll(rappelCard);
			}
			bighbox.getChildren().addAll(bigCardBox);

		}
		// general view
		
		// Add event button
		Button addEvent = new Button("Ajouter un Evenement");

		addEvent.setAlignment(Pos.BASELINE_RIGHT);
		addEvent.setEffect(new DropShadow(10, Color.BLACK));
		Button rappel = new Button("Actions à valider");

		rappel.setOnMouseClicked(e -> {
			app.showCalendarRappelScene();
		});
		// Set general grid
		BIGGRID.add(bighbox, 1, 2);

		HBox btnBox = new HBox(rappel, addEvent);
		rappel.getStylesheets().add("/styles.css");
		rappel.setEffect(new DropShadow(10, Color.BLACK));
		addEvent.getStylesheets().add("/styles.css");
		btnBox.setSpacing(20);
		BIGGRID.add(btnBox, 1, 3);
		btnBox.setAlignment(Pos.BASELINE_RIGHT);
		
		BIGGRID.setVgap(20);
		ScrollPane stackPane = new ScrollPane();
		stackPane.setContent(BIGGRID);
		stackPane.setFitToWidth(true);
		this.setCenter(stackPane);

		// Button Action
		addEvent.setOnMouseClicked(e -> {
			if (bighbox.getChildren().size() != 1) {
				bighbox.getChildren().remove(1, bighbox.getChildren().size());
				bigCardBox.getChildren().remove(0, bigCardBox.getChildren().size());
			}
			Label evName = new Label("Nom de l'évènement :");
			TextField eventName = new TextField();
			Label evDate = new Label("Date :");
			DatePicker eventDate = new DatePicker();
			Label Vehiculeid = new Label("Numéro du véhicule :");
			TextField idvehi = new TextField();
			idvehi.setTextFormatter(new TextFormatter<>(filter));
			Button ajouter = new Button("Ajouter");

			ajouter.setEffect(new DropShadow(10, Color.BLACK));
			ajouter.setOnMouseClicked(f -> {
				int id = 0;
				Date today2 = new Date();
				Calendar cl2 = Calendar.getInstance();
				cl2.setTime(today2);

				if (idvehi.getText() != "")
					id = Integer.parseInt(idvehi.getText());

				if (eventDate.getValue() == null) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Données manquantes");
					alert.setHeaderText(null);
					alert.setContentText("Veuillez renseigner la date de l'évènement");
					alert.showAndWait();
				}

				else if (eventDate.getValue().isBefore(
						LocalDate.of(cl2.get(Calendar.YEAR), cl2.get(Calendar.MONTH) + 1, cl2.get(Calendar.DATE)))) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Données incorrectes");
					alert.setHeaderText(null);
					alert.setContentText("Veuillez renseigner une date à venir");
					alert.showAndWait();
				} else if (eventName.getText() == "") {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Données manquantes");
					alert.setHeaderText(null);
					alert.setContentText("Veuillez renseigner le nom de l'évènement");
					alert.showAndWait();
				} else {
					calcon.addEvent(eventName.getText(), java.sql.Date.valueOf(eventDate.getValue()), id, null);
					HBox Validationbox = new HBox();
					Validationbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
							+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;" + "-fx-border-color: #3b703d;"
							+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

					Label ValidationLabel = new Label("L'évènement a bien été ajouté !");
					ValidationLabel.setFont(Font.font("Arial", 20));
					ValidationLabel.setStyle("-fx-text-fill: #000000");
					Validationbox.setPadding(new Insets(20));
					Validationbox.setAlignment(Pos.BASELINE_CENTER);
					Validationbox.getChildren().addAll(ValidationLabel);
					calcon.GetAllEvent(this);
					String buffer = intmonth.getText();

					intmonth.setText(buffer);
					BIGGRID.add(Validationbox, 1, 1);
				}
			});
			bigCardBox.getChildren().addAll(evName, eventName, evDate, eventDate, Vehiculeid, idvehi, ajouter);
			bighbox.getChildren().addAll(bigCardBox);
		});
		Suppr.setOnMouseClicked(f -> {
			calcon.DeleteEvent(Integer.parseInt(eventid.getText()));

		});

		Modifier.setOnMouseClicked(f -> {
			int id = 0;
			Date today2 = new Date();
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(today2);

			if (idvehiC.getText() != "")
				id = Integer.parseInt(idvehiC.getText());

			if (eventDateC.getValue() == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Données manquantes");
				alert.setHeaderText(null);
				alert.setContentText("Veuillez renseigner la date de l'évènement");
				alert.showAndWait();
			}

			else if (eventDateC.getValue().isBefore(
					LocalDate.of(cl2.get(Calendar.YEAR), cl2.get(Calendar.MONTH) + 1, cl2.get(Calendar.DATE)))) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Données incorrectes");
				alert.setHeaderText(null);
				alert.setContentText("Veuillez renseigner une date à venir");
				alert.showAndWait();
			} else if (eventNameC.getText() == "") {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Données manquantes");
				alert.setHeaderText(null);
				alert.setContentText("Veuillez renseigner le nom de l'évènement");
				alert.showAndWait();
			} else {

				calcon.UpdateEvent(eventNameC.getText(), java.sql.Date.valueOf(eventDateC.getValue()), id,
						Integer.parseInt(eventid.getText()), null);

				HBox Validationbox = new HBox();
				Validationbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
						+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;" + "-fx-border-color: #3b703d;"
						+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

				Label ValidationLabel = new Label("L'évènement a bien été modifié !");
				ValidationLabel.setFont(Font.font("Arial", 20));
				ValidationLabel.setStyle("-fx-text-fill: #000000");
				Validationbox.setPadding(new Insets(20));
				Validationbox.setAlignment(Pos.BASELINE_CENTER);
				Validationbox.getChildren().addAll(ValidationLabel);
				calcon.GetAllEvent(this);
				String buffer = intmonth.getText();

				intmonth.setText(buffer);
				BIGGRID.add(Validationbox, 1, 1);
			}

		});

		return new Scene(this, 600, 400);
	}
}
