package aplicacao;

import aplicacao.pecas.Peca;
import aplicacao.pecas.Pos;
import org.junit.jupiter.api.Test;
import aplicacao.pecas.*;

import static org.junit.jupiter.api.Assertions.*;

class ControleJogadaTest {

    public boolean ehValidoJogada(String posicaoInicial, String posicaoDestino) {

        if (posicaoInicial.equals(posicaoDestino)) {

            return true;

        }
        return false;
    }

    @Test
    public void testeJogadaCerto() {
        ControleJogadaTest teste = new ControleJogadaTest();
        String inicial = "R3k1r1/4pp2/3p4/1p1n3p/4r1pP/2P3B1/5PP1/5RK1";
        String destino = "R3k1r1/4pp2/3p4/1p1n3p/4r1pP/2P3B1/5PP1/5RK1";
        assertTrue(teste.ehValidoJogada(inicial, destino));

    }

    @Test
    public void testeJogadaErrado() {
        ControleJogadaTest teste = new ControleJogadaTest();
        String inicial = "R3k1r1/4pp2/3p4/1p1n3p/4r1pP/2P3B1/5PP1/5RK1";
        String destino = "R5r1/3kpp2/3p4/1p1n3p/4r1pP/2P3B1/5PP1/5RK1";
        assertFalse(teste.ehValidoJogada(inicial, destino));

    }

    @Test
    public void verificaMovimentoValido() {

        // testes feitos com o tabuleiro do puzzle m3
        Jogo.carregaNovoProblema();
        Peca[][] tabuleiro = new Peca[8][8];

        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabuleiro[i][j] = Jogo.getJogo()[i][j];
            }
        }
        //adicionando um cavalo p/ completar os testes
        tabuleiro[6][2] = new Cavalo(Cor.BRANCO, new Pos(6,2), 'n');

        assertEquals(true, tabuleiro[0][0].testaMovimento(tabuleiro[0][0].getPosicao(), new Pos(2, 0), tabuleiro));
        assertEquals(true, tabuleiro[0][0].testaMovimento(tabuleiro[4][0].getPosicao(), new Pos(2, 0), tabuleiro));
        assertEquals(false, tabuleiro[0][0].testaMovimento(tabuleiro[0][0].getPosicao(), new Pos(2, 1), tabuleiro));
        assertEquals(false, tabuleiro[6][6].testaMovimento(tabuleiro[6][6].getPosicao(), new Pos(7, 7), tabuleiro));
        assertEquals(true, tabuleiro[6][6].testaMovimento(tabuleiro[6][6].getPosicao(), new Pos(5, 5), tabuleiro));
        assertEquals(true, tabuleiro[6][7].testaMovimento(tabuleiro[6][7].getPosicao(), new Pos(5, 7), tabuleiro));
        assertEquals(false, tabuleiro[6][7].testaMovimento(tabuleiro[6][7].getPosicao(), new Pos(5, 6), tabuleiro));
        assertEquals(false, tabuleiro[2][2].testaMovimento(tabuleiro[2][2].getPosicao(), new Pos(2, 4), tabuleiro));
        assertEquals(true, tabuleiro[2][2].testaMovimento(tabuleiro[2][2].getPosicao(), new Pos(1, 1), tabuleiro));
        assertEquals(false, tabuleiro[7][7].testaMovimento(tabuleiro[7][7].getPosicao(), new Pos(6, 6), tabuleiro));
        assertEquals(true, tabuleiro[7][7].testaMovimento(tabuleiro[7][7].getPosicao(), new Pos(7, 6), tabuleiro));
        assertEquals(false, tabuleiro[4][4].testaMovimento(tabuleiro[4][4].getPosicao(), new Pos(7, 4), tabuleiro));
        assertEquals(true, tabuleiro[4][4].testaMovimento(tabuleiro[4][4].getPosicao(), new Pos(3, 4), tabuleiro));
        assertEquals(true, tabuleiro[6][2].testaMovimento(tabuleiro[6][2].getPosicao(), new Pos(4, 1), tabuleiro));
        assertEquals(false, tabuleiro[6][2].testaMovimento(tabuleiro[6][2].getPosicao(), new Pos(4, 2), tabuleiro));

    }

}