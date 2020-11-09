package geographyMiniproject;

public class GeoController {
	private GeoView view;
	private GeoModel model;

	private boolean countryValid;
	private boolean stateValid;
	private boolean areaValid;
	private boolean populationValid;

	public GeoController(GeoModel model, GeoView view) {
		this.model = model;
		this.view = view;

		view.saveButton.setOnAction((e) -> {
			Country counrty = new Country(view.countryTF.getText(), Integer.valueOf(view.areaTF.getText()),
					Integer.valueOf(view.populationTF.getText()), view.formOfGovBox.getValue());
			model.addCountry(counrty);
			clearInputArea();
			updateComboBox();
			view.selectButton.setDisable(false);
		});

		view.addStateButton.setOnAction((e) -> {
			int countryPosition = view.countryBox.getSelectionModel().getSelectedIndex();
			State state = new State(view.stateTF.getText(), Integer.valueOf(view.areaTF.getText()),
					Integer.valueOf(view.populationTF.getText()), view.formOfGovBox.getValue(),
					model.getCountries().get(countryPosition).getCountryName());
			model.addState(state);
			clearInputArea();
			updateComboBoxState(countryPosition);
		});

		view.selectButton.setOnAction((e) -> {
			if (view.countryBox.getSelectionModel().getSelectedIndex() >= 0) {
				int countryPosition = view.countryBox.getSelectionModel().getSelectedIndex();
				view.displayCountry.setText(model.getCountries().get(countryPosition).toString());
				
				//TODO -> put that in Model and display only States which belong to Country
				String stateList = "All States:" + "\n";
				for (int i = 0; i < model.getStates().size(); i++) {
					String state = model.getStates().get(i).getStateName();
					stateList += state;
					stateList += "\n";
				}
				
				view.displayStates.setText(stateList);
				view.deleteButton.setDisable(false); // Item must be selected before deleting is possible
				updateComboBoxState(countryPosition);
			}
		});
		
		view.selectStateButton.setOnAction((e) -> {
			if (view.stateBox.getSelectionModel().getSelectedIndex() >= 0) {
				int statePosition = view.stateBox.getSelectionModel().getSelectedIndex();
				view.displayStates.setText(model.getStates().get(statePosition).toString());
			}
		});

		view.deleteButton.setDisable(true);
		view.deleteButton.setOnAction((e) -> {
			int countryPosition = view.countryBox.getSelectionModel().getSelectedIndex();
			model.deleteCountry(countryPosition);
			updateComboBox();
			view.displayCountry.setText("--Deleted--");
			view.deleteButton.setDisable(true);
			if (model.getCountries().size() < 1)
				view.selectButton.setDisable(true);
		});

		// TODO
		// how to read file when opened? .sav the right option? / do NOT use this button
		view.updateButton.setOnAction(event -> {
			int x = model.getCountries().size();
			String in = "";
			for (int i = 0; i < x; i++) {
				in += model.getCountries().get(i).toString();
				in += "\n" + "-------------" + "\n";
			}
			model.setSaveFile(in);
		});

		//Input control - enable/disable buttons
		view.saveButton.setDisable(true);
		view.addStateButton.setDisable(true);
		view.countryTF.textProperty().addListener((observable, oldValue, newValue) -> validateInputCountry(newValue));
		view.stateTF.textProperty().addListener((observable, oldValue, newValue) -> validateInputState(newValue));
		view.areaTF.textProperty().addListener((observable, oldValue, newValue) -> validateInputArea(newValue));
		view.populationTF.textProperty()
				.addListener((observable, oldValue, newValue) -> validateInputPopulation(newValue));
	}

	// Always removes entire ComboBox list and refills it
	private void updateComboBox() {
		view.countryBox.getItems().clear();
		String boxItem = null;
		for (int i = 0; i < model.getCountries().size(); i++) {
			boxItem = model.getCountries().get(i).getCountryName();
			view.countryBox.getItems().add(boxItem);
		}
	}

	// Updates Box and only displays States which belong to the selected Country
	private void updateComboBoxState(int countryPosition) {
		view.stateBox.getItems().clear();
		String boxItem = null;
		for (int i = 0; i < model.getStates().size(); i++) {
			if (model.getStates().get(i).getBelongsToCountry() == model.getCountries().get(countryPosition)
					.getCountryName()) {
				boxItem = model.getStates().get(i).getStateName();
				view.stateBox.getItems().add(boxItem);
			}
		}
	}

	private void clearInputArea() {
		view.countryTF.clear();
		view.areaTF.clear();
		view.populationTF.clear();
		view.stateTF.clear();
	}

	private void validateInputCountry(String newValue) {
		boolean validCountry = false;

		if (newValue.length() > 2 && newValue.length() < 25) {
			validCountry = true;
		}
		countryValid = validCountry;
		enableDisableButton();
	}

	private void validateInputState(String newValue) {
		boolean validState = false;

		if (newValue.length() > 2 && newValue.length() < 25) {
			validState = true;
		}
		stateValid = validState;
		enableDisableButtonState();
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
		enableDisableButtonState();
	}

	// TODO (repetition - maybe change)
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
		enableDisableButtonState();
	}

	private void enableDisableButton() {
		boolean valid = countryValid && areaValid && populationValid;
		view.saveButton.setDisable(!valid);
	}

	private void enableDisableButtonState() {
		boolean valid = stateValid && areaValid && populationValid;
		view.addStateButton.setDisable(!valid);
	}
}
