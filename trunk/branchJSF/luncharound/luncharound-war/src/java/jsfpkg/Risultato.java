/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpkg;

import luncharoundpkg.Locale;
import luncharoundpkg.Valutazione;

/**
 *
 * @author Bovio Lorenzo, Bronzino Francesco e Concas Davide
 */
public class Risultato {
    public int posizione;
    public Locale loc;
    public double vel;
    public double aff;
    public double qual;
    public double quan;
    public double pul;
    public double cor;

    public Risultato(int posizione, Locale loc, Valutazione val) {
        this.posizione = posizione;
        this.loc = loc;
        vel=val.getVelocita();
        aff=val.getAffollamento();
        qual=val.getQualita();
        quan=val.getQuantita();
        pul=val.getPulizia();
        cor=val.getCortesia();
    }

    public Locale getLoc() {
        return loc;
    }

    public void setLoc(Locale loc) {
        this.loc = loc;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public double getAff() {
        return aff;
    }

    public void setAff(double aff) {
        this.aff = aff;
    }

    public double getCor() {
        return cor;
    }

    public void setCor(double cor) {
        this.cor = cor;
    }

    public double getPul() {
        return pul;
    }

    public void setPul(double pul) {
        this.pul = pul;
    }

    public double getQual() {
        return qual;
    }

    public void setQual(double qual) {
        this.qual = qual;
    }

    public double getQuan() {
        return quan;
    }

    public void setQuan(double quan) {
        this.quan = quan;
    }

    public double getVel() {
        return vel;
    }

    public void setVel(double vel) {
        this.vel = vel;
    }
    
}
