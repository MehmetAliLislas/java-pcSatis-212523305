package com.IsteMySQL.Util;

import java.sql.*;

public class VeritabaniUtil {
	static Connection conn = null;

	public static Connection Baglan() {
		try {
			// jdbc:mysql://serverIPadresi/db_ismi, "username", "pass"
			conn = DriverManager.getConnection("jdbc:mysql://localhost/projemdb", "root", "");
			return conn;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage().toString());
			return null;
		}
	}
}
