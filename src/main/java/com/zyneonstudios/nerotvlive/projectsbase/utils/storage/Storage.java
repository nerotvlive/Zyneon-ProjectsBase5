package com.zyneonstudios.nerotvlive.projectsbase.utils.storage;

import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.types.Config;
import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.types.MySQL;
import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.types.SQLite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Storage {

    private storageType type;
    private Connection connection;
    private MySQL mysql;
    private SQLite sqLite;
    private Config yaml;

    public Storage(MySQL mysql) {
        this.type = storageType.MySQL;
        this.mysql = mysql;
        this.connection = this.mysql.getConnection();

        this.sqLite = null;
        this.yaml = null;
        setupTable("storage", 1);
    }

    public Storage(SQLite sqLite) {
        this.type = storageType.SQLite;
        this.sqLite = sqLite;
        this.connection = this.sqLite.getConnection();

        this.mysql = null;
        this.yaml = null;
        setupTable("storage", 1);
    }

    public Storage(Config config) {
        this.type = storageType.YAML;
        this.yaml = config;

        this.mysql = null;
        this.sqLite = null;
        this.connection = null;
    }

    public Storage(String host, String port, String database, String username, String password) {
        this.type = storageType.MySQL;
        this.mysql = new MySQL(host, port, database, username, password);
        this.connection = this.mysql.getConnection();

        this.sqLite = null;
        this.yaml = null;
        setupTable("storage", 1);
    }

    public Storage(String path) {
        if (path.contains(".yml")) {
            this.type = storageType.YAML;
            this.yaml = new Config(path);

            this.mysql = null;
            this.sqLite = null;
            this.connection = null;
        } else {
            this.type = storageType.SQLite;
            this.sqLite = new SQLite(path);
            this.connection = this.sqLite.getConnection();

            this.mysql = null;
            this.yaml = null;
            setupTable("storage", 1);
        }
    }

    public void setupTable(String table, int contentSize) {
        if (type != storageType.YAML) {
            try {
                if (contentSize > 1) {
                    String statement = "CREATE TABLE IF NOT EXISTS " + table + " (path TEXT";
                    for (int i = 0; i < contentSize; i++) {
                        statement = statement + ",content" + i + " TEXT";
                    }
                    statement = statement + ")";
                    connection.prepareStatement(statement).executeUpdate();
                } else {
                    connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + " (path TEXT,content0 TEXT)").executeUpdate();
                }
            } catch (SQLException e) {
                Communicator.sendError("§8=============================================================");
                Communicator.sendError("Couldn't connect to the " + type.toString() + "-Storage (" + table + ")...");
                for (int i = 0; i < e.getStackTrace().length; i++) {
                }
                Communicator.sendError("§8=============================================================");
            }
        }
    }

    public Storage.storageType getStorageType() {
        return type;
    }

    public Connection getConnection() {
        return connection;
    }

    public MySQL getMySQL() {
        return mysql;
    }

    public SQLite getSQLite() {
        return sqLite;
    }

    public Config getYaml() {
        return yaml;
    }

    public boolean exist(String path) {
        if (type == storageType.YAML) {
            return yaml.get(path) != null;
        } else {
            String data = null;
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT content0 FROM storage WHERE path = ?");
                ps.setString(1, path);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if(rs.getString("content0")!=null) {
                        data = rs.getString("content0");
                    }
                }
            } catch (SQLException ignore) {}
            return data != null;
        }
    }

    public boolean exist(String table, String path) {
        if (type == storageType.YAML) {
            return yaml.get(path) != null;
        } else {
            String data = null;
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT content0 FROM "+table+" WHERE path = ?");
                ps.setString(1, path);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if(rs.getString("content0")!=null) {
                        data = rs.getString("content0");
                    }
                }
            } catch (SQLException ignore) {}
            return data != null;
        }
    }

    public Object get(String path) {
        if (type == storageType.YAML) {
            return yaml.get("storage." + path + ".content0");
        } else {
            String data = null;
            if (exist(path)) {
                try {
                    PreparedStatement ps = connection.prepareStatement("SELECT content0 FROM storage WHERE path = ?");
                    ps.setString(1, path);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getString("content0");
                    }
                } catch (SQLException ignore) {
                }
            }
            return data;
        }
    }

    public Object get(String table, String path, int row) {
        if (type == storageType.YAML) {
            return yaml.get(table + "." + path + ".content" + row);
        } else {
            String data = null;
            if (exist(table,path)) {
                try {
                    PreparedStatement ps = connection.prepareStatement("SELECT content" + row + " FROM " + table + " WHERE path = ?");
                    ps.setString(1, path);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getString("content" + row);
                    }
                } catch (SQLException ignore) {
                }
            }
            return data;
        }
    }

    public void set(String path, Object content) {
        if (type == storageType.YAML) {
            if (path.contains(".content")) {
                yaml.set(path, content);
            } else {
                yaml.set("storage." + path + ".content0", content);
            }
            yaml.saveConfig();
            yaml.reloadConfig();
        } else {
            try {
                if (exist(path)) {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM storage WHERE path = ?");
                    ps.setString(1, path);
                    ps.executeUpdate();
                }
                PreparedStatement ps = connection.prepareStatement("INSERT INTO storage (path,content0) VALUES (?,?)");
                ps.setString(1, path);
                ps.setString(2, content.toString());
                ps.executeUpdate();
            } catch (SQLException ignore) {
            }
        }
    }

    public void set(String table, String path, Object... content) {
        if (type == storageType.YAML) {
            if (content.length < 2) {
                path = table + "." + path + ".content0";
                set(path, content[0]);
            } else {
                int row = 0;
                path = table + "." + path + ".content";
                for (Object s : content) {
                    set(path + row, s);
                    row = row + 1;
                }
            }
        } else {
            setupTable(table, content.length);
            PreparedStatement ps;
            try {
                if (content.length < 2) {
                    if (exist(table,path)) {
                        ps = connection.prepareStatement("DELETE FROM " + table + " WHERE path = ?");
                        ps.setString(1, path);
                        ps.executeUpdate();
                    }
                    ps = connection.prepareStatement("INSERT INTO " + table + " (path,content0) VALUES (?,?)");
                    ps.setString(1, path);
                    ps.setString(2, content[0].toString());
                    ps.executeUpdate();
                } else {
                    String contents = "";
                    String questionMarks = "";
                    for (int i = 0; i < content.length; i++) {
                        if (i > 0) {
                            contents = contents + ",content" + i;
                            questionMarks = questionMarks + ",?";
                        }
                    }
                    if (exist(table,path)) {
                        ps = connection.prepareStatement("DELETE FROM " + table + " WHERE path = ?");
                        ps.setString(1, path);
                        ps.executeUpdate();
                    }
                    ps = connection.prepareStatement("INSERT INTO " + table + " (path,content0" + contents + ") VALUES (?,?" + questionMarks + ")");
                    ps.setString(1, path);
                    int row = 2;
                    for (Object s : content) {
                        ps.setString(row, s.toString());
                        row = row + 1;
                    }
                    ps.executeUpdate();
                }
            } catch (SQLException ignore) {
            }
        }
    }

    public String getString(String path) {
        if (get(path) == null) {
            return "null";
        }
        return get(path).toString();
    }

    public String getString(String table, String path, int row) {
        if (get(table, path, row) == null) {
            return "null";
        }
        return get(table, path, row).toString();
    }

    public void setString(String table, String path, String content, int row) {
        set(table, path, content, row);
    }

    public int getInteger(String path) {
        try {
            if (get(path) == null) {
                return -1;
            }
            return Integer.parseInt(get(path).toString().replace("I", ""));
        } catch (NumberFormatException | NullPointerException ignore) {
            return -1;
        }
    }

    public int getInteger(String table, String path, int row) {
        try {
            if (get(table, path, row) == null) {
                return -1;
            }
            return Integer.parseInt(get(table, path, row).toString().replace("I", ""));
        } catch (NumberFormatException | NullPointerException ignore) {
            return -1;
        }
    }

    public void setInteger(String path, int content) {
        set(path, content + "I");
    }

    public void setInteger(String table, String path, int content) {
        set(table, path, content + "I");
    }

    public double getDouble(String path) {
        try {
            if (get(path) == null) {
                return -1;
            }
            return Double.parseDouble(get(path).toString().replace("D", ""));
        } catch (NumberFormatException | NullPointerException ignore) {
            return -1;
        }
    }

    public double getDouble(String table, String path, int row) {
        try {
            if (get(table, path, row) == null) {
                return -1;
            }
            return Double.parseDouble(get(table, path, row).toString().replace("D", ""));
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

    public void setDouble(String path, double content) {
        set(path, content + "D");
    }

    public void setDouble(String table, String path, double content) {
        set(table, path, content + "D");
    }

    public void unload() {
        if (type == storageType.YAML) {
            yaml.saveConfig();
            yaml.unload();
            this.yaml = null;
        } else if (type == storageType.MySQL) {
            connection = null;
            mysql.disconnect();
            this.mysql = null;
        } else if (type == storageType.SQLite) {
            connection = null;
            sqLite.disconnect();
            this.sqLite = null;
        }
        this.type = null;
        System.gc();
    }

    public enum storageType {
        MySQL,
        SQLite,
        YAML
    }
}