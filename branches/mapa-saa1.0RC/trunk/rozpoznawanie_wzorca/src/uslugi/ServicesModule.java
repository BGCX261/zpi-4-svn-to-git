package uslugi;

import uslugi.fingerprint.FingerprintAddStrategy;
import uslugi.fingerprint.FingerprintReadingStrategy;

import com.google.inject.AbstractModule;

/**
 * Modul konfigurujacy serwisy
 * 
 * @author elistan
 * 
 */
public class ServicesModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(AddingStrategy.class).to(FingerprintAddStrategy.class);
    bind(ReadingStrategy.class).to(FingerprintReadingStrategy.class);

  }

}
