package LabyrinthSolvers;

import edu.salleurl.arcade.labyrinth.model.enums.Cell;
import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class BranchAndBoundLabyrinthSolver extends AbstractLabyrinthSolver{

    private final ArrayList<Direction> config;
    private ArrayList<Direction> bestSolution;
    private Cell[][] cells;

    private final static int STARTING_X_COORD = 1; //Could be changed dynamically using a method that searches for the starting point
    private final static int STARTING_Y_COORD = 1;

    public BranchAndBoundLabyrinthSolver(boolean renderGraphics) {
        super(renderGraphics);
        config = new ArrayList<>();
    }

    @Override
    protected List<Direction> solve(Cell[][] cells) {
        //Clone the cells matrix
        this.cells = cells.clone();
        for(int i = 0; i < this.cells.length; i++){
            this.cells[i] = cells[i].clone();
        }

        bruteForce(STARTING_X_COORD, STARTING_Y_COORD);

        return bestSolution;
    }

    private void bruteForce(int x, int y){

    }

    private void checkSolution(){
        //Check whether this solution is better than the last one
        if(bestSolution == null || bestSolution.size() > config.size())
            bestSolution = new ArrayList<>(config);
    }

}
