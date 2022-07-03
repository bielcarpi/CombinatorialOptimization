package WordsSolvers;

import edu.salleurl.arcade.words.controller.WordsRenderer;
import edu.salleurl.arcade.words.model.WordsSolver;

public abstract class AbstractWordsSolver implements WordsSolver {
    /**
     * Whether graphics should be rendered or not.
     */
    private final boolean renderGraphics;
    private WordsRenderer wr;

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
        wr = wordsRenderer;
        long currentTime = System.nanoTime();
        int[] solution = solve(cloneWords(words), needle);
        long endTime = System.nanoTime();
        System.out.println("The word was found in " + (endTime-currentTime)/1000000f + "ms");

        wordsRenderer.render(words, needle, solution);
        return solution;
    }

    protected final void render(char[][] words, String needle, int[] partialSolution){
        if(renderGraphics) wr.render(words, needle, partialSolution, 30); //If we want to render the graphics, render them
    }


    private char[][] cloneWords(char[][] words){
        //Clone the words matrix
        char[][] clone = words.clone();
        for(int i = 0; i < clone.length; i++)
            clone[i] = words[i].clone();

        return clone;
    }
}
