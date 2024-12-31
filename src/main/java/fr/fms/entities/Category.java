package fr.fms.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "T_Categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class Category implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String description;

	@OneToMany(mappedBy = "category")
	private Collection<Article> articles;

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}
