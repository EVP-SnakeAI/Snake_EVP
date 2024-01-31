// SnakeAI.cpp : This file contains the 'main' function. Program execution begins and ends there.
//
public class HamiltonianCycleOnGrid {
    int ARENA_HEIGHT = 20;
    int ARENA_WIDTH = 27;

    int ARENA_SIZE = ((ARENA_WIDTH) *(ARENA_HEIGHT));

    int[] tourToNumber= new int[ARENA_SIZE];

    /* Take an x,y coordinate, and turn it into an index in the tour */
    int getPathNumber(int x, int y) {
        return tourToNumber[x + ARENA_WIDTH * y];
    }

    int path_distance(int a, int b) {
        if (a < b)
            return b - a - 1;
        return b - a - 1 + ARENA_SIZE;
    }

    class Food
    {
        int x = 2 * ARENA_WIDTH / 3;
        int y = 2 * ARENA_HEIGHT / 3;
        int value = 1;
    }

    Food food = new Food();

    public enum SnakeDirection {
        None(0) ,
        Right(1) ,
        Left(2) ,
        Up(4) ,
        Down(8);
        public final int x;
        private SnakeDirection(int x){this.x =x;}

        public SnakeDirection bitwiseOr(SnakeDirection other){
            return SnakeDirection.values()[this.ordinal() | other.ordinal()];
        }
    };


    class Snake
    {
        int head_x = ARENA_WIDTH / 3;
        int head_y = ARENA_HEIGHT / 3;
        int tail_x = ARENA_WIDTH / 3;
        int tail_y = ARENA_HEIGHT / 3;
        int growth_length = 1;
        int drawn_length = 1;

        /* Store the body in two parts.  For each x,y store the direction
           that the snake come *from* and the direction it is going to.
           So if a snake goes moves down, the previous head square gets
           the 'out' set to down, and the new head square gets the in set
           to down. */
        SnakeDirection[] snake_body_in = new SnakeDirection [ARENA_SIZE];
        SnakeDirection[] snake_body_out = new SnakeDirection [ ARENA_SIZE];

        SnakeDirection get_snake_body_in_at ( int x, int y){
            return snake_body_in[x + y * ARENA_WIDTH];
        }
        SnakeDirection get_snake_body_out_at ( int x, int y){
            return snake_body_out[x + y * ARENA_WIDTH];
        }


        void set_snake_body_in_at ( int x, int y, SnakeDirection new_body_dir){
            snake_body_in[x + y * ARENA_WIDTH] = new_body_dir;
        }

        void set_snake_body_out_at ( int x, int y, SnakeDirection new_body_dir){
            snake_body_out[x + y * ARENA_WIDTH] = new_body_dir;
        }

        Boolean has_snake_at ( int x, int y){
            return get_snake_body_in_at(x, y) != SnakeDirection.None  || get_snake_body_out_at(x, y) != SnakeDirection.None;
        }

        final char get_snake_body_glyph_at( int x, int y){
            SnakeDirection dir = get_snake_body_in_at(x, y).bitwiseOr(get_snake_body_out_at(x, y));
            Boolean is_tail = tail_x == x && tail_y == y;

            if (dir == SnakeDirection.Right) {
                return is_tail ? '╶' : '╺';
            } else if (dir == SnakeDirection.Left) {
                return is_tail ? '╴' : '╸';
            } else if (dir == SnakeDirection.Up) {
                return is_tail ? '╵' : '╹';
            } else if (dir == SnakeDirection.Down) {
                return is_tail ? '╷' : '╻';
            } else if (dir == SnakeDirection.Right.bitwiseOr(SnakeDirection.Left)) {
                return '━';
            } else if (dir == SnakeDirection.Up.bitwiseOr(SnakeDirection.Down)) {
                return '┃';
            } else if (dir == SnakeDirection.Up.bitwiseOr(SnakeDirection.Left)) {
                return '┛';
            } else if (dir == SnakeDirection.Up.bitwiseOr(SnakeDirection.Right)) {
                return '┗';
            } else if (dir == SnakeDirection.Down.bitwiseOr(SnakeDirection.Left)) {
                return '┓';
            } else if (dir == SnakeDirection.Down.bitwiseOr(SnakeDirection.Right)) {
                return '┏';
            } else {
                return ' ';
            }
        }

        SnakeDirection reverse_direction (SnakeDirection dir){
            switch (dir) {
                case Right:
                    return SnakeDirection.Left;
                case Left:
                    return SnakeDirection.Right;
                case Up:
                    return SnakeDirection.Down;
                case Down:
                    return SnakeDirection.Up;
            }
            return SnakeDirection.None;
        }

