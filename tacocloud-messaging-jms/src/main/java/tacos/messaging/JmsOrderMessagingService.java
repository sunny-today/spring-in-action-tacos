package tacos.messaging;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import tacos.Order;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

  private JmsTemplate jms;

  @Autowired
  public JmsOrderMessagingService(JmsTemplate jms) {
    this.jms = jms;
  }

  @Override
  public void sendOrder(Order order) {
    jms.convertAndSend("tacocloud.order.queue", order,
        this::addOrderSource);	// 사용 addOrderSource()
    	// 다른 convertAndSend() 호출에서 동일한 MessagePostProcessor를 사용할 수 있다.
  }
  
  private Message addOrderSource(Message message) throws JMSException {
    message.setStringProperty("X_ORDER_SOURCE", "WEB");
    return message;
  }

}
