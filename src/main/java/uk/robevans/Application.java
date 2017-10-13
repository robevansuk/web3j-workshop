package uk.robevans;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uk.robevans.config.ApplicationConfiguration;

import java.io.IOException;

/**
 * Created on 12/10/17.
 *
 */
public class Application {

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
    }
}
