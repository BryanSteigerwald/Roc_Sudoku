package factory;

import model.CellPosition;

import java.util.List;

public interface CluePlacementStrategy {

    List<CellPosition> removalOrder();
}