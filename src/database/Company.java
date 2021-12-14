package database;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Company implements DataObject{

    private int id;
    private String name;
    private int capital;
    private int employees;
    private String location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
    }

    @Override
    public DataObjectType getType() {
        return DataObjectType.Company;
    }

    @Override
    public String toString(){
        return getName();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
