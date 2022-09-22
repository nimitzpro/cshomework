
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.JUnitCore;


/**
 * Tester class, runs tests on various Car methods
 */
public class Tester {

    /**
     * stopTest checks that Car's stop() method works by checking if it is moving after stop() is used
     */
    @Test
    public void stopTest( ) {
        final Car c = new Car();
        c.stop();
        assertEquals(false, c.isMoving());
    }

    /**
     * stopTest2 is a variation on the stopTest check
     */
    @Test
    public void stopTest2( ) {
        final Car c = new Car();
        c.move();
        c.stop();
        assertEquals(false, c.isMoving());
    }

    /**
     * moveTest checks that Car's move() method works by checking if it is at its original position
     * after move() is used, using the incrementSpeedInVerticalDirection() method
     */
    @Test
    public void moveTest( ) {
        Car c = new Car();
        c.incrementSpeedInVerticalDirection();
        c.move();
        assertEquals(false, c.isAtOriginalPosition());
    }

    /**
     * moveTest2 is a variation on the moveTest check, using
     * incrementSpeedInHorizontalDirection instead
     */
    @Test
    public void moveTest2( ) {
        Car c = new Car();
        c.incrementSpeedInHorizontalDirection();
        c.incrementSpeedInHorizontalDirection();
        c.move();
        assertEquals(false, c.isAtOriginalPosition());
    }

    /**
     * isMovingTest checks if the isMoving method returns true when the Car is moving
     */
    @Test
    public void isMovingTest( ) {
        Car c = new Car();
        c.stop();
        c.incrementSpeedInHorizontalDirection();
        assertEquals(true, c.isMoving());
    }

    /**
     * isMovingTest2 checks if the isMoving method returns false when the Car is not moving.
     * isMoving() checks the Car's speed to be 0 in both horizontal and vertical directions
     */
    @Test
    public void isMovingTest2( ) {
        Car c = new Car();
        c.stop();
        c.move();
        assertEquals(false, c.isMoving());
    }

    /**
     * isAtOriginalPositionTest tests isAtOriginalPosition(), which compares the
     * Car's current position to its origin and should return true
     */
    @Test
    public void isAtOriginalPositionTest( ) {
        Car c = new Car();
        c.incrementSpeedInHorizontalDirection();
        assertEquals(true, c.isAtOriginalPosition());
    }

    /**
     * isAtOriginalPositionTest2 tests isAtOriginalPosition() by first
     * moving the Car and checking if isAtOriginalPosition() returns false
     */
    @Test
    public void isAtOriginalPositionTest2( ) {
        Car c = new Car();
        c.incrementSpeedInHorizontalDirection();
        c.move();
        c.stop();
        assertEquals(false, c.isAtOriginalPosition());
    }

    /**
     * incrementSpeedInHorizontalDirectionTest checks if the horizontal movement method
     * executes as intended, by changing the car from a stopped to moving state,
     * and checking if it is moving
     */
    @Test
    public void incrementSpeedInHorizontalDirectionTest( ) {
        Car c = new Car();
        c.stop();
        c.incrementSpeedInHorizontalDirection();
        assertEquals(true, c.isMoving());
    }

    /**
     * incrementSpeedInHorizontalDirectionTest2 is a variation on the first
     * incrementSpeedInHorizontalDirectionTest
     */
    @Test
    public void incrementSpeedInHorizontalDirectionTest2( ) {
        Car c = new Car();
        c.decrementSpeedInHorizontalDirection();
        c.incrementSpeedInHorizontalDirection();
        c.incrementSpeedInHorizontalDirection();
        assertEquals(true, c.isMoving());
    }

}
