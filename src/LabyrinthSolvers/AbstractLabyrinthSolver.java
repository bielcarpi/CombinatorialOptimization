package LabyrinthSolvers;

import edu.salleurl.arcade.labyrinth.controller.LabyrinthRenderer;
import edu.salleurl.arcade.labyrinth.model.LabyrinthSolver;
import edu.salleurl.arcade.labyrinth.model.enums.Cell;
import edu.salleurl.arcade.labyrinth.model.enums.Direction;

import java.util.List;

public abstract class AbstractLabyrinthSolver implements LabyrinthSolver {

    /**
     * Whether graphics should be rendered or not.
     */
    private final boolean renderGraphics;
    private LabyrinthRenderer lr;

    public AbstractLabyrinthSolver(boolean renderGraphics){
        this.renderGraphics = renderGraphics;
    }

    /**
     * Inheriting classes need to override and implement this method
     * @param cells The cells
     * @return A list of directions representing the solution
     */
    protected abstract List<Direction> solve(Cell[][] cells);

    @Override
    public final List<Direction> solve(Cell[][] cells, LabyrinthRenderer labyrinthRenderer){
        lr = labyrinthRenderer;
        long currentTime = System.currentTimeMillis();
        List<Direction> solution = solve(cloneCells(cells));
        long endTime = System.currentTimeMillis();
        System.out.println("The Labyrinth was solved in " + (endTime-currentTime) + "ms");

        lr.render(cells, solution);
        return solution;
    }

    protected final void render(Cell[][] cells, List<Direction> partialSolution){
        if(renderGraphics) lr.render(cells, partialSolution, 30); //If we want to render the graphics, render them
    }


    private Cell[][] cloneCells(Cell[][] cells){
        //Clone the cells matrix
        Cell[][] clone = cells.clone();
        for(int i = 0; i < clone.length; i++)
            clone[i] = cells[i].clone();

        return clone;
    }
}
