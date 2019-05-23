package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Iterator;

import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {
	

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected Label[] initLabel(int Nbnode, Node Origin, Node Dest) {
    	LabelStar lbs[] = new LabelStar[Nbnode];
    	for(int i=0; i < Nbnode; i++) lbs[i]=null;
    	
    	LabelStar first_label = new LabelStar(Origin, Dest);
		lbs[Origin.getId()] = first_label;
		this.tas_label.insert(first_label);
		first_label.setmarque();
		first_label.setCout(0);
    	
    	return(lbs);
    }

    protected Label creer_label(Node n, Node d) {
    	LabelStar lb = new LabelStar(n,d);
    	return(lb);
    }
}
