package baza_danych.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ConnectionFactoryTest.class, DatabaseFacadeTest.class,
    UzytkownicyTest.class, ParaCzytnikowTest.class, GrafCzytnikowTest.class,
    TabelaGrafowTest.class })
public class BazaDanychTestSuite {

}
