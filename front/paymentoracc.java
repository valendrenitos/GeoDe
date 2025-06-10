package front;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

import back.Invoice;
import back.InvoiceInput;
import back.InvoiceTaker;
import back.User;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class paymentoracc extends BorderPane {

	InvoiceInput invoiceinput = new InvoiceInput();
	InvoiceTaker invoicetaker = new InvoiceTaker();
	private static final DecimalFormat df = new DecimalFormat("0.00");
	private App app;
	public List<String> amt;
	public List<String> mtd;
	public List<LocalDate> datepaym;
	int i;
	private User utilisateur;
	public ObservableList<TextField> txtpuht;
	public ObservableList<ChoiceBox<String>> txtpay;
	public ObservableList<DatePicker> txtdate;
	public Text totalhtField;
	public VBox paydate;
	public VBox paytype;
	public VBox puhtvbox;

	public paymentoracc(App app, User utilisateur) {
		this.app = app;
		this.utilisateur = utilisateur;
	}

	public Scene createPayOrAcc(App app, Invoice invoice) {
		txtdate = FXCollections.observableArrayList();
		txtpay = FXCollections.observableArrayList();
		txtpuht = FXCollections.observableArrayList();
		TopNavBar topnav = new TopNavBar(app, "NewVehicule", utilisateur);
		this.setTop(topnav);
		Label Title = new Label();
		ParamButton pambut = new ParamButton(app);	
		this.setBottom(pambut);
		
		Title.setText("Ajouter un paiement ou accompte");
		Title.getStylesheets().add("/styles.css");
		Title.getStyleClass().add("Title");
		invoicetaker.acctaker(invoice.invoiceid, this);
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
		generalVbox.getStylesheets().add("/styles.css");
		generalVbox.getStyleClass().add("main-box");

		HBox topHbox = new HBox();
		topHbox.setSpacing(20);
		topHbox.setPadding(new Insets(20));
		topHbox.setStyle(
				"-fx-background-color: #e6dfdf; " + "-fx-border-radius : 15px;" + "-fx-background-radius : 15px;");

		Text numfa = new Text(invoice.getInvoiceNum());
		Text clienttype = new Text(invoice.getInvoiceClientType() + " ");
		Text client = new Text(invoice.getInvoiceClient());

		Text fatype = new Text(invoice.getInvoiceStatus());
		Text totalttc = new Text(invoice.getTotalttc() + "");
		Button addbutton = new Button("Ajouter");
		HBox payoradd = new HBox();

		topHbox.getChildren().addAll(new Label("Numéro de Facture : "), numfa, new Label("Client : "), clienttype,
				client, new Label("Status : "), fatype, new Label("Total TTC : "), totalttc);

		puhtvbox = new VBox();
		Label puhtLabel = new Label("Montant :");

		puhtvbox.getChildren().addAll(puhtLabel);

		paytype = new VBox();
		Label paytypeLabel = new Label("Moyen de payment:");

		paydate = new VBox();
		Label paydateLabel = new Label("Date de payment:");

		totalhtField = new Text();
		Text totalttcField = new Text();

		totalhtField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				double value = Double.parseDouble(newValue);
				String ttc = df.format(value) + "";
				ttc = ttc.replace(",", ".");
				totalttcField.setText(ttc);
			}
		});

		totalhtField.setText(invoice.getTotalttc() + "");
		paytype.getChildren().addAll(paytypeLabel);
		paydate.getChildren().addAll(paydateLabel);
		addLines();
		paytype.setSpacing(5);

		paydate.setSpacing(5);
		puhtvbox.setSpacing(5);

		if (amt.size() != 0) {

			for (int i = 0; i < amt.size(); i++) {

				txtpuht.get(i).setText(amt.get(i));
				txtpay.get(i).setValue(mtd.get(i));
				txtdate.get(i).setValue(datepaym.get(i));

			}

		}

		addbutton.setOnAction(e -> {
			int compt = 0;
			for (i = 0; i < txtpuht.size(); i++) {
				if (txtpuht.get(i).getText() != ""
						&& (txtdate.get(i).getValue() == null | txtpay.get(i).getValue() == "")) {
					compt++;
				}
			}
			if (compt != 0) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Données manquantes");
				alert.setHeaderText(null);
				alert.setContentText("Lignes de paiement incomplètes");
				alert.showAndWait();
			} else {
				boolean payoradv = false;
				HBox Validationbox = new HBox();
				Validationbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
						+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;" + "-fx-border-color: #3b703d;"
						+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

				Label ValidationLabel = new Label("Le(s) paiements/Accomptes pour la facture " + invoice.getInvoiceNum()
						+ " a/ont bien été ajouté !");
				ValidationLabel.setFont(Font.font("Arial", 20));
				ValidationLabel.setStyle("-fx-text-fill: #000000");
				Validationbox.setPadding(new Insets(20));
				Validationbox.setAlignment(Pos.BASELINE_CENTER);
				Validationbox.getChildren().addAll(ValidationLabel);
				grid.add(Validationbox, 1, 1);
				if (Float.parseFloat(totalhtField.getText()) <= 0) {
					payoradv = true;
					if (invoice.invoiceStatus.equals("Définitive"))
						invoiceinput.paidInvoice(invoice.getInvoiceid());

				}
				invoiceinput.ClearPayOrAdd(invoice.getInvoiceid());
				for (i = 0; i < txtpuht.size(); i++) {
					if (txtpuht.get(i).getText() != "") {
						java.sql.Date date = java.sql.Date.valueOf(txtdate.get(i).getValue());

						invoiceinput.AddPayOrAddLines(txtpuht.get(i).getText(), txtpay.get(i).getValue(), date,
								payoradv, invoice.getInvoiceid());
					}
				}

			}

		});

		payoradd.getChildren().addAll(puhtvbox, paytype, paydate);
		payoradd.setSpacing(10);
		HBox totals = new HBox(new Label("Total Restant :"), totalttcField);
		totals.setAlignment(Pos.BASELINE_RIGHT);
		generalVbox.getChildren().addAll(topHbox, new Separator(Orientation.HORIZONTAL), payoradd,
				new Separator(Orientation.HORIZONTAL), totals, addbutton);
		grid.add(generalVbox, 1, 2);
		ScrollPane stackPane = new ScrollPane();
		stackPane.setContent(grid);
		stackPane.setFitToWidth(true);
		this.setCenter(stackPane);

		return new Scene(this);
	}

	public void addLines() {
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

		txtpay = this.txtpay;
		txtdate = this.txtdate;

		txtpuht = this.txtpuht;
		ChoiceBox<String> txtpayField = new ChoiceBox();
		txtpayField.getItems().add("CB");
		txtpayField.getItems().add("Chèque");
		txtpayField.getItems().add("Espèce");
		txtpayField.getItems().add("Virement");

		DatePicker txtdateField = new DatePicker();

		TextField txtpuhtField = new TextField();

		txtpuhtField.setTextFormatter(new TextFormatter<>(filter));

		txtpuhtField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (oldValue != "") {
					if (totalhtField.getText() != "" && newValue != "") {
						float total = Float.parseFloat(totalhtField.getText()) + Float.parseFloat(oldValue)
								- Float.parseFloat(newValue);
						String values = total + "";
						totalhtField.setText(values);
					} else if (newValue == "") {
						float total = Float.parseFloat(totalhtField.getText()) + Float.parseFloat(oldValue);
						String values = total + "";
						totalhtField.setText(values);
					} else {
						totalhtField.setText(newValue);
					}

				}

				else {
					if (totalhtField.getText() != "") {
						float total = Float.parseFloat(totalhtField.getText()) - Float.parseFloat(newValue);
						String values = total + "";
						totalhtField.setText(values);
					} else {
						totalhtField.setText(newValue);

					}
					addLines();
				}

			}
		});
		txtpay.add(txtpayField);
		txtdate.add(txtdateField);
		txtpuht.add(txtpuhtField);

		paydate.getChildren().addAll(txtdateField);
		puhtvbox.getChildren().addAll(txtpuhtField);
		paytype.getChildren().addAll(txtpayField);

	}
}
