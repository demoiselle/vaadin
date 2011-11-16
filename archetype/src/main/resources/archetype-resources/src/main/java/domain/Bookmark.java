package ${package}.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.gov.frameworkdemoiselle.vaadin.annotation.CheckBox;
import br.gov.frameworkdemoiselle.vaadin.annotation.ComboBox;
import br.gov.frameworkdemoiselle.vaadin.annotation.Field;
import br.gov.frameworkdemoiselle.vaadin.annotation.TextField;

@Entity
public class Bookmark implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column
	@TextField
	@Field(prompt = "{bookmark.prompt.description}", label = "{bookmark.label.description}")
	private String description;

	@Column
	@NotNull(message="{message.error.link.notnull}")
	@Field(prompt = "{bookmark.prompt.link}", label = "{bookmark.label.link}")
	private String link;

	@ManyToOne
	@ComboBox(fieldLabel = "description")
	@Field(prompt = "{bookmark.prompt.category}", label = "{bookmark.label.category}")
	private Category category;

	@Column
	@CheckBox
	@Field(prompt = "{bookmark.prompt.visited}", label = "{bookmark.label.visited}")
	private boolean visited;

	public Bookmark() {
		super();
	}

	public Bookmark(String description, String link) {
		this.description = description;
		this.link = link;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public boolean isVisited() {
		return visited;
	}

}
