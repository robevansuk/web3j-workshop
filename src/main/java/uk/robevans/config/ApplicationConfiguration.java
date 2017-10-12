package uk.robevans.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import uk.robevans.Application;

/**
 * Created on 11/22/16.
 */
@Configuration
@ComponentScan
class ApplicationConfiguration {

  @Bean
  public Application getApplication(){
    return new Application();
  }
}