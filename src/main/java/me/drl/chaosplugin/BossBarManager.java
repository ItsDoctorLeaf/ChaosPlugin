package me.drl.chaosplugin;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BossBarManager {

    BossBar bar;

    public BossBarManager(String title, BarColor colour, BarStyle style)
    {
        SetBossBar(title,colour,style);
    }

    void SetBossBar(String title, BarColor colour, BarStyle style)
    {
        bar.setTitle(title);
        bar.setColor(colour);
        bar.setStyle(style);
    }

    void SetBossBar(String title)
    {
        SetBossBar(title,bar.getColor(),bar.getStyle());

    }
    void SetBossBar(BarStyle style)
    {
        SetBossBar(bar.getTitle(), bar.getColor(), style);
    }
    void SetBossBar(BarColor colour)
    {
        SetBossBar(bar.getTitle(), colour, bar.getStyle());
    }

    void BossBarAdd(Player player)
    {
        bar.addPlayer(player);

    }
    void BossBarAdd(ArrayList<Player> players)
    {
        for (Player player : players)
        {
            bar.addPlayer(player);
        }
    }

    void BossBarRemove(Player player)
    {
        bar.addPlayer(player);

    }
    void BossBarRemove(ArrayList<Player> players)
    {
        for (Player player : players)
        {
            bar.addPlayer(player);
        }
    }
    void Visibility(boolean state)
    {
        bar.setVisible(state);
    }

    double GetProgress()
    {
        return bar.getProgress();
    }

    void SetProgress(double progress)
    {
        bar.setProgress(progress);
    }
}
