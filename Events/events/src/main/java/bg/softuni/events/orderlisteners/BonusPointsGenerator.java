package bg.softuni.events.orderlisteners;

import bg.softuni.events.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BonusPointsGenerator {

  private Logger LOGGER = LoggerFactory.getLogger(BonusPointsGenerator.class);

  @EventListener(OrderCreatedEvent.class)
  public void onOrderCreated(OrderCreatedEvent orderCreatedEvent) {
    LOGGER.info("Order no {} has been create. I'm going to give bonus points to the "
        + "client.", orderCreatedEvent.getOrderId());

    // TODO - give bonus points to the client
  }


}
