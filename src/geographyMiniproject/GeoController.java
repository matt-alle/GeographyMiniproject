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
			String stateList = model.createDisplayListOfStates(countryPosition);
			view.displayStates.setText(stateList);
		});

		view.selectButton.setOnAction((e) -> {
			if (view.countryBox.getSelectionModel().getSelectedIndex() >= 0) {
				int countryPosition = view.countryBox.getSelectionModel().getSelectedIndex();
				view.displayCountry.setText(model.getCountries().get(countryPosition).toString());
				String stateList = model.createDisplayListOfStates(countryPosition);
				view.displayStates.setText(stateList);
				view.deleteCountryButton.setDisable(false); // Item must be selected before deleting is possible
				updateComboBoxState(countryPosition);
			}
		});

		// TODO: ERROR: always displays States of the first country / or does not work at all atm
		view.selectStateButton.setOnAction((e) -> {
			if (view.stateBox.getSelectionModel().getSelectedIndex() >= 0) {
				// int statePosition = view.stateBox.getSelectionModel().getSelectedIndex();
				String stateName = view.stateBox.getSelectionModel().getSelectedItem();
			//	model.getStateByName(stateName);
				view.displayStates.setText(model.getStateByName(stateName).toString());
				view.deleteStateButton.setDisable(false);
			}
		});

		view.deleteCountryButton.setDisable(true);
		view.deleteCountryButton.setOnAction((e) -> {
			int countryPosition = view.countryBox.getSelectionModel().getSelectedIndex();
			model.deleteCountry(countryPosition); // Also deletes all States of the Country
			updateComboBox();
			view.stateBox.getItems().clear();
			view.displayCountry.setText("--Deleted--");
			view.displayStates.setText("--Deleted--");
			view.deleteCountryButton.setDisable(true);
			if (model.getCountries().size() < 1)
				view.selectButton.setDisable(true);
		});

		// TODO:not working right - avoid repetition
		view.deleteStateButton.setDisable(true);
		view.deleteStateButton.setOnAction((e) -> {
			int statePosition = view.stateBox.getSelectionModel().getSelectedIndex();
			int countryPosition = view.countryBox.getSelectionModel().getSelectedIndex();
			model.deleteCountry(statePosition); // Also deletes all States of the Country
			updateComboBoxState(countryPosition);
			view.stateBox.getItems().clear();
			view.displayStates.setText("--Deleted--");
			view.deleteStateButton.setDisable(true);
			if (model.getStates().size() < 1)
				view.selectStateButton.setDisable(true);
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

		// Input control - enable/disable buttons
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
		boolean valid = stateValid && areaValid && populationValid && !countryValid; // doesn't work until sth in
																						// "State" is changed again
		view.addStateButton.setDisable(!valid);
	}
}
