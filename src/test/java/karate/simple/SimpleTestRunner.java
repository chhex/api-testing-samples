package karate.simple;

import com.apgsga.apitestingsample.ApiTestingSampleApplication;
import com.apgsga.apitestingsample.SimpleApplicationKt;
import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

class SimpleTestRunner {

    private static ConfigurableApplicationContext context = null;

    @BeforeAll
    public static void setUp() {

        context = SpringApplication.run(ApiTestingSampleApplication.class, new String[]{});
    }

    @AfterAll
    public static void tearDown() {
       if(context!=null)
           context.stop();
    }


    @Karate.Test
    Karate testAll() {
        return Karate.run().relativeTo(getClass());
    }
}