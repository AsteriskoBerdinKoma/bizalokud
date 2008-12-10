package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.util.JavaScriptObjectHelper;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Container;
import com.gwtext.client.widgets.HTMLPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.WindowMgr;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.event.TabPanelListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.CardLayout;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;
import com.sgta07.bizalokud.login.client.Login;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gunea implements EntryPoint, HistoryListener {

	private GuneInfo gunea;
	private Login login;

	private Mapa mapa;
	
	private static PopupPanel mezuPanela;
    private TabPanel erdikoPanela;
    private PantailaKudeatzailea pantailaKud;
    private Menu menua;
	
	

	public void onModuleLoad() {
		login = new Login();
		login.erakutsi();
		
		mezuPanela = new PopupPanel(true);
		mapa = new Mapa();
		mapa.setSize(500, 400);

		GuneaService.Util.getInstance().getMyInfo(
				new AsyncCallback<GuneInfo>() {

					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						setGunea(null);
					}

					public void onSuccess(GuneInfo result) {
						try {
							setGunea(result);
						} catch (Exception e) {
							e.printStackTrace();
							setGunea(null);
						}

					}
				});
		

		Panel panelNagusia = new Panel();
		panelNagusia.setBorder(false);
		panelNagusia.setPaddings(0);
		panelNagusia.setId("panel-nagusia");
		panelNagusia.setLayout(new BorderLayout());

		//Iparraldeko layout propietateak konfiguratu
		BorderLayoutData iparLayoutData = new BorderLayoutData(RegionPosition.NORTH);
        iparLayoutData.setSplit(false);

        //Erdialdeko layout propietateak konfiguratu
        BorderLayoutData erdiLayoutData = new BorderLayoutData(RegionPosition.CENTER);
        erdiLayoutData.setMargins(new Margins(5, 0, 5, 5));
        
        //Mendebaldeko layout propietateak konfiguratu
        BorderLayoutData mendebaldeLayoutData = new BorderLayoutData(RegionPosition.WEST);
        mendebaldeLayoutData.setMargins(new Margins(5, 5, 0, 5));
        mendebaldeLayoutData.setCMargins(new Margins(5, 5, 5, 5));
        mendebaldeLayoutData.setMinSize(155);
        mendebaldeLayoutData.setMaxSize(350);
        mendebaldeLayoutData.setSplit(true);
		
        Panel erdikoPanelWrappper = new Panel();
        erdikoPanelWrappper.setLayout(new FitLayout());
        erdikoPanelWrappper.setBorder(false);
        erdikoPanelWrappper.setBodyBorder(false);
        
        erdikoPanela = new TabPanel();
        erdikoPanela.setBodyBorder(false);
        erdikoPanela.setEnableTabScroll(true);
        erdikoPanela.setAutoScroll(true);
        erdikoPanela.setAutoDestroy(false);
        erdikoPanela.setActiveTab(0);
        
//        erdikoPanela.addListener(new TabPanelListenerAdapter() {
//            public boolean doBeforeTabChange(TabPanel source, Panel newPanel, Panel oldPanel) {
//                WindowMgr.hideAll();
//                return true;
//            }
//
//            public void onRemove(Container self, Component component) {
//                component.hide();
//            }
//
//            public void onContextMenu(TabPanel source, Panel tab, EventObject e) {
//                erakutsiMenua(tab, e);
//            }
//        });

        erdikoPanela.setLayoutOnTabChange(true);
        erdikoPanela.setTitle("Ekintzak");
        
        pantailaKud = new PantailaKudeatzailea(erdikoPanela);

        

        //Mendebaldeko panela sortu eta panel nagusira gehitu mendebaldeko layout propietateak aplikatuz
        Panel mendebaldekoPanela = sortuMendebaldekoPanela();
        panelNagusia.add(mendebaldekoPanela, mendebaldeLayoutData);
        
        //Iparraldeko panela sortu eta panel nagusira gehitu iparraldeko layout propietateak aplikatuz
        Panel iparraldekoPanela = new Panel();
        Image banner = new Image("images/banner.png");
        iparraldekoPanela.setBorder(false);
        iparraldekoPanela.setCollapsible(false);
        iparraldekoPanela.add(banner);
        iparraldekoPanela.setAutoHeight(true);
        iparraldekoPanela.setFrame(false);
        iparraldekoPanela.setStyle("backgroundColor: #dfe8f6;");
        iparraldekoPanela.setBodyStyle("backgroundColor: #dfe8f6;");
        
        panelNagusia.add(iparraldekoPanela, iparLayoutData);
        
        //Hasierako panela erdiko panelera gehitu
        final Panel sarreraPanela = new Panel();
		sarreraPanela.setLayout(new CardLayout());


		Panel centerPanelTwo = new HTMLPanel();
		centerPanelTwo
				.setHtml("<br><br><h1>Ongietorria Bizalokud sistemara!</h1>\n<br><p>Aukera ezazu menuko ekintza bat aplikazioa erabiltzen hasteko.");
		centerPanelTwo.setTitle("Ekintzak");
		centerPanelTwo.setAutoScroll(true);
		centerPanelTwo.add(mapa.getMapPanel());
		
		sarreraPanela.add(centerPanelTwo);

        
        erdikoPanela.add(sarreraPanela, erdiLayoutData);
        erdikoPanelWrappper.add(erdikoPanela);
        panelNagusia.add(erdikoPanelWrappper, erdiLayoutData);

        final String initToken = History.getToken();
        if (initToken.length() != 0) {
            panelNagusia.addListener(new PanelListenerAdapter() {
                public void onRender(Component component) {
                    onHistoryChanged(initToken);
                }
            });
        }

        Viewport viewport = new Viewport(panelNagusia);

        // Add history listener
        History.addHistoryListener(this);
        
        
        
        
        

//		 //Egoaldeko panela: Azken minutuko informazioak jasotzeko
//		 Panel southPanel = new HTMLPanel(
//		 "<br>"
//		 +
//		 "<li>Amarako gunea bihartik aurrera itxita egongo da, konponketak egiteko!</li> "
//		 +
//		 "<li>Groseko obrak direla eta, Sagues-Bulevard ibilbidea ez hartzea gomendatzen da</li>");
//		 southPanel.setHeight(100);
//		 southPanel.setCollapsible(false);
//		 southPanel.setTitle("Azken orduko informazioa");
//		
//		 BorderLayoutData southData = new BorderLayoutData(RegionPosition.SOUTH);
//		 southData.setMinSize(100);
//		 southData.setMaxSize(200);
//		 southData.setMargins(new Margins(0, 0, 0, 0));
//		 southData.setSplit(true);
//		 borderPanel.add(southPanel, southData);

		
	}

	protected void setGunea(GuneInfo gunea) {
		this.gunea = gunea;
		if (gunea != null) {
			if (!gunea.hasLatLon())
				mapa.updateMap(gunea.getIzena(), gunea.getHelbidea(),
						JavaScriptObjectHelper.createObject(), mapa);
			else {
				mapa.markaGehitu(gunea);
				mapa.finkatu(gunea);
			}
		}
	}

	public String getHelbidea() {
		return this.gunea.getHelbidea();
	}

	private Panel sortuMendebaldekoPanela() {
        Panel mendebaldekoPanela = new Panel();
        mendebaldekoPanela.setId("nabigazio-menua");
        mendebaldekoPanela.setTitle("Menu Nagusia");
        mendebaldekoPanela.setLayout(new FitLayout());
        mendebaldekoPanela.setWidth(210);
        //mendebaldekoPanela.setCollapsible(true);

        mendebaldekoPanela.add(pantailaKud.getNabigazioMenua());

        return mendebaldekoPanela;
    }
	
	public void onHistoryChanged(String historyToken) {
		pantailaKud.showScreen(historyToken);
		
	}
	
	private void erakutsiMenua(final Panel tab, EventObject e) {
        if (menua == null) {
            menua = new Menu();
            Item close = new Item("Itxi Fitxa");
            close.setId("itxi-fitxa-item");
            close.addListener(new BaseItemListenerAdapter() {
                public void onClick(BaseItem item, EventObject e) {
                    erdikoPanela.remove(erdikoPanela.getActiveTab());
                }
            });
            menua.addItem(close);

            Item closeOthers = new Item("Itxi Beste Fitxak");
            closeOthers.setId("itxi-besteak-item");
            closeOthers.addListener(new BaseItemListenerAdapter() {
                public void onClick(BaseItem item, EventObject e) {
                    Component[] items = erdikoPanela.getItems();
                    for (int i = 0; i < items.length; i++) {
                        Panel panel = (Panel) items[i];
                        if (panel.isClosable() && !panel.getId().equals(erdikoPanela.getActiveTab().getId())) {
                            erdikoPanela.remove(panel);
                        }
                    }
                }
            });
            menua.addItem(closeOthers);
        }

        BaseItem close = menua.getItem("itxi-fitxa-item");
        if (!erdikoPanela.getActiveTab().isClosable()) {
            close.disable();
        } else {
            close.enable();
        }

        BaseItem closeOthers = menua.getItem("itxi-besteak-item");
        if (erdikoPanela.getItems().length == 1) {
            closeOthers.disable();
        } else {
            closeOthers.enable();
        }
        menua.showAt(e.getXY());
    }

    public static void showMessage(String title, String message) {
        mezuPanela.setPopupPosition(500, 300);
        mezuPanela.setWidget(new HTML(getMessageHtml(title, message)));
        mezuPanela.setWidth("300px");
        mezuPanela.show();
    }

    private static native String getMessageHtml(String title, String message) /*-{
                                                          return ['<div class="msg">',
                                                                  '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
                                                                  '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', title, '</h3>', message, '</div></div></div>',
                                                                  '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
                                                                  '</div>'].join('');
                                                      }-*/;
}
