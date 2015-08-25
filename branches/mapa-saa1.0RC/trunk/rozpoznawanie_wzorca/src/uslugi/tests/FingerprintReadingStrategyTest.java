package uslugi.tests;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import uslugi.fingerprint.FingerprintReadingStrategy;
import algorytm.HashUzytkownika;

public class FingerprintReadingStrategyTest {
  FingerprintReadingStrategy readStrategy;

  @Before
  public void setUp() throws Exception {
    readStrategy = new FingerprintReadingStrategy();
  }

  @Test
  public void testGenerujSql() {
    String expectedQuery = "SELECT op.hash FROM odcisk_palca op WHERE op.aktywny = 1;";
    assertEquals(expectedQuery, readStrategy.generujSql());
  }

  @Test
  public void testGenerujSqlHashUzytkownika() {
    String expectedQuery = "SELECT op.uzytkownik_id FROM odcisk_palca op WHERE op.hash = 'HASH';";
    HashUzytkownika hash = mock(HashUzytkownika.class);
    when(hash.toString()).thenReturn("HASH");
    assertEquals(expectedQuery, readStrategy.generujSql(hash));
  }
}
