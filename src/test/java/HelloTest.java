import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HelloTest {

	@Test
	public void test() {
		Hello hello = new Hello();
		assertEquals(hello.hello, "Hello World !");
	}
}
