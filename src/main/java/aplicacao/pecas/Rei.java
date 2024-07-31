package aplicacao.pecas;

public class Rei extends Peca{
    private static final String caminhoImagem = "/aplicacao/pngPecas/rei";

    public Rei(Cor cor, Pos posicao, char simbolo) {
        super(cor, simbolo, posicao);
    }

    @Override
    public boolean testaMovimento (Pos posicaoAtual, Pos destino, Peca[][] tabuleiro) {
        if(estaNosLimites(destino)) {
            if(Math.abs(destino.getLinha() - posicaoAtual.getLinha()) + Math.abs(destino.getColuna() - posicaoAtual.getColuna()) == 1 ||      // em linha reta
                    Math.abs(destino.getLinha() - posicaoAtual.getLinha()) * Math.abs(destino.getColuna() - posicaoAtual.getColuna()) == 1) { // diagonal
                if(casaValida(destino, tabuleiro)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override public String getCaminhoImagem(){
        return caminhoImagem;
    }
}
