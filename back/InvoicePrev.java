package back;

import java.awt.Dimension;
import java.util.List;

import front.AllInvoiceTable;
import front.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class InvoicePrev {
	public List<Integer> listqte;
	public List<String> act;
	public List<String> price;
	public List<String> vehi;

	public void prevInvoice(Invoice inv, AllInvoiceTable invoicetable, App app) {
		invoicepdfgenerator invpdf = new invoicepdfgenerator();
		DataTaker datataker = new DataTaker();
		Separator separator = new Separator(Orientation.HORIZONTAL);
		Separator separator2 = new Separator(Orientation.HORIZONTAL);
		InvoiceTaker intaker = new InvoiceTaker();
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth() / 6.5;
		intaker.prevtaker(inv.getInvoiceid(), this);
		VBox prevBox = new VBox();
		prevBox.setMinWidth(width);
		prevBox.setMaxWidth(width);
		prevBox.setStyle(
				"-fx-background-color: #e6dfdf; " + "-fx-border-radius : 15px;" + "-fx-background-radius : 15px;");
		prevBox.setPadding(new Insets(20));
		prevBox.setSpacing(20);
		HBox hbox1 = new HBox();
		HBox hbox2 = new HBox();
		HBox hbox3 = new HBox();
		Text Client = new Text(inv.getInvoiceClient() + " ");
		Text textfa = new Text(inv.getInvoiceText());
		Text ClientType = new Text(inv.getInvoiceClientType() + " ");
		Text facttype = new Text("Status : " + inv.getInvoiceStatus() + " ");
		Label invnum = new Label("Facture n° " + inv.getInvoiceNum());
		Label invdat = new Label("Faite le : " + inv.getInvoicedatecreation());
		// Text totalttc = new Text(inv.getTotalttc() + "");
		HBox statustype = new HBox();
		facttype.setTextAlignment(TextAlignment.RIGHT);
		statustype.getChildren().addAll(invnum, invdat);
		statustype.setSpacing(40);
		hbox1.getChildren().addAll(ClientType, Client);
		hbox2.getChildren().addAll(textfa);

		double total = 0;
		HBox line = new HBox();
		line.setSpacing(10);
		VBox listAction = new VBox();
		VBox listprix = new VBox();
		for (int i = 0; i < act.size(); i++) {

			listAction.getChildren().addAll(new Text(act.get(i)));
			listprix.getChildren().addAll(new Text(price.get(i)));
			listprix.setAlignment(Pos.BASELINE_RIGHT);
			total = total + Double.parseDouble(price.get(i));
		}
		line.getChildren().addAll(listAction, listprix);

		Label totalttc = new Label("Reste dû : " + String.valueOf(total) + " €");

		hbox3.getChildren().addAll(line);
		Label actions = new Label("Actions et paiement :");
		prevBox.getChildren().addAll(statustype, facttype, separator, hbox1, hbox2, separator2, actions, hbox3,
				totalttc);
		Button avoirbtn = new Button("Générer un avoir");
		HBox hboxbtn = new HBox();
		hboxbtn.setSpacing(20);
		Button pdfbuild = new Button("Imprimer en pdf");
		hboxbtn.getChildren().addAll(pdfbuild);

		if (inv.getListvehi() != null) {
			Button seeList = new Button("Afficher la liste");
			hboxbtn.getChildren().addAll(seeList);

			seeList.setOnAction(e -> {

				ObservableList<vehicule> multiSelect = FXCollections.observableArrayList();
				ObservableList<Integer> listids = getIds(inv.getListvehi());
				System.out.println(listids.size());
				for (int i = 0; i < listids.size(); i++) {
					multiSelect = datataker.getnewMulti(multiSelect, listids.get(i));
				}
				System.out.println(multiSelect.size());
				ScrollPane listpane = new ScrollPane();
				listpane.getStylesheets().add("/styles.css");
				listpane.getStyleClass().add("main-box");
				VBox listbox = new VBox();
				listbox.getChildren().addAll(new Label("Listes des véhicules sélectionnés"),
						new Separator(Orientation.HORIZONTAL));
				listbox.getStylesheets().add("/styles.css");

				listbox.setSpacing(10);
				listbox.setPadding(new Insets(10));
				for (int i = 0; i < multiSelect.size(); i++) {
					System.out.println("ajout");
					Text vehiadd = new Text(multiSelect.get(i).getImmatriculation() + " "
							+ multiSelect.get(i).getMarque() + " " + multiSelect.get(i).getType());
					listbox.getChildren().addAll(vehiadd, new Separator(Orientation.HORIZONTAL));
				}

				listpane.setContent(listbox);
				if (invoicetable.cardbox.getChildren().size() == 2) {
					invoicetable.cardbox.getChildren().addAll(listpane);

				} else {
					invoicetable.cardbox.getChildren().remove(2);
					invoicetable.cardbox.getChildren().addAll(listpane);

				}

			});
		}
		avoirbtn.setOnAction(e -> {
			app.showInvoiceScene(false, true, inv.getInvoiceid());
		});

		Button accbtn = new Button("Accompte ou Paiement");
		accbtn.setOnAction(e -> {
			app.showPaymentScene(inv);
		});

		if (inv.getInvoiceStatus() == "Définitive" | inv.getInvoiceStatus() == "Payée") {
			hboxbtn.getChildren().addAll(avoirbtn);
		}
		if (inv.getInvoiceStatus() == "Définitive" | inv.getInvoiceStatus() == "Temporaire") {
			hboxbtn.getChildren().addAll(accbtn);
		}
		prevBox.getChildren().addAll(hboxbtn);
		if (invoicetable.cardbox.getChildren().size() == 1) {
			invoicetable.cardbox.getChildren().addAll(prevBox);

		} else {
			while (invoicetable.cardbox.getChildren().size() != 1)
				invoicetable.cardbox.getChildren().remove(1);
			
			invoicetable.cardbox.getChildren().addAll(prevBox);

		}

		pdfbuild.setOnAction(e -> {

			invpdf.generateInvoice(inv);
		});

	}

	public ObservableList<Integer> getIds(String listvehi) {
		ObservableList<Integer> listids = FXCollections.observableArrayList();
		String temp = "";
		for (int i = 0; i < listvehi.length(); i++) {

			if (listvehi.charAt(i) != '.') {
				temp = temp + listvehi.charAt(i);

			} else {
				listids.add(Integer.parseInt(temp));
				temp = "";
			}

		}
		listids.add(Integer.parseInt(temp));

		return listids;

	}

}
