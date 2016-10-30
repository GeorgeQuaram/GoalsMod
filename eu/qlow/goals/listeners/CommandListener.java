package eu.qlow.goals.listeners;

import com.google.common.reflect.ClassPath;
import de.labystudio.modapi.EventHandler;
import de.labystudio.modapi.Listener;
import de.labystudio.modapi.ModAPI;
import de.labystudio.modapi.events.SendChatMessageEvent;
import eu.qlow.goals.utils.command.Command;

import java.util.HashSet;
import java.util.Set;

/**
 * Class created by qlow | Jan
 */
public class CommandListener implements Listener {

    // List with all commands
    private static Set<Command> commandList = new HashSet<Command>();

    /**
     * Registering all commands
     */
    static {
        try {
            // Iterating through all commands in package eu.qlow.goals.commands
            for ( ClassPath.ClassInfo classInfo :
                    ClassPath.from( CommandListener.class.getClassLoader() ).getTopLevelClasses( "eu.qlow.goals.commands" ) ) {
                Command command = ( Command ) classInfo.load().newInstance();
                System.out.println( "Loaded command " + command.getCommandName() );
                commandList.add( command );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public CommandListener() {
        ModAPI.registerListener( this );
    }

    @EventHandler
    public void onSendChat( SendChatMessageEvent event ) {
        String message = event.getMessage();

        // Checking for command prefix
        if ( !message.startsWith( "/" ) )
            return;

        // Checking for commands
        for ( Command command : commandList ) {
            if ( !message.split( " " )[0].equalsIgnoreCase( "/" + command.getCommandName() ) )
                continue;

            // Executing command
            event.setCancelled( true );
            command.executeInternally( message );
            break;
        }
    }

}
