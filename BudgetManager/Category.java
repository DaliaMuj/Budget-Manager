package BudgetManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import static java.util.stream.Collectors.toMap;


public class Category {
    private final Map<String, Double> map;
    private double income;
    public Category() {
        map = new HashMap<>();
        this.income = 0.00;
    }
    public void setList(String name, double price){
        map.put(name, price);
    }
    public void setIncome(double income) {
        this.income += income;
    }
    public Map<String, Double> getMap() {
        return map;
    }
    public double getIncome() {
        return income;
    }
    public void displayList() {
        if (!map.isEmpty()) {
            map.keySet().forEach(System.out::println);
        } else {
            System.out.println("Purchase list is empty.");
        }
    }
    public void displayIncome() {
        System.out.printf("Total sum: $%.2f \n", income);
    }
    public boolean checkList() {
        return map.isEmpty();
    }
    public void addPurchase(String purchase, String price) {
        if (!map.containsKey(purchase) && !map.containsValue(Double.parseDouble(price))) {
            map.put(purchase, Double.parseDouble(price));
            income += Double.parseDouble(price);
        }
    }
    public void fileWriter(int num, FileWriter myWriter) {
            map.forEach( (k,v) -> {
                try {
                    myWriter.write(num + ")" + k + " $" + String.format("%.2f", v) + "\n");
                } catch (IOException e) {
                    System.out.println("An error occurred!");
                }
            });
    }
    public Map<String, Double> sortByPrice() {
        Map<String, Double>  sorted = map
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        return sorted;
    }

}
