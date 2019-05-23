package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Iterator;
import org.insa.graph.Node;

public class Label implements Comparable<Label>{
	
	private Node sommet_courant;
	private boolean marque;
	private Double cout;
	private Node pere;
	private Node noeud;
	private boolean inTas;
	
	
	public Label(Node s, Node d) {
		this.sommet_courant = s;
		this.marque = false;
		this.cout = Double.POSITIVE_INFINITY;
		this.pere = null;
		this.inTas = false;
	}
	
	public Double getCost() {
		return(this.cout);
	}
	@Override
	public int compareTo(Label autre) {
		return(Double.compare(this.getCost(), autre.getCost()));
	}
	
	public Node get_smt() {
		return this.sommet_courant;
	}
	
	public boolean getMarque() {
		return this.marque;
	}
	
	public Node getpere() {
		return this.pere;
	}
	
	public boolean getinTas() {
		return this.inTas;
	}
	
	
	public void setmarque() {
		this.marque = true;
	}
	
	public void setCout(double cout) {
		this.cout = cout;
	}
	
	public void setpere(Node pere) {
		this.pere = pere;
	}
	
	public void setinTas() {
		this.inTas = true;
	}
	
	public Double get_total_cost() {
		return(this.cout);
	}
	
}