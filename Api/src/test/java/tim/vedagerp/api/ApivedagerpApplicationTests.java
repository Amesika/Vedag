package tim.vedagerp.api;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import tim.vedagerp.api.entities.StandardAccount;

@SpringBootTest
class ApivedagerpApplicationTests {


	@Test
	void contextLoads() {
	}

	@Test
	public void givenStandardAccount_thenUseStandardAccessors() {
		StandardAccount account = new StandardAccount();
		account.setName("Basic Accessors");
		account.setBalance(BigDecimal.TEN);

		assertEquals("Basic Accessors", account.getName());
		assertEquals(BigDecimal.TEN, account.getBalance());
	}

}
