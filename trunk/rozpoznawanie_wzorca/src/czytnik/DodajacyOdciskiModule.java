package czytnik;

import java.util.concurrent.Semaphore;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Modul dedykowany dodawaniu odciskow do bazy
 * 
 * @author elistan
 * 
 */
public class DodajacyOdciskiModule extends AbstractModule {
  int iloscPowtorzen;

  /**
   * Tworzy modul
   * 
   * @param iloscPowtorzen
   *          Ile razy nalezy pobraz odcisk od jednej osoby.
   */
  public DodajacyOdciskiModule(int iloscPowtorzen) {
    this.iloscPowtorzen = iloscPowtorzen;
  }

  @Override
  protected void configure() {
    bind(ObserwatorCzytnika.class).to(DodawaczOdciskow.class);
    bind(Integer.class).annotatedWith(Names.named("Powtorzenia")).toInstance(
        iloscPowtorzen);
    // Zablokuje sie na iloscObrazkow odblokowan
    bind(Semaphore.class).toInstance(new Semaphore(0));
    bind(Integer.class).toInstance(iloscPowtorzen);
  }
}
