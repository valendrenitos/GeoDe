package front;

import back.User;
import back.UserTaker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LoginPage extends BorderPane {
	private App app;

	public LoginPage(App app) {
		this.app = app;
	}

	public Scene createLoginPage() {
		UserTaker usertaker = new UserTaker();

		GridPane grid = new GridPane();
		Label Title = new Label();

		Title.setText("Connexion");
		Title.getStylesheets().add("/styles.css");
		Title.getStyleClass().add("Title");

		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setAlignment(Pos.CENTER);
		grid.add(Title, 1, 0);
		Text us = new Text("Nom d'Utilisateur : ");
	
		TextField userField = new TextField();
		us.setStyle("-fx-min-width: 130px; -fx-max-width:130px;");
		HBox userhbox = new HBox(us, userField);
		userhbox.setSpacing(20);
		userhbox.setAlignment(Pos.BASELINE_RIGHT);
		userField.setPromptText("Utilisateur");

		PasswordField passField = new PasswordField();
		Text mdp = new Text("Mot de Passe : ");
		mdp.setStyle("-fx-min-width: 160px; -fx-max-width: 160px;");
		HBox passhbox = new HBox(mdp, passField);
		passhbox.setAlignment(Pos.BASELINE_RIGHT);
		passhbox.setSpacing(20);
		passField.setPromptText("Mot de Passe");
		Button connexion = new Button("Connexion");
		connexion.getStylesheets().add("/styles.css");
		VBox loginBox = new VBox(userhbox, passhbox);
		loginBox.getStylesheets().add("/styles.css");
		loginBox.getStyleClass().add("main-box");
		loginBox.setSpacing(20);
		loginBox.setPadding(new Insets(20, 20, 20, 20));
		grid.add(loginBox, 1, 2);
		grid.add(connexion, 1, 3);
		
		
		
		
		
		connexion.setOnAction(e -> {
			String userText = userField.getText();
			String passText = passField.getText();
			grid.getChildren()
			.removeIf(node -> GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 1);
			if (userText == "" | passText == "") {
				HBox Deniedbox = new HBox();
				Deniedbox.setStyle("-fx-background-color: #ed7777;" + "-fx-border-radius : 15px;"
						+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;" + "-fx-border-color:#9c1717;"
						+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

				Label DeniedLabel = new Label("Veuillez remplir tous les champs !");
				DeniedLabel.setFont(Font.font("Arial", 20));
				DeniedLabel.setStyle("-fx-text-fill: #000000");
				Deniedbox.setPadding(new Insets(20));
				Deniedbox.setAlignment(Pos.BASELINE_CENTER);
				Deniedbox.getChildren().addAll(DeniedLabel);
				grid.add(Deniedbox, 1, 1);

			} else {
				User user = usertaker.ValidateUser(userText, passText);
				if (user.getUserrole() == -1) {
					HBox Deniedbox = new HBox();
					Deniedbox.setStyle("-fx-background-color: #ed7777;" + "-fx-border-radius : 15px;"
							+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;" + "-fx-border-color: #9c1717;"
							+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

					Label DeniedLabel = new Label("Nom d'utilisateur ou mot de Passe incorrect !");
					DeniedLabel.setFont(Font.font("Arial", 20));
					DeniedLabel.setStyle("-fx-text-fill: #000000");
					Deniedbox.setPadding(new Insets(20));
					Deniedbox.setAlignment(Pos.BASELINE_CENTER);
					Deniedbox.getChildren().addAll(DeniedLabel);

					grid.add(Deniedbox, 1, 1);

				} else {
					app.showHomeScene(user);
				}

			}

		});

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(grid);

		this.setCenter(stackPane);

		return new Scene(this);

	}

}
