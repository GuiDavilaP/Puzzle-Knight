module application {
    requires javafx.controls;
    requires javafx.fxml;


    opens aplicacao to javafx.fxml;
    exports aplicacao;
    exports aplicacao.pecas;
    opens aplicacao.pecas to javafx.fxml;
}