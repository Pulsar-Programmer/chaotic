package aspects.enemy;

import java.util.ArrayList;
import java.util.Arrays;

import NEAT.Environment;
import NEAT.Genome;
import NEAT.Pool;
import main.GamePanel;

public class EnemyChoice implements Environment {
    int generation;
    Pool pool;
    Genome topGenome;
    Monster monster;

    public EnemyChoice(Monster monster) {
        generation = 0;
        pool = new Pool();
        pool.initializePool();
        topGenome = new Genome();
        this.monster = monster;
    }


    /**
     * Returns outputs from population of neurons, outputs[0] == attacking (round for true or false), outputs[1] == speed, outputs[2] == direction
     */
    public void evaluateFitness(ArrayList<Genome> population, GamePanel gp) {
        float outputs[] = {0, 0, 0};

        for (Genome gene : population) {
            float fitness = 0;
            gene.setFitness(0);

            float inputs[] = {
                    gp.player.world_x, gp.player.world_y,
                    gp.player.speed, gp.player.direction,
                    gp.player.special_counter, gp.player.attacking ? 1 : 0,
                    gp.player.health, gp.player.defense,
                    gp.player.offense, gp.player.invincible ? 1 : 0,
                    gp.player.special_attacking ? 1 : 0, gp.player.class_type,

                    monster.world_x, monster.world_y,
                    monster.speed, monster.direction,
                    monster.health, monster.defense,
                    monster.offense, monster.invincible ? 1 : 0,
                    monster.alive ? 1 : 0
            };

            outputs = gene.evaluateNetwork(inputs);

            System.out.println("Outputs: " + Arrays.toString(outputs));

            float expected[] = { 0, 0, 0 };

            if (Math.hypot(gp.player.world_x - monster.world_x, gp.player.world_y - monster.world_y) < 600) {
                float x_dist = gp.player.world_x - monster.world_x;
                float y_dist = gp.player.world_y - monster.world_y;

                int speed = 4;
                int direction = 0;
                int attacking = 1;

                if (y_dist > 0) {
                    direction = Entity.DOWN;
                } 
                if (y_dist < 0) {
                    direction = Entity.UP;
                } 
                if (x_dist > 0) {
                    direction = Entity.RIGHT;
                } 
                if (x_dist < 0) {
                    direction = Entity.LEFT;
                }
                if(gp.player.attacking) {
                    speed = 1;
                }

                expected = new float[] { attacking, speed, direction };
            } else {
                expected = new float[] { 0, 0, 0 };
            }

            System.out.println("Expected: " + Arrays.toString(expected));

            for (int i = 0; i < outputs.length; i++) {
                fitness += (1 - Math.abs(expected[i] - outputs[i]));
            }

            fitness = fitness * fitness;

            gene.setFitness(fitness);
        }
    }

    public void periodic(EnemyChoice choice, GamePanel gp) {
        pool.evaluateFitness(choice, gp);
        topGenome = pool.getTopGenome();
        System.out.println("TopFitness : " + topGenome.getPoints());
        System.out.println("Generation : " + generation);

        pool.breedNewGeneration();
        generation++;

        System.out.println(topGenome.evaluateNetwork(new float[] {
                gp.player.world_x, gp.player.world_y,
                gp.player.speed, gp.player.direction,
                gp.player.special_counter, gp.player.attacking ? 1 : 0,
                gp.player.health, gp.player.defense,
                gp.player.offense, gp.player.invincible ? 1 : 0,
                gp.player.special_attacking ? 1 : 0, gp.player.class_type,

                monster.world_x, monster.world_y,
                monster.speed, monster.direction,
                monster.health, monster.defense,
                monster.offense, monster.invincible ? 1 : 0,
                monster.alive ? 1 : 0
        }));
    }
}
