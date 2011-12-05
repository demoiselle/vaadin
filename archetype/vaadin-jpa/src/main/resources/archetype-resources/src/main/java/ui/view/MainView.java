package ${package}.ui.view;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.util.ResourceBundle;
import br.gov.frameworkdemoiselle.vaadin.annotation.Navigable;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessMenuSelection;
import br.gov.frameworkdemoiselle.vaadin.ui.StructuredView;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class MainView extends StructuredView {

	private static final long serialVersionUID = 1L;

	@Inject
	private ResourceBundle bundle;
	
	@Navigable
	private TabSheet tabSheet = new TabSheet();

	@Inject
	@ProcessMenuSelection
	private javax.enterprise.event.Event<String> menuEvent;

	private Command menuCommand = new Command() {

		private static final long serialVersionUID = 1L;

		public void menuSelected(MenuItem selectedItem) {
			menuEvent.fire(selectedItem.getText());
		}

	};
	
	@Override
	public void initializeComponents() {
		final GridLayout grid = new GridLayout(3, 1);

		Embedded em = new Embedded("", new ThemeResource("images/bookmark-icon-logo.png"));
		grid.addComponent(em, 0, 0);
		grid.setComponentAlignment(em, Alignment.MIDDLE_CENTER);

		Label labelLogo = new Label("Bookmark Manager");
		labelLogo.setStyleName("logolabel");

		grid.addComponent(labelLogo, 1, 0);
		grid.setComponentAlignment(labelLogo, Alignment.MIDDLE_CENTER);

		getHeader().addComponent(grid);
		getFooter().addComponent(new Label("Bookmark Manager - Developed with Vaadin and Demoiselle Framework"));

		createMenu();
		
		getContent().addComponent(getTabSheet());
	}

	public TabSheet getTabSheet() {
		return tabSheet;
	}

	private void createMenu() {
		getMenuBar().setWidth("100%");
		final MenuBar.MenuItem menuNew = getMenuBar().addItem(bundle.getString("menu.new"), null);
		menuNew.setIcon(new ThemeResource("icons/16/document.png"));

		menuNew.setIcon(new ThemeResource("icons/16/document.png"));
		menuNew.addItem(bundle.getString("menu.new.bookmark"), menuCommand);
		menuNew.addItem(bundle.getString("menu.new.category"), menuCommand);

		final MenuBar.MenuItem menuHelp = getMenuBar().addItem(bundle.getString("menu.help"), null);
		menuHelp.addItem(bundle.getString("menu.help.about"), null);
		menuHelp.setIcon(new ThemeResource("icons/16/help.png"));
	}
}
