import com.cloud.aopproxy.AopProxy;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jiuyun.zhang on 2016/12/19.
 */
public class TestMain {
    public static void main(String[] args) throws InterruptedException, MemcachedException, TimeoutException, IOException {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        Object p = context.getBean("proxy");

        AopProxy proxy = (AopProxy)p;

        MemcachedClient memcachedClient = proxy.getMemcachedClient();
        try {
            System.out.println(memcachedClient);
            System.out.println(memcachedClient.getServersDescription());
            System.out.println(memcachedClient.set("foo", 5000, "bar"));
            System.out.println(memcachedClient.get("foo"));
        } finally {
            memcachedClient.shutdown();
        }
    }
}
