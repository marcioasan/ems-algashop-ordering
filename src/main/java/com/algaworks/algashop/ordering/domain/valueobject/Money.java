package com.algaworks.algashop.ordering.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

//6.12. Desafio: Implemente o Value Object de Money e Quantity

public record Money(BigDecimal value) implements Comparable<Money> {

    private static final RoundingMode roundingMode = RoundingMode.HALF_EVEN;

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(String value) {
        this(new BigDecimal(value));
    }

    /**
     Esse construtor do record Money recebe um BigDecimal, garante que ele não seja nulo (senão lança NullPointerException),
     ajusta o valor para ter sempre duas casas decimais (setScale(2, roundingMode)),
     e lança IllegalArgumentException se o valor for negativo.
     Assim, ele garante que o valor de Money seja sempre positivo (ou zero) e com duas casas decimais,
     como é comum para valores monetários
     * */
    public Money(BigDecimal value) {
        Objects.requireNonNull(value); //todo mensagem
        this.value = value.setScale(2, roundingMode);
        if (this.value.signum() == -1) {
            throw new IllegalArgumentException();//todo mensagem
        }
    }

    public Money multiply(Quantity quantity) {
        Objects.requireNonNull(quantity);
        if (quantity.value() < 1) {
            throw new IllegalArgumentException();
        }
        BigDecimal multiplied = this.value.multiply(new BigDecimal(quantity.value()));
        return new Money(multiplied);
    }

    public Money add(Money money) {
        Objects.requireNonNull(money);
        return new Money(this.value.add(money.value));
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compareTo(Money o) {
        return this.value.compareTo(o.value);
    }

    public Money divide(Money o) {
        return new Money(this.value.divide(o.value, roundingMode));
    }
}

    /*
    A classe RoundingMode, em Java, define as estratégias de arredondamento para operações com números decimais (como BigDecimal).
    Ela é uma enumeração (enum) e cada valor representa uma regra diferente para decidir como arredondar quando o resultado
    não pode ser representado exatamente. Os principais modos são:
    UP: Arredonda para longe de zero (sempre para cima, independente do sinal).
    DOWN: Arredonda em direção a zero (descarta os decimais).
    CEILING: Arredonda para cima (em direção ao infinito positivo).
    FLOOR: Arredonda para baixo (em direção ao infinito negativo).
    HALF_UP: Arredonda para o mais próximo; se for exatamente no meio, arredonda para cima.
    HALF_DOWN: Arredonda para o mais próximo; se for exatamente no meio, arredonda para baixo.
    HALF_EVEN: Arredonda para o mais próximo; se for exatamente no meio, arredonda para o número par mais próximo (“arredondamento do banqueiro”).
    UNNECESSARY: Lança exceção se o arredondamento for necessário (ou seja, só aceita valores exatos).
    Esses modos são usados, por exemplo, ao definir quantas casas decimais um BigDecimal deve ter.
    * */