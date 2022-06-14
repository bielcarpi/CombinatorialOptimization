package LabyrinthSolvers;

import edu.salleurl.arcade.labyrinth.model.enums.Cell;
import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class BruteForceLabyrinthSolver extends AbstractLabyrinthSolver{

    private final ArrayList<Direction> config;
    private ArrayList<Direction> bestSolution;
    private Cell[][] cells;

    private final static int STARTING_X_COORD = 1; //Could be changed dynamically using a method that searches for the starting point
    private final static int STARTING_Y_COORD = 1;

    public BruteForceLabyrinthSolver(boolean renderGraphics) {
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
        cells[y][x] = Cell.WALL; //The last cell is a wall

        if(!cells[y][x+1].equals(Cell.WALL)){
            config.add(Direction.RIGHT);
            x++;
        }
        else if(!cells[y][x-1].equals(Cell.WALL)){
            config.add(Direction.LEFT);
            x--;
        }
        else if(!cells[y-1][x].equals(Cell.WALL)){
            config.add(Direction.UP);
            y--;
        }
        else if(!cells[y+1][x].equals(Cell.WALL)){
            config.add(Direction.DOWN);
            y++;
        }
        else{
            cells[y][x] = Cell.EMPTY;
            return;
        }

        outerLoop:
        while(true) {
            //If we're not in the solution yet, bruteForce again
            if(!cells[y][x].equals(Cell.EXIT)){
                bruteForce(x, y);
            }
            else{
                //Solution case
                checkSolution();
            }

            // Next successor
            Direction d = config.get(config.size() - 1);
            switch (d){
                case RIGHT:
                    x--;
                    if(!cells[y][x-1].equals(Cell.WALL)){
                        config.remove(config.size() - 1);
                        config.add(Direction.LEFT);
                        x--;
                        break;
                    }
                case LEFT:
                    if(d.equals(Direction.LEFT)) x++;
                    if(!cells[y-1][x].equals(Cell.WALL)) {
                        config.remove(config.size() - 1);
                        config.add(Direction.UP);
                        y--;
                        break;
                    }
                case UP:
                    if(d.equals(Direction.UP)) y++;
                    if(!cells[y+1][x].equals(Cell.WALL)) {
                        config.remove(config.size() - 1);
                        config.add(Direction.DOWN);
                        y++;
                        break;
                    }
                case DOWN:
                    config.remove(config.size() - 1);
                    y--;
                    cells[y][x] = Cell.EMPTY;
                    break outerLoop; //If we're in DOWN, we already checked all possible options
                }
            }

        }

    private void checkSolution(){
        //Check whether this solution is better than the last one
        if(bestSolution == null || bestSolution.size() > config.size())
            bestSolution = new ArrayList<>(config);
    }

}
