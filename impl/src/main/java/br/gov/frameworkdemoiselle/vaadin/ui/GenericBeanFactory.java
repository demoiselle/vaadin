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

import java.lang.reflect.Field;

import org.vaadin.addons.lazyquerycontainer.Query;
import org.vaadin.addons.lazyquerycontainer.QueryDefinition;
import org.vaadin.addons.lazyquerycontainer.QueryFactory;

import br.gov.frameworkdemoiselle.util.Reflections;

/**
 * Factory of ${link Query} implementations. Used by LazyContainer.
 * 
 * @author CETEC
 */
public class GenericBeanFactory<E> implements QueryFactory {

	private QueryDefinition definition;

	private Class<E> beanClass;

	private ResultListHandler<E> handler;

	public GenericBeanFactory(Class<E> beanClass, ResultListHandler<E> handler) {
		this.beanClass = beanClass;
		this.handler = handler;
	}

	@Override
	public Query constructQuery(Object[] sortPropertyIds, boolean[] sortStates) {
		GenericBeanQuery<E> beanQuery = new GenericBeanQuery<E>(beanClass, handler);
		return beanQuery;
	}

	@Override
	public void setQueryDefinition(QueryDefinition queryDefinition) {
		this.definition = queryDefinition;
		if (queryDefinition.getPropertyIds().isEmpty()) {
			Field[] fields = Reflections.getNonStaticDeclaredFields(beanClass);
			for (Field field : fields) {
				this.definition.addProperty(field.getName().toLowerCase(), field.getType(), "", true, true);
			}
		}
	}

}
