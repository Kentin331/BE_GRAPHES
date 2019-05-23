package org.insa.algo.shortestpath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;

import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.*;


import java.util.ArrayList;
import java.util.List;

import org.insa.graph.Arc;
import org.insa.graph.Node;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;

        Graph graph = data.getGraph();
        int nbNodes = graph.size();
        
        
        // Labels        
        Label list_label[] = new Label[nbNodes]; 
        for(int i = 0; i<nbNodes; i++) list_label[i] = null;
        BinaryHeap<Label> tas_label = new BinaryHeap<Label>();
        
        Boolean test[] = new Boolean[nbNodes]; 
                
        //origin label
		Label first_label = new Label(data.getOrigin());
		list_label[data.getOrigin().getId()] = first_label;
		tas_label.insert(first_label);
		first_label.setmarque();
		first_label.setCout(0);

        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        Boolean end = false;
        Label courant = null;

        
        // While nodes not marked exist
        while(tas_label.isEmpty() == false && end == false) {
        	       	
        	courant = tas_label.deleteMin();
        	courant.setmarque();  
        	notifyNodeMarked(courant.get_smt()); 
        	
        	if(courant.get_smt() == data.getDestination()) end = true;

        	
        	//parcours des successeurs
        	List<Arc> arcs = courant.get_smt().getSuccessors();     	
        	
        	
        	for(Arc arc : arcs) {
        		
        		if(arc.getOrigin() != courant.get_smt()) {
        			continue;
        		}
        		
        		if(list_label[arc.getDestination().getId()]!= null) {        			
        			if(tas_label.array.contains(list_label[arc.getDestination().getId()])==false) {
        				continue;
        			}
        		}
        		
        		
        		
       
        		
        		//chemin empruntable
        		if(data.isAllowed(arc)==false) continue;
        		
        		Node successor = arc.getDestination();
        		Label successor_label = list_label[successor.getId()];
        		
        		
        		
        		
        		//create label if it doesn't exist
        		if (successor_label == null) {
        			successor_label = new Label(successor);
        			list_label[successor_label.get_smt().getId()] = successor_label;
        		}
        		
        		//successor not marked yet
        		if(successor_label.getMarque()==false) {
        			//look for a better cost (if it does exist)
        				if(successor_label.getCost() == Double.POSITIVE_INFINITY) {
        					successor_label.setCout(courant.getCost()+(float) data.getCost(arc));
        					tas_label.insert(successor_label);
        					successor_label.setpere(courant.get_smt());
        					successor_label.setinTas();
        					notifyNodeReached(successor_label.get_smt()); 
        				}
        				else if(successor_label.getCost() > courant.getCost() + data.getCost(arc)) {
        					successor_label.setCout(courant.getCost() + (float)data.getCost(arc));
        					successor_label.setpere(courant.get_smt());
        					
        					//if this label is already in the heap we update it position
        	        		if(successor_label.getinTas() == true) {
        	        			tas_label.remove(successor_label);
        	        		}
        	        		else {
        	        			successor_label.setinTas();        			
        	        		}   
        	        		tas_label.insert(successor_label);   
        	        		notifyNodeReached(successor_label.get_smt()); 
        				} 
        		  }
        		
        	}
         } //Fin WHILE
        
        notifyDestinationReached(data.getDestination());
        
        
        ArrayList<Arc> farc = new ArrayList<Arc>();
        Node Dest = data.getDestination();
        Node node_courante = courant.get_smt();   
        Arc prev_arc = null;
        Node predecesseur = courant.getpere();

        List<Arc> arcs = predecesseur.getSuccessors();
    	for(Arc a : arcs) {
    		if(Dest == a.getDestination() && a.getOrigin() == predecesseur) {
    			farc.add(a);
    			prev_arc = a;
    		}
    	 }
    	
    	System.out.println("Arc " + prev_arc.getOrigin().getId() + "->" + prev_arc.getDestination().getId());
       
        
        /*Node Dest = data.getDestination();
        ArrayList<Arc> farc = new ArrayList<Arc>();
        Node node_courante = null;
        Arc prev_arc = null;
        
        for(Label l : list_label) { // recherche de l'arc reliant la node DEST a son père 
        	if( l != null) {
	        	if(l.get_smt() == Dest) {
	        		for(Node n : graph.getNodes()) {
	                	List<Arc> arcs = n.getSuccessors();
	                	for(Arc a : arcs) {
	                		if(n == l.getpere() && Dest == a.getDestination() && a.getOrigin() == n) {
	                			farc.add(a);
	                			prev_arc = a;
	                			node_courante = n;
	                		}
	                	 }
	                 }
	        	 }
        	}
        	
          }*/
        
 
        
        
        
        while( node_courante.getId()  != data.getOrigin().getId()) { // A partir de l'arc trouvé on remonte jusqu'a la source	
        
        	for(Label l : list_label) {
        		if(l != null) {
		        	if(l.get_smt() == prev_arc.getOrigin()) {
		        		//regarder le pere
		        		for(Node n : graph.getNodes()) {
		                	List<Arc> arcs1 = n.getSuccessors();
		                	for(Arc a : arcs1) {
		                		if(n == l.getpere() && l.get_smt() == a.getDestination() && a.getOrigin() == n) {
		                			farc.add(a);
		                			prev_arc = a;
		                			node_courante = n;
		                		}
		                	}
		                }
		        	}
        		}
        	}
        }
    	
 

 			// Reverse the path
 			Collections.reverse(farc);

 			// Create the final solution.
 			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, farc));

        
        
        return solution;
    }
}
