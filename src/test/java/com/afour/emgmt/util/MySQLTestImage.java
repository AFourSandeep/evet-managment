/**
 * 
 */
package com.afour.emgmt.util;

import org.testcontainers.utility.DockerImageName;

/**
 * 
 */
public class MySQLTestImage {
	
	public static final DockerImageName MYSQL_56_IMAGE = DockerImageName.parse("mysql:5.6.51");

    public static final DockerImageName MYSQL_57_IMAGE = DockerImageName.parse("mysql:5.7.34");

    public static final DockerImageName MYSQL_80_IMAGE = DockerImageName.parse("mysql:8.0.24");
    
    public static final DockerImageName MYSQL_LATEST = DockerImageName.parse("mysql:latest");

}
