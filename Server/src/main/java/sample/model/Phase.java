package sample.model;

import java.io.Serializable;

public class Phase  implements Serializable {
    private PhaseLevel phaseLevel;
    public enum PhaseLevel{
        DRAW("draw phase"),
        STANDBY("standby phase"),
        MAIN1("main phase 1"),
        BATTLE("battle phase"),
        MAIN2("main phase 2"),
        END("end phase");

        PhaseLevel(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;
    }
}
