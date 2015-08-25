package czytnik;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * Klasa zawierajaca w sobie dane czytnika - na razie tylko id
 * 
 * @author elistan
 * 
 */
public class DaneCzytnika {
  int id;

  /**
   * @param id
   *          Id czytnika
   */
  @Inject
  public DaneCzytnika(@Assisted int id) {
    this.id = id;
  }

  /**
   * @return Id czytnika
   */
  public int getId() {
    return id;
  }

}
