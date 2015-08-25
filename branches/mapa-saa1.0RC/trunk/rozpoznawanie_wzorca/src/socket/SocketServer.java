package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import czytnik.ObserwatorCzytnika;

/**
 * Serwer socketow nasluchujacy polaczen
 * 
 * @author elistan
 * 
 */
public class SocketServer implements Runnable {
  ServerSocket socket;
  boolean listening = true;
  private ImageReaderFactory imageReaderFactory;
  ExecutorService pool;
  ObserwatorCzytnika obserwator;

  /**
   * @param socket
   *          ServerSocketow lezacy pod spodem
   * @param pool
   *          Pula watkow czytajacych obrazki
   * @param imageReaderFactory
   *          Fabryka obiektow odczytujacych
   * @param obserwator
   *          Obserwator czytnika uzywajacy tego serwera
   */
  @Inject
  public SocketServer(ServerSocket socket, ExecutorService pool,
      ImageReaderFactory imageReaderFactory, ObserwatorCzytnika obserwator) {
    this.socket = socket;
    this.pool = pool;
    this.imageReaderFactory = imageReaderFactory;
    this.obserwator = obserwator;
  }

  @Override
  public void run() {
    while (listening) {
      try {
        Socket s = socket.accept();
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(
            s.getInputStream()));
        pool.execute(imageReaderFactory.createImageReader(out, in, obserwator));
      } catch (IOException e) {
        // TODO: log
        e.printStackTrace();
        listening = false;
      }
    }
  }

  /**
   * Zatrzymuje serwer
   */
  public void stop() {
    listening = false;
  }

  /**
   * Wznawia serwer
   */
  public void startListening() {
    listening = true;
    run();
  }

  public static void main(String[] args) throws IOException {
    Injector inject = Guice.createInjector(new SocketModule());
    SocketServer server = inject.getInstance(SocketServer.class);
    server.run();
  }
}
