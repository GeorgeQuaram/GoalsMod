package eu.qlow.goals.commands.arguments.goals;

import eu.qlow.goals.GoalsMod;
import eu.qlow.goals.config.Goal;
import eu.qlow.goals.config.GoalElement;
import eu.qlow.goals.utils.command.ArgumentCommand;

/**
 * Class created by qlow | Jan
 */
public class SetArgument extends ArgumentCommand {

    public SetArgument() {
        super( "set" );
    }

    @Override
    public void execute( String[] args ) {
        // Checking for right usage
        if ( args.length < 4
                || (args.length > 1 && !args[1].equalsIgnoreCase( "30d" ) && !args[1].equalsIgnoreCase( "alltime" ))
                || (args.length > 2 && !args[2].equalsIgnoreCase( "kd" ) && !args[2].equalsIgnoreCase( "kills" ) && !args[2].equalsIgnoreCase( "wins" )) ) {
            sendMessage( "\u00A7cUsage: /goals set <game-mode> <30d/alltime> <kd/kills/wins> <goal>" );
            return;
        }

        // Getting gamemode by argument
        String gameMode = getGameMode( args[0] );

        if ( gameMode == null ) {
            sendMessage( "\u00A7cDidn't found game-mode!" );
            return;
        }

        // Getting goal
        GoalElement goalElement = GoalsMod.getInstance().getGoalConfig().getGoals().get( gameMode );
        Goal goal = args[1].equalsIgnoreCase( "30d" ) ? goalElement.getMonthlyGoal() : goalElement.getAlltimeGoal();

        if ( args[2].equalsIgnoreCase( "kd" ) ) {
            float kd = 0F;

            try {
                kd = Float.parseFloat( args[3] );
            } catch ( Exception ex ) {
                sendMessage( "\u00A7c" + args[3] + " is not a valid kd!" );
                return;
            }

            if ( kd <= 0 )
                return;

            goal.setEnabled( true );
            goal.setKdGoal( kd );
        } else {
            int value = 0;

            try {
                value = Integer.parseInt( args[3] );
            } catch ( Exception ex ) {
                sendMessage( "\u00A7c" + args[3] + " is not a valid kd!" );
                return;
            }

            if ( value < 1 )
                return;

            goal.setEnabled( true );

            if ( args[2].equalsIgnoreCase( "kills" ) ) {
                goal.setKillsGoal( value );
            } else if ( args[2].equalsIgnoreCase( "wins" ) ) {
                goal.setWinsGoal( value );
            }
        }

        GoalsMod.getInstance().getGoalConfigManager().save();
        sendMessage( "\u00A7a\u00A7lGoal set!" );
    }
}
