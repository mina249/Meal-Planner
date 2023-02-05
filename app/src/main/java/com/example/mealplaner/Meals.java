package com.example.mealplaner;

public class Meals {
    String name;
    String Descript;

    public Meals(String name, String descript) {
        this.name = name;
        Descript = descript;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescript() {
        return Descript;
    }

    public void setDescript(String descript) {
        Descript = descript;
    }
}
