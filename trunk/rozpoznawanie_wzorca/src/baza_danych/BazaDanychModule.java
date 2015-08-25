package baza_danych;

import baza_danych.model.GrafCzytnikowFactory;
import baza_danych.model.UzytkownikFactory;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class BazaDanychModule extends AbstractModule {

  public BazaDanychModule() {

  }

  @Override
  protected void configure() {
    bind(ConnectionFactory.class).to(RemoteMySqlConnectionFactory.class);
    install(new FactoryModuleBuilder().build(UzytkownikFactory.class));
    install(new FactoryModuleBuilder().build(GrafCzytnikowFactory.class));
  }
}
