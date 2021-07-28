package tacos.messaging;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
  // 메시지 변환기를 변경할 때는 MessageConverter타입의 빈을 구성하면 된다.
  // JSON 기반 메시지 변환의 경우는 다음과 같다. 
  @Bean
  public Jackson2JsonMessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

}
