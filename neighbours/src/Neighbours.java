import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.round;
import static java.lang.Math.sqrt;
import static java.lang.System.*;

/*
 *  Program to simulate segregation.
 *  See : http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/
 *
 * NOTE:
 * - JavaFX first calls method init() and then method start() far below.
 * - To test uncomment call to test() first in init() method!
 *
 */
// Extends Application because of JavaFX (just accept for now)
public class Neighbours extends Application {

    class Actor {
        final Color color;        // Color an existing JavaFX class
        boolean isSatisfied;      // false by default

        Actor(Color color) {      // Constructor to initialize
            this.color = color;
        }
    }

    // Below is the *only* accepted instance variable (i.e. variables outside any method)
    // This variable may *only* be used in methods init() and updateWorld()
    Actor[][] world;              // The world is a square matrix of Actors

    // This is the method called by the timer to update the world
    // (i.e move unsatisfied) approx each 1/60 sec.
    void updateWorld() {
        // % of surrounding neighbours that are like me
        double threshold = 0.7;

        // TODO update world

        int[] empty = emptySpaces(world);
        Actor[] actors = unsatisfied(world, threshold);
        world = placement(world, actors, empty);
    }

    // This method initializes the world variable with a random distribution of Actors
    // Method automatically called by JavaFX runtime
    // That's why we must have "@Override" and "public" (just accept for now)
    @Override
    public void init() {
        //test();    // <---------------- Uncomment to TEST!

        // %-distribution of RED, BLUE and NONE
        double[] dist = {0.25, 0.25, 0.50};
        // Number of locations (places) in world (must be a square)
        int nLocations = 900;   // Should also try 90 000

        // TODO initialize the world

        world = generateWorld(dist, nLocations);

        // Should be last
        fixScreenSize(nLocations);
    }

    // ---------------  Methods ------------------------------

    // TODO Many ...

    // Check if inside world
    boolean isValidLocation(int size, int row, int col) {
        return 0 <= row && row < size && 0 <= col && col < size;
    }

    Actor[][] generateWorld(double[] dist, int nLocations) {
        double prcR = dist[0];
        double prcB = dist[1];
        Actor[] arr = new Actor[nLocations];
        int am1 = (int) Math.round(nLocations * prcR);
        int am2 = (int) Math.round(nLocations * prcB);
        for (int i = 0; i < am1; i++) {
            arr[i] = new Actor(Color.BLUE);
        }
        for (int i = am1; i < am1 + am2; i++) {
            arr[i] = new Actor(Color.RED);
        }
        arr = shuffle(arr);
        Actor[][] matrix = arr2matrix(arr);
        return matrix;
    }

    boolean isSatisfied(Actor[][] world, int size, int row, int col, double threshold) {
        double neighbours = -1;
        double goodNeighbours = -1;
        if (isValidLocation(size, row, col) && world[row][col] != null) {
            Color color = world[row][col].color;
            for (int r = row - 1; r <= row + 1; r++) {
                for (int c = col - 1; c <= col + 1; c++) {
                    if (isValidLocation(size, r, c)) {
                        if (world[r][c] != null) {
                            if (color == world[r][c].color) {
                                goodNeighbours++;
                                neighbours++;
                            } else {
                                neighbours++;
                            }
                        }
                    }
                }
            }
        } else {
            neighbours++;
            goodNeighbours++;
        }
        boolean satisfied = false;
        if (neighbours != 0 && goodNeighbours != 0) {
            satisfied = goodNeighbours / neighbours >= threshold;
        }
        if (world[row][col] == null) {
            satisfied = true;
        }
        return satisfied;
    }

