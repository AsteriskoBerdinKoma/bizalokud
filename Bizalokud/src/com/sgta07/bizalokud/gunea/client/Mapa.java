package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.gwtext.client.core.Function;
import com.gwtext.client.util.JavaScriptObjectHelper;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.map.GoogleMap;
import com.gwtext.client.widgets.map.LatLonPoint;
import com.gwtext.client.widgets.map.MapPanel;
import com.gwtext.client.widgets.map.Marker;

public class Mapa extends MapPanel{

	MapPanel mapPanel;
	private boolean mapRendered;

	public Mapa(int width, int height){
		super();
		mapPanel = new GoogleMap();
		mapPanel.setBorder(false);
		mapPanel.setFrame(true);
		mapPanel.addLargeControls();
		mapPanel.setWidth(width);
		mapPanel.setHeight(height);
		
		mapRendered = false;
		
		mapPanel.addListener(MapPanel.MAP_RENDERED_EVENT, new Function() {
			public void execute() {
				mapRendered = true;
			}
		});

		mapPanel.addListener(new PanelListenerAdapter() {
			public void onResize(BoxComponent component, int adjWidth,
					int adjHeight, int rawWidth, int rawHeight) {

				if (mapRendered)
					mapPanel.resizeTo(mapPanel.getInnerWidth(), mapPanel
							.getInnerHeight());
			}
		});
	}

	public void setNeurria(int width, int height){
		if(mapRendered)
			mapPanel.resizeTo(width, height);
	}
	
	public native void updateMap(String locationAddress, JavaScriptObject llp,
			Mapa thisModule) /*-{
	var geo = new $wnd.GClientGeocoder();
	
	geo.getLocations(locationAddress, 
		function(response) 		// callback method to be executed when result arrives from server
		{
			if (!response || response.Status.code != 200) 
			{
	  				alert("Unable to geocode that address");
			} 
			else 
	      		{
		    		var place = response.Placemark[0];
		    		llp.lat = place.Point.coordinates[1];
		    		llp.lon = place.Point.coordinates[0];
	
		    		thisModule.@com.sgta07.bizalokud.gunea.client.Mapa::renderMap(Lcom/google/gwt/core/client/JavaScriptObject;)(llp);
	      		}
	     		}
	     	);
	}-*/;
	
	public void renderMap(JavaScriptObject jsObj) {
		double lat = Double.parseDouble(JavaScriptObjectHelper.getAttribute(
				jsObj, "lat"));
		double lon = Double.parseDouble(JavaScriptObjectHelper.getAttribute(
				jsObj, "lon"));

		LatLonPoint latLonPoint = new LatLonPoint(lat, lon);
		mapPanel.setCenterAndZoom(latLonPoint, 17);
		final Marker m = new Marker(latLonPoint);
		//m.setInfoBubble("Latitude: " + lat + "<br/>Longitude: " + lon);
		mapPanel.addMarker(m);
		// addListenerToMarker(m.toGoogle());

	}
	
	// private native void addListenerToMarker(JavaScriptObject markerJS)/*-{
	// var self = this;
	// $wnd.GEvent.addListener(markerJS, 'click', function(coords) {
	// alert('Marker at ' + coords + ' clicked');
	// });
	// }-*/;
	
	public MapPanel getMapPanel() {
		return mapPanel;
	}

	@Override
	protected JavaScriptObject doRenderMap(Element element) {
		// TODO Auto-generated method stub
		return null;
	}
}
