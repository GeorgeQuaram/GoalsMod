package eu.qlow.goals;

import de.labystudio.modapi.Module;
import eu.qlow.goals.config.GoalConfig;
import eu.qlow.goals.listeners.ChatListener;
import eu.qlow.goals.listeners.CommandListener;
import eu.qlow.goals.utils.ConfigManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * The GoalsMod is a mod for GommeHDnet players
 * who want to reach stats-goals
 * <p>
 * Mod created by qlow with ♥
 */
public class GoalsMod extends Module {

    // The mod's instance
    private static GoalsMod instance;

    // Some constants
    private final static String VERSION = "1.0";
    private final static String[] GAME_MODES = new String[]{
            "SkyWars",
            "SurvivalGames",
            "Quick SurvivalGames",
            "BedWars",
            "EnderGames",
            "Cores",
            "MineWar"
    };

    private final static Map<String, String> GAME_MODE_ALIASES = new HashMap<String, String>() {{
        put( "Quick SurvivalGames", "QSG" );
        put( "SurvivalGames", "SG" );
        put( "BedWars", "BW" );
        put( "SkyWars", "SW" );
        put( "EnderGames", "EG" );
    }};

    // Config-stuff
    private ConfigManager<GoalConfig> goalConfigManager;
    private GoalConfig goalConfig;

    // Other stuff
    private boolean isEnglish;

    @Override
    public void onEnable() {
        // Defining instance
        instance = this;

        // Loading config
        this.goalConfigManager = new ConfigManager<GoalConfig>( new File( "LabyMod/goals.json" ), GoalConfig.class );
        this.goalConfig = goalConfigManager.getSettings();
        goalConfigManager.save();

        // Registering listeners
        new CommandListener();
        new ChatListener();

        // Some info messages
        System.out.println( "--------------------------------------" );
        System.out.println();
        System.out.println( "GoalsMod v" + VERSION + " by qlow" );
        System.out.println( "http://youtube.com/qlow" );
        System.out.println( "http://twitter.com/_qlow" );
        System.out.println();
        System.out.println( "Configure it using /goals!" );
        System.out.println();
        System.out.println( "--------------------------------------" );
    }

    /**
     * @return the mod's config's config-manager
     */
    public ConfigManager<GoalConfig> getGoalConfigManager() {
        return goalConfigManager;
    }

    /**
     * @return the mod's config
     */
    public GoalConfig getGoalConfig() {
        return goalConfig;
    }

    /**
     * @return true if the set language on GommeHDnet is english
     */
    public boolean isEnglish() {
        return isEnglish;
    }

    /**
     * Sets the english-state
     *
     * @param english new english-state
     */
    public void setEnglish( boolean english ) {
        isEnglish = english;
    }

    /**
     * @return the mod's instance
     */
    public static GoalsMod getInstance() {
        return instance;
    }

    /**
     * @return the mod's version
     */
    public static String getVersion() {
        return VERSION;
    }

    /**
     * @return array with supported game-modes
     */
    public static String[] getGameModes() {
        return GAME_MODES;
    }

    /**
     * @return map with all gamemode aliases
     */
    public static Map<String, String> getGameModeAliases() {
        return GAME_MODE_ALIASES;
    }
}
