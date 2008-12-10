package com.sgta07.bizalokud.gunea.client;

import com.google.gwt.user.client.History;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.Node;
import com.gwtext.client.data.ObjectFieldDef;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.util.DelayedTask;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.layout.AccordionLayout;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreePanel;
import com.gwtext.client.widgets.tree.event.TreeNodeListenerAdapter;

public class PantailaKudeatzailea {

	private static Store store;
	private TabPanel appTabPanel;
	private DelayedTask delayedTask = new DelayedTask();
	private static ArrayReader reader;
	private static MemoryProxy proxy;

	public PantailaKudeatzailea(TabPanel tabPanel) {
		this.appTabPanel = tabPanel;
	}

	public Panel getNabigazioMenua() {
		Panel accordion = new Panel();
		//accordion.setTitle("Menu Nagusia");
		accordion.setLayout(new AccordionLayout(true));

		Store store = getStore();

		Record[] records = store.getRecords();
		for (int i = 0; i < records.length; i++) {
			Record record = records[i];

			String id = record.getAsString("id");
			final String category = record.getAsString("kategoria");
			String title = record.getAsString("izenburua");
			final String iconCls = record.getAsString("iconCls");
			String thumbnail = record.getAsString("irudia");

			final EdukienPanela panel = (EdukienPanela) record.getAsObject("pantaila");

			record.set("pantaila", panel);

			if (category == null) {
				Panel categoryPanel = new Panel();
				categoryPanel.setAutoScroll(true);
				categoryPanel.setLayout(new FitLayout());
				categoryPanel.setId(id + "-acc");
				categoryPanel.setTitle(title);
				categoryPanel.setIconCls(iconCls);
				accordion.add(categoryPanel);
			} else {
				Panel categoryPanel = (Panel) accordion.findByID(category+ "-acc");
				TreePanel treePanel = (TreePanel) categoryPanel.findByID(category + "-acc-tree");
				TreeNode root = null;
				if (treePanel == null) {
					treePanel = new TreePanel();
					treePanel.setAutoScroll(true);
					treePanel.setId(category + "-acc-tree");
					treePanel.setRootVisible(false);
					root = new TreeNode();
					treePanel.setRootNode(root);
					categoryPanel.add(treePanel);
				} else {
					root = treePanel.getRootNode();
				}

				TreeNode node = new TreeNode();
				node.setText(title);
				node.setId(id);
				if (iconCls != null)
					node.setIconCls(iconCls);
				root.appendChild(node);

				addNodeClickListener(node, panel, iconCls);
			}
		}

		return accordion;
	}

	private void addNodeClickListener(TreeNode node, final Panel panel,
			final String iconCls) {
		if (panel != null) {
			node.addListener(new TreeNodeListenerAdapter() {
				public void onClick(Node node, EventObject e) {
					String panelID = panel.getId();
					if (appTabPanel.hasItem(panelID)) {
						showScreen(panel, null, null, node.getId());
					} else {
						TreeNode treeNode = (TreeNode) node;
						panel.setTitle(treeNode.getText());
						String nodeIconCls = iconCls;
						if (iconCls == null) {
							nodeIconCls = ((TreeNode) treeNode.getParentNode())
									.getIconCls();
						}
						showScreen(panel, treeNode.getText(), nodeIconCls, node
								.getId());
					}
				}
			});
		}
	}

	public void showScreen(String historyToken) {
		if (historyToken == null || historyToken.equals("")) {
			appTabPanel.activate(0);
		} else {
			Record record = store.getById(historyToken);
			if (record != null) {
				EdukienPanela panel = (EdukienPanela) record
						.getAsObject("screen");
				String title = record.getAsString("title");
				String iconCls = record.getAsString("iconCls");
				showScreen(panel, title, iconCls, historyToken);
			}
		}
	}

	public void showScreen(Panel panel, String title, String iconCls,
			String screenName) {
		String panelID = panel.getId();
		if (appTabPanel.hasItem(panelID)) {
			appTabPanel.scrollToTab(panel, true);
			appTabPanel.activate(panelID);
		} else {
			if (!panel.isRendered()) {
				panel.setTitle(title);
				if (iconCls == null) {
					iconCls = "plugins-nav-icon";
				}
				panel.setIconCls(iconCls);
			}
			appTabPanel.add(panel);
			appTabPanel.activate(panel.getId());
		}
		History.newItem(screenName);
	}

	public static Store getStore() {
		if (store == null) {
			proxy = new MemoryProxy(getData());

			RecordDef recordDef = new RecordDef(new FieldDef[] {
					new StringFieldDef("id"), new StringFieldDef("kategoria"),
					new StringFieldDef("izenburua"),
					new StringFieldDef("iconCls"),
					new StringFieldDef("irudia"),
					new ObjectFieldDef("pantaila"), });

			reader = new ArrayReader(0, recordDef);
			store = new Store(proxy, reader);
			store.load();
		}
		return store;
	}

	private static Object[][] getData() {
		return new Object[][] {

				new Object[] { "ekintzak-kategoria", null, "Ekintzak","ekintza-kategoria-icon", null, null },
				new Object[] { "alokatu", "ekintzak-kategoria","Bizikleta Alokatu", null,"images/thumbnails/tree/editable.gif", new Alokatu() },
				new Object[] { "entregatu", "ekintzak-kategoria","Bizikleta Entregatu", null, null, new Entregatu() },

				new Object[] { "kontua-kategoria", null, "Nire Kontua","kontua-kategoria-icon", null, null },
				new Object[] { "estatistikak", "kontua-kategoria","Nire Ibilbideak", null, null, new Estatistikak() },
				new Object[] { "pasahitza", "kontua-kategoria","Pasahitza Aldatu", null, null, new Pasahitza() },
				new Object[] { "abisuak", "kontua-kategoria","Nire Abisuak",null,null,new Abisuak() },
				new Object[] { "datuak", "kontua-kategoria", "Nire Datuak",null, null, new NireDatuak() } };
	}

	public static ArrayReader getReader() {
		getStore();
		return reader;
	}

	public static MemoryProxy getProxy() {
		getStore();
		return proxy;
	}

}
