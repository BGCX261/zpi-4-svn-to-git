package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import czytnik.DaneCzytnikaFactory;

/**
 * Modul odpowiedzialny za komunikacje z aplikacja c++ po stronie czytnika
 * 
 * @author elistan
 * 
 */
public class SocketModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new FactoryModuleBuilder().build(ImageReaderFactory.class));
    bind(ExecutorService.class).toInstance(Executors.newFixedThreadPool(5));
    install(new FactoryModuleBuilder().build(DaneCzytnikaFactory.class));
  }

  @Provides
  ServerSocket provideSocket() {
    try {
      ServerSocket socket = new ServerSocket(8081);
      return socket;
    } catch (IOException e) {
      try {
        ServerSocket socket = new ServerSocket(8081);
        return socket;
      } catch (IOException e1) {
        // TODO: log
        return null;
      }

    }
  }
}
