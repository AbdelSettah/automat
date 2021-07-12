package de.bpgit.automat.domain;

import lombok.Data;

import java.util.Arrays;

/**
 * @author Abdel
 * Münzenbündel für sowohl das die eingeworfenen Münzen, siehe {@link Muenze} als auch das Wechselgeld
 * Es werden die Anzahl der jeweiligen Münzen gesetzt
 *
 */
@Data
public class MuenzenBuendel {

    private int anzahlZenCentMuenzen;
    private int anzahlZwanzigCentMuenzen;
    private int anzahlFuenfzigCentMuenzen;
    private int anzahlEinEuroMuenzen;
    private int anzahlZweiEuroMuenzen;

    public MuenzenBuendel(Muenze... eingeworfen) {
        this.anzahlZenCentMuenzen = (int) Arrays.stream(eingeworfen).filter(m->m.equals(Muenze.ZEHN_CENT)).count();
        this.anzahlZwanzigCentMuenzen = (int) Arrays.stream(eingeworfen).filter(m->m.equals(Muenze.ZWANZIG_CENT)).count();
        this.anzahlFuenfzigCentMuenzen = (int) Arrays.stream(eingeworfen).filter(m->m.equals(Muenze.FUENFZIG_CENT)).count();
        this.anzahlEinEuroMuenzen = (int) Arrays.stream(eingeworfen).filter(m->m.equals(Muenze.EIN_EURO)).count();
        this.anzahlZweiEuroMuenzen = (int) Arrays.stream(eingeworfen).filter(m->m.equals(Muenze.ZWEI_EURO)).count();
    }
}
