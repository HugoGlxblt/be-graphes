package org.insa.graphs.gui.simple;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
//import org.kxml2.kdom.Node;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;
public class Launch {

    /**
     * Create a new Drawing inside a JFrame an return it.
     *
     * @return The created drawing.
     * @throws Exception if something wrong happens when creating the graph.
     */
    public static Drawing createDrawing() throws Exception {
        BasicDrawing basicDrawing = new BasicDrawing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("BE Graphes - Launch");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setContentPane(basicDrawing);
                frame.validate();
            }
        });
        return basicDrawing;
    }

    public static void main(String[] args) throws Exception {

        // visit these directory to see the list of available files on commetud.
        final String mapName =
                "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
        final String pathName =
                "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";

        final Graph graph;
        final Path path;

        // create a graph reader
        try (final GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream(mapName))))) {

            // read the graph
            graph = reader.read();
        }

        // create the drawing
        final Drawing drawing = createDrawing();

        // draw the graph on the drawing
        drawing.drawGraph(graph);

        // create a path reader
        try (final PathReader pathReader = new BinaryPathReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream(pathName))))) {

            // TODO: read the path
            path = pathReader.readPath(graph);
        }
        Node origin = graph.get(0);
        Node destination= graph.get(10);
        for( int i = 0; i<4; i++){
            // draw the path on the drawing
            drawing.drawPath(path);
            ShortestPathData data = new ShortestPathData(graph, origin, destination, ArcInspectorFactory.getAllFilters().get(i));
            DijkstraAlgorithm da = new DijkstraAlgorithm(data);
            BellmanFordAlgorithm bfa = new BellmanFordAlgorithm(data);
            ShortestPathSolution solution_bfa = bfa.doRun();
            ShortestPathSolution solution_da = da.doRun();
            double cost_bfa = 0, cost_da = 0;
            for (Arc arc : solution_bfa.getPath().getArcs()) {
                cost_bfa += solution_bfa.getInputData().getCost(arc);
            }
            for (Arc arc : solution_da.getPath().getArcs()) {
                cost_da += solution_da.getInputData().getCost(arc);
            }
            assert(Math.abs(cost_bfa-cost_da)<1e-9);
        }



    }

}
