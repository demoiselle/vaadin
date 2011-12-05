package ${package}.ui;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.vaadin.template.VaadinApplication;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import ${package}.ui.presenter.MainPresenter;

import com.vaadin.ui.Window;

@SessionScoped
public class BookmarkApplication extends VaadinApplication {

	private static final long serialVersionUID = 1L;

	@Inject
	private ResourceBundle bundle;

	@Inject
	private MainPresenter mainPresenter; 

	public void init() {
		setTheme("bookmark");
		final Window window = new Window(bundle.getString("app.title"));
		window.addComponent(mainPresenter.getView());
		setMainWindow(window);
	}

}
