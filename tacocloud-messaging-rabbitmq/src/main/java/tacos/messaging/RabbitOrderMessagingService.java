package tacos.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tacos.Order;

@Service
public class RabbitOrderMessagingService
       implements OrderMessagingService {
  
  private RabbitTemplate rabbit;
  
  @Autowired
  public RabbitOrderMessagingService(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }
  
  // MessageProperties 객체를 직접 사용할 수 없으므로 다음과 같이 MessagePostProcessor를 사용해야 한다.
  public void sendOrder(Order order) {
    rabbit.convertAndSend("tacocloud.order.queue", order,
        new MessagePostProcessor() {	
          @Override
          public Message postProcessMessage(Message message)
              throws AmqpException {
            MessageProperties props = message.getMessageProperties(); // Message 객체의 MessageProperties를 가져온 후 
            props.setHeader("X_ORDER_SOURCE", "WEB");				  // setHeader()를 호출하여 X_ORDER_SOURCE 헤더를 설정할 수 있다. 
            return message;
          } 
        });
  }
  
}
