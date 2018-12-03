package org.hbrs.embedded;

import org.hbrs.embedded.common.Karte;

public interface Spieler {

  Action getAction(Karte[] hand, Karte[] tisch);

  Decision getDecision(Karte[] hand);

  void notify(String text);

  String getName();

  enum Action {
    CLOSE, CHECK, T11, T12, T13, T21, T22, T23, T31, T32, T33;
  }

  enum Decision {
    YES, NO;
  }

}
