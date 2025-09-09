[09/09, 10:22 pm] ʜᴀʀɪꜱꜱʜ...: // User.java
package harissh;

public class User {
    String userId;
    String name;
    String email;
    double rating;
    String role;

    public User(String userId, String name, String email, double rating, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.rating = rating;
        this.role = role;
    }

    public String toString() {
        return name + " (" + role + ")";
    }
}
[09/09, 10:23 pm] ʜᴀʀɪꜱꜱʜ...: // Item.java
package iyyappanass;

public class Item {
    String itemId;
    String title;
    String description;
    double basePrice;
    User seller;
    String state; // "OPEN" or "CLOSED"

    public Item(String itemId, String title, String description, double basePrice, User seller) {
        this.itemId = itemId;
        this.title = title;
        this.description = description;
        this.basePrice = basePrice;
        this.seller = seller;
        this.state = "OPEN";
    }

    public String toString() {
        return "[" + itemId + "] " + title + " - $" + basePrice + " (" + state + ")";
    }
}
[09/09, 10:23 pm] ʜᴀʀɪꜱꜱʜ...: // Bid.java
package iyyappanass;

public class Bid {
    String bidId;
    String itemId;
    User bidder;
    double amount;
    String time;

    public Bid(String bidId, String itemId, User bidder, double amount, String time) {
        this.bidId = bidId;
        this.itemId = itemId;
        this.bidder = bidder;
        this.amount = amount;
        this.time = time;
    }

    public String toString() {
        return bidder.name + " bid $" + amount + " at " + time;
    }
}
[09/09, 10:23 pm] ʜᴀʀɪꜱꜱʜ...: // AuctionService.java
package iyyappanass;
import java.util.*;

public class AuctionService {
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Bid> bids = new ArrayList<>();

    public void listItem(Item item) {
        items.add(item);
        System.out.println("Item listed: " + item);
    }

    public void placeBid(Bid bid) {
        Item item = findItemById(bid.itemId);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }
        if (!item.state.equals("OPEN")) {
            System.out.println("Auction is closed.");
            return;
        }
        Bid highest = getHighestBid(bid.itemId);
        if (highest != null && bid.amount <= highest.amount) {
            System.out.println("Your bid must be higher than the current highest bid.");
            return;
        }
        bids.add(bid);
        System.out.println("Bid placed: " + bid);
    }

    public void closeAuction(String itemId) {
        Item item = findItemById(itemId);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }
        item.state = "CLOSED";
        Bid highest = getHighestBid(itemId);

        System.out.println("Auction closed for: " + item.title);
        if (highest != null) {
            System.out.println("Winner: " + highest.bidder.name + ", Amount: $" + highest.amount);
            System.out.println("Generating Invoice...");
            System.out.println("--- Invoice ---");
            System.out.println("Item: " + item.title);
            System.out.println("Buyer: " + highest.bidder.name);
            System.out.println("Seller: " + item.seller.name);
            System.out.println("Amount: $" + highest.amount);
            System.out.println("---------------");
        } else {
            System.out.println("No bids placed.");
        }
    }

    public void showItems() {
        for (Item i : items) {
            System.out.println(i);
        }
    }

    private Item findItemById(String id) {
        for (Item i : items) {
            if (i.itemId.equals(id)) {
                return i;
            }
        }
        return null;
    }

    private Bid getHighestBid(String itemId) {
        Bid highest = null;
        for (Bid b : bids) {
            if (b.itemId.equals(itemId)) {
                if (highest == null || b.amount > highest.amount) {
                    highest = b;
                }
            }
        }
        return highest;
    }
}
[09/09, 10:23 pm] ʜᴀʀɪꜱꜱʜ...: // Main.java
package iyyappanass;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuctionService auction = new AuctionService();

        User seller = new User("S1", "Alice", "alice@example.com", 4.5, "Seller");
        User buyer1 = new User("B1", "Bob", "bob@example.com", 4.2, "Buyer");
        User buyer2 = new User("B2", "Charlie", "charlie@example.com", 4.8, "Buyer");

        while (true) {
            System.out.println("\n--- Auction Platform ---");
            System.out.println("1. List Item");
            System.out.println("2. Place Bid");
            System.out.println("3. Close Auction");
            System.out.println("4. View Items");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Item ID: ");
                String id = sc.nextLine();
                System.out.print("Title: ");
                String title = sc.nextLine();
                System.out.print("Description: ");
                String desc = sc.nextLine();
                System.out.print("Base Price: ");
                double price = sc.nextDouble();
                sc.nextLine();
                Item item = new Item(id, title, desc, price, seller);
                auction.listItem(item);
            } else if (choice == 2) {
                System.out.print("Item
