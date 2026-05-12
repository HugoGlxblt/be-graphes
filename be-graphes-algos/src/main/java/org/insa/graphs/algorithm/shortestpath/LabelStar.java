package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;
public class LabelStar extends Label {
    
    private float cout_estime ;

    public LabelStar(Node sommet_courant, boolean marque, float cout_realise, float cout_estime, Arc pere) {
        super(sommet_courant, marque, cout_realise, pere) ;
        this.cout_estime = cout_estime ;
    }

    public float getTotalCost() {
        return this.getCost() + this.cout_estime ;
    }

    public void maj_coutEstime(Node destNode){
        this.cout_estime = (float) Point.distance(this.getSommetCourant().getPoint(), destNode.getPoint());
    }





}