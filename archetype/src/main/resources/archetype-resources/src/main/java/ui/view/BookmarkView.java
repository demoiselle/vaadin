package ${package}.ui.view;

import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.vaadin.data.collectioncontainer.CollectionContainer;

import br.gov.frameworkdemoiselle.vaadin.template.AbstractCrudView;
import br.gov.frameworkdemoiselle.vaadin.ui.CrudForm;
import ${package}.domain.Bookmark;
import ${package}.domain.Category;

import com.vaadin.ui.ComboBox;

public class BookmarkView extends AbstractCrudView<Bookmark> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ResourceBundle bundle;

	public BookmarkView() {
		super(new CrudForm<Bookmark>(Bookmark.class));
	}

	public void initializeComponents() {
		super.initializeComponents();
		setCaption("Bookmarks");

		addComponent(getCrudForm());
		addComponent(getListTable());

		getListTable().setCaption(bundle.getString("bookmark.table.title"));
	}

	public void setDeleteButtonEnabled(boolean enabled) {	
		getCrudForm().getButtonDelete().setEnabled(enabled);
	}

	public void setCategories(List<Category> list) {
		ComboBox categories = (ComboBox) getCrudForm().getField("category");
		categories.setContainerDataSource(CollectionContainer.fromBeans(list));
	}

}
