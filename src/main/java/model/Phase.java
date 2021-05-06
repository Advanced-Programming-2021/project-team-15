package model;

public class Phase {
    private PhaseLevel phaseLevel;
    public enum PhaseLevel{
        DRAW,
        STANDBY,
        MAIN1,
        BATTLE,
        MAIN2,
        END
    }
}
