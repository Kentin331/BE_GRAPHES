package org.insa.algo.shortestpath;

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
