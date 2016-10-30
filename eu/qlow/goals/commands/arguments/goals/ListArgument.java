package eu.qlow.goals.commands.arguments.goals;

import eu.qlow.goals.GoalsMod;
import eu.qlow.goals.config.Goal;
import eu.qlow.goals.config.GoalElement;
import eu.qlow.goals.utils.command.ArgumentCommand;

/**
 * Class created by qlow | Jan
 */
public class ListArgument extends ArgumentCommand {

    public ListArgument() {
        super( "list" );
    }

    @Override
    public void execute( String[] args ) {
        boolean foundGoal = false;

        // Iterating through all goal elements
        for ( GoalElement goalElement : GoalsMod.getInstance().getGoalConfig().getGoals().values() ) {
            // Getting alltime & monthly goal
            Goal monthlyGoal = goalElement.getMonthlyGoal();
            Goal alltimeGoal = goalElement.getAlltimeGoal();

            // Checking whether one of them is enabled
            if ( monthlyGoal.isEnabled() || alltimeGoal.isEnabled() ) {
                foundGoal = true;
                sendMessage( "" );
                sendMessage( "\u00A77- \u00A79" + goalElement.getName() + "\u00A77 -" );
            }

            // Sending monthly goal if it is enabled
            if ( monthlyGoal.isEnabled() ) {
                sendMessage( "\u00A7e30d [ " + buildGoalString( monthlyGoal ) + " \u00A7e]" );
            }

            // Sending alltime goal if it is enabled
            if ( alltimeGoal.isEnabled() ) {
                sendMessage( "\u00A7eAlltime [ " + buildGoalString( alltimeGoal ) + " \u00A7e]" );
            }
        }

        if ( !foundGoal ) {
            sendMessage( "\u00A7cThere is no active goal!" );
        } else {
            sendMessage( "" );
        }
    }

    private String buildGoalString( Goal goal ) {
        String returnedString = "";

        if ( goal.getKdGoal() != -1F ) {
            returnedString += " \u00A78| \u00A76KD [" + goal.getKdGoal() + "]: \u00A7e" + getNumberColor( goal.isCheckedOnce() ? goal.getLastCalculatedKillsToKd() : -1 ) + " \u00A77kills left";
        }

        if ( goal.getKillsGoal() != -1 ) {
            returnedString += " \u00A78| \u00A76Kills: \u00A7e" + getNumberColor( goal.isCheckedOnce() ? goal.getLastKills() : -1, goal.getKillsGoal() ) + "\u00A77/" + goal.getKillsGoal();
        }

        if ( goal.getWinsGoal() != -1 ) {
            returnedString += " \u00A78| \u00A76Wins: \u00A7e" + getNumberColor( goal.isCheckedOnce() ? goal.getLastWins() : -1, goal.getWinsGoal() ) + "\u00A77/" + goal.getWinsGoal();
        }

        return returnedString.substring( 5, returnedString.length() );
    }

    private String getNumberColor( int number ) {
        return getNumberColor( number, number + 1 );
    }

    private String getNumberColor( int number, int maxNumber ) {
        return number == -1 ? "\u00A74?" : (number >= maxNumber ? "\u00A7a" + number : "\u00A7e" + number);
    }

}
