package com.lucasmoyano.willywonka.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "job")
@ToString @EqualsAndHashCode
public class Job extends BaseEntity {

	@Column(name = "name")
	@Getter @Setter private String name;
}