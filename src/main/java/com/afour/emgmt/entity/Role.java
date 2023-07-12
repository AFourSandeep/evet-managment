/**
 * 
 */
package com.afour.emgmt.entity;

import javax.persistence.*;

import com.afour.emgmt.common.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role", schema = "event_mgmt")
public class Role {

	@Id
	@Column(name="role_id", length = 64)
	@Enumerated(EnumType.STRING)
	private RoleEnum roleId;
	
	@Column(name="role_name")
	private String roleName;

}
