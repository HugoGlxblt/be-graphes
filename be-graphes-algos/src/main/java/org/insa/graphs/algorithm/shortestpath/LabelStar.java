package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;
public class LabelStar extends Label {
    
    private double cout_estime ;

    public LabelStar(Node sommet_courant, double cout_realise, double cout_estime, Arc pere) {
        super(sommet_courant, cout_realise, pere) ;
        this.cout_estime = cout_estime ;
    }

    public double getTotalCost() {
        return this.getCost() + this.cout_estime ;
    }

    // public void maj_coutEstime(Node destNode){
    //     this.cout_estime = Point.distance(this.getSommetCourant().getPoint(), destNode.getPoint());
    // }

    @Override
    public int compareTo(Label l){
        return Double.compare(this.getTotalCost(), l.getTotalCost());
    }

}