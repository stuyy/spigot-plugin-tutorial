import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class MyEventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)   {
        event.setJoinMessage("Hey there! Welcome to the server " + event.getPlayer().getDisplayName());
        Bukkit.broadcastMessage("Welcome " + event.getPlayer().getDisplayName() + " to the server!");
    }

    @EventHandler
    public void onPlayerFishing(PlayerFishEvent event) {
        Bukkit.broadcastMessage(
                event.getPlayer().getDisplayName() + " is now fishing!"
        );
    }
}
