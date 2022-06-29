import LabyrinthSolvers.BacktrackingLabyrinthSolver;
import LabyrinthSolvers.BacktrackingPBMSCLabyrinthSolver;
import LabyrinthSolvers.BranchAndBoundLabyrinthSolver;
import edu.salleurl.arcade.Arcade;
import edu.salleurl.arcade.ArcadeBuilder;

public class Main {

    /*
     * In order to modify the program execution, you can modify the following constants;
     * See the Method enum to know which solving methods are available
     */
    private static final int LABYRINTH_COLUMNS = 90;
    private static final int LABYRINTH_ROWS = 90;
    private static final int WORDS_COLUMNS = 12;
    private static final int WORDS_ROWS = 12;

    private static final SolvingMethod LABYRINTH_SOLVING_METHOD = SolvingMethod.BACKTRACKING;
    private static final SolvingMethod WORDS_SOLVING_METHOD = SolvingMethod.BACKTRACKING;
    private static final int SEED = 40; //-1 if no seed wants to be used
    private static final boolean stepRender = false;
    /**
     * -------------------------------------------------------------------------------
     * -------------------------------------------------------------------------------
     * -------------------------------------------------------------------------------
     */

    public static void main(String[] args){
        System.out.println("P2 - Combinatorial Optimization");

        ArcadeBuilder builder = new ArcadeBuilder();
        builder.setLabyrinthColumns(LABYRINTH_COLUMNS);
        builder.setLabyrinthRows(LABYRINTH_ROWS);
        builder.setWordsColumns(WORDS_COLUMNS);
        builder.setWordsRows(WORDS_ROWS);
        setLabyrinthSolver(builder, LABYRINTH_SOLVING_METHOD);
        setWordsSolver(builder, WORDS_SOLVING_METHOD);
        if(SEED != -1) builder.setSeed(SEED);

        Arcade arcade = builder.build();
        arcade.run();
    }

    private static void setLabyrinthSolver(ArcadeBuilder builder, SolvingMethod method){
        switch(method){
            case BACKTRACKING -> builder.setLabyrinthSolver(new BacktrackingLabyrinthSolver(stepRender));
            case BACKTRACKING_PBMSC -> builder.setLabyrinthSolver(new BacktrackingPBMSCLabyrinthSolver(stepRender));
            case BRANCH_AND_BOUND -> builder.setLabyrinthSolver(new BranchAndBoundLabyrinthSolver(stepRender));
        }
    }
    private static void setWordsSolver(ArcadeBuilder builder, SolvingMethod method){
        switch(method){
            case BACKTRACKING -> builder.setWordsSolver(null);
            case BACKTRACKING_PBMSC -> builder.setWordsSolver(null);
            case BRANCH_AND_BOUND -> builder.setWordsSolver(null);
        }
    }
}
