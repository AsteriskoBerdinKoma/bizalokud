package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.core.Function;
import com.gwtext.client.util.JavaScriptObjectHelper;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.map.GoogleMap;
import com.gwtext.client.widgets.map.LatLonPoint;
import com.gwtext.client.widgets.map.MapPanel;
import com.gwtext.client.widgets.map.Marker;

public class Mapa extends MapPanel {

	private MapPanel mapPanel;
	private boolean mapRendered;

	public Mapa() {
		super();
		mapPanel = new GoogleMap();
		mapPanel.setBorder(false);
		mapPanel.setFrame(true);
		mapPanel.addLargeControls();
		mapPanel.setId("mapPanel");
		
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

	public void setNeurria(int width, int height) {
		if (mapRendered)
			mapPanel.resizeTo(width, height);
	}

	public native void updateMap(String izena, String locationAddress, JavaScriptObject llp,
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
		    		llp.izena = izena;
		    		llp.helbidea = locationAddress;
	
		    		thisModule.@com.sgta07.bizalokud.gunea.client.Mapa::renderMap(Lcom/google/gwt/core/client/JavaScriptObject;)(llp);
	      		}
	     		}
	     	);
	}-*/;
	
	public void finkatu(GuneInfo gunea) {
		finkatu(gunea.getLat(), gunea.getLon());
	}
	
	public void finkatu(double lat, double lon) {
		ExtElement map = ExtElement.get("mapPanel");
		map.mask("Mapa eguneratzen. Itxaron mesedez.", true);
		
		LatLonPoint latLonPoint = new LatLonPoint(lat, lon);
		mapPanel.setCenterAndZoom(latLonPoint, 17);
		
		map.unmask();
	}
	
	public void markaGehitu(GuneInfo gunea){
		markaGehitu(gunea.getIzena(), gunea.getHelbidea(), gunea.getLat(), gunea.getLon());
	}
	
	public void markaGehitu(String izena, String helbidea, double lat,
			double lon) {
		ExtElement map = ExtElement.get("mapPanel");
		//map.mask("Mapa eguneratzen. Itxaron mesedez.", true);
		
		LatLonPoint latLonPoint = new LatLonPoint(lat, lon);
		Marker m = new Marker(latLonPoint);
		mapPanel.setCenterAndZoom(latLonPoint, 17);
		m.setInfoBubble("<h1>" + izena + "</h1><br><b>Helbidea:</b> " + helbidea);
		mapPanel.addMarker(m);
		
		//map.unmask();
	}

	public void renderMap(JavaScriptObject jsObj) {
		double lat = Double.parseDouble(JavaScriptObjectHelper.getAttribute(
				jsObj, "lat"));
		double lon = Double.parseDouble(JavaScriptObjectHelper.getAttribute(
				jsObj, "lon"));
		String izena = JavaScriptObjectHelper.getAttribute(jsObj, "izena");
		String helbidea = JavaScriptObjectHelper.getAttribute(jsObj, "helbidea");
		
		markaGehitu(izena, helbidea, lat, lon);
		finkatu(lat, lon);
		// addListenerToMarker(m.toGoogle());
	}

//	public native void addMarker(String locationAddress, JavaScriptObject llp, Mapa thisModule)/*-{
//					var geo = new $wnd.GClientGeocoder();
//										
//					geo.getLocations(locationAddress, 
//						function(response) 		// callback method to be executed when result arrives from server
//						{
//							if (!response || response.Status.code != 200) 
//							{
//				  				alert("Unable to geocode that address");
//							} 
//							else 
//				      		{
//					    		var place = response.Placemark[0];
//					    		
//					    		llp.lat = place.Point.coordinates[1];
//					    		llp.lon = place.Point.coordinates[0];
//					    		llp.hel = locationAddress;
//					    		
//					    		thisModule.@com.sgta07.bizalokud.gunea.client.Mapa::addMarker(Lcom/google/gwt/core/client/JavaScriptObject;)(llp);
//			      			}
//			   			}
//			   		);
//				}-*/;
//
//	private void addMarker(JavaScriptObject jsObj) {
//
//		double lat = Double.parseDouble(JavaScriptObjectHelper.getAttribute(
//				jsObj, "lat"));
//		double lon = Double.parseDouble(JavaScriptObjectHelper.getAttribute(
//				jsObj, "lon"));
//		
//		String hel = JavaScriptObjectHelper.getAttribute(jsObj, "hel");
//
//		LatLonPoint latLonPoint = new LatLonPoint(lat, lon);
//		
//		System.out.println(hel + ": " + lat + " " + lon);
//		
//		Marker m = new Marker(latLonPoint);
//		mapPanel.addMarker(m);
//		
//		mapPanel.setCenterAndZoom(lastPos, 0);
//	}

	// private native void addListenerToMarker(JavaScriptObject markerJS)/*-{
	// var self = this;
	// $wnd.GEvent.addListener(markerJS, 'click', function(coords) {
	// alert('Marker at ' + coords + ' clicked');
	// });
	// }-*/;

	public MapPanel getMapPanel() {
		return mapPanel;
	}

	protected JavaScriptObject doRenderMap(Element element) {
		// TODO Auto-generated method stub
		return null;
	}

	public static native void getGeoLocations(GuneInfo gunea, String helbidea) /*-{
	var geo = new $wnd.GClientGeocoder();
	
	geo.getLocations(helbidea, 
		function(response) 		// callback method to be executed when result arrives from server
		{
			if (!response || response.Status.code != 200) 
			{
  				alert("Unable to geocode that address");
			} 
			else 
      		{
	    		var place = response.Placemark[0];
	    		
	    		var lat = place.Point.coordinates[1];
	    		var lon = place.Point.coordinates[0];
	    		
	    		gunea.@com.sgta07.bizalokud.gunea.client.GuneInfo::setLatLon(DD)(lat, lon);
  			}
			}
		);
	}-*/;
}
