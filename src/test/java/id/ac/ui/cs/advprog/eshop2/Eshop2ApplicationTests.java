package id.ac.ui.cs.advprog.eshop2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class Eshop2ApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> Eshop2Application.main(new String[]{}));
    }

}
