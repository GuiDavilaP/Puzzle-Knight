package aplicacao.pecas;

public class Pos {
    private int linha;
    private int coluna;

    public Pos(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public static boolean posIguais(Pos pos, Pos pos2){
        return pos.getLinha() == pos2.getLinha() && pos.getColuna() == pos2.getColuna();
    }

    @Override
    public String toString() {
        return "Pos{" +
                "linha=" + linha +
                ", coluna=" + coluna +
                '}';
    }
}
