public class Main {
    public static void main(String[] args){
        Car car = new Car();
        car.move();
        car.decrementSpeedInHorizontalDirection();
        car.incrementSpeedInVerticalDirection();
        car.move();
        System.out.println(car.isAtOriginalPosition());
    }
}
