import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class InverseBWT {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    String inverseBWT(String bwt) {
        StringBuilder result = new StringBuilder();
        String lastColumn = bwt;
        char[] lastColArray = bwt.toCharArray();
        Arrays.sort(lastColArray);
        String firstColumn = new String(lastColArray);
        int textLength = bwt.length();
        HashMap<Character, Integer> ordinals = new HashMap();
        ordinals.put('A', 0);
        ordinals.put('C', 0);
        ordinals.put('T', 0);
        ordinals.put('G', 0);
        ordinals.put('$', 0);
        ColumnChar[] lastCol = new ColumnChar[textLength];
        for(int i=0;i<textLength;i++){
            char nextChar = bwt.charAt(i);
            lastCol[i] = new ColumnChar(nextChar, ordinals.get(nextChar));
            ordinals.put(nextChar, ordinals.get(nextChar) + 1);
        }
        
        ColumnChar[] firstCol = Arrays.copyOf(lastCol, lastCol.length);
        Arrays.sort(firstCol);
        
        HashMap<Character, ArrayList<Integer>> lastColCharIndices = new HashMap<>();
        lastColCharIndices.put('A', new ArrayList<>());
        lastColCharIndices.put('C', new ArrayList<>());
        lastColCharIndices.put('T', new ArrayList<>());
        lastColCharIndices.put('G', new ArrayList<>());
        lastColCharIndices.put('$', new ArrayList<>());
        for(int i=0;i<textLength;i++){
            ArrayList indices = lastColCharIndices.get(bwt.charAt(i));
            indices.add(i);
        }
        
        HashMap<Character, ArrayList<Integer>> firstColCharIndices = new HashMap<>();
        firstColCharIndices.put('A', new ArrayList<>());
        firstColCharIndices.put('C', new ArrayList<>());
        firstColCharIndices.put('T', new ArrayList<>());
        firstColCharIndices.put('G', new ArrayList<>());
        firstColCharIndices.put('$', new ArrayList<>());
        for(int i=0;i<textLength;i++){
            ArrayList indices = firstColCharIndices.get(bwt.charAt(i));
            indices.add(i);
        }
        
        ColumnChar currentChar = new ColumnChar('$', 0);
        char addChar = '$';
        int ordinal = 0;
        for(int i=0;i<bwt.length();i++){
            currentChar = firstCol[firstColCharIndices.get(currentChar.c).get(currentChar.ordinal)];
            currentChar = lastCol[lastColCharIndices.get(currentChar.c).get(currentChar.ordinal)];
            result.append(currentChar.c);
        }
        // write your code here
        //result.reverse();
        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
    }
    
    
    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
    }
        private class ColumnChar implements Comparable<ColumnChar>{
        char c;
        int ordinal;
        public ColumnChar(char c, int ordinal){
            this.c = c;
            this.ordinal = ordinal;
        }
        @Override
            public int compareTo(ColumnChar o) {
                if(this.c == o.c)
                    return 0;
                return this.c < o.c ? -1:1;
            }
            
        }
}
