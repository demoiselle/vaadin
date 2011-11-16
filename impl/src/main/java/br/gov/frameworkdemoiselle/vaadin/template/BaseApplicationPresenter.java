package br.gov.frameworkdemoiselle.vaadin.template;

public abstract class BaseApplicationPresenter<V extends BaseVaadinView> extends AbstractPresenter<V> {

	private static final long serialVersionUID = 1L;

	public abstract void navigate(View view);

}
