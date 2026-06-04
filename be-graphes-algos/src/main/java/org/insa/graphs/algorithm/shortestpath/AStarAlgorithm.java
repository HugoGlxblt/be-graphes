package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    public Label createLabel(Node successeur, Arc pere, double cost) {

        final ShortestPathData data = getInputData();
        final Node destination = data.getDestination();

        double distance = Point.distance(successeur.getPoint(), destination.getPoint());
        double coutEstime = 0.0;

        if (data.getMode() == Mode.LENGTH) {
            coutEstime = distance;
        } else if (data.getMode() == Mode.TIME) {
            int vitesseMax = 130;
            if (data.getGraph().getGraphInformation() != null) {
                int mapSpeed = data.getGraph().getGraphInformation().getMaximumSpeed();
                if (mapSpeed != -1) {
                    vitesseMax = mapSpeed;
                }
            }
            double vitesseEnMetresParSeconde = vitesseMax / 3.6;
            coutEstime = distance / vitesseEnMetresParSeconde;
        }
        return new LabelStar(successeur, cost, coutEstime, pere);
    }
}