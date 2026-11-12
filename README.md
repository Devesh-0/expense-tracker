# Personal Expense Tracker

A console-based Java application to record, categorize and analyze personal expenses. Expenses are saved to a CSV file, so your data is still there the next time you run the program, and the file opens directly in Excel.

## Features

- [x] Add an expense (amount, category, date, note)
- [x] View all expenses
- [x] Delete an expense
- [x] Category-wise spending summary with total
- [x] Daily and monthly spending summary
- [x] Save expenses to `expenses.csv` and load them on startup
- [x] Input validation (no crashes on wrong input)

## Tech Stack

- **Java** (JDK 17 or later)
- **OOP** – `Expense` (data), `ExpenseManager` (logic), `Main` (menu)
- **Collections** – `ArrayList` for the expenses, `HashMap` for category totals, `TreeMap` for sorted daily/monthly totals
- **java.time** – `LocalDate` and `YearMonth` for dates
- **File Handling** – `BufferedReader` / `BufferedWriter` with try-with-resources

## Project Structure

```
expense-tracker/
├── src/
│   ├── Expense.java          # One expense + converting to/from a CSV line
│   ├── ExpenseManager.java   # Add, delete, list, summaries, save, load
│   └── Main.java             # Menu loop and input validation
├── expenses.csv              # Created automatically when you add an expense
├── README.md
└── ROADMAP.md
```

## How to Run

From the `expense-tracker` folder:

```
javac -d out src/*.java
java -cp out Main
```

## Sample Run

```
===== Expense Tracker =====
1. Add expense
2. View all expenses
3. Delete an expense
4. Category summary
5. Daily and monthly summary
6. Exit
Choose an option: 1
Amount (Rs): 250
Category (e.g. Food, Travel): food
Date (YYYY-MM-DD, press Enter for today): 2026-10-31
Note: Lunch
Expense added!

Choose an option: 4
--- Spending by category ---
Travel: Rs 80.00
Shopping: Rs 1200.00
Food: Rs 400.00
Total spending: Rs 1680.00
```

## How It Works

- Each expense is an `Expense` object with private fields and getters (encapsulation).
- `ExpenseManager` keeps all expenses in an `ArrayList<Expense>`.
- After every add or delete, the whole list is written to `expenses.csv`, one expense per line, with a header row so it opens neatly in Excel.
- On startup, the file is read back line by line, and each line is turned into an `Expense` object again. A broken line is skipped instead of crashing the program.
- The category summary adds up amounts per category with a `HashMap<String, Double>`.
- The daily and monthly summary uses `TreeMap`, which keeps dates in order.
- Categories are capitalized (`food` → `Food`) so they group correctly, and commas are removed from text so they don't break the CSV columns.

## Future Improvements

- Export a formatted `.xlsx` file with Apache POI
- Monthly budget limit with warnings
- Store data in MySQL instead of a CSV file
- Simple GUI using JavaFX or Swing

## Author

**Devesh Sureka** – B.Tech CSE, Amity University Kolkata
