package fr.unepicier.financetracker.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Income {
    private final LocalDate date;
    private final float total;
    private final float salary;
    private final float helps;
    private final float autoBusiness;
    private final float passives;
    private final float other;

    private final static String PRICE_FORMAT = "%.2f €";
    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM yyyy");

    public Income(LocalDate date, float salary, float helps, float autoBusiness, float passives, float other) {
        this.date = date;
        this.total = salary + helps + autoBusiness + passives + other;
        this.salary = salary;
        this.helps = helps;
        this.autoBusiness = autoBusiness;
        this.passives = passives;
        this.other = other;
    }

    // Getters

    public LocalDate getDate() {
        return date;
    }

    public float getTotal() {
        return total;
    }

    public float getSalary() {
        return salary;
    }

    public float getHelps() {
        return helps;
    }

    public float getAutoBusiness() {
        return autoBusiness;
    }

    public float getPassives() {
        return passives;
    }

    public float getOther() {
        return other;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "date=" + date +
                ", total=" + total +
                ", salary=" + salary +
                ", helps=" + helps +
                ", autoBusiness=" + autoBusiness +
                ", passives=" + passives +
                ", other=" + other +
                '}';
    }
}
