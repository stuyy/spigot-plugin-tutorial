package commands;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import entities.User;
import entities.UserHome;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class HomeCommand implements CommandExecutor {

    private Dao<User, Integer> userDao;
    private Dao<UserHome, Integer> userHomeDao;

    public HomeCommand() {

    }

    public HomeCommand(Dao<User, Integer> userDao, Dao<UserHome, Integer> userHomeDao) {
        this.userDao = userDao;
        this.userHomeDao = userHomeDao;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            QueryBuilder<User, Integer> queryBuilder = this.userDao.queryBuilder();
            try {
                queryBuilder.where().eq("uniqueId", player.getUniqueId().toString());
                PreparedQuery<User> preparedQuery = queryBuilder.prepare();
                User user = this.userDao.queryForFirst(preparedQuery);
                if (user != null) {
                    if (user.getHome() != null) {
                        UserHome userHome = this.userHomeDao.queryForId(user.getHome().getId());
                        if (userHome != null) {
                            Location location = new Location(
                                    player.getWorld(),
                                    userHome.getX(),
                                    userHome.getY(),
                                    userHome.getZ());
                            player.teleport(location);
                            String teleportSuccessMessage = String.format(
                                    "You have teleported to your home. x: %f y: %f z: %f",
                                    userHome.getX(),
                                    userHome.getY(),
                                    userHome.getZ());
                            player.sendMessage(teleportSuccessMessage);
                        } else {
                            player.sendMessage("You do not have a home set! Use /sethome to set your home.");
                        }
                    } else {
                        player.sendMessage("You have not set your home yet. Use /sethome to set your home!");
                    }
                } else {
                    System.out.println("User not found.");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return true;
    }
}
