package socket.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.*;

import static org.mockito.Matchers.eq;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.OngoingStubbing;

import socket.HashDaneCzytnikaPair;
import socket.ImageReader;
import algorytm.FeatureExtractor;
import czytnik.DaneCzytnika;
import czytnik.DaneCzytnikaFactory;
import czytnik.ObserwatorCzytnika;

public class ImageReaderTest {
  ImageReader reader;
  private PrintWriter outputWriter;
  private BufferedReader inputReader;
  private ObserwatorCzytnika obserwatorCzytnika;
  private FeatureExtractor ekstraktorCech;
  private DaneCzytnikaFactory daneCzytnikaFactory;
  private int idCzytnika;
  private String naglowekIdCzytnika;
  private String naglowekPGM;
  private ArgumentCaptor<Object> captor;

  @Before
  public void setUp() throws Exception {
    outputWriter = mock(PrintWriter.class);
    inputReader = mock(BufferedReader.class);
    obserwatorCzytnika = mock(ObserwatorCzytnika.class);
    ekstraktorCech = mock(FeatureExtractor.class);
    daneCzytnikaFactory = new DaneCzytnikaFactory() {

      @Override
      public DaneCzytnika stworz(int id) {
        return new DaneCzytnika(id);
      }
    };
    reader = new ImageReader(outputWriter, inputReader, obserwatorCzytnika,
        ekstraktorCech, daneCzytnikaFactory);
    idCzytnika = 7;
    naglowekIdCzytnika = "" + idCzytnika;
    captor = ArgumentCaptor.forClass(Object.class);
  }

  @Test
  public void runPassTest() {
    int[][] spodziewaneDane = { { 1, 1 }, { 1, 1 } };
    przygotujDaneDoWyslania(2, 2, "1 1", "1 1");
    reader.run();
    verify(obserwatorCzytnika).update(eq(reader), captor.capture());
    HashDaneCzytnikaPair pair = (HashDaneCzytnikaPair) captor.getValue();
    assertEquals(idCzytnika, pair.dane.getId());
    ArgumentCaptor<int[][]> hashCaptor = ArgumentCaptor.forClass(int[][].class);
    verify(ekstraktorCech).przetworzObraz(hashCaptor.capture());
    assertArrayEquals(spodziewaneDane[0], hashCaptor.getValue()[0]);
    assertArrayEquals(spodziewaneDane[1], hashCaptor.getValue()[1]);
  }

  @Test
  public void zlyNaglowek() {
    try {
      when(inputReader.readLine()).thenReturn("Nie to co trzeba");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    reader.run();
    verify(obserwatorCzytnika).update(eq(reader), captor.capture());
    HashDaneCzytnikaPair pair = (HashDaneCzytnikaPair) captor.getValue();
    assertFalse(pair.valid);

  }

  @Test
  public void zlyNaglowekPgm() {
    try {
      when(inputReader.readLine()).thenReturn(naglowekIdCzytnika).thenReturn(
          "Nietocotrzeba");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    reader.run();
    verify(obserwatorCzytnika).update(eq(reader), captor.capture());
    HashDaneCzytnikaPair pair = (HashDaneCzytnikaPair) captor.getValue();
    assertFalse(pair.valid);

  }

  private void przygotujDaneDoWyslania(int wysokosc, int szerokosc,
      String... linie) {
    try {
      przygotujNaglowekPgm(wysokosc, szerokosc);
      OngoingStubbing<String> stubbing = when(inputReader.readLine())
          .thenReturn(naglowekIdCzytnika).thenReturn(naglowekPGM);
      for (String linia : linie) {
        stubbing = stubbing.thenReturn(linia);
      }
      stubbing.thenReturn(null);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void przygotujNaglowekPgm(int wysokosc, int szerokosc) {
    naglowekPGM = "P2 " + wysokosc + " " + szerokosc + " 255";
  }
}
