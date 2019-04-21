import java.io.*;
import java.net.*;
import java.util.*;

public class PatternStore {
    public static final int MAX_NUMBER_PATTERNS = 1000;
    private ArrayList<Pattern> patterns = new ArrayList<>();

    public PatternStore(String source) throws IOException, PatternFormatException {
        if (source.startsWith("http://") || source.startsWith("https://")) {
            loadFromURL(source);
        } else {
            loadFromDisk(source);
        }
    }

    public PatternStore(Reader source) throws IOException, PatternFormatException {
        load(source);
    }

    private void load(Reader r) throws IOException, PatternFormatException {

        BufferedReader br = new BufferedReader(r);
        String line;
        while ((line = br.readLine()) != null) {
            try {
                Pattern pattern = new Pattern(line);
                patterns.add(pattern);
            } catch (PatternFormatException p) {
                System.out.println((p.getMessage()));

            }

        }
        br.close();


    }

    private void loadFromURL(String url) throws IOException, PatternFormatException {

        URL destination = new URL(url);
        URLConnection conn = destination.openConnection();
        Reader readFromURL = new InputStreamReader(conn.getInputStream());
        load(readFromURL);
    }

    private void loadFromDisk(String filename) throws IOException, PatternFormatException {
        Reader readFromDisk = new FileReader(filename);
        load(readFromDisk);
    }

    public ArrayList<Pattern> getPatternsNameSorted() {
        ArrayList<Pattern> copy = new ArrayList<>(patterns);
        Collections.sort(copy);
        return copy;
    }

    public Pattern[] getPatterns(){
        Pattern[] result = new Pattern[patterns.size()];
        for(int i = 0; i < patterns.size();i++){
            result[i] = patterns.get(i);
        }
        return result;
    }


    public static void main(String args[]) throws Exception {
        PatternStore p = new PatternStore(args[0]);

    }
}
