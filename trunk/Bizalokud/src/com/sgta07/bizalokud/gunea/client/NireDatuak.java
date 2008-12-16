package com.sgta07.bizalokud.gunea.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.core.NameValuePair;
import com.gwtext.client.util.Format;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.ComponentListenerAdapter;
import com.gwtext.client.widgets.grid.GridView;
import com.gwtext.client.widgets.grid.PropertyGridPanel;
import com.gwtext.client.widgets.grid.event.PropertyGridPanelListener;
import com.gwtext.client.widgets.layout.FitLayout;

public class NireDatuak extends BarnePanela {

	private Panel panel;
	private PropertyGridPanel grid;
	private ExtElement element;
	private boolean aldaketakDaude;
	private HashMap<String,String> datuak;

	public NireDatuak(Gunea owner) {
		super(owner);
		panel = this;
		aldaketakDaude = false;
		panel.setTitle("Nire Informazioa");
		panel.setLayout(new FitLayout());
		panel.setBorder(false);
		panel.setAutoScroll(true);
		panel.setCollapsible(false);
		panel.setPaddings(15);
		
		datuak = new HashMap<String, String>();
		datuak.put("Izena", "");
		datuak.put("Abizenak", "");
		datuak.put("ePosta", "");
		datuak.put("Telf. Zenbakia", "");
		
		grid = new PropertyGridPanel();
		grid.setId("props-grid");
		grid.setNameText("Properties Grid");
		grid.setWidth(300);
		grid.setAutoHeight(true);
		grid.setSorted(false);

		GridView view = new GridView();
		view.setForceFit(true);
		view.setScrollOffset(2); // the grid will never have scrollbars
		grid.setView(view);

		NameValuePair[] source = new NameValuePair[4];
		source[0] = new NameValuePair("Izena", "Zure Izena");
		source[1] = new NameValuePair("Abizenak", "Zure Abizenak");
		source[2] = new NameValuePair("ePosta", "Zure ePosta");
		source[3] = new NameValuePair("Telf. Zenbakia",
				"Zure Telefono Zenbakia");

		grid.setSource(source);
		grid.addPropertyGridPanelListener(new PropertyGridPanelListener() {
			public boolean doBeforePropertyChange(PropertyGridPanel source,
					String recordID, Object value, Object oldValue) {
				return true;
			}

			public void onPropertyChange(PropertyGridPanel source,
					String recordID, Object value, Object oldValue) {
						datuak.put(recordID.toString(), value.toString());
						aldaketakDaude=true;
						
			}
		});
		panel.add(grid);
		Toolbar tb = new Toolbar();
		ToolbarButton aldaketak = new ToolbarButton("Aldaketak Gorde");
		ToolbarButton utzi = new ToolbarButton("Utzi");
		aldaketak.addListener(new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				grid.stopEditing();
				if (aldaketakDaude) {
					aldaketakGorde();
				}
			}
		});
		
		utzi.addListener(new ButtonListenerAdapter() {
			public void onClick(Button button, EventObject e) {
				grid.stopEditing();
				if (aldaketakDaude) {
					MessageBox.confirm("Galdera",
							"Datuetan egindako aldaketak gorde nahi dituzu?",
							new MessageBox.ConfirmCallback() {
								public void execute(String btnID) {
									if (btnID.equals("yes"))
										aldaketakGorde();
									else if (btnID.equals("no"))
										aldaketakDaude = false;
										jabea.getCenterPanelCardLayout();
								}	
							});
				} else{
					aldaketakDaude = false;
					jabea.getCenterPanelCardLayout();
				}
			}
		});

		tb.addButton(aldaketak);
		tb.addFill();
		tb.addButton(utzi);
		panel.setBottomToolbar(tb);

		this.addListener(new ComponentListenerAdapter() {
			public void onShow(Component component) {
				datuakEguneratu();
			}
		});

	}
	
	private void aldaketakGorde() {
		element = new ExtElement(getElement());
		element.mask("Datuak eguneratzen. Itxaron mesedez...", true);
		GuneaService.Util.getInstance().erabDatuakEguneratu(datuak,jabea.getErabNan(), new AsyncCallback<Boolean>(){

			public void onFailure(Throwable caught) {
				element.unmask();
				MessageBox
				.show(new MessageBoxConfig() {
					{
						setTitle("Errorea Komunikazioan");
						setMsg("Zerbitzariarekin komunikatzeko arazoak daude.\n Saiatu berriz beranduago.");
						setButtons(MessageBox.OK);
						setIconCls(MessageBox.ERROR);
					}
				});
			}

			public void onSuccess(Boolean result) {
				element.unmask();
				MessageBox
				.show(new MessageBoxConfig() {
					{
						setTitle("Datuak Eguneratuta");
						setMsg("Zure datuak eguneratu egin dira.");
						setButtons(MessageBox.OK);
						setIconCls(MessageBox.INFO);
						jabea.setErabiltzaileDatuak(jabea.getErabNan(), datuak.get("Izena"), datuak.get("Abizenak"), datuak.get("ePosta"), datuak.get("Telf. Zenbakia"), jabea.isAdmin());
						datuakEguneratu();
					}
				});
				aldaketakDaude = false;
				
			}
			
		});
		
	}

	public void datuakEguneratu() {
		element = new ExtElement(getElement());
		element.mask("Itxaron mesedez", true);
		if (jabea.isErabIdentif()) {
			NameValuePair[] source = new NameValuePair[4];
			source[0] = new NameValuePair("Izena", jabea.getErabIzena());
			source[1] = new NameValuePair("Abizenak", jabea.getErabAbizen());
			source[2] = new NameValuePair("ePosta", jabea.getErabEposta());
			source[3] = new NameValuePair("Telf. Zenbakia", jabea
					.getErabTelefonoa());
			datuak.put("Izena", jabea.getErabIzena());
			datuak.put("Abizenak", jabea.getErabAbizen());
			datuak.put("ePosta", jabea.getErabEposta());
			datuak.put("Telf. Zenbakia", jabea.getErabTelefonoa());
			grid.setSource(source);
			panel.doLayout();
		}
		element.unmask();
	}

	public void datuakReseteatu() {
		NameValuePair[] source = new NameValuePair[4];
		source[0] = new NameValuePair("Izena", "Zure Izena");
		source[1] = new NameValuePair("Abizenak", "Zure Abizenak");
		source[2] = new NameValuePair("ePosta", "Zure ePosta");
		source[3] = new NameValuePair("Telf. Zenbakia",
				"Zure Telefono Zenbakia");
		datuak.put("Izena", "");
		datuak.put("Abizenak", "");
		datuak.put("ePosta", "");
		datuak.put("Telf. Zenbakia", "");
		grid.setSource(source);
		panel.doLayout();

	}

}
