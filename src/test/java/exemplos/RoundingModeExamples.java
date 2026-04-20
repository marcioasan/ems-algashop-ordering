package exemplos;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundingModeExamples {
    public static void main(String[] args) {
        BigDecimal valor = new BigDecimal("2.555");

        // UP: Arredonda sempre para longe do zero
        System.out.println(valor.setScale(2, RoundingMode.UP)); // 2.56

        // DOWN: Arredonda sempre para zero
        System.out.println(valor.setScale(2, RoundingMode.DOWN)); // 2.55

        // CEILING: Arredonda para cima (infinito positivo)
        System.out.println(valor.setScale(2, RoundingMode.CEILING)); // 2.56

        // FLOOR: Arredonda para baixo (infinito negativo)
        System.out.println(valor.setScale(2, RoundingMode.FLOOR)); // 2.55

        // HALF_UP: Arredonda para o mais próximo, se for meio, vai para cima
        System.out.println(valor.setScale(2, RoundingMode.HALF_UP)); // 2.56

        // HALF_DOWN: Arredonda para o mais próximo, se for meio, vai para baixo
        System.out.println(valor.setScale(2, RoundingMode.HALF_DOWN)); // 2.55

        // HALF_EVEN: Arredonda para o mais próximo, se for meio, vai para o par mais próximo
        System.out.println(valor.setScale(2, RoundingMode.HALF_EVEN)); // 2.56

        // UNNECESSARY: Lança exceção se precisar arredondar
        try {
            System.out.println(valor.setScale(2, RoundingMode.UNNECESSARY));
        } catch (ArithmeticException e) {
            System.out.println("Exceção: arredondamento seria necessário");
        }
    }
}