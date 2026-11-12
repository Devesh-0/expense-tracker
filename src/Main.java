import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

// Shows the menu, reads what the user types, and calls ExpenseManager.
public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager("expenses.csv");
        manager.loadFromFile();

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("===== Expense Tracker =====");
            System.out.println("1. Add expense");
            System.out.println("2. View all expenses");
            System.out.println("3. Delete an expense");
            System.out.println("4. Category summary");
            System.out.println("5. Daily and monthly summary");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addExpense(manager);
                    break;
                case "2":
                    manager.listExpenses();
                    break;
                case "3":
                    deleteExpense(manager);
                    break;
                case "4":
                    manager.printCategorySummary();
                    break;
                case "5":
                    manager.printDailyAndMonthlySummary();
                    break;
                case "6":
                    running = false;
                    System.out.println("Your expenses are saved in expenses.csv. Bye!");
                    break;
                default:
                    System.out.println("Please choose a number from 1 to 6.");
            }
        }
        scanner.close();
    }

    private static void addExpense(ExpenseManager manager) {
        double amount = readPositiveAmount();
        String category = readNonEmpty("Category (e.g. Food, Travel): ");
        category = capitalize(clean(category));
        LocalDate date = readDate();
        System.out.print("Note: ");
        String note = clean(scanner.nextLine().trim());

        manager.addExpense(new Expense(amount, category, date, note));
        System.out.println("Expense added!");
    }

    private static void deleteExpense(ExpenseManager manager) {
        if (manager.getCount() == 0) {
            System.out.println("No expenses to delete.");
            return;
        }
        manager.listExpenses();
        System.out.print("Enter the number to delete: ");
        String input = scanner.nextLine().trim();
        try {
            int number = Integer.parseInt(input);
            if (manager.deleteExpense(number)) {
                System.out.println("Expense deleted.");
            } else {
                System.out.println("No expense with number " + number + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("That is not a number.");
        }
    }

    // Keeps asking until the user types a number greater than 0
    private static double readPositiveAmount() {
        while (true) {
            System.out.print("Amount (Rs): ");
            String input = scanner.nextLine().trim();
            try {
                double amount = Double.parseDouble(input);
                if (amount > 0) {
                    return amount;
                }
                System.out.println("Amount must be more than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please type a number, like 250 or 99.50.");
            }
        }
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("This cannot be empty.");
        }
    }

    // Empty input means today's date
    private static LocalDate readDate() {
        while (true) {
            System.out.print("Date (YYYY-MM-DD, press Enter for today): ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return LocalDate.now();
            }
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Please use the format YYYY-MM-DD, like 2026-11-12.");
            }
        }
    }

    // Commas would break the CSV columns, so replace them with spaces
    private static String clean(String text) {
        return text.replace(",", " ");
    }

    // "food" and "FOOD" both become "Food", so the summary groups them together
    private static String capitalize(String text) {
        text = text.trim().toLowerCase();
        if (text.isEmpty()) {
            return "Other";
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
}
