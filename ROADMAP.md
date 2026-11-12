# Roadmap – Personal Expense Tracker

Build this in order. Each step is small, and each one teaches a Java concept you may be asked about in an interview. Don't move on until the current step works **and** you can explain it in your own words.

Estimated time: **2 days** (about 3–4 hours per day).

---

## Step 0 – Setup (30 min)

- Install **JDK 17+** and **VS Code** with the "Extension Pack for Java".
- Install **Git** and create a GitHub account if you don't have one.
- Create the folder `expense-tracker/src/`.
- Write a `Main.java` that prints "Hello" and run it.

**Done when:** `java -cp out Main` prints Hello.

---

## Step 1 – The `Expense` class (45 min)

Create a class that represents one expense.

- Private fields: `amount` (double), `category` (String), `date` (String), `note` (String)
- A constructor that sets all fields
- Getters for each field
- A `toString()` method that returns a readable line

**Concepts:** class, object, constructor, encapsulation (private fields + getters).

**Done when:** you can create an `Expense` in `main` and print it.

---

## Step 2 – The `ExpenseManager` class (1 hr)

This class manages the list of expenses.

- Field: `ArrayList<Expense> expenses`
- Methods: `addExpense(Expense e)`, `listExpenses()`, `deleteExpense(int index)`, `getTotal()`

**Concepts:** ArrayList (add, get, remove, size), loops, methods.

**Done when:** you can add 3 expenses, delete one, and print the remaining list and total.

---

## Step 3 – Menu with `Scanner` (1 hr)

In `Main.java`, build a loop that shows the menu and reads the user's choice.

- Use `Scanner` to read input
- Use `switch` (or if/else) for each option
- Loop until the user picks "Exit"

**Concepts:** user input, while loop, switch-case.

**Done when:** you can add, view and delete expenses from the menu.

---

## Step 4 – Category summary (45 min)

Add `printCategorySummary()` to `ExpenseManager`.

- Loop through all expenses
- Use a `HashMap<String, Double>` to add up the amount per category
- Print each category and its total

**Concepts:** HashMap, getOrDefault, iterating over a map.

**Done when:** adding Food 100 and Food 50 shows "Food: 150.0".

---

## Step 5 – Save and load from a file (1.5 hr)

- `saveToFile()`: write each expense as one line: `amount,category,date,note`
- `loadFromFile()`: read each line, `split(",")`, and rebuild the `Expense` objects
- Call `loadFromFile()` at startup and `saveToFile()` after every change
- Wrap file code in `try-catch` for `IOException`

**Concepts:** file handling, BufferedReader/BufferedWriter, exception handling, string split.

**Done when:** you add expenses, close the program, run it again, and they're still there.

---

## Step 6 – Input validation (30 min)

- Don't allow negative or zero amounts
- Handle the user typing letters instead of a number (catch `InputMismatchException` or use `Double.parseDouble` in a try-catch)
- Check the index is valid before deleting

**Concepts:** exception handling, defensive programming.

**Done when:** bad input shows a friendly message instead of crashing.

---

## Step 7 – Push to GitHub (30 min)

```
git init
git add .
git commit -m "Personal Expense Tracker in Java"
git branch -M main
git remote add origin https://github.com/<your-username>/expense-tracker.git
git push -u origin main
```

- Update the feature checkboxes in `README.md`.
- Add a `.gitignore` containing `out/` so compiled files aren't uploaded.

---

## Step 8 – Interview prep for this project

Practise answering these out loud:

1. **"Explain your project in one minute."**
2. **"Why did you use an ArrayList instead of an array?"** – ArrayList grows automatically; we don't know how many expenses there will be.
3. **"How is the data saved?"** – CSV file, one expense per line, read back on startup.
4. **"Where did you use OOP?"** – `Expense` and `ExpenseManager` classes, private fields with getters (encapsulation).
5. **"What was difficult?"** – Pick something real from your own build.
6. **"How would you improve it?"** – Database instead of a file, monthly reports, a GUI.

**Done when:** you can answer all six without looking at notes.
