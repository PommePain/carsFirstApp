package carsLittleApp.service;

import carsLittleApp.app.Cars;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class serviceCars {
    private static final myBdd myBdd = new myBdd();

    public static void updateCarsDb(Cars car) {
        try {
            String sqlCheck = "SELECT id, name FROM cars WHERE name = " + car.getName();
            boolean checkIfCarExist = myBdd.isExist(sqlCheck);
            if (checkIfCarExist) {
                String sqlUpdateQuery = "UPDATE cars SET name = ?, brand = ?, price = ?, speed = ?," +
                        " dateCreation = ?, colors = ?";
                PreparedStatement updateStatement = myBdd.createDatabaseConnection().prepareStatement(sqlUpdateQuery);

                updateStatement.setString(1, car.getName());
                updateStatement.setString(2, car.getBrand());
                updateStatement.setInt(3, car.getPrice());
                updateStatement.setInt(4, car.getSpeed());
                updateStatement.setString(5, car.getDateCreation());
                updateStatement.setString(6, car.getColors().toString());

                updateStatement.execute();
                updateStatement.close();
                System.out.println(car.getName() + "has been updated in Database!");
            } else {
                insertCarsInDb(car);
            }
        } catch (Exception exception) {
            System.out.println("Error during updating this car in Database : " + car.getName() + car.getId());
            exception.printStackTrace();
        }
    }

    public static void insertCarsInDb (Cars car) {
        try {
            String insertQuery = "INSERT INTO cars (name, brand, price, speed, dateCreation, colors) " +
                    "VALUES (?,?,?,?,STR_TO_DATE(?, '%d/%m/%Y'),?)";
            PreparedStatement insertStatement = myBdd.createDatabaseConnection().prepareStatement(insertQuery);
            insertStatement.setString(1, car.getName());
            insertStatement.setString(2, car.getBrand());
            insertStatement.setInt(3, car.getPrice());
            insertStatement.setInt(4, car.getSpeed());
            insertStatement.setString(5, car.getDateCreation());
            insertStatement.setString(6, car.getColors().toString());

            insertStatement.execute();
            insertStatement.close();
            System.out.println(car.getName() + " has been created!");
        } catch (Exception exception) {
            System.out.println("Error during insertion of this car in Database : " + car.getName());
            exception.printStackTrace();
        }
    }

    public static Cars getCar(String carName) {
        Cars resCar = new Cars();
        try {
            ResultSet searchedCar = myBdd.getContentFromDb("SELECT * FROM cars WHERE name = '" + carName + "'");
            if (searchedCar.next()) {
                resCar.setName(searchedCar.getString("name"));
                resCar.setBrand(searchedCar.getString("brand"));
                resCar.setPrice(searchedCar.getInt("price"));
                resCar.setSpeed(searchedCar.getInt("speed"));
                resCar.setColors(Collections.singletonList(searchedCar.getString("colors")));
                resCar.setDateCreation(searchedCar.getString("dateCreation"));
            } else {
                resCar.setName("Non trouvé");
            }
        } catch (Exception exception) {
            System.out.println("Error during getting car : " + carName);
            exception.printStackTrace();
        }
        return resCar;
    }

    public static List<Cars> getAllCars() {
        List<Cars> allCars = new ArrayList<>();
        try {
            String query = "SELECT * FROM cars ORDER by brand";
            ResultSet allCarsDb = myBdd.getContentFromDb(query);
            while (allCarsDb.next()) {
                Cars car = new Cars(
                        allCarsDb.getString("name"),
                        allCarsDb.getString("brand"),
                        allCarsDb.getInt("price"),
                        allCarsDb.getInt("speed"),
                        allCarsDb.getString("dateCreation"),
                        List.of(allCarsDb.getString("colors").split(","))
                );
                allCars.add(car);
            }
        } catch (Exception exception) {
            System.out.println("Error during getting all cars");
            exception.printStackTrace();
        }
        return allCars;
    }

    public static int createCar (Cars car) {
        int check = 0;
        try {
            if (car.getName() == null || car.getName().isEmpty() || car.getBrand().isEmpty()) return check;
            if (car.getColors().isEmpty()) car.setColors(List.of("Aucune"));
            insertCarsInDb(car);
        } catch (Exception exception) {
            System.out.println("Error during creating car " + car.getName());
            exception.printStackTrace();
        }
        return check;
    }

    public static int deleteCar(String carName) {
        int res = 0;
        try {
            ResultSet checkIfExist = myBdd.getContentFromDb("SELECT id FROM cars WHERE name = '" + carName + "'");
            if (checkIfExist.next()) {
                myBdd.deleteRow(checkIfExist.getInt("id"));
                res = 1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            System.out.println("Error during deleting car : " + carName);
            exception.printStackTrace();
        }
        return res;
    }
}
