/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package starosa.fmp210282026.problem4;

/**
 *
 * @author dastarosa
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

class Product {
    private final int id;
    private final String name;
    private final double price;
    private int stock;

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void restock(int quantity) {
        stock += quantity;
    }

    public boolean sell(int quantity) {
        if (quantity > stock) {
            return false;
        }

        stock -= quantity;
        return true;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + ", Name: " + name
                + ", Price: " + String.format("%.2f", price)
                + ", Stock: " + stock;
    }
}

class MenuUtil {
    public static void displayMenu() {
        System.out.println("\nInventory System:");
        System.out.println("1. Add");
        System.out.println("2. Find");
        System.out.println("3. Restock");
        System.out.println("4. Sell");
        System.out.println("5. List");
        System.out.println("6. Sort by price");
        System.out.println("0. Exit");
    }

    public static void addProduct(
        Scanner scanner,
        HashMap<Integer, Product> products) {

        System.out.print("Enter product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (products.containsKey(id)) {
            System.out.println("Error: Product ID already exists.");
            return;
        }

        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter product stock: ");
        int stock = scanner.nextInt();

        if (price < 0 || stock < 0) {
            System.out.println("Error: Price and stock cannot be negative.");
            return;
        }

        products.put(id, new Product(id, name, price, stock));
        System.out.println("Product added.");
    }

    public static void findProduct(
        Scanner scanner,
        HashMap<Integer, Product> products) {

        System.out.print("Enter product ID: ");
        int id = scanner.nextInt();

        Product product = products.get(id);

        if (product == null) {
            System.out.println("Product not found.");
        } else {
            System.out.println(product);
        }
    }

    public static void restockProduct(
        Scanner scanner,
        HashMap<Integer, Product> products) {

        System.out.print("Enter product ID: ");
        int id = scanner.nextInt();

        Product product = products.get(id);

        if (product == null) {
            System.out.println("Error: Product not found.");
            return;
        }

        System.out.print("Enter quantity to restock: ");
        int quantity = scanner.nextInt();

        if (quantity <= 0) {
            System.out.println("Error: Quantity must be greater than zero.");
            return;
        }

        product.restock(quantity);
        System.out.println("Product restocked successfully.");
    }

    public static void sellProduct(
        Scanner scanner,
        HashMap<Integer, Product> products) {

        System.out.print("Enter product ID: ");
        int id = scanner.nextInt();

        Product product = products.get(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter quantity to sell: ");
        int quantity = scanner.nextInt();

        if (quantity <= 0) {
            System.out.println("Error: Quantity must be greater than zero.");
            return;
        }

        if (product.sell(quantity)) {
            System.out.println("Product sold successfully.");
        } else {
            System.out.println("Insufficient stock.");
        }
    }

    public static void listProducts(
        HashMap<Integer, Product> products) {

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Product product : products.values()) {
            System.out.println(product);
        }
    }

    public static void sortByPrice(
        HashMap<Integer, Product> products) {

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        ArrayList<Product> productSnapshot =
                new ArrayList<>(products.values());

        Comparator<Product> priceComparator =
                Comparator.comparingDouble(Product::getPrice)
                        .thenComparingInt(Product::getId);

        productSnapshot.sort(priceComparator);

        for (Product product : productSnapshot) {
            System.out.println(product);
        }
    }
}

public class Problem4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<Integer, Product> products = new HashMap<>();

        int choice;

        do {
            MenuUtil.displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> MenuUtil.addProduct(scanner, products);
                case 2 -> MenuUtil.findProduct(scanner, products);
                case 3 -> MenuUtil.restockProduct(scanner, products);
                case 4 -> MenuUtil.sellProduct(scanner, products);
                case 5 -> MenuUtil.listProducts(products);
                case 6 -> MenuUtil.sortByPrice(products);
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);

        scanner.close();
    }
}