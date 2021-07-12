package de.bpgit.automat.domain;

/**
 * @author Abdel
 * Implementierung vom {@link GeldRechner}
 */
public class GeldRechnerImpl implements GeldRechner {

    /**
     * @author Abdel
     * Setzt alle möglichen Münzenanzahl mit Modulo Brechnung
     * @param wechselGeld Die Geldsumme des Wechselgeldes
     * @return Münzenbündel mit jeweils der Anzahl der Münzen, siehe {@link Muenze}
     */
    @Override
    public MuenzenBuendel rechneWechselGeld(int wechselGeld) {
        MuenzenBuendel wechselGeldBuendel = new MuenzenBuendel();
        int wechselGeldRest = wechselGeld;
        
        wechselGeldBuendel.setAnzahlZweiEuroMuenzen(wechselGeldRest / Muenze.ZWEI_EURO.getwert());
        wechselGeldRest %= Muenze.ZWEI_EURO.getwert();

        wechselGeldBuendel.setAnzahlEinEuroMuenzen(wechselGeldRest / Muenze.EIN_EURO.getwert());
        wechselGeldRest %= Muenze.EIN_EURO.getwert();

        wechselGeldBuendel.setAnzahlFuenfzigCentMuenzen(wechselGeldRest / Muenze.FUENFZIG_CENT.getwert());
        wechselGeldRest %= Muenze.FUENFZIG_CENT.getwert();

        wechselGeldBuendel.setAnzahlZwanzigCentMuenzen(wechselGeldRest / Muenze.ZWANZIG_CENT.getwert());
        wechselGeldRest %= Muenze.ZWANZIG_CENT.getwert();

        wechselGeldBuendel.setAnzahlZenCentMuenzen(wechselGeldRest / Muenze.ZEHN_CENT.getwert());

        return wechselGeldBuendel;
    }
}
