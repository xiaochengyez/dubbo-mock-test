import com.gongxc.inter.GreetingsService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestDubbo {
    static GreetingsService service;
    @BeforeAll
    static void beforeAll(){
        String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        reference.setInterface(GreetingsService.class);
        service = reference.get();
    }
    @Test
    void testSayHi(){
        System.out.println(service.sayHello());
    }
}
