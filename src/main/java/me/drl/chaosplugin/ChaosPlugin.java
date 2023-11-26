package me.drl.chaosplugin;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;


public final class ChaosPlugin extends JavaPlugin implements Listener, CommandExecutor {

    int currentEventIndex;
    BossBarManager manager = new BossBarManager(this,"EVENT NULL",BarColor.WHITE, BarStyle.SOLID);
    ChaosSettings settings = new ChaosSettings();
    final int Seconds = 30;

    Random random = new Random();
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("--- CHAOS MOD ENABLED ---");
        getServer().getPluginManager().registerEvents(this,this);
        getCommand("startgame").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                RandomBossEvent();
                manager.Visibility(true);
                return true;
            }
        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("--- CHAOS MOD DISABLED ---");
    }
    void StartCountdown()
    {
        BukkitRunnable barCountdown = new BukkitRunnable() {
            double progress = 1;
            @Override
            public void run() {
                manager.SetProgress(1);
                progress -= 1f/(float)(Seconds*20);
                if (progress <= 0)
                {
                    OnCountdownOver();
                    this.cancel();
                }
            }
        };

        barCountdown.runTaskTimer(this,0,20* Seconds);
    }

    void OnCountdownOver()
    {
        RandomBossEvent();
    }

    void RandomBossEvent()
    {
        int randomIndex = random.nextInt(settings.settings.length);
        manager.NewEvent(settings.settings[randomIndex]);
        StartCountdown();
    }
    ChaosEvent IndexToEvent(int index)
    {
        return ChaosEvent.values()[index];
    }

    int EventToIndex(ChaosEvent event)
    {
        return event.ordinal();
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event)
    {
        manager.BossBarAdd(event.getPlayer());

    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event)
    {
        manager.BossBarRemove(event.getPlayer());
    }
}
enum ChaosEvent
{
    TNT_RAIN,
    EXPLOSIVE_FEET,
    LOW_GRAVITY,
    MANY_MOBS,
    EARTHQUAKES,
    BLINDNESS,
    STATIC,
    SANIC,
    SPLIT_THE_SEAS,
    LAUNCH_IN_THE_AIR,
    AMBUSH,
    SUPER_LOOTS,
    INSTA_BREAK,
    INVIS,
    GOD_MODE,
    MORTAL_MODE,
    FAKE_KICK,
    EMPTY

}
class ChaosEventDisplaySetting
{
    public ChaosEventDisplaySetting(String title, BarColor colour)
    {
        this.title = title;
        this.colour = colour;
    }
    String title;
    BarColor colour;

}
class ChaosSettings
{
    public ChaosEventDisplaySetting[] settings = {
            new ChaosEventDisplaySetting("TNT Rain.",BarColor.RED),
            new ChaosEventDisplaySetting("Explosive Feet.",BarColor.RED),
            new ChaosEventDisplaySetting("To the moon!",BarColor.WHITE),
            new ChaosEventDisplaySetting("We're surrounded...",BarColor.GREEN),
            new ChaosEventDisplaySetting("Earthquake!",BarColor.PURPLE),
            new ChaosEventDisplaySetting("I can't see.",BarColor.PURPLE),
            new ChaosEventDisplaySetting("ICED",BarColor.BLUE),
            new ChaosEventDisplaySetting("Gotta go fast!",BarColor.BLUE),
            new ChaosEventDisplaySetting("Split the seas!",BarColor.BLUE),
            new ChaosEventDisplaySetting("Gotta fly now",BarColor.WHITE),
            new ChaosEventDisplaySetting("ITS AN AMBUSH",BarColor.GREEN),
            new ChaosEventDisplaySetting("Here comes the money.",BarColor.YELLOW),
            new ChaosEventDisplaySetting("Break it baby",BarColor.YELLOW),
            new ChaosEventDisplaySetting("I can't be seen?",BarColor.PURPLE),
            new ChaosEventDisplaySetting("I AM A GOD BEYOND ALL",BarColor.YELLOW),
            new ChaosEventDisplaySetting("Mortality sucks",BarColor.RED),
            new ChaosEventDisplaySetting("Kicking from server...",BarColor.RED),
            new ChaosEventDisplaySetting("NULL EVENT",BarColor.WHITE),
    };
}