        void move_snake_head (SnakeDirection dir){
            int x = head_x;
            int y = head_y;
            switch (dir) {
                case Right:
                    ++x;
                    break;
                case Left:
                    --x;
                    break;
                case Down:
                    ++y;
                    break;
                case Up:
                    --y;
                    break;
            }

            // Now update the snake_body
            set_snake_body_out_at(head_x, head_y, dir);
            set_snake_body_in_at(x, y, reverse_direction(dir));

            head_x = x;
            head_y = y;

            if (head_x == food.x && head_y == food.y) {
                // eat food
                growth_length += food.value;
                food.value++;
                if (drawn_length < ARENA_SIZE - 1) {
                    do {
                        food.x = (int)(Math.random() * 32767) % ARENA_WIDTH;
                        food.y = (int)(Math.random() * 32767) % ARENA_HEIGHT;
                    } while (has_snake_at(food.x, food.y));
                }
            }

            if (growth_length > 0) {
                growth_length--;
                drawn_length++;
            } else {
                /* Now shrink the tail */
                int tailX = tail_x;
                int tailY = tail_y;
                switch (get_snake_body_out_at(tail_x, tail_y)) {
                    case Right:
                        ++tailX;
                        break;
                    case Left:
                        --tailX;
                        break;
                    case Down:
                        ++tailY;
                        break;
                    case Up:
                        --tailY;
                        break;
                }
                set_snake_body_out_at(tail_x, tail_y, SnakeDirection.None);
                set_snake_body_in_at(tailX, tailY, SnakeDirection.None);
                tail_x = tailX;
                tail_y = tailY;
            }
        }
    }

    Snake snake = new Snake();

    public class Maze
    {
        class Node {
            public Boolean visited;
            public Boolean canGoRight;
            public Boolean canGoDown;
            Node()
            {
                this.visited=false;
                this.canGoRight=false;
                this.canGoDown=false;
            }
        } ;
        Node[] nodes = new Node[ARENA_SIZE/4];
        Maze(){
            for (int i = 0; i < ARENA_SIZE/4; i++) {
               Node n = new Node();
               nodes[i]=n;
            }
        }
        void markVisited ( int x, int y){
            nodes[x + y * ARENA_WIDTH / 2].visited = true;
        }
        void markCanGoRight ( int x, int y){
            nodes[x + y * ARENA_WIDTH / 2].canGoRight = true;
        }
        void markCanGoDown ( int x, int y){
            nodes[x + y * ARENA_WIDTH / 2].canGoDown = true;
        }
        Boolean canGoRight ( int x, int y){
            return nodes[x + y * ARENA_WIDTH / 2].canGoRight;
        }
        Boolean canGoDown ( int x, int y){
            return nodes[x + y * ARENA_WIDTH / 2].canGoDown;
        }
        Boolean canGoLeft ( int x, int y){
            if (x == 0) return false;
            return nodes[(x - 1) + y * ARENA_WIDTH / 2].canGoRight;
        }

        Boolean canGoUp ( int x, int y){
            if (y == 0) return false;
            return nodes[x + (y - 1) * ARENA_WIDTH / 2].canGoDown;
        }

        Boolean isVisited ( int x, int y){
            return nodes[x + y * ARENA_WIDTH / 2].visited;
        }

        void generate () {
            generate_r(-1, -1, 0, 0);
            generateTourNumber();
        }

        void generate_r ( int fromx, int fromy, int x, int y){
            if (x < 0 || y < 0 || x >= ARENA_WIDTH / 2 || y >= ARENA_HEIGHT / 2)
                return;
            if (isVisited(x, y))
                return;
            markVisited(x, y);

            if (fromx != -1) {
                if (fromx < x)
                    markCanGoRight(fromx, fromy);
                else if (fromx > x)
                    markCanGoRight(x, y);
                else if (fromy < y)
                    markCanGoDown(fromx, fromy);
                else if (fromy > y)
                    markCanGoDown(x, y);

                //Remove wall between fromx and fromy
            }

            /* We want to visit the four connected nodes randomly,
             * so we just visit two randomly (maybe already visited)
             * then just visit them all non-randomly.  It's okay to
             * visit the same node twice */
            for (int i = 0; i < 2; i++) {
                int r = (int)(Math.random() * 32767) % 4;
                switch (r) {
                    case 0:
                        generate_r(x, y, x - 1, y);
                        break;
                    case 1:
                        generate_r(x, y, x + 1, y);
                        break;
                    case 2:
                        generate_r(x, y, x, y - 1);
                        break;
                    case 3:
                        generate_r(x, y, x, y + 1);
                        break;
                }
            }
            generate_r(x, y, x - 1, y);
            generate_r(x, y, x + 1, y);
            generate_r(x, y, x, y + 1);
            generate_r(x, y, x, y - 1);
        }

