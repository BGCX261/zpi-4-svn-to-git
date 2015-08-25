package baza_danych.model;

public class ParaCzytnikow {
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + drugiCzytnik;
    result = prime * result + pierwszyCzytnik;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ParaCzytnikow other = (ParaCzytnikow) obj;
    if (drugiCzytnik != other.drugiCzytnik)
      return false;
    if (pierwszyCzytnik != other.pierwszyCzytnik)
      return false;
    return true;
  }

  public int pierwszyCzytnik;
  public int drugiCzytnik;

  public ParaCzytnikow(int pierwszy, int drugi) {
    if (drugi < pierwszy) {
      int tmp = pierwszy;
      pierwszy = drugi;
      drugi = tmp;
    }
    this.pierwszyCzytnik = pierwszy;
    this.drugiCzytnik = drugi;
  }

  @Override
  public String toString() {
    StringBuilder wynik = new StringBuilder(7);
    wynik.append("(").append(pierwszyCzytnik).append(", ").append(drugiCzytnik)
        .append(")");
    return wynik.toString();
  }
}
