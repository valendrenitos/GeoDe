package front;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class ParamButton extends BorderPane {

	public ParamButton(App app) {

		Button homeButton = new Button("Paramètres");

		homeButton.setEffect(new DropShadow(10, Color.BLACK));
		homeButton.getStylesheets().add("/styles.css");
		homeButton.setTextAlignment(TextAlignment.CENTER);
		homeButton.setPadding(new Insets(10));
		homeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				app.showParamScene();
			}

		});
		if (app.getUser().getUserrole() == 0) {
			HBox butt = new HBox(homeButton);
			butt.setPadding(new Insets(20));
			setLeft(butt);
		}

	}
}
