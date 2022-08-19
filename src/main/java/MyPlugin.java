import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import entities.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Level;

public class MyPlugin extends JavaPlugin  {

    public MyPlugin() throws SQLException {
        String databaseUrl = "jdbc:mysql://testuser:testuser123@localhost/minecraft_spigot_server_db";
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
        Dao<User, Integer> userDao = DaoManager.createDao(connectionSource, User.class);
        TableUtils.createTable(connectionSource, User.class);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
    @Override
    public void onEnable() {
        super.onEnable();
        this.getLogger().log(Level.INFO, "MyPlugin Successfully Loaded!");
        this.getLogger().log(Level.INFO, "Hello, World!");
        getLogger().log(Level.INFO, "Hey. My Plugin!");
        getServer().getPluginManager().registerEvents(new MyEventListener(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        getLogger().log(Level.INFO, sender.getName() + " issued the command: " + command.getName());
        for (String arg : args) {
            getLogger().log(Level.INFO, arg);
        }
        return true;
    }
}
