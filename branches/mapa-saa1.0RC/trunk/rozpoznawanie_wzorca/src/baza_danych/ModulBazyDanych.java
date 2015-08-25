package baza_danych;

import java.util.List;
import java.util.logging.Handler;

import algorytm.HashUzytkownika;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.name.Names;

public class ModulBazyDanych extends AbstractModule {
  boolean debug = false;

  public ModulBazyDanych() {

  }

  public ModulBazyDanych(boolean debug) {
    this.debug = debug;
  }

  @Override
  protected void configure() {
    // Na razie niepotrzebne - byc moze bedzie przydatne poxniej
    bind(ConnectionFactory.class).annotatedWith(Names.named("Local")).to(
        LocalMySqlConnectionFactory.class);
    if (debug) {
      bind(DatabaseFacade.class).to(FakeDatabaseFacade.class);
    } else {
      bind(ConnectionFactory.class).to(RemoteMySqlConnectionFactory.class);
    }
  }

}

class FakeDatabaseFacade extends DatabaseFacade {
  @Inject
  public FakeDatabaseFacade() {
    super(null, null);
  }

  @Override
  public boolean czyKwerendaZwrocilaWyniki() {
    return true;
  }

  @Override
  public Handler createSqlHandler() {
    return null;
  }

  @Override
  public Integer pobierzIdUzytkownika(String sql) {
    System.out.println(sql);
    return 0;
  }

  @Override
  public List<HashUzytkownika> pobierzUzytkownikow(String query) {
    System.out.println(query);
    return null;
  }

  @Override
  public boolean wyslijKwerende(String query) {
    System.out.println(query);
    return true;
  }

  @Override
  public int wyslijKwerendeUpdate(String query) {
    System.out.println(query);
    return 1;
  }
}
