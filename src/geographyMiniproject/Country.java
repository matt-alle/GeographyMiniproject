package geographyMiniproject;

public class Country extends GovernedRegion {

	public enum states {
		Q, W, E, R
	};

	private states states; // not really? get from state class

	public Country(int area, int population, formOfGov formOfGov) {
		super(area, population, formOfGov);
	}

	// look at dis -> dafuq?
	public int getArea(int area) {
		int qq = super.getArea();
		return qq;
	}
}
