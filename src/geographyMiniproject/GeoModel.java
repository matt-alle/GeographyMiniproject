package geographyMiniproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GeoModel {

	private static String COUNTRIES = "Countries.sav";
	private String ProjectData;

	private ArrayList<Country> countries = new ArrayList<>();
	private ArrayList<State> states = new ArrayList<>();

	public void addCountry(Country country) {
		countries.add(country);
	}

	public void deleteCountry(int delPosition) {
		for (int i = 0; i < states.size(); i++) {
			if (countries.get(delPosition).getCountryName() == states.get(i).getBelongsToCountry()) {
				states.remove(i);
			}
		}
		countries.remove(delPosition);
	}

	public ArrayList<Country> getCountries() {
		return countries;
	}

	public void addState(State state) {
		states.add(state);
	}

	public void deleteState(int delPosition) {
		states.remove(delPosition);
	}

	public ArrayList<State> getStates() {
		return states;
	}

	public State getStateByName(String stateName) {
		State foundState = null;
		for (int i = 0; i <= states.size(); i++) {
			if (states.get(i).getStateName() == stateName) {
				foundState = states.get(i);
			}
		}
		return foundState;
	}

	// Creates a list of all the states which belong to the selected Country
	public String createDisplayListOfStates(int countryPosition) {
		String stateList = "All States:" + "\n";
		String selectedCountry = countries.get(countryPosition).getCountryName();
		for (int i = 0; i < states.size(); i++) {
			String countryOfState = states.get(i).getBelongsToCountry();
			if (countryOfState == selectedCountry) {
				String state = states.get(i).getStateName();
				stateList += state;
				stateList += "\n";
			}
		}
		return stateList;
	}

	// Save/Read File
	public void setSaveFile(String in) {
		ProjectData = "Save file contents: " + in;
	}

	public void readSaveFile() {
		File file = new File(COUNTRIES);
		String data = "";
		try (BufferedReader fileIn = new BufferedReader(new FileReader(file))) {
			data = fileIn.readLine();
		} catch (FileNotFoundException e) {
			data = "Save file does not exist";
		} catch (IOException e) {
			data = e.getClass().toString();
		}
		this.ProjectData = data;
	}

	public void writeSaveFile() {
		File file = new File(COUNTRIES);
		try (FileWriter fileOut = new FileWriter(file)) {
			fileOut.write(ProjectData + "\n");
			fileOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
