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
		model.readSaveFileCountries();
		model.readSaveFileStates();
		view.start();

		// Add loaded States to ComboBox
		view.countryBox.getItems().clear();
		String boxItem = null;
		for (int i = 0; i < model.getCountries().size(); i++) {
			boxItem = model.getCountries().get(i).getCountryName();
			view.countryBox.getItems().add(boxItem);
		}
	}

	@Override
	public void stop() {
		model.writeSaveFileCountries();
		model.writeSaveFileStates();
	}
}
