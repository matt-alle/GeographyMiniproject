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
			updateComboBox(0);
			view.countryBox.getSelectionModel().selectLast();
		});

		view.addStateButton.setOnAction((e) -> {
			if (view.countryBox.getSelectionModel().getSelectedIndex() >= 0) {
				int countryPosition = view.countryBox.getSelectionModel().getSelectedIndex();
				State state = new State(view.stateTF.getText(), Integer.valueOf(view.areaTF.getText()),
						Integer.valueOf(view.populationTF.getText()), view.formOfGovBox.getValue(),
						model.getCountries().get(countryPosition).getCountryName());
				model.addState(state);
				clearInputArea();
				updateComboBoxState(0);
				view.stateBox.getSelectionModel().selectLast();
				String stateList = model.createDisplayListOfStates(countryPosition);
				view.displayStates.setText(stateList);
			}
		});

		view.countryBox.setOnAction((e) -> {
			if (view.countryBox.getSelectionModel().getSelectedIndex() >= 0) {
				int countryPosition = view.countryBox.getSelectionModel().getSelectedIndex();
				view.displayCountry.setText(model.getCountries().get(countryPosition).toString());
				updateComboBoxState(0);
				String stateList = model.createDisplayListOfStates(countryPosition);
				view.displayStates.setText(stateList);
				view.deleteCountryButton.setDisable(false); // Item must be selected before deleting is possible
				view.updateStateButton.setDisable(true);
			}
		});

		view.stateBox.setOnAction((e) -> {
			if (view.stateBox.getSelectionModel().getSelectedIndex() >= 0) {
				int statePosition = view.stateBox.getSelectionModel().getSelectedIndex();
				String countryName = view.countryBox.getSelectionModel().getSelectedItem();
				view.displayStates.setText(model.getStatesOfCountry(countryName).get(statePosition).toString());
				view.deleteStateButton.setDisable(false);
				view.updateStateButton.setDisable(false);
			}
		});

		view.deleteCountryButton.setDisable(true);
		view.deleteCountryButton.setOnAction((e) -> {
			int countryPosition = view.countryBox.getSelectionModel().getSelectedIndex();
			String countryName = view.countryBox.getSelectionModel().getSelectedItem();
			model.deleteStatesOfCountry(countryName);
			model.deleteCountry(countryPosition); // Also deletes all States of the Country
			updateComboBox(countryPosition - 1);
			view.stateBox.getItems().clear();
			view.displayCountry.setText("--Deleted--");
			view.displayStates.setText("--Deleted--");
			view.deleteCountryButton.setDisable(true);
			view.updateStateButton.setDisable(true);
		});

		view.deleteStateButton.setDisable(true);
		view.deleteStateButton.setOnAction((e) -> {
			if (view.stateBox.getSelectionModel().getSelectedIndex() >= 0) {
				String stateName = view.stateBox.getSelectionModel().getSelectedItem();
				model.deleteState(stateName); // Also deletes all States of the Country
				updateComboBoxState(0);
				view.displayStates.setText("--Deleted--");
				view.deleteStateButton.setDisable(true);
				view.updateStateButton.setDisable(true);
			}
		});

		view.updateCountryButton.setOnAction((e) -> {
			if (view.countryBox.getSelectionModel().getSelectedIndex() >= 0) {
				int countryPosition = view.countryBox.getSelectionModel().getSelectedIndex();
				Country country = model.getCountries().get(countryPosition);
				if (view.areaTF.getText() != "")
					country.setArea(Integer.valueOf(view.areaTF.getText()));
				if (view.populationTF.getText() != "")
					country.setPopulation(Integer.valueOf(view.populationTF.getText()));
				if (view.formOfGovBox.getValue() != null)
					country.setFormOfGov(view.formOfGovBox.getValue());
				view.displayCountry.setText(country.toString());
				clearInputArea();
				updateComboBox(countryPosition);
			}
		});

		view.updateStateButton.setOnAction((e) -> {
			if (view.stateBox.getSelectionModel().getSelectedIndex() >= 0) {
				String countryName = view.countryBox.getSelectionModel().getSelectedItem();
				int statePosition = view.stateBox.getSelectionModel().getSelectedIndex();
				State state = model.getStatesOfCountry(countryName).get(statePosition);
				if (countryName.equals(null))
					System.out.println("nonono");
				if (view.areaTF.getText() != "")
					state.setArea(Integer.valueOf(view.areaTF.getText()));
				if (view.populationTF.getText() != "")
					state.setPopulation(Integer.valueOf(view.populationTF.getText()));
				if (view.formOfGovBox.getValue() != null)
					state.setFormOfGov(view.formOfGovBox.getValue());
				view.displayStates.setText(state.toString());
				clearInputArea();
			}
		});

		/*
		 * Input control - enable/disable buttons
		 */
		view.saveButton.setDisable(true);
		view.addStateButton.setDisable(true);
		view.updateCountryButton.setDisable(true);
		view.updateStateButton.setDisable(true);
		view.countryTF.textProperty().addListener((observable, oldValue, newValue) -> validateInputCountry(newValue));
		view.stateTF.textProperty().addListener((observable, oldValue, newValue) -> validateInputState(newValue));
		view.areaTF.textProperty().addListener((observable, oldValue, newValue) -> validateInputArea(newValue));
		view.populationTF.textProperty()
				.addListener((observable, oldValue, newValue) -> validateInputPopulation(newValue));
	}

	// Always removes entire ComboBox list and refills it
	private void updateComboBox(int selectIndex) {
		view.countryBox.getItems().clear();
		String boxItem = null;
		for (int i = 0; i < model.getCountries().size(); i++) {
			boxItem = model.getCountries().get(i).getCountryName();
			view.countryBox.getItems().add(boxItem);
		}
		view.countryBox.getSelectionModel().select(selectIndex);
	}

	// Updates Box and only displays States which belong to the selected Country
	private void updateComboBoxState(int selectedIndex) {
		view.stateBox.getItems().clear();
		String boxItem = null;
		String countryName = view.countryBox.getSelectionModel().getSelectedItem();
		for (int i = 0; i < model.getStatesOfCountry(countryName).size(); i++) {
			boxItem = model.getStatesOfCountry(countryName).get(i).getStateName();
			view.stateBox.getItems().add(boxItem);
		}
		view.stateBox.getSelectionModel().select(selectedIndex);
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
			// prevent same Country twice
			for (int i = 0; i < model.getCountries().size(); i++) {
				if (newValue.equals(model.getCountries().get(i).getCountryName()))
					validCountry = false;
			}
		}
		countryValid = validCountry;
		enableDisableButton();
	}

	private void validateInputState(String newValue) {
		boolean validState = false;

		if (newValue.length() >= minNameLength && newValue.length() <= maxNameLength) {
			validState = true;
			for (int i = 0; i < model.getStates().size(); i++) {
				if (newValue.equals(model.getStates().get(i).getStateName()))
					validState = false;
			}
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
		try {
			if (Integer.valueOf(newValue) <= 0 || Integer.valueOf(newValue) > maxArea)
				validArea = false;
		} catch (NumberFormatException e) {
			validArea = false;
		}
		areaValid = validArea;
		enableDisableButton();
		enableDisableButtonState();
		enableDisableButtonUpdateCountry();
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
		enableDisableButtonUpdateCountry();
	}

	private void enableDisableButton() {
		boolean valid = countryValid && areaValid && populationValid;
		view.saveButton.setDisable(!valid);
	}

	private void enableDisableButtonState() {
		boolean valid = stateValid && areaValid && populationValid && !countryValid;
		view.addStateButton.setDisable(!valid);
	}

	private void enableDisableButtonUpdateCountry() {
		boolean valid = (areaValid || populationValid) && view.countryTF.getText() == ""
				&& view.stateTF.getText() == "";
		view.updateCountryButton.setDisable(!valid);
		view.updateStateButton.setDisable(!valid);
	}
}
