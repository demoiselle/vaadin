package br.gov.frameworkdemoiselle.vaadin.template;

import java.lang.reflect.Field;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Id;

import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.template.Crud;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.Reflections;

public abstract class AbstractCrudPresenter<V extends AbstractCrudView<E>, E, I, B extends Crud<E, I>> extends
		AbstractPresenter<V> {

	@Inject
	private MessageContext messageContext;

	private B delegate;

	private Class<E> entityClass;

	private static final long serialVersionUID = 1L;

	public AbstractCrudPresenter() {
		entityClass = Reflections.getGenericTypeArgument(getClass(), 1);
		Class<B> bcClass = Reflections.getGenericTypeArgument(getClass(), 3);
		delegate = Beans.getReference(bcClass);
	}

	@SuppressWarnings("unchecked")
	private I getIdFieldValue(E object) {
		I result = null;
		Field[] fields = Reflections.getNonStaticDeclaredFields(object.getClass());
		for (Field field : fields) {
			if (field.isAnnotationPresent(Id.class)) {
				result = (I) Reflections.getFieldValue(field, object);
				break;
			}
		}
		return result;
	}

	public void doSave(E entity) {
		if (getIdFieldValue(entity) != null) {
			getDelegate().update(entity);
		} else {
			getDelegate().insert(entity);
		}
		getView().clear();
		getView().setDeleteButtonEnabled(false);
		getView().setClearButtonEnabled(false);
		getView().setList(getResultList());
	}

	protected List<E> getResultList() {
		return getDelegate().findAll();
	}

	public void doItemSelection(E entity) {
		getView().setBean(entity);
		getView().setDeleteButtonEnabled(true);
		getView().setClearButtonEnabled(true);
	}

	public void doDelete(E entity) {
		getDelegate().delete(getIdFieldValue(entity));
		getView().setDeleteButtonEnabled(false);
		getView().setClearButtonEnabled(false);
		getView().setList(getResultList());
		getView().setBean(Reflections.instantiate(entityClass));
	}

	public void doNavigation(V view) {
		view.setBean(Reflections.instantiate(entityClass));
		view.setList(getResultList());
	}

	public void doFormClear(E entity) {
		getView().clear();
		getView().setDeleteButtonEnabled(false);
		getView().setClearButtonEnabled(false);
	}

	@ExceptionHandler
	public void testError(RuntimeException exception) {
		messageContext.add(exception.getMessage(), SeverityType.ERROR);
	}

	public B getDelegate() {
		return delegate;
	}

}
