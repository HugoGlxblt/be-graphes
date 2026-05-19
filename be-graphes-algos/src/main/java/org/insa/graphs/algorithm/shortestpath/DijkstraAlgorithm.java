package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

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

    public ArrayList<Label> ajouterLabel(ArrayList<Label> labels, Node successeur, Arc pere, Node destination) {
        int index = successeur.getId();
        Label elem = new Label(successeur, false, Float.MAX_VALUE, pere);
        labels.remove(index);
        labels.add(index, elem);
        return labels;
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
        ArrayList<Label> labels = new ArrayList<>();
        BinaryHeap<Label> tas = new BinaryHeap<>();
        Node elem;
        for (int i = 0; i < graph.getNodes().size(); i++) {
            elem = graph.get(i);
            if (origin.equals(elem)) {
                Label lab_origine = new Label(elem, false, 0, null);
                tas.insert(lab_origine);
                labels.add(lab_origine);
            } else {
                labels.add(null);
            }
        }

        // Iterations
        boolean sommet_atteint = false;
        while (!tas.isEmpty() && !sommet_atteint) {
            Label x = tas.deleteMin();
            x.setMarque(true);
            if (x.getSommetCourant().getId() == destination.getId()) {
                sommet_atteint = true;
            }
            for (Arc arc_succeseur : x.getSommetCourant().getSuccessors()) {
                if (!data.isAllowed(arc_succeseur)) {
                    continue;
                }
                Node successeur = arc_succeseur.getDestination();
                Label it_label = labels.get(successeur.getId());
                if (it_label == null) {
                    labels = ajouterLabel(labels, successeur, arc_succeseur, destination);
                    it_label = labels.get(successeur.getId());
                }
                else                if (it_label.getMarque()) {
                    continue;
                }

                else                 if (it_label.getTotalCost() > x.getTotalCost() + arc_succeseur.getLength()) {
                    tas.remove(it_label);                 
                    it_label.setCost(x.getTotalCost() + arc_succeseur.getLength());
                    it_label.setPere(arc_succeseur);
                    notifyNodeReached(arc_succeseur.getDestination());
                    tas.insert(it_label);
                }
            }
    }

        // Solution Construction
        Arc arc = labels.get(destination.getId()).getPere();
        ArrayList<Arc> arcs = new ArrayList<>();while(arc!=null)
        {
            arcs.add(arc);
            arc = labels.get(arc.getOrigin().getId()).getPere();
        }
        // Reverse the path...
        Collections.reverse(arcs);
        // Create the final solution.
        solution=new ShortestPathSolution(data,Status.OPTIMAL,new Path(graph,arcs));
        // when the algorithm terminates, return the solution that has been found

        return solution;
    }

}
