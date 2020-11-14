package geographyMiniproject;

public class State extends GovernedRegion {

	private final int stateID;
	private static int highestID = 0;
	private String stateName;
	private String belongsToCountry;

	private static int getNextID() {
		return highestID++;
	}

	public State(String stateName, int area, int population, formOfGov formOfGov, String belongsToCountry) {
		super(area, population, formOfGov);
		this.stateID = getNextID();
		this.stateName = stateName;
		this.belongsToCountry = belongsToCountry;
	}

	public String getStateName() {
		return stateName;
	}

	public String getBelongsToCountry() {
		return belongsToCountry;
	}

	@Override
	public String toString() {
		String description;
		description = this.stateName + " (ID: " + this.stateID + ")" + "\nArea: " + super.getArea() + "\nPopulation: "
				+ super.getPopulation() + "\nForm of Government: " + super.getFormOfGov() + "\nBelongs to: "
				+ this.getBelongsToCountry();

		return description;
	}

}
