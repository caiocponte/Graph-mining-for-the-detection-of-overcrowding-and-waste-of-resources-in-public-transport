package br.com.engcon.util;

import java.io.BufferedReader;
import java.io.FileReader;

import br.com.engcon.graph.Edge;
import br.com.engcon.graph.Graph;
import br.com.engcon.graph.Node;

public class Import {
	
	public static Graph loadGraph(String dataset) throws Exception{
		Graph graph = new Graph(dataset);
		
		BufferedReader in = new BufferedReader(new FileReader("./graphs/"+dataset+".txt"));
		
		String line = null;	
		
		String mode = null;		
		
		while(in.ready()){			
			
			line = in.readLine();
			
			if(line.equals("nodes") || line.equals("edges")){
				mode = line;
				continue;
			}
			
			if(mode.equals("nodes")){				
				String nodeTxt[] = line.split(",");
				
				Node n = graph.addNode(nodeTxt[0],new Float(nodeTxt[2]),nodeTxt[1]);
				
				if(nodeTxt.length>3){
					
					for (int i = 3; i < nodeTxt.length; i+=2) {
						n.getOtherAttributes().put(nodeTxt[i], nodeTxt[i+1]);
					}
				}
								
			}else{
				
				String edgeTxt[] = line.split(",");
				
				Node source = graph.getNode(edgeTxt[0]);
				Node target = graph.getNode(edgeTxt[2]);
				
				Float weight = new Float(edgeTxt[1]);
				
				Edge e = graph.addEdge(source, target, weight);
				
				if(edgeTxt.length>3){
					
					for (int i = 3; i < edgeTxt.length; i+=2) {
						e.getOtherAttributes().put(edgeTxt[i], edgeTxt[i+1]);
					}
					
				}
			}
		}	
		
		in.close();
		
		return graph;
	}
}
