package front;

import back.ParameterController;
import back.User;
import back.UserTaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ParametersScene extends BorderPane {
	private User utilisateur;
	public User listener;
	private App app;
	public GridPane grid;
	ParameterController pamcont = new ParameterController();
	public TableView listUtilisateur;

	public Button modif = new Button("Modifier");
	public Button del = new Button("Supprimer");

	public ParametersScene(App app, User utilisateur) {
		this.app = app;
		this.utilisateur = utilisateur;
	}

	public Scene createParameterScene() {
		ObservableList<TextField> textList = FXCollections.observableArrayList();
		modif.setVisible(false);
		del.setVisible(false);
		Label Title = new Label();
		UserTaker usertaker = new UserTaker();
		usertaker.getAllUsers(this);
		Title.setText("Paramètres");
		Title.getStylesheets().add("/styles.css");
		Title.getStyleClass().add("Title");

		TopNavBar topnav = new TopNavBar(app, "param", utilisateur);

		this.setTop(topnav);
		ParamButton pambut = new ParamButton(app);
		this.setBottom(pambut);

		grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setAlignment(Pos.CENTER);
		grid.add(Title, 1, 0);

		// ///////////////////////UTILISATEUR//////////////////////////////////

		Label usernames = new Label("Bienvenue " + utilisateur.getUsername());

		TextField utilnameField = new TextField(utilisateur.getUsername());
		Label utilnamelab = new Label("Nom d'utilisateur :");

		HBox utiln = new HBox(utilnamelab, utilnameField);
		utiln.setSpacing(20);

		PasswordField passnameField = new PasswordField();
		Label passnamelab = new Label("Mot de Passe Actuel :");
		HBox Passbox = new HBox(passnamelab, passnameField);
		Passbox.setSpacing(20);

		PasswordField passnameField2 = new PasswordField();
		Label passnamelab2 = new Label("Nouveau Mot de Passe :");
		HBox Passbox2 = new HBox(passnamelab2, passnameField2);
		Passbox2.setSpacing(20);
		Button valid = new Button("Valider");
		VBox user = new VBox(new Label("Utilisateur"), new Separator(Orientation.HORIZONTAL), utiln, Passbox, Passbox2,
				valid);
		user.setPadding(new Insets(20));
		user.setSpacing(20);
		user.getStylesheets().add("/styles.css");
		user.getStyleClass().add("main-box");

		grid.add(user, 1, 3);
		////////////////////////////////// FIN UTILISATEUR
		////////////////////////////////// //////////////////////////////////////

		////////////////////// LISTE UTILISATEUR ///////////////////////////////////

		Button add = new Button("Ajouter");

		HBox btnbox = new HBox(add, modif, del);
		VBox listutil = new VBox(new Label("Liste des Utilisateurs"), new Separator(Orientation.HORIZONTAL),
				listUtilisateur, btnbox);

		listutil.setPadding(new Insets(20));
		listutil.setSpacing(20);
		listutil.getStylesheets().add("/styles.css");
		listutil.getStyleClass().add("main-box");
		if (utilisateur.getUserrole() == 0)
			grid.add(listutil, 2, 2);

		/////////////////////// FIN LISTE UTILISATEUR ///////////////

		//////////////////////////////////////////// INFO SOCIETE
		////////////////////////////////////////////

		String[] info = pamcont.GetInfo();

		TextField comname = new TextField(info[0]);
		comname.setStyle("-fx-min-width:400px;");
		TextField comtitle = new TextField(info[1]);
		comtitle.setStyle("-fx-min-width:400px;");
		TextField comtitle2 = new TextField(info[2]);
		comtitle2.setStyle("-fx-min-width:400px;");
		TextField comloc = new TextField(info[3]);
		comloc.setStyle("-fx-min-width:400px;");
		TextField comphone = new TextField(info[4]);
		comphone.setStyle("-fx-min-width:400px;");
		TextField comfax = new TextField(info[5]);
		comfax.setStyle("-fx-min-width:400px;");
		TextField commail = new TextField(info[6]);
		commail.setStyle("-fx-min-width:400px;");
		TextField comSiret = new TextField(info[7]);
		comSiret.setStyle("-fx-min-width:200px;");
		TextField combottomText = new TextField(info[8]);
		combottomText.setStyle("-fx-min-width:400px;");
		TextField combottomText2 = new TextField(info[9]);
		combottomText2.setStyle("-fx-min-width:400px;");
		TextField combottomText3 = new TextField(info[10]);
		combottomText3.setStyle("-fx-min-width:400px;");
		TextField tvatext = new TextField(info[11]);
		tvatext.setStyle("-fx-min-width:400px;");
		textList.addAll(comname, comtitle, comtitle2, comloc, comphone, comfax, commail, comSiret, combottomText,
				combottomText2, combottomText3, tvatext);
		VBox titles = new VBox(new Label("Nom de Société : "), new Label("Titre : "), new Label("Adresse : "),
				new Label("Téléphone :"), new Label("Fax :"), new Label("Email :"), new Label("SIRET :"),
				new Label("Texte bas de Page :"), new Label("TVA intracommunautaire :"));
		titles.setSpacing(43);

		HBox btitle = new HBox(comtitle, comtitle2);

		HBox bbot = new HBox(combottomText, combottomText2, combottomText3);

		VBox fields = new VBox(comname, btitle, comloc, comphone, comfax, commail, comSiret, bbot, tvatext);
		fields.setSpacing(22);
		titles.setAlignment(Pos.BASELINE_LEFT);
		// fields.setAlignment(Pos.BASELINE_LEFT);
		HBox bname = new HBox(titles, fields);
		Button modifer = new Button("Modifier");
		VBox binfo = new VBox(new Label("Information sur la société "), new Separator(Orientation.HORIZONTAL), bname,
				modifer);
		binfo.getStylesheets().add("/styles.css");
		binfo.getStyleClass().add("main-box");
		if (utilisateur.getUserrole() == 0)
			grid.add(binfo, 1, 2);
		binfo.setPadding(new Insets(20));
		binfo.setSpacing(10);

		modifer.setOnAction(e -> {
			int compteur = 0;
			String[] infogetter = new String[12];
			for (int i = 0; i < textList.size(); i++) {
				if (textList.get(i).getText() == "")
					compteur++;
				infogetter[i] = textList.get(i).getText();
			}
			
			if (compteur == 0) {
				pamcont.modifyInfos(infogetter);
				HBox Validationbox = new HBox();
				Validationbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
						+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;" + "-fx-border-color: #3b703d;"
						+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

				Label ValidationLabel = new Label("Les informations de la société ont bien été modifié !");
				ValidationLabel.setFont(Font.font("Arial", 20));
				ValidationLabel.setStyle("-fx-text-fill: #000000");
				Validationbox.setPadding(new Insets(20));
				Validationbox.setAlignment(Pos.BASELINE_CENTER);
				Validationbox.getChildren().addAll(ValidationLabel);
				grid.getChildren()
						.removeIf(node -> GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 1);
				grid.add(Validationbox, 1, 1, 2, 1);
			} else {

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Données manquantes");
				alert.setHeaderText(null);

				alert.setContentText("Veuillez remplir tous les champs !");
				alert.showAndWait();
			}

		});

		add.setOnAction(e -> {
			TextField newuser = new TextField();
			newuser.setPromptText("Identifiant");
			ChoiceBox<String> userroles = new ChoiceBox<String>();
			userroles.getItems().add("Terrain");
			userroles.getItems().add("Bureau");
			userroles.setValue("Terrain");

			Button valider = new Button("Valider");

			valider.setOnAction(f -> {
				if (newuser.getText() != "") {
					pamcont.addUser(newuser.getText(), userroles.getValue());
					HBox Validationbox = new HBox();
					Validationbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
							+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;" + "-fx-border-color: #3b703d;"
							+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

					Label ValidationLabel = new Label("L'utilisateur a bien été ajouté !");
					ValidationLabel.setFont(Font.font("Arial", 20));
					ValidationLabel.setStyle("-fx-text-fill: #000000");
					Validationbox.setPadding(new Insets(20));
					Validationbox.setAlignment(Pos.BASELINE_CENTER);
					Validationbox.getChildren().addAll(ValidationLabel);
					grid.getChildren()
							.removeIf(node -> GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 1);
					grid.add(Validationbox, 1, 1, 2, 1);

					usertaker.getAllUsers(this);
					listutil.getChildren().remove(0, listutil.getChildren().size());
					listutil.getChildren().addAll(new Label("Liste des Utilisateurs"),
							new Separator(Orientation.HORIZONTAL), listUtilisateur, btnbox);
				}

				else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Données manquantes");
					alert.setHeaderText(null);

					alert.setContentText("Veuillez renseigner un identifiant");
					alert.showAndWait();
				}

			});
			VBox newuserCard = new VBox(new Label("Ajouter un nouvel utilisateur"),

					new Separator(Orientation.HORIZONTAL), newuser, userroles, new Separator(Orientation.HORIZONTAL),
					valider);
			newuserCard.getStylesheets().add("/styles.css");
			newuserCard.getStyleClass().add("main-box");
			newuserCard.setSpacing(10);
			newuserCard.setPadding(new Insets(10));
			grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 3 && GridPane.getColumnIndex(node) == 2);
			grid.add(newuserCard, 2, 3);

		});

		valid.setOnAction(e -> {

			if (passnameField.getText() != "" && passnameField2.getText() != "")
				pamcont.comparePassword(utilisateur, passnameField.getText(), this, passnameField2.getText());
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Données manquantes");
				alert.setHeaderText(null);

				alert.setContentText("Veuillez renseigner le mot de passe actuel et un nouveau mot de passe !");
				alert.showAndWait();
			}
		});

		del.setOnAction(e -> {

			if (listener.getUserrole() != 0) {
				pamcont.DeleteUser(listener);
				HBox Validationbox = new HBox();
				Validationbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
						+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;" + "-fx-border-color: #3b703d;"
						+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

				Label ValidationLabel = new Label("L'utilisateur a bien été supprimé!");
				ValidationLabel.setFont(Font.font("Arial", 20));
				ValidationLabel.setStyle("-fx-text-fill: #000000");
				Validationbox.setPadding(new Insets(20));
				Validationbox.setAlignment(Pos.BASELINE_CENTER);
				Validationbox.getChildren().addAll(ValidationLabel);
				grid.getChildren()
						.removeIf(node -> GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 1);
				grid.add(Validationbox, 1, 1, 2, 1);

				usertaker.getAllUsers(this);
				listutil.getChildren().remove(0, listutil.getChildren().size());
				listutil.getChildren().addAll(new Label("Liste des Utilisateurs"),
						new Separator(Orientation.HORIZONTAL), listUtilisateur, btnbox);

			}

			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Action non authorisée");
				alert.setHeaderText(null);

				alert.setContentText("Vous ne pouvez pas supprimer un Administrateur !");
				alert.showAndWait();
			}

		});

		modif.setOnAction(e -> {

			TextField newuser = new TextField(listener.getUsername());
			newuser.setPromptText("Identifiant");
			ChoiceBox<String> userroles = new ChoiceBox<String>();
			userroles.getItems().add("Terrain");
			userroles.getItems().add("Bureau");
			userroles.setValue(listener.getNameRole());
			PasswordField newpass = new PasswordField();
			newpass.setPromptText("Nouveau Mot de Passe");
			Button valider = new Button("Valider");

			valider.setOnAction(f -> {
				if (newuser.getText() != "") {
					pamcont.ModifyUser(listener, newuser.getText(), userroles.getValue(), newpass.getText());
					HBox Validationbox = new HBox();
					Validationbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
							+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;" + "-fx-border-color: #3b703d;"
							+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

					Label ValidationLabel = new Label("L'utilisateur a bien été modifié !");
					ValidationLabel.setFont(Font.font("Arial", 20));
					ValidationLabel.setStyle("-fx-text-fill: #000000");
					Validationbox.setPadding(new Insets(20));
					Validationbox.setAlignment(Pos.BASELINE_CENTER);
					Validationbox.getChildren().addAll(ValidationLabel);
					grid.getChildren()
							.removeIf(node -> GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 1);
					grid.add(Validationbox, 1, 1, 2, 1);
					usertaker.getAllUsers(this);
					listutil.getChildren().remove(0, listutil.getChildren().size());
					listutil.getChildren().addAll(new Label("Liste des Utilisateurs"),
							new Separator(Orientation.HORIZONTAL), listUtilisateur, btnbox);
				}

				else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Données manquantes");
					alert.setHeaderText(null);

					alert.setContentText("Veuillez renseigner un identifiant");
					alert.showAndWait();
				}

			});
			VBox newuserCard = new VBox(new Label("Modifier un utilisateur"),

					new Separator(Orientation.HORIZONTAL), newuser, newpass, userroles, new Separator(Orientation.HORIZONTAL),
					valider);
			newuserCard.getStylesheets().add("/styles.css");
			newuserCard.getStyleClass().add("main-box");
			newuserCard.setSpacing(10);
			newuserCard.setPadding(new Insets(10));

			grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 3 && GridPane.getColumnIndex(node) == 2);

			grid.add(newuserCard, 2, 3);

		});

		//////////////////////////////////////// FIN INFO SOCIETE///////////////

		ScrollPane stackPane = new ScrollPane();
		stackPane.setContent(grid);
		grid.setHgap(10);
		grid.setVgap(10);
		this.setCenter(stackPane);

		return new Scene(this);
	}
}
