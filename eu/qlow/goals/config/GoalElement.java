package eu.qlow.goals.config;

/**
 * Class created by qlow | Jan
 */
public class GoalElement {

    private String name;

    private Goal monthlyGoal = new Goal();
    private Goal alltimeGoal = new Goal();

    public GoalElement( String name ) {
        this.name = name;
    }

    /**
     * @return the goals's gamemode (visible in the tab-header http://i.imgur.com/4ss5aUN.png)
     */
    public String getName() {
        return name;
    }

    // I don't think, this is very clear, so I don't document it:

    public Goal getMonthlyGoal() {
        return monthlyGoal;
    }

    public void setMonthlyGoal( Goal monthlyGoal ) {
        this.monthlyGoal = monthlyGoal;
    }

    public Goal getAlltimeGoal() {
        return alltimeGoal;
    }

    public void setAlltimeGoal( Goal alltimeGoal ) {
        this.alltimeGoal = alltimeGoal;
    }
}
