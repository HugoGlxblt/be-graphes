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
    public Label createLabel(Node successeur, Arc pere, double cost) {
        final var destination = getInputData().getDestination();
        return new LabelStar(successeur,
                cost,
                Point.distance(successeur.getPoint(), destination.getPoint()),
                pere);
    }
}
