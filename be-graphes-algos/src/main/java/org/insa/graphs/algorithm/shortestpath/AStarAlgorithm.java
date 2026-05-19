package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.algorithm.shortestpath.LabelStar;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    @Override
    public ArrayList<Label> ajouterLabel(ArrayList<Label> labels, Node successeur, Arc pere, Node destination){
        labels.add(new LabelStar(successeur , 
                                false , 
                                Float.MAX_VALUE, 
                                (float) Point.distance(successeur.getPoint(), destination.getPoint()),
                                pere));
        return labels;
    }
}
