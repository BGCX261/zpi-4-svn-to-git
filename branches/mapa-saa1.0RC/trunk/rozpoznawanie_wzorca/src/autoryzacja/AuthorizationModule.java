package autoryzacja;

import java.util.logging.Logger;

import logging.SystemAccessLogger;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Named;

/**
 * Modul odpowiedzialny za pakiet autoryzacji
 * 
 * @author elistan
 * 
 */
public class AuthorizationModule extends AbstractModule {
  @Override
  protected void configure() {
    Multibinder<AuthorizationMethod> authorizationBinder = Multibinder
        .newSetBinder(binder(), AuthorizationMethod.class);
    authorizationBinder.addBinding().to(GroupAuthorizationMethod.class);
  }

  @Provides
  @Named("SystemAccessLogger")
  Logger getLogger(SystemAccessLogger logWrapper) {
    return logWrapper.systemAccessLogger;
  }
}