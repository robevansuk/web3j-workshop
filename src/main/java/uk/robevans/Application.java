package uk.robevans;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created on 11/22/16.
 */
public class Application {

  public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
  }
}
