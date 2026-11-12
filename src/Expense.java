import java.time.LocalDate;

// One expense: how much, what for, when, and a short note.
public class Expense {
    // private = encapsulation: other classes can only read these through getters
    private double amount;
    private String category;
    private LocalDate date;
    private String note;

    public Expense(double amount, String category, LocalDate date, String note) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;
    }

    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public LocalDate getDate() { return date; }
    public String getNote() { return note; }

    // Turns this expense into one line of the CSV file: date,category,amount,note
    public String toCsvLine() {
        return date + "," + category + "," + amount + "," + note;
    }

    // Does the opposite: reads one CSV line and builds an Expense object from it
    public static Expense fromCsvLine(String line) {
        String[] parts = line.split(",", 4);
        LocalDate date = LocalDate.parse(parts[0]);
        String category = parts[1];
        double amount = Double.parseDouble(parts[2]);
        String note = parts.length > 3 ? parts[3] : "";
        return new Expense(amount, category, date, note);
    }

    @Override
    public String toString() {
        return date + " | " + category + " | Rs " + String.format("%.2f", amount) + " | " + note;
    }
}
