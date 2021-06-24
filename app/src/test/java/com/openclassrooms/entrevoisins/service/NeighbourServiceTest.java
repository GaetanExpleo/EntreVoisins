package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void addNeighboursWithSuccess(){
        Neighbour neighbour = new Neighbour(13,"Olivier","http:test","Pas loin","0123456789","Je ne parle pas beaucoup de moi",false);
        service.createNeighbour(neighbour);
        assertTrue(service.getNeighbours().contains(neighbour));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

   @Test
   public void makeNeighbourFavoriteWithSuccess(){
        Neighbour neighbourToFavorite = service.getNeighbours().get(0);
        service.setFavoriteNeighbour(neighbourToFavorite);
        assertTrue(service.getFavoriteNeighbours().contains(neighbourToFavorite));
   }

   @Test
    public void removeNeighbourToFavoriteWithSuccess(){
       Neighbour neighbourToFavorite = service.getNeighbours().get(0);
       Neighbour neighbourToFavorite2 = service.getNeighbours().get(2);
       service.setFavoriteNeighbour(neighbourToFavorite);
       service.setFavoriteNeighbour(neighbourToFavorite2);
       service.setFavoriteNeighbour(neighbourToFavorite);
       assertTrue(service.getFavoriteNeighbours().contains(neighbourToFavorite2));
       assertFalse(service.getFavoriteNeighbours().contains(neighbourToFavorite));
   }
}
