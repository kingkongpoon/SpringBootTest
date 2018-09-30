package sptest.controller

import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.boot.context.embedded.{ConfigurableEmbeddedServletContainer, EmbeddedServletContainerCustomizer}
import org.springframework.stereotype.Controller

@SpringBootApplication
class AppConfig extends EmbeddedServletContainerCustomizer{
  @Override
  def customize(configurableEmbeddedServletContainer: ConfigurableEmbeddedServletContainer): Unit = {
    configurableEmbeddedServletContainer.setPort(4848)
  }
}