package eu.qlow.goals.listeners;

import de.labystudio.modapi.EventHandler;
import de.labystudio.modapi.Listener;
import de.labystudio.modapi.ModAPI;
import de.labystudio.modapi.events.ChatReceivedEvent;
import eu.qlow.goals.GoalsMod;
import eu.qlow.goals.config.Goal;
import eu.qlow.goals.config.GoalElement;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;

import java.lang.reflect.Field;

/**
 * Class created by qlow | Jan
 */
public class ChatListener implements Listener {

    private Field headerField;
    private String currentGameMode;

    private boolean readingStats;
    private boolean monthlyStats;

    public ChatListener() {

        // Initializing header field
        try {
            this.headerField = Class.forName( "awh" ).getDeclaredField( "i" );
            headerField.setAccessible( true );
        } catch ( Exception ex ) {
            try {
                this.headerField = Class.forName( "net.minecraft.client.gui.GuiPlayerTabOverlay" ).getDeclaredField( "header" );
                headerField.setAccessible( true );
            } catch ( Exception e ) {
                e.printStackTrace();
                System.out.println( "if this happen, you're using a modified minecraft-version or not the 1.8.8 lol" );
            }
        }

        // Registering listener
        ModAPI.registerListener( this );

        // Starting header check thread
        new Thread( new Runnable() {
            @Override
            public void run() {
                while ( true ) {
                    // Calling check every second
                    try {
                        Thread.sleep( 1000L );
                    } catch ( InterruptedException e ) {
                        e.printStackTrace();
                    }

                    // Checking if the player is null
                    if ( Minecraft.getMinecraft().thePlayer == null ) {
                        continue;
                    }

                    // Getting header
                    String header;

                    try {
                        IChatComponent headerChatComponent = ( IChatComponent ) ChatListener.this.headerField.get( Minecraft.getMinecraft().ingameGUI.getTabList() );

                        if ( headerChatComponent == null )
                            continue;

                        header = headerChatComponent.getUnformattedText();
                    } catch ( IllegalAccessException e ) {
                        e.printStackTrace();
                        System.out.println( "Couldn't get tab header!" );
                        return;
                    }

                    // Checking for english language
                    GoalsMod.getInstance().setEnglish( header.contains( "You want to report a player?" ) );

                    // Checking for current gamemode
                    boolean foundGamemode = false;

                    for ( String gamemode : GoalsMod.getInstance().getGameModes() ) {
                        if ( !header.startsWith( "GommeHD.net " + gamemode ) )
                            continue;

                        foundGamemode = true;
                        currentGameMode = gamemode;
                        break;
                    }

                    if ( !foundGamemode ) {
                        currentGameMode = null;
                    }
                }
            }
        } ).start();
    }

    @EventHandler
    public void onChatReceived( ChatReceivedEvent event ) {
        if ( currentGameMode == null )
            return;

        String playerName = Minecraft.getMinecraft().thePlayer.getName();
        String cleanMessage = event.getCleanMsg();

        if ( readingStats ) {
            // Checking for end of stats
            if ( cleanMessage.equals( "---------------------" ) ) {
                readingStats = false;

                // Saving config
                GoalsMod.getInstance().getGoalConfigManager().save();
                return;
            }

            // There is always a space before the stats-line
            cleanMessage = cleanMessage.substring( 1, cleanMessage.length() ).replace( ",", "" );

            GoalElement goalElement = GoalsMod.getInstance().getGoalConfig().getGoals().get( currentGameMode );
            Goal goal = monthlyStats ? goalElement.getMonthlyGoal() : goalElement.getAlltimeGoal();

            goal.setCheckedOnce( true );
            String[] splitted = cleanMessage.split( ": " );

            if ( splitted.length == 1 )
                return;

            String key = splitted[0];
            String value = splitted[1];

            // Setting last kd
            if ( key.equals( "K/D" ) ) {
                goal.setLastKd( Float.parseFloat( value ) );
            }

            // Setting last kills
            if ( key.equals( "Kills" ) ) {
                int kills = Integer.parseInt( value );

                goal.setLastKills( kills, currentGameMode, monthlyStats );
            }

            // Setting last wins
            if ( (key.equals( "Games won" ) || key.equals( "Gewonnene Spiele" )) ) {
                int wins = Integer.parseInt( value );

                goal.setLastWins( wins, currentGameMode, monthlyStats );
            }

            // Setting last calculated kills to kd
            if ( (key.equals( "Tode" ) || key.equals( "Deaths" )) && goal.getKdGoal() != -1F ) {
                float deaths = Integer.parseInt( value );
                deaths = deaths == 0 ? 1F : deaths;
                float requiredKillsFloat = deaths * goal.getKdGoal();

                int requiredKills = requiredKillsFloat > (( int ) requiredKillsFloat) ? (( int ) requiredKillsFloat) + 1 : (( int ) requiredKillsFloat);

                if ( goal.getLastKills() >= requiredKills ) {
                    float kdGoal = goal.getKdGoal();
                    goal.setKdGoal( -1F );
                    goal.setLastCalculatedKillsToKd( 0 );
                    goal.onFinishGoal( kdGoal + " K/D", currentGameMode, monthlyStats );
                    return;
                }

                goal.setLastCalculatedKillsToKd( requiredKills - goal.getLastKills() );
            }

            return;
        }

        if ( cleanMessage.startsWith( "-=" ) ) {
            // I know, this is dirty ._.

            // Checking for english stats message
            if ( GoalsMod.getInstance().isEnglish() && (cleanMessage.contains( playerName + " Stats" ) || cleanMessage.contains( "Statistics of " + playerName )) ) {
                readingStats = true;
                monthlyStats = cleanMessage.contains( "30 days" );
                return;
            }

            // Checking for german stats message
            if ( !GoalsMod.getInstance().isEnglish() && (cleanMessage.contains( "Stats von " + playerName ) || cleanMessage.contains( "Statistiken von " + playerName )) ) {
                readingStats = true;
                monthlyStats = cleanMessage.contains( "30 Tage" );
                return;
            }
        }
    }

}
