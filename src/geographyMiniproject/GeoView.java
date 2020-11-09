package geographyMiniproject;

//import java.awt.TextArea;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GeoView {

	private Stage stage;
	private GeoModel model;

	Label stateLabel = new Label("State");
	Label countryLabel = new Label("Country");
	Label populationLabel = new Label("Population");
	Label areaLabel = new Label("Area");
	Label formOfGovLabel = new Label("Form of Government");

	TextField countryTF = new TextField();
	TextField stateTF = new TextField();
	TextField populationTF = new TextField();
	TextField areaTF = new TextField();

	TextArea displayCountry = new TextArea();
	TextArea displayStates = new TextArea();

	Button selectButton = new Button("Select");
	Button selectStateButton = new Button("Select State");
	Button saveButton = new Button("Save Country");
	Button addStateButton = new Button("Add State to Country");
	Button deleteCountryButton = new Button("Delete Country");
	Button deleteStateButton = new Button("Delete State");
	Button updateButton = new Button("Update"); // not sure if needed

	ComboBox<String> countryBox = new ComboBox<>();
	ComboBox<String> stateBox = new ComboBox<>();
	ComboBox<GovernedRegion.formOfGov> formOfGovBox = new ComboBox<>();

	public GeoView(Stage stage, GeoModel model) {
		this.stage = stage;
		this.model = model;

		VBox root = new VBox();
		root.setSpacing(20);
		root.getChildren().add(dataEntry());
		root.getChildren().add(buttonArea());
		root.getChildren().add(dataSelection());
		root.getChildren().add(displayArea());

		// Standard stuff for Scene and Stage
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("GeoStyle.css").toExternalForm());
		stage.setTitle("Geography Project");
		stage.setScene(scene);
		stage.show();
	}

	private HBox dataSelection() {
		HBox hBox = new HBox();
		hBox.setSpacing(10);
		countryBox.setPrefSize(150, 25);
		stateBox.setPrefSize(150, 25);
		selectButton.setPrefSize(80, 25);
		selectStateButton.setPrefSize(80, 25);
		hBox.getChildren().addAll(countryBox, selectButton, stateBox, selectStateButton);
		return hBox;
	}

	private Pane dataEntry() {
		GridPane pane = new GridPane();
		pane.setHgap(30);
		pane.setVgap(10);
		formOfGovBox.setPrefSize(130, 25);
		pane.add(countryLabel, 0, 0);
		pane.add(countryTF, 0, 1);
		pane.add(stateLabel, 2, 0);
		pane.add(stateTF, 2, 1);
		pane.add(populationLabel, 0, 2);
		pane.add(populationTF, 0, 3);
		pane.add(areaLabel, 1, 2);
		pane.add(areaTF, 1, 3);
		pane.add(formOfGovLabel, 2, 2);
		formOfGovBox.getItems().addAll(GovernedRegion.formOfGov.values());
		pane.add(formOfGovBox, 2, 3);
		return pane;
	}

	private HBox buttonArea() {
		HBox hBox = new HBox();
		saveButton.setPrefSize(130, 25);
		deleteCountryButton.setPrefSize(130, 25);
		addStateButton.setPrefSize(130, 25);
		deleteStateButton.setPrefSize(130, 25);
		updateButton.setPrefSize(130, 25);
		hBox.setSpacing(10);
		hBox.getChildren().addAll(saveButton, deleteCountryButton, addStateButton, deleteStateButton, updateButton);
		return hBox;
	}

	private Pane displayArea() {
		GridPane pane = new GridPane();
		displayCountry.setPrefSize(350, 250);
		displayCountry.setEditable(false);
		displayStates.setPrefSize(350, 250);
		displayStates.setEditable(false);
		pane.add(displayCountry, 0, 0);
		pane.add(displayStates, 1, 0);
		return pane;
	}

	public void start() {
		stage.show();
	}
}
