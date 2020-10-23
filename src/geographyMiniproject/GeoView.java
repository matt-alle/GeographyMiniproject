package geographyMiniproject;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GeoView {

	private Stage stage;
	private GeoModel model;

	Label text = new Label();
	TextField tfArea = new TextField();
	Button copyButton = new Button("copy");

	public GeoView(Stage stage, GeoModel model) {
		this.stage = stage;
		this.model = model;

		VBox root = new VBox();
		root.getChildren().add(tfArea);
		root.getChildren().add(copyButton);
		root.getChildren().add(text);

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
