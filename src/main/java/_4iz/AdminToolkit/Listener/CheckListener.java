package _4iz.AdminToolkit.Listener;

import _4iz.AdminToolkit.Main;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;

public class CheckListener implements Listener {

    private final Main plugin;

    public CheckListener(Main plugin){
        this.plugin = plugin;
    }


// Запрещает ломать блоки
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (event.isCancelled()) return;

        if (plugin.isFrozen(player)) {
            event.setCancelled(true);
        }
    }

    // Запрещает ставить блоки
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (plugin.isFrozen(player)){
            event.setCancelled(true);
        }
    }
// Запрещает дропать предметы
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (plugin.isFrozen(player)){
            event.setCancelled(true);
        }
    }
// Запрещает взаимодействия в инвентаре
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.isFrozen(player)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        if (plugin.isFrozen(player)){
            event.setCancelled(true);
        }
    }

// Запрещает движения
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        if (plugin.isFrozen(player)){
            if (event.getFrom().getX() != event.getTo().getX() ||
                    (event.getFrom().getY() != event.getTo().getY() ||
                            (event.getFrom().getZ() != event.getTo().getZ()))){

                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void QuitPlayer(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (plugin.isFrozen(player)) {
            Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(),
                    plugin.getConfig().getString(
                            "ban-leave-reason", "Уход от проверки"), null, null);
            player.sendMessage(
                    "Игрок был заблокирован по причине: " + plugin.getConfig().getString(
                            "ban-leave-reason", "Уход от проверки"));
            plugin.getLogger().info(
                    "Игрок был заблокирован по причине: " + plugin.getConfig().getString(
                            "ban-leave-reason", "Уход от проверки"));
        }
    }
}
