package model.field;

import model.exception.NotExistingFieldException;
import model.enums.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Field implements Serializable {
    private List<Edge> edges = new ArrayList<>();
    private List<Square> squares = new ArrayList<>();
    /**
     * Spawn Squares are also separated for practical issues
     */
    private List<SpawnSquare> spawnSquares = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public List<Room> getRooms() {
        return this.rooms;
    }

    public Field(int fieldNumber){
        generateField(fieldNumber);
    }

    /**
     * Generates edges and Squares depending on the field number inserted, from 1 to 3
     * @param fieldNumber   the number of the field requested
     */
    public void generateField(int fieldNumber){
        switch (fieldNumber) {
            case(1):
                /*     A   B   C   D            A   B   C   D
                 *    ---------------          ---------------
                 * 3 | b e b e B     |3     3 | 0 e 1 e 2     |3
                 *   | e       e     |        | e       e     |
                 * 2 | R e r e r e y |2     2 | 3 e 4 e 5 e 6 |2
                 *   |     e       e |        |     e       e |
                 * 1 |     w e w e Y |1     1 |     7 e 8 e 9 |1
                 *    ---------------          ---------------
                 *     A   B   C   D            A   B   C   D  */
                this.squares.add(new AmmoSquare(Color.BLUE, new Coordinate('A', 3)));//0
                this.squares.add(new AmmoSquare(Color.BLUE, new Coordinate('B',3)));//1
                addSpawnSquare(new SpawnSquare(Color.BLUE, new Coordinate('C',3)));//2
                addSpawnSquare(new SpawnSquare(Color.RED, new Coordinate('A',2)));//3
                this.squares.add(new AmmoSquare(Color.RED, new Coordinate('B',2)));//4
                this.squares.add(new AmmoSquare(Color.RED, new Coordinate('C',2)));//5
                this.squares.add(new AmmoSquare(Color.WHITE, new Coordinate('B',1)));//6
                this.squares.add(new AmmoSquare(Color.WHITE, new Coordinate('C',1)));//7
                this.squares.add(new AmmoSquare(Color.YELLOW, new Coordinate('D',2)));//8
                addSpawnSquare(new SpawnSquare(Color.YELLOW, new Coordinate('D',1)));//9
                this.edges.add(new Edge(this.squares.get(0),this.squares.get(1)));
                this.edges.add(new Edge(this.squares.get(1),this.squares.get(2)));
                this.edges.add(new Edge(this.squares.get(0),this.squares.get(3)));
                this.edges.add(new Edge(this.squares.get(3),this.squares.get(4)));
                this.edges.add(new Edge(this.squares.get(4),this.squares.get(5)));
                this.edges.add(new Edge(this.squares.get(2),this.squares.get(5)));
                this.edges.add(new Edge(this.squares.get(5),this.squares.get(6)));
                this.edges.add(new Edge(this.squares.get(6),this.squares.get(9)));
                this.edges.add(new Edge(this.squares.get(4),this.squares.get(7)));
                this.edges.add(new Edge(this.squares.get(7),this.squares.get(8)));
                this.edges.add(new Edge(this.squares.get(8),this.squares.get(9)));
                this.rooms.add(new Room(Color.RED,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.RED))
                                .collect(Collectors.toList())));
                this.rooms.add(new Room(Color.BLUE,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.BLUE))
                                .collect(Collectors.toList())));
                this.rooms.add(new Room(Color.YELLOW,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.YELLOW))
                                .collect(Collectors.toList())));
                this.rooms.add(new Room(Color.WHITE,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.WHITE))
                                .collect(Collectors.toList())));
                break;
            case(2):
                /*     A   B   C   D         A   B   C   D
                 *    ---------------       ---------------
                 * 3 | b e b e B e g |3  3 | 0 e 1 e 2 e 3 |3
                 *   | e       e   e |     | e       e   e |
                 * 2 | R e r   y e y |2  2 | 4 e 5   6 e 7 |2
                 *   |     e   e   e |     |     e   e   e |
                 * 1 |     w e y e Y |1  1 |    10 e 8 e 9 |1
                 *    ---------------       ---------------
                 *     A   B   C   D         A   B   C   D
                 */

                this.squares.add(new AmmoSquare(Color.BLUE, new Coordinate('A', 3)));
                this.squares.add(new AmmoSquare(Color.BLUE, new Coordinate('B',3)));
                addSpawnSquare(new SpawnSquare(Color.BLUE, new Coordinate('C',3)));
                this.squares.add(new AmmoSquare(Color.GREEN, new Coordinate('D',3)));
                addSpawnSquare(new SpawnSquare(Color.RED, new Coordinate('A',2)));
                this.squares.add(new AmmoSquare(Color.RED, new Coordinate('B',2)));
                this.squares.add(new AmmoSquare(Color.YELLOW, new Coordinate('C',2)));
                this.squares.add(new AmmoSquare(Color.YELLOW, new Coordinate('D',2)));
                this.squares.add(new AmmoSquare(Color.YELLOW, new Coordinate('C',1)));
                addSpawnSquare(new SpawnSquare(Color.YELLOW, new Coordinate('D',1)));
                this.squares.add(new AmmoSquare(Color.WHITE, new Coordinate('B',1)));

                this.edges.add(new Edge(this.squares.get(0),this.squares.get(1)));
                this.edges.add(new Edge(this.squares.get(1),this.squares.get(2)));
                this.edges.add(new Edge(this.squares.get(2),this.squares.get(3)));
                this.edges.add(new Edge(this.squares.get(0),this.squares.get(4)));
                this.edges.add(new Edge(this.squares.get(4),this.squares.get(5)));
                this.edges.add(new Edge(this.squares.get(5),this.squares.get(10)));
                this.edges.add(new Edge(this.squares.get(10),this.squares.get(8)));
                this.edges.add(new Edge(this.squares.get(8),this.squares.get(9)));
                this.edges.add(new Edge(this.squares.get(7),this.squares.get(9)));
                this.edges.add(new Edge(this.squares.get(6),this.squares.get(7)));
                this.edges.add(new Edge(this.squares.get(6),this.squares.get(8)));
                this.edges.add(new Edge(this.squares.get(2),this.squares.get(6)));
                this.edges.add(new Edge(this.squares.get(3),this.squares.get(7)));

                this.rooms.add(new Room(Color.RED,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.RED))
                                .collect(Collectors.toList())));
                this.rooms.add(new Room(Color.BLUE,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.BLUE))
                                .collect(Collectors.toList())));
                this.rooms.add(new Room(Color.YELLOW,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.YELLOW))
                                .collect(Collectors.toList())));
                this.rooms.add(new Room(Color.WHITE,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.WHITE))
                                .collect(Collectors.toList())));
                this.rooms.add(new Room(Color.GREEN,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.GREEN))
                                .collect(Collectors.toList())));
                break;
            case(3):
                /*     A   B   C   D         A   B   C   D
                 *    ---------------       ---------------
                 * 3 | r e b e B e g |3  3 | 0 e 2 e 3 e 4 |3
                 *   | e   e   e   e |     | e   e   e   e |
                 * 2 | R   l   y e y |2  2 | 1   5  10 e 9 |2
                 *   | e   e   e   e |     | e   e   e   e |
                 * 1 | w e w e y e Y |1  1 | 6 e 7 e 8 e 11|1
                 *    ---------------       ---------------
                 *     A   B   C   D         A   B   C   D    */
                this.squares.add(new AmmoSquare(Color.RED, new Coordinate('A',3)));
                addSpawnSquare(new SpawnSquare(Color.RED, new Coordinate('A',2)));
                this.squares.add(new AmmoSquare(Color.BLUE, new Coordinate('B',3)));
                addSpawnSquare(new SpawnSquare(Color.BLUE, new Coordinate('C',3)));
                this.squares.add(new AmmoSquare(Color.GREEN, new Coordinate('D',3)));
                this.squares.add(new AmmoSquare(Color.PURPLE, new Coordinate('B',2)));
                this.squares.add(new AmmoSquare(Color.WHITE, new Coordinate('A',1)));
                this.squares.add(new AmmoSquare(Color.WHITE, new Coordinate('B',1)));
                this.squares.add(new AmmoSquare(Color.YELLOW, new Coordinate('C',1)));
                this.squares.add(new AmmoSquare(Color.YELLOW, new Coordinate('D',2)));
                this.squares.add(new AmmoSquare(Color.YELLOW, new Coordinate('C',2)));
                addSpawnSquare(new SpawnSquare(Color.WHITE, new Coordinate('D',1)));
                this.edges.add(new Edge(this.squares.get(0),this.squares.get(1)));
                this.edges.add(new Edge(this.squares.get(1),this.squares.get(6)));
                this.edges.add(new Edge(this.squares.get(0),this.squares.get(2)));
                this.edges.add(new Edge(this.squares.get(2),this.squares.get(5)));
                this.edges.add(new Edge(this.squares.get(5),this.squares.get(7)));
                this.edges.add(new Edge(this.squares.get(6),this.squares.get(7)));
                this.edges.add(new Edge(this.squares.get(2),this.squares.get(3)));
                this.edges.add(new Edge(this.squares.get(3),this.squares.get(4)));
                this.edges.add(new Edge(this.squares.get(3),this.squares.get(10)));
                this.edges.add(new Edge(this.squares.get(4),this.squares.get(9)));
                this.edges.add(new Edge(this.squares.get(10),this.squares.get(9)));
                this.edges.add(new Edge(this.squares.get(10),this.squares.get(8)));
                this.edges.add(new Edge(this.squares.get(9),this.squares.get(11)));
                this.edges.add(new Edge(this.squares.get(7),this.squares.get(8)));
                this.edges.add(new Edge(this.squares.get(8),this.squares.get(11)));

                this.rooms.add(new Room(Color.RED,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.RED))
                                .collect(Collectors.toList())));
                this.rooms.add(new Room(Color.BLUE,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.BLUE))
                                .collect(Collectors.toList())));
                this.rooms.add(new Room(Color.YELLOW,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.YELLOW))
                                .collect(Collectors.toList())));
                this.rooms.add(new Room(Color.WHITE,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.WHITE))
                                .collect(Collectors.toList())));
                this.rooms.add(new Room(Color.GREEN,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.GREEN))
                                .collect(Collectors.toList())));
                this.rooms.add(new Room(Color.PURPLE,
                        this.squares.stream()
                                .filter(square -> square.getColor().equals(Color.PURPLE))
                                .collect(Collectors.toList())));
                break;
            default: throw new NotExistingFieldException();
        }
    }

    private void addSpawnSquare(SpawnSquare spawnSquare){
        this.squares.add(spawnSquare);
        this.spawnSquares.add(spawnSquare);
    }

    public List<SpawnSquare> getSpawnSquares() {
        return spawnSquares;
    }
}
