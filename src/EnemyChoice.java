import java.util.ArrayList;

import NEAT.Environment;
import NEAT.Genome;
import NEAT.Pool;

public class EnemyChoice implements Environment {
    int generation;
    Pool pool;

    Genome topGenome;

    public EnemyChoice() {
        generation = 0;
        pool = new Pool();
        pool.initializePool();
        topGenome = new Genome();
    }

    public void evaluateFitness(ArrayList<Genome> population) {
        for (Genome gene: population) {
            float fitness = 0;
            gene.setFitness(0);
            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 2; j++) {
                    float inputs[] = {i, j};
                    float output[] = gene.evaluateNetwork(inputs);
                    int expected = i^j;
                    //                  System.out.println("Inputs are " + inputs[0] +" " + inputs[1] + " output " + output[0] + " Answer : " + (i ^ j));
                    //if (output[0] == (i ^ j))
                    fitness +=  (1 - Math.abs(expected - output[0]));
                }
            fitness = fitness * fitness;

            gene.setFitness(fitness);
        }
    }
    
    public void periodic(EnemyChoice choice) {
        //pool.evaluateFitness();
        pool.evaluateFitness(choice);
        topGenome = pool.getTopGenome();
        System.out.println("TopFitness : " + topGenome.getPoints());
//            System.out.println("Population : " + pool.getCurrentPopulation() );
        System.out.println("Generation : " + generation );
        //           System.out.println("Total number of matches played : "+TicTacToe.matches);
        //           pool.calculateGenomeAdjustedFitness();

        pool.breedNewGeneration();
        generation++;

        System.out.println(topGenome.evaluateNetwork(new float[]{1,0})[0]);
    }
}
