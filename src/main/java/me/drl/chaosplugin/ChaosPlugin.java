package me.drl.chaosplugin;

import org.bukkit.plugin.java.JavaPlugin;

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
    WATERTOLAVA,
    SPLITTHESEAS,
    LAUNCHINTHEAIR,
    AMBUSH,
    SUPERLOOTS,
    INSTABREAK,
    INVIS,
    GODMODE,
    MORTALMODE,
    EMPTY

}


public final class ChaosPlugin extends JavaPlugin {

    int currentEventIndex;
    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    ChaosEvent IndexToEvent(int index)
    {
        return ChaosEvent.values()[index];
    }

    int EventToIndex(ChaosEvent event)
    {
        return event.ordinal();
    }
}
