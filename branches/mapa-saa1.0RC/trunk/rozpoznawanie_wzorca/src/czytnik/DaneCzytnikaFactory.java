package czytnik;

/**
 * Interfejs do tworzenia obiektow DaneCzytnika
 * 
 * @author elistan
 * 
 */
public interface DaneCzytnikaFactory {
  /**
   * ..
   * 
   * @param id
   *          Id czytnika
   * @return Obiekt typu DaneCzytnika
   */
  DaneCzytnika stworz(int id);
}
