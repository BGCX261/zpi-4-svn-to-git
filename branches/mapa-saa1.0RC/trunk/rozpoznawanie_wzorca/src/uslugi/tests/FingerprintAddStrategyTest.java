package uslugi.tests;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import uslugi.fingerprint.FingerprintAddStrategy;
import algorytm.HashUzytkownika;
import czytnik.ObserwatorCzytnika;

public class FingerprintAddStrategyTest {

  private ObserwatorCzytnika obserwator;
  private FingerprintAddStrategy strategia;
  private HashUzytkownika hash;

  @Before
  public void setUp() throws Exception {
    obserwator = mock(ObserwatorCzytnika.class);
    strategia = new FingerprintAddStrategy(obserwator);
    hash = mock(HashUzytkownika.class);
  }

  @Test
  public void testGenerujSql() {
    try {
      int id = 0;
      when(obserwator.getHash()).thenReturn(hash);
      String spodziewanyHash = "HASH";
      when(hash.toString()).thenReturn(spodziewanyHash);
      String result = strategia.generujSql(id);
      assertEquals(przygotujZapytanie(spodziewanyHash, id), result);
    } catch (InterruptedException e) {
      fail("Exception " + e);
    }
  }

  private String przygotujZapytanie(String hash, int id) {
    String wynik = "INSERT INTO odcisk_palca(hash, uzytkownik_id) VALUES(\""
        + hash + "\", " + id + ");";
    return wynik;
  }
}
