package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ComponentListener;
import com.gwtext.client.widgets.event.ComponentListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;

public class Estatistikak extends BarnePanela {

	private String userNan;
	private Panel panel;
	private ExtElement element;

	public Estatistikak(Gunea owner) {
		super(owner);
		panel = this;
		panel.setTitle("Nire Ibilaldiak");
		panel.setLayout(new FitLayout());
		panel.setBorder(false);
		panel.setAutoScroll(true);
		panel.setCollapsible(false);

		addListener(new ComponentListenerAdapter(){
			public void onShow(Component component) {
				datuakEguneratu();
			}
		});
	}

	public void datuakEguneratu() {
		element = new ExtElement(getElement());
		element.mask("Itxaron mesedez", true);
		if (jabea.isErabIdentif()) {
			GuneaService.Util.getInstance().nireIbilbideakLortu(
					jabea.getErabNan(), new AsyncCallback<DatuEstatistiko>() {

						public void onFailure(Throwable caught) {
							caught.printStackTrace();

						}

						public void onSuccess(DatuEstatistiko result) {
							System.out.println("Alokairu Kopurua: "
									+ result.getAlokairuKop());
							System.out.println("Alokairu Luzeena: "
									+ result.getAlokairuLuzeenaEguna() + ": "
									+ result.getAlokairuLuzeenaDenbora());
							System.out.println("Egun aktiboena: "
									+ result.getEgunAktiboneaEguna() + ": "
									+ result.getEgunAktiboenaDenbora());
							System.out.println("Egindako Ibilaldiak: "
									+ result.getIbilaldiKop());
							System.out.println("Bidiaien portzentaiak");
							for (IbilaldienPortzentaiak row : result
									.getIbilaldienPortzentaiak()) {
								System.out.println(row.getHasIzena() + " - " + row.getHelIzena()
										+ ": " + ((Double) row.getTemp3() * 100) + "%");
							}
							element.unmask();
						}

					});
		}

	}

	public void datuakReseteatu() {
		// TODO Auto-generated method stub

	}

}
