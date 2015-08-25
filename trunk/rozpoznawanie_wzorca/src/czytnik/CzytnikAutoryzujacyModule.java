package czytnik;

import java.util.concurrent.Semaphore;

import com.google.inject.AbstractModule;

/**
 * Modul obslugi czytnika sluzacy do autoryzacji
 * 
 * @author elistan
 * 
 */
public class CzytnikAutoryzujacyModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ObserwatorCzytnika.class).to(WeryfikatorOdciskow.class);
    bind(Semaphore.class).toInstance(new Semaphore(0));
    bind(Integer.class).toInstance(1);
  }
}
