package aplicacao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JogoTest {

    // teste básico que testa se a string FEN não possui algum typo ou está fora do escopo normal
    public boolean ehValidoFEN(String fen) {

        if (fen == null || fen.isEmpty()) {
            return false;
        }

        if (!fen.matches("[rnbqkpRNBQKP10-8/ wbKQkq-]+")) {
            return false;
        }

        if (fen.length() < 15 || fen.length() > 100) {
            return false;
        }

        return true;
    }

    @Test
    public void testeFENCerto() {
        JogoTest teste = new JogoTest();
        String validoFEN = "4k1r1/4pp2/3p1n2/1p1q3p/4r1pP/2PQ2B1/5PP1/R4RK1";
        assertTrue(teste.ehValidoFEN(validoFEN));

    }

    @Test
    public void testeFENErrado() {
        JogoTest teste = new JogoTest();
        String invalidoFEN = "4k1r1/4pp2/3p1n2/1p1q3p/4r1pP/2PQ2B1/5PP1/R4RK1;"; // caractere ';' invalida
        assertFalse(teste.ehValidoFEN(invalidoFEN));
    }

}