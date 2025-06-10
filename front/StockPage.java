package front;

import java.awt.Dimension;

import back.DataTaker;
import back.Stockpdfgenerator;
import back.User;
import back.vehicule;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class StockPage extends BorderPane {
	public VBox prev;
	public HBox cardbox;
	private App app;
	public TableView<vehicule> stockTable;
	public GridPane grid;
	private User utilisateur;
	public ObservableList<vehicule> multiSelect;
	public int tempid;

	public StockPage(App app, User utilisateur) {
		this.app = app;
		this.utilisateur = utilisateur;
	}

	public Scene createStockTables(int verifpage) {
		
		
		
		Stockpdfgenerator stockpdf = new Stockpdfgenerator();
		DataTaker datataker = new DataTaker();
		String pagename = "StockPage";
		Separator separator = new Separator(Orientation.HORIZONTAL);
		if (verifpage == 0)
			pagename = "AllVehiScene";

		Label Title = new Label();

		if (verifpage == 1)
			Title.setText("Liste des véhicules en Stock");
		else
			Title.setText("Liste de tous les véhicules");
		Title.getStylesheets().add("/styles.css");
		Title.getStyleClass().add("Title");

		TopNavBar topnav = new TopNavBar(app, pagename, utilisateur);
		ParamButton pambut = new ParamButton(app);	
		this.setBottom(pambut);
		
		this.setTop(topnav);

		grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setAlignment(Pos.CENTER);
		grid.add(Title, 1, 0);
		VBox bigvbox = new VBox();
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		double width = 3 * screenSize.getWidth() / 4;
		double height = 3 * screenSize.getHeight() / 4;
		bigvbox.setMinWidth(width);

		// searchbar import

		if (stockTable == null) {
			if (verifpage == 1)
				stockTable = datataker.getStockVehicules(app, this);
			else
				stockTable = datataker.getAllVehicules(app, this);

		}

		cardbox = new HBox();
		cardbox.getChildren().addAll(stockTable);
		cardbox.setSpacing(20);

		// searchbar import
		HBox buttonBox = new HBox();
		Button vehiculeButton = new Button("imprimer la liste en PDF");

		vehiculeButton.setEffect(new DropShadow(10, Color.BLACK));

		vehiculeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				stockpdf.GenerateStockpdf(stockTable);
			}

		});

		VehiculeSearchBar vehiSB = new VehiculeSearchBar(app, stockTable, verifpage, this, utilisateur);

		Button addButton = new Button("Ajouter un Véhicule");

		addButton.setEffect(new DropShadow(10, Color.BLACK));

		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				app.showVehiculeScene(false, "");
			}

		});

		Button invoiceButton = new Button("Ajouter à la facture");

		invoiceButton.setEffect(new DropShadow(10, Color.BLACK));

		invoiceButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (tempid!=0)
				app.showInvoiceScene(multiSelect, tempid);
				else
					app.showInvoiceScene(multiSelect);
			}

		});
		
		if (verifpage == 1)
			buttonBox.getChildren().addAll(vehiculeButton);
		else if (verifpage == 2) {
			buttonBox.getChildren().addAll(invoiceButton);
			
			if (multiSelect != null) {
				ObservableList<vehicule> bufferlist = multiSelect;
				
				
				for (int i = 0; i < stockTable.getItems().size(); i++) {
					for (int j = 0; j < bufferlist.size(); j++) {
						System.out.println(bufferlist.size());
						if (stockTable.getItems().get(i).getId()==bufferlist.get(j).getId()) {
							stockTable.getSelectionModel().select(i);
							System.out.println("selected");
						}
					}
				}
				

			}
		}
	
		
		
		else {
			buttonBox.getChildren().addAll(addButton);

		}

		buttonBox.setAlignment(Pos.BASELINE_RIGHT);

		bigvbox.getChildren().addAll(vehiSB, separator, cardbox, buttonBox);
		cardbox.setMinWidth(vehiSB.getWidth());

		stockTable.setMinWidth((2 * bigvbox.getMinWidth() / 3));
		stockTable.setMaxWidth((2 * bigvbox.getMinWidth() / 3));

		bigvbox.getMaxHeight();
		bigvbox.setPadding(new Insets(20));
		bigvbox.getStylesheets().add("/styles.css");
		bigvbox.getStyleClass().add("main-box");
		bigvbox.setSpacing(40);

		grid.add(bigvbox, 1, 2);
		ScrollPane stackPane = new ScrollPane();
		stackPane.setContent(grid);
		stackPane.setFitToWidth(true);
		this.setCenter(stackPane);


		return new Scene(this);
	}

}
