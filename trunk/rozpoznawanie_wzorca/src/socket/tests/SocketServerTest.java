package socket.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.*;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.*;

import org.junit.Test;

import socket.ImageReaderFactory;
import socket.SocketServer;
import czytnik.DodawaczOdciskow;
import czytnik.ObserwatorCzytnika;

/**
 * Testy jednostkowe serwera socketow
 * 
 * @author elistan
 * 
 */
public class SocketServerTest {

  /**
   * Testowe uruchomienie.
   */
  @Test
  public void testRun() {
    boolean exception = false;
    ServerSocket mockSocketServer = mock(ServerSocket.class);
    ExecutorService pool = mock(ExecutorService.class);
    Socket mockSocket = mock(Socket.class);
    ImageReaderFactory mockFactory = mock(ImageReaderFactory.class);
    try {
      when(mockSocketServer.accept()).thenReturn(mockSocket).thenThrow(
          new RuntimeException("End of test"));
      when(mockSocket.getInputStream()).thenReturn(System.in);
      when(mockSocket.getOutputStream()).thenReturn(System.out);

      SocketServer sc = new SocketServer(mockSocketServer, pool, mockFactory,
          mock(DodawaczOdciskow.class));
      sc.run();
    } catch (Exception ex) {
      assertEquals("End of test", ex.getMessage());
      exception = true;
    }
    try {
      verify(mockSocket).getInputStream();
      verify(mockSocket).getOutputStream();
      verify(pool).execute((Runnable) any());
      verify(mockFactory, times(1)).createImageReader((PrintWriter) any(),
          (BufferedReader) any(), (ObserwatorCzytnika) any());
      assertTrue(exception);
    } catch (IOException e) {
      e.printStackTrace();
      fail("IOException: " + e);
    }
  }

  /**
   * Testowe zamkniecie
   */
  @Test
  public void testStop() {
    ServerSocket mockSocketServer = mock(ServerSocket.class);
    ExecutorService pool = mock(ExecutorService.class);
    ImageReaderFactory mockFactory = mock(ImageReaderFactory.class);
    try {

      SocketServer sc = new SocketServer(mockSocketServer, pool, mockFactory,
          mock(DodawaczOdciskow.class));
      sc.stop();
      sc.run();
    } catch (Exception ex) {
      assertEquals("End of test", ex.getMessage());
    }
    try {
      verify(mockSocketServer, never()).accept();
    } catch (IOException e) {
      fail("Exception " + e);
    }
  }

  @Test
  public void testStartListening() {
    boolean exception = false;
    ServerSocket mockSocketServer = mock(ServerSocket.class);
    ExecutorService pool = mock(ExecutorService.class);
    Socket mockSocket = mock(Socket.class);
    ImageReaderFactory mockFactory = mock(ImageReaderFactory.class);
    try {
      when(mockSocketServer.accept()).thenReturn(mockSocket).thenThrow(
          new RuntimeException("End of test"));
      when(mockSocket.getInputStream()).thenReturn(System.in);
      when(mockSocket.getOutputStream()).thenReturn(System.out);

      SocketServer sc = new SocketServer(mockSocketServer, pool, mockFactory,
          mock(DodawaczOdciskow.class));
      sc.startListening();
    } catch (Exception ex) {
      assertEquals("End of test", ex.getMessage());
      exception = true;
    }
    try {
      verify(mockSocket).getInputStream();
      verify(mockSocket).getOutputStream();
      verify(pool).execute((Runnable) any());
      verify(mockFactory, times(1)).createImageReader((PrintWriter) any(),
          (BufferedReader) any(), (ObserwatorCzytnika) any());
      assertTrue(exception);
    } catch (IOException e) {
      e.printStackTrace();
      fail("IOException: " + e);
    }
  }
}
