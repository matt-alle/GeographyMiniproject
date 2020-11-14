package geographyMiniproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import geographyMiniproject.GovernedRegion.formOfGov;

public class GeoModel {

	private static String COUNTRIES = "Countries.sav";
	private static String STATES = "States.sav";
	private static String SEPARATOR = ";";

	private ArrayList<Country> countries = new ArrayList<>();
	private ArrayList<State> states = new ArrayList<>();
	private ArrayList<State> statesOfCountry = new ArrayList<>();

	public void addCountry(Country country) {
		countries.add(country);
	}

	public void deleteCountry(int delPosition) {
		System.out.println("REMOVED COUNTRY: " + countries.get(delPosition).getCountryName());
		countries.remove(delPosition);
	}

	public ArrayList<Country> getCountries() {
		return countries;
	}

	public void addState(State state) {
		states.add(state);
	}

	public void deleteState(String stateName) {
		for (int i = 0; i < states.size(); i++) {
			if (states.get(i).getStateName().equals(stateName))
				states.remove(i);
		}
	}

	public void deleteStatesOfCountry(String countryName) {
		for (int i = states.size() - 1; i >= 0; i--) {
			if (states.get(i).getBelongsToCountry().equals(countryName)) {
				System.out.println("REMOVED STATE: " + states.get(i).getStateName());
				states.remove(i);
			}
		}
	}

	public ArrayList<State> getStates() {
		return states;
	}

	public ArrayList<State> getStatesOfCountry(String countryName) {
		statesOfCountry.clear();
		for (int i = 0; i < states.size(); i++) {
			if (states.get(i).getBelongsToCountry().equals(countryName)) {
				statesOfCountry.add(states.get(i));
			}
		}
		return statesOfCountry;
	}

	// Creates a list of all the states which belong to the selected Country
	public String createDisplayListOfStates(int countryPosition) {
		String stateList = "All States:" + "\n";
		String selectedCountry = countries.get(countryPosition).getCountryName();
		for (int i = 0; i < states.size(); i++) {
			String countryOfState = states.get(i).getBelongsToCountry();
			if (countryOfState.equals(selectedCountry)) {
				String state = states.get(i).getStateName();
				stateList += state;
				stateList += "\n";
			}
		}
		return stateList;
	}

	/*
	 * Save/Read File Countries
	 */
	public void readSaveFileCountries() {
		File file = new File(COUNTRIES);
		String data = "";
		try (BufferedReader fileIn = new BufferedReader(new FileReader(file))) {
			String line = fileIn.readLine();
			while (line != null) {
				Country country = readCountry(line);
				countries.add(country);
				line = fileIn.readLine();
			}
		} catch (FileNotFoundException e) {
			data = "Save file does not exist";
		} catch (IOException e) {
			data = e.getClass().toString();
		}
	}

	public void writeSaveFileCountries() {
		File file = new File(COUNTRIES);
		try (FileWriter fileOut = new FileWriter(file)) {
			for (Country country : countries) {
				String line = writeCountry(country);
				fileOut.write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String writeCountry(Country country) {
		String line = country.getCountryName() + SEPARATOR + country.getArea() + SEPARATOR + country.getPopulation()
				+ SEPARATOR + country.getFormOfGov() + "\n";
		return line;
	}

	public Country readCountry(String line) {
		String[] attributes = line.split(SEPARATOR);
		String name = "-not found-";
		int area = -999;
		int population = -999;
		formOfGov formOfGov = null;
		try {
			name = attributes[0];
			area = Integer.valueOf(attributes[1]);
			population = Integer.valueOf(attributes[2]);
			formOfGov = null;
		} catch (Exception e) {
			name = "-Error in Line-";
			System.out.println("Incomplete Line");
		}
		Country country = new Country(name, area, population, formOfGov);
		return country;
	}

	/*
	 * Save/Read File States
	 */
	public void readSaveFileStates() {
		File file = new File(STATES);
		String data = "";
		states.clear();
		statesOfCountry.clear();
		try (BufferedReader fileIn = new BufferedReader(new FileReader(file))) {
			String line = fileIn.readLine();
			while (line != null) {
				State state = readState(line);
				states.add(state);
				line = fileIn.readLine();
			}
		} catch (FileNotFoundException e) {
			data = "Save file does not exist";
		} catch (IOException e) {
			data = e.getClass().toString();
		}
	}

	public void writeSaveFileStates() {
		File file = new File(STATES);
		try (FileWriter fileOut = new FileWriter(file)) {
			for (State state : states) {
				String line = writeState(state);
				fileOut.write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String writeState(State state) {
		String line = state.getStateName() + SEPARATOR + state.getArea() + SEPARATOR + state.getPopulation() + SEPARATOR
				+ state.getFormOfGov() + SEPARATOR + state.getBelongsToCountry() + "\n";
		return line;
	}

	public State readState(String line) {
		String[] attributes = line.split(SEPARATOR);
		String name = "-not found-";
		int area = -999;
		int population = -999;
		formOfGov formOfGov = null;
		String belongsTo = "-";
		try {
			name = attributes[0];
			area = Integer.valueOf(attributes[1]);
			population = Integer.valueOf(attributes[2]);
			formOfGov = null;
			belongsTo = attributes[4];
		} catch (Exception e) {
			name = "-Error in Line-";
			System.out.println("Incomplete Line");
		}
		State state = new State(name, area, population, formOfGov, belongsTo);
		return state;
	}

}
