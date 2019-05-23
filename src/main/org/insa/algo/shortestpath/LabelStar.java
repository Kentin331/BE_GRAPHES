package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Iterator;
import org.insa.graph.Node;
import org.insa.graph.Point;

public class LabelStar extends Label{
	
	private Double cout_des;
	private Node dest;
	
	public LabelStar(Node s, Node d) {
		super(s,d);
		this.dest = d;
		this.cout_des = Double.POSITIVE_INFINITY;
	}
	
	public Double get_cout_dest() {		
		return(Point.distance(this.get_smt().getPoint(), this.dest.getPoint()));
	}
	
	public Double get_total_cost() {
		return(this.getCost() + this.get_cout_dest());
		
	}
	

}
