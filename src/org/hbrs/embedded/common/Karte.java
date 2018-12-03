package org.hbrs.embedded.common;

import java.util.Objects;

public class Karte {

  private final Farbe farbe;
  private final Typ typ;

  public Karte(Farbe farbe, Typ typ) {
    this.typ = typ;
    this.farbe = farbe;
  }

  public Typ getTyp() {
    return typ;
  }

  public Farbe getFarbe() {
    return farbe;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Karte karte = (Karte) o;
    return typ == karte.typ && farbe == karte.farbe;
  }

  @Override
  public int hashCode() {
    return Objects.hash(typ, farbe);
  }

  @Override
  public String toString() {
    return farbe + " " + typ;
  }

  public enum Typ {
    SIEBEN(7), ACHT(8), NEUN(9), ZEHN(10), BUBE(10), DAME(10), KÖNIG(10), ASS(11);

    public final int wert;

    Typ(int wert) {
      this.wert = wert;
    }
    public String toString() {
      return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }
  }
  public enum Farbe {
    KARO, HERZ, PIK, KREUZ;

    public String toString() {
      return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }
  }
}
