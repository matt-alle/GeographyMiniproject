package geographyMiniproject;

public class GeoController {
	private GeoView view;
	private GeoModel model;

	private boolean countryValid;
	private boolean areaValid;
	private boolean populationValid;

	public GeoController(GeoModel model, GeoView view) {
		this.model = model;
		this.view = view;

		view.saveButton.setOnAction((e) -> {
			Country counrty = new Country(view.countryTF.getText(), Integer.valueOf(view.areaTF.getText()),
					Integer.valueOf(view.populationTF.getText()), view.formOfGovBox.getValue());
			model.AddCountry(counrty);
			view.countryTF.clear();
			view.areaTF.clear();
			view.populationTF.clear();
			updateComboBox();
		});

		view.selectButton.setOnAction((e) -> {
			int x = view.countryBox.getSelectionModel().getSelectedIndex();
			view.displayCountry.setText(model.getCountries().get(x).toString());
		});

		view.saveButton.setDisable(true);
		view.countryTF.textProperty().addListener((observable, oldValue, newValue) -> validateInputCountry(newValue));
		view.areaTF.textProperty().addListener((observable, oldValue, newValue) -> validateInputArea(newValue));
		view.populationTF.textProperty()
				.addListener((observable, oldValue, newValue) -> validateInputPopulation(newValue));
	}

	private void updateComboBox() {
		String boxItem = null;
		for (int i = 0; i < model.getCountries().size(); i++) {
			boxItem = model.getCountries().get(i).getCountryName();
		}
		view.countryBox.getItems().addAll(boxItem);
	}

	private void validateInputCountry(String newValue) {
		boolean validCountry = false;

		if (newValue.length() > 2 && newValue.length() < 25) {
			validCountry = true;
		}
		countryValid = validCountry;
		enableDisableButton();
	}

	private void validateInputArea(String newValue) {
		boolean validArea = true;
		for (int i = 0; i < newValue.length(); i++) {
			if (newValue.charAt(i) < '0' || newValue.charAt(i) > '9') {
				validArea = false;
			}
		}
		if (newValue.length() == 0)
			validArea = false;
		areaValid = validArea;
		enableDisableButton();
	}

	// (repetition - maybe change
	private void validateInputPopulation(String newValue) {
		boolean validPopulation = true;
		for (int i = 0; i < newValue.length(); i++) {
			if (newValue.charAt(i) < '0' || newValue.charAt(i) > '9') {
				validPopulation = false;
			}
		}
		if (newValue.length() == 0)
			validPopulation = false;
		populationValid = validPopulation;
		enableDisableButton();
	}

	private void enableDisableButton() {
		boolean valid = countryValid && areaValid && populationValid;
		view.saveButton.setDisable(!valid);
	}
}
