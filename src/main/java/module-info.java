module com.battleship {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    
    opens com.battleship to javafx.fxml;
    exports com.battleship;
}
