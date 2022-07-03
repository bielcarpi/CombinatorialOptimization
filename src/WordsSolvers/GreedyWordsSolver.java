package WordsSolvers;

import java.util.Random;

public class GreedyWordsSolver extends AbstractWordsSolver{

    private char[][] words;
    private String needle;

    public GreedyWordsSolver(boolean renderGraphics) {
        super(renderGraphics);
    }

    @Override
    protected int[] solve(char[][] words, String needle) {
        this.words = words;
        this.needle = needle;
        return greedy();
    }

    private int[] greedy(){
        int needleLength = needle.length();
        boolean found;
        Random r = new Random();

        while(true) {
            int x = r.nextInt(words[0].length);
            int y = r.nextInt(words.length);

            render(words, needle, new int[]{y, x, y, x});
            if (words[y][x] != needle.charAt(0)) continue;

            //Search horizontally
            found = true;
            if (x <= words[0].length - needleLength) {
                for (int i = 1; i < needleLength; i++) {
                    super.render(words, needle, new int[]{y, x, y, x + i});
                    if (words[y][x + i] != needle.charAt(i)) {
                        found = false;
                        break;
                    }
                }
                if (found) return new int[]{y, x, y, x + needleLength - 1};
            }

            //Search vertically
            found = true;
            if (y <= words.length - needleLength) {
                for (int i = 1; i < needleLength; i++) {
                    if (words[y + i][x] != needle.charAt(i)) {
                        super.render(words, needle, new int[]{y, x, y + i, x});
                        found = false;
                        break;
                    }
                }
                if (found) return new int[]{y, x, y + needleLength - 1, x};
            }

            //Search diagonally
            found = true;
            if (y <= words.length - needleLength && x <= words.length - needleLength) {
                for (int i = 1; i < needleLength; i++) {
                    if (words[y + i][x + i] != needle.charAt(i)) {
                        found = false;
                        break;
                    }
                }
                if (found) return new int[]{y, x, y + needleLength-1, x + needleLength-1};
            }
        }
    }

}
