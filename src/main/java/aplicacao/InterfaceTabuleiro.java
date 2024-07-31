package aplicacao;
import aplicacao.pecas.*;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;

import java.net.URL;

public class InterfaceTabuleiro extends GridPane {
    public static final int tamCasa = 80;
    private StackPane[][] casas = new StackPane[8][8];
    Label movimentoLabel = new Label("");
    Label fimLabel = new Label("");

    public InterfaceTabuleiro() {
        setHgap(0);  // Sem espaçamento entre as células
        setVgap(0);  // Sem espaçamento entre as células

        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                Rectangle quadrado = new Rectangle(tamCasa, tamCasa);
                quadrado.setFill((linha + coluna) % 2 == 0 ? Color.SANDYBROWN : Color.SADDLEBROWN);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(quadrado);
                stackPane.setId("casa_" + linha + "_" + coluna); // Identificador

                // Handler para quando uma casa é clicada.
                stackPane.setOnMouseClicked(event -> {
                    int linhaClicada = Integer.parseInt(stackPane.getId().split("_")[1]);
                    int colunaClicada = Integer.parseInt(stackPane.getId().split("_")[2]);
                    ControleJogada.registraClique(new Pos(linhaClicada, colunaClicada));
                });

                add(stackPane, coluna, linha);
                casas[linha][coluna] = stackPane;
            }
        }
        atualizaTabuleiro();
    }

    public void atualizaTabuleiro() {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                Peca peca = Jogo.getJogo(new Pos(linha, coluna));
                atualizaCelula(linha, coluna, peca);
            }
        }

    }

    private void atualizaCelula(int linha, int coluna, Peca peca) {
        StackPane stackPane = casas[linha][coluna];
        stackPane.getChildren().clear();

        Rectangle quadrado = new Rectangle(tamCasa, tamCasa);
        quadrado.setFill((linha + coluna) % 2 == 0 ? Color.SANDYBROWN : Color.SADDLEBROWN);
        stackPane.getChildren().add(quadrado);

        if (peca != null) {
            String caminho = getCaminhoPeca(peca);
            if (!caminho.isEmpty()) {
                URL urlImagem = getClass().getResource(caminho);
                if (urlImagem != null) {
                    String url = urlImagem.toExternalForm();
                    Image image = new Image(url);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(tamCasa);
                    imageView.setFitHeight(tamCasa);
                    imageView.setPreserveRatio(true);
                    imageView.setSmooth(true);

                    stackPane.getChildren().add(imageView);
                } else {
                    System.err.println("Imagem não encontrada: " + caminho);
                }
            }
        }
    }

    public static String getFem(Peca[][] tabuleiro){
        String fem = "";
        int somaFem=0;
        int contBarra=0;

        for(int i=0;i<tabuleiro.length;i++){
            for(int j=0;j<tabuleiro[i].length;j++){
                if(tabuleiro[i][j] == null) {
                    somaFem++;
                }
                else if(somaFem!=8){
                    if(somaFem !=0){
                        fem = fem.concat(somaFem + "");
                    }
                    somaFem=0;
                    if(tabuleiro[i][j].getCor() == Cor.BRANCO){
                        fem = fem.concat( Character.toUpperCase(tabuleiro[i][j].getSimbolo())+ "");
                    }
                    else{
                        fem =  fem.concat( tabuleiro[i][j].getSimbolo()+ "");
                    }
                }
            }
            if(somaFem!=0){
                fem = fem.concat(somaFem +"");
                if( contBarra!=7){
                    fem = fem.concat("/");
                }
                contBarra++;
                somaFem=0;
            }
            else if(i!=0 && contBarra!=7){
                fem = fem.concat("/");
                contBarra++;
            }
        }
        return fem;
    }

    private String getCaminhoPeca(Peca peca) {
        String caminho = "";
        if (peca != null) {
            caminho = peca.getCaminhoImagem();
            if (!caminho.isEmpty()) {
                if (peca.getCor() == Cor.BRANCO)
                    caminho += "Branco.png";
                else
                    caminho += "Preto.png";
            }
        }
        return caminho;
    }
}