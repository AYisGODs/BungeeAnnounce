
package me.mateo.announce.bungee.tasks;

import me.mateo.announce.bungee.BungeeAnnounce;
import me.mateo.announce.bungee.utils.Utils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.Configuration;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;


public class AnnounceTask implements Runnable {

    @Override
    public void run() {
        Set<String> broadcastList = (Set<String>) BungeeAnnounce.getConfig().getSection("Anuncios").getKeys();
        String broadcastId = getRandom(broadcastList);
        Configuration broadcast = BungeeAnnounce.getConfig().getSection("Anuncios." + broadcastId);
        for (String message : broadcast.getStringList("LINEAS")) {
            ProxyServer.getInstance().getPlayers().forEach((player -> player.sendMessage(Utils.formatComponent(message.replace("{arrow}", "»")))));
        }
    }

    private static String getRandom(Set<String> set) {
        int index = (new Random()).nextInt(set.size());
        Iterator<String> iterator = set.iterator();
        for (int i = 0; i < index; i++) {
            iterator.next();
        }
        return iterator.next();
    }
}