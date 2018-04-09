package br.com.engcon.graph;

import java.util.HashMap;

public class Edge implements Comparable<Edge>{
	
	private Node source;
	
	private Node target;
	
	private Float weight;
	
	private HashMap<String, Object> otherAttributes = new HashMap<String, Object>();

	public Edge(){
		
	}
	
	public Edge(Node source, Node target, Float weight) {
		super();
		this.source = source;
		this.target = target;
		this.weight = weight;
	}

	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		this.source = source;
	}

	public Node getTarget() {
		return target;
	}

	public void setTarget(Node target) {
		this.target = target;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public HashMap<String, Object> getOtherAttributes() {
		return otherAttributes;
	}

	public void setOtherAttributes(HashMap<String, Object> otherAttributes) {
		this.otherAttributes = otherAttributes;
	}

	@Override
	public int compareTo(Edge o) {
		return getWeight().compareTo(o.getWeight());
	}
	
	@Override
	public boolean equals(Object obj) {
		Edge e = (Edge) obj;
		
		if(e.source.getId().equals(this.source.getId()) &&
		   e.target.getId().equals(this.target.getId()) &&
		   e.weight==this.weight){
				return true;			
		} else {
			return false;
		}
	}
	
	
}
