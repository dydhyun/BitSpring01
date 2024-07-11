package com.bit.springboard.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCUtil {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/studydb?serverTimezone=UTC",
                    "study",
                    "!dkdlxl1234"
            );
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void close(Connection conn, Statement stmt) {
        if(stmt != null) {
            try {
                if(!stmt.isClosed()) {
                    stmt.close();
                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
            } finally {
                stmt = null;
            }
        }

        if(conn != null) {
            try {
                if(!conn.isClosed()) {
                    conn.close();
                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
            } finally {
                conn = null;
            }
        }
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        if(stmt != null) {
            try {
                if(!stmt.isClosed()) {
                    stmt.close();
                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
            } finally {
                stmt = null;
            }
        }

        if(conn != null) {
            try {
                if(!conn.isClosed()) {
                    conn.close();
                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
            } finally {
                conn = null;
            }
        }

        if(rs != null) {
            try {
                if(!rs.isClosed()) {
                    rs.close();
                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
            } finally {
                rs = null;
            }
        }
    }
}
