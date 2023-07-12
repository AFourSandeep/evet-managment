/**
 * 
 */
package com.afour.emgmt.model;

import com.afour.emgmt.common.RoleEnum;
import lombok.AllArgsConstructor;
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
public class RoleDTO {

	private RoleEnum roleId;

	private String roleName;

}
