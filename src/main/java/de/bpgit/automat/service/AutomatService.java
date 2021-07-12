package de.bpgit.automat.service;

import de.bpgit.automat.domain.*;
import de.bpgit.automat.exceptions.BetragZuNiedrigException;
import de.bpgit.automat.exceptions.GetraenkNichtVerfuegbarException;
import de.bpgit.automat.exceptions.KeinPassendesWechselGeldException;

import java.util.List;

/**
 * @author Abdel
 * Service, der die Funktionalität des Getränkeautomates kapselt.
 * Der Service stellt folgende Funktionen zur Verfügung:
 * <ul>
 *     <li>Befüllen der Ware</li>
 *     <li>Befüllen des Geldbestandes</li>
 *     <li>Entleeren der Ware</li>
 *     <li>Entleeren des Geldbestandes</li>
 *     <li>Kaufen eines Getränks</li>
 * </ul>
 *
 */
public interface AutomatService {
    void wareFuellen(List<Getraenk> faecher);
    void geldFuellen(List<Muenze> muenzen);
    void wareEntleeren();
    void geldEntleeren();
    GetraenkAnfrage kaufen(Getraenk auswahl, Muenze... einzahlungen) throws GetraenkNichtVerfuegbarException, BetragZuNiedrigException, KeinPassendesWechselGeldException;
    Automat getAutomat();
}
