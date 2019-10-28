package com.lucasmoyano.willywonka.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@ToString @EqualsAndHashCode
public abstract class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Access(AccessType.PROPERTY)
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	@Getter @Setter private long id;

	@JsonIgnore
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	@Getter @Setter private Date createdDate;

	@JsonIgnore
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	@Getter @Setter private Date updatedDate;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_date")
	@Getter @Setter private Date deletedDate;
	
	public boolean isActive() {
		return deletedDate == null;
	}
}
