package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelStar extends Label {

    private double cout_estime;

    public LabelStar(Node sommet_courant, double cout_realise, double cout_estime, Arc pere) {
        super(sommet_courant, cout_realise, pere);
        this.cout_estime = cout_estime;
    }

    public double getTotalCost() {
        return this.getCost() + this.cout_estime;
    }

    public double getCoutEstime() {
        return this.cout_estime;
    }

    @Override
    public int compareTo(Label l) {
        int compareTotal = Double.compare(this.getTotalCost(), l.getTotalCost());

        if (compareTotal != 0) {
            return compareTotal;
        } else {
            // en cas d'égalité sur les coûts, on choisit celui qui a l'estimation
            // restante la plus petite
            LabelStar autreLabel = (LabelStar) l;
            return Double.compare(this.getCoutEstime(), autreLabel.getCoutEstime());
        }
    }

}