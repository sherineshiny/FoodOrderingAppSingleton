import java.util.*;

class FoodOrderingApp {
    private static FoodOrderingApp instance;
    private boolean isLoggedIn;
    private String loggedInUser;
    private Map<String, String> userCredentials;
    private List<String> selectedItems;
    private double totalBill;

    private FoodOrderingApp() {
        isLoggedIn = false;
        loggedInUser = null;
        userCredentials = new HashMap<>();
        selectedItems = new ArrayList<>();
        totalBill = 0.0;
    }

    public static FoodOrderingApp getInstance() {
        if (instance == null) {
            instance = new FoodOrderingApp();
        }
        return instance;
    }

    public void signUp() {
        if (isLoggedIn) {
            System.out.println("You are already logged in. Please sign out first.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your mobile number: ");
        String mobileNumber = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        userCredentials.put(mobileNumber, password);
        System.out.println("User registration successful!");
    }

    public void signIn() {
        if (isLoggedIn) {
            System.out.println("You are already logged in.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your mobile number: ");
        String mobileNumber = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        String storedPassword = userCredentials.get(mobileNumber);
        if (storedPassword != null && storedPassword.equals(password)) {
            System.out.println("Login successful!");
            loggedInUser = mobileNumber;
            isLoggedIn = true;
        } else {
            System.out.println("Invalid mobile number or password. Login failed.");
        }
    }

    public void forgotPassword() {
        if (isLoggedIn) {
            System.out.println("You are already logged in. Please sign out first.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your mobile number: ");
        String mobileNumber = scanner.nextLine();

        if (userCredentials.containsKey(mobileNumber)) {
            String otp = generateOTP();
            System.out.println("OTP generated: " + otp);

            System.out.print("Enter the OTP received: ");
            String enteredOTP = scanner.nextLine();

            if (otp.equals(enteredOTP)) {
                String password = userCredentials.get(mobileNumber);
                System.out.println("Your password is: " + password);
            } else {
                System.out.println("Invalid OTP. Password retrieval failed.");
            }
        } else {
            System.out.println("Mobile number not found. Password retrieval failed.");
        }
    }

    public void updateCredentials() {
        if (!isLoggedIn) {
            System.out.println("Please sign in to update your credentials.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Update Credentials:");
        System.out.println("1. Update Mobile Number");
        System.out.println("2. Update Password");
        System.out.print("Enter your choice (1-2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                updateMobileNumber();
                break;
            case 2:
                updatePassword();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void updateMobileNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your new mobile number: ");
        String newMobileNumber = scanner.nextLine();

        if (userCredentials.containsKey(newMobileNumber)) {
            System.out.println("Mobile number already exists. Update failed.");
        } else {
            userCredentials.put(newMobileNumber, userCredentials.remove(loggedInUser));
            loggedInUser = newMobileNumber;
            System.out.println("Mobile number updated successfully!");
        }
    }

    private void updatePassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();

        userCredentials.put(loggedInUser, newPassword);
        System.out.println("Password updated successfully!");
    }

    public void displayMenu() {
        if (!isLoggedIn) {
            System.out.println("Please sign in to access the menu.");
            return;
        }

        System.out.println("Menu:");
        System.out.println("1. Veg Category");
        System.out.println("2. Non-Veg Category");
        System.out.print("Enter your choice (1-2): ");

        Scanner scanner = new Scanner(System.in);
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();

        switch (categoryChoice) {
            case 1:
                displayVegMenu();
                break;
            case 2:
                displayNonVegMenu();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void displayVegMenu() {
        System.out.println("Veg Category Menu:");
        System.out.println("1. Item 1 - $10");
        System.out.println("2. Item 2 - $12");
        System.out.println("3. Item 3 - $8");
        // Add more items as needed

        Scanner scanner = new Scanner(System.in);
        int itemChoice;
        boolean addAnother = true;

        while (addAnother) {
            System.out.print("Enter your choice (1-3): ");
            itemChoice = scanner.nextInt();
            scanner.nextLine();

            if (itemChoice >= 1 && itemChoice <= 3) {
                selectedItems.add("Item " + itemChoice);
                totalBill += getItemPrice(itemChoice);

                System.out.print("Do you want to add another item? (yes/no): ");
                String addAnotherChoice = scanner.nextLine();

                addAnother = addAnotherChoice.equalsIgnoreCase("yes");
            } else {
                System.out.println("Invalid choice.");
            }
        }

        processPayment();
    }

    private void displayNonVegMenu() {
        System.out.println("Non-Veg Category Menu:");
        System.out.println("1. Item A - $15");
        System.out.println("2. Item B - $18");
        System.out.println("3. Item C - $20");
        // Add more items as needed

        Scanner scanner = new Scanner(System.in);
        int itemChoice;
        boolean addAnother = true;

        while (addAnother) {
            System.out.print("Enter your choice (1-3): ");
            itemChoice = scanner.nextInt();
            scanner.nextLine();

            if (itemChoice >= 1 && itemChoice <= 3) {
                selectedItems.add("Item " + itemChoice);
                totalBill += getItemPrice(itemChoice);

                System.out.print("Do you want to add another item? (yes/no): ");
                String addAnotherChoice = scanner.nextLine();

                addAnother = addAnotherChoice.equalsIgnoreCase("yes");
            } else {
                System.out.println("Invalid choice.");
            }
        }

        processPayment();
    }

    private double getItemPrice(int itemChoice) {
        // Return the price of the selected item based on the choice
        // Modify this method to return actual item prices
        return 0.0;
    }

    private void processPayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the total amount for the selected items: ");
        double enteredAmount = scanner.nextDouble();

        if (enteredAmount < totalBill) {
            System.out.println("Insufficient amount. Please pay the exact bill amount: $" + totalBill);
        } else if (enteredAmount == totalBill) {
            System.out.println("Payment successful. Thank you!");
        } else {
            double balance = enteredAmount - totalBill;
            System.out.println("Payment successful. Your balance change: $" + balance);
        }

        generateBill();
    }

    private void generateBill() {
        System.out.println("----- Bill -----");
        System.out.println("Selected Items:");
        for (String item : selectedItems) {
            System.out.println(item);
        }
        System.out.println("Total Bill: $" + totalBill);
        System.out.println("----------------");
    }

    private String generateOTP() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000;
        return String.valueOf(otp);
    }

    public static void main(String[] args) {
        FoodOrderingApp app = FoodOrderingApp.getInstance();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Food Ordering App");
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Forgot Password");
            System.out.println("4. Update Credentials");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    app.signUp();
                    break;
                case 2:
                    app.signIn();
                    break;
                case 3:
                    app.forgotPassword();
                    break;
                case 4:
                    app.updateCredentials();
                    break;
                case 5:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            System.out.println();
        } while (choice != 5);
    }
}
