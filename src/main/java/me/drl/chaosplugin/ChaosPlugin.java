package me.drl.chaosplugin;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public final class ChaosPlugin extends JavaPlugin implements Listener, CommandExecutor {

    ChaosEvent currentEvent = null;
    BossBarManager manager = new BossBarManager(this,"EVENT NULL",BarColor.WHITE, BarStyle.SOLID);
    ChaosSettings settings = new ChaosSettings();
    int Seconds = 20/*SECOND*/ * 10;
    Random random = new Random();
    
    FileConfiguration config = getConfig();
    @Override
    public void onEnable() {
        LoadConfig();
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
    void LoadConfig()
    {
        reloadConfig();
        config = this.getConfig();
        config.addDefault("timeBetweenEvents",10);

        config.options().copyDefaults(true);
        saveConfig();
        Seconds = config.getInt("timeBetweenEvents");

    }

    void StartCountdown()
    {
        BukkitRunnable barCountdown = new BukkitRunnable() {
            double progress = 1;
            @Override
            public void run() {
                progress -= (float)(1f/(float)Seconds);
                if (progress <= 0)
                {
                    OnCountdownOver();
                    this.cancel();
                }
                if (progress >= 0)
                {
                    manager.SetProgress(progress);
                }

            }
        };

        barCountdown.runTaskTimer(this,0,1);
    }

    void OnCountdownOver()
    {
        RandomBossEvent();
    }

    void RandomBossEvent()
    {
        int currentEventIndex = random.nextInt(settings.settings.length);
        manager.NewEvent(settings.settings[currentEventIndex]);
        currentEvent = IndexToEvent(currentEventIndex);
        EventStart();
        StartCountdown();


    }
    ChaosEvent IndexToEvent(int index)
    {
        ChaosEvent event = ChaosEvent.TNT_RAIN;
        switch (index)
        {
            case 0: event = ChaosEvent.TNT_RAIN;
                break;
            case 1: event = ChaosEvent.EXPLOSIVE_FEET;
                break;
            case 2: event = ChaosEvent.LOW_GRAVITY;
                break;
            case 3: event = ChaosEvent.MANY_MOBS;
                break;
            case 4: event = ChaosEvent.EARTHQUAKES;
                break;
            case 5: event = ChaosEvent.BLINDNESS;
                break;
            case 6: event = ChaosEvent.STATIC;
                break;
            case 7: event = ChaosEvent.SANIC;
                break;
            case 8: event = ChaosEvent.SPLIT_THE_SEAS;
                break;
            case 9: event = ChaosEvent.LAUNCH_IN_THE_AIR;
                break;
            case 10: event = ChaosEvent.AMBUSH;
                break;
            case 11: event = ChaosEvent.SUPER_LOOTS;
                break;
            case 12: event = ChaosEvent.INSTA_BREAK;
                break;
            case 13: event = ChaosEvent.INVIS;
                break;
            case 14: event = ChaosEvent.GOD_MODE;
                break;
            case 15: event = ChaosEvent.MORTAL_MODE;
                break;
            case 16: event = ChaosEvent.FAKE_KICK;
                break;
            case 17: event = ChaosEvent.DONT_BREAK_BLOCKS;
                break;
            case 18: event = ChaosEvent.DONT_ATTACK;
                break;
            case 19: event = ChaosEvent.TO_THE_NETHER;
                break;
            case 20: event = ChaosEvent.LAVA_FILL;
                break;
            case 21: event = ChaosEvent.MOB_SPAWNER_PLACE;
                break;
            case 22: event = ChaosEvent.BECOME_TREE;
                break;
            case 23: event = ChaosEvent.RAIN_FIRE;
                break;
            case 24: event = ChaosEvent.BLAZE_ATTACK;
                break;
            case 25: event = ChaosEvent.OBSIDIAN_PRISON;
                break;
            case 26: event = ChaosEvent.RANDOM_TELEPORT;
                break;
            case 27: event = ChaosEvent.TO_THE_SKY_TEMP;
                break;
            case 28: event = ChaosEvent.PHANTOM_SWARM;
                break;
            case 29: event = ChaosEvent.EXPLOSIVE_JUMP;
                break;
            case 30: event = ChaosEvent.CROWCH_FIRE;
                break;
            case 31: event = ChaosEvent.ZUES;
                break;
            case 32: event = ChaosEvent.EXTREME_EARTHQUAKE;
                break;
            case 33: event = ChaosEvent.DOGS;
                break;
            case 34: event = ChaosEvent.GIGA_GIANT;
                break;
            case 35: event = ChaosEvent.JUMPSCARE;
                break;
            case 36: event = ChaosEvent.WITHERED;
                break;
            case 37: event = ChaosEvent.GETROBBED;
                break;
            case 38: event = ChaosEvent.INVERSE_QUAKE;
                break;
            case 39: event = ChaosEvent.THE_WALLS;
                break;
            case 40: event = ChaosEvent.ALL_TOGETHER_NOW;
                break;


        }
        return event;
    }

    int EventToIndex(ChaosEvent event)
    {
        return event.ordinal();
    }

    ArrayList<Player> GetAllPlayers()
    {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }
    String f(String message)
    {
        return ChatColor.translateAlternateColorCodes('&',message);
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

//    --- EVENTS ---

    void EventStart()
    {
        if (currentEvent == ChaosEvent.LAUNCH_IN_THE_AIR)
        {
            for (Player p : GetAllPlayers())
            {
                p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,20,20,true,false));
            }
            return;
        }
        if (currentEvent == ChaosEvent.INSTA_BREAK)
        {
            for (Player p : GetAllPlayers())
            {
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,Seconds,255,true,false));
            }
            return;
        }
        if (currentEvent == ChaosEvent.INVIS)
        {
            for (Player p : GetAllPlayers())
            {
                p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,Seconds,255,true,false));
            }
            return;
        }
        if (currentEvent == ChaosEvent.BLINDNESS)
        {
            for (Player p : GetAllPlayers())
            {
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,Seconds,255,true,false));
            }
            return;
        }
        if (currentEvent == ChaosEvent.LOW_GRAVITY)
        {
            for (Player p : GetAllPlayers())
            {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,Seconds,1,true,false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,Seconds,10,true,false));
            }
            return;
        }
        if (currentEvent == ChaosEvent.SANIC)
        {
            for (Player p : GetAllPlayers())
            {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Seconds,10,true,false));
           }
            return;
        }
        if (currentEvent == ChaosEvent.FAKE_KICK)
        {
            Bukkit.broadcastMessage(f("&c") + "Server shutting down in 5s for maintenance. Please disconnect to prevent IO-Exceptions");
            return;
        }
        if (currentEvent == ChaosEvent.THE_WALLS)
        {
            for (Player p : GetAllPlayers())
            {
                Location pl = p.getLocation();
                World w = pl.getWorld();
                w.getBlockAt(new Location(p.getWorld(),pl.getX()+1,pl.getY()+1,pl.getZ()+0)).setType(Material.STONE);
                w.getBlockAt(new Location(p.getWorld(),pl.getX()-1,pl.getY()+1,pl.getZ()+0)).setType(Material.STONE);
                w.getBlockAt(new Location(p.getWorld(),pl.getX()+0,pl.getY()+1,pl.getZ()+1)).setType(Material.STONE);
                w.getBlockAt(new Location(p.getWorld(),pl.getX()+0,pl.getY()+1,pl.getZ()-1)).setType(Material.STONE);
            }
            return;
        }
        if (currentEvent == ChaosEvent.ALL_TOGETHER_NOW)
        {
            ArrayList<Player> players = GetAllPlayers();
            Player mainPlayer = players.get(random.nextInt(players.size()-1));
            for (Player p: players)
            {
                p.teleport(mainPlayer.getLocation());
            }
        }
        if (currentEvent == ChaosEvent.EARTHQUAKES)
        {
            BukkitRunnable runnable = new BukkitRunnable() {
                int countdown = Seconds/4;
                @Override
                public void run() {
                    for (Player p : GetAllPlayers())
                    {
                        Location pl = p.getLocation();
                        World w = p.getWorld();
                        w.getBlockAt(new Location(p.getWorld(),pl.getX()+.5,pl.getY()-1,pl.getZ()+.5)).breakNaturally();
                        w.getBlockAt(new Location(p.getWorld(),pl.getX()+.5,pl.getY()-1,pl.getZ()-.5)).breakNaturally();
                        w.getBlockAt(new Location(p.getWorld(),pl.getX()-.5,pl.getY()-1,pl.getZ()+.5)).breakNaturally();
                        w.getBlockAt(new Location(p.getWorld(),pl.getX()-.5,pl.getY()-1,pl.getZ()-.5)).breakNaturally();
                    }
                    countdown--;
                    if (countdown <= 0)
                    {
                        this.cancel();
                    }
                }
            };
            runnable.runTaskTimer(this,0,4);
            return;
        }
        if (currentEvent == ChaosEvent.INVERSE_QUAKE)
        {
            BukkitRunnable runnable = new BukkitRunnable() {
                int countdown = Seconds/5;
                @Override
                public void run() {
                    for (Player p : GetAllPlayers())
                    {
                        Location pl = p.getLocation();
                        World w = p.getWorld();
                        w.getBlockAt(new Location(p.getWorld(),pl.getX()+.5,pl.getY(),pl.getZ()+.5)).setType(Material.DIRT);
                        w.getBlockAt(new Location(p.getWorld(),pl.getX()+.5,pl.getY(),pl.getZ()-.5)).setType(Material.DIRT);;
                        w.getBlockAt(new Location(p.getWorld(),pl.getX()-.5,pl.getY(),pl.getZ()+.5)).setType(Material.DIRT);;
                        w.getBlockAt(new Location(p.getWorld(),pl.getX()-.5,pl.getY(),pl.getZ()-.5)).setType(Material.DIRT);
                        p.teleport(new Location(pl.getWorld(),0,1,0).add(pl));
                    }
                    countdown--;
                    if (countdown <= 0)
                    {
                        this.cancel();
                    }
                }
            };
            runnable.runTaskTimer(this,0,5);
            return;
        }
        if (currentEvent == ChaosEvent.EXTREME_EARTHQUAKE)
        {
            BukkitRunnable runnable = new BukkitRunnable() {
                int countdown = Seconds/2;
                @Override
                public void run() {
                    for (Player p : GetAllPlayers())
                    {
                        Location pl = p.getLocation();
                        World w = p.getWorld();
                        w.getBlockAt(new Location(p.getWorld(),pl.getX()+.5,pl.getY()-1,pl.getZ()+.5)).breakNaturally();
                        w.getBlockAt(new Location(p.getWorld(),pl.getX()+.5,pl.getY()-1,pl.getZ()-.5)).breakNaturally();
                        w.getBlockAt(new Location(p.getWorld(),pl.getX()-.5,pl.getY()-1,pl.getZ()+.5)).breakNaturally();
                        w.getBlockAt(new Location(p.getWorld(),pl.getX()-.5,pl.getY()-1,pl.getZ()-.5)).breakNaturally();
                    }
                    countdown--;
                    if (countdown <= 0)
                    {
                        this.cancel();
                    }
                }
            };
            runnable.runTaskTimer(this,0,2);
            return;
        }
        if (currentEvent == ChaosEvent.ZUES)
        {
            BukkitRunnable runnable = new BukkitRunnable() {
                int countdown = Seconds/4;
                @Override
                public void run() {
                    for (Player p : GetAllPlayers())
                    {
                        Location pl = p.getLocation();
                        World w = p.getWorld();
                        w.strikeLightning(new Location(pl.getWorld(),(random.nextInt(30)-15)+pl.getX(),pl.getY(),(random.nextInt(30)-15)+pl.getZ()));
                    }
                    countdown--;
                    if (countdown <= 0)
                    {
                        this.cancel();
                    }
                }
            };
            runnable.runTaskTimer(this,0,4);
            return;
        }
        if (currentEvent == ChaosEvent.AMBUSH)
        {
            for (Player p : GetAllPlayers())
            {
                Location pl = p.getLocation();
                pl.add(0,5,0);
                p.getWorld().spawnEntity(pl,EntityType.ZOMBIE);
                p.getWorld().spawnEntity(pl,EntityType.ZOMBIE);
                p.getWorld().spawnEntity(pl,EntityType.ZOMBIE);
                p.getWorld().spawnEntity(pl,EntityType.ZOMBIE);
                p.getWorld().spawnEntity(pl,EntityType.SKELETON);
                p.getWorld().spawnEntity(pl,EntityType.SKELETON);

            }
            return;
        }
        if (currentEvent == ChaosEvent.BLAZE_ATTACK)
        {
            for (Player p : GetAllPlayers())
            {
                Location pl = p.getLocation();
                pl.add(0,5,0);
                p.getWorld().spawnEntity(pl,EntityType.BLAZE);
                p.getWorld().spawnEntity(pl,EntityType.BLAZE);
                p.getWorld().spawnEntity(pl,EntityType.BLAZE);
                p.getWorld().spawnEntity(pl,EntityType.BLAZE);


            }
            return;
        }
        if (currentEvent == ChaosEvent.DOGS)
        {
            for (Player p : GetAllPlayers())
            {
                Location pl = p.getLocation();
                pl.add(0,3,0);
                p.getWorld().spawnEntity(pl,EntityType.WOLF);
                p.getWorld().spawnEntity(pl,EntityType.WOLF);
                p.getWorld().spawnEntity(pl,EntityType.WOLF);
                p.getInventory().addItem(new ItemStack(Material.BONE,32));

            }
            return;
        }
        if (currentEvent == ChaosEvent.GIGA_GIANT)
        {
            for (Player p : GetAllPlayers())
            {
                CustomMobs.SpawnGiant(p.getLocation());

            }                return;
        }
        if (currentEvent == ChaosEvent.JUMPSCARE)
        {
            for (Player p : GetAllPlayers())
            {
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,Seconds/4,255,true,false));
                p.spawnParticle(Particle.SONIC_BOOM,new Location(p.getWorld(),0,1,0).add(p.getLocation()),1);
                p.playSound(p.getLocation(),Sound.ENTITY_ENDER_DRAGON_DEATH,1,1);
            }
            return;
        }
        if (currentEvent == ChaosEvent.WITHERED)
        {
            Player p = GetAllPlayers().get(0);
            LivingEntity e = (Wither) p.getWorld().spawnEntity(p.getWorld().getSpawnLocation(),EntityType.WITHER);
            BukkitRunnable killWither = new BukkitRunnable() {
                @Override
                public void run() {
                    e.setHealth(0);

                }
            };
            killWither.runTaskLater(this,Seconds*2);
        }
        if (currentEvent == ChaosEvent.MANY_MOBS)
        {
            for (Player p : GetAllPlayers())
            {
                Location pl = p.getLocation();
                pl.add(0,5,0);
                p.getWorld().spawnEntity(pl,EntityType.CREEPER);
                p.getWorld().spawnEntity(pl,EntityType.ZOMBIE);
                p.getWorld().spawnEntity(pl,EntityType.SKELETON);

                p.getWorld().spawnEntity(pl,EntityType.SPIDER);
                p.getWorld().spawnEntity(pl,EntityType.HUSK);   p.getWorld().spawnEntity(pl,EntityType.ZOMBIE_VILLAGER);
            }
            return;
        }
        if (currentEvent == ChaosEvent.PHANTOM_SWARM)
        {
            for (Player p : GetAllPlayers())
            {
                Location pl = p.getLocation();
                pl.add(0,5,0);
                p.getWorld().spawnEntity(pl,EntityType.PHANTOM);
                p.getWorld().spawnEntity(pl,EntityType.PHANTOM);
                p.getWorld().spawnEntity(pl,EntityType.PHANTOM);
                p.getWorld().spawnEntity(pl,EntityType.PHANTOM);

           }
            return;
        }
        if (currentEvent == ChaosEvent.TO_THE_NETHER)
        {
            for (Player p : GetAllPlayers())
            {
                Location pl = p.getLocation();
                pl.add(0,5,0);
                p.getWorld().getBlockAt(pl).setType(Material.LAVA);
                pl.add(0,-1,0);
                p.getWorld().getBlockAt(pl).setType(Material.LAVA);
                pl.add(0,-1,0);
                p.getWorld().getBlockAt(pl).setType(Material.LAVA);
                pl.add(0,-1,0);
                p.getWorld().getBlockAt(pl).setType(Material.LAVA);
            }
            return;
        }

        if (currentEvent == ChaosEvent.LAVA_FILL)
        {
            for (Player p : GetAllPlayers()) {

                Location pl = p.getLocation();
                pl.add(0,-1,0);
                p.getWorld().getBlockAt(pl).setType(Material.LAVA);
            }
            return;

        }

        if (currentEvent == ChaosEvent.MOB_SPAWNER_PLACE)
        {
            for (Player p : GetAllPlayers()) {

                Location pl = p.getLocation();
                pl.add(0,2,0);
                Block b = pl.getBlock();
                b.setType(Material.SPAWNER);
            }
            return;
        }

        if (currentEvent == ChaosEvent.BECOME_TREE)
        {
            for (Player p : GetAllPlayers()) {

                Location pl = p.getLocation();
                p.getWorld().getBlockAt(new Location(p.getWorld(),pl.getX(),pl.getY()-1,pl.getZ())).setType(Material.GRASS_BLOCK);
                p.getWorld().generateTree(pl,TreeType.TREE);
            }
            return;
        }

        if (currentEvent == ChaosEvent.OBSIDIAN_PRISON)
        {
            for (Player p : GetAllPlayers()) {

                Location pl = p.getLocation();
                pl.setX(Math.floor(pl.getX()));

                pl.setY(Math.floor(pl.getY()));

                pl.setZ(Math.floor(pl.getZ()));
                World w = p.getWorld();
                w.getBlockAt(new Location(p.getWorld(),pl.getX()+1,pl.getY(),pl.getZ()+0)).setType(Material.OBSIDIAN);
                w.getBlockAt(new Location(p.getWorld(),pl.getX()-1,pl.getY(),pl.getZ()+0)).setType(Material.OBSIDIAN);
                w.getBlockAt(new Location(p.getWorld(),pl.getX()+0,pl.getY(),pl.getZ()+1)).setType(Material.OBSIDIAN);
                w.getBlockAt(new Location(p.getWorld(),pl.getX()+0,pl.getY(),pl.getZ()-1)).setType(Material.OBSIDIAN);

                w.getBlockAt(new Location(p.getWorld(),pl.getX()+1,pl.getY()+1,pl.getZ()+0)).setType(Material.OBSIDIAN);
                w.getBlockAt(new Location(p.getWorld(),pl.getX()-1,pl.getY()+1,pl.getZ()+0)).setType(Material.OBSIDIAN);
                w.getBlockAt(new Location(p.getWorld(),pl.getX()+0,pl.getY()+1,pl.getZ()+1)).setType(Material.OBSIDIAN);
                w.getBlockAt(new Location(p.getWorld(),pl.getX()+0,pl.getY()+1,pl.getZ()-1)).setType(Material.OBSIDIAN);

                w.getBlockAt(new Location(p.getWorld(),pl.getX()+0,pl.getY()-1,pl.getZ()+0)).setType(Material.OBSIDIAN);
            }

            return;
        }

        if (currentEvent == ChaosEvent.RANDOM_TELEPORT)
        {
            for (Player p : GetAllPlayers()) {

                Location pl = p.getLocation();
                p.teleport(new Location(p.getWorld(),pl.getX()+(random.nextInt(30)-15),pl.getY()+50,pl.getZ()+(random.nextInt(30)-15)));

            }

            return;
        }
        if (currentEvent == ChaosEvent.TO_THE_SKY_TEMP)
        {
            for (Player p : GetAllPlayers()) {

                Location pl = p.getLocation();
                p.teleport(new Location(p.getWorld(),pl.getX()+(random.nextInt(30)-15),pl.getY()+1000,pl.getZ()+(random.nextInt(30)-15)));
                BukkitRunnable runnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.teleport(p.getWorld().getSpawnLocation());
                    }
                };
                runnable.runTaskLater(this,100);
            }

            return;
        }
        if (currentEvent == ChaosEvent.GETROBBED)
        {
            for (Player p: GetAllPlayers())
            {
                if (currentEvent == ChaosEvent.GETROBBED) {
                    p.getInventory().remove(p.getItemInUse());
                }

            }
            return;
        }
    }

    @EventHandler
    void onPlayerTakeDamage(EntityDamageEvent event)
    {
        if (event.getEntityType() != EntityType.PLAYER) {return;}

        if (currentEvent == ChaosEvent.MORTAL_MODE)
        {
            event.setDamage(999999);
            return;
        }

        if (currentEvent == ChaosEvent.GOD_MODE)
        {
            event.setDamage(0);
            return;
        }

        if (currentEvent == ChaosEvent.EXPLOSIVE_FEET)
        {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                Player p = (Player)event.getEntity();

                p.getWorld().createExplosion(p.getLocation(),5,true,true);
                event.setDamage(0);
            }
            return;
        }
    }

    @EventHandler
    void onPlayerMove(PlayerMoveEvent event)
    {
        if (currentEvent == ChaosEvent.CROWCH_FIRE)
        {
            Player player = event.getPlayer();
            if(player.isSneaking()) {
                player.getWorld().getBlockAt(player.getLocation()).setType(Material.FIRE);
            }
            return;
        }
        if (currentEvent == ChaosEvent.TNT_RAIN)
        {
            if (random.nextInt(100) < 20)
            {
                Location offset = new Location(event.getPlayer().getWorld(),random.nextInt(10)-5,10,random.nextInt(10)-5);
                event.getPlayer().getWorld().spawnEntity(offset.add(event.getPlayer().getLocation()),EntityType.PRIMED_TNT);
            }
            return;
        }
        if (currentEvent == ChaosEvent.RAIN_FIRE)
        {
            if (random.nextInt(100) < 50)
            {
                Location offset = new Location(event.getPlayer().getWorld(),random.nextInt(10)-5,10,random.nextInt(10)-5);
                Entity e = event.getPlayer().getWorld().spawnEntity(offset.add(event.getPlayer().getLocation()),EntityType.FIREBALL);

                e.setVelocity(new Vector(0,-1,0));

            }
            return;
        }
        if (currentEvent == ChaosEvent.STATIC)
        {
            event.setCancelled(true);
            return;
        }
        if (currentEvent == ChaosEvent.SPLIT_THE_SEAS)
        {
            Material m = event.getPlayer().getLocation().getBlock().getType();
            if (m == Material.WATER)
            {
                event.getPlayer().setHealth(0);
            }
            return;
        }
        if (currentEvent == ChaosEvent.EXPLOSIVE_JUMP)
        {
            if (event.getTo().getY() > event.getFrom().getY())
            {
                event.getPlayer().getWorld().createExplosion(event.getPlayer().getLocation(),5,true,true);
            }
        }
    }
    @EventHandler
    void onBreakBlock(BlockBreakEvent event)
    {
        if (currentEvent == ChaosEvent.SUPER_LOOTS)
        {
            if (random.nextInt(100) <= 30)
            {
                int rnd = random.nextInt(100);
                if (rnd <= 30)
                {
                    event.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_INGOT));
                }
                else if (rnd <= 60)
                {
                    event.getPlayer().getInventory().addItem(new ItemStack(Material.EMERALD));
                }
                else
                {
                    event.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND));
                }
            }
            return;
        }
        if (currentEvent == ChaosEvent.DONT_BREAK_BLOCKS)
        {
            event.getPlayer().setHealth(0);
            return;
        }
    }

    @EventHandler
    void onPlayerAttack(EntityDamageByEntityEvent event)
    {
        if (currentEvent != ChaosEvent.DONT_ATTACK) {return;}
        if (event.getDamager().getType() == EntityType.PLAYER)
        {
            ((Player)event.getDamager()).setHealth(0);
        }
    }

    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        if (currentEvent == ChaosEvent.CROWCH_FIRE)
        {
            Player player = event.getPlayer();
            if(player.isSneaking()) {
                player.getWorld().getBlockAt(player.getLocation()).setType(Material.FIRE);
            }
        }

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
    DONT_BREAK_BLOCKS,
    DONT_ATTACK,
    TO_THE_NETHER,
    LAVA_FILL,
    MOB_SPAWNER_PLACE,
    BECOME_TREE,
    RAIN_FIRE,
    BLAZE_ATTACK,
    OBSIDIAN_PRISON,
    RANDOM_TELEPORT,
    TO_THE_SKY_TEMP,
    PHANTOM_SWARM,
    EXPLOSIVE_JUMP,
    CROWCH_FIRE,
    ZUES,
    EXTREME_EARTHQUAKE,
    DOGS,
    GIGA_GIANT,
    JUMPSCARE,
    WITHERED,
    GETROBBED,
    INVERSE_QUAKE,
    THE_WALLS,
    ALL_TOGETHER_NOW




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
    String f(String message)
    {
        return ChatColor.translateAlternateColorCodes('&',message);
    }
    public ChaosEventDisplaySetting[] settings = {
            new ChaosEventDisplaySetting(f("&c")+"TNT Rain.",BarColor.RED),
            new ChaosEventDisplaySetting(f("&c")+"Explosive Feet.",BarColor.RED),
            new ChaosEventDisplaySetting("To the moon!",BarColor.WHITE),
            new ChaosEventDisplaySetting(f("&a")+"We're surrounded...",BarColor.GREEN),
            new ChaosEventDisplaySetting(f("&d")+"Sinkhole!",BarColor.RED),
            new ChaosEventDisplaySetting(f("&d")+"I can't see.",BarColor.PURPLE),
            new ChaosEventDisplaySetting(f("&b")+"ICED",BarColor.BLUE),
            new ChaosEventDisplaySetting(f("&b")+"Gotta go fast!",BarColor.BLUE),
            new ChaosEventDisplaySetting(f("&b")+"So like, don't drown...",BarColor.BLUE),
            new ChaosEventDisplaySetting("Gotta fly now",BarColor.WHITE),
            new ChaosEventDisplaySetting(f("&a")+"ITS AN AMBUSH",BarColor.GREEN),
            new ChaosEventDisplaySetting(f("&e")+"Here comes the money.",BarColor.YELLOW),
            new ChaosEventDisplaySetting(f("&e")+"Break it baby",BarColor.YELLOW),
            new ChaosEventDisplaySetting(f("&d")+"I can't be seen?",BarColor.PURPLE),
            new ChaosEventDisplaySetting(f("&e")+"I AM A GOD BEYOND ALL",BarColor.YELLOW),
            new ChaosEventDisplaySetting(f("&c")+"Mortality sucks",BarColor.RED),
            new ChaosEventDisplaySetting(f("&c")+"Kicking from server...",BarColor.RED),
            new ChaosEventDisplaySetting(f("&c")+"Why don't you try and break a block...",BarColor.RED),
            new ChaosEventDisplaySetting(f("&c")+"Why don't you try and attack...",BarColor.RED),
            new ChaosEventDisplaySetting(f("&c")+"Its raining lava",BarColor.RED),
            new ChaosEventDisplaySetting(f("&c")+"Haht feet",BarColor.RED),
            new ChaosEventDisplaySetting(f("&d")+"Theres a mob spawner, it doesn't do much...",BarColor.PURPLE),
            new ChaosEventDisplaySetting(f("&a")+"I AM GROOT",BarColor.YELLOW),
            new ChaosEventDisplaySetting(f("&c")+"ITS RAINING FIRE.",BarColor.RED),
            new ChaosEventDisplaySetting(f("&c")+"Blaze attack!",BarColor.RED),
            new ChaosEventDisplaySetting(f("&d")+"PRISON",BarColor.RED),
            new ChaosEventDisplaySetting(f("&d")+"Goodbye.",BarColor.PURPLE),
            new ChaosEventDisplaySetting(f("&b")+"Like a birdie!",BarColor.BLUE),
            new ChaosEventDisplaySetting(f("&d")+"SLEEP NOW",BarColor.PINK),
            new ChaosEventDisplaySetting(f("&c")+"Just don't JUMP",BarColor.RED),
            new ChaosEventDisplaySetting(f("&c")+"Shifty Fire!",BarColor.RED),
            new ChaosEventDisplaySetting(f("&b")+"THO HATH ANGERED THE GODS",BarColor.YELLOW),
            new ChaosEventDisplaySetting("Magnitude 10.",BarColor.RED),
            new ChaosEventDisplaySetting(f("&e")+"Doggi <3",BarColor.YELLOW),
            new ChaosEventDisplaySetting(f("&a")+"GIANT OF DEATH",BarColor.GREEN),
            new ChaosEventDisplaySetting("Boo!",BarColor.WHITE),
            new ChaosEventDisplaySetting(f("&0")+"Nothing lasts forever...",BarColor.WHITE),
            new ChaosEventDisplaySetting(f("&d")+"You can't hold that!",BarColor.PINK),

            new ChaosEventDisplaySetting(f("&a")+"Earthquake but its opposite day.",BarColor.BLUE),
            new ChaosEventDisplaySetting(f("&e")+"Trapped XD",BarColor.BLUE),
            new ChaosEventDisplaySetting(f("&a")+"All together now!",BarColor.BLUE),
    };
}
