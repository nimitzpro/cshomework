package org.example;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.ArrayUtils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

//        System.out.println("Hello world!");

        VaccinationData v = new VaccinationData("src/main/resources/vaccinations2.txt");

        Model model = new Model("Vaccinations");

        // Create variables
        IntVar[][] centres_days = model.intVarMatrix(v.getNumCentres(), v.getNumDays(), 0, 100); // Create matrix of variables, with each row -> centre, col -> day

        for(int i = 0; i < v.getNumCentres(); i++) {
            for (int j = 0; j < v.getNumDays(); j++) {
                model.arithm(centres_days[i][j], ">=", v.getMinReqs()[i][j]).post(); // Min Reqs per day, per centre
            }
            model.sum(centres_days[i], ">=", v.getReqs()[i]).post(); // Min total Reqs per centre
        }

        for(int j = 0; j < v.getNumDays(); j++){
            model.sum(ArrayUtils.getColumn(centres_days, j), "<=", v.getSupplies()[j]).post(); // Max supplies available per day
        }

        // Solve the problem
        Solver solver = model.getSolver();
        if (solver.solve()) {     // Print the solution
            System.out.println(solver.findSolution());
        }
        else {
            System.out.println("No Solution");
        }
    }
}