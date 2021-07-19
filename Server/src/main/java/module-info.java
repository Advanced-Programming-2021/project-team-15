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
    requires java.desktop;
    opens sample.model.cards to cloning;
    opens sample.model to cloning, javafx.base;
    exports sample.model;
    exports sample.model.cards;
//    opens javafx.scene.image to cloning;
    opens sample to javafx.fxml;
    exports sample;
    opens sample.view to javafx.fxml;
    exports sample.view;

}