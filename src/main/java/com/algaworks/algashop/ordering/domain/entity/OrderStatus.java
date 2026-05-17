package com.algaworks.algashop.ordering.domain.entity;

//6.14. Implementando Aggregate de Order
//6.19. Controlando alteração de status
import java.util.Arrays;
import java.util.List;

public enum OrderStatus {
    DRAFT,
    PLACED(DRAFT),
    PAID(PLACED),
    READY(PAID),
    CANCELED(PAID, READY, PLACED, DRAFT);

    OrderStatus(OrderStatus... previousStatuses) {
        this.previousStatuses = Arrays.asList(previousStatuses);
    }

    private final List<OrderStatus> previousStatuses;

    public boolean canChangeTo(OrderStatus newStatus) {
        OrderStatus currentStatus = this;
        return newStatus.previousStatuses.contains(currentStatus);
    }

    public boolean canNotChangeTo(OrderStatus newStatus) {
        return !canChangeTo(newStatus);
    }

    //implementação sugerida por um aluno.
    //https://app.algaworks.com/forum/topicos/91854/controlando-alteracao-de-status-outra-implementacao

    /*
  DRAFT {
    @Override
    public Set<OrderStatus> allowedTransitions() {
      return Set.of(PLACED, CANCELED);
    }
  },
  PLACED {
    @Override
    public Set<OrderStatus> allowedTransitions() {
      return Set.of(PAID, CANCELED);
    }
  },
  PAID {
    @Override
    public Set<OrderStatus> allowedTransitions() {
      return Set.of(READY, CANCELED);
    }
  },
  READY {
    @Override
    public Set<OrderStatus> allowedTransitions() {
      return Set.of(CANCELED);
    }
  },
  CANCELED {
    @Override
    public Set<OrderStatus> allowedTransitions() {
      return Set.of();
    }
  };

  public abstract Set<OrderStatus> allowedTransitions();
  public boolean canChangeTo(OrderStatus next) {
    return allowedTransitions().contains(next);
  }
  public boolean canNotChangeTo(OrderStatus next) {
    return !canChangeTo(next);
  }
     */
}
