package aplicacao;

import aplicacao.pecas.Peca;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

import java.io.IOException;
import java.net.URL;

import static aplicacao.pecas.Cor.BRANCO;

public class Main extends Application {
    public static InterfaceTabuleiro tabuleiro;

    public static void main(String[] args) throws IOException {
        Jogo.carregaNovoProblema();
        //desenhaTabuleiro(Jogo.getJogo());
        launch(args);
    }

    @Override
    public void start(Stage estagio) {
        tabuleiro = new InterfaceTabuleiro();
        BorderPane tela = getTela();
        tela.setLeft(tabuleiro);


        // Cria e adiciona o menu à direita
        VBox menu = criaMenu();
        tela.setRight(menu);

        // Cria imagem de icone.
        URL recurso = getClass().getResource("/aplicacao/pngPecas/cavaloPreto.png");
        if (recurso != null) {
            String urlIcone = recurso.toExternalForm();
            Image pecaImage = new Image(urlIcone);
            estagio.getIcons().add(pecaImage);
        } else {
            System.err.println("Ícone não encontrado.");
        }

        // Define o titulo da janela.
        estagio.setTitle("Puzzle Knight");
        estagio.setResizable(false);

        // Instancia scena
        Scene scena = new Scene(tela);
        estagio.setScene(scena);
        estagio.show();
    }

    private static BorderPane getTela() {
        BorderPane tela = new BorderPane();

        // Cor de fundo.
        BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        tela.setBackground(background);

        // Espaçamento da tela.
        tela.setPadding(new Insets(20,20,20,20));

        return tela;
    }

    private VBox criaMenu() {
        VBox menu = new VBox(20); // Espaçamento de 10 pixels entre os itens do menu
        menu.setPadding(new Insets(40)); // Padding ao redor do menu
        menu.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        menu.setAlignment(Pos.TOP_CENTER);

        addLabels(menu);
        addBotoes(menu);
        return menu;
    }

    private static void addLabels(VBox menu){
        // Adiciona os elementos do menu
        Label tituloLabel = new Label("Puzzle Knight");
        tituloLabel.setTextFill(Color.WHITE);
        tituloLabel.setStyle("-fx-font-size: 34px;");

        Label jogadaLabel = new Label("Brancas Jogam");
        jogadaLabel.setTextFill(Color.WHITE);
        jogadaLabel.setStyle("-fx-font-size: 28px;");

        tituloLabel.setAlignment(Pos.CENTER);
        jogadaLabel.setAlignment(Pos.CENTER);

        // Label para o movimento
        tabuleiro.movimentoLabel.setTextFill(Color.GREEN);
        tabuleiro.movimentoLabel.setStyle("-fx-font-size: 28px;");
        tabuleiro.movimentoLabel.setAlignment(Pos.CENTER);

        menu.getChildren().addAll(tituloLabel, jogadaLabel, tabuleiro.movimentoLabel);
    }

    private static void addBotoes(VBox menu){
        // Adiciona botões "Voltar" e "Próximo"
        Button voltarButton = new Button("Voltar");
        Button proximoButton = new Button("Próximo");

        // Estiliza os botões
        String buttonStyle = "-fx-background-color: #333333; -fx-text-fill: white; " +
                "-fx-font-size: 18px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        voltarButton.setStyle(buttonStyle);
        proximoButton.setStyle(buttonStyle);

        // Adiciona ação ao botão "Voltar"
        voltarButton.setOnAction(event -> ControleJogada.voltaJogada());

        HBox buttonBox = new HBox(20); // Espaçamento de 20 pixels entre os botões
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(voltarButton, proximoButton);

        menu.getChildren().add(buttonBox);
    }

    public static void atualizaTabuleiro(){
        tabuleiro.atualizaTabuleiro();
        System.out.println("Turno:" + Jogo.getTurnoAtual());
    }

    public static void atualizaMenu(EstadoMovimento estadoMovimento){
        if (estadoMovimento == EstadoMovimento.CORRETO) {
            tabuleiro.movimentoLabel.setText("Correto");
            tabuleiro.movimentoLabel.setTextFill(Color.GREEN);
        } else if(estadoMovimento == EstadoMovimento.ERRADO){
            tabuleiro.movimentoLabel.setText("Incorreto");
            tabuleiro.movimentoLabel.setTextFill(Color.RED);
        }
        else{
            tabuleiro.movimentoLabel.setText("");
        }
    }

    public static void desenhaTabuleiro(Peca[][] tabuleiro) {
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(tabuleiro[i][j] != null){
                    if(tabuleiro[i][j].getCor() == BRANCO) {
                        System.out.print(" " + Character.toUpperCase(tabuleiro[i][j].getSimbolo()) + " ");
                    }
                    else{
                        System.out.print(" " + tabuleiro[i][j].getSimbolo() + " ");
                    }
                }
                else{
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
    }
}