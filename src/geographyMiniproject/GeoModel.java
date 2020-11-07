package geographyMiniproject;

import java.util.ArrayList;

public class GeoModel {

	private ArrayList<Country> countries = new ArrayList<>();

	public void addCountry(Country country) {
		countries.add(country);
		System.out.println("Actual list:");
		for (int i = 0; i < countries.size(); i++) {
			System.out.println(countries.get(i).toString());
		}
	}

	public void deleteCountry(int x) {
		countries.remove(x);
	}

	public ArrayList<Country> getCountries() {
		return countries;
	}

	public void addStateToCountry(Country country, State state) {
		country.addState(state);
	}

}
