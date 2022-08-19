import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import commands.HomeCommand;
import commands.SetHomeCommand;
import commands.TeleportRequestCommand;
import commands.WhisperMessageCommand;
import entities.User;
import entities.UserHome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Level;

public class MyPlugin extends JavaPlugin {

    private String databaseUrl = "jdbc:mysql://testuser:testuser123@localhost/minecraft_spigot_server_db";
    private ConnectionSource connectionSource;
    private Dao<User, Integer> userDao;
    private Dao<UserHome, Integer> userHomeDao;

    public MyPlugin() throws SQLException {
        this.connectionSource = new JdbcConnectionSource(databaseUrl);
        this.userDao = DaoManager.createDao(connectionSource, User.class);
        this.userHomeDao = DaoManager.createDao(connectionSource, UserHome.class);
        TableUtils.createTableIfNotExists(connectionSource, User.class);
        TableUtils.createTableIfNotExists(connectionSource, UserHome.class);
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
        getServer().getPluginManager().registerEvents(new MyEventListener(this.userDao), this);
        getCommand("tprequest").setExecutor(new TeleportRequestCommand());
        getCommand("whispermsg").setExecutor(new WhisperMessageCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand(this.userDao, this.userHomeDao));
        getCommand("home").setExecutor(new HomeCommand(this.userDao, this.userHomeDao));
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
