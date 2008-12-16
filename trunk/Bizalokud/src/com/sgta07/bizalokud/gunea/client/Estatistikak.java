package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ComponentListenerAdapter;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.layout.RowLayoutData;

public class Estatistikak extends BarnePanela {

	private Panel panel;
	private ExtElement element;
	private Label infoLabel;

	public Estatistikak(Gunea owner) {
		super(owner);
		panel = this;
		panel.setTitle("Nire Ibilaldiak");
		panel.setLayout(new FitLayout());
		panel.setBorder(false);
		panel.setAutoScroll(true);
		panel.setCollapsible(false);
		panel.setPaddings(15);
		infoLabel = new Label();
		infoLabel.setStyle("background: white;");
		panel.add(infoLabel, new RowLayoutData());

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
							element.unmask();
							String html = "<p style=\"font-size:medium;\" align=\"center\"><b>Momentu honetan ezin dira datuak eskuratu.<br> Mesedez saiatu berriz beranduago.</b></p>";
							infoLabel.setHtml(html);
						}

						public void onSuccess(DatuEstatistiko result) {
							
							String html = "<p><span class=\"style1\">Alokairu Kopurua: </span>" +
										  result.getAlokairuKop()+"</p><br>"+
										  "<p><span class=\"style1\">Alokairu Luzeena: </span>"+
										  result.getAlokairuLuzeenaEguna()+" | "+result.getAlokairuLuzeenaDenbora()+"</p><br>"+
										  "<p><span class=\"style1\">Egun Aktiboena: </span>"+
										  result.getEgunAktiboneaEguna()+" | "+result.getEgunAktiboenaDenbora()+"</p><br>"+
										  "<p><span class=\"style1\">Egindako Ibilaldi Kopurua: </span>"+
										  result.getIbilaldiKop()+"</p><br>"+
										  "<table width=\"600\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" class=\"taula\">"+
										  "<tr><th colspan=\"2\" bgcolor=\"#CEEFFF\" class=\"style1\" scope=\"col\"><div align=\"center\">Ibilaldien Portzentaiak</div></th></tr>";
							for (IbilaldienPortzentaiak row : result.getIbilaldienPortzentaiak()) {
								html+="<tr><td><div align=\"center\">"+row.getHasierakoGuneIzena() + " - " + row.getHelburuGuneIzena()+"</div></td>"+
							    "<td><div align=\"center\">"+(row.getPortzentaia() * 100) + "%"+"</div></td></tr>";
							}
							html+="</table>";
							infoLabel.setHtml(html);
							element.unmask();
							panel.doLayout();
						}

					});
		} else {
			element.unmask();
			String html = "<p style=\"font-size:medium;\" align=\"center\"><b>Ez dago daturik erakusteko.</b></p>";
			infoLabel.setHtml(html);
		}

	}

	public void datuakReseteatu() {
		String html = "<p style=\"font-size:medium;\" align=\"center\"><b>Ez dago daturik erakusteko.</b></p>";
		infoLabel.setHtml(html);

	}

}
