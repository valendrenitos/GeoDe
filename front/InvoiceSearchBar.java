package front;

import back.InvoiceTaker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InvoiceSearchBar extends VBox {

	public InvoiceSearchBar(App app, AllInvoiceTable invtb) {

		InvoiceTaker invoicetaker = new InvoiceTaker();
		setPadding(new Insets(30, 0, 20, 0));

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
		TextField invoiceNumberField = new TextField();
		invoiceNumberField.setPromptText("Numéro de Facture");
		invoiceNumberField.setStyle("-fx-prompt-text-fill: black");
		invoiceNumberField.setPrefWidth(340);

		TextField invoiceClientField = new TextField();
		invoiceClientField.setPromptText("Client");
		invoiceClientField.setStyle("-fx-prompt-text-fill: black");
		invoiceClientField.setPrefWidth(340);

		DatePicker startDate = new DatePicker();
		startDate.setPromptText("A partir de :");
		DatePicker endDate = new DatePicker();
		endDate.setPromptText("Jusqu'à :");
		ComboBox<String> destchoiceBox = new ComboBox<String>();
		destchoiceBox.getItems().add("Temporaire");
		destchoiceBox.getItems().add("Définitive");
		destchoiceBox.getItems().add("Avoir");
		destchoiceBox.getItems().add("Payée");
		destchoiceBox.setPromptText("Status");

		// Création du bouton de validation pour appliquer les filtres
		Button validateBtn = new Button();
		validateBtn.setGraphic(iconView2);
		validateBtn.getStyleClass().add("iconBtn");

		searchBtns.getChildren().addAll(invoiceNumberField, invoiceClientField, startDate, endDate, destchoiceBox,
				validateBtn);

		searchPane.getChildren().addAll(searchBtns);
		getChildren().addAll(searchPane);

		validateBtn.setOnAction(e -> {

			System.out.println(endDate.getValue());
			app.showInvoiceTableScene(invoiceNumberField.getText(), invoiceClientField.getText(),
					destchoiceBox.getValue(), startDate.getValue(), endDate.getValue());
		});
	}

}
