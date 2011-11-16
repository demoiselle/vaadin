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
package br.gov.frameworkdemoiselle.vaadin.ui;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.addons.lazyquerycontainer.Query;

import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.pagination.PaginationContext;
import br.gov.frameworkdemoiselle.util.Beans;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;

/**
 * 
 * 
 * @author CETEC
 *
 * @param <E>
 */
public class GenericBeanQuery<E> implements Query {

	private Class<E> beanClass;

	private int size;

	private ResultListHandler<E> handler;

	private List<E> list;

	public GenericBeanQuery(final Class<E> beanClass, final ResultListHandler<E> handler) {
		this.beanClass = beanClass;
		this.handler = handler;
	}

	@Override
	public Item constructItem() {
		E object;
		try {
			object = beanClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		BeanItem<E> beanItem = new BeanItem<E>(object);
		return beanItem;
	}

	@Override
	public boolean deleteAllItems() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Item> loadItems(final int startIndex, final int count) {
		PaginationContext paginationContext = Beans.getReference(PaginationContext.class);
		Pagination pagination = paginationContext.getPagination(beanClass);

		pagination.setFirstResult(startIndex);

		List<E> resultList;
		if (list != null && !list.isEmpty()) {
			resultList = list;
			list = null;
		} else {
			resultList = handler.handleResultList();
		}

		size = pagination.getTotalResults();

		List<Item> items = new ArrayList<Item>();
		for (E entity : resultList) {
			items.add(new BeanItem<E>(entity));
		}

		return items;
	}

	@Override
	public void saveItems(final List<Item> addedItems, final List<Item> modifiedItems, final List<Item> removedItems) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Vaadin's ${link Table} will call this method first.
	 *  
	 * Demoiselle's pagination mechanism needs a first call to "findAll" method to know how much records exists in the underlying persistence storage. 
	 * Because that, we first check the size, asking only for one record (0 to 1). After that the "PageSize" attribute will be set.
	 */
	@Override
	public int size() {
		PaginationContext paginationContext = Beans.getReference(PaginationContext.class);
		Pagination pagination = paginationContext.getPagination(beanClass, true);

		// If size equals 0 then is the first call to container or the table has 0 record.
		// Lets minimize the overhead asking only for 1 record.
		if (size == 0) {
			// Store this list for further use. Lets minimize even more the overhead.
			list = handler.handleResultList();
			size = pagination.getTotalResults(); // Ok, now we have the real totalResults.
		}

		return size;
	}

}