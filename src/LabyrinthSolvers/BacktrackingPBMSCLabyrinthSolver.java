package LabyrinthSolvers;

import edu.salleurl.arcade.labyrinth.model.enums.Cell;
import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class BacktrackingPBMSCLabyrinthSolver extends AbstractLabyrinthSolver{

    private final ArrayList<Direction> config; //Current config
    private ArrayList<Direction> bestSolution; //Best config
    private Cell[][] cells;

    private final static int STARTING_X_COORD = 1; //Could be changed dynamically using a method that searches for the starting point
    private final static int STARTING_Y_COORD = 1;

    public BacktrackingPBMSCLabyrinthSolver(boolean renderGraphics) {
        super(renderGraphics);
        config = new ArrayList<>();
    }

    @Override
    protected List<Direction> solve(Cell[][] cells) {
        this.cells = cells;
        bruteForce(STARTING_X_COORD, STARTING_Y_COORD);
        return bestSolution;
    }


    private void checkSolution(){
        //Check whether this solution is better than the last one
        if(bestSolution == null || bestSolution.size() > config.size())
            bestSolution = new ArrayList<>(config);
    }

    private void bruteForce(int x, int y){
        cells[y][x] = Cell.WALL; //The last cell is a wall

        //Choose the next cell we'll explore (we can't explore a wall)
        if(!cells[y][x+1].equals(Cell.WALL)){
            config.add(Direction.RIGHT);
            x++;
        }
        else if(x > 0 && !cells[y][x-1].equals(Cell.WALL)){
            config.add(Direction.LEFT);
            x--;
        }
        else if(y > 0 && !cells[y-1][x].equals(Cell.WALL)){
            config.add(Direction.UP);
            y--;
        }
        else if(!cells[y+1][x].equals(Cell.WALL)){
            config.add(Direction.DOWN);
            y++;
        }
        else{
            //If our surroundings are all walls, make this cell empty again and return
            cells[y][x] = Cell.EMPTY;
            return;
        }


        outerLoop:
        while(true) {
            //If we're not in the solution yet, bruteForce again
            if(!cells[y][x].equals(Cell.EXIT)){
                if(config.size() > (bestSolution == null? -1: bestSolution.size())) //PBMSC
                    bruteForce(x, y);
            }
            else{ //Solution case
                checkSolution();
            }

            //Next successor
            Direction d = config.get(config.size() - 1);
            switch (d){
                case RIGHT:
                    x--;
                    if(x > 0 && !cells[y][x-1].equals(Cell.WALL)){
                        config.remove(config.size() - 1);
                        config.add(Direction.LEFT);
                        x--;
                        break;
                    }
                case LEFT:
                    if(d.equals(Direction.LEFT)) x++;
                    if(y > 0 && !cells[y-1][x].equals(Cell.WALL)) {
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
                    if(d.equals(Direction.DOWN)) y--;
                    config.remove(config.size() - 1);
                    cells[y][x] = Cell.EMPTY;
                    break outerLoop; //If we're in DOWN, we already checked all possible options
                }
            }
        }

}
