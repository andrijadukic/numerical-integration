package integration.demo;

import integration.algorithms.*;
import integration.util.*;
import linear.matrix.ArrayMatrix;
import linear.matrix.Matrix;
import linear.vector.Vector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Demo {

    private static final AbstractExplicitLinearSystemIntegrator EULER = new EulerMethod();
    private static final AbstractImplicitLinearSystemIntegrator BACKWARD_EULER = new BackwardEuler();
    private static final AbstractExplicitLinearSystemIntegrator RUNGE_KUTTA = new RungeKutta();
    private static final AbstractImplicitLinearSystemIntegrator TRAPEZOIDAL = new Trapezoidal();
    private static final AbstractLinearSystemIntegrator PECE = new PECE(new EulerMethod(), new Trapezoidal());
    private static final AbstractLinearSystemIntegrator PECE2 = new PECE(new EulerMethod(), new BackwardEuler(), 2);


    public static void main(String[] args) throws IOException {
        example1();
        System.out.println();

        example2();
        System.out.println();

        example3();
        System.out.println();

        example4();
    }

    private static void example1() throws IOException {
        System.out.println("Example 1");
        System.out.println();

        var logger = new NthIterationObserver(new StandardOutputLogger(), 100);
        var accumulator = new AbsoluteErrorAccumulator(t -> Vector.of(Math.cos(t) + Math.sin(t), Math.cos(t) - Math.sin(t)));
        var collector = new StateCollector();

        Matrix A = loadMatrix("data/A_zad1.txt");
        Matrix B = loadMatrix("data/B_zad1.txt");
        Vector x0 = loadMatrix("data/x0_zad1.txt").columns()[0];

        double max = 10.;
        double T = 0.01;

        for (var integrator : List.of(EULER, BACKWARD_EULER, TRAPEZOIDAL, RUNGE_KUTTA, PECE, PECE2)) {
            System.out.println(integrator.getName());

            integrator.addObserver(logger);
            integrator.addObserver(accumulator);
            integrator.addObserver(collector);

            integrator.solve(A, B, t -> Vector.of(0., 0.), T, max, x0);

            System.out.println("Accumulated error: " + accumulator.getAccumulatedError());

            accumulator.clear();
            writeToFile(integrator.getName() + "_zad1.txt", collector.getStatistics());
            collector.clear();

            integrator.clearObservers();

            System.out.println();
        }
    }

    private static void example2() throws IOException {
        System.out.println("Example 2");
        System.out.println();

        var logger = new StandardOutputLogger();
        var collector = new StateCollector();

        Matrix A = loadMatrix("data/A_zad2.txt");
        Matrix B = loadMatrix("data/B_zad2.txt");
        Vector x0 = loadMatrix("data/x0_zad2.txt").columns()[0];

        double max = 1.;
        double T = 0.1;

        for (var integrator : List.of(EULER, BACKWARD_EULER, TRAPEZOIDAL, PECE, PECE2)) {
            System.out.println(integrator.getName());

            integrator.addObserver(logger);
            integrator.addObserver(collector);

            integrator.solve(A, B, t -> Vector.of(0., 0.), T, max, x0);

            writeToFile(integrator.getName() + "_zad2.txt", collector.getStatistics());
            collector.clear();

            integrator.clearObservers();

            System.out.println();
        }

        System.out.println(RUNGE_KUTTA.getName());

        RUNGE_KUTTA.addObserver(logger);
        RUNGE_KUTTA.addObserver(collector);

        RUNGE_KUTTA.solve(A, B, t -> Vector.of(0., 0.), 0.04, max, x0);

        writeToFile(RUNGE_KUTTA.getName() + "_zad2.txt", collector.getStatistics());
        collector.clear();

        RUNGE_KUTTA.clearObservers();
    }

    private static void example3() throws IOException {
        System.out.println("Example 3");
        System.out.println();

        var logger = new StandardOutputLogger();
        var collector = new StateCollector();

        Matrix A = loadMatrix("data/A_zad3.txt");
        Matrix B = loadMatrix("data/B_zad3.txt");
        Vector x0 = loadMatrix("data/x0_zad3.txt").columns()[0];

        double max = 10.;
        double T = 0.01;

        for (var integrator : List.of(EULER, BACKWARD_EULER, TRAPEZOIDAL, RUNGE_KUTTA, PECE, PECE2)) {
            System.out.println(integrator.getName());

            integrator.addObserver(new NthIterationObserver(logger, 100));
            integrator.addObserver(collector);

            integrator.solve(A, B, t -> Vector.of(1., 1.), T, max, x0);

            writeToFile(integrator.getName() + "_zad3.txt", collector.getStatistics());
            collector.clear();

            integrator.clearObservers();

            System.out.println();
        }
    }

    private static void example4() throws IOException {
        System.out.println("Example 4");
        System.out.println();

        var logger = new StandardOutputLogger();
        var collector = new StateCollector();

        Matrix A = loadMatrix("data/A_zad4.txt");
        Matrix B = loadMatrix("data/B_zad4.txt");
        Vector x0 = loadMatrix("data/x0_zad4.txt").columns()[0];

        double max = 1.;
        double T = 0.01;

        for (var integrator : List.of(EULER, BACKWARD_EULER, TRAPEZOIDAL, RUNGE_KUTTA, PECE, PECE2)) {
            System.out.println(integrator.getName());

            integrator.addObserver(new NthIterationObserver(logger, 10));
            integrator.addObserver(collector);

            integrator.solve(A, B, t -> Vector.of(t, t), T, max, x0);

            writeToFile(integrator.getName() + "_zad4.txt", collector.getStatistics());
            collector.clear();

            integrator.clearObservers();

            System.out.println();
        }
    }

    private static Matrix loadMatrix(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        int rowDimension = lines.size();
        double[][] array = new double[rowDimension][];
        for (int i = 0; i < rowDimension; i++) {
            array[i] = Arrays.stream(lines.get(i).split("\\s+")).mapToDouble(Double::parseDouble).toArray();
        }

        return new ArrayMatrix(array);
    }

    private static void writeToFile(String fileName, List<StateStatistics> states) throws IOException {
        Files.write(
                Path.of("data/" + fileName.replace(" ", "_")),
                states.stream()
                        .map(state -> state.t() + ";" + state.x().toString().replace(" ", ";"))
                        .toList()
        );
    }
}
