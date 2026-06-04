package org.insa.graphs.algorithm.shortestpath;

public class DijkstraAlgorithmTest extends ShortestPathAlgorithmTest {
    @Override
    protected ShortestPathAlgorithm createAlgorithm(ShortestPathData data) {
        return new DijkstraAlgorithm(data);
    }
}