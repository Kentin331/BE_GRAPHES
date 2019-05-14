package org.insa.algo.shortestpath;
import java.util.ArrayList;
import org.insa.graph.Node;

public class label{
	private int sommet_courant;
	private boolean marque;
	private int cout;
	private int pere;
	private Node noeud;
	
	public label(int s, boolean m, int c, int p) {
		this.sommet_courant=s;
		this.marque=m;
		this.cout=c;
		this.pere=p;
	}
	public int getCost() {
		return (this.cout);
	}
	public int getCurrentNode() {
		return (this.sommet_courant);
	}
}
