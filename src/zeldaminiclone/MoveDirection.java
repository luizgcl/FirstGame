package zeldaminiclone;

public enum MoveDirection {

    NONE(new int[] {0, 0}), UP(new int[] {0, 1}), DOWN(new int[] {0, 1}), LEFT(new int[] {4, 5}), RIGHT(new int[] {2, 3}),;

    int[] value;

    MoveDirection(int[] value) {
        this.value = value;
    }

}
