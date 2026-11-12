import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

// Manages the list of expenses: add, delete, list, summaries, save and load.
public class ExpenseManager {
    private static final String CSV_HEADER = "Date,Category,Amount,Note";

    private ArrayList<Expense> expenses = new ArrayList<>();
    private String fileName;

    public ExpenseManager(String fileName) {
        this.fileName = fileName;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        saveToFile();
    }

    // number is what the user sees (starts at 1); the list index starts at 0
    public boolean deleteExpense(int number) {
        if (number < 1 || number > expenses.size()) {
            return false;
        }
        expenses.remove(number - 1);
        saveToFile();
        return true;
    }

    public int getCount() {
        return expenses.size();
    }

    public void listExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses yet.");
            return;
        }
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println((i + 1) + ". " + expenses.get(i));
        }
    }

    public double getTotal() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

    // Adds up spending per category using a HashMap (category -> total)
    public void printCategorySummary() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses yet.");
            return;
        }
        HashMap<String, Double> totals = new HashMap<>();
        for (Expense e : expenses) {
            String category = e.getCategory();
            totals.put(category, totals.getOrDefault(category, 0.0) + e.getAmount());
        }
        System.out.println("--- Spending by category ---");
        for (Map.Entry<String, Double> entry : totals.entrySet()) {
            System.out.println(entry.getKey() + ": Rs " + String.format("%.2f", entry.getValue()));
        }
        System.out.println("Total spending: Rs " + String.format("%.2f", getTotal()));
    }

    // Per-day and per-month totals. TreeMap keeps the dates sorted automatically.
    public void printDailyAndMonthlySummary() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses yet.");
            return;
        }
        TreeMap<LocalDate, Double> daily = new TreeMap<>();
        TreeMap<YearMonth, Double> monthly = new TreeMap<>();
        for (Expense e : expenses) {
            LocalDate date = e.getDate();
            YearMonth month = YearMonth.from(date);
            daily.put(date, daily.getOrDefault(date, 0.0) + e.getAmount());
            monthly.put(month, monthly.getOrDefault(month, 0.0) + e.getAmount());
        }
        System.out.println("--- Spending per day ---");
        for (Map.Entry<LocalDate, Double> entry : daily.entrySet()) {
            System.out.println(entry.getKey() + ": Rs " + String.format("%.2f", entry.getValue()));
        }
        System.out.println("--- Spending per month ---");
        for (Map.Entry<YearMonth, Double> entry : monthly.entrySet()) {
            System.out.println(entry.getKey() + ": Rs " + String.format("%.2f", entry.getValue()));
        }
    }

    // Writes every expense to the CSV file (overwrites the old file).
    // CSV opens directly in Excel, one expense per row.
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(CSV_HEADER);
            writer.newLine();
            for (Expense e : expenses) {
                writer.write(e.toCsvLine());
                writer.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Could not save expenses: " + ex.getMessage());
        }
    }

    // Reads the CSV file at startup and rebuilds the Expense objects.
    public void loadFromFile() {
        File file = new File(fileName);
        if (!file.exists()) {
            return; // first run: nothing saved yet
        }
        int skipped = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank() || line.equals(CSV_HEADER)) {
                    continue;
                }
                try {
                    expenses.add(Expense.fromCsvLine(line));
                } catch (Exception badLine) {
                    skipped++; // a broken line should not crash the whole program
                }
            }
        } catch (IOException ex) {
            System.out.println("Could not load expenses: " + ex.getMessage());
        }
        if (skipped > 0) {
            System.out.println("Skipped " + skipped + " unreadable line(s) in " + fileName);
        }
    }
}
