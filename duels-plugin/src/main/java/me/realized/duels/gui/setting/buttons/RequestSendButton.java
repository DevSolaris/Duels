package me.realized.duels.gui.setting.buttons;

import me.realized.duels.DuelsPlugin;
import me.realized.duels.gui.BaseButton;
import me.realized.duels.setting.Settings;
import me.realized.duels.util.inventory.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RequestSendButton extends BaseButton {

    public RequestSendButton(final DuelsPlugin plugin) {
        super(plugin, ItemBuilder.of(Material.STAINED_GLASS_PANE, 1, (short) 5).name(plugin.getLang().getMessage("GUI.settings.buttons.send.name")).build());
    }

    @Override
    public void onClick(final Player player) {
        final Settings settings = settingManager.getSafely(player);

        if (settings.getTarget() == null) {
            settings.reset();
            player.closeInventory();
            return;
        }

        final Player target = Bukkit.getPlayer(settings.getTarget());

        if (target == null) {
            settings.reset();
            player.closeInventory();
            lang.sendMessage(player, "ERROR.player.no-longer-online");
            return;
        }

        if (!config.isUseOwnInventoryEnabled() && settings.getKit() == null) {
            player.closeInventory();
            return;
        }

        player.closeInventory();
        requestManager.send(player, target, settings);
    }
}
