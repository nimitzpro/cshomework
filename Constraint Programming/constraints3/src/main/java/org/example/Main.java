package org.example;

import java.io.IOException;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.nary.automata.FA.FiniteAutomaton;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.ArrayUtils;


// Alexander Stradnic 119377263
public class Main {

    public static void process(String filename) throws IOException {

        FireBrigadeData f = new FireBrigadeData(filename);

        Model model = new Model("Fire Brigade Scheduling");

        int num_shifts = f.getNumShifts();
        int num_firefighters = f.getNumFirefighters();
        int num_qualifications = f.getNumQualifications();

        int max_consec_shifts = f.getMaxConsecutive();
        int small_break = f.getMinBreak();
        int longer_break = f.getMinLongerBreak();
        int min_work = f.getMinWork();

        int[] min_fighters = f.getShiftMinimum();
        int[][] quals_per_shift = f.getQualsRequired();
        int[][] quals_per_fighter = f.getQualifiedFirefighters();


        IntVar[][] scheduleTable = model.intVarMatrix(num_firefighters, num_shifts, 0, num_qualifications); // Create matrix of variables, with each row -> firefighter, col -> shift

        FiniteAutomaton regex = new FiniteAutomaton("0*1{0,"+max_consec_shifts+"}(0{"+small_break+"}1{0,"+max_consec_shifts+"})*0{"+longer_break+"}1{0,"+max_consec_shifts+"}(0{"+small_break+"}1{0,"+max_consec_shifts+"})*0*");

        for(int i = 0; i < scheduleTable.length; i++) {

            model.regular(scheduleTable[i], regex).post(); // Match each firefighter with max consecutive shifts, minimum break between shifts, and minimum longer break

            model.sum(scheduleTable[i], ">=", min_work).post(); // Min total work shifts per firefighter

            for(int j = 0; j < scheduleTable[i].length; j++){
                model.sum(ArrayUtils.getColumn(scheduleTable, j), ">=", min_fighters[j]).post(); // Min fighters needed per shift
            }

        }

        for(int k = 0; k < num_qualifications; k++){
            for(int g = 0; g < num_firefighters; g++){
                for(int l = 0; l < num_shifts; l++) {
                    model.scalar(ArrayUtils.getColumn(scheduleTable, l), quals_per_fighter[k], ">=", quals_per_shift[k][l]).post(); // Make sure each firefighter has the qualifications required of the shift
                }
            }
        }

        IntVar deployed = model.sum("deployed", ArrayUtils.flatten(scheduleTable));
        model.setObjective(Model.MINIMIZE, deployed);
        Solver solver = model.getSolver();
//        System.out.println(solver.findOptimalSolution(deployed, false));

        while (solver.solve()){
            System.out.printf("X");
            for (int i = 0; i < num_shifts; i++) {
                System.out.printf("\ts%d", i);
            }
            System.out.println();
            for (int i = 0; i < scheduleTable.length; i++) {
                System.out.printf("f%d\t", i);
                for (IntVar j : scheduleTable[i]) {
                    System.out.printf("%d\t", j.getValue());
                }
                System.out.println();
            }
        }
        solver.printStatistics();
    }
    public static void main(String[] args) throws IOException {
//        process("src/main/resources/fire0corrected.txt");
//        process("src/main/resources/fire1corrected.txt");
//        process("src/main/resources/fire2corrected.txt");
        process("src/main/resources/fire3corrected.txt");
    }

}