import java.util.*;
import java.io.*;


public class GameOfLife {
    private World world;
    private PatternStore patStore;
    ArrayList<World> cachedWorlds = new ArrayList<>();

    public GameOfLife(PatternStore pts) {
        patStore = pts;
    }

    private World copyWorld(boolean useCloning) throws Exception {
        World copy = null;
        if (useCloning) {
            if (world instanceof ArrayWorld) {
               copy = new ArrayWorld((ArrayWorld) world);
            } else if (world instanceof PackedWorld) {
                copy = new PackedWorld((PackedWorld) world);
            } else {
                copy = world.clone();
            }

        }
        return copy;
    }

    public void play() throws Exception {
        String response = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please select a pattern to play (l to list):");
        while (!response.equals("q")) {
            response = in.readLine();
            System.out.println(response);
            if (response.equals("f")) {
                if (world == null) {
                    System.out.println("Please select a pattern to play (l to list):");

                } else {
                    if (world.getGenerationCount() < cachedWorlds.size() - 1) {
                        world = cachedWorlds.get(world.getGenerationCount() + 1);
                    } else {
                        cachedWorlds.add(copyWorld(true));
                        world.nextGeneration();

                        if (world instanceof ArrayWorld) {

                            ((ArrayWorld) world).optimize();
                        }
                    }

                    print();
                }
            } else if (response.equals("l")) {
                ArrayList<Pattern> patterns = patStore.getPatternsNameSorted();
                int i = 0;
                for (Pattern p : patterns) {
                    System.out.println(i + " " + p.getName() + " (" + p.getAuthor() + ")");
                    i++;
                }
            } else if (response.startsWith("p")) {
                ArrayList<Pattern> patterns = patStore.getPatternsNameSorted();

                Integer number = Integer.parseInt(response.split(" ")[1]);
                Pattern pattern = patterns.get(number);
                if (pattern.getWidth() * pattern.getHeight() > 64) {
                    world = new ArrayWorld(pattern);
                } else {
                    world = new PackedWorld(pattern);
                }

                print();
            } else if (response.startsWith("b")) {
                if (world.getGenerationCount() == 0) {
                    continue;

                } else if (world.getGenerationCount() > 0) {
                    world = cachedWorlds.get(world.getGenerationCount() - 1);
                    System.out.println();
                }
                print();
            }
        }
    }

    public static void main(String args[]) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java GameOfLife <path/url to store>");
            return;
        }
        try {
            PatternStore ps = new PatternStore(args[0]);
            GameOfLife gol = new GameOfLife(ps);
            gol.play();
        } catch (IOException ioe) {
            System.out.println("Failed to load pattern store");
        }
    }

    public void print() {
        System.out.println("-" + world.getGenerationCount());
        for (int i = 0; i < world.getHeight(); ++i) {
            for (int j = 0; j < world.getWidth(); ++j) {

                if (world.getCell(i, j)) {
                    System.out.print(" + ");
                } else {
                    System.out.print(" - ");
                }

            }
            System.out.println();
        }
    }

}
