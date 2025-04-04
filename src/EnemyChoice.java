import java.util.ArrayList;
import java.util.Arrays;

import NEAT.Environment;
import NEAT.Genome;
import NEAT.Pool;

public class EnemyChoice implements Environment {
    int generation;
    Pool pool;

    Genome topGenome;

    Player player;
    Monster monster;

    public EnemyChoice(Player player, Monster monster) {
        generation = 0;
        pool = new Pool();
        pool.initializePool();
        topGenome = new Genome();
        this.player = player;
        this.monster = monster;
    }

    public void evaluateFitness(ArrayList<Genome> population) {
        for (Genome gene: population) {
            float fitness = 0;
            gene.setFitness(0);

            float inputs[] = {
                player.world_x, player.world_y, 
                player.speed, player.direction, 
                player.special_counter, player.attacking ? 1 : 0,
                player.health, player.defense,
                player.offense, player.invincible ? 1 : 0,
                player.special_attacking ? 1 : 0, player.class_type,
                
                monster.world_x, monster.world_y,
                monster.speed, monster.direction,
                monster.health, monster.defense,
                monster.offense, monster.invincible ? 1 : 0,
                monster.alive ? 1 : 0
            };

            // System.out.println(Arrays.toString(inputs));
            float outputs[] = gene.evaluateNetwork(inputs);
            float expected[] = {1, 1, 4};
            
            for(int i =0; i < outputs.length; i++) {
                fitness +=  (1 - Math.abs(expected[i] - outputs[i]));
            }

            fitness = fitness * fitness;

            gene.setFitness(fitness);
        }
    }
    
    public void periodic(EnemyChoice choice) {
        pool.evaluateFitness(choice);
        topGenome = pool.getTopGenome();
        System.out.println("TopFitness : " + topGenome.getPoints());
        System.out.println("Generation : " + generation );

        pool.breedNewGeneration();
        generation++;

        System.out.println(Arrays.toString(topGenome.evaluateNetwork(new float[] {
            player.world_x, player.world_y, 
            player.speed, player.direction, 
            player.special_counter, player.attacking ? 1 : 0,
            player.health, player.defense,
            player.offense, player.invincible ? 1 : 0,
            player.special_attacking ? 1 : 0, player.class_type,
            
            monster.world_x, monster.world_y,
            monster.speed, monster.direction,
            monster.health, monster.defense,
            monster.offense, monster.invincible ? 1 : 0,
            monster.alive ? 1 : 0
        })));
    }
}
