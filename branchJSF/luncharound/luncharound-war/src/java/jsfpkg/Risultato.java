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
    /**
     * 
     */
    public int posizione;
    /**
     * 
     */
    public Locale loc;
    /**
     * 
     */
    public double vel;
    /**
     * 
     */
    public double aff;
    /**
     * 
     */
    public double qual;
    /**
     * 
     */
    public double quan;
    /**
     * 
     */
    public double pul;
    private String direction;
    /**
     * 
     */
    public double cor;

    /**
     * 
     * @param posizione
     * @param loc
     * @param val
     * @param direction
     */
    public Risultato(int posizione, Locale loc, Valutazione val, String direction) {
        this.posizione = posizione;
        this.loc = loc;
        vel=val.getVelocita();
        aff=val.getAffollamento();
        qual=val.getQualita();
        quan=val.getQuantita();
        pul=val.getPulizia();
        cor=val.getCortesia();
        this.direction=direction;
    }

    /**
     * 
     * @return
     */
    public Locale getLoc() {
        return loc;
    }

    /**
     * 
     * @param loc
     */
    public void setLoc(Locale loc) {
        this.loc = loc;
    }

    /**
     * 
     * @return
     */
    public int getPosizione() {
        return posizione;
    }

    /**
     * 
     * @param posizione
     */
    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    /**
     * 
     * @return
     */
    public double getAff() {
        return aff;
    }

    /**
     * 
     * @param aff
     */
    public void setAff(double aff) {
        this.aff = aff;
    }

    /**
     * 
     * @return
     */
    public String getDirection() {
        return direction;
    }

    /**
     * 
     * @param direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    
    
    /**
     * 
     * @return
     */
    public double getCor() {
        return cor;
    }

    /**
     * 
     * @param cor
     */
    public void setCor(double cor) {
        this.cor = cor;
    }

    /**
     * 
     * @return
     */
    public double getPul() {
        return pul;
    }

    /**
     * 
     * @param pul
     */
    public void setPul(double pul) {
        this.pul = pul;
    }

    /**
     * 
     * @return
     */
    public double getQual() {
        return qual;
    }

    /**
     * 
     * @param qual
     */
    public void setQual(double qual) {
        this.qual = qual;
    }

    /**
     * 
     * @return
     */
    public double getQuan() {
        return quan;
    }

    /**
     * 
     * @param quan
     */
    public void setQuan(double quan) {
        this.quan = quan;
    }

    /**
     * 
     * @return
     */
    public double getVel() {
        return vel;
    }

    /**
     * 
     * @param vel
     */
    public void setVel(double vel) {
        this.vel = vel;
    }
    
}
