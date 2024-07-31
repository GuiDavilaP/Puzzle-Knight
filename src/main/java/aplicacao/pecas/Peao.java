package aplicacao.pecas;

import static aplicacao.pecas.Cor.BRANCO;

public class Peao extends Peca {
    private static final String caminhoImagem = "/aplicacao/pngPecas/peao";
    public boolean moveu = false;
    public Peao(Cor cor, Pos posicao, char simbolo) {
        super(cor, simbolo, posicao);
    }

    @Override
    public boolean testaMovimento (Pos posicaoAtual, Pos destino, Peca[][] tabuleiro) {

        if(estaNosLimites(destino)) {
            if(!(destino.getLinha() == posicaoAtual.getLinha() && destino.getColuna() == posicaoAtual.getColuna())) { //nao eh a mesma casa

                // define o movimento de acordo com a cor
                int dirMovimento;

                if(this.getCor() == BRANCO)
                    dirMovimento = -1;
                else
                    dirMovimento = 1;

                System.out.println("mov " + dirMovimento);

                Peca pecaAtingida = pecaAtingida(destino, tabuleiro);

                // movimento de uma casa
                if(destino.getColuna() == posicaoAtual.getColuna() && destino.getLinha() == posicaoAtual.getLinha() + dirMovimento && pecaAtingida == null) {
                    moveu = true;
                    return true;
                }

                // duas casas
                if(destino.getColuna() == posicaoAtual.getColuna() && destino.getLinha() == posicaoAtual.getLinha() + dirMovimento*2) {
                    if(!moveu && !pecaNaFrenteLinhaReta(destino, tabuleiro) && pecaAtingida == null && posicaoAtual.getLinha() == 6) {
                        moveu = true;
                        return true;
                    }
                }

                // diagonal capturando uma peça
                if(Math.abs(destino.getColuna() - posicaoAtual.getColuna()) == 1 && destino.getLinha() == posicaoAtual.getLinha() + dirMovimento) {
                    if(pecaAtingida != null && pecaAtingida.getCor() != this.getCor()) {
                        moveu = true;
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
