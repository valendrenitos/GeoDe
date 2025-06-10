package front;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import back.AnalitycsController;
import back.DataTaker;
import back.User;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Analitycs extends BorderPane {
	private App app;
	AnalitycsController alcon = new AnalitycsController();
	public List<Integer> totalvehi;
	public List<Integer> totalvehiout;
	public List<Float> income;
	public List<Float> outcome;
	String month;
	private User utilisateur;

	public Analitycs(App app, User utilisateur) {
		this.app = app;
		this.utilisateur = utilisateur;
	}

	public Scene createAnalitycsPage() {
		DataTaker datataker = new DataTaker();
		TopNavBar topnav = new TopNavBar(app, "Analitycs", utilisateur);
		this.setTop(topnav);
		Label Title = new Label();
		ParamButton pambut = new ParamButton(app);	
		this.setBottom(pambut);
		
		Title.setText("Statistiques");
		Title.getStylesheets().add("/styles.css");
		Title.getStyleClass().add("Title");
		alcon.getVehiculesoveryear(this);
		alcon.GetCA(this);
		int unpaid = alcon.getTotalFinishedInvoice();
		int paid = alcon.getTotalPaidInvoice();

		// Calendar grid
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20));
		grid.setAlignment(Pos.CENTER);

		grid.add(Title, 1, 0);
		/////// DECLARA2TION CA//////////////
		CategoryAxis x2Axis = new CategoryAxis();
		x2Axis.setLabel("Mois");

		NumberAxis y2Axis = new NumberAxis();
		y2Axis.setLabel("Montant (en €)");

		LineChart lineChart = new LineChart(x2Axis, y2Axis);

		XYChart.Series dataSeries3 = new XYChart.Series();
		dataSeries3.setName("Entrée");

		XYChart.Series dataSeries4 = new XYChart.Series();

		dataSeries4.setName("Dépense");

		////////////////////////////////////////////////////// ENTREE SORTIE SUR 1 AN
		////////////////////////////////////////////////////// //////////////////////////////////////////////
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Mois");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Nbre véhicule");

		BarChart barChart = new BarChart(xAxis, yAxis);
		barChart.setTitle("Nombre d'entrées et de sorties des véhicules par mois");
		XYChart.Series dataSeries1 = new XYChart.Series();
		XYChart.Series dataSeries2 = new XYChart.Series();
		dataSeries1.setName("Entrées");
		dataSeries2.setName("Sorties");
		Date today = new Date();
		Calendar cl = Calendar.getInstance();

		cl.setTime(today);

		cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) - 12);
		cl.set(Calendar.DATE, 1);
		String name = "";

		for (int i = 0; i < 13; i++) {

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
			name = name + " " + cl.get(Calendar.YEAR);

			dataSeries1.getData().add(new XYChart.Data(name, totalvehi.get(i)));
			dataSeries2.getData().add(new XYChart.Data(name, totalvehiout.get(i)));
			dataSeries3.getData().add(new XYChart.Data(name, income.get(i)));
			dataSeries4.getData().add(new XYChart.Data(name, outcome.get(i)));
			cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) + 1);
			if (i == 12)
				month = name;
		}

		barChart.getData().add(dataSeries1);
		barChart.getData().add(dataSeries2);
//////////////////////////////////////////////////////FIN   ENTREE SORTIE SUR 1 AN //////////////////////////////////////////////

		/////////////////////////////// PAID N UNPAID INBVOICE
		/////////////////////////////// %////////////////////////////////

		PieChart pieChart = new PieChart();
		float total = paid + unpaid;

		float perunpaid = (unpaid / total) * 100;

		float perpaid = (paid / total) * 100;
		PieChart.Data slice1 = new PieChart.Data("Non-Payé (" + perunpaid + "%)", unpaid);
		PieChart.Data slice2 = new PieChart.Data("Payé (" + perpaid + "%)", paid);
		pieChart.getData().add(slice1);
		pieChart.getData().add(slice2);
		pieChart.setTitle("Pourcentage des factures payées et non payées");
		/////////////////////////////// FIN PAID N UNPAID INBVOICE
		/////////////////////////////// %////////////////////////////////

