package br.com.engcon.mining;

import java.util.List;

import br.com.engcon.graph.Edge;
import br.com.engcon.graph.Graph;
import br.com.engcon.graph.Node;
import br.com.engcon.util.Exportation;
import br.com.engcon.util.Import;

public class IdentifyBottleneckNetwork {
	
	public static float IS = 0, ID = 0;

	public static void main(String[] args) throws Exception {
		
		Graph suply = Import.loadGraph("oferta-full-norm-mcc-modularity");
		
		Graph demand = Import.loadGraph("demanda-full-norm-mcc-modularity");
		
		Graph suplyDemand = calculateWeightGraph(suply, demand, "network-suply-demand", IS);
		Graph demandSuply = calculateWeightGraph(demand, suply, "network-demand-suply", ID);
		
		Exportation.exportTxt(suplyDemand);
		Exportation.exportGephi(suplyDemand);
		
		Exportation.exportTxt(demandSuply);
		Exportation.exportGephi(demandSuply);

	}
	
	public static Graph calculateWeightGraph(Graph G1, Graph G2, String name, float I) {
		
		Graph G = new Graph(name);
		List<Edge> edgesG1 = G1.getEdges();
		
		for (Edge edgeG1 : edgesG1) {
			Node n1 = G2.getNode(edgeG1.getSource().getId());
			Node n2 = G2.getNode(edgeG1.getTarget().getId());
			Edge edgeG2 = n1.isSourceOf(n2);
			if(edgeG2 != null) {
				
				Float weightG1 = edgeG1.getWeight();
				Float weightG2 = edgeG2.getWeight();
				Float weight = weightG1 - weightG2;
				
				Node node1 = G.addNode(n1.getId(), 1.0F, "p");
				node1.getOtherAttributes().put("latitude", n1.getOtherAttributes().get("latitude"));
				node1.getOtherAttributes().put("longitude", n1.getOtherAttributes().get("longitude"));
				
				Node node2 = G.addNode(n2.getId(), 1.0F, "p");				
				node2.getOtherAttributes().put("latitude", n2.getOtherAttributes().get("latitude"));
				node2.getOtherAttributes().put("longitude", n2.getOtherAttributes().get("longitude"));
				
				if(weight < I) {
					G.addEdge(node1, node2, weight);
				}
			}
		}
		
		return G;		
	}
}