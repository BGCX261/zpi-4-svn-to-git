package test;

import logging_tests.MySqlLogFormaterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import socket.tests.SocketTestSuite;
import uslugi.tests.UslugiTestSuite;
import algorytm.tests.AlgorytmTestSuite;
import autoryzacja.tests.AutoryzacjaTestSuite;
import baza_danych.tests.BazaDanychTestSuite;
import czytnik.tests.CzytnikTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ AlgorytmTestSuite.class, SocketTestSuite.class,
    MySqlLogFormaterTest.class, CzytnikTestSuite.class, UslugiTestSuite.class,
    AutoryzacjaTestSuite.class, BazaDanychTestSuite.class })
public class AllTests {
}
