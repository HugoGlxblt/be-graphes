package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.insa.graphs.algorithm.ArcInspector;
import org.junit.Test;

public class AStarAlgorithmTest extends ShortestPathAlgorithmTest {
    @Override
    protected ShortestPathAlgorithm createAlgorithm(ShortestPathData data) {
        return new AStarAlgorithm(data);
    }

    @Test
    public void testAStarDijkstra() {
        for (ArcInspector filter : filters) {
            ShortestPathData data = new ShortestPathData(smallGraph, graphNodes[0], graphNodes[3], filter);
            ShortestPathSolution solDijkstra = new DijkstraAlgorithm(data).doRun();
            ShortestPathSolution solAStar = new AStarAlgorithm(data).doRun();

            assertTrue("Dijkstra doit trouver une solution", solDijkstra.isFeasible());
            assertTrue("A* doit trouver une solution", solAStar.isFeasible());

            double coutDijkstra = pathCost(solDijkstra);
                    
            double coutAStar = pathCost(solAStar);

            assertEquals("A* et Dijkstra doivent trouver le même coût sur le graphe",
                    coutDijkstra, coutAStar, 1e-6);
        }
    }
}