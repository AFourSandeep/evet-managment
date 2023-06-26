/**
 * 
 */
package com.afour.emgmt.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "event", schema = "event_management")
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="event_id")
	private Integer eventId;
	
	@Column(name="event_name", length=100, nullable=false, unique=false)
	private String eventName;
	
	@Column(name="start_at")
	private LocalDateTime startAt;
	
	@Column(name="end_at")
	private LocalDateTime endAt;
	
	@OneToOne
	@JoinColumn(name="organizer_id", unique=true, nullable=false)
	private Organizer owner;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@OneToMany( mappedBy = "event")
	private List<Esession> sessions;
	
	@OneToMany( mappedBy = "event")
	private List<Visitor> visitors;

}