        SnakeDirection findNextDir ( int x, int y, SnakeDirection dir){
            if (dir == SnakeDirection.Right) {
                if (canGoUp(x, y))
                    return SnakeDirection.Up;
                if (canGoRight(x, y))
                    return SnakeDirection.Right;
                if (canGoDown(x, y))
                    return SnakeDirection.Down;
                return SnakeDirection.Left;
            } else if (dir == SnakeDirection.Down) {
                if (canGoRight(x, y))
                    return SnakeDirection.Right;
                if (canGoDown(x, y))
                    return SnakeDirection.Down;
                if (canGoLeft(x, y))
                    return SnakeDirection.Left;
                return SnakeDirection.Up;
            } else if (dir == SnakeDirection.Left) {
                if (canGoDown(x, y))
                    return SnakeDirection.Down;
                if (canGoLeft(x, y))
                    return SnakeDirection.Left;
                if (canGoUp(x, y))
                    return SnakeDirection.Up;
                return SnakeDirection.Right;
            } else if (dir == SnakeDirection.Up) {
                if (canGoLeft(x, y))
                    return SnakeDirection.Left;
                if (canGoUp(x, y))
                    return SnakeDirection.Up;
                if (canGoRight(x, y))
                    return SnakeDirection.Right;
                return SnakeDirection.Down;
            }
            return (SnakeDirection.None); //Unreachable
        }
        void setTourNumber ( int x, int y, int number){
            if (getPathNumber(x, y) != 0)
                return; /* Back to the starting node */
            tourToNumber[x + ARENA_WIDTH * y] = number;
        }

        void generateTourNumber () {
            final int start_x = 0;
            final int start_y = 0;
            int x = start_x;
            int y = start_y;
            final SnakeDirection start_dir = canGoDown(x, y) ? SnakeDirection.Up : SnakeDirection.Left;
            SnakeDirection dir = start_dir;
            int number = 0;
            do {
                SnakeDirection nextDir = findNextDir(x, y, dir);
                switch (dir) {
                    case Right:
                        setTourNumber(x * 2, y * 2, number++);
                        if (nextDir == dir || nextDir == SnakeDirection.Down || nextDir == SnakeDirection.Left)
                            setTourNumber(x * 2 + 1, y * 2, number++);
                        if (nextDir == SnakeDirection.Down || nextDir == SnakeDirection.Left)
                            setTourNumber(x * 2 + 1, y * 2 + 1, number++);
                        if (nextDir == SnakeDirection.Left)
                            setTourNumber(x * 2, y * 2 + 1, number++);
                        break;
                    case Down:
                        setTourNumber(x * 2 + 1, y * 2, number++);
                        if (nextDir == dir || nextDir == SnakeDirection.Left || nextDir == SnakeDirection.Up)
                            setTourNumber(x * 2 + 1, y * 2 + 1, number++);
                        if (nextDir == SnakeDirection.Left || nextDir == SnakeDirection.Up)
                            setTourNumber(x * 2, y * 2 + 1, number++);
                        if (nextDir == SnakeDirection.Up)
                            setTourNumber(x * 2, y * 2, number++);
                        break;
                    case Left:
                        setTourNumber(x * 2 + 1, y * 2 + 1, number++);
                        if (nextDir == dir || nextDir == SnakeDirection.Up || nextDir == SnakeDirection.Right)
                            setTourNumber(x * 2, y * 2 + 1, number++);
                        if (nextDir == SnakeDirection.Up || nextDir == SnakeDirection.Right)
                            setTourNumber(x * 2, y * 2, number++);
                        if (nextDir == SnakeDirection.Right)
                            setTourNumber(x * 2 + 1, y * 2, number++);
                        break;
                    case Up:
                        setTourNumber(x * 2, y * 2 + 1, number++);
                        if (nextDir == dir || nextDir == SnakeDirection.Right || nextDir == SnakeDirection.Down)
                            setTourNumber(x * 2, y * 2, number++);
                        if (nextDir == SnakeDirection.Right || nextDir == SnakeDirection.Down)
                            setTourNumber(x * 2 + 1, y * 2, number++);
                        if (nextDir == SnakeDirection.Down)
                            setTourNumber(x * 2 + 1, y * 2 + 1, number++);
                        break;
                }
                dir = nextDir;

                switch (nextDir) {
                    case Right:
                        ++x;
                        break;
                    case Left:
                        --x;
                        break;
                    case Down:
                        ++y;
                        break;
                    case Up:
                        --y;
                        break;
                }

            } while (number != ARENA_SIZE); //Loop until we return to the start
        }
    }

