/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.mateo.announce.bungee.commands;

import me.mateo.announce.bungee.BungeeAnnounce;
import me.mateo.announce.bungee.tasks.AnnounceTask;
import me.mateo.announce.bungee.utils.Utils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

import java.util.concurrent.TimeUnit;

/**
 * @author Zachary Baldwin / Refrac
 */
public class AnnounceReloadCommand extends Command {

    public AnnounceReloadCommand() {
        super("announcereload", "bungeeannounce.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        BungeeAnnounce.getInstance().loadConfig();
        ProxyServer.getInstance().getScheduler().cancel(BungeeAnnounce.getInstance());
        ProxyServer.getInstance().getScheduler().schedule(BungeeAnnounce.getInstance(), new AnnounceTask(), 0, BungeeAnnounce.getConfig().getInt("Interval"), TimeUnit.SECONDS);
        sender.sendMessage(Utils.formatComponent("&7Config files reloaded. Changes should be live in-game!"));
    }
}