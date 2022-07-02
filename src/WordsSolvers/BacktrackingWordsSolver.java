package WordsSolvers;

import java.util.Arrays;

public class BacktrackingWordsSolver extends AbstractWordsSolver{

    private char[][] words;
    private String needle;

    public BacktrackingWordsSolver(boolean renderGraphics) {
        super(renderGraphics);
    }

    @Override
    protected int[] solve(char[][] words, String needle) {
        this.words = words;
        this.needle = needle;
        return backtracking(0, 0);
    }

    /**
     * Performs backtracking searching the needle
     * @param x The X position of the grid to look at
     * @param y The Y position of the grid to look at
     */
    private int[] backtracking(int x, int y){
        super.render(words, needle, new int[]{y, x, y, x});

        //If the current position IS the first letter of the needle, explore horizontally, vertically and diagonally downwards
        if (words[y][x] == needle.charAt(0)) {

            //Search horizontally
            boolean found = true;
            if (x <= words[0].length - needle.length()) {
                for (int i = 1; i < needle.length(); i++) {
                    super.render(words, needle, new int[]{y, x, y, x+i});
                    if (words[y][x + i] != needle.charAt(i)) {
                        found = false;
                        break;
                    }
                }
                if (found) return new int[]{y, x, y, x + needle.length() - 1};
            }

            //Search vertically
            found = true;
            if (y <= words.length - needle.length()) {
                for (int i = 1; i < needle.length(); i++) {
                    if (words[y + i][x] != needle.charAt(i)) {
                        super.render(words, needle, new int[]{y, x, y+i, x});
                        found = false;
                        break;
                    }
                }
                if (found) return new int[]{y, x, y + needle.length() - 1, x};
            }

            //Search diagonally
            found = true;
            if (y <= words.length - needle.length() && x <= words.length - needle.length()) {
                for (int i = 1; i < needle.length(); i++) {
                    if (words[y + i][x + i] != needle.charAt(i)) {
                        found = false;
                        break;
                    }
                }
                if (found) return new int[]{y, x, y + needle.length() - 1, x + needle.length() - 1};
            }
        }


        //If the current position is not the first letter of the needle, or we haven't found the word, move on
        if(x != words[0].length - 1){
            x++;
        }
        else{
            x = 0;
            y++;
        }

        return backtracking(x, y);
    }
}
