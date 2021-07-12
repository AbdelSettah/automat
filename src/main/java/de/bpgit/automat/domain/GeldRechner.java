package de.bpgit.automat.domain;

/**
 * @author Abdel
 * Kapselt die Berechnung des Wechselgeldes
 */
public interface GeldRechner {
    MuenzenBuendel rechneWechselGeld(int wechselGeld);
}
