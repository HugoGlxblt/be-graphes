package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    public Label createLabel(Node successeur, Arc pere, double cost) {
        return new Label(successeur, cost, pere);
    }

    @Override
    public ShortestPathSolution doRun() {

        // retrieve data from the input problem (getInputData() is inherited from the
        // parent class ShortestPathAlgorithm)
        final ShortestPathData data = getInputData();

        // variable that will contain the solution of the shortest path problem
        ShortestPathSolution solution = null;

        // implement the Dijkstra algorithm
        Graph graph = data.getGraph();
        Node origin = data.getOrigin();
        Node destination = data.getDestination();
        // Initialisation
        ArrayList<Label> labels = Arrays.stream(new Label[graph.getNodes().size()])
                .collect(Collectors.toCollection(ArrayList::new));
        BinaryHeap<Label> tas = new BinaryHeap<>();
        Label originLabel = createLabel(origin, null, 0);
        labels.set(origin.getId(), originLabel);
        tas.insert(originLabel);
        // Iterations
        boolean sommet_atteint = false;
        while (!tas.isEmpty() && !sommet_atteint) {
            Label x = tas.deleteMin();
            x.setMarque();
            notifyNodeMarked(x.getSommetCourant());
            if (x.getSommetCourant().getId() == destination.getId()) {
                sommet_atteint = true;
                continue;
            }
            for (Arc arc_successeur : x.getSommetCourant().getSuccessors()) {
                if (!data.isAllowed(arc_successeur)) {
                    continue;
                }
                Node successeur = arc_successeur.getDestination();
                Label it_label = labels.get(successeur.getId());

                double cout_arc = data.getCost(arc_successeur);

                if (it_label == null) {
                    it_label = createLabel(successeur, arc_successeur, x.getCost() + cout_arc);
                    labels.set(successeur.getId(), it_label);
                    tas.insert(it_label);
                    notifyNodeReached(arc_successeur.getDestination());
                } else if (it_label.getMarque()) {
                    continue;
                } else if (it_label.getCost() > x.getCost() + cout_arc) {
                    tas.remove(it_label);
                    it_label.setPere(arc_successeur, x.getCost() + cout_arc);
                    tas.insert(it_label);
                }
            }
        }
        // cas ou la solution n'est pas faisable
        Label destLabel = labels.get(destination.getId());
        if (destLabel == null || !destLabel.getMarque()) {
            return new ShortestPathSolution(data, Status.INFEASIBLE);
        }

        // Solution Construction
        Arc arc = labels.get(destination.getId()).getPere();
        ArrayList<Arc> arcs = new ArrayList<>();
        while (arc != null) {
            arcs.add(arc);
            arc = labels.get(arc.getOrigin().getId()).getPere();
        }
        // Reverse the path...
        Collections.reverse(arcs);
        // Create the final solution.
        if (arcs.isEmpty()) {
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, origin));
        } else {
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        // when the algorithm terminates, return the solution that has been found

        return solution;
    }

}
