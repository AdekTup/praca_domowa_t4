package pl.baksza.praca_domowa_t4.Service;

import org.springframework.stereotype.Service;
import pl.baksza.praca_domowa_t4.Model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {
    List<Car> carslist;

    public CarService() {
        carslist = new ArrayList<>();

        Car car1 = new Car(1l,"BMW","i8","red");
        Car car2 = new Car(2l,"Fiat","Punto","green");
        Car car3 = new Car(3l,"Renault","Clieo","red");
        Car car4 = new Car(4l,"Opel","Corsa","yellow");

        carslist.add(car1);
        carslist.add(car2);
        carslist.add(car3);
        carslist.add(car4);
    }

    public List<Car> getAllCars() {
        return carslist;
    }

    public Optional<Car> getCarById(long id) {
        return carslist.stream().filter(car -> car.getId() == id).findFirst();
    }

    public boolean addCar(Car caradd) {
            caradd.setId(generateId());
            System.out.println("ID: " + caradd.getId());
            return carslist.add(caradd);
    }

    public boolean updateCar(long id, Car car) {
        Optional<Car> findcar = getCarById(id);

        if (findcar.isPresent()) {
            Car carmod = findcar.get();
            carmod.setMark(car.getMark());
            carmod.setModel(car.getModel());
            carmod.setColor(car.getColor());
            return true;
        }
        return false;
    }

    public boolean deleteCar(long id) {
        Optional<Car> carfind = getCarById(id);
        
        if (carfind.isPresent()) {
            carslist.remove(carfind.get());
            return true;
        }
        return false;
    }

    public List<Car> getCarByColor(String color){
        List<Car> carscolorlist = carslist.stream().filter(car -> car.getColor().equals(color)).collect(Collectors.toList());
        return carscolorlist;
    }

    private long generateId() {
        Car newcar = new Car();
        Car carmax = carslist.stream().reduce(newcar, (car1, car2) -> car1.getId() > car2.getId() ? car1 : car2);  // 10
        return carmax.getId()+1;
    }
}
