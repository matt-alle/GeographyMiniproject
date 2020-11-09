package geographyMiniproject;

import java.util.ArrayList;

public class Country extends GovernedRegion {

	private final int countryID;
	private static int highestID = 0;
	private String countryName;
//	private ArrayList<State> states = new ArrayList<>();

	private static int getNextID() {
		return highestID++;
	}

	public Country(String countryName, int area, int population, formOfGov formOfGov) {
		super(area, population, formOfGov);
		this.countryID = getNextID();
		this.countryName = countryName;
	}

	public String getCountryName() {
		return countryName;
	}

	@Override
	public int getArea() {
		return super.getArea();
	}

	@Override
	public int getPopulation() {
		return super.getPopulation();
	}

	@Override
	public String toString() {
		String description;
		description = this.countryName + " (ID: " + this.countryID + ")" + "\n" + "Area: " + super.getArea()
				+ " / Population: " + super.getPopulation() + "\n" + "Form of Government: " + super.getFormOfGov();
		return description;
	}

}
