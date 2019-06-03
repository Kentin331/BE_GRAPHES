package org.insa.algo.shortestpath;

import java.io.PrintWriter;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.insa.graph.Graph;
import org.insa.algo.* ;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.Test;

public class PerformancesTest { 

	private static Graph small_graph, bigger_graph, average_graph, cars_graph, no_car_graph;
    private static ShortestPathData datas[]; 
    private static ArcInspector inspector_all = ArcInspectorFactory.getAllFilters().get(0);
    private static ArcInspector inspector_cars_length = ArcInspectorFactory.getAllFilters().get(1);
    private static ArcInspector inspector_cars_time = ArcInspectorFactory.getAllFilters().get(2); 
    private static ArcInspector inspector_no_car = ArcInspectorFactory.getAllFilters().get(3);
    private static String small_map, bigger_map, average_map, cars_only, no_car ;
    private static GraphReader small_reader, bigger_reader, average_reader,car_reader, no_car_reader;
    private static DijkstraAlgorithm[] dijkstra, astar ;
	private static long start;
	private static long end;

    
    public void initAll() throws IOException {
    	
    	/*	Initializing Maps		*/
        
    	
    	/*average_map = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/belgium.mapgr";
        small_map = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
        bigger_map = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/midi-pyrenees.mapgr";
        cars_only = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr";
        no_car = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bordeaux.mapgr";
        */
        
        average_map = "C:/Users/richa/Documents/BE_GRAPHES/BE_GRAPHES-m/Maps/belgium.mapgr";
        small_map = "C:/Users/richa/Documents/BE_GRAPHES/BE_GRAPHES-m/Maps/insa.mapgr";
        bigger_map = "C:/Users/richa/Documents/BE_GRAPHES/BE_GRAPHES-m/Maps//midi-pyrenees.mapgr";
        cars_only = "C:/Users/richa/Documents/BE_GRAPHES/BE_GRAPHES-m/Maps/toulouse.mapgr";
        no_car = "C:/Users/richa/Documents/BE_GRAPHES/BE_GRAPHES-m/Maps/bordeaux.mapgr"; 
          
        /*	Initializing Readers	*/
        
        small_reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(small_map))));
    	bigger_reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(bigger_map))));
    	average_reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(average_map))));
    	car_reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(cars_only))));
    	no_car_reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(no_car))));
    	
    	/*	Initializing Graphs		*/
    	
    	small_graph = small_reader.read();
    	average_graph = average_reader.read();
    	bigger_graph = bigger_reader.read();
    	cars_graph = car_reader.read();
    	no_car_graph = no_car_reader.read();
    	 
    	/*	Initializing Data	and Algorithms*/
    	
    	datas = new ShortestPathData[50];
    	dijkstra = new DijkstraAlgorithm[50] ;
    	astar = new AStarAlgorithm[50] ;
    	int i;
    	int origin = 701;
    	int destination = 284 ;

    	for(i=0; i<10;i++) { //100 first couples of nodes on small map
    		datas[i] = new ShortestPathData(small_graph,small_graph.get(origin), small_graph.get(destination), inspector_all);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+3))%(small_graph.size()));
    		destination = Math.abs((destination - (-1)*(i+3))%(small_graph.size()));
    	}
    	
    	origin = 778946;
    	destination = 36231 ;
    	for(i=10; i< 20;i++) { //100 second couples of nodes on average map
    		datas[i] = new ShortestPathData(average_graph,average_graph.get(origin), average_graph.get(destination), inspector_all);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+1320))%(average_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+1320))%(average_graph.size()));
    	}
    	
    	origin = 518036;
    	destination = 113426 ;
    	for(i=20; i< 30;i++) { //100 third couples of nodes on bigger map
    		datas[i] = new ShortestPathData(bigger_graph,bigger_graph.get(origin), bigger_graph.get(destination), inspector_all);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin + (-1)*(i+123))%(bigger_graph.size()));
        	destination = Math.abs((destination - (-1)*(i+123))%(bigger_graph.size()));
    	}
    	
    	origin = 4272;
    	destination =  4053;
    	for(i=30; i < 40;i++) {//100 next couples of nodes on Toulouse map
    		datas[i] = new ShortestPathData(cars_graph,cars_graph.get(origin), cars_graph.get(destination), inspector_cars_length);
    		dijkstra[i] = new DijkstraAlgorithm(datas[i]);
    		astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin - (-1)*(i+55))%(cars_graph.size()));
    		destination = Math.abs((destination - (-1)*(i+55))%(cars_graph.size()));
    	}
    	
    	origin = 1324 ;
    	destination =  1975;
    	for(i=40; i < 50;i++) { //100 last couples of nodes on Bordeaux map
        	datas[i] = new ShortestPathData(no_car_graph,no_car_graph.get(origin), no_car_graph.get(destination), inspector_no_car);
        	dijkstra[i] = new DijkstraAlgorithm(datas[i]);
        	astar[i] = new AStarAlgorithm(datas[i]);
    		origin = Math.abs((origin - (-1)*(i+13))%(no_car_graph.size()));
    		destination = Math.abs((destination - (-1)*(i+13))%(no_car_graph.size()));
    	}
    	

  	
    }
    
     @Test   
	public void launchTestDijkstraDistance() throws IOException{
    	initAll();
		ShortestPathSolution[] solutions = new ShortestPathSolution[50];
		int i;
		PrintWriter small = new PrintWriter(new FileWriter("insa_distance_dijkstra_10_data.txt"));
		PrintWriter average = new PrintWriter(new FileWriter("belgium_distance_dijkstra_10_data.txt"));
		PrintWriter big = new PrintWriter(new FileWriter("pyrenees_distance_dijkstra_10_data.txt"));
		PrintWriter cars = new PrintWriter(new FileWriter("toulouse_distance_dijkstra_10_data.txt"));
		PrintWriter no_car = new PrintWriter(new FileWriter("bordeaux_distance_dijkstra_10_data.txt"));
		small.println("Insa"); average.println("Belgium"); big.println("Pyrenees"); cars.println("Toulouse");no_car.println("Bordeaux");
		small.println("0\n100\nDijkstra"); average.println("0\n100\nDijkstra"); big.println("0 \n100\n Dijkstra"); cars.println("0\n100\nDijkstra");no_car.println("0\n100\nDijkstra");
		System.out.println("check 1");
		for(i = 0; i < 10; i++) {
			start=System.nanoTime();
			solutions[i] = dijkstra[i].doRun();
			end = System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible()) {
				small.printf("%d \t to %d \t %f km \t in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),(end-start)/1000000.0f);
			}
		} 
		System.out.println("check 2");
		for(i = 10; i < 20; i++) {
			start=System.nanoTime(); 
			solutions[i] = dijkstra[i].doRun();
			end = System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible()) {
				average.printf("%d \t to %d \t %f km \t in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),(end-start)/1000000.0f);
			}
		} 
		System.out.println("check 3");
		for(i = 20; i < 30; i++) {
			start=System.nanoTime();
			solutions[i] = dijkstra[i].doRun();
			end = System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible()) {
				big.printf("%d \t to %d \t %f km \t in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),(end-start)/1000000.0f);
			}
		}
		System.out.println("check 4");
		for(i = 30; i < 40; i++) {
			start=System.nanoTime(); 
			solutions[i] = dijkstra[i].doRun();
			end = System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible()) {
				cars.printf("%d \t to %d \t %f km \t in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),(end-start)/1000000.0f);
			}
		} 
		System.out.println("check 5");
		for(i = 40; i < 50; i++) {
			start=System.nanoTime();
			solutions[i] = dijkstra[i].doRun();
			end = System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible()) {
				no_car.printf("%d \t to %d \t %f km \t in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),(end-start)/1000000.0f);
			}
		}
		small.close(); average.close();big.close();no_car.close();cars.close();
	}

      @Test   
	public void launchTestAstarDistance() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[50]; 
		int i ;
		PrintWriter small = new PrintWriter(new FileWriter("insa_distance_astar_10_data.txt"));
		PrintWriter average = new PrintWriter(new FileWriter("belgium_distance_astar_10_data.txt"));
		PrintWriter big = new PrintWriter(new FileWriter("pyrenees_distance_astar_10_data.txt"));
		PrintWriter cars = new PrintWriter(new FileWriter("toulouse_distance_astar_10_data.txt"));
		PrintWriter no_car = new PrintWriter(new FileWriter("bordeaux_distance_astar_10_data.txt"));
		small.println("Insa"); average.println("Belgium"); big.println("Pyrenees"); cars.println("Toulouse");no_car.println("Bordeaux");
		small.println("0\n100\nA*"); average.println("0\n100\nA*"); big.println("0\n100\nA*"); cars.println("0\n100\nA*");no_car.println("0\n100\nA*");
		System.out.println("check 1");
		for(i = 0; i < 10; i++) {
			start=System.nanoTime();
			solutions[i] = astar[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())		small.printf("%d \t to %d \t %f km \t in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),(end-start)/1000000.0f);
		}
		System.out.println("check 2");
		for(i = 10; i < 20; i++) {
			start=System.nanoTime();
			solutions[i] = astar[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())		average.printf("%d \t to %d \t %f km \t in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),(end-start)/1000000.0f);
		}
		System.out.println("check 3");
		for(i = 20; i < 30; i++) {
			start=System.nanoTime();
			solutions[i] = astar[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())		big.printf("%d \t to %d \t %f km \t in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),(end-start)/1000000.0f);
		}
		System.out.println("check 4");
		for(i = 30; i < 40; i++) {
			start=System.nanoTime();
			solutions[i] = astar[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())		cars.printf("%d \t to %d \t %f km \t in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),(end-start)/1000000.0f);
		
		}
		System.out.println("check 5");
		for(i = 40; i < 50; i++) {
			start=System.nanoTime();
			solutions[i] = astar[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())		no_car.printf("%d \t to %d \t %f km \t in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getLength(),(end-start)/1000000.0f);
		}
		
		small.close(); average.close();big.close();no_car.close();cars.close();
	} 
     
     @Test   
	public void launchTestDijkstraTime() throws IOException{
		ShortestPathSolution[] solutions = new ShortestPathSolution[50];
		int i ;
		PrintWriter small = new PrintWriter(new FileWriter("insa_temps_dijkstra_10_data.txt"));
		PrintWriter average = new PrintWriter(new FileWriter("belgium_temps_dijkstra_10_data.txt"));
		PrintWriter big = new PrintWriter(new FileWriter("pyrenees_temps_dijkstra_10_data.txt"));
		PrintWriter cars = new PrintWriter(new FileWriter("toulouse_temps_dijkstra_10_data.txt"));
		PrintWriter no_car = new PrintWriter(new FileWriter("bordeaux_temps_dijkstra_10_data.txt"));
		small.println("Insa"); average.println("Belgium"); big.println("Pyrenees"); cars.println("Toulouse");no_car.println("Bordeaux");
		small.println("1\n100\nDijkstra"); average.println("1\n100\nDijkstra"); big.println("0\n100\nDijkstra"); cars.println("0\n100\nDijkstra");no_car.println("0\n100\nDijkstra");
		System.out.println("check 1");
		for(i = 0; i < 10; i++) {
			start=System.nanoTime();
			solutions[i] = dijkstra[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())			small.printf("%d \t to %d \t %f sec in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),(end-start)/1000000.0f);
		}
		System.out.println("check 2");

		for(i = 10; i < 20; i++) { 
			start=System.nanoTime();
			solutions[i] = dijkstra[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())			average.printf("%d \t to %d \t %f sec in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),(end-start)/1000000.0f);
		}
		System.out.println("check 3");
		for(i = 20; i < 30; i++) {
			start=System.nanoTime();
			solutions[i] = dijkstra[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())			big.printf("%d \t to %d \t %f sec in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),(end-start)/1000000.0f);
		} 
		System.out.println("check 4");
		for(i = 30; i < 40; i++) {
			start=System.nanoTime();
			solutions[i] = dijkstra[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())			cars.printf("%d \t to %d \t %f sec in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),(end-start)/1000000.0f);
		} 
		
		System.out.println("check 5");
		for(i = 40; i < 50; i++) {
			start=System.nanoTime();
			solutions[i] = dijkstra[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())			no_car.printf("%d \t to %d \t %f sec in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),(end-start)/1000000.0f);
		}
		small.close(); average.close();big.close();no_car.close();cars.close();
	} 

     @Test   
	public void launchTestAstarTime() throws IOException{
     	initAll();
		ShortestPathSolution[] solutions = new ShortestPathSolution[50];
		int i ;
		PrintWriter small = new PrintWriter(new FileWriter("insa_temps_astar_10_data.txt"));
		PrintWriter average = new PrintWriter(new FileWriter("belgium_temps_astar_10_data.txt"));
		PrintWriter big = new PrintWriter(new FileWriter("pyrenees_temps_astar_10_data.txt"));
		PrintWriter cars = new PrintWriter(new FileWriter("toulouse_temps_astar_10_data.txt"));
		PrintWriter no_car = new PrintWriter(new FileWriter("bordeaux_temps_astar_10_data.txt"));
		small.println("Insa"); average.println("Belgium"); big.println("Pyrenees"); cars.println("Toulouse");no_car.println("Bordeaux");
		small.println("1\n100\nA*"); average.println("1\n100\nA*"); big.println("0\n100\nA*"); cars.println("0\n100\nA*");no_car.println("0\n100\nA*");
		System.out.println("check 1");
		for(i =0 ; i < 10; i++) {
			start=System.nanoTime();
			solutions[i] = astar[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())			small.printf("%d \t to %d \t %f sec in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),(end-start)/1000000.0f);
		}
		System.out.println("check 2");
		
		for(i = 10; i < 20; i++) {
			start=System.nanoTime();
			solutions[i] = astar[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())			average.printf("%d \t to %d \t %f sec in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),(end-start)/1000000.0f);
		}
		System.out.println("check 3");
		for(i = 20; i < 30; i++) {
			start=System.nanoTime();
			solutions[i] = astar[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())			big.printf("%d \t to %d \t %f sec in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),(end-start)/1000000.0f);
		}
		System.out.println("check 4");
		for(i = 30; i < 40; i++) {
			start=System.nanoTime();
			solutions[i] = astar[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())			cars.printf("%d \t to %d \t %f sec in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),(end-start)/1000000.0f);
		}
		
		System.out.println("check 5");
		for(i = 40; i < 50; i++) {
			start=System.nanoTime();
			solutions[i] = astar[i].doRun();
			end=System.nanoTime();
			if(solutions[i] != null && solutions[i].isFeasible())			no_car.printf("%d \t to %d \t %f sec in %f ns\n",datas[i].getOrigin().getId(),datas[i].getDestination().getId(),solutions[i].getPath().getMinimumTravelTime(),(end-start)/1000000.0f);
		}

		
		small.close(); average.close();big.close();no_car.close();cars.close();
	}

}