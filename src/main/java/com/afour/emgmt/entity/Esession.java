/**
 * 
 */
package com.afour.emgmt.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "esession", schema = "event_management")
public class Esession {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="esession_id")
	private Integer esessionId;
	
	@Column(name="esession_title", length=100, nullable=false)
	private String esessionTitle;
	
	@Column(name="start_at")
	private LocalDateTime startAt;
	
	@Column(name="end_at")
	private LocalDateTime endAt;
	
	@ManyToOne
	@JoinColumn(name="event_id")
	private Event event;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;

}
