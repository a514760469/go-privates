import ai.lifo.CloudStreamApplication;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhanglifeng
 * @since 2024-06-27
 */
@SpringBootTest(classes =  CloudStreamApplication.class)
public class BootTestStreamApplicationTests {

//    @Autowired
//    private InputDestination input;
//
//    @Autowired
//    private OutputDestination output;
//
//    @Test
//    void contextLoads() {
//        input.send(new GenericMessage<>("hello".getBytes()));
//        assertThat(output.receive().getPayload()).isEqualTo("HELLO".getBytes());
//    }
}
