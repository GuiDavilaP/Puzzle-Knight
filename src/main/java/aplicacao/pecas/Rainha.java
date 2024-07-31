package aplicacao.pecas;

public class Rainha extends Peca{
    private static final String caminhoImagem = "/aplicacao/pngPecas/rainha";

    public Rainha(Cor cor, Pos posicao, char simbolo) {
        super(cor, simbolo, posicao);
    }

    @Override
    public boolean testaMovimento (Pos posicaoAtual, Pos destino, Peca[][] tabuleiro) {
        if (estaNosLimites(destino)) {
            if(!(destino.getLinha() == posicaoAtual.getLinha() && destino.getColuna() == posicaoAtual.getColuna())) { //nao eh a mesma casa

                // vertical e horizontal
                if(destino.getLinha() == posicaoAtual.getLinha() || destino.getColuna() == posicaoAtual.getColuna()) {
                    if(casaValida(destino, tabuleiro) && !pecaNaFrenteLinhaReta(destino, tabuleiro)) {
                        return true;
                    }
                }

                // diagonal
                if(Math.abs(destino.getLinha() - posicaoAtual.getLinha()) == Math.abs(destino.getLinha() - posicaoAtual.getLinha())) {
                    if(casaValida(destino, tabuleiro) && caminhoLivreDiagonal(destino, tabuleiro)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override public String getCaminhoImagem(){
        return caminhoImagem;
    }
}
