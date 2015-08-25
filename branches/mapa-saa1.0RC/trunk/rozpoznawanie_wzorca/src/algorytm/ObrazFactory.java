package algorytm;

/**
 * Fabryka obrazow
 * @author elistan
 *
 */
public interface ObrazFactory {
  /**
   * Generuje obraz na podstawie tablicy z pikselami
   * @param piksele
   * @return Wygenerowany obraz
   */
  Obraz generujObraz(int[][] piksele);
  /**
   * Generuje obraz na podstawie tablicy z pikselami
   * @param piksele
   * @return Wygenerowany obraz
   */
  Obraz generujObraz(double[][] piksele);
}