////////////////////////////////////////////DEPENSE ET ENTREE PAR MOIS SUR 1 AN //////////////////////////////////

		lineChart.getData().add(dataSeries3);
		lineChart.getData().add(dataSeries4);

		////////////////////////////////////// CA DU MOIS
		////////////////////////////////////// ///////////////////////////////////////////////

		VBox rappelCard = new VBox();
		rappelCard.setMinHeight(80);
		rappelCard.setMinWidth(80);
		Text catxt = new Text("Chiffre d'affaire entré de :");
		Text dayText = new Text((income.get(12) - outcome.get(12)) + " €");
		Text monthtxt = new Text("sur le mois de " + month + "");
		rappelCard.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
				+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

		rappelCard.setPadding(new Insets(10));
		rappelCard.setAlignment(Pos.CENTER);
		rappelCard.getChildren().addAll(catxt, dayText, monthtxt);

		VBox annecomptaCard = new VBox();
		annecomptaCard.setMinHeight(80);
		annecomptaCard.setMinWidth(80);
		Text annecomptaCardtxt = new Text("Résultat entré de :");
		float totalac = 0;
		int compteur = 0;
		cl.setTime(today);

		cl.get(Calendar.MONTH);

		while (cl.get(Calendar.MONTH) != 6) {
			cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) - 1);
			compteur++;
		}
		for (int i =0; i !=compteur; i++) {
			totalac = totalac+(income.get(12 - i) - outcome.get(12 - i));
		}
		cl.setTime(today);
		Text annecomptaCardText = new Text(totalac + " €");
		Text annecomptaCardtxt2 = new Text(
				"sur l'année  " + (cl.get(Calendar.YEAR) - 1) + " - " + cl.get(Calendar.YEAR));
		annecomptaCard.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
				+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

		annecomptaCard.setPadding(new Insets(10));
		annecomptaCard.setAlignment(Pos.CENTER);
		annecomptaCard.getChildren().addAll(annecomptaCardtxt, annecomptaCardText, annecomptaCardtxt2);

		
		

		VBox annecomptaCard3 = new VBox();
		annecomptaCard3.setMinHeight(80);
		annecomptaCard3.setMinWidth(80);
		Text annecomptaCardtxt3 = new Text("Chiffre d'affaire entré de :");
		float totalac3 = 0;
		int compteur3 = 0;
		cl.setTime(today);

		cl.get(Calendar.MONTH);

		while (cl.get(Calendar.MONTH) != 6) {
			cl.set(Calendar.MONTH, cl.get(Calendar.MONTH) - 1);
			compteur3++;
		}
		for (int i = 0; i !=compteur3 ; i++) {
			totalac3 = totalac3+ income.get(12 - i);
		}
		cl.setTime(today);
		Text annecomptaCardText3 = new Text(totalac3 + " €");
		Text annecomptaCardtxt23 = new Text(
				"sur l'année  " + (cl.get(Calendar.YEAR) - 1) + " - " + cl.get(Calendar.YEAR));
		annecomptaCard3.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
				+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");

		annecomptaCard3.setPadding(new Insets(10));
		annecomptaCard3.setAlignment(Pos.CENTER);
		annecomptaCard3.getChildren().addAll(annecomptaCardtxt3, annecomptaCardText3, annecomptaCardtxt23);
		
		
		VBox factvbox = new VBox();
		HBox factures = new HBox(pieChart);
		factvbox.getChildren().addAll(new Label("Factures"), new Separator(Orientation.HORIZONTAL), factures);
		factvbox.getStylesheets().add("/styles.css");
		factvbox.getStyleClass().add("main-box");
		factvbox.setSpacing(15);
		factvbox.setPadding(new Insets(20));
		VBox vehivbox = new VBox();

		VBox stockCard = new VBox();
		stockCard.setStyle("-fx-background-color: #FAFAFA ;" + "-fx-border-radius : 15px;"
				+ "-fx-background-radius : 15px;" + "-fx-border-width: 2px;" + "-fx-border-color: #D9D9D9;"
				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
		stockCard.setPadding(new Insets(10));
		stockCard.setAlignment(Pos.CENTER);
		Image userImg = new Image(getClass().getResource("/voiture(2).png").toExternalForm());
		ImageView imageView = new ImageView(userImg);
		imageView.setFitWidth(64);
		imageView.setPreserveRatio(true);

		int stocklength = datataker.stockVehiculeLength();
		Label totalStock = new Label("" + stocklength + "");
		Label stockLabel = new Label("en stock");
		stockCard.getChildren().addAll(imageView, totalStock, stockLabel);
		HBox vehicules = new HBox(barChart, new Separator(Orientation.VERTICAL), stockCard);
		vehicules.setSpacing(20);
		vehivbox.getStylesheets().add("/styles.css");
		vehivbox.getStyleClass().add("main-box");
		vehivbox.setSpacing(15);
		vehivbox.setPadding(new Insets(20));
		vehivbox.getChildren().addAll(new Label("Véhicules"), new Separator(Orientation.HORIZONTAL), vehicules);

		VBox cavbox = new VBox();
		VBox cacard = new VBox(rappelCard, annecomptaCard, 	annecomptaCard3);
		cacard.setSpacing(20);

		HBox cabox = new HBox(lineChart, new Separator(Orientation.VERTICAL), cacard);
		cabox.setSpacing(20);
		lineChart.setTitle("Evolution du débit et crédit par mois");
		cavbox.getStylesheets().add("/styles.css");
		cavbox.getStyleClass().add("main-box");
		cavbox.setSpacing(15);
		cavbox.setPadding(new Insets(20));
		cavbox.getChildren().addAll(new Label("Chiffres d'affaires"), new Separator(Orientation.HORIZONTAL), cabox);

		grid.add(factvbox, 1, 2);
		grid.add(vehivbox, 1, 3);
		grid.add(cavbox, 2, 2);
		grid.setHgap(30);
		grid.setVgap(30);
		ScrollPane stackPane = new ScrollPane();
		stackPane.setContent(grid);
		stackPane.setFitToWidth(true);
		this.setCenter(stackPane);

		this.setCenter(stackPane);

		return new Scene(this, 600, 400);
	}
}
