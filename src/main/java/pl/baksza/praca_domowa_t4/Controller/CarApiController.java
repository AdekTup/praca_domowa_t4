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
            model.addAttribute("newcar",new Car());
            return "AllCarsForm";
        }
        return "";
    }

//    Optional<Car> findcar = carService.getCarById(1L);
//        if (findcar.isPresent()) {
//        Car car = findcar.get();
//        model.addAttribute("car", car);
//        return "AllCarsForm";
//    }
//    public ResponseEntity<CollectionModel<Car>> getAllCars(){
//        List<Car> carList = carService.getAllCars();
//        carList.forEach(car -> car.addIf(!car.hasLinks(), () -> linkTo(CarApiController.class).slash(car.getId()).withSelfRel()));
//        Link link = linkTo(CarApiController.class).withSelfRel();
//        return ResponseEntity.ok(new CollectionModel<>(carList, link));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById (@PathVariable long id) {
        Optional<Car> findcar = carService.getCarById(id);
        if (findcar.isPresent()) {
            return new ResponseEntity<>(findcar.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Car>> getCarByColor (@PathVariable String color) {
        List<Car> findcolorcar = carService.getCarByColor(color);
        if (!findcolorcar.isEmpty()) {
            return new ResponseEntity<>(findcolorcar, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-car")
    public String addCar(@ModelAttribute Car car) {
        System.out.println(car);
        return "redirect:/cars";
    }
//    public ResponseEntity addCar(@Validated @RequestBody Car car) {
//        if (carService.addCar(car)) {
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @PutMapping
    public ResponseEntity<Car> modCar(@RequestBody Car car) {
        Optional<Car> findcar = carService.getCarById(car.getId());
        if (findcar.isPresent()) {
            Car carmod = findcar.get();
            carmod.setMark(car.getMark());
            carmod.setModel(car.getModel());
            carmod.setColor(car.getColor());
            return new ResponseEntity<>(carmod, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/model/{id}/{model}")
    public ResponseEntity<Car> modModelCar(@PathVariable long id, @PathVariable String model) {
        Optional<Car> findcar = carService.getCarById(id);
        if (findcar.isPresent()) {
            Car carmod = findcar.get();
            carmod.setModel(model);
            return new ResponseEntity<>(carmod, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCar(@PathVariable long id) {
        if (carService.deleteCar(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
