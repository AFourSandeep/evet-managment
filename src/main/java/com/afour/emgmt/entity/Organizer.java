/**
 * 
 */
package com.afour.emgmt.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "organizer", schema = "event_management")
public class Organizer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="organizer_id")
	private Integer organizerId;
	
	@Column(name="user_name", length=100, nullable=false)
	private String userName;
	
	@Column(name="first_name", length=100, nullable=false)
	private String firstName;
	
	@Column(name="last_name", length=100)
	private String lastName;
	
	@Column(name="password", length=100, nullable=false)
	private String password;
	
	@Column(name="is_active", nullable=false)
	private boolean isActive;
	
	@OneToOne()
	@JoinColumn(name="role_id", unique=true, nullable=false)
	private Role role;

	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_at")
	private Timestamp createdAt;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="updated_at")
	private Timestamp updatedAt;
	

}
