package algorytm;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;

/**
 * Modul odpowiedzialny za pakiet z algorytmem
 * 
 * @author elistan
 * 
 */
public class AlgorytmModule extends AbstractModule {
  private static final int ROZMIAR_WIDMA = 160;
  private double PROG_PODOBIENSTWA = 2;
  private static final int ROZMIAR_HASHA = 17;

  public AlgorytmModule() {

  }

  public AlgorytmModule(double prog) {
    PROG_PODOBIENSTWA = prog;
  }

  @Override
  protected void configure() {
    // tworzy automatycznie fabryke i dodaje ja do modulu
    install(new FactoryModuleBuilder().build(HashUzytkownikaFactory.class));
    install(new FactoryModuleBuilder().build(ObrazFactory.class));
    bind(Integer.class).annotatedWith(Names.named("Magnitude")).toInstance(
        ROZMIAR_WIDMA);
    bind(Double.class).annotatedWith(Names.named("Threshold")).toInstance(
        PROG_PODOBIENSTWA);
    bind(Integer.class).annotatedWith(Names.named("RozmiarHasha")).toInstance(
        ROZMIAR_HASHA);
  }

  /**
   * 
   * @return Metoda sektoryzacji obrazu
   */
  @Provides
  public SektoryzatorObrazu provideSektoryzator() {
    return new SektoryzatorObrazu(ROZMIAR_WIDMA);
  }

}
