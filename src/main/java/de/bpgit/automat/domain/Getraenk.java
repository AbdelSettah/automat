package de.bpgit.automat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Abdel
 * Ein zu kaufendenes Getränk mit Preis, {@link GetraenkTyp} und einer Nummer, die die Fachnummer im Automaten darstellt
 * Der minimale Preis ist 80 Cent
 */
@Data
@AllArgsConstructor
public class Getraenk {

    public static final int MIN_PREIS = 80;

    private int preis;
    private int nummer;
    private GetraenkTyp getraenkTyp;

}
