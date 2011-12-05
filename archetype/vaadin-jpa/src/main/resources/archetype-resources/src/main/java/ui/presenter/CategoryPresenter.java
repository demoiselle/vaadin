package ${package}.ui.presenter;

import javax.enterprise.context.SessionScoped; 
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.vaadin.event.BeforeNavigateToView;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessClear;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessDelete;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessItemSelection;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessSave;
import br.gov.frameworkdemoiselle.vaadin.template.AbstractPresenter;
import ${package}.business.CategoryBC;
import ${package}.domain.Category;
import ${package}.ui.view.CategoryView;

@ViewController
@SessionScoped
public class CategoryPresenter extends AbstractPresenter<CategoryView> {

	private static final long serialVersionUID = 1L;

	@Inject
	private CategoryBC categoryBC;

	public void processSave(@Observes @ProcessSave Category category) {
		if (category.getId() != null) {
			categoryBC.update(category);
		} else {
			categoryBC.insert(category);
		}
		getView().clear();
		getView().setDeleteButtonEnabled(false);
		getView().setList(categoryBC.findAll());
	}

	public void processItemSelection(@Observes @ProcessItemSelection Category category) {
		getView().setBean(category);
		getView().setDeleteButtonEnabled(true);
	}

	public void processDelete(@Observes @ProcessDelete Category category) {
		categoryBC.delete(category.getId());
		getView().setDeleteButtonEnabled(false);
		getView().setList(categoryBC.findAll());
	}

	public void beforeNavigateToView(@Observes @BeforeNavigateToView CategoryView view) {
		view.setList(categoryBC.findAll());
	}

	public void processFormClear(@Observes @ProcessClear Category category) {
		getView().clear();
		getView().setDeleteButtonEnabled(false);
	}

}
