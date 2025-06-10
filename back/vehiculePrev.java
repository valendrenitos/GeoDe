package back;

import java.awt.Dimension;
import java.io.File;
import java.util.List;
import java.util.function.UnaryOperator;

import front.StockPage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class vehiculePrev {
	FileTaker ft = new FileTaker();
PrintVehi pv= new PrintVehi();
	public void prevVehicule(vehicule vehi, StockPage stock) {

		VBox prevBox = new VBox();
		Separator separator = new Separator(Orientation.HORIZONTAL);
		Separator separator2 = new Separator(Orientation.HORIZONTAL);
		

		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		double width = 3 * screenSize.getWidth() / 14;
		dataInput di = new dataInput();
		DataTaker dt = new DataTaker();
		
		
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
		prevBox.setMinWidth(width);
		prevBox.setSpacing(15);
		prevBox.setStyle(
				"-fx-background-color: #e6dfdf; " + "-fx-border-radius : 15px;" + "-fx-background-radius : 15px;");
		prevBox.setPadding(new Insets(20));
		HBox hbox1 = new HBox();
		HBox hbox2 = new HBox();
		HBox hbox3 = new HBox();
		HBox hbox4 = new HBox();
		HBox hbox5 = new HBox();
		Text marque = new Text(vehi.getMarque() + " ");
		Text immat = new Text(vehi.getImmatriculation());
		Text type = new Text(vehi.getType());
		Text numSer = new Text(vehi.getNumeroSerie());
		Text dest = new Text(vehi.getDestination() + " ");
		Text dateentree = new Text(String.valueOf(vehi.getDateEntree()) + " ");
		// Text klms = new Text(vehi.getKlms());
		Text carros = new Text(vehi.getCarrosserie());
		Text energy = new Text(vehi.getEnergie());
		Text couleur = new Text(vehi.getCouleur());
		String cg = ft.RetrieveCG(vehi.getId());
		List<String> otherdocs = ft.RetrieveotherDoc(vehi.getId());
		List<String> pics = ft.Retrievepic(vehi.getId());
		Button cgbut = new Button("Carte Grise");
		Button docsbtn = new Button("Autre(s) document(s)");
		Button picbtn = new Button("Photo(s)");
		Label vehilbl = new Label("Véhicule : ");
		Label imatlbl = new Label("Immatriculé : ");
		Label stslbl = new Label("Status : ");
		Label crclbl = new Label("Carrosserie : ");
		Label entryDate = new Label("Entré le : ");
		Label idlbl = new Label("ID : ");
		hbox3.getChildren().addAll(cgbut, docsbtn, picbtn);
		hbox3.setSpacing(20);
		

		if (cg == "") {
			cgbut.setDisable(true);
		}

		if (otherdocs.size() == 0) {
			docsbtn.setDisable(true);

		} else {
			docsbtn.setText(docsbtn.getText() + " " + "(" + otherdocs.size() + ")");
		}
		if (pics.size() == 0) {
			picbtn.setDisable(true);

		} else {
			picbtn.setText(picbtn.getText() + " " + "(" + pics.size() + ")");
		}

		cgbut.setOnMouseClicked(f -> {

			ft.fileOpener(cg);

		});

		docsbtn.setOnMouseClicked(f -> {

			for (int i = 0; i < otherdocs.size(); i++)
				ft.fileOpener(otherdocs.get(i));

		});

		picbtn.setOnMouseClicked(f -> {

			for (int i = 0; i < pics.size(); i++)
				ft.fileOpener(pics.get(i));

		});

		Label proposedPrice = new Label("Prix proposé : ");
		TextField price = new TextField();
		
		price.setTextFormatter(new TextFormatter<>(filter));
		Button addPrice = new Button("Ajouter");
		HBox hprice = new HBox(proposedPrice, price, addPrice);
		hprice.setSpacing(15);
		hprice.setAlignment(Pos.BASELINE_LEFT);
		addPrice.setOnMouseClicked(f -> {
			di.addPrice(vehi.getId(), price.getText());
			HBox Validationbox = new HBox();
			Validationbox.setStyle("-fx-background-color: #7dd180;" + "-fx-border-radius : 15px;"
					+ "-fx-background-radius : 15px;" + "-fx-border-width: 5px;"
					+ "-fx-border-color: #3b703d;"
					+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

			Label ValidationLabel = new Label(
					"Le prix proposé a bien été enregistré !");
			ValidationLabel.setFont(Font.font("Arial", 20));
			ValidationLabel.setStyle("-fx-text-fill: #000000");
			Validationbox.setPadding(new Insets(20));
			Validationbox.setAlignment(Pos.BASELINE_CENTER);
			Validationbox.getChildren().addAll(ValidationLabel);
			stock.grid.add(Validationbox, 1, 1);
		});
		hbox1.getChildren().addAll(vehilbl, marque, type);
		hbox2.getChildren().addAll(imatlbl, immat, dest, energy);
		hbox4.getChildren().addAll(stslbl, dest, entryDate, dateentree, idlbl, new Text(String.valueOf(vehi.getId())));
		hbox5.getChildren().addAll(crclbl, carros, couleur);
		Button print = new Button("Imprimer la fiche");
		print.setOnAction(e->{
			pv.createVehiPrint(vehi);
			
		});
		prevBox.getChildren().addAll(hbox4, separator, hbox1, hbox2, hbox5, separator2, hbox3, print);
		if (vehi.getDestination().equals("Occasion") && vehi.getHascg()==1) {
			prevBox.getChildren().addAll(new Separator(Orientation.HORIZONTAL), hprice);
			price.setText(dt.retrievePriceWithId(vehi.getId()));
		}

		if (stock.cardbox.getChildren().size() == 1) {
			stock.cardbox.getChildren().addAll(prevBox);

		} else {
			stock.cardbox.getChildren().remove(1);
			stock.cardbox.getChildren().addAll(prevBox);

		}

	}

}
