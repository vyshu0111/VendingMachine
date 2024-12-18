import java.util.Scanner;

public class VendingMachine {
    private final Inventory inventory;
    private final Cart cart;
    private static final int DEFAULT_RESTOCK_QUANTITY = 10; // Default quantity for restocking

    public VendingMachine() {
        this.inventory = new Inventory();
        this.cart = new Cart();
        initializeInventory();
    }

    private void initializeInventory() {
        inventory.addProduct(new Product("Chips", 10), DEFAULT_RESTOCK_QUANTITY);
        inventory.addProduct(new Product("Soda", 15), DEFAULT_RESTOCK_QUANTITY);
        inventory.addProduct(new Product("Chocolate", 20), DEFAULT_RESTOCK_QUANTITY);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Cart getCart() {
        return cart;
    }

    private void refillInventoryIfNeeded() {
        boolean needsRefill = inventory.getInventory().values().stream()
                .anyMatch(quantity -> quantity <= 2); // Refill if any product is low

        if (needsRefill) {
            System.out.println("\n[ALERT] Low stock detected. Automatically restocking...");
            inventory.getInventory().replaceAll((k, v) -> v <= 2 ? DEFAULT_RESTOCK_QUANTITY : v);
        }
    }

    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Automatic inventory check and refill
            vendingMachine.refillInventoryIfNeeded();

            System.out.println("\nVending Machine Menu:");
            System.out.println("1. Display Products");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. Remove Item from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    vendingMachine.getInventory().displayInventory();
                    break;
                case 2:
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    Product selectedProduct = null;
                    for (Product product : vendingMachine.getInventory().getInventory().keySet()) {
                        if (product.getName().equalsIgnoreCase(productName)) {
                            selectedProduct = product;
                            break;
                        }
                    }

                    if (selectedProduct == null) {
                        System.out.println("Product not found.");
                    } else {
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        if (vendingMachine.getInventory().isAvailable(selectedProduct, quantity)) {
                            vendingMachine.getCart().addToCart(selectedProduct, quantity);
                            System.out.println("Added to cart: " + selectedProduct.getName());
                        } else {
                            System.out.println("Insufficient stock for " + selectedProduct.getName());
                        }
                    }
                    break;
                case 3:
                    if (vendingMachine.getCart().getCartItems().isEmpty()) {
                        System.out.println("Your cart is empty.");
                        break;
                    }
                    System.out.print("Enter product name to remove: ");
                    String removeProductName = scanner.nextLine();
                    Product removeProduct = null;
                    for (Product product : vendingMachine.getCart().getCartItems().keySet()) {
                        if (product.getName().equalsIgnoreCase(removeProductName)) {
                            removeProduct = product;
                            break;
                        }
                    }

                    if (removeProduct == null) {
                        System.out.println("Product not found in cart.");
                    } else {
                        System.out.print("Enter quantity to remove: ");
                        int removeQuantity = scanner.nextInt();
                        vendingMachine.getCart().removeFromCart(removeProduct, removeQuantity);
                        System.out.println("Removed from cart: " + removeProduct.getName());
                    }
                    break;
                case 4:
                    vendingMachine.getCart().displayCart();
                    System.out.println("Cart Total: " + vendingMachine.getCart().calculateTotal());
                    break;
                case 5:
                    double totalAmount = vendingMachine.getCart().calculateTotal();
                    if (totalAmount == 0) {
                        System.out.println("Your cart is empty.");
                        break;
                    }
                    System.out.println("Total Amount to Pay: " + totalAmount);
                    System.out.print("Enter payment amount: ");
                    double payment = scanner.nextDouble();
                    if (payment >= totalAmount) {
                        System.out.println("Payment successful. Dispensing items...");
                        for (var entry : vendingMachine.getCart().getCartItems().entrySet()) {
                            vendingMachine.getInventory().reduceProduct(entry.getKey(), entry.getValue());
                        }
                        vendingMachine.getCart().getCartItems().clear();
                        System.out.println("Change returned: " + (payment - totalAmount));
                    } else {
                        System.out.println("Insufficient payment. Transaction canceled.");
                    }
                    break;
                case 6:
                    System.out.println("Thank you for using the vending machine. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}