package br.com.engcon.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Node implements Comparable<Node>{
	
	private String id;
	
	private Float weight;
	
	private String label;
	
	private Integer connectedComponentId;
	
	private List<Edge> edges = new ArrayList<Edge>();
	
	private HashMap<String, Object> otherAttributes = new HashMap<String, Object>();
	
	public Node(){
		
	}
	
	public Node(String id, Float weight) {
		super();
		this.id = id;
		this.weight = weight;
	}
	
	public Node(String id, Float weight, String label) {
		super();
		this.id = id;
		this.weight = weight;
		this.label = label;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Edge> getEdges() {
		return edges;
	}
	
	public List<Edge> getEdgesOut() {
		
		List<Edge> edgesOut = new ArrayList<Edge>();
		
		for (Edge edge : edges) {
			if(edge.getSource().getId().equals(this.getId())){
				edgesOut.add(edge);
			}
		}
		
		return edgesOut;
	}
	
	public List<Edge> getEdgesIn() {
		List<Edge> edgesIn = new ArrayList<Edge>();
		
		for (Edge edge : edges) {
			if(edge.getTarget().getId().equals(this.getId())){
				edgesIn.add(edge);
			}
		}
		
		return edgesIn;
	}

	public HashMap<String, Object> getOtherAttributes() {
		return otherAttributes;
	}

	public void setOtherAttributes(HashMap<String, Object> otherAttributes) {
		this.otherAttributes = otherAttributes;
	}

	public Integer getConnectedComponentId() {
		return connectedComponentId;
	}

	public void setConnectedComponentId(Integer connectedComponentId) {
		this.connectedComponentId = connectedComponentId;
	}

	public List<Node> getNeighbors() {
		
		List<Node> neighbors = new ArrayList<Node>();
		
		for (Edge edge : edges) {
			if(edge.getSource().getId().equals(this.getId())){
				neighbors.add(edge.getTarget());
			}else{
				neighbors.add(edge.getSource());
			}
		}
		
		return neighbors;
	}
	
	public List<Node> getNeighborsOut() {
		
		List<Node> neighbors = new ArrayList<Node>();
		
		for (Edge edge : edges) {
			if(edge.getSource().equals(this))
				neighbors.add(edge.getTarget());
			
		}
		
		return neighbors;
	}
	
	public List<Node> getNeighborsIn() {
		
		List<Node> neighbors = new ArrayList<Node>();
		
		for (Edge edge : edges) {
			if(edge.getTarget().equals(this))
				neighbors.add(edge.getSource());
		}
		
		return neighbors;
	}
	
	public Integer getDegree(){
		return edges.size();
	}

	public void setNeighbors(List<Edge> neighbors) {
		this.edges = neighbors;
	}
	
	public boolean isNeighbor(Node neighbor){
		
		for (Edge a : edges) {
			if(a.getSource().getId().equals(neighbor.getId()) || a.getTarget().getId().equals(neighbor.getId())){
				return true;
			}
		}
		
		return false;
	}
	
	public Edge isSourceOf(Node neighbor){
		for (Edge a : edges) {
			if(a.getSource().getId().equals(this.getId()) && a.getTarget().getId().equals(neighbor.getId())){
				return a;
			}
		}
		
		return null;
	}
	
	public Edge isTargetOf(Node neighbor){
		for (Edge a : edges) {
			if(a.getSource().getId().equals(neighbor.getId()) && a.getTarget().getId().equals(this.getId())){
				return a;
			}
		}
		
		return null;
	}
	
	public Edge getEdgeWith(Node neighbor){
		for (Edge a : edges) {
			if(a.getSource().getId().equals(neighbor.getId()) || a.getTarget().getId().equals(neighbor.getId())){
				return a;
			}
		}
		
		return null;
	}
	
	public void addNeighbor(Edge a){
		this.edges.add(a);
	}
	
	public double getClusteringCoefficient(){	
		
		List<Node> neighbors = this.getNeighbors();
		
		double k = neighbors.size();
		
		if(k == 1)
			return 1;
		
		double y = 0;
		
		for (int i = 0; i < neighbors.size(); i++) {
			for (int j = 0; j < neighbors.size(); j++) {
				if(j > i){
					Node neighbor1 = neighbors.get(i);
					Node neighbor2 = neighbors.get(j);
					if(neighbor1.isNeighbor(neighbor2)){
						y += 1;
					}
				}			
			}
		}
		
		double coefficient = (2 * y) / (k*(k-1));
		
		return coefficient;
	}
	
	@Override
	public boolean equals(Object obj) {		
		return this.id.equals(((Node)obj).getId());
	}
	
	@Override
	public String toString() {		
		return id;
	}

	@Override
	public int compareTo(Node o) {		
		return getDegree().compareTo(o.getDegree());
	}
	
}
