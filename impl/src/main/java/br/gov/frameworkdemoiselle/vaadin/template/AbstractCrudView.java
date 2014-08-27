/*
 * Demoiselle Framework
 * Copyright (C) 2010 SERPRO
 * ----------------------------------------------------------------------------
 * This file is part of Demoiselle Framework.
 * 
 * Demoiselle Framework is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3
 * along with this program; if not,  see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 * ----------------------------------------------------------------------------
 * Este arquivo é parte do Framework Demoiselle.
 * 
 * O Framework Demoiselle é um software livre; você pode redistribuí-lo e/ou
 * modificá-lo dentro dos termos da GNU LGPL versão 3 como publicada pela Fundação
 * do Software Livre (FSF).
 * 
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA
 * GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou
 * APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU/LGPL em português
 * para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da GNU LGPL versão 3, sob o título
 * "LICENCA.txt", junto com esse programa. Se não, acesse <http://www.gnu.org/licenses/>
 * ou escreva para a Fundação do Software Livre (FSF) Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */
package br.gov.frameworkdemoiselle.vaadin.template;

import java.util.List;

import org.vaadin.data.collectioncontainer.CollectionContainer;

import br.gov.frameworkdemoiselle.util.Reflections;
import br.gov.frameworkdemoiselle.vaadin.ui.AutoTable;
import br.gov.frameworkdemoiselle.vaadin.ui.CrudForm;
import br.gov.frameworkdemoiselle.vaadin.ui.PagedContainer;
import br.gov.frameworkdemoiselle.vaadin.ui.ResultListHandler;

/**
 * View implementation that defines a default behavior for CRUD operations.
 * 
 * @author Marlon
 * @param <E>
 *            Domain class that will receive the CRUD operations.
 */
public abstract class AbstractCrudView<E> extends BaseVaadinView implements CrudView<E> {

	private static final long serialVersionUID = 1L;

	private Class<E> beanClass;

	private CrudForm<E> crudForm;

	private AutoTable listTable;

	public AbstractCrudView(CrudForm<E> crudForm) {
		beanClass = Reflections.getGenericTypeArgument(getClass(), 0);
		this.crudForm = crudForm;
		listTable = new AutoTable(beanClass);
	}

	public void setDeleteButtonEnabled(boolean enabled) {
		getCrudForm().getButtonDelete().setEnabled(enabled);
	}

	public void setSaveButtonEnabled(boolean enabled) {
		getCrudForm().getButtonSave().setEnabled(enabled);
	}

	public void setClearButtonEnabled(boolean enabled) {
		getCrudForm().getButtonClear().setEnabled(enabled);
	}

	private E instantiate() {
		try {
			return beanClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initializeComponents() {
		setBean(instantiate());
		listTable.setWidth("100%");
	}

	@Override
	public E getBean() {
		return getCrudForm().getBean();
	}

	@Override
	public void setBean(E bean) {
		getCrudForm().setBean(bean);
	}

	@Override
	public void clear() {
		getCrudForm().setBean(instantiate());
	}

	public void setCrudForm(CrudForm<E> crudForm) {
		this.crudForm = crudForm;
	}

	public CrudForm<E> getCrudForm() {
		return crudForm;
	}

	public void setListTable(AutoTable listTable) {
		this.listTable = listTable;
	}

	public void setList(ResultListHandler<E> resultListHandler) {
		getListTable().setContainerDataSource(PagedContainer.create(beanClass, resultListHandler));
	}

	@Override
	public void setList(List<E> list) {
		getListTable().setContainerDataSource(CollectionContainer.fromBeans(list));
	}

	public AutoTable getListTable() {
		return listTable;
	}

}
