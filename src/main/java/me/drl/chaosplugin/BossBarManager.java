package me.drl.chaosplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class BossBarManager {

    BossBar bar;
    BossBarManager manager = this;
    Plugin plugin;


    public BossBarManager(Plugin plugin, String title, BarColor colour, BarStyle style)
    {
        this.bar = Bukkit.createBossBar(title,colour,style);
        SetBossBar(title,colour,style);
        this.plugin = plugin;
    }

    void SetBossBar(String title, BarColor colour, BarStyle style)
    {
        bar.setTitle(title);
        bar.setColor(colour);
        bar.setStyle(style);
    }

    void SetBossBar(ChaosEventDisplaySetting setting)
    {
        SetBossBar(setting.title,setting.colour,bar.getStyle());
    }

    void BossBarAdd(Player player)
    {
        bar.addPlayer(player);

    }


    void BossBarRemove(Player player)
    {
        bar.addPlayer(player);

    }

    void Visibility(boolean state)
    {
        bar.setVisible(state);
    }

    void SetProgress(double progress)
    {
        bar.setProgress(progress);
    }

    void NewEvent(ChaosEventDisplaySetting setting)
    {
        SetBossBar(setting);
        SetProgress(1);
    }
}
