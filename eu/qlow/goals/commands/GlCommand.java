package eu.qlow.goals.commands;

import eu.qlow.goals.commands.arguments.goals.ListArgument;
import eu.qlow.goals.utils.command.Command;

/**
 * Just an alias for /goals list
 * Class created by qlow | Jan
 */
public class GlCommand extends Command {

    public GlCommand() {
        super( "gl" );
    }

    @Override
    public void execute( String[] args ) {
        new ListArgument().execute( new String[0] );
    }
}
