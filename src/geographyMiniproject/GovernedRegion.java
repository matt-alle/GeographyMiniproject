package geographyMiniproject;

public abstract class GovernedRegion {

	public enum formOfGov {
		A, B, C
	};

	private int area;
	private int population;
	private formOfGov formOfGov;

	public GovernedRegion(int area, int population, formOfGov formOfGov) {
		if (area > 0 & area < 10000) {
			this.area = area;
		} else {
			System.out.println("tresh area");
			this.area = -9999;
		}
		this.population = population;
		this.formOfGov = formOfGov;
	}

	public int getArea() {
		return area;
	}

	// ev. no need for setters
	public void setArea(int area) {
		this.area = area;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public formOfGov getFormOfGov() {
		return formOfGov;
	}

	public void setFormOfGov(formOfGov formOfGov) {
		this.formOfGov = formOfGov;
	}

}