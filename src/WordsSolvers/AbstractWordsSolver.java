package WordsSolvers;

import edu.salleurl.arcade.words.controller.WordsRenderer;
import edu.salleurl.arcade.words.model.WordsSolver;

public abstract class AbstractWordsSolver implements WordsSolver {
    /**
     * Whether graphics should be rendered or not.
     */
    private final boolean renderGraphics;

    public AbstractWordsSolver(boolean renderGraphics){
        this.renderGraphics = renderGraphics;
    }

    /**
     * Inheriting classes need to override and implement this method
     * @param words The letter soup
     * @param needle The string to be searched
     * @return An array of int representing the solution
     */
    protected abstract int[] solve(char[][] words, String needle);

    @Override
    public final int[] solve(char[][] words, String needle, WordsRenderer wordsRenderer){
        long currentTime = System.currentTimeMillis();
        int[] solution = solve(words, needle);
        long endTime = System.currentTimeMillis();
        System.out.println("The word was found in " + (endTime-currentTime)/1000.0 + " seconds");

        if(renderGraphics) wordsRenderer.render(words, needle, solution); //If we want to render the graphics, render them
        return solution;
    }
}
