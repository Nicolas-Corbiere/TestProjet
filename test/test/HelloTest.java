package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import def.Hello;

class HelloTest {

	@Test
	void test() {
		Hello hello = new Hello();
		assertEquals( hello.hello, "Hello World !");
	}

}
