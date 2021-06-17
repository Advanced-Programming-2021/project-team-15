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
    opens view to javafx.fxml;
    opens model to javafx.base;
    exports view;
}