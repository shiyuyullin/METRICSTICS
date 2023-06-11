module calculator.ui {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;
    requires Metricstics;

    opens calculator.ui to javafx.fxml;
    exports calculator.ui;
}