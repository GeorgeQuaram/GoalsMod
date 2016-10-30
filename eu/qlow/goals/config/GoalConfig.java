package eu.qlow.goals.config;

import eu.qlow.goals.GoalsMod;

import java.util.HashMap;
import java.util.Map;

/**
 * Class created by qlow | Jan
 */
public class GoalConfig {

    private HashMap<String, GoalElement> goals = new HashMap<String, GoalElement>();

    public GoalConfig() {
        for ( String gameMode : GoalsMod.getGameModes() ) {
            if ( goals.containsKey( gameMode ) ) {
                continue;
            }

            goals.put( gameMode, new GoalElement( gameMode ) );
        }
    }

    /**
     * HashMap with all saved goals-elements
     * A GoalElement contains the goals for every game-mode that is supported
     */
    public Map<String, GoalElement> getGoals() {
        return goals;
    }
}
