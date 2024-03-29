package me.mateo.announce.bungee;

import com.google.common.io.ByteStreams;
import me.mateo.announce.bungee.commands.AnnounceCommand;
import me.mateo.announce.bungee.commands.AnnounceReloadCommand;
import me.mateo.announce.bungee.tasks.AnnounceTask;
import me.mateo.announce.bungee.utils.Logger;
import me.mateo.announce.bungee.utils.Utils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Zachary Baldwin / Refrac
 */
public final class BungeeAnnounce extends Plugin {

    private static BungeeAnnounce instance;
    private static Configuration config;

    @Override
    public void onEnable() {
        instance = this;

        loadConfig();

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new AnnounceCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new AnnounceReloadCommand());

        ProxyServer.getInstance().getScheduler().schedule(this, new AnnounceTask(), 0, getConfig().getInt("Interval"), TimeUnit.SECONDS);
        sendBanner();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;

        ProxyServer.getInstance().getScheduler().cancel(this);
        ProxyServer.getInstance().getPluginManager().unregisterCommands(this);
    }

    public static BungeeAnnounce getInstance() {
        return instance;
    }

    public static Configuration getConfig() {
        return config;
    }

    public void loadConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(
                    loadResource(this, "bungee-config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File loadResource(Plugin plugin, String resource) {
        File folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, resource);
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                try (InputStream in = plugin.getResourceAsStream(resource);
                     OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceFile;
    }
    public void sendBanner() {
        Logger.NONE.out(Utils.format("&8&m==&c&m=====&f&m======================&c&m=====&8&m=="));
        Logger.NONE.out(Utils.format("&e" + Utils.getName + " Activado."));
        Logger.NONE.out(Utils.format(" &f[*] &6Version&f: &b" + Utils.getVersion));
        Logger.NONE.out(Utils.format(" &f[*] &6Author&f: &b" + Utils.getDeveloper));
        Logger.NONE.out(Utils.format("&8&m==&c&m=====&f&m======================&c&m=====&8&m=="));
    }
}