package com.lucasmoyano.willywonka.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "oompaloompa")
@ToString @EqualsAndHashCode
public class OompaLoompa extends BaseEntity {

	@Column(name = "name")
	@Getter @Setter private String name;
	
	@Column(name = "birthday")
	@Getter @Setter private Date birthday;

	@JsonIgnore
	@Column(name = "weight")
	@Getter @Setter private Double weight;

	@JsonIgnore
	@Column(name = "height")
	@Getter @Setter private Double height;

	@JsonIgnore
	@Column(name = "description")
	@Getter @Setter private String description;

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_job")
	@Getter @Setter private Job job;

}
