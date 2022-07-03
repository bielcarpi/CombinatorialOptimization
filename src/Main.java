import LabyrinthSolvers.BacktrackingLabyrinthSolver;
import LabyrinthSolvers.BacktrackingPBMSCLabyrinthSolver;
import LabyrinthSolvers.BranchAndBoundLabyrinthSolver;
import WordsSolvers.BacktrackingWordsSolver;
import WordsSolvers.GreedyWordsSolver;
import edu.salleurl.arcade.Arcade;
import edu.salleurl.arcade.ArcadeBuilder;

public class Main {

    /*
     * In order to modify the program execution, you can modify the following constants;
     * See the Method enum to know which solving methods are available
     */
    private static final int LABYRINTH_COLUMNS = 25;
    private static final int LABYRINTH_ROWS = 25;
    private static final int WORDS_COLUMNS = 30;
    private static final int WORDS_ROWS = 30;

    private static final SolvingMethod LABYRINTH_SOLVING_METHOD = SolvingMethod.BRANCH_AND_BOUND;
    private static final SolvingMethod WORDS_SOLVING_METHOD = SolvingMethod.GREEDY;
    private static final int SEED = 46; //-1 if no seed wants to be used
    private static final boolean stepRender = true; //false if you don't want to see the process
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
            default -> System.err.println("Error. The Labyrinth Solver has to be either Backtracking, Backtracking with PBMSC or Branch and Bound");
        }
    }
    private static void setWordsSolver(ArcadeBuilder builder, SolvingMethod method){
        switch(method){
            case BACKTRACKING -> builder.setWordsSolver(new BacktrackingWordsSolver(stepRender));
            case GREEDY -> builder.setWordsSolver(new GreedyWordsSolver(stepRender));
            default -> System.err.println("Error. The Words Solver has to be either Backtracking or Greedy.");
        }
    }
}
