package eu.qlow.goals.config;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

/**
 * Class created by qlow | Jan
 */
public class Goal {

    private boolean enabled;
    private boolean checkedOnce;

    private float lastKd;
    private int lastKills = -1;
    private int lastWins = -1;
    private int lastCalculatedKillsToKd = -1;

    private float kdGoal = -1F;
    private int killsGoal = -1;
    private int winsGoal = -1;

    /**
     * @return true if the goals is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled-state of the goals
     *
     * @param enabled new enabled-state
     */
    public void setEnabled( boolean enabled ) {
        this.enabled = enabled;
    }

    /**
     * Called when a goal is finished
     */
    public void onFinishGoal( final String goalName, final String mode, final boolean monthly ) {
        // Sending info message with delay
        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep( 300L );
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }

                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
                        new ChatComponentText( "\u00A7a\u00A7lDu hast das Ziel erreicht: \u00A7r\u00A76\u00A7l"
                                + goalName + " in " + mode + " (" + (monthly ? "30d" : "alltime") + ")" ) );
            }
        } ).start();

        // Disabling goal if everything is done
        if ( kdGoal == -1F && killsGoal == -1 && winsGoal == -1 ) {
            enabled = false;
        }
    }

    // I don't think, this is very clear, so I don't document it:

    public boolean isCheckedOnce() {
        return checkedOnce;
    }

    public void setCheckedOnce( boolean checkedOnce ) {
        this.checkedOnce = checkedOnce;
    }

    public float getKdGoal() {
        return kdGoal;
    }

    public void setKdGoal( float kdGoal ) {
        this.kdGoal = kdGoal;
    }

    public int getKillsGoal() {
        return killsGoal;
    }

    public void setKillsGoal( int killsGoal ) {
        this.killsGoal = killsGoal;
    }

    public int getWinsGoal() {
        return winsGoal;
    }

    public void setWinsGoal( int winsGoal ) {
        this.winsGoal = winsGoal;
    }

    public float getLastKd() {
        return lastKd;
    }

    public void setLastKd( float lastKd ) {
        this.lastKd = lastKd;
    }

    public int getLastKills() {
        return lastKills;
    }

    public void setLastKills( int lastKills, String gamemode, boolean monthly ) {
        this.lastKills = lastKills;

        if ( killsGoal != -1 && lastKills >= killsGoal ) {
            int killsGoal = this.killsGoal;
            this.killsGoal = -1;
            onFinishGoal( killsGoal + " Kills", gamemode, monthly );
        }
    }

    public int getLastWins() {
        return lastWins;
    }

    public void setLastWins( int lastWins, String gamemode, boolean monthly ) {
        this.lastWins = lastWins;

        if ( winsGoal != -1 && lastWins >= winsGoal ) {
            int winsGoal = this.winsGoal;
            this.winsGoal = -1;
            onFinishGoal( winsGoal + " Wins", gamemode, monthly );
        }
    }

    public int getLastCalculatedKillsToKd() {
        return lastCalculatedKillsToKd;
    }

    public void setLastCalculatedKillsToKd( int lastCalculatedKillsToKd ) {
        this.lastCalculatedKillsToKd = lastCalculatedKillsToKd;
    }

}
