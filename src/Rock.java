public class Rock extends Entity {
    public Rock() {
        super();
    }

    public void collidedWithPlayer(Player player) {
        if (CollisionChecker.check_collision(this, player)) {
            move(player);
        }
    }

    public void move(Player player) {
        int direction = player.direction;
        if (direction == DOWN) {
            world_y += speed;
        } else if (direction == LEFT) {
            world_x -= speed;
        } else if (direction == RIGHT) {
            world_x += speed;
        } else if (direction == UP) {
            world_y -= speed;
        }
    }

    public void detectPlate() {
        // if()
    }
}