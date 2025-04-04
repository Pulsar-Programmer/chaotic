package NEAT;

import java.util.ArrayList;

import main.GamePanel;

/**
 * assign Fitness to each genome
 * Created by vishnu on 12/1/17.
 *
 */
public interface Environment {
     float[] evaluateFitness(ArrayList<Genome> population, GamePanel gp);
}