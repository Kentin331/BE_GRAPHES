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
<<<<<<< HEAD
        Node noeud_courant=data.getOrigin();
        List<Arc> arc_courant= new ArrayList<Arc>();
        List<label> non_traitees= new ArrayList<label>();
        List<label> traitees= new ArrayList<label>();
        int id_noeud_courant;
        float distance_courante=0;
        float nouvelle_distance=0;
        label lab = new label(noeud_courant.getId(), false, 0, 0);
        non_traitees.add(lab);
        ArrayList<Integer> id_noeud = new ArrayList();
        ArrayList<Integer> cout_noeud = new ArrayList();
        List<Arc> chemin = new ArrayList<Arc>();
        while(non_traitees.isEmpty()==false){
        	id_noeud_courant=this.getNodeWithLowestDistance(non_traitees);
            id_noeud.add(id_noeud_courant);
            cout_noeud.add(0);
        	this.DijkstraUpdate(traitees, non_traitees);
        	for (Node node : data.getGraph().getNodes()) {
        		for(Arc voisin : node.getSuccessors()) {
	        		if (voisin.getOrigin().getId()==id_noeud_courant){
	        			/*arc_courant=node.getSuccessors();
	        			for (Arc arc : arc_courant) {     				
	        				if(arc.getOrigin()==node) { */
	        			for (label label : traitees) {
			        		if(label.getCurrentNode()==id_noeud_courant) {
			        			distance_courante=label.getCost();
			        		}
			        	}
		        		nouvelle_distance=((Arc) voisin).getLength()+getCost(voisin.getDestination().getId(), id_noeud, cout_noeud);

		        		if( > nouvelle_distance) {
			        			distance_courante = nouvelle_distance;
			        			label new_lab = new label(voisin.getDestination().getId(), false, (int) distance_courante, node.getId());
			        			non_traitees.add(new_lab);
			        	}
	        		}	
        		}
        	}	
        	
        }

=======
        
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

        
        // While nodes not marked exist
        while(tas_label.isEmpty() == false) {
        	
        	int index = 0;
        	Double mincout = Double.POSITIVE_INFINITY;
        	for(int i =0; i< tas_label.size(); i++) {
        		Label lab = tas_label.array.get(i);
        		Double cout = lab.getCost();
        		if(cout < mincout) {
        			mincout = cout;
        			index = i;
        		}
        	}
        	
        	Label courant = tas_label.array.get(index);
        	tas_label.remove(courant);
        	courant.setmarque();       	

        	
        	//parcours des successeurs
        	List<Arc> arcs = courant.get_smt().getSuccessors();  
        	
        	       	
        	for (int i = 0; i<nbNodes; i++) {
        		test[i] = false;
        	}
        	
        	for(Arc arc : arcs) {
        		
        		if(arc.getOrigin() != courant.get_smt()) {
        			continue;
        		}
        		if(test[arc.getDestination().getId()] == true) {
        			continue;
        		}
        		if(list_label[arc.getDestination().getId()]!= null) {        			
        			if(tas_label.array.contains(list_label[arc.getDestination().getId()])==false) {
        				continue;
        			}
        		}
        		
        		
        		
        		test[arc.getDestination().getId()]=true;
        		//System.out.println("Sommet " + arc.getDestination().getId() + " en visite");
        		
        		
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
        				} 
        		  }
        		
        	}
         } //Fin WHILE
        
       
        
        Node Dest = data.getDestination();
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
        	
          }
        
 
        
        
        
        while( node_courante.getId()  != data.getOrigin().getId()) { // A partir de l'arc trouvé on remonte jusqu'a la source	
        
        	for(Label l : list_label) {
        		if(l != null) {
		        	if(l.get_smt() == prev_arc.getOrigin()) {
		        		//regarder le pere
		        		for(Node n : graph.getNodes()) {
		                	List<Arc> arcs = n.getSuccessors();
		                	for(Arc a : arcs) {
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

        
        
>>>>>>> 343ae99d4bdaf7210083c6522497e371120d7020
        return solution;
    }
    
    int getCost(int id_noeud, ArrayList<Integer> id, ArrayList<Integer> cost) {
    	int i, node, cout=0;
		for (i=0;i<cost.size();i++) {
			node=id.get(i);
    		if(id_noeud==node) {
    			cout=cost.get(id.indexOf(node));
    		}
    	}
    	
    	return cout;
    }
    
    
    int getNodeWithLowestDistance(List<label> tab_dijktra) {
    	int noeud=tab_dijktra.get(0).getCurrentNode();
    	int cout_min=tab_dijktra.get(0).getCost();
    	for (label lab : tab_dijktra) {
    		if(lab.getCost()<cout_min) {
    			cout_min=lab.getCost();
    			noeud=lab.getCurrentNode();
    		}
    	}
    	
    	return noeud;
    }
      
    void DijkstraUpdate(List<label> add, List<label> remove) {
    	int cout_min=remove.get(0).getCost();
    	label label_min = null;
    	for (label lab : remove) {
    		if(lab.getCost()<cout_min) {
    			cout_min=lab.getCost();
    			label_min=lab;
    		}
    	}
    	add.add(label_min);
    	remove.remove(label_min);

    }
    
 

}
