package geographyMiniproject;

import geographyMiniproject.GovernedRegion.formOfGov;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GeoView {

	private Stage stage;
	private GeoModel model;

	Label text = new Label();

	public GeoView(Stage stage, GeoModel model) {
		this.stage = stage;
		this.model = model;

		// just to test
		Country counrty1 = new Country(1, 1, formOfGov.A);
		// int a =
		text.setText("qwe");
		VBox root = new VBox();
		root.getChildren().add(text);
		// ---

		// Standard stuff for Scene and Stage
		Scene scene = new Scene(root);
		// scene.getStylesheets().add(getClass().getResource("Pet.css").toExternalForm());
		stage.setTitle("GeoStuff");
		stage.setScene(scene);
		stage.show();
	}

	public void start() {
		stage.show();
	}
}
