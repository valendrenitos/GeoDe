package front;

import java.awt.Dimension;

import back.InvoiceTaker;
import back.User;
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

public class AllInvoiceTable extends BorderPane  {

	private App app;
	public TableView stockTable;
	public boolean test = false;
	public HBox cardbox;
	private User utilisateur;
	public  AllInvoiceTable(App app, User utilisateur) {
		this.app = app;
		this.utilisateur=utilisateur;
	}

	
	public Scene createInvoiceStockTables(App app) {
		InvoiceTaker invoicetaker = new InvoiceTaker();		
		TopNavBar topnav = new TopNavBar(app, "InvoicePage", utilisateur);
		this.setTop(topnav);
		ParamButton pambut = new ParamButton(app);	
		this.setBottom(pambut);
		
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		double width = 3 * screenSize.getWidth() / 4;
		double height = 3 * screenSize.getHeight() / 4;
		Label Title = new Label();

			Title.setText("Liste des factures");
		Title.getStylesheets().add("/styles.css");
		Title.getStyleClass().add("Title");
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setAlignment(Pos.CENTER);
		grid.add(Title, 1, 0);
		VBox bigvbox = new VBox();
		bigvbox.getStylesheets().add("/styles.css");
		bigvbox.getStyleClass().add("main-box");
		Separator separator = 
			    new Separator(Orientation.HORIZONTAL);
		//searchbar import
		bigvbox.setMinWidth(width);
		HBox buttonBox = new HBox();
		Button InvoiceButton = new Button("Créer une facture");
		
		
		InvoiceButton.setEffect(new DropShadow(10, Color.BLACK));
		
		InvoiceButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				app.showInvoiceScene(true, false, 0) ;
			}

		});

		Button InvoiceListButton = new Button("Facture à partir d'une liste");
		
		
		InvoiceListButton .setEffect(new DropShadow(10, Color.BLACK));
		
		InvoiceListButton .setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				app.invoiceListScene();
			}

		});
		
		
		
		
		
		
		
		buttonBox.getChildren().addAll(InvoiceButton, InvoiceListButton);
		buttonBox.setAlignment(Pos.BASELINE_RIGHT);
		buttonBox.setSpacing(10);
		if (stockTable == null) {
			
			stockTable = invoicetaker. getAllInvoice(app, this);
		}
		cardbox = new HBox();
		cardbox.getChildren().addAll(stockTable);

		InvoiceSearchBar invoiceSB = new InvoiceSearchBar(app, this);
		cardbox.setSpacing(40);
		bigvbox.getChildren().addAll(invoiceSB, separator, cardbox, buttonBox);
		bigvbox.setSpacing(40);
		bigvbox.setPadding(new Insets(20));
		bigvbox.setStyle("-fx-background-color: #C4C0C0;");
	
		
		grid.add(bigvbox,1,1);
		ScrollPane stackPane = new ScrollPane();
		stackPane.setContent(grid);
		stackPane.setFitToWidth(true);
		this.setCenter(stackPane);

		stockTable.setMinWidth((2 * bigvbox.getMinWidth() / 3));
		stockTable.setMaxWidth((2 * bigvbox.getMinWidth() / 3));
		

			
		return new Scene (this);
	}
	
	
}
