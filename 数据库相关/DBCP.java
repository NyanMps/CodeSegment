package com.etoak.book.commons.factory;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by 冰封承諾Andy on 2018/5/19.
 */
public class CF {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql:///book?useSSL=true&characterEncoding=UTF-8");
        ds.setUsername("loli");
        ds.setPassword("12358");
    }

    public static DataSource getDataSource() {
        return ds;
    }

    public static Connection getConn() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
