package aplicacao.pecas;

public class Peca implements Cloneable{

    private Cor cor; // 0 = branco, 1 = preto
    private char simbolo; // 'p' = peão, 'r' = torre, 'k' = cavalo, 'b' = bispo, 'q' = rainha, 'k' = rei
    private Pos posicao;
    private static final int DIMENSAO = 8;

    public Peca(Cor cor, char simbolo, Pos posicao) {
        this.cor = cor;
        this.simbolo = simbolo;
        this.posicao = posicao;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public Pos getPosicao() {
        return posicao;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public Cor getCor(){
        return this.cor;
    }

    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }

    public void setPosicao(Pos posicao) {
        this.posicao = posicao;
    }

    public boolean testaMovimento (Pos posicaoAtual, Pos destino, Peca[][] tabuleiro) {
        return false;
    }

    public String getCaminhoImagem(){
        return "";
    }

    public boolean estaNosLimites(Pos destino) {
        if (destino.getLinha() >= 0 && destino.getLinha() < DIMENSAO) {
            if (destino.getColuna() >= 0 && destino.getColuna() < DIMENSAO) {
                return true;
            }
        }
        return false;
    }

    public Peca pecaAtingida(Pos destino, Peca[][] tabuleiro) {
        for(int i=0; i < DIMENSAO; i++) {
            for (int j=0; j < DIMENSAO; j++) {
                if(tabuleiro[i][j] != null){
                    if(tabuleiro[i][j].posicao.getLinha() == destino.getLinha() && tabuleiro[i][j].posicao.getColuna() == destino.getColuna() && tabuleiro[i][j] != this) {
                        return tabuleiro[i][j];
                    }
                }
            }
        }
        return null;
    }

    public boolean casaValida(Pos destino, Peca[][] tabuleiro) {
        Peca pecaAtingida = pecaAtingida(destino, tabuleiro);

        if(pecaAtingida == null) { // casa nao ocupada por nenhuma peca
            return true;
        }
        else if(this.cor != pecaAtingida.cor) { //peca eh de cor diferente, entao pode capturar
            return true;
        }
        return false;
    }

    public boolean pecaNaFrenteLinhaReta(Pos destino, Peca[][] tabuleiro) {
        // movendo p/ esquerda
        for(int c = posicao.getColuna()-1; c > destino.getColuna(); c--){
            for (int i = 0; i < DIMENSAO; i++) {
                for (int j = 0; j < DIMENSAO; j++) {
                    if(tabuleiro[i][j] != null) {
                        if(tabuleiro[i][j].posicao.getColuna() == c && tabuleiro[i][j].posicao.getLinha() == destino.getLinha()) {
                            return true;
                        }
                    }
                }
            }
        }

        // movendo p/ direita
        for(int c = posicao.getColuna() +1; c < destino.getColuna(); c++){
            for (int i = 0; i < DIMENSAO; i++) {
                for (int j = 0; j < DIMENSAO; j++) {
                    if(tabuleiro[i][j] != null) {
                        if(tabuleiro[i][j].posicao.getColuna() == c && tabuleiro[i][j].posicao.getLinha() == destino.getLinha()) {
                            return true;
                        }
                    }
                }
            }
        }

        // movendo p/ cima
        for(int r = posicao.getLinha()-1; r > destino.getLinha(); r--){
            for (int i = 0; i < DIMENSAO; i++) {
                for (int j = 0; j < DIMENSAO; j++) {
                    if(tabuleiro[i][j] != null) {
                        if(tabuleiro[i][j].posicao.getColuna() == destino.getColuna() && tabuleiro[i][j].posicao.getLinha() == r) {
                            return true;
                        }
                    }
                }
            }
        }

        // movendo p/ baixo
        for(int r = posicao.getLinha()+1; r < destino.getLinha(); r++){
            for (int i = 0; i < DIMENSAO; i++) {
                for (int j = 0; j < DIMENSAO; j++) {
                    if(tabuleiro[i][j] != null) {
                        if(tabuleiro[i][j].posicao.getColuna() == destino.getColuna() && tabuleiro[i][j].posicao.getLinha() == r) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    boolean caminhoLivreDiagonal(Pos destino, Peca[][] tabuleiro) {
        int dx = Math.abs(destino.getLinha() - posicao.getLinha());
        int dy = Math.abs(destino.getColuna() - posicao.getColuna());
        if (dx != dy) return false; // Movimento inválido

        int stepX = (destino.getLinha() > posicao.getLinha()) ? 1 : -1;
        int stepY = (destino.getColuna() > posicao.getColuna()) ? 1 : -1;
        for (int x = posicao.getLinha() + stepX, y = posicao.getColuna() + stepY; x != destino.getLinha(); x += stepX, y += stepY) {
            if (tabuleiro[x][y] != null) {
                return false; // Há uma peça no caminho
            }
        }
        return true; // Caminho está livre
    }

    @Override
    public String toString() {
        return "Peca{" +
                "cor=" + cor +
                ", simbolo=" + simbolo +
                ", posicao=" + posicao +
                '}';
    }

    @Override
    public Peca clone() {
        try {
            Peca clone = (Peca) super.clone();
            clone.posicao = new Pos(this.posicao.getLinha(), this.posicao.getColuna());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}