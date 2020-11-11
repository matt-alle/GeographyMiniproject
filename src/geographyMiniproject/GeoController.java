package geographyMiniproject;

public class GeoController {
	private GeoView view;
	private GeoModel model;

	private final int maxNameLength = 15;
	private final int minNameLength = 3;
	private final int maxPopulation = 1000000000;
	private final int maxArea = 500000000;

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
		});

		view.addStateButton.setOnAction((e) -> {
			if (view.countryBox.getSelectionModel().getSelectedIndex() >= 0) {
				int countryPosition = view.countryBox.getSelectionModel().getSelectedIndex();
				State state = new State(view.stateTF.getText(), Integer.valueOf(view.areaTF.getText()),
						Integer.valueOf(view.populationTF.getText()), view.formOfGovBox.getValue(),
						model.getCountries().get(countryPosition).getCountryName());
				model.addState(state);
				clearInputArea();
				updateComboBoxState();
				String stateList = model.createDisplayListOfStates(countryPosition);
				view.displayStates.setText(stateList);
				view.deleteStateButton.setDisable(false);
			}
		});

		view.countryBox.setOnAction((e) -> {
			if (view.countryBox.getSelectionModel().getSelectedIndex() >= 0) {
				int countryPosition = view.countryBox.getSelectionModel().getSelectedIndex();
				view.displayCountry.setText(model.getCountries().get(countryPosition).toString());
				String stateList = model.createDisplayListOfStates(countryPosition);
				view.displayStates.setText(stateList);
				view.deleteCountryButton.setDisable(false); // Item must be selected before deleting is possible
				updateComboBoxState();
			}
		});

		view.stateBox.setOnAction((e) -> {
			if (view.stateBox.getSelectionModel().getSelectedIndex() >= 0) {
				int statePosition = view.stateBox.getSelectionModel().getSelectedIndex();
				String countryName = view.countryBox.getSelectionModel().getSelectedItem();
				view.displayStates.setText(model.getStatesOfCountry(countryName).get(statePosition).toString());
				view.deleteStateButton.setDisable(false);
			}
		});

		// TODO: delete all States of a deleted Country
		view.deleteCountryButton.setDisable(true);
		view.deleteCountryButton.setOnAction((e) -> {
			int countryPosition = view.countryBox.getSelectionModel().getSelectedIndex();
			model.deleteCountry(countryPosition); // Also deletes all States of the Country
			updateComboBox();
			view.stateBox.getItems().clear();
			view.displayCountry.setText("--Deleted--");
			view.displayStates.setText("--Deleted--");
			view.deleteCountryButton.setDisable(true);
		});

		// TODO: ComboBox is not always updated correctly after deleting a State - still
		// shows it -> error if selected
		// or just clear entire box until another country is selected
		view.deleteStateButton.setDisable(true);
		view.deleteStateButton.setOnAction((e) -> {
			if (view.stateBox.getSelectionModel().getSelectedIndex() >= 0) {
				String stateName = view.stateBox.getSelectionModel().getSelectedItem();
				model.deleteState(stateName); // Also deletes all States of the Country
				updateComboBoxState();
				view.displayStates.setText("--Deleted--");
				view.deleteStateButton.setDisable(true);
			}
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
		// TODO: move all disable/enable button down here
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
	private void updateComboBoxState() {
		view.stateBox.getItems().clear();
		String boxItem = null;
		String countryName = view.countryBox.getSelectionModel().getSelectedItem();
		for (int i = 0; i < model.getStatesOfCountry(countryName).size(); i++) {
			boxItem = model.getStatesOfCountry(countryName).get(i).getStateName();
			view.stateBox.getItems().add(boxItem);
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

		if (newValue.length() >= minNameLength && newValue.length() <= maxNameLength) {
			validCountry = true;
		}
		countryValid = validCountry;
		enableDisableButton();
	}

	private void validateInputState(String newValue) {
		boolean validState = false;

		if (newValue.length() >= minNameLength && newValue.length() <= maxNameLength) {
			validState = true;
		}
		stateValid = validState;
		enableDisableButtonState();
	}

	// TODO combine validate area and population to validate NumberInput or
	// something (same with country/state?)
	private void validateInputArea(String newValue) {
		boolean validArea = true;
		for (int i = 0; i < newValue.length(); i++) {
			if (newValue.charAt(i) < '0' || newValue.charAt(i) > '9') {
				validArea = false;
			}
		}
		try {
			if (Integer.valueOf(newValue) <= 0 || Integer.valueOf(newValue) > maxArea)
				validArea = false;
		} catch (NumberFormatException e) {
			validArea = false;
		}
		areaValid = validArea;
		enableDisableButton();
		enableDisableButtonState();
	}

	private void validateInputPopulation(String newValue) {
		boolean validPopulation = true;
		for (int i = 0; i < newValue.length(); i++) {
			if (newValue.charAt(i) < '0' || newValue.charAt(i) > '9') {
				validPopulation = false;
			}
		}
		try {
			if (Integer.valueOf(newValue) <= 0 || Integer.valueOf(newValue) > maxPopulation)
				validPopulation = false;
		} catch (NumberFormatException e) {
			validPopulation = false;
		}
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
