package geographyMiniproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Lalal extends Application {
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Label lblHello = new Label("Hello, JavaFX!");
		BorderPane root = new BorderPane();
		root.setCenter(lblHello);
		Scene scene = new Scene(root);
		primaryStage.setTitle("Hello, JavaFX!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
