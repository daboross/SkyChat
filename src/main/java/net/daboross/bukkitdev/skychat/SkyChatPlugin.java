/*
 * Copyright (C) 2014 Dabo Ross <http://daboross.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.skychat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyChatPlugin extends JavaPlugin implements Listener {

    private String format;

    private void loadFormat() {
        format = ChatColor.translateAlternateColorCodes('&', getConfig().getString("format").replace("{player}", "%1$s").replace("{message}", "%2$s"));
    }

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        saveDefaultConfig();
        loadFormat();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("skychatreload")) {
            reloadConfig();
            loadFormat();
            sender.sendMessage(ChatColor.DARK_GREEN + "Reloaded.");
            return true;
        }

        sender.sendMessage("SkyChat doesn't know about the command /" + cmd.getName());
        return true;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent evt) {
        evt.setFormat(format);
    }
}
