import LabyrinthSolvers.BruteForceLabyrinthSolver;
import edu.salleurl.arcade.Arcade;
import edu.salleurl.arcade.ArcadeBuilder;

public class Main {

    /*
     * In order to modify the program execution, you can modify the following constants;
     * See the Method enum to know which solving methods are available
     */
    private static final int LABYRINTH_COLUMNS = 9;
    private static final int LABYRINTH_ROWS = 9;
    private static final int WORDS_COLUMNS = 12;
    private static final int WORDS_ROWS = 12;

    private static final SolvingMethod LABYRINTH_SOLVING_METHOD = SolvingMethod.BRUTE_FORCE;
    private static final SolvingMethod WORDS_SOLVING_METHOD = SolvingMethod.BRUTE_FORCE;
    private static final int SEED = 40; //-1 if no seed wants to be used


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
            case BRUTE_FORCE -> builder.setLabyrinthSolver(new BruteForceLabyrinthSolver(true));
            case BACKTRACKING -> builder.setLabyrinthSolver(null);
            case BACKTRACKING_PBMSC -> builder.setLabyrinthSolver(null);
            case BRANCH_AND_BOUND -> builder.setLabyrinthSolver(null);
        }
    }
    private static void setWordsSolver(ArcadeBuilder builder, SolvingMethod method){
        switch(method){
            case BRUTE_FORCE -> builder.setWordsSolver(null);
            case BACKTRACKING -> builder.setWordsSolver(null);
            case BACKTRACKING_PBMSC -> builder.setWordsSolver(null);
            case BRANCH_AND_BOUND -> builder.setWordsSolver(null);
        }
    }
}
