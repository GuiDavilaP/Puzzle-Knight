package aplicacao;

import aplicacao.pecas.Peca;
import aplicacao.pecas.Pos;
import aplicacao.pecas.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static aplicacao.EstadoMovimento.ERRADO;
import static aplicacao.pecas.Cor.*;
import static java.nio.file.Files.readAllLines;

public class Jogo {
    private static final int NUMPROB = 20;
    //Problema problema;
    private static Peca[][] tabuleiroJogo = new Peca[8][8];
    //private static ArrayList<Jogada> jogadas = new ArrayList<>();
    private static ArrayList<String> movimentos = new ArrayList<>();
    private static ArrayList<Integer> puzzlesDisp = new ArrayList<>();
    private static int turnoAtual = 0;

    public static void carregaNovoProblema(){
        clearJogo();
        movimentos.clear();
        ControleJogada.resetEstadoMov();
        ControleJogada.resetQuantMov();
        turnoAtual = 0;

        Path problema = escolheProblema();

        List<String> linhas;
        try {
            linhas = readAllLines(problema);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Salva posições do problema.
        leituraJogadas(linhas);
        // Atualiza posição.
        atualizaJogo(linhas.getFirst());

        Main.atualizaTabuleiro();
        Main.atualizaMenu(EstadoMovimento.SEMMOV);
    }

    private static Path escolheProblema(){
        int randomNum;

        if(puzzlesDisp.isEmpty()){
            for(int i = 1; i <= NUMPROB; i++){
                puzzlesDisp.add(i);
            }
        }
        randomNum = ThreadLocalRandom.current().nextInt(0, puzzlesDisp.size());
        int posicao = puzzlesDisp.get(randomNum);
        puzzlesDisp.remove(randomNum);

        return Paths.get("src/main/java/problemas/m" + String.valueOf(posicao)+".txt");
    }

    public static void atualizaJogo(String linhaFem){
        int linhaTab = 0;
        int colunaTab = 0;

        for(int posCharLido = 0; posCharLido<linhaFem.length(); posCharLido++){
            char caractere = linhaFem.charAt(posCharLido);
            // Digitos indicam quantia de casas vazias.
            if(Character.isDigit(caractere)){
                int casasVazias = Character.getNumericValue(caractere);
                for(int i = 0; i < casasVazias; i++, colunaTab++){
                    tabuleiroJogo[linhaTab][colunaTab] = null;
                }
            }
            else{
                Cor cor;
                if(Character.isUpperCase(caractere))
                    cor = BRANCO;
                else
                    cor = PRETO;

                boolean fimLinha = false;
                Peca novaPeca = null;
                caractere = Character.toLowerCase(caractere);
                // Define qual peca esta sendo lida.
                switch(caractere) {
                    //TODO: se possivel, tentar descobrir uma forma de diminuir essa repeticao de codigo
                    case 'k':
                        novaPeca = new Rei(cor, new Pos(linhaTab, colunaTab), caractere);
                        break;
                    case 'r':
                        novaPeca = new Torre(cor, new Pos(linhaTab, colunaTab), caractere);
                        break;
                    case 'n':
                        novaPeca = new Cavalo(cor, new Pos(linhaTab, colunaTab), caractere);
                        break;
                    case 'b':
                        novaPeca = new Bispo(cor, new Pos(linhaTab, colunaTab), caractere);
                        break;
                    case 'q':
                        novaPeca = new Rainha(cor, new Pos(linhaTab, colunaTab), caractere);
                        break;
                    case 'p':
                        novaPeca = new Peao(cor, new Pos(linhaTab, colunaTab), caractere);
                        break;
                    // Indica que a linha acabou.
                    case '/':
                        linhaTab++;
                        colunaTab = 0;
                        fimLinha = true;
                        break;
                    default:
                        System.out.println("Leitura do tabuleiro nao implementada");
                        break;
                }
                // Insere nova peca no tabuleiro.
                if(!fimLinha){
                    tabuleiroJogo[linhaTab][colunaTab] = novaPeca;
                    colunaTab++;
                }
            }
        }
    }

    public static String getFen(Peca[][] tabuleiro) {
        StringBuilder fen = new StringBuilder();
        int emptyCount = 0;

        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                if (tabuleiro[i][j] == null) {
                    emptyCount++;
                } else {
                    if (emptyCount > 0) {
                        fen.append(emptyCount);
                        emptyCount = 0;
                    }
                    char symbol = tabuleiro[i][j].getSimbolo();
                    if (tabuleiro[i][j].getCor() == Cor.BRANCO) {
                        symbol = Character.toUpperCase(symbol);
                    }
                    fen.append(symbol);
                }
            }
            if (emptyCount > 0) {
                fen.append(emptyCount);
                emptyCount = 0;
            }
            if (i < tabuleiro.length - 1) {
                fen.append('/');
            }
        }
        return fen.toString();
    }

    private static void leituraJogadas(List<String> linhas){
        int i = 0;
        while(linhas.get(i) != null && !linhas.get(i).isEmpty()){
            Jogo.movimentos.add(linhas.get(i));
            System.out.println(Jogo.movimentos.get(i));
            i++;
        }
    }

    public static void voltaPosicao() {
        EstadoMovimento estadoMov = ControleJogada.getEstadoMov();
        int quantMov = ControleJogada.getQuantMov();

        if(estadoMov == ERRADO && quantMov > 0){
            Jogo.subTurno();
            Jogo.resetaTabuleiro();
            ControleJogada.voltaJogada();
        }
    }

    private static void clearJogo() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Jogo.tabuleiroJogo[i][j] = null;
            }
        }
    }

    public static Peca[][] getJogo() {
        return Jogo.tabuleiroJogo;
    }

    public static Peca getJogo(Pos posicao) {
        return Jogo.tabuleiroJogo[posicao.getLinha()][posicao.getColuna()];
    }

    public static void apagaPosJogo(Pos posicaoDestino) {
        Jogo.tabuleiroJogo[posicaoDestino.getLinha()][posicaoDestino.getColuna()] = null;
    }

    public static void inserePecaJogo(Peca peca) {
        int linha = peca.getPosicao().getLinha();
        int coluna = peca.getPosicao().getColuna();

        if (linha >= 0 && linha <= 7 && coluna >= 0 && coluna <= 7)
            tabuleiroJogo[linha][coluna] = peca;
        else
            System.out.println(" Inserção de peça inválida.");
    }

    public static int getTurnoAtual() {
        return turnoAtual;
    }
    public static void addTurno() {
        Jogo.turnoAtual += 1;
    }
    public static void subTurno() {
        Jogo.turnoAtual -= 1;
    }

    public static void resetaTabuleiro(){
        atualizaJogo(movimentos.get(turnoAtual));
    }

    public static Peca[][] getTabuleiroJogo() {
        return tabuleiroJogo;
    }

    public static ArrayList<String> getMovimentos() {
        return movimentos;
    }

    public static boolean terminou(){
        // Subtrai 1 pois problemas não incluem o movimento de resposta das pretas final.
        return (turnoAtual == movimentos.size() - 1);
    }
}
