package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.google.inject.Inject;

public class Ramka extends JFrame {
  private final class ButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        int nowyId = Integer.parseInt(textFieldIdCzytnika.getText());
        if (nowyId <= 0)
          throw new NumberFormatException(
              "Id czytnika powinno byc wieksze od 0");
        aktualnyIdCzytnika.setText(textFieldIdCzytnika.getText());
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(
            Ramka.this,
            "Nie udalo sie poprawnie odczytac id czytnika.\nBlad to: "
                + ex.getMessage());
      }
      textFieldIdCzytnika.setText("");
      textFieldIdCzytnika.requestFocus();
    }

  }

  private JPanel panelWyboruCzytnika, panelWynikuAutoryzacji;
  private JScrollPane panelLogow;
  private static final Color zielony = Color.green, czerwony = Color.red;
  private JLabel aktualnyIdCzytnika;
  private JTextField textFieldIdCzytnika;
  private JTextArea poleTekstoweNaLogi;

  @Inject
  public Ramka() {
    setLayout(new FlowLayout());
    przygotujPanelWprowadzania();
    przygotujPanelWynikuAutoryzacji();
    przygotujPanelLogow();
    dodajKomponenty();
    ustawParametryDzialania();
  }

  private void przygotujPanelLogow() {
    poleTekstoweNaLogi = new JTextArea();
    panelLogow = new JScrollPane(poleTekstoweNaLogi);
    panelLogow.setPreferredSize(new Dimension(370, 275));
    poleTekstoweNaLogi.setEditable(false);
  }

  private void przygotujPanelWynikuAutoryzacji() {
    panelWynikuAutoryzacji = new JPanel();
    panelWynikuAutoryzacji.setPreferredSize(new Dimension(200, 200));
    panelWynikuAutoryzacji.setBackground(czerwony);
  }

  private void przygotujPanelWprowadzania() {
    panelWyboruCzytnika = new JPanel();
    panelWyboruCzytnika.setLayout(new BoxLayout(panelWyboruCzytnika,
        BoxLayout.Y_AXIS));
    JPanel panelWprowadzania = new JPanel(new GridLayout(2, 2));
    JLabel labelIdCzytnika = new JLabel("Id czytnika:");
    panelWprowadzania.add(labelIdCzytnika);
    textFieldIdCzytnika = new JTextField(10);
    panelWprowadzania.add(textFieldIdCzytnika);
    JLabel labelAktualny = new JLabel("Aktualny id:");
    panelWprowadzania.add(labelAktualny);
    aktualnyIdCzytnika = new JLabel("1");
    panelWprowadzania.add(aktualnyIdCzytnika);
    panelWyboruCzytnika.add(panelWprowadzania);
    JButton potwierdzZmiane = new JButton("Zapisz");
    potwierdzZmiane.addActionListener(new ButtonListener());
    panelWyboruCzytnika.add(potwierdzZmiane);
  }

  private void ustawParametryDzialania() {
    setSize(400, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  private void dodajKomponenty() {
    add(panelWynikuAutoryzacji);
    add(panelWyboruCzytnika);
    add(panelLogow);
  }

  public int pobierzWybraneIdCzytnika() {
    return Integer.parseInt(aktualnyIdCzytnika.getText());
  }

  public void uruchom() {
    setVisible(true);
  }

  public void uaktualnijStatus(final boolean wynikAutoryzacji,
      final String komunikat) {
    if (SwingUtilities.isEventDispatchThread()) {
      zmienKolor(wynikAutoryzacji);
      ustawKomunikat(komunikat);
      validate();
    } else {
      try {
        SwingUtilities.invokeAndWait(new Runnable() {

          @Override
          public void run() {
            uaktualnijStatus(wynikAutoryzacji, komunikat);
          }
        });
      } catch (InvocationTargetException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  private void zmienKolor(boolean czyPozytywny) {
    if (czyPozytywny) {
      panelWynikuAutoryzacji.setBackground(zielony);
    } else {
      panelWynikuAutoryzacji.setBackground(czerwony);
    }
  }

  private void ustawKomunikat(String komunikat) {
    poleTekstoweNaLogi.append(komunikat);
    poleTekstoweNaLogi.append("\n");
  }
}
