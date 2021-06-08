package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        List<Neighbour> favoriteNeighbourList = new ArrayList<>();
        for (Neighbour neighbour : neighbours){
            if(neighbour.isFavoris()){
                favoriteNeighbourList.add(neighbour);
            }
        }
        return favoriteNeighbourList;
    }

    /**
     * @param neighbour
     */
    @Override
    public void setFavoriteNeighbour(Neighbour neighbour){
        for (Neighbour neighbour1: neighbours) {
            if (neighbour1.equals(neighbour)) {
                neighbour1.setFavoris(!neighbour1.isFavoris());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }
}
