package ${package}.ui.presenter;

import javax.enterprise.context.SessionScoped; 
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.vaadin.event.ProcessMenuSelection;
import br.gov.frameworkdemoiselle.vaadin.template.AbstractPresenter;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.vaadin.util.ViewNavigator;
import ${package}.ui.view.AboutWindow;
import ${package}.ui.view.MainView;

import com.vaadin.ui.Window;

@ViewController
@SessionScoped
public class MainPresenter extends AbstractPresenter<MainView> {

	public static final String MENU_BOOKMARK = "Bookmark";

	public static final String MENU_CATEGORY = "Category";
	
	public static final String MENU_ABOUT = "About";

	private static final long serialVersionUID = 1L;

	@Inject
	private ViewNavigator navigator;

	@Inject
	private BookmarkPresenter bookmarkPresenter;

	@Inject
	private CategoryPresenter categoryPresenter;

	public void processMenuSelected(@Observes @ProcessMenuSelection String selection) {
		if (MENU_BOOKMARK.equals(selection)) {
			navigator.navigate(bookmarkPresenter.getView());
		}
		if (MENU_CATEGORY.equals(selection)) {
			navigator.navigate(categoryPresenter.getView());
		}
		if (MENU_ABOUT.equals(selection)) {
			Window about = new AboutWindow();
			getView().getWindow().addWindow(about);
		}
	}

}
