import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;


public class KarateTests {


    private static ConfigurableApplicationContext context = null;

    @BeforeAll
    public static void setUp() {
        context = SpringApplication.run(SpringBootApp.class, new String[]{});
    }

    @AfterAll
    public static void tearDown() {
        if(context!=null)
            context.stop();
    }


    @Test
    void testContextLoad() {
    }


    @Karate.Test
    Karate testAll() throws IOException {
        ResourceLoader rl = new FileSystemResourceLoader();
        Resource resource = rl.getResource("classpath:some-reuseable.feature");
        String nofailFeature = resource.getFile().getAbsolutePath();
        resource = rl.getResource("classpath:simple-scenario.feature");
        String simpleScenarioFeature = resource.getFile().getAbsolutePath();
        return Karate.run(nofailFeature,simpleScenarioFeature);
    }
    
}