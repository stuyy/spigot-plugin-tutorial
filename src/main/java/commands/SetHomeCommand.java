package commands;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import entities.User;
import entities.UserHome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class SetHomeCommand implements CommandExecutor {

    private Dao<User, Integer> userDao;
    private Dao<UserHome, Integer> userHomeDao;

    public SetHomeCommand() {
    }

    public SetHomeCommand(Dao<User, Integer> userDao, Dao<UserHome, Integer> userHomeDao) {
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
                    UserHome userHome = user.getHome();
                    if (userHome != null) {
                        UserHome userHomeDB = this.userHomeDao.queryForId(userHome.getId());
                        if (userHomeDB != null) {
                            System.out.println(userHome.getId());
                            userHome.setCoordinates(
                                    player.getLocation().getX(),
                                    player.getLocation().getY(),
                                    player.getLocation().getZ());
                            userHome.setUser(user);
                            this.userHomeDao.update(userHome);
                            player.sendMessage("Your coordinates for your home was updated.");
                            System.out.println("Updated User Coordinates");
                        } else {
                            UserHome newUserHome = new UserHome(
                                    player.getLocation().getX(),
                                    player.getLocation().getY(),
                                    player.getLocation().getZ()
                            );
                            newUserHome.setUser(user);
                            this.userHomeDao.create(newUserHome);
                            user.setHome(newUserHome);
                            this.userDao.update(user);
                            player.sendMessage("You have set your new home!");
                        }
                    } else {
                        UserHome newUserHome = new UserHome(
                                player.getLocation().getX(),
                                player.getLocation().getY(),
                                player.getLocation().getZ()
                        );
                        newUserHome.setUser(user);
                        this.userHomeDao.create(newUserHome);
                        user.setHome(newUserHome);
                        this.userDao.update(user);
                        player.sendMessage("You have set your new home!");
                    }
                } else {
                    System.out.println("User was not found...");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return true;
    }


}
