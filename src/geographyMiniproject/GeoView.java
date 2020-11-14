package geographyMiniproject;

import javafx.geometry.Insets;

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
	Label areaLabel = new Label("Area in km" + '\u00B2');
	Label formOfGovLabel = new Label("Form of Government");

	TextField countryTF = new TextField();
	TextField stateTF = new TextField();
	TextField populationTF = new TextField();
	TextField areaTF = new TextField();

	TextArea displayCountry = new TextArea();
	TextArea displayStates = new TextArea();

	Button saveButton = new Button("Save Country");
	Button addStateButton = new Button("Add State to Country");
	Button deleteCountryButton = new Button("Delete Country");
	Button deleteStateButton = new Button("Delete State");
	Button updateCountryButton = new Button("Update Country");
	Button updateStateButton = new Button("Update State");

	ComboBox<String> countryBox = new ComboBox<>();
	ComboBox<String> stateBox = new ComboBox<>();
	ComboBox<GovernedRegion.formOfGov> formOfGovBox = new ComboBox<>();

	public GeoView(Stage stage, GeoModel model) {
		this.stage = stage;
		this.model = model;

		VBox root = new VBox();
		root.setId("rootStyle");
		root.setSpacing(15);
		root.getChildren().add(dataEntry());
		root.getChildren().add(dataSelection());
		root.getChildren().add(buttonArea());
		root.getChildren().add(displayArea());

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("GeoStyle.css").toExternalForm());
		stage.setTitle("Geography Project");
		stage.setScene(scene);
		stage.show();
	}

	private HBox dataSelection() {
		HBox hBox = new HBox();
		// hBox.setSpacing(50);
		countryBox.setPrefSize(150, 25);
		stateBox.setPrefSize(150, 25);
		HBox.setMargin(countryBox, new Insets(10, 20, 0, 40));
		HBox.setMargin(stateBox, new Insets(10, 20, 0, 40));
	//	countryBox.setValue("Select Country");
		countryBox.getSelectionModel().selectFirst();
	//	stateBox.setValue("Select State");
		stateBox.getSelectionModel().selectFirst();
		hBox.getChildren().addAll(countryBox, stateBox);
		return hBox;
	}

	private Pane dataEntry() {
		GridPane pane = new GridPane();
		GridPane.setMargin(countryLabel, new Insets(10, 5, 0, 10));
		GridPane.setMargin(stateLabel, new Insets(10, 5, 0, 5));
		GridPane.setMargin(populationLabel, new Insets(10, 5, 0, 10));
		GridPane.setMargin(areaLabel, new Insets(10, 5, 0, 5));
		GridPane.setMargin(formOfGovLabel, new Insets(10, 5, 0, 5));
		stateLabel.setId("stateLabel");
		countryLabel.setId("countryLabel");
		GridPane.setMargin(countryTF, new Insets(0, 5, 0, 5));
		GridPane.setMargin(saveButton, new Insets(0, 5, 0, 5));
		GridPane.setMargin(populationTF, new Insets(0, 5, 0, 5));
		pane.setHgap(30);
		pane.setVgap(5);
		formOfGovBox.setPrefSize(180, 25);
		pane.add(countryLabel, 0, 0);
		pane.add(countryTF, 0, 1);
		pane.add(stateLabel, 2, 0);
		pane.add(stateTF, 2, 1);
		pane.add(saveButton, 0, 2);
		pane.add(addStateButton, 2, 2);
		pane.add(populationLabel, 0, 3);
		pane.add(populationTF, 0, 4);
		pane.add(areaLabel, 1, 3);
		pane.add(areaTF, 1, 4);
		pane.add(formOfGovLabel, 2, 3);
		formOfGovBox.getItems().addAll(GovernedRegion.formOfGov.values());
		pane.add(formOfGovBox, 2, 4);
		return pane;
	}

	private HBox buttonArea() {
		HBox hBox = new HBox();
		saveButton.setPrefSize(130, 25);
		deleteCountryButton.setPrefSize(130, 25);
		addStateButton.setPrefSize(130, 25);
		deleteStateButton.setPrefSize(130, 25);
		updateCountryButton.setPrefSize(130, 25);
		updateStateButton.setPrefSize(130, 25);
		HBox.setMargin(updateCountryButton, new Insets(5, 5, 0, 30));
		HBox.setMargin(deleteCountryButton, new Insets(5, 20, 0, 25));
		HBox.setMargin(updateStateButton, new Insets(5, 5, 0, 30));
		HBox.setMargin(deleteStateButton, new Insets(5, 20, 0, 20));
		hBox.getChildren().addAll(updateCountryButton, deleteCountryButton, updateStateButton, deleteStateButton);
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
