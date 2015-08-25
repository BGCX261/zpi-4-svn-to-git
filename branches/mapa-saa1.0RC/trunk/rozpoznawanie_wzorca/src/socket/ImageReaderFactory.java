package socket;

import java.io.BufferedReader;
import java.io.PrintWriter;

import czytnik.ObserwatorCzytnika;

/**
 * Interfejs fabrykujacy na potrzeby guice
 * 
 * @author elistan
 * 
 */
public interface ImageReaderFactory {
  /**
   * @param out
   *          Strumien wyjsciowy
   * @param in
   *          Strumien wejsciowy
   * @param oc
   *          Obserwator
   * @return Obiekt ktory wczyta obrazek
   */
  public ImageReader createImageReader(PrintWriter out, BufferedReader in,
      ObserwatorCzytnika oc);
}
