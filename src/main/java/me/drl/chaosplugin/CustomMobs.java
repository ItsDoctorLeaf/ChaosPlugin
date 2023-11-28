package me.drl.chaosplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;

public class CustomMobs{
    static void SpawnGiant(Location location)
    {
        LivingEntity e = (Giant) location.getWorld().spawnEntity( location, EntityType.GIANT);
        e.setCustomName(ChatColor.RED + "CHAD.");

        e.setCustomNameVisible(true);

        Attributable mobAttribute = e;
        AttributeInstance maxHealth = mobAttribute.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        maxHealth.setBaseValue(100);
        e.setHealth(100);

        e.setAI(true);

        LivingEntity e2 = (Zombie) location.getWorld().spawnEntity( location, EntityType.ZOMBIE);
        e.addPassenger(e2);

    }



}
