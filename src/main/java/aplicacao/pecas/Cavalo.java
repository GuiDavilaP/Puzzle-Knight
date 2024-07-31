package aplicacao.pecas;

public class Cavalo extends Peca{
    private static final String caminhoImagem = "/aplicacao/pngPecas/cavalo";

    public Cavalo(Cor cor, Pos posicao, char simbolo) {
        super(cor, simbolo, posicao);
    }

    @Override
    public boolean testaMovimento (Pos posicaoAtual, Pos destino, Peca[][] tabuleiro) {
        if(estaNosLimites(destino)) {
            // para o movimento valido, os resultado vao ser 2:1 ou 1:2
            if(Math.abs(destino.getLinha() - posicaoAtual.getLinha()) * Math.abs(destino.getColuna() - posicaoAtual.getColuna()) == 2) {
                if(casaValida(destino, tabuleiro)) {
                    return true;
                }
            }
        }
        System.out.println("Esta nos limites:" + estaNosLimites(destino) + "Multi: "+ Math.abs(destino.getLinha() - posicaoAtual.getLinha()) * Math.abs(destino.getColuna() - posicaoAtual.getColuna()));
        return false;
    }

    @Override public String getCaminhoImagem(){
        return caminhoImagem;
    }
}
