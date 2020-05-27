package pl.baksza.praca_domowa_t4.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.baksza.praca_domowa_t4.Model.Car;
import pl.baksza.praca_domowa_t4.Service.CarService;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping(value = "/cars" , produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CarApiController {
    CarService carService;

    @Autowired
    public CarApiController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public String getAllCar(Model model) {
        List<Car> carList = carService.getAllCars();
        if (!carList.isEmpty()) {
            model.addAttribute("carList", carList);

            return "AllCarsForm";
        }
        return "";
    }

    @GetMapping("/getCarById/{id}")
    public String getCarById (@PathVariable long id, Model model) {
        Car carToGet = new Car();
        carToGet.setId(0);
        model.addAttribute("carToGet",carToGet);
        return "getCarById";
    }

    @PostMapping("/getCarById/{id}")
    public String getCarById (@PathVariable long id, Car getByIdCar, Model model) {
        Optional<Car> getCar = carService.getCarById(getByIdCar.getId());
        if (!getCar.isEmpty()) {
            model.addAttribute("carToGet", getCar.get());
            return "getCarById";
        }
        return "emptyList";
    }

    @GetMapping("/getCarColor/{color}")
    public String getCarByColor (@PathVariable String color, Model model) {
        Car carToGet = new Car();
        carToGet.setColor("");
        model.addAttribute("carToGet",carToGet);
        return "getCarByColor";
    }
//@PathVariable String color
    @PostMapping("/getCarColor")
    public String getCarByColor (Car car, Model model) {
        List<Car> findcolorcar = carService.getCarByColor(car.getColor());
        if (!findcolorcar.isEmpty()) {
           model.addAttribute("carColorList", findcolorcar);
           return "getCarByColorList";
        }
        return "emptyList";
    }

    @GetMapping("/addCar")
    public String addCar(Model model) {
        model.addAttribute("newcar",new Car());
        return "addCar";
    }

    @PostMapping("/addCar")
    public String addCar(@ModelAttribute Car car, Model model) {
        if (carService.addCar(car)) {
            model.addAttribute("carList", carService.getAllCars());
            return "AllCarsForm";
        }
        return "emptyList";
    }

    @GetMapping("/updateCar/{id}")
    public String updateCar (@PathVariable long id, Model model) {
        Optional<Car> upCar = carService.getCarById(id);
        if (!upCar.isEmpty()) {
            model.addAttribute("carToUpdate", upCar.get());
            return "updateCar";
        }
        return "emptyList";
    }

    @PostMapping("/updateCar/{id}")
    public String updateCar (@PathVariable long id, Car updateCar , Model model) {
        if (carService.updateCar(id, updateCar)) {
            model.addAttribute("carList", carService.getAllCars());
            return "AllCarsForm";
        }
        return "emptyList";
    }

    @GetMapping("/deleteCar/{id}")
    public String deleteCar(@PathVariable long id, Model model) {
        Optional<Car> deleCar = carService.getCarById(id);
        if (!deleCar.isEmpty()) {
            model.addAttribute("carToDelete", deleCar.get());
            return "deleteCar";
        }
        return "emptyList";
    }

    @PostMapping("/deleteCar/{id}")
    public String deleteCar(@PathVariable long id, Car deleteCar, Model model) {
        if (carService.deleteCar(id)) {
            model.addAttribute("carList", carService.getAllCars());
            return "AllCarsForm";
        }
        return "emptyList";
    }
}
