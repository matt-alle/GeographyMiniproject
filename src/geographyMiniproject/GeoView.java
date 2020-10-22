package geographyMiniproject;

import javafx.stage.Stage;

public class GeoView {

	private Stage stage;
	private GeoModel model;

	public GeoView(Stage stage, GeoModel model) {
		this.stage = stage;
		this.model = model;
	}

	public void start() {
		stage.show();
	}
}
