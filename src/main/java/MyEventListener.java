import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import entities.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class MyEventListener implements Listener {

    private Dao<User, Integer> userDao;

    public MyEventListener() {

    }

    public MyEventListener(Dao<User, Integer> userDao) {
        this.userDao = userDao;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
//        event.setJoinMessage("Hey there! Welcome to the server " + event.getPlayer().getDisplayName());
//        Bukkit.broadcastMessage("Welcome " + event.getPlayer().getDisplayName() + " to the server!");
        Player player = event.getPlayer();
        String uniqueId = player.getUniqueId().toString();
        String username = player.getDisplayName();
        QueryBuilder<User, Integer> queryBuilder = this.userDao.queryBuilder();
        try {
            queryBuilder.where().eq("uniqueId", uniqueId);
            PreparedQuery<User> preparedQuery = queryBuilder.prepare();
            User user = this.userDao.queryForFirst(preparedQuery);
            if (user != null) {
                String formattedMessage = String.format("Welcome back to the server, %s!", username);
                Bukkit.broadcastMessage(formattedMessage);
            } else {
                User newUser = new User();
                newUser.setUsername(username);
                newUser.setUniqueId(uniqueId);
                this.userDao.create(newUser);
                System.out.println("Created New User");
                String formattedMessage = String.format("Welcome to the server for the first time, %s!", username);
                Bukkit.broadcastMessage(formattedMessage);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @EventHandler
    public void onPlayerFishing(PlayerFishEvent event) {
        Bukkit.broadcastMessage(
                event.getPlayer().getDisplayName() + " is now fishing!"
        );
    }
}
