package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        // Создание и сохранение списка юзеров

        List<User> users = Arrays.asList(
                new User("User1", "Lastname1", "user1@mail.ru"),
                new User("User2", "Lastname2", "user2@mail.ru"),
                new User("User3", "Lastname3", "user3@mail.ru"),
                new User("User4", "Lastname4", "user4@mail.ru")
        );

        for (User user : users) {
            userService.add(user);
        }

        // Создание и сохранение списка машин

        List<Car> cars = Arrays.asList(
                new Car("Car1", 1),
                new Car("Car2", 2),
                new Car("Car3", 3),
                new Car("Car4", 4)
        );

        for (Car car : cars) {
            userService.addCar(car);
        }

        // Получение списка пользователей и машин из базы данных

        List<User> usersFromDb = userService.listUsers();
        List<Car> carsFromDb = userService.listCars();

        // Раздать машины юзерам

        for (int i = 0; i < usersFromDb.size() && i < carsFromDb.size(); i++) {
            User user = usersFromDb.get(i);
            Car car = carsFromDb.get(i);
            user.setCar(car);
            userService.add(user);
        }

        for (User user : usersFromDb) {
            userService.add(user);
        }

        List<User> updatedUsers = userService.listUsers();
        for (User user : updatedUsers) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car = " + user.getCar());
            System.out.println();
        }

        context.close();
    }
}
