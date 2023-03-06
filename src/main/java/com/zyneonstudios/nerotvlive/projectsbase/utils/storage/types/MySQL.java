package com.zyneonstudios.nerotvlive.projectsbase.utils.storage.types;

import java.sql.*;

public class MySQL {

    private String host;
    private String port;
    private String database;
    private String username;
    private Connection con;

    public MySQL(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        if (!isConnected()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public Connection getConnection() {
        return con;
    }

    public boolean isConnected() {
        return (con != null);
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        con = null;
        host = null;
        port = null;
        database = null;
        username = null;
        System.gc();
    }
}