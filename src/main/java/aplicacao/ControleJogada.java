package aplicacao;

import aplicacao.pecas.*;
import static aplicacao.EstadoMovimento.*;

public class ControleJogada {
    private static Peca pecaSelecionada = null;
    private static boolean prontoMov = false;
    private static EstadoMovimento estadoMov = EstadoMovimento.SEMMOV;
    private static int quantMov = 0;

    private static void movePeca(Pos posicaoDestino) {
        boolean movValido = pecaSelecionada.testaMovimento(pecaSelecionada.getPosicao(), posicaoDestino, Jogo.getJogo());

        boolean continua = (estadoMov == SEMMOV) || (estadoMov == CORRETO);
        if (movValido && continua) {
            quantMov += 1;
            Jogo.addTurno();

            // Apaga posição original.
            Jogo.apagaPosJogo(pecaSelecionada.getPosicao());

            // Atualiza posição de destino.
            pecaSelecionada.setPosicao(posicaoDestino);
            Jogo.inserePecaJogo(pecaSelecionada);
            // Reset da selecao de peca.
            prontoMov = false;
            pecaSelecionada = null;

            Main.desenhaTabuleiro(Jogo.getJogo());
            Main.atualizaTabuleiro();

            boolean jogadaCorreta = testaJogada();
            if (jogadaCorreta) {
                if(Jogo.terminou()){
                    estadoMov = FINALIZADO;
                }
                else{
                    estadoMov = CORRETO;
                    jogadaBot();
                }
            } else
                estadoMov = ERRADO;

            Main.atualizaMenu(estadoMov);
        } else
            System.out.println("!!!!! Movimento invalido: " + posicaoDestino);
    }

    public static void jogadaBot() {
        Jogo.addTurno();
        int turno = Jogo.getTurnoAtual();
        String tabuleiroNovo = Jogo.getMovimentos().get(turno);

        Jogo.atualizaJogo(tabuleiroNovo);
        Main.atualizaTabuleiro();
    }

    public static void registraClique(Pos posicao) {
        Peca pecaClicada = null;
        boolean casaOcupada = Jogo.getJogo(posicao) != null;
        if (casaOcupada)
            pecaClicada = Jogo.getJogo(posicao);

        // Segundo clique, se já tiver feito um primeiro clique válido.
        if (ControleJogada.prontoMov) {
            boolean posDiferentes = !posicao.equals(pecaSelecionada.getPosicao());
            if (posDiferentes) {
                if (casaOcupada) {
                    // Captura.
                    if (pecaClicada.getCor() == Cor.PRETO) {
                        movePeca(posicao);
                    }
                    // Troca a peca selecionada
                    else
                        pecaSelecionada = pecaClicada;
                }
                // Movimento para casa vazia.
                if (!casaOcupada)
                    movePeca(posicao);
            }
        }
        // Primeiro Clique.
        else {
            // Primeiro clique deve ser de uma peça branca.
            if (casaOcupada && pecaClicada.getCor() == Cor.BRANCO) {
                ControleJogada.pecaSelecionada = pecaClicada;
                prontoMov = true;
            }
        }
    }

    public static boolean testaJogada() {
        int turnoAtual = Jogo.getTurnoAtual();
        String fenCorreto = Jogo.getMovimentos().get(turnoAtual);
        String fenAtual = Jogo.getFen(Jogo.getTabuleiroJogo());
        System.out.println("FenEsperado: " + fenCorreto);
        System.out.println("FenJogado: " + fenAtual);
        return (fenAtual.equals(fenCorreto));
    }

    public static void voltaJogada(){
        quantMov -= 1;
        estadoMov = SEMMOV;
        prontoMov = false;

        Main.desenhaTabuleiro(Jogo.getJogo());
        Main.atualizaTabuleiro();
        Main.atualizaMenu(estadoMov);
    }

    public static EstadoMovimento getEstadoMov(){
        return estadoMov;
    }

    public static void resetEstadoMov() {
        estadoMov = SEMMOV;
    }

    public static int getQuantMov(){
        return quantMov;
    }

    public static void resetQuantMov() {
        quantMov = 0;
    }
}