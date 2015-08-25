package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import logging.BasicLogger;
import socket.SocketModule;
import socket.SocketServer;
import uslugi.CreateService;
import uslugi.ServicesModule;
import algorytm.AlgorytmModule;
import autoryzacja.AuthorizationModule;
import baza_danych.ModulBazyDanych;

import com.google.inject.Guice;
import com.google.inject.Injector;

import czytnik.ModulCzytnikaDodajacyOdciski;

/**
 * Aplet pozwalajacy na dodanie odcisku palca.
 * 
 * @author andrzej
 * 
 */
public class Applet extends JApplet {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  Logger logger = BasicLogger.applicationLogger;
  private CreateService cs;
  private JButton btn = new JButton("Dodaj odcisk palca dla użytkownika");
  private int liczbaPrzeciagniec;
  private JLabel label = new JLabel();
  private int uid;
  private FingerprintAddingWorker fingerprintAddingWorker;

  // Called when this applet is loaded into the browser.
  public void init() {

    // pobierz wartość przekazaną z html'a
    String uzytkownikId = getParameter("uzytkownik_id");
    logger.setLevel(Level.ALL);
    if (uzytkownikId == null)
      uid = 12;// domyślna wartość
    else
      uid = Integer.parseInt(uzytkownikId);
    liczbaPrzeciagniec = 3;
    final Injector inject = Guice.createInjector(new AuthorizationModule(),
        new ModulCzytnikaDodajacyOdciski(liczbaPrzeciagniec),
        new AlgorytmModule(), new SocketModule(), new ServicesModule(),
        new ModulBazyDanych());

    cs = inject.getInstance(CreateService.class);
    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

      @Override
      protected Void doInBackground() throws Exception {
        SocketServer server = inject.getInstance(SocketServer.class);
        server.run();
        return null;
      }
    };
    worker.execute();
    fingerprintAddingWorker = new FingerprintAddingWorker();
    try {
      SwingUtilities.invokeAndWait(new Runnable() {

        public void run() {
          createGui();
        }

      });
    } catch (InvocationTargetException e) {
      logger.throwing(Applet.class.getCanonicalName(), "init", e);
    } catch (InterruptedException e) {
      logger.throwing(Applet.class.getCanonicalName(), "init", e);
    }

  }

  private void createGui() {
    setSize(300, 60);
    btn.addActionListener(new ActionListenerImpl());
    add(btn);
  }

  private void switchButtonToLabel() {
    remove(btn);
    label = new JLabel("Przeciągnij " + liczbaPrzeciagniec
        + " razy palcem przez czytnik");
    add(label);
    validate();
  }

  private final class FingerprintAddingWorker extends
      SwingWorker<Boolean, Void> {

    @Override
    protected Boolean doInBackground() throws Exception {
      return cs.dodaj(uid);
    }

    @Override
    protected void done() {
      try {
        boolean wynik = get();
        if (wynik == true) {
          label.setText("Dodawanie odcisku palca zakonczone powodzeniem.");
        } else {
          obsluzBlednyOdczyt();
        }
      } catch (InterruptedException e) {
        obsluzBlednyOdczyt();
        logger.throwing(FingerprintAddingWorker.class.getName(), "done", e);
      } catch (ExecutionException e) {
        obsluzBlednyOdczyt();
        logger.throwing(FingerprintAddingWorker.class.getName(), "done", e);
      }
      super.done();
    }

    private void obsluzBlednyOdczyt() {
      label.setText("Operacja nie powiodła się :(");
    }
  }

  private final class ActionListenerImpl implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      switchButtonToLabel();
      fingerprintAddingWorker.execute();
    }
  }
}
