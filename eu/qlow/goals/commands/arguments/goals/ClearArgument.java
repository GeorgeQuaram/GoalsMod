package eu.qlow.goals.commands.arguments.goals;

import eu.qlow.goals.GoalsMod;
import eu.qlow.goals.config.Goal;
import eu.qlow.goals.config.GoalElement;
import eu.qlow.goals.utils.command.ArgumentCommand;

/**
 * Class created by qlow | Jan
 */
public class ClearArgument extends ArgumentCommand {

    public ClearArgument() {
        super( "clear" );
    }

    @Override
    public void execute( String[] args ) {
        // Checking for right usage
        if ( args.length < 1 || (args.length >= 2 && !args[1].equalsIgnoreCase( "30d" ) && !args[1].equalsIgnoreCase( "alltime" )) ) {
            sendMessage( "\u00A7cUsage: /goals clear <game-mode> [30d/alltime]" );
            return;
        }

        // Getting gamemode by argument
        String gameMode = getGameMode( args[0] );

        if ( gameMode == null ) {
            sendMessage( "\u00A7cDidn't found game-mode!" );
            return;
        }

        GoalElement goalElement = GoalsMod.getInstance().getGoalConfig().getGoals().get( gameMode );

        // Clearing goals
        if ( args.length == 1 ) {
            goalElement.setAlltimeGoal( new Goal() );
            goalElement.setMonthlyGoal( new Goal() );
        } else {
            if ( args[1].equalsIgnoreCase( "30d" ) ) {
                goalElement.setMonthlyGoal( new Goal() );
            } else {
                goalElement.setAlltimeGoal( new Goal() );
            }
        }

        GoalsMod.getInstance().getGoalConfigManager().save();
        sendMessage( "\u00A7a\u00A7lCleared goal of " + gameMode );
    }
}
