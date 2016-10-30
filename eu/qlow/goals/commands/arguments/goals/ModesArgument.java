package eu.qlow.goals.commands.arguments.goals;

import eu.qlow.goals.GoalsMod;
import eu.qlow.goals.utils.command.ArgumentCommand;

/**
 * Class created by qlow | Jan
 */
public class ModesArgument extends ArgumentCommand {

    public ModesArgument() {
        super( "modes" );
    }

    @Override
    public void execute( String[] args ) {
        sendMessage( "\u00A77All supported \u00A76game-modes\u00A77:" );

        String gameModeString = "";

        // Iterating through all game m
        for ( String gameMode : GoalsMod.getGameModes() ) {
            // Checking for aliases
            if ( GoalsMod.getGameModeAliases().containsKey( gameMode ) ) {
                gameMode += "/" + GoalsMod.getGameModeAliases().get( gameMode );
            }

            gameModeString += (gameModeString.equals( "" ) ? "\u00A7a" : "\u00A77, \u00A7a") + gameMode;
        }

        sendMessage( gameModeString );
    }
}
