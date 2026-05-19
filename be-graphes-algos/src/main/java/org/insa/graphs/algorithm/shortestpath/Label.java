package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

public class Label implements Comparable<Label>{

    private Node sommet_courant ;
    private boolean marque ;
    private float cout_realise ;
    private Arc pere ;

    public Label(Node sommet_courant, boolean marque, float cout_realise, Arc pere) {
        this.sommet_courant = sommet_courant ;
        this.marque = marque ;
        this.cout_realise = cout_realise ;
        this.pere = pere ;
    }

    public float getCost() {
        return this.cout_realise ;
    }

    public float getTotalCost() {
        return this.cout_realise ;
    }

    public boolean getMarque(){return marque;}

    public Node getSommetCourant(){return sommet_courant;}

    public Arc getPere(){return pere;}

    public void setCost(float cout){cout_realise = cout;}

    public void setMarque(boolean m){marque = m;}

    public void setPere(Arc p){pere = p;}

    public void setSommet(Node s){sommet_courant = s;}

    @Override
    public int compareTo(Label l){
        if(this.cout_realise > l.getTotalCost()){
            return 1;
        }
        if(this.cout_realise < l.getTotalCost()){
            return -1;
        }
        else{
            return 0;
        }
    }

    /*public int compareTo(Label l){
        if(this.getTotalCost() > l.getTotalCost()){
            return 1;
        }
        if(this.getTotalCost() < l.getTotalCost()){
            return -1;
        }
        else{
            return 0;
        }
    }*/
}
