module YuGiOhG15 {
    requires javafx.controls;
    requires javafx.fxml;
    requires gson;
    requires json;
    requires java.sql;
    requires javafx.media;
    requires opencsv;
    requires cloning;
    requires gson.extras;
    opens sample to javafx.fxml;
    exports sample;
    opens sample.view to javafx.fxml;
    exports sample.view;

}