package br.com.engcon.util;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import br.com.engcon.graph.Edge;
import br.com.engcon.graph.Graph;
import br.com.engcon.graph.Node;

public class Exportation {

	public static void exportTxt(Graph graph) throws Exception{
		DataOutputStream graphTxt = new DataOutputStream(new FileOutputStream("./graphs/"+graph.getDatasetName()+".txt"));
		
		List<Node> vertices = graph.getNodeList();
		
		graphTxt.writeBytes("nodes");		
		
		for (Node vertice : vertices) {
			
			graphTxt.writeBytes("\n");
			
			graphTxt.writeBytes(vertice.getId()+","+vertice.getLabel()+","+vertice.getWeight());
			
			Set<String> attributes = vertice.getOtherAttributes().keySet();
			
			for (String key : attributes) {
				graphTxt.writeBytes(","+key+","+vertice.getOtherAttributes().get(key));
			}
			
		}
		
		graphTxt.writeBytes("\n");
		
		graphTxt.writeBytes("edges");	
		
		List<Edge> edges = graph.getEdges();
		
		for (Edge edge : edges) {
			
			graphTxt.writeBytes("\n");
			
			graphTxt.writeBytes(edge.getSource().getId()+","+edge.getWeight()+","+edge.getTarget().getId());
			
			Set<String> attributes = edge.getOtherAttributes().keySet();
			
			for (String key : attributes) {
				graphTxt.writeBytes(","+key+","+edge.getOtherAttributes().get(key));
			}
		}
		
		graphTxt.close();
	}
	
	public static void exportGephi(Graph graph) throws Exception{
		List<Node> nodes = graph.getNodeList();
		
		DataOutputStream dos = new DataOutputStream(new FileOutputStream("./graphs/"+graph.getDatasetName()+".graphml"));
				
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">\n"+
								"\t<key attr.name=\"weight\" attr.type=\"double\" for=\"edge\" id=\"d0\" />\n" + 
								"<key attr.name=\"lng\" attr.type=\"double\" for=\"node\" id=\"d2\" />\n" +
								"<key attr.name=\"lat\" attr.type=\"double\" for=\"node\" id=\"d1\" />";
								
		
		xml+="\t<graph edgedefault=\"directed\" id=\"\">\n";
		
		
		dos.writeBytes(xml);
		for (Node node : nodes) {
			xml = "\t\t<node id=\""+node.getId()+"\">\n"+
					"\t\t\t\t<data key=\"d1\">" + (String) node.getOtherAttributes().get("latitude") + "</data>\n" + 
					"\t\t\t\t<data key=\"d2\">" + (String) node.getOtherAttributes().get("longitude") + "</data>" +
					"\t\t</node>\n";
			dos.writeBytes(xml);
		}
		
		
		Collection<Edge> edges = graph.getEdges();		
			
		for (Edge edge : edges) {
			xml = "\t\t<edge source=\""+edge.getSource().getId()+"\" target=\""+edge.getTarget().getId()+"\">\n"+
							"	\t\t\t<data key=\"d0\">"+edge.getWeight()+"</data>\n"+
							"\t\t</edge>\n";
			dos.writeBytes(xml);
			
		}
		
		xml="\t</graph>\n</graphml>";
		
		dos.writeBytes(xml);
		dos.flush();
		dos.close();
	}
	
}
