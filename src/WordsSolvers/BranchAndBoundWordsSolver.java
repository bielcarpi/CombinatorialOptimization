package WordsSolvers;

public class BranchAndBoundWordsSolver extends AbstractWordsSolver{

    private char[][] words;
    private String needle;

    public BranchAndBoundWordsSolver(boolean renderGraphics) {
        super(renderGraphics);
    }

    @Override
    protected int[] solve(char[][] words, String needle) {
        this.words = words;
        this.needle = needle;
        return branchAndBound();
    }

    private int[] branchAndBound(){
        int needleLength = needle.length();
        boolean found = false;

        //Horizontal search
        for(int y = 0; y < words.length; y++){
            for(int x = 0; x < words[0].length - needleLength; x += needleLength){
                found = true;

                for(int i = 0; i < needleLength; i++){
                    if(words[y][x+i] != needle.charAt(i)){
                        found = false;
                        break;
                    }
                }

                if(found) return new int[]{y, x, y, x+needleLength};
            }
        }


        //Vertical search



        //Diagonal search



        //Solution doesn't exist?
        return null;
    }
}