    ;

    Boolean is_outside_maze(int x, int y) {
        return x < 0 || y < 0 || x >= ARENA_WIDTH || y >= ARENA_HEIGHT;
    }

    Boolean check_for_collision(int x, int y) {
        if (is_outside_maze(x, y))
            return true;

        //return snake.has_snake_at(x, y);
        return false;
    }

    SnakeDirection aiGetNewSnakeDirection() {
        int x = snake.head_x;
        int y = snake.head_y;
        final int pathNumber = getPathNumber(x, y);
        final int distanceToFood = path_distance(pathNumber, getPathNumber(food.x, food.y));
        final int distanceToTail = path_distance(pathNumber, getPathNumber(snake.tail_x, snake.tail_y));
        int cuttingAmountAvailable = distanceToTail - snake.growth_length - 3 /* Allow a small buffer */;
        final int numEmptySquaresOnBoard = ARENA_SIZE - snake.drawn_length - snake.growth_length - food.value;
        // If we don't have much space (i.e. snake is 75% of board) then don't take any shortcuts */
        if (numEmptySquaresOnBoard < ARENA_SIZE / 2)
            cuttingAmountAvailable = 0;
        else if (distanceToFood < distanceToTail) { /* We will eat the food on the way to the tail, so take that into account */
            cuttingAmountAvailable -= food.value;
            /* Once we ate that food, we might end up with another food suddenly appearing in front of us */
            if ((distanceToTail - distanceToFood) * 4 > numEmptySquaresOnBoard) /* 25% chance of another number appearing */
                cuttingAmountAvailable -= 10;
        }
        int cuttingAmountDesired = distanceToFood;
        if (cuttingAmountDesired < cuttingAmountAvailable)
            cuttingAmountAvailable = cuttingAmountDesired;
        if (cuttingAmountAvailable < 0)
            cuttingAmountAvailable = 0;
        // cuttingAmountAvailable is now the maximum amount that we can cut by

        Boolean canGoRight = !check_for_collision(x + 1, y);
        Boolean canGoLeft = !check_for_collision(x - 1, y);
        Boolean canGoDown = !check_for_collision(x, y + 1);
        Boolean canGoUp = !check_for_collision(x, y - 1);

        SnakeDirection bestDir = null;
        int bestDist = -1;
        if (canGoRight) {
            int dist = path_distance(pathNumber, getPathNumber(x + 1, y));
            if (dist <= cuttingAmountAvailable && dist > bestDist) {
                bestDir = SnakeDirection.Right;
                bestDist = dist;
            }
        }
        if (canGoLeft) {
            int dist = path_distance(pathNumber, getPathNumber(x - 1, y));
            if (dist <= cuttingAmountAvailable && dist > bestDist) {
                bestDir = SnakeDirection.Left;
                bestDist = dist;
            }
        }
        if (canGoDown) {
            int dist = path_distance(pathNumber, getPathNumber(x, y + 1));
            if (dist <= cuttingAmountAvailable && dist > bestDist) {
                bestDir = SnakeDirection.Down;
                bestDist = dist;
            }
        }
        if (canGoUp) {
            int dist = path_distance(pathNumber, getPathNumber(x, y - 1));
            if (dist <= cuttingAmountAvailable && dist > bestDist) {
                bestDir = SnakeDirection.Up;
                bestDist = dist;
            }
        }
        if (bestDist >= 0)
            return bestDir;

        if (canGoUp)
            return SnakeDirection.Up;
        if (canGoLeft)
            return SnakeDirection.Left;
        if (canGoDown)
            return SnakeDirection.Down;
        if (canGoRight)
            return SnakeDirection.Right;
        //System.exit(0);
        return SnakeDirection.None;
    }

    public Maze maze = new Maze();

    HamiltonianCycleOnGrid(int jatekMeret){
        this.ARENA_HEIGHT = 20;
        this.ARENA_WIDTH = 27;
        this.ARENA_SIZE=(ARENA_HEIGHT*ARENA_WIDTH);
        maze= new Maze();
        maze.generate();
    }
    public char getDir(){
        SnakeDirection new_dir = aiGetNewSnakeDirection();
        snake.move_snake_head(new_dir);
        switch (new_dir) {
            case Right:
                return 'R';
            case Left:
                return 'L';
            case Up:
                return 'U';
            case Down:
                return 'D';
        }
        return ' ';
    }

    Boolean doTick() {
        SnakeDirection new_dir = aiGetNewSnakeDirection();
        if (new_dir == SnakeDirection.None)
            return false;
        snake.move_snake_head(new_dir);
        return true;
    }
}


