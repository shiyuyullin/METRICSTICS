module calculator.ui {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;

    opens frontend.calculator.ui to javafx.fxml;
    exports frontend.calculator.ui;
}