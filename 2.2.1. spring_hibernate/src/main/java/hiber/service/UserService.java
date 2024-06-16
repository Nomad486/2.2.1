package hiber.service;

import hiber.model.Car;
import hiber.model.User;

import java.util.List;

public interface UserService {
    void add(User user);

    User getUsersByCars(String model, int series);

    List<User> listUsers();

    List<Car> listCars();

    void addCar(Car car);
}
