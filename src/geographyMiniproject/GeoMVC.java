package geographyMiniproject;

import javafx.application.Application;
import javafx.stage.Stage;

public class GeoMVC extends Application {
	private GeoView view;
	private GeoModel model;
	private GeoController controller;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		model = new GeoModel();
		view = new GeoView(stage, model);
		controller = new GeoController(model, view);
		view.start();
	}
}
