package com.MySql.Util;

import java.sql.*;

public class VeritabaniUtil {
	static Connection conn = null;

	public static Connection Baglan() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/pcsatis", "root", "");
			return conn;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage().toString());
			return null;
		}
	}
}
