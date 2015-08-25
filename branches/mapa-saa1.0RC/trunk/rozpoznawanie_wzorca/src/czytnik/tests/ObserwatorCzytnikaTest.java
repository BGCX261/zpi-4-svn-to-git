package czytnik.tests;

import static org.junit.Assert.fail;

import java.util.Observable;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.*;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import socket.HashDaneCzytnikaPair;
import algorytm.HashUzytkownika;
import algorytm.HashUzytkownikaFactory;
import czytnik.DaneCzytnika;

public class ObserwatorCzytnikaTest {

  protected HashUzytkownikaFactory hashFactory;
  protected DaneCzytnika dane;
  protected Observable observable;

  public ObserwatorCzytnikaTest() {
    super();
  }

  protected void prepareHashFactory() {
    hashFactory = mock(HashUzytkownikaFactory.class);
    when(hashFactory.createHashUzytkownika((double[]) any())).thenAnswer(
        new Answer<HashUzytkownika>() {
  
          @Override
          public HashUzytkownika answer(InvocationOnMock invocation)
              throws Throwable {
            double[] argument = (double[]) invocation.getArguments()[0];
            return new HashUzytkownika(argument, argument.length);
          }
        });
  }

  protected void failPoWyjatku(Exception e) {
    fail("Zlapany wyjatek" + e.getLocalizedMessage());
  }

  protected HashDaneCzytnikaPair stworzHash(double... tablicaZHashem) {
    HashUzytkownika hash = new HashUzytkownika(tablicaZHashem,
        tablicaZHashem.length);
    HashDaneCzytnikaPair pair = new HashDaneCzytnikaPair(hash, dane);
    return pair;
  }

}