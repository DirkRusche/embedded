package org.hbrs.embedded;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import org.hbrs.embedded.Karte.Farbe;
import org.hbrs.embedded.Karte.Typ;

public class Stapel {

  private List<Karte> stapel;
  private Random random = new Random();

  public Stapel() {
    this.stapel = new ArrayList<>(32);

    for (Farbe farbe : Farbe.values()) {
      for (Typ typ : Typ.values()) {
        stapel.add(new Karte(farbe, typ));
      }
    }
  }

  public boolean isEmpty() {
    return stapel.isEmpty();
  }

  public Karte getKarte() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    return stapel.remove(random.nextInt(stapel.size()));
  }

}
