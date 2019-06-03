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
	
	protected BinaryHeap<Label> tas_label;

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        this.tas_label = new BinaryHeap<Label>();
    }
    
    protected Label[] initLabel(int nbNode, Node Origin,Node Dest) {
    	Label list_label[] = new Label[nbNode]; 
        for(int i = 0; i<nbNode; i++) list_label[i] = null; 
        
        Label first_label = new Label(Origin,Dest);
		list_label[Origin.getId()] = first_label;
		this.tas_label.insert(first_label);
		first_label.setmarque();
		first_label.setCout(0);
        
        return(list_label);
    }
    
    protected Label creer_label(Node n, Node d) {
    	Label lb = new Label(n,d);
    	return(lb);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	int it = 0;
    	
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;

        Graph graph = data.getGraph();
        int nbNodes = graph.size();
        
        Node Origin = data.getOrigin();
        Node Dest = data.getDestination();
        Node pere[] = new Node[nbNodes];
        Boolean stop = false;
        
        ArrayList<Arc> arcs_retour = new ArrayList<Arc>();
        
        
        // Labels        
        Label list_label[] = new Label[nbNodes]; 
        list_label = initLabel(nbNodes, Origin, Dest);
        
                
        //origin label
		

        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        Boolean end = false;
        Label courant = creer_label(null,Dest);
        
        // While nodes not marked exist
        while(this.tas_label.isEmpty() == false && end == false) {
        	
        	it=0;
        	
        	courant = this.tas_label.deleteMin();
        	courant.setmarque();  
        	notifyNodeMarked(courant.get_smt());        	
        	
        	if(courant.get_smt() != data.getOrigin()) {
        	stop = false;
        	
	        	List<Arc> arcs_rapid = courant.getpere().getSuccessors();
	        	
	        	for(Arc a : arcs_rapid) {
	        		if(a.getOrigin() == courant.getpere() && a.getDestination() == courant.get_smt() && stop == false) {
	        			arcs_retour.add(a);
	        			stop = true;
	        		}
	        	}
	        	
	        	
	            
	            
        	}
        	
        	if(courant.get_smt() == data.getDestination()) end = true;

        	
        	//parcours des successeurs
        	List<Arc> arcs = courant.get_smt().getSuccessors();   
        	
        	
        	for(Arc arc : arcs) {
        		
        		
        		if(arc.getOrigin() != courant.get_smt()) {
        			continue;
        		}
        		
        		if(list_label[arc.getDestination().getId()]!= null) {        			
        			if(this.tas_label.array.contains(list_label[arc.getDestination().getId()])==false) {
        				continue;
        			}
        		}
        		
        		it++;
        		
       
        		
        		//chemin empruntable
        		if(data.isAllowed(arc)==false) continue;
        		
        		Node successor = arc.getDestination();
        		Label successor_label = creer_label(null,Dest);
        		successor_label = list_label[successor.getId()];
        		
        		
        		
        		
        		//create label if it doesn't exist
        		if (successor_label == null) {
        			successor_label = creer_label(successor,Dest);
        			list_label[successor_label.get_smt().getId()] = successor_label;
        		}
        		
        		//successor not marked yet
        		if(successor_label.getMarque()==false) {
        			//look for a better cost (if it does exist)
        				if(successor_label.getCost() == Double.POSITIVE_INFINITY) {
        					successor_label.setCout(courant.getCost()+(float) data.getCost(arc));
        					this.tas_label.insert(successor_label);
        					successor_label.setpere(courant.get_smt());
        					successor_label.setinTas();        
        					pere[successor.getId()] = courant.get_smt();
        					
        					notifyNodeReached(successor_label.get_smt()); 
        				}
        				else if(successor_label.getCost() > courant.getCost() + data.getCost(arc)) {
        					successor_label.setCout(courant.getCost() + (float)data.getCost(arc));
        					successor_label.setpere(courant.get_smt());
        					
        					//if this label is already in the heap we update it position
        	        		if(successor_label.getinTas() == true) {
        	        			this.tas_label.remove(successor_label);
        	        		}
        	        		else {
        	        			successor_label.setinTas();        			
        	        		}   
        	        		this.tas_label.insert(successor_label); 
        	        		pere[successor.getId()] = courant.get_smt();
        	                	        		
        	        		notifyNodeReached(successor_label.get_smt()); 
        				} 
        				
        				
        		  }
        		
        	}
         } //Fin WHILE
        
        notifyDestinationReached(data.getDestination());
        
       
        
        
        ArrayList<Arc> farc = new ArrayList<Arc>();
        Node node_courante = courant.get_smt();   
        Arc prev_arc = null;
        Node predecesseur = courant.getpere();
        Boolean ok = false;

        List<Arc> arcs = predecesseur.getSuccessors();
        	for(Arc a : arcs) {
	    		if(Dest == a.getDestination() && a.getOrigin() == predecesseur && ok == false) {
	    			farc.add(a);
	    			prev_arc = a;
	    			ok = true;
	    			node_courante = a.getOrigin();
	    		}
        	}	
        
       
        
        while( node_courante.getId()  != data.getOrigin().getId()) { 
        if(prev_arc!=null) {
	        for(Arc a : arcs_retour) {
	        		if(a.getDestination() == prev_arc.getOrigin() && a.getOrigin() == pere[prev_arc.getOrigin().getId()]) {
	        			farc.add(a);
	        			node_courante = a.getOrigin();
	        			prev_arc = a;
	        			
	        		}
	        	}
        }
        
        }
       
        
        	
        Collections.reverse(farc);
 			// Create the final solution.
 			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, farc));
			System.out.println("---Dijkstra*"
					+ "---\n");
			System.out.println("Sommet d'origine : "+data.getOrigin().getId());
			System.out.println("sommet de destination : "+data.getDestination().getId());
			System.out.println("\n");
			float temps=0;
			float cost=0;
			int heures, minutes, secondes;
			for (Arc a: solution.getPath().getArcs()){
				cost+=a.getLength();
			}
			heures = (int) cost/3600;
			minutes = (int)(cost % 3600)/60;
			secondes = (int) ((cost % 3600) % 60);
			System.out.println("Coût du chemin : "+ cost);
			//System.out.println("Coût du chemin : "+ heures +" heures, " + minutes+ " minutes, " + secondes + " secondes" );
	        return solution;
	    }
}
