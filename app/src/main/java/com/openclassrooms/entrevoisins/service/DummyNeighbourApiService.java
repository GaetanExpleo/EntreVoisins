package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    private List<Neighbour> favoriteNeighbours = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        favoriteNeighbours.clear();
        for (Neighbour neighbour : neighbours){
            if(neighbour.isFavoris()){
                favoriteNeighbours.add(neighbour);
            }
        }
        return favoriteNeighbours;
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

    //@Override
    //public void removeFavoriteNeighbour(Neighbour neighbour) {
    //    for (Neighbour neighbour1 : favoriteNeighbours) {
    //        if (neighbour1.equals(neighbour)){
    //            favoriteNeighbours.remove(neighbour);
    //            return;
    //        }
    //    }
    //}

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
