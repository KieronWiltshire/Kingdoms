package kieronwiltshire.com.kingdoms;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import kieronwiltshire.com.kingdoms.gameplay.Clan;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public final class Kingdoms extends JavaPlugin {

    private Connection connection;
    private Database database;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        try {
            this.connection = this.initDatabase();
        } catch (SQLException e) {
            this.getServer().getConsoleSender().sendMessage("&4Unable to connect to the specified database.");
            this.getServer().shutdown();
        }
    }

    @Override
    public void onDisable() {
        DatabaseFactory.shutdown();
    }

    /**
     * Begin the database setup and connection process.
     *
     * @throws SQLException
     */
    private Connection initDatabase() throws SQLException {
        String schema = this.getConfig().getString("connections.sql.database");
        String url = String.format("jdbc:%s://%s:%s/%s?serverTimezone=UTC",
                this.getConfig().getString("connections.sql.driver"),
                this.getConfig().getString("connections.sql.host"),
                this.getConfig().getString("connections.sql.port"),
                schema);

        DataSourceConfig dsc = new DataSourceConfig()
                .setUrl(url);

        dsc.setUsername(this.getConfig().getString("connections.sql.username"));
        dsc.setPassword(this.getConfig().getString("connections.sql.password"));
        dsc.setSchema(schema);

        DatabaseConfig uc = new DatabaseConfig();
        uc.setDataSourceConfig(dsc);
        uc.setDdlGenerate(true);
        uc.setDdlRun(true);
        uc.setName(schema);

        this.database = DatabaseFactory.createWithContextClassLoader(uc, this.getClassLoader());

        return this.database.getPluginApi().getDataSource().getConnection();
    }

}
