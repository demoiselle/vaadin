package ${package}.ui.view;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class AboutWindow extends Window {

	private static final long serialVersionUID = 1L;

	public AboutWindow() {
		setWidth("400px");
		setHeight("170px");
		setModal(true);

		setCaption("About");

		GridLayout gridLayout = new GridLayout(1, 2);
		gridLayout.setWidth("100%");
		gridLayout.setHeight("100%");

		Embedded em = new Embedded("", new ThemeResource("images/bookmark-icon-logo.png"));
		Label label = new Label("Bookmark Manager - Version 1.0");
		label.setSizeFull();

		gridLayout.addComponent(em, 0, 0);
		gridLayout.setComponentAlignment(em, Alignment.MIDDLE_CENTER);

		gridLayout.addComponent(label, 0, 1);
		gridLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);

		addComponent(gridLayout);
	}

}
