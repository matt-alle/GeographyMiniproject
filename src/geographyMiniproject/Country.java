package geographyMiniproject;

public class Country extends GovernedRegion {

	private final int countryID;
	private static int highestID = 0;
	private String countryName;

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
	public void setArea(int area) {
		super.setArea(area);
	}

	@Override
	public void setPopulation(int population) {
		super.setPopulation(population);
	}

	@Override
	public void setFormOfGov(formOfGov formOfGov) {
		super.setFormOfGov(formOfGov);
	}

	@Override
	public String toString() {
		String description;
		description = this.countryName + " (ID: " + this.countryID + ")" + "\nArea: " + super.getArea()
				+ "\nPopulation: " + super.getPopulation() + "\nForm of Government: " + super.getFormOfGov();
		return description;
	}

}
