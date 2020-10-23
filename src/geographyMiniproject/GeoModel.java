package geographyMiniproject;

import java.util.ArrayList;

public class GeoModel {

	private ArrayList<Country> countries = new ArrayList<>();

	public void AddCountry(Country country) {
		countries.add(country);
		System.out.println("Actual list:");
		for (int i = 0; i < countries.size(); i++) {
			System.out.println("Area from list: " + countries.get(i).getArea());
		}
	}

}
