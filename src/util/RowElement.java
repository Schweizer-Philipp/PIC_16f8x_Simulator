package util;

/**
 * hso.ra.java.simulator.pic16f8x
 * microController
 * Mike Bruder, Philipp Schweizer
 * 27.10.2018
 */
public class RowElement<E> {
    private E leftElement;
    private E rightElement;

    public RowElement(E leftElement, E rightElement) {
        this.leftElement = leftElement;
        this.rightElement = rightElement;
    }

    public E getLeftElement() {
        return leftElement;
    }

    public E getRightElement() {
        return rightElement;
    }

    @Override
    public String toString() {
        return String.format("%10s %10s", leftElement.toString(), rightElement.toString());
    }
}
