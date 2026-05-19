package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

public class Label implements Comparable<Label> {

    private Node sommet_courant;
    private boolean marque;
    private double cout_realise;
    private Arc pere;

    public Label(Node sommet_courant, double cout_realise, Arc pere) {
        this.sommet_courant = sommet_courant;
        this.marque = false;
        this.cout_realise = cout_realise;
        this.pere = pere;
    }

    public double getCost() {
        return this.cout_realise;
    }

    public double getTotalCost() {
        return this.cout_realise;
    }

    public boolean getMarque() {
        return marque;
    }

    public Node getSommetCourant() {
        return sommet_courant;
    }

    public Arc getPere() {
        return pere;
    }

    public void setPere(Arc p, double cout) {
        pere = p;
        cout_realise = cout;
    }

    public void setMarque() {
        marque = true;
    }

    @Override
    public int compareTo(Label l) {
        return Double.compare(this.cout_realise, l.cout_realise);
    }

    /*
     * public int compareTo(Label l){
     * if(this.getTotalCost() > l.getTotalCost()){
     * return 1;
     * }
     * if(this.getTotalCost() < l.getTotalCost()){
     * return -1;
     * }
     * else{
     * return 0;
     * }
     * }
     */
}
