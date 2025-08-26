package _4iz.AdminToolkit.Listener;

import _4iz.AdminToolkit.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Check implements Listener {

    private final Main plugin;

    public Check(Main main){
        this.plugin = plugin;
    }
// Запрещает ломать блоки
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
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
}
