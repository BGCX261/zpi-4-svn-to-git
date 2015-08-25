package algorytm;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_2D;

/**
 * Udostepnia transformacje fouriera z odpowiednimi parametrami
 * 
 * @author elistan
 * 
 */
public class ProviderTransformacji {
  /**
   * @param wysokosc
   * @param szerokosc
   * @return transformacja
   */
  public DoubleFFT_2D provide(int wysokosc, int szerokosc) {
    return new DoubleFFT_2D(wysokosc, szerokosc);
  }
}
