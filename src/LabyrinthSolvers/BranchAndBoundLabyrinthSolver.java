package LabyrinthSolvers;

import edu.salleurl.arcade.labyrinth.model.enums.Cell;
import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BranchAndBoundLabyrinthSolver extends AbstractLabyrinthSolver{

    private LabyrinthConfig bestSolution;
    private PriorityQueue<LabyrinthConfig> queue;

    private final static int STARTING_X_COORD = 1; //Could be changed dynamically using a method that searches for the starting point
    private final static int STARTING_Y_COORD = 1;

    public BranchAndBoundLabyrinthSolver(boolean renderGraphics) {
        super(renderGraphics);
        queue = new PriorityQueue<>();
    }

    @Override
    protected List<Direction> solve(Cell[][] cells) {
        Point startPoint = new Point(STARTING_X_COORD, STARTING_Y_COORD);
        Point endPoint = new Point(cells[0].length - 2, cells.length - 2);
        LabyrinthConfig firstConfig = new LabyrinthConfig(startPoint, endPoint, cells);
        queue.offer(firstConfig);

        while(!queue.isEmpty()){
            LabyrinthConfig config = queue.poll();
            super.render(cells, config.getConfig());
            Iterable<LabyrinthConfig> successors = config.expand();

            for(LabyrinthConfig successor: successors){
                //If the successor is solution and has better cost than the best solution found until now, update the best solution
                if(successor.isSolution() && (bestSolution == null || successor.cost() < bestSolution.cost())){
                    bestSolution = successor;
                }
                else{
                    //If the successor does not exceed the best solution cost, put it to que queue to be explored later on (PBMSC)
                    if(bestSolution == null || successor.cost() < bestSolution.cost())
                        queue.offer(successor);
                }
            }

        }

        return bestSolution.getConfig();
    }




    private static class LabyrinthConfig implements Comparable<LabyrinthConfig>{
        private final ArrayList<Direction> config;
        private final Point startPoint, currentPoint, endPoint;
        private final Cell[][] cells;
        private boolean[][] cellsVisited;

        //Constructor for the first configuration
        public LabyrinthConfig(Point startPoint, Point endPoint, Cell[][] cells) {
            config = new ArrayList<>();
            this.cells = cells;
            this.startPoint = startPoint;
            this.currentPoint = startPoint;
            this.endPoint = endPoint;

            cellsVisited = new boolean[cells.length][cells[0].length];
            cellsVisited[startPoint.y][startPoint.x] = true; //Mark current cell as visited
        }

        //Constructor for other configurations when expanding
        private LabyrinthConfig(LabyrinthConfig old, Direction newDirection) {
            this.config = new ArrayList<>(old.config);
            this.cells = old.cells;
            this.startPoint = old.startPoint;
            this.endPoint = old.endPoint;
            this.cellsVisited = old.cellsVisited;

            config.add(newDirection);
            switch (newDirection){
                case RIGHT -> currentPoint = new Point(old.currentPoint.x + 1, old.currentPoint.y);
                case LEFT -> currentPoint = new Point(old.currentPoint.x - 1, old.currentPoint.y);
                case UP -> currentPoint = new Point(old.currentPoint.x, old.currentPoint.y - 1);
                default -> currentPoint = new Point(old.currentPoint.x, old.currentPoint.y + 1); //DOWN
            }

            cellsVisited[currentPoint.y][currentPoint.x] = true; //Mark current cell as visited
        }

        public Iterable<LabyrinthConfig> expand() {
            Collection<LabyrinthConfig> successors = new ArrayList<>();

            if(!cells[currentPoint.y][currentPoint.x + 1].equals(Cell.WALL) && !cellsVisited[currentPoint.y][currentPoint.x + 1])
                successors.add(new LabyrinthConfig(this, Direction.RIGHT));
            if(currentPoint.x > 0 && !cells[currentPoint.y][currentPoint.x - 1].equals(Cell.WALL) && !cellsVisited[currentPoint.y][currentPoint.x - 1])
                successors.add(new LabyrinthConfig(this, Direction.LEFT));
            if(currentPoint.y > 0 && !cells[currentPoint.y - 1][currentPoint.x].equals(Cell.WALL) && !cellsVisited[currentPoint.y - 1][currentPoint.x])
                successors.add(new LabyrinthConfig(this, Direction.UP));
            if(!cells[currentPoint.y + 1][currentPoint.x].equals(Cell.WALL) && !cellsVisited[currentPoint.y + 1][currentPoint.x])
                successors.add(new LabyrinthConfig(this, Direction.DOWN));

            return successors;
        }

        public boolean isSolution() {
            return currentPoint.equals(endPoint);
        }

        public int cost() {
            //The real current cost of the config
            return config.size();
        }

        private int estimatedCost() {
            //Estimated Cost will be expressed as f(n) = g(n) + h(n)
            //Where h(n) is the heuristic. How many units we are away from the solution
            // and g(n) is a function that gives us how many units we are away from the start.
            //In order to calculate these, we'll use the Euclidean Distance

            //Thus, we're really using the A* Algorithm
            return (int)(currentPoint.distance(startPoint) + currentPoint.distance(endPoint));
        }

        @Override
        public int compareTo(LabyrinthConfig that) {
            /*
             * Compares this object with the specified object for order.  Returns a
             * negative integer, zero, or a positive integer as this object is less
             * than, equal to, or greater than the specified object.
             */
            return this.estimatedCost() - that.estimatedCost();
        }

        public ArrayList<Direction> getConfig(){
            return config;
        }
    }
}
