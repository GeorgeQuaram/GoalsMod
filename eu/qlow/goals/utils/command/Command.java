package eu.qlow.goals.utils.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an ingame-command
 * Class created by qlow | Jan
 */
public abstract class Command {

    // The command's name
    private String commandName;

    // Defined arguments
    private Set<ArgumentCommand> argumentCommands = new HashSet<ArgumentCommand>();

    /**
     * Constructs a new ingame-command
     *
     * @param commandName      the command's name (ingame: /<commandName>)
     * @param argumentCommands defined arguments
     */
    public Command( String commandName, ArgumentCommand... argumentCommands ) {
        this.commandName = commandName;
        this.argumentCommands.addAll( Arrays.asList( argumentCommands ) );
    }

    /**
     * @return the command's name
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Called by the method that checks for commands
     *
     * @param message the raw message
     */
    public void executeInternally( String message ) {
        // Splitting message by spaces
        String[] splittedMessage = message.split( " " );
        String[] args = new String[0];

        // Checking for any arguments
        if ( splittedMessage.length != 1 ) {
            args = new String[splittedMessage.length - 1];
            System.arraycopy( splittedMessage, 1, args, 0, splittedMessage.length - 1 );
        }

        if ( args.length != 0 ) {
            // Checking for defined arguments
            for ( ArgumentCommand argument : argumentCommands ) {
                if ( !args[0].equalsIgnoreCase( argument.getArgumentName() ) )
                    continue;

                argument.executeInternally( args );
                return;
            }
        }

        // Executing command
        execute( args );
    }

    /**
     * Sends a chat-message to the minecraft-player
     *
     * @param message message that should be sent
     */
    public void sendMessage( String message ) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage( new ChatComponentText( message ) );
    }

    /**
     * Called when this command is called without any defined arguments
     *
     * @param args the arguments that has been specified
     */
    public abstract void execute( String[] args );

}
