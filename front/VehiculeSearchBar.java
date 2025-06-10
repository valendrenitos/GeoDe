package front;

import java.util.function.UnaryOperator;

import back.DataTaker;
import back.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VehiculeSearchBar extends VBox {

	public VehiculeSearchBar(App app, TableView stockTable, int verifpage, StockPage stock, User user) {

		StockPage stockpage = new StockPage(app, user);
		DataTaker datataker = new DataTaker();
		setPadding(new Insets(30, 0, 20, 0));

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

		VBox searchPane = new VBox();
		searchPane.setSpacing(20);

		// Création du bouton de recherche
		Image searchIcon = new Image(getClass().getResource("/searchICon.png").toExternalForm());
		ImageView iconView = new ImageView(searchIcon);
		iconView.setFitWidth(25);
		iconView.setPreserveRatio(true);

		ImageView iconView2 = new ImageView(searchIcon);
		iconView2.setFitWidth(25);
		iconView2.setPreserveRatio(true);

		HBox btnContent = new HBox();
		btnContent.setAlignment(Pos.CENTER_LEFT);
		btnContent.setSpacing(115);
		btnContent.getChildren().addAll(new Label("Rechercher par :"), iconView);

		HBox searchBtns = new HBox();
		searchBtns.setSpacing(20);

		// Création des champs de texte pour la recherche
		TextField marqueTextField = new TextField();
		marqueTextField.setPromptText("Marque");
		marqueTextField.setStyle("-fx-prompt-text-fill: black");
		marqueTextField.setPrefWidth(340);

		TextField typeTextField = new TextField();
		typeTextField.setPromptText("Type");
		typeTextField.setStyle("-fx-prompt-text-fill: black");
		typeTextField.setPrefWidth(340);

		TextField immatTextField = new TextField();
		immatTextField.setPromptText("Immatriculation");
		immatTextField.setStyle("-fx-prompt-text-fill: black");
		immatTextField.setPrefWidth(340);

		TextField numSerieTextField = new TextField();
		numSerieTextField.setPromptText("Numero de Serie");
		numSerieTextField.setStyle("-fx-prompt-text-fill: black");
		numSerieTextField.setPrefWidth(340);

		TextField couleurTextField = new TextField();
		couleurTextField.setPromptText("Couleur");
		couleurTextField.setStyle("-fx-prompt-text-fill: black");
		couleurTextField.setPrefWidth(340);

		ComboBox<String> destchoiceBox = new ComboBox<String>();
		destchoiceBox.getItems().add("Occasion");
		destchoiceBox.getItems().add("Assurance");
		destchoiceBox.getItems().add("Casse");
		destchoiceBox.getItems().add("Dépot");
		destchoiceBox.setPromptText("Destination");

		TextField idField = new TextField();
		idField.setPromptText("ID");
		idField.setStyle("-fx-prompt-text-fill: black");
		idField.setPrefWidth(340);
		idField.setTextFormatter(new TextFormatter<>(filter));

		Label cgl = new Label("Carte Grise : ");
		CheckBox hascg = new CheckBox();
		HBox cg = new HBox(cgl, hascg);

		ComboBox<String> cgBox = new ComboBox<String>();
		cgBox.getItems().add("Avec CG");
		cgBox.getItems().add("Sans CG");
		cgBox.setPromptText("Carte Grise");

		Label brl = new Label("brulée : ");
		CheckBox hasbr = new CheckBox();
		HBox br = new HBox(brl, hasbr);

		// Création du bouton de validation pour appliquer les filtres
		Button validateBtn = new Button();
		validateBtn.setGraphic(iconView2);
		validateBtn.getStyleClass().add("iconBtn");

		searchBtns.getChildren().addAll(idField, marqueTextField, typeTextField, immatTextField, br, validateBtn);

		HBox searbtns2 = new HBox(numSerieTextField, couleurTextField, destchoiceBox, cgBox);
		searbtns2.setSpacing(20);

		VBox totalSearch = new VBox(searchBtns, searbtns2);
		totalSearch.setSpacing(10);
		searchPane.getChildren().addAll(totalSearch);
		getChildren().addAll(searchPane);

		validateBtn.setOnAction(e -> {

			if (verifpage==0)
				app.showAllVehiculeScene(marqueTextField.getText(), typeTextField.getText(), immatTextField.getText(),
						numSerieTextField.getText(), couleurTextField.getText(), destchoiceBox.getValue(),
						idField.getText(), cgBox.getValue(), hasbr.isSelected());
			else
				app.showStockScene(marqueTextField.getText(), typeTextField.getText(), immatTextField.getText(),
						numSerieTextField.getText(), couleurTextField.getText(), destchoiceBox.getValue(),
						idField.getText(), cgBox.getValue(), hasbr.isSelected());
		});
	}
}
