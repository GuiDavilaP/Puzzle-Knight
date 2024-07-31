package aplicacao.pecas;

public class Bispo extends Peca{
    private static final String caminhoImagem = "/aplicacao/pngPecas/bispo";
    public Bispo(Cor cor, Pos posicao, char simbolo) {
        super(cor, simbolo, posicao);
    }

    @Override
    public boolean testaMovimento (Pos posicaoAtual, Pos destino, Peca[][] tabuleiro) {
        if (estaNosLimites(destino)) {
            if (!(destino.getLinha() == posicaoAtual.getLinha() && destino.getColuna() == posicaoAtual.getColuna())) {
                // para mover nas diagonais, as diferenças vao ser iguais
                if (Math.abs(destino.getLinha() - posicaoAtual.getLinha()) == Math.abs(destino.getColuna() - posicaoAtual.getColuna())) {
                    if (casaValida(destino, tabuleiro) && caminhoLivreDiagonal(destino,tabuleiro)) {
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
