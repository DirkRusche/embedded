package org.hbrs.embedded;

import java.util.HashSet;
import java.util.Set;
import org.hbrs.embedded.Spieler.Action;
import org.hbrs.embedded.common.Karte;
import org.hbrs.embedded.common.Karte.Farbe;
import org.hbrs.embedded.common.Karte.Typ;

public class Helper {

  public static float getPunkte(Hand hand) {
    Karte[] karten = hand.getKarten();

    if (isSameTyp(karten) && isDifferentFarbe(karten)) {
      return 30.5f;
    }
    if (isSameFarbe(karten)) {
      return getWert(karten);
    }
    for (int i = 0; i < 3; i++) {
      for (int j = i + 1; j < 3; j++) {
        if (isSameFarbe(karten[i], karten[j])) {
          return getWert(karten[i], karten[j]);
        }
      }
    }

    return getMaxWert(karten);
  }

  private static boolean isSameTyp(Karte... karten) {
    if (karten == null || karten.length == 0) {
      return true;
    }
    Typ typ = karten[0].getTyp();
    for (int i = 1; i < karten.length; i++) {
      if (typ != karten[i].getTyp()) {
        return false;
      }
    }
    return true;
  }

  private static boolean isSameFarbe(Karte... karten) {
    if (karten == null || karten.length == 0) {
      return true;
    }
    Farbe farbe = karten[0].getFarbe();
    for (int i = 1; i < karten.length; i++) {
      if (farbe != karten[i].getFarbe()) {
        return false;
      }
    }
    return true;
  }

  private static boolean isDifferentFarbe(Karte... karten) {
    if (karten == null || karten.length == 0) {
      return true;
    }
    Set<Farbe> farben = new HashSet<>(karten.length);

    for (Karte karte : karten) {
      if (!farben.add(karte.getFarbe())) {
        return false;
      }
    }

    return true;
  }

  private static float getWert(Karte... karten) {
    if (karten == null || karten.length == 0) {
      return 0;
    }

    float wert = 0;
    for (Karte karte : karten) {
      wert += karte.getTyp().wert;
    }
    return wert;
  }

  private static float getMaxWert(Karte... karten) {
    if (karten == null || karten.length == 0) {
      return 0;
    }

    float wert = 0;
    for (Karte karte : karten) {
      wert = (karte.getTyp().wert > wert ? karte.getTyp().wert : wert);
    }
    return wert;
  }

  public static Action getAction(String input) {
    if (input.equalsIgnoreCase("close")) {
      return Action.CLOSE;
    }
    else if (input.equalsIgnoreCase("check")){
      return Action.CHECK;
    }
    else if (input.equalsIgnoreCase("t11")){
      return Action.T11;
    }
    else if (input.equalsIgnoreCase("t12")){
      return Action.T12;
    }
    else if (input.equalsIgnoreCase("t13")){
      return Action.T13;
    }
    else if (input.equalsIgnoreCase("t21")){
      return Action.T21;
    }
    else if (input.equalsIgnoreCase("t22")){
      return Action.T22;
    }
    else if (input.equalsIgnoreCase("t23")){
      return Action.T23;
    }
    else if (input.equalsIgnoreCase("t31")){
      return Action.T31;
    }
    else if (input.equalsIgnoreCase("t32")){
      return Action.T32;
    }
    else if (input.equalsIgnoreCase("t33")){
      return Action.T33;
    }
    else {
      return Action.CHECK;
    }
  }
}
