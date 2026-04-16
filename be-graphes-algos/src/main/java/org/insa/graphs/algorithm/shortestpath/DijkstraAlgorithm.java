package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.algorithm.shortestpath.Label;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {

        // retrieve data from the input problem (getInputData() is inherited from the
        // parent class ShortestPathAlgorithm)
        final ShortestPathData data = getInputData();

        // variable that will contain the solution of the shortest path problem
        ShortestPathSolution solution = null;

        // TODO: implement the Dijkstra algorithm
        Graph graph = data.getGraph();
        Node origin = data.getOrigin();

        ArrayList<Label> labels = new ArrayList<>();
        for (Node elem : graph.getNodes()) {
            if( origin.equals(elem)){
                labels.add(new Label(elem,false , 0, null));
            }
            else{
                labels.add(new Label(elem,false , Float.MAX_VALUE, null));
            }    
        }
        boolean existe_false = true;
        while()

        for (Label elem : labels) {
            float dist_min = Float.MAX_VALUE ;
            Label sommet_utilise ;
            if(elem.getCost() < dist_min){
                dist_min = elem.getCost() ;
                sommet_utilise = elem ;
            }
            
        }




        // when the algorithm terminates, return the solution that has been found
        return solution;
    }

}
