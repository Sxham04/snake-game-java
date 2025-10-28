package proj.snake.src;

public class Snake {
    // determines the snake's position
    int[] snakexLength = new int[750];
    int[] snakeyLength = new int[750];

    // snake length and whether the snake has moved yet or not
    int lengthOfSnake;
    int moves;

    // snake's direction
    boolean left;
    boolean right;
    boolean up;
    boolean down;

    // whether the snake is dead or not
    boolean death;

    // constructor
    public Snake(){
        this.left=false;
        this.right=false;
        this.up=false;
        this.down=false;
        this.death=false;
        this.lengthOfSnake=5;
        this.moves=0;
    }

    // move right
    public void moveRight(){
        if (this.moves != 0 && !this.death) {
                this.moves++;
                if (!this.left) {
                    this.right = true;
                } else {
                    this.right = false;
                    this.left = true;
                }
                this.up = false;
                this.down = false;
        }
    }

    // move left
    public void moveLeft(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.right) {
                this.left = true;
            } else {
                this.left = false;
                this.right = true;
            }
            this.up = false;
            this.down = false;
        }
    }

    // move up
    public void moveUp(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.down) {
                this.up = true;
            } else {
                this.up = false;
                this.down = true;
            }
            this.left = false;
            this.right = false;
        }
    }

    // move down
    public void moveDown(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.up) {
                this.down = true;
            } else {
                this.down = false;
                this.up = true;
            }
            this.left = false;
            this.right = false;
        }
    }

    // dead function to avoid rewriting code multiple times
    public void dead() {
        // make the snake unable to move
        this.right = false;
        this.left = false;
        this.up = false;
        this.down = false;
        this.death = true;
    }

    // snake movement to the right
    public void movementRight(){
        // move the head position to the next index
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            // move the snakeyLength position
            this.snakeyLength[i + 1] = this.snakeyLength[i];
        }
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            // move the snakexLength position
            if (i == 0) {
                this.snakexLength[i] = this.snakexLength[i] + 6;
            } else {
                this.snakexLength[i] = this.snakexLength[i - 1];
            }
            // if it has passed the right edge
            if (this.snakexLength[0] > 637) {
                // move the head back onto the board
                this.snakexLength[0] -= 6;
                // dead
                dead();
            }
        }
    }

    // snake movement to the left
    public void movementLeft(){
        // move the head position to the next index
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            // move the snakeyLength position
            this.snakeyLength[i + 1] = this.snakeyLength[i];
        }
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            // move the snakexLength position
            if (i == 0) {
                this.snakexLength[i] = this.snakexLength[i] - 6;
            } else {
                this.snakexLength[i] = this.snakexLength[i - 1];
            }
            // if it has passed the left edge
            if (this.snakexLength[0] < 25) {
                // move the head back onto the board
                this.snakexLength[0] += 6;
                // dead
                dead();
            }
        }
    }

    // snake movement upwards
    public void movementUp(){
        // move the head position to the next index
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            // move the snakexLength position
            this.snakexLength[i + 1] = this.snakexLength[i];
        }
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            // move the snakeyLength position
            if (i == 0) {
                this.snakeyLength[i] = this.snakeyLength[i] - 6;
            } else {
                this.snakeyLength[i] = this.snakeyLength[i - 1];
            }
            // if it has passed the top edge
            if (this.snakeyLength[0] < 73) {
                // move the head back onto the board
                this.snakeyLength[0] += 6;
                // dead
                dead();
            }
        }
    }

    // snake movement downwards
    public void movementDown(){
        // move the head position to the next index
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            // move the snakexLength position
            this.snakexLength[i + 1] = this.snakexLength[i];
        }
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            // move the snakeyLength position
            if (i == 0) {
                this.snakeyLength[i] = this.snakeyLength[i] + 6;
            } else {
                this.snakeyLength[i] = this.snakeyLength[i - 1];
            }
            // if it has passed the bottom edge
            if (this.snakeyLength[0] > 679) {
                // move the head back onto the board
                this.snakeyLength[0] -= 6;
                // dead
                dead();
            }
        }
    }
}