package BudgetManager;

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.toMap;

public class BudgetManager {
    public static Scanner scanner = new Scanner(System.in);
    public static double totalIncome = 0.00;
    public static Category food = new Category();
    public static Category clothes = new Category();
    public static Category entertainment = new Category();
    public static Category other = new Category();
    public static void main(String[] args) {
        int action;
        do {
            try {
                printMenu();
                action = scanner.nextInt();
                System.out.println();
                switch(action) {
                    case 1:
                        addIncome();
                        break;
                    case 2:
                        addPurchase();
                        break;
                    case 3:
                        showList();
                        break;
                    case 4:
                        balance();
                        break;
                    case 5:
                        save();
                        break;
                    case 6:
                        load();
                        break;
                    case 7:
                        analyze();
                        break;
                }
                System.out.println();
            } catch(Exception e) {
                System.out.println("Wrong input!");
                System.out.println();
                action = 10;
            }
        } while (action != 0);
        System.out.println("Bye!");
        System.exit(0);

    }
    public static void addIncome() {
        System.out.println("Enter income:");
        totalIncome += scanner.nextDouble();
        System.out.println("Income was added!");
    }
    public static void addPurchase() {
        int type;
        do {
            try {
                System.out.println("Choose the type of purchase");
                System.out.println("1) Food\n2) Clothes\n3) Entertainment\n4) Other\n5) Back\n");
                type = Integer.parseInt(scanner.nextLine());
                if (type == 5) {
                    break;
                } else {
                    System.out.println("Enter purchase name:");
                    String purchase = scanner.nextLine();
                    System.out.println("Enter its price:");
                    String price = scanner.nextLine();
                    switch(type){
                        case 1:
                            food.addPurchase(purchase, price);
                            totalIncome -= Double.parseDouble(price);
                            break;
                        case 2:
                            clothes.addPurchase(purchase, price);
                            totalIncome -= Double.parseDouble(price);
                            break;
                        case 3:
                            entertainment.addPurchase(purchase, price);
                            totalIncome -= Double.parseDouble(price);
                            break;
                        case 4:
                            other.addPurchase(purchase, price);
                            totalIncome -= Double.parseDouble(price);
                            break;
                        default:
                            break;
                    }
                    System.out.println("Purchase was added!");
                    System.out.println();
                }
            } catch (Exception e) {
                System.out.println("Wrong input!");
                type = 10;
            }
        } while (type != 5);
    }
    public static void showList() {
        int action;
        do {
            try {
                System.out.println();
                System.out.println("Choose the type of purchase");
                System.out.println("1) Food\n2) Clothes\n3) Entertainment\n4) Other\n5) All\n6) Back\n");
                action = scanner.nextInt();
                switch(action) {
                    case 1:
                        System.out.println("Food:");
                        food.displayList();
                        food.displayIncome();
                        break;
                    case 2:
                        System.out.println("Clothes:");
                        clothes.displayList();
                        clothes.displayIncome();
                        break;
                    case 3:
                        System.out.println("Entertainment:");
                        entertainment.displayList();
                        entertainment.displayIncome();
                        break;
                    case 4:
                        System.out.println("Other:");
                        other.displayList();
                        other.displayIncome();
                        break;
                    case 5:
                        System.out.println("All:");
                        if (food.checkList() && clothes.checkList() && entertainment.checkList() && other.checkList()) {
                            System.out.println("Purchase list is empty.");
                        } else {
                            if (!food.checkList()) {
                                food.displayList();
                            }
                            if (!clothes.checkList()) {
                                clothes.displayList();
                            }
                            if (!entertainment.checkList()) {
                                entertainment.displayList();
                            }
                            if (!other.checkList()) {
                                other.displayList();
                            }
                            System.out.printf("Total sum: $%.2f \n", (food.getIncome() + clothes.getIncome() +  entertainment.getIncome() + other.getIncome()));
                        }
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                action = 10;
            }
        } while (action != 6);
    }
    public static void balance() {
        System.out.printf("Balance: $%.2f", totalIncome);
        System.out.println();
    }
    public static void save() {
        try {
            FileWriter myWriter = new FileWriter("purchases.txt");
            myWriter.write("Balance: $" + totalIncome + "\n");
            if (!food.checkList()) {
                food.fileWriter(1, myWriter);
            } if (!clothes.checkList()) {
                clothes.fileWriter(2, myWriter);
            } if (!entertainment.checkList()) {
                entertainment.fileWriter(3, myWriter);
            } if (!other.checkList()) {
                other.fileWriter(4, myWriter);
            }
            System.out.println("Purchases were saved!");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred!");
        }
    }
    public static void load() {
        try {
            File file = new File("purchases.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if(data.contains("Balance")) {
                    totalIncome += Double.parseDouble(data.substring(data.indexOf("$") + 1));
                }
                double sign = Double.parseDouble(data.substring(data.lastIndexOf("$") + 1));
                String list = data.substring(data.indexOf(")") + 1);
                if(data.contains("1)")) {
                    food.setList(list, sign);
                    food.setIncome(sign);
                }
                if(data.charAt(0) == '2') {
                    clothes.setList(list, sign);
                    clothes.setIncome(sign);
                }
                if(data.charAt(0) == '3') {
                    entertainment.setList(list, sign);
                    entertainment.setIncome(sign);
                }
                if(data.charAt(0) == '4') {
                    other.setList(list, sign);
                    other.setIncome(sign);
                }
                reader.close();
            }
            System.out.println("Purchases were loaded!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void sortByPurchases() {
        if (food.checkList() && clothes.checkList() && entertainment.checkList() && other.checkList()) {
            System.out.println("The purchase list is empty!");
        } else {
            Map<String, Double> map = new HashMap<>();
            map.putAll(food.getMap());
            map.putAll(clothes.getMap());
            map.putAll(entertainment.getMap());
            map.putAll(other.getMap());
            Map<String, Double>  sorted = map
                    .entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
            sorted.keySet().forEach(System.out::println);

        }
        System.out.println();
    }
    public static void sortByType() {
        System.out.printf("Food - $%.2f%n", food.getIncome());
        System.out.printf("Entertainment - $%.2f%n", entertainment.getIncome());
        System.out.printf("Clothes - $%.2f%n", clothes.getIncome());
        System.out.printf("Other - $%.2f%n", other.getIncome());
        System.out.println();
    }
    public static void sortByACertainType() {
        System.out.println("Choose the type of purchase\n" + "1) Food\n" +"2) Clothes\n" +"3) Entertainment\n" + "4) Other");
        int action = scanner.nextInt();
        System.out.println();
        switch (action) {
            case 1:
                if (food.checkList()) {
                    System.out.println("The purchase list is empty!");
                } else {
                    food.sortByPrice().keySet().forEach(System.out::println);
                }
                System.out.println();
                break;
            case 2:
                if (clothes.checkList()) {
                    System.out.println("The purchase list is empty!");
                } else {
                    clothes.sortByPrice().keySet().forEach(System.out::println);
                }
                System.out.println();
                break;
            case 3:
                if (entertainment.checkList()) {
                    System.out.println("The purchase list is empty!");
                } else {
                    entertainment.sortByPrice().keySet().forEach(System.out::println);
                }
                System.out.println();
                break;
            case 4:
                if (other.checkList()) {
                    System.out.println("The purchase list is empty!");
                } else {
                    other.sortByPrice().keySet().forEach(System.out::println);
                }
                System.out.println();
                break;
            default:
                break;
        }
    }
    public static void analyze() {
        int action;
        do {
            System.out.println("How do you want to sort?");
            System.out.println("1) Sort all purchases\n" + "2) Sort by type\n" + "3) Sort certain type\n" + "4) Back");
            action = scanner.nextInt();
            switch (action) {
                case 1:
                    System.out.println();
                    sortByPurchases();
                    break;
                case 2:
                    System.out.println();
                    sortByType();
                    break;
                case 3:
                    System.out.println();
                    sortByACertainType();
                    break;
                default:
                    break;
            }
        } while (action != 4);
    }
    public static void printMenu() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income\n2) Add purchase\n3) Show list of purchases\n4) Balance\n5) Save\n6) Load\n7) Analyze (Sort)\n0) Exit");
    }

}

