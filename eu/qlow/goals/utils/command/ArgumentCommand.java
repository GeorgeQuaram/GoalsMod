package eu.qlow.goals.utils.command;

import eu.qlow.goals.GoalsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.util.Map;

/**
 * Represents a defined argument for {@link Command}
 * Class created by qlow | Jan
 */
public abstract class ArgumentCommand {

    // The argument's command
    private String argumentName;

    /**
     * Constructs a new defined argument
     *
     * @param argumentName the argument's name
     */
    public ArgumentCommand( String argumentName ) {
        this.argumentName = argumentName;
    }

    /**
     * @return the argument's name
     */
    public String getArgumentName() {
        return argumentName;
    }

    /**
     * Called by {@link Command} when this argument is called
     *
     * @param rawArgs all arguments of the command (including this argument)
     */
    public void executeInternally( String[] rawArgs ) {
        String[] args = new String[0];

        // Removing this argument from all arguments
        if ( rawArgs.length > 1 ) {
            args = new String[rawArgs.length - 1];
            System.arraycopy( rawArgs, 1, args, 0, rawArgs.length - 1 );
        }

        // Executing argument
        execute( args );
    }

    /**
     * Sends a chat-message to the minecraft-player
     * @param message message that should be sent
     */
    public void sendMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage( new ChatComponentText( message ) );
    }

    /**
     * Gets the gamemode by just one argument
     *
     * @param arg argument with gamemode
     * @return the game-mode's name
     */
    public String getGameMode( String arg ) {
        String gameMode = null;

        if ( GoalsMod.getGameModeAliases().containsValue( arg ) ) {
            for ( Map.Entry<String, String> aliasEntry : GoalsMod.getGameModeAliases().entrySet() ) {
                if ( !aliasEntry.getValue().equalsIgnoreCase( arg ) )
                    continue;

                gameMode = aliasEntry.getKey();
                break;
            }
        } else {
            for ( String supportedMode : GoalsMod.getGameModes() ) {
                if ( !supportedMode.replace( " ", "" ).equalsIgnoreCase( arg ) )
                    continue;

                gameMode = supportedMode;
                break;
            }
        }

        return gameMode;
    }


    /**
     * Called when the argument was executed
     * @param args arguments (not including this argument)
     */
    public abstract void execute( String[] args );

}
