package geographyMiniproject;

public class State extends GovernedRegion {

	private final int stateID;
	private static int highestID = 0;
	private String stateName;

	private static int getNextID() {
		return highestID++;
	}

	public State(String stateName, int area, int population, formOfGov formOfGov) {
		super(area, population, formOfGov);
		this.stateID = getNextID();
		this.stateName = stateName;
	}

}
