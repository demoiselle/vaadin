package ${package}.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column
	@NotNull(message="{message.error.category.description.notnull}")
	private String description;

	public Category() {
	}
	
	public Category(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		return description;
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) 
			return false;
		if ( id == null ) 
			return false;
		Category c = (Category) obj;
		return id.equals(c.getId());
	}
	
	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : super.hashCode();
	}

}
