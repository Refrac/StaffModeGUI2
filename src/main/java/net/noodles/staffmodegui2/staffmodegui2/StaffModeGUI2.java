package net.noodles.staffmodegui2.staffmodegui2;

import net.noodles.staffmodegui2.staffmodegui2.Events.UpdateNotification;
import net.noodles.staffmodegui2.staffmodegui2.Inv.MainInv;
import net.noodles.staffmodegui2.staffmodegui2.Inv.TimeInv;
import net.noodles.staffmodegui2.staffmodegui2.Inv.WeatherInv;
import net.noodles.staffmodegui2.staffmodegui2.Inv.WhitelistInv;
import net.noodles.staffmodegui2.staffmodegui2.commands.StaffModeCommand;
import net.noodles.staffmodegui2.staffmodegui2.commands.UpdateCommand;
import net.noodles.staffmodegui2.staffmodegui2.util.Glow;
import net.noodles.staffmodegui2.staffmodegui2.util.Logger;
import net.noodles.staffmodegui2.staffmodegui2.util.Settings;
import net.noodles.staffmodegui2.staffmodegui2.util.UpdateChecker.UpdateChecker;
import org.bukkit.command.CommandExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

public final class StaffModeGUI2 extends JavaPlugin {

    public static StaffModeGUI2 plugin;
    private UpdateChecker checker;
    private static StaffModeGUI2 instance;


    //INV
    private MainInv mainInv;
    private WhitelistInv whitelistInv;
    private TimeInv timeInv;
    private WeatherInv weatherInv;



    @Override
    public void onEnable() {
        Logger.log(Logger.LogLevel.OUTLINE,  "********************");
        Logger.log(Logger.LogLevel.INFO, "Initializing StaffModeGUI2 Version: " + Settings.VERSION);
        Logger.log(Logger.LogLevel.INFO, "Created by: " + Settings.DEVELOPER_NAME);
        Logger.log(Logger.LogLevel.INFO, "Website: " + Settings.DEVELOPER_URL);
        Logger.log(Logger.LogLevel.INFO, "Spigot Link: " + Settings.PLUGIN_URL);
        Logger.log(Logger.LogLevel.INFO, "Support Link: " + Settings.SUPPORT_DISCORD_URL);
        Logger.log(Logger.LogLevel.OUTLINE,  "********************");
        Logger.log(Logger.LogLevel.INFO, "Plugin Loading...");
        Logger.log(Logger.LogLevel.INFO, "Registering Managers...");
        this.plugin = this;
        instance = this;
        this.registerGlow();
        Logger.log(Logger.LogLevel.INFO, "Managers Registered!");
        Logger.log(Logger.LogLevel.INFO, "Registering Listeners...");
        registerListener(
                new UpdateNotification(this),
                new MainInv(this),
                new TimeInv(this),
                new WhitelistInv(this),
                new WeatherInv(this)

        );
        Logger.log(Logger.LogLevel.INFO, "Listeners Registered!");
        Logger.log(Logger.LogLevel.INFO, "Registering Commands...");
        new StaffModeCommand();
        new UpdateCommand();
        Logger.log(Logger.LogLevel.INFO, "Commands Registered!");
        Logger.log(Logger.LogLevel.INFO, "Loading Inventory's...");
        this.mainInv = new MainInv(this);
        this.whitelistInv = new WhitelistInv(this);
        this.timeInv = new TimeInv(this);
        this.weatherInv = new WeatherInv(this);
        Logger.log(Logger.LogLevel.INFO, "Inventory's Registered!");
        Logger.log(Logger.LogLevel.INFO, "Loading Config's...");
        this.createConfig();
        Logger.log(Logger.LogLevel.INFO, "Config's Registered!");
        Logger.log(Logger.LogLevel.SUCCESS, "StaffModeGUI2 Version: " + Settings.VERSION + " Loaded.");
        this.setEnabled(true);
        Logger.log(Logger.LogLevel.OUTLINE,  "********************");
        Logger.log(Logger.LogLevel.INFO, "Checking for updates...");
        this.checker = new UpdateChecker(this);
        if (this.checker.isConnected()) {
            if (this.checker.hasUpdate()) {
                Logger.log(Logger.LogLevel.OUTLINE,  "********************");
                Logger.log(Logger.LogLevel.WARNING,("StaffModeGUI2 is outdated!"));
                Logger.log(Logger.LogLevel.WARNING,("Newest version: " + this.checker.getLatestVersion()));
                Logger.log(Logger.LogLevel.WARNING,("Your version: " + Settings.VERSION));
                Logger.log(Logger.LogLevel.WARNING,("Please Update Here: " + Settings.PLUGIN_URL));
                Logger.log(Logger.LogLevel.OUTLINE,  "********************");
            } else {
                Logger.log(Logger.LogLevel.SUCCESS, "StaffModeGUI2 is up to date!");
            }
        }
    }



    @Override
    public void onDisable() {
    }


    private void registerListener(Listener... listeners) {
        Arrays.stream(listeners).forEach(l -> getServer().getPluginManager().registerEvents(l, this));
    }


    public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = new Glow(70);
            Enchantment.registerEnchantment(glow);
        } catch (IllegalArgumentException ex) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static StaffModeGUI2 getPlugin() {
        return (StaffModeGUI2) getPlugin((Class) StaffModeGUI2.class);
    }

    private void createConfig(){
        try{
            if(!getDataFolder().exists()) getDataFolder().mkdirs();

            File file = new File(getDataFolder(), "config.yml");
            if(!file.exists()){
                saveDefaultConfig();
            }

        }catch(Exception exception){
            getLogger().info(exception.getMessage());
        }
    }


    public static StaffModeGUI2 getInstance() {
        return instance;
    }


    //INV

    public MainInv getMainInv() {
        return mainInv;
    }

    public WhitelistInv getWhitelistInv() {
        return whitelistInv;
    }

    public TimeInv getTimeInv() {
        return timeInv;
    }

    public WeatherInv getWeatherInv() {
        return weatherInv;
    }


}

