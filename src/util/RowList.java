package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * hso.ra.java.simulator.pic16f8x
 * microController
 * Mike Bruder, Philipp Schweizer
 * 27.10.2018
 */
public class RowList<E> {
    private List<RowElement<E>> elements;

    public RowList() {
        this.elements = new ArrayList<>();
    }

    public void add(E left, E right) {
        elements.add(new RowElement<>(left, right));
    }

    public int size() {
        return elements.size();
    }

    public Stream<RowElement<E>> stream() {
        return elements.stream();
    }

    public List<RowElement<E>> toList() {
        return elements;
    }


}