    Actor[] unsatisfied(Actor[][] world, double threshold) {
        int size = world.length;
        int count = 0;
        Actor[] tmpNotsatisifed = new Actor[size * size];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (!isSatisfied(world, size, r, c, threshold)) {
                    tmpNotsatisifed[count] = world[r][c];
                    world[r][c] = null;
                    count++;
                }
            }
        }
        Actor[] notsatisifed = new Actor[count];
        for (int i = 0; i < count; i++) {
            notsatisifed[i] = tmpNotsatisifed[i];
        }
        return notsatisifed;
    }

    int[] emptySpaces(Actor[][] world) {
        Actor[] worldArr = matrix2arr(world);
        int[] indexes = new int[countNull(world)];
        int count = 0;
        for (int i = 0; i < worldArr.length; i++) {
            if (worldArr[i] == null) {
                indexes[count] = i;
                count++;
            }
        }
        indexes = shuffleInt(indexes);
        return indexes;
    }

    Actor[][] placement(Actor[][] world, Actor[] actors, int[] indexes) {
        Actor[] worldArr = matrix2arr(world);
        for (int i = 0; i < actors.length; i++) {
            worldArr[indexes[i]] = actors[i];
        }
        Actor[][] nWorld = arr2matrix(worldArr);
        return nWorld;
    }

    // ----------- Utility methods -----------------

    // TODO (general method possible reusable elsewhere)

    Actor[] matrix2arr(Actor[][] matrix) {
        int length = 0;
        for (int i = 0; i < matrix.length; i++) {
            length += matrix[i].length;
        }
        Actor[] arr = new Actor[length];
        int k = 0;
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[r].length; c++) {
                arr[k] = matrix[r][c];
                k++;
            }
        }
        return arr;
    }

    Actor[][] arr2matrix(Actor[] arr) {
        int nRC = (int) Math.round(sqrt(arr.length));
        Actor[][] matrix = new Actor[nRC][nRC];
        int k = 0;
        for (int r = 0; r < nRC; r++) {
            for (int c = 0; c < nRC; c++) {
                matrix[r][c] = arr[k];
                k++;
            }
        }
        return matrix;
    }

    Actor[] shuffle(Actor[] arr) {
        Random rand = new Random();
        for (int i = arr.length - 1; i >= 0; i--) {
            int j = rand.nextInt(i + 1);
            Actor tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    int[] shuffleInt(int[] arr) {
        Random rand = new Random();
        for (int i = arr.length - 1; i >= 0; i--) {
            int j = rand.nextInt(i + 1);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    int countNull(Actor[][] world) {
        int nNull = 0;
        Actor[] arr = matrix2arr(world);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                nNull++;
            }
        }
        return nNull;
    }

    // ------- Testing -------------------------------------

    // Here you run your tests i.e. call your logic methods
    // to see that they really work. Important!!!!
    void test() {
        // A small hard coded world for testing
        Actor[][] testWorld = new Actor[][]{
                {new Actor(Color.BLUE), new Actor(Color.RED), null},
                {null, new Actor(Color.RED), null},
                {new Actor(Color.BLUE), null, new Actor(Color.BLUE)}
        };
        double th = 0.5;   // Simple threshold used for testing

        int size = testWorld.length;
        //out.println(isValidLocation(size, 0, 0));
        //out.println(!isValidLocation(size, -1, 0));
        //out.println(!isValidLocation(size, 0, 3));

        // TODO

        /*double[] dist = {0.25, 0.25, 0.50};
        int nLoc = 25;
        Actor[][] testWorld2 = generateWorld(dist, nLoc);
        Actor[] testArr = matrix2arr(testWorld2);
        for (int i = 0; i < nLoc; i++) {
            if (testArr[i] != null) {
                //out.println(testArr[i].color);
            } else {
                //out.println("null");
            }
        }
        for(int i = 0;i<testWorld2.length;i++){
            out.println(Arrays.toString(testWorld2[i]));
        }*/

        //out.println(countNull(testWorld));

        //out.println((isSatisfied(testWorld, 3, 1, 0, th) == true));
        //out.println((isSatisfied(testWorld, 3, 1, 1, 1.0) == false));
        //out.println((isSatisfied(testWorld, 3, 2, 2, th) == false));
        //out.println(Arrays.toString(unsatisfied(testWorld, th)));
        //out.println(Arrays.toString(emptySpaces(testWorld)));

        //Actor[] dude1 = new Actor[]{new Actor(Color.RED)/*, new Actor(Color.BLUE), new Actor(Color.RED)*/};
        //int[] ind = new int[] {2,3};
        //out.println(Arrays.toString(placement(testWorld, dude1, ind)[0]));
        //out.println(Arrays.toString(placement(testWorld, dude1, ind)[1]));
        //out.println(Arrays.toString(placement(testWorld, dude1, ind)[2]));

        exit(0);
    }

    // ******************** NOTHING to do below this row, it's JavaFX stuff  **************

    double width = 500;   // Size for window
    double height = 500;
    final double margin = 50;
    double dotSize;

    void fixScreenSize(int nLocations) {
        // Adjust screen window
        dotSize = 9000 / nLocations;
        if (dotSize < 1) {
            dotSize = 2;
        }
        width = sqrt(nLocations) * dotSize + 2 * margin;
        height = width;
    }

    long lastUpdateTime;
    final long INTERVAL = 450_000_000;


    @Override
    public void start(Stage primaryStage) throws Exception {

        // Build a scene graph
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        root.getChildren().addAll(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create a timer
        AnimationTimer timer = new AnimationTimer() {
            // This method called by FX, parameter is the current time
            public void handle(long now) {
                long elapsedNanos = now - lastUpdateTime;
                if (elapsedNanos > INTERVAL) {
                    updateWorld();
                    renderWorld(gc);
                    lastUpdateTime = now;
                }
            }
        };

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation");
        primaryStage.show();

        timer.start();  // Start simulation
    }


    // Render the state of the world to the screen
    public void renderWorld(GraphicsContext g) {
        g.clearRect(0, 0, width, height);
        int size = world.length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int x = (int) (dotSize * col + margin);
                int y = (int) (dotSize * row + margin);
                if (world[row][col] != null) {
                    g.setFill(world[row][col].color);
                    g.fillOval(x, y, dotSize, dotSize);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
