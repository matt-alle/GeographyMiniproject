package geographyMiniproject;

public class GeoController {
	private GeoView view;
	private GeoModel model;

	public GeoController(GeoModel model, GeoView view) {
		this.model = model;
		this.view = view;
	}
}
