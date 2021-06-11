package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class RemoveFavoriteNeighbourEvent {

    public Neighbour mNeighbour;

    public RemoveFavoriteNeighbourEvent(Neighbour neighbour) {
        this.mNeighbour = neighbour;
    }
}
