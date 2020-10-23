package geographyMiniproject;

import geographyMiniproject.GovernedRegion.formOfGov;

public class GeoController {
	private GeoView view;
	private GeoModel model;

	public GeoController(GeoModel model, GeoView view) {
		this.model = model;
		this.view = view;

		view.copyButton.setOnAction((e) -> {
			Country counrty1 = new Country(Integer.valueOf(view.tfArea.getText()), 1, formOfGov.A);
			model.AddCountry(counrty1);
			view.text.setText(Integer.toString(counrty1.getArea()));
			view.tfArea.clear();
		});
	}

}
