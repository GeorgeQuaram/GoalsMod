package eu.qlow.goals.commands;

import eu.qlow.goals.GoalsMod;
import eu.qlow.goals.commands.arguments.goals.ClearArgument;
import eu.qlow.goals.commands.arguments.goals.ListArgument;
import eu.qlow.goals.commands.arguments.goals.ModesArgument;
import eu.qlow.goals.commands.arguments.goals.SetArgument;
import eu.qlow.goals.utils.command.Command;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class created by qlow | Jan
 */
public class GoalsCommand extends Command {

    // The help-entries
    private final static Map<String, String> helpEntries = new LinkedHashMap<String, String>() {{
        put( "/goals", "Shows this menu" );
        put( "/goals list", "Shows you all goals" );
        put( "/goals modes", "Shows you all game-modes that are supported" );
        put( "/goals set <game-mode> <30d/alltime> <kd/kills/wins> <goal>", "Sets a goal" );
        put( "/goals clear <game-mode> [30d/alltime]", "Clears the goals of this game-mode" );
    }};

    public GoalsCommand() {
        super( "goals", new ModesArgument(), new ClearArgument(), new SetArgument(), new ListArgument() );
    }

    @Override
    public void execute( String[] args ) {
        sendHelpMessages();
    }

    /**
     * Sends the help menu to the player
     */
    private void sendHelpMessages() {
        sendMessage( "\u00A76GoalsMod \u00A77v" + GoalsMod.getVersion() + " by qlow" );

        for ( Map.Entry<String, String> helpEntry : helpEntries.entrySet() ) {
            sendMessage( "\u00A76" + helpEntry.getKey() + " \u00A78- \u00A77" + helpEntry.getValue() );
        }
    }

}
