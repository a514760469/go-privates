package ai.lifo.goprivates;

import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;

@SpringBootTest
class GoPrivatesApplicationTests {

    public static void main(String[] args) {
        ByteArrayInputStream stream = new ByteArrayInputStream(new byte[]{});
        System.out.println("hello" + stream);
    }

}
