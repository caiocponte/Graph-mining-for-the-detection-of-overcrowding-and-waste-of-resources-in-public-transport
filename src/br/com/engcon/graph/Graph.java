package br.com.engcon.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Graph implements Cloneable {
	
	protected String dataSetName;
	
	protected HashMap<String, Node> nodeHash = new HashMap<String, Node>();
	
	protected List<Edge> edges = new ArrayList<Edge>();
	
	public Graph(String datasetName) {
		super();
		this.dataSetName = datasetName;
	}

	public Node addNode(String id, Float weight, String label){
		
		Node v = nodeHash.get(id);
		
		if(v == null){
			
			v = new Node(id,weight,label);
		
			this.nodeHash.put(id, v);
			
		}
		
		return v;
	}
	
	public Edge addEdge(Node source, Node target, Float weight){		
		
		Edge a = new Edge(source, target, weight);
		
		source.addNeighbor(a);
		
		target.addNeighbor(a);
		
		edges.add(a);		
		
		return a;
		
	}
	
	public Node removeNode(String id) {
		
		return nodeHash.remove(id);
		
	}
	
	public Edge removeEdge(Node source, Node target){		
		
		for (Edge e : edges) {
			
			if(e.getTarget().getId().equals(target.getId()) && e.getSource().getId().equals(source.getId())){
				
				edges.remove(e);
				
				return e;
			}
		}
		
		return null;
	}
	
	public Integer size(){
		return new Integer(getNodeList().size());
	}
	
	public Node getHub(){
		List<Node> nodes = this.getNodeList();
		
		Collections.sort(nodes);
		Collections.reverse(nodes);
		
		return nodes.get(0);		
	}
	
	public String getDatasetName() {
		return dataSetName;
	}

	public void setDatasetName(String datasetName) {
		this.dataSetName = datasetName;
	}
	
	public List<Node> getNodeList(){
		List<Node> lista = new ArrayList<Node>();
		
		Set<String> chaves =  nodeHash.keySet();
		
		for (String chave : chaves) {
			Node v = nodeHash.get(chave);
			
			lista.add(v);
		}
		
		return lista;
	}
	
	public Node getNode(String id){
		return nodeHash.get(id);
	}
	
	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
	public float getClusteringCoefficient(){	
		
		Collection<Node> nodes = this.getNodeList();
				
		float coefficient = 0.0f;
		
		float N = nodes.size();		
		
		for (Node node : nodes) {
			coefficient += node.getClusteringCoefficient();
		}
		
		return coefficient / N ;
	}
	
	public float getDegreeCentrality(){
		Node hub = this.getHub();
		
		int hubDegree = hub.getDegree();
		
		List<Node> nodes = this.getNodeList();
		
		float sum = 0.0f;
		
		float N = nodes.size();		
		
		for (Node node : nodes) {
			if(!node.getId().equals(hub.getId())){
				sum += ((double)hubDegree/(N - 1)) - ((double)node.getDegree()/(N - 1));
			}
		}
		
		return sum/(N - 2);
	}
	
	public float getDensity(){
		float edgesCount = edges.size();
		float nodesCount = nodeHash.size();
		float edgesCompleteGraph = (nodesCount*(nodesCount-1))/2;
		
		return edgesCount/edgesCompleteGraph;
	}
	
	public float getEntropy(){
		List<Node> nodes = this.getNodeList();
		Collections.sort(nodes);
		
		HashMap<Integer, Float> probabilities = new HashMap<Integer, Float>();
		
		for (Node node : nodes) {
			Integer degree = node.getDegree();
			if(!probabilities.containsKey(degree)){
				float p = 0.0f;
				for (Node node2 : nodes) {
					if(node2.getDegree().equals(degree)){
						p++;
					}
				}
				p = (p/nodes.size());
				probabilities.put(degree, p);
				
			}
			
		}
		
		Set<Integer> keySets = probabilities.keySet();
		
		float sum = 0.0f;
		
		for (Integer degree : keySets) {
			float p = probabilities.get(degree);
			sum += - (p * (Math.log(p)/Math.log(2)));
		}
		
		return sum;
	}
	
	public Integer shortPath(Node va, Node vb){
		int shortPathLenght = 0;		
		
		if(va.equals(vb)){
			return shortPathLenght;
		}
		
		Edge e = va.getEdgeWith(vb);
		
		if(e!=null && e.getSource().equals(va)){
			shortPathLenght = 1;
			return shortPathLenght;
		}
		
		shortPathLenght = 2;
		
		List<Node> alreadyTested = new ArrayList<Node>();
		alreadyTested.add(va);
		alreadyTested.add(vb);
		
		List<Node> toTest = va.getNeighborsOut();	
		
		List<Node> toTestAux = new ArrayList<Node>();
		
		while(alreadyTested.size() < size() && toTest.size() != 0){			
			
			for (Node nodeSource : toTest) {			
				
				e = nodeSource.getEdgeWith(vb);
				
				if(e!=null && e.getSource().equals(nodeSource)){
					return shortPathLenght;
				}
				
				if(!alreadyTested.contains(nodeSource)){
					alreadyTested.add(nodeSource);
				}
				
				List<Node> nodesNextLevel =  nodeSource.getNeighborsOut();
				
				for (Node vProx : nodesNextLevel) {
					if(!alreadyTested.contains(vProx) && !toTestAux.contains(vProx)){
						toTestAux.add(vProx);
					}
				}				
				
			}
			
			toTest = toTestAux;
			
			toTestAux = new ArrayList<Node>();
			
			shortPathLenght++;
			
		}
		
		
		return Integer.MAX_VALUE;
		
	}
	
	public Integer getDiameter(){
		
		int diameter = 0;
		
		List<Node> vertices = (List<Node>) getNodeList();
		
		for (int i = 0; i < vertices.size(); i++) {
			Node va = vertices.get(i);
			for (int j = 0; j < vertices.size(); j++){				
				Node vb = vertices.get(j);				
				if(i < j){					
					Integer menor = shortPath(va, vb);
					
					if(!menor.equals(Integer.MAX_VALUE)){					
						diameter = Math.max(diameter, menor);
						System.out.println(i+","+j+"="+diameter+"("+menor+")");
					}	
				}	
			}
			
		}
		
		return diameter;
	}
	
	
	@Override
    public Graph clone() throws CloneNotSupportedException {
        return (Graph) super.clone();
    }
}