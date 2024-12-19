# Overview

The Vending Machine system simulates the functionality of a real-world
vending machine. It allows users to view available products, add items
to a cart, remove items, and proceed to checkout. The system is
structured into four main components: `Product`, `Inventory`, `Cart`,
and `VendingMachine`.

# Class Descriptions

## 1. Class: Product

-   **Purpose**: Represents a product that can be sold in the vending
    machine.

### Attributes:

-   `name`: A String representing the name of the product.\
     
-   `price`: A double representing the price of the product.

### Constructor:

-   `public Product(String name, double price)`: Initializes a new
    instance of the Product class with the specified name and price.

### Methods:

-   `public String getName()`: Returns the name of the product.\
     
-   `public double getPrice()`: Returns the price of the product.\
     
-   `@Override public String toString()`: Returns a string
    representation of the product, including its name and price.

## 2. Class: Inventory

-   **Purpose**: Represents the inventory of products available in the
    vending machine.

### Attributes:

-   `inventory`: A `ConcurrentHashMap` that maps Product objects to
    their respective quantities in stock.

### Methods:

-   `public void addProduct(Product product, int quantity)`: Adds a
    specified quantity of a product to the inventory. If the product
    already exists, it merges the quantities.\
     
-   `public boolean isAvailable(Product product, int quantity)`: Checks
    if a specified quantity of a product is available in the inventory.\
     
-   `public boolean reduceProduct(Product product, int quantity)`:
    Reduces the quantity of a specified product in the inventory if
    enough stock is available. Returns `true` if the reduction was
    successful, otherwise returns `false`.\
     
-   `public void displayInventory()`: Displays the available products in
    the inventory along with their prices and quantities.\
     
-   `public ConcurrentHashMap<Product, Integer> getInventory()`: Returns
    the current inventory of products.

## 3. Class: Cart

-   **Purpose**: Represents the shopping cart that holds products
    selected for purchase.

### Attributes:

-   `cart`: A `HashMap` that maps Product objects to their respective
    quantities in the cart.

### Methods:

-   `public void addToCart(Product product, int quantity)`: Adds a
    specified quantity of a product to the cart. If the product already
    exists, it merges the quantities.\
     
-   `public void removeFromCart(Product product, int quantity)`: Removes
    a specified quantity of a product from the cart. If the quantity to
    remove is greater than or equal to the current quantity, the product
    is removed from the cart entirely.\
     
-   `public void displayCart()`: Displays the items currently in the
    cart along with their quantities and prices. If the cart is empty,
    it notifies the user.\
     
-   `public double calculateTotal()`: Calculates and returns the total
    price of all items in the cart.\
     
-   `public HashMap<Product, Integer> getCartItems()`: Returns the items
    currently in the cart.

## 4. Class: VendingMachine

-   **Purpose**: Represents the vending machine that manages products,
    inventory, and the shopping cart.

### Attributes:

-   `inventory`: An instance of the `Inventory` class that holds the
    available products.\
     
-   `cart`: An instance of the `Cart` class that manages the items
    selected for purchase.\
     
-   `DEFAULT_RESTOCK_QUANTITY`: A constant integer representing the
    default quantity for restocking products.

### Constructor:

-   `public VendingMachine()`: Initializes a new instance of the
    VendingMachine class, creates an inventory and a cart, and populates
    the inventory with default products.

### Methods:

-   `private void initializeInventory()`: Populates the inventory with
    default products (Chips, Soda, Chocolate) and their respective
    quantities.\
     
-   `public Inventory getInventory()`: Returns the inventory of the
    vending machine.\
     
-   `public Cart getCart()`: Returns the cart of the vending machine.\
     
-   `private void refillInventoryIfNeeded()`: Checks if any product is
    low in stock and automatically refills it if necessary.\
     
-   `public static void main(String[] args)`: The main method that runs
    the vending machine application, providing a menu for user
    interaction.

# Logic and Interaction Flow

**Initialization**: When the `VendingMachine` is instantiated, it
initializes the `Inventory` and `Cart` and populates the inventory with
default products.

**User Interaction**: The main method provides a menu for user
interaction, allowing users to:

-   Display available products using `inventory.displayInventory()`.\
     
-   Add products to their Cart, checking availability through
    `inventory.isAvailable()` and then calling `cart.addToCart()`.\
     
-   Remove products from the cart using `cart.removeFromCart()`.\
     
-   View the cart contents and total price using `cart.displayCart()`
    and `cart.calculateTotal()`.\
     
-   Checkout by processing payment and updating the inventory using
    `inventory.reduceProduct()`.

**Inventory Management**: The `VendingMachine` class periodically checks
the inventory for low stock using `refillInventoryIfNeeded()`, which
alerts the user and restocks products if necessary.

# Data Flow

-   The `VendingMachine` interacts with both `Inventory` and `Cart` to
    manage products and user selections.\
     
-   The `Cart` interacts with `Product` to manage selected items and
    their quantities.\
     
-   The `Inventory` interacts with `Product` to manage available stock
    and ensure that the cart operations are valid.
