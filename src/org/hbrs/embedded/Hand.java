package org.hbrs.embedded;

import org.hbrs.embedded.common.Karte;

public class Hand {

  private Karte karte1;
  private Karte karte2;
  private Karte karte3;

  public Hand(Karte karte1, Karte karte2, Karte karte3) {
    this.karte1 = karte1;
    this.karte2 = karte2;
    this.karte3 = karte3;
  }

  public Karte setKarte1(Karte karte1) {
    Karte karte = this.karte1;
    this.karte1 = karte1;
    return karte;
  }

  public Karte setKarte2(Karte karte2) {
    Karte karte = this.karte2;
    this.karte2 = karte2;
    return karte;
  }

  public Karte setKarte3(Karte karte3) {
    Karte karte = this.karte3;
    this.karte3 = karte3;
    return karte;
  }

  public Karte getKarte1() {
    return karte1;
  }

  public Karte getKarte2() {
    return karte2;
  }

  public Karte getKarte3() {
    return karte3;
  }

  public Karte[] getKarten() {
    return new Karte[]{karte1, karte2, karte3};
  }



}
