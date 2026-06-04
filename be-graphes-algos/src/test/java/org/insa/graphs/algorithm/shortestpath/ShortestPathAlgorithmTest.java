//Classe de tests pour dijkstra et a star
package org.insa.graphs.algorithm.shortestpath;
 
import static org.junit.Assert.*;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.model.*;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;
 
public abstract class ShortestPathAlgorithmTest {
 
    protected static Graph smallGraph;
    protected static Node[] graphNodes;
    protected static List<ArcInspector> filters;
    private static final double EPSILON = 1e-6; //tolérance comparaison entre nombres réels

    @BeforeClass //exécutée une seule fois pour tout les tests
    public static void initAll() throws Exception {
        filters = ArcInspectorFactory.getAllFilters();
        buildSmallGraph();
    }
 
    private static void buildSmallGraph() {
        graphNodes = new Node[6];
        graphNodes[0] = new Node(0, new Point(1.00000f, 43.00000f));
        graphNodes[1] = new Node(1, new Point(1.00001f, 43.00000f));
        graphNodes[2] = new Node(2, new Point(1.00000f, 43.00001f));
        graphNodes[3] = new Node(3, new Point(1.00002f, 43.00002f));
        graphNodes[4] = new Node(4, new Point(1.00001f, 43.00001f));
        graphNodes[5] = new Node(5, new Point(1.00010f, 43.00010f));
        RoadInformation ri = new RoadInformation(
                RoadType.PRIMARY, new AccessRestrictions(), true, 130, "test road");
 
        Node.linkNodes(graphNodes[0], graphNodes[1], 10f, ri, new ArrayList<>());
        Node.linkNodes(graphNodes[0], graphNodes[2],  5f, ri, new ArrayList<>());
        Node.linkNodes(graphNodes[1], graphNodes[3], 20f, ri, new ArrayList<>());
        Node.linkNodes(graphNodes[1], graphNodes[4],  5f, ri, new ArrayList<>());
        Node.linkNodes(graphNodes[2], graphNodes[4],  8f, ri, new ArrayList<>());
        Node.linkNodes(graphNodes[4], graphNodes[3], 15f, ri, new ArrayList<>());
 
        smallGraph = new Graph("SMALL", "Graphe de test", Arrays.asList(graphNodes), null);
    }
 
    // on fait une méthode abstraite pour pouvoir effectuer les tets avec dijsktra et a star
    protected abstract ShortestPathAlgorithm createAlgorithm(ShortestPathData data);
 
    //execute l'algo séléctionné
    private ShortestPathSolution run(Graph graph, Node origin, Node dest, ArcInspector filter) {
        ShortestPathData data = new ShortestPathData(graph, origin, dest, filter);
        return createAlgorithm(data).doRun();
    }
 
    private double pathCost(ShortestPathSolution sol) {
        if (sol.getPath() == null) return 0;
        double cost = 0;
        for (Arc arc : sol.getPath().getArcs()) {
            cost += sol.getInputData().getCost(arc);
        }
        return cost;
    }
 
    // calcule la référence via bellman ford
    private double bellmanCost(Graph graph, Node origin, Node dest, ArcInspector filter) {
        ShortestPathData data = new ShortestPathData(graph, origin, dest, filter);
        ShortestPathSolution ref = new BellmanFordAlgorithm(data).doRun();
        assertTrue("Bellman-Ford devrait trouver une solution", ref.isFeasible());
        return pathCost(ref);
    }
 
    //scénarios de test


     //Chemin de longueur nulle (Origine == Destination)
    @Test
    public void testCheminLongueurNulle() {
        for (ArcInspector filter : filters) {
            ShortestPathSolution sol = run(smallGraph, graphNodes[0], graphNodes[0], filter);
            assertTrue("Le statut doit être FEASIBLE lorsque Origine == Destination", sol.isFeasible());
            assertEquals("Le coût doit être strictement égal à 0", 0.0, pathCost(sol), EPSILON);
        }
    }
 
    // chemin inexistant

    @Test
    public void testCheminInexistant() {

        ShortestPathSolution solToIsolated = run(smallGraph, graphNodes[0], graphNodes[5], filters.get(0));
        assertFalse("Le chemin ne doit pas être faisable", solToIsolated.isFeasible());
        assertEquals(Status.INFEASIBLE, solToIsolated.getStatus());

        ShortestPathSolution solFromIsolated = run(smallGraph, graphNodes[5], graphNodes[0], filters.get(0));
        assertFalse("Le chemin ne doit pas être faisable", solFromIsolated.isFeasible());
    }
 
     //Coût de l'algorithme
    @Test
    public void testValiditeEtCoherenceCout() {
        //test cohérence cout algo en distance
        ShortestPathSolution solDist = run(smallGraph, graphNodes[0], graphNodes[3], filters.get(0));
        assertTrue(solDist.isFeasible());
        assertTrue("Le chemin doit être valide", solDist.getPath().isValid());
        assertEquals("Le coût donné par l'algo doit être égal à Path.getLength()", 
                solDist.getPath().getLength(), pathCost(solDist), EPSILON);
        assertTrue("Le coût calculé doit être positif", pathCost(solDist) >= 0);
 
        //test cohérence cout algo en temps
        ShortestPathSolution solTime = run(smallGraph, graphNodes[0], graphNodes[3], filters.get(2));
        assertTrue(solTime.isFeasible());
        assertEquals("Le coût en temps doit être égal à Path.getMinimumTravelTime()", 
                solTime.getPath().getMinimumTravelTime(), pathCost(solTime), EPSILON);
    }
 
    // même résultat que bellman ford
    @Test
    public void testTrajetCourtVsBellmanFord() {
        for (ArcInspector filter : filters) {
            ShortestPathSolution sol = run(smallGraph, graphNodes[0], graphNodes[4], filter);
            assertTrue(sol.isFeasible());
            double expectedCost = bellmanCost(smallGraph, graphNodes[0], graphNodes[4], filter);
            assertEquals("Le coût doit être identique à Bellman-Ford", expectedCost, pathCost(sol), EPSILON);
        }
    }
    // même résultat que bellman ford sur trajet long
    @Test
    public void testTrajetLongVsBellmanFord() {
        for (ArcInspector filter : filters) {
            ShortestPathSolution sol = run(smallGraph, graphNodes[0], graphNodes[3], filter);
            assertTrue(sol.isFeasible());
            double expectedCost = bellmanCost(smallGraph, graphNodes[0], graphNodes[3], filter);
            assertEquals("Le coût doit être identique à Bellman-Ford)", expectedCost, pathCost(sol), EPSILON);
        }
    }
}