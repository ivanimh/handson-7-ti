import java.util.Random;

public class InstanceMembersPractice {
    public static void main(String[] args) {
        /*
         * PRAKTIK HANDS-ON: Instance Variables & Methods
         *
         * Instruksi: Lengkapi semua latihan di bawah ini untuk menguasai
         * instance variables, instance methods, dan perbedaannya dengan static.
         */

        // ===== INSTANCE VARIABLES BASICS =====
        System.out.println("=== INSTANCE VARIABLES BASICS ===");

        // Latihan 1: Memahami instance variables
        // Buat beberapa object dari class BankAccount
        // Tunjukkan bahwa setiap object memiliki data sendiri

        // Buat 3 object BankAccount dengan data berbeda
        BankAccount acc1 = new BankAccount("111-AAA", "Alya Putri", "SAVINGS", 1000.0);
        BankAccount acc2 = new BankAccount("222-BBB", "Budi Santoso", "CHECKING", 500.0);
        BankAccount acc3 = new BankAccount("333-CCC", "Citra Dewi", "SAVINGS", 2500.0);

        // Tampilkan saldo masing-masing account
        acc1.displayBalance();
        acc2.displayBalance();
        acc3.displayBalance();

        // Lakukan transaksi pada masing-masing account
        System.out.println("\n-- Lakukan transaksi pada masing-masing account --");
        acc1.deposit(250.0);
        acc2.withdraw(100.0);
        acc3.deposit(500.0).withdraw(200.0); // contoh chaining: deposit lalu withdraw

        // Tunjukkan bahwa perubahan pada satu object tidak mempengaruhi yang lain
        System.out.println("\nSaldo setelah transaksi:");
        acc1.displayBalance();
        acc2.displayBalance();
        acc3.displayBalance();

        // ===== INSTANCE METHODS ADVANCED =====
        System.out.println("\n=== INSTANCE METHODS ADVANCED ===");

        // Latihan 2: Instance methods yang bekerja dengan instance variables
        // Implementasikan berbagai jenis instance methods
        // Methods yang mengubah state object
        // Methods yang mengembalikan nilai berdasarkan state
        // Methods yang berinteraksi dengan object lain

        // Demonstrasikan berbagai jenis instance methods
        double interestAcc1 = acc1.calculateInterest(0.03); // 3% interest (hanya hitung)
        System.out.println("Interest untuk acc1 (3%): " + String.format("%.2f", interestAcc1));
        acc1.deposit(interestAcc1);
        System.out.println("Bisa withdraw 5000 dari acc2? " + (acc2.canWithdraw(5000.0) ? "Ya" : "Tidak"));

        // ===== METHOD INTERACTION =====
        System.out.println("\\n=== METHOD INTERACTION ===");

        // Latihan 3: Methods yang memanggil methods lain
        // Buat methods yang memanggil methods lain dalam class yang sama
        // Demonstrasikan method chaining

        // Implementasikan method chaining dan interaction
        acc3.safeWithdraw(1000.0);
        acc3.displayBalance();

        // ===== OBJECT COLLABORATION =====
        System.out.println("\\n=== OBJECT COLLABORATION ===");

        // Latihan 4: Object yang berinteraksi dengan object lain
        // Implementasikan transfer uang antar account
        // Buat history transaksi

        // Implementasikan object collaboration
        System.out.println("Transfer 200 dari acc1 ke acc2:");
        boolean t1 = acc1.transfer(acc2, 200.0);
        System.out.println("Transfer berhasil? " + (t1 ? "Ya" : "Tidak"));

        System.out.println("\n--- Statement acc1 ---");
        acc1.printStatement();

        System.out.println("\n--- Statement acc2 ---");
        acc2.printStatement();

        // Demo Customer
        System.out.println("\n=== CUSTOMER & MULTI ACCOUNT ===");
        Customer custAlya = new Customer("CUST-001", "Alya Putri", "081234567890", "alya@example.com");
        custAlya.addAccount(acc1);
        custAlya.addAccount(acc3);
        custAlya.printCustomerInfo();
        System.out.println("Total balance (Alya): " + String.format("%.2f", custAlya.getTotalBalance()));

        System.out.println("\nAlya transfer 300 dari acc3 ke acc1 lewat Customer helper:");
        boolean ct = custAlya.transferBetweenAccounts("333-CCC", "111-AAA", 300.0);
        System.out.println("Berhasil? " + (ct ? "Ya" : "Tidak"));

        System.out.println("\nSaldo akhir setelah transfer:");
        acc1.displayBalance();
        acc3.displayBalance();

        System.out.println("\n--- Final statements ---");
        acc1.printStatement();
        acc3.printStatement();
    }
}

// ===== CLASS DEFINITIONS =====

// Implementasikan class BankAccount
class BankAccount {
    // Instance variables
    // accountNumber, accountHolderName, balance, accountType, isActive
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String accountType;
    private boolean isActive;
    private Transaction[] history;
    private int historyCount;

    private static Random rnd = new Random();

    // Constructor(s)
    // Buat multiple constructors
    public BankAccount(String accountNumber, String accountHolderName, String accountType, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = Math.max(0.0, initialBalance);
        this.isActive = true;
        this.history = new Transaction[16];
        this.historyCount = 0;
        addHistory(new Transaction("OPEN", initialBalance, "Initial deposit / opening balance"));
    }

    // Instance methods untuk account operations
    // deposit(double amount)
    public BankAccount deposit(double amount) {
        if (!isActive) {
            System.out.println("Account " + accountNumber + " inactive. Deposit rejected.");
            return this;
        }
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return this;
        }
        balance += amount;
        addHistory(new Transaction("DEPOSIT", amount, "Deposit to account"));
        return this;
    }

    // withdraw(double amount) - with validation
    public boolean withdraw(double amount) {
        if (!isActive) {
            System.out.println("Account " + accountNumber + " inactive. Withdrawal rejected.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Withdraw amount must be positive.");
            return false;
        }
        if (!canWithdraw(amount)) {
            System.out.println("Insufficient funds for withdrawal from " + accountNumber);
            return false;
        }
        balance -= amount;
        addHistory(new Transaction("WITHDRAW", amount, "Withdraw from account"));
        return true;
    }

    // transfer(BankAccount target, double amount)
    public boolean transfer(BankAccount target, double amount) {
        if (target == null) {
            System.out.println("Target account is null.");
            return false;
        }
        if (!this.isActive) {
            System.out.println("Source account inactive. Transfer rejected.");
            return false;
        }
        if (!target.isActive) {
            System.out.println("Target account inactive. Transfer rejected.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive.");
            return false;
        }
        if (!canWithdraw(amount)) {
            System.out.println("Insufficient funds to transfer from " + accountNumber);
            return false;
        }
        this.balance -= amount;
        target.balance += amount;
        addHistory(new Transaction("TRANSFER_OUT", amount, "Transfer to " + target.accountNumber));
        target.addHistory(new Transaction("TRANSFER_IN", amount, "Transfer from " + this.accountNumber));
        return true;
    }

    // getBalance()
    public double getBalance() {
        return balance;
    }

    // getAccountInfo()
    public String getAccountInfo() {
        return accountNumber + " | " + accountHolderName + " | " + accountType + " | Balance: " + String.format("%.2f", balance) + " | Active: " + isActive;
    }

    // activateAccount() / deactivateAccount()
    public void activateAccount() {
        this.isActive = true;
        addHistory(new Transaction("ACCOUNT_ACTIVATE", 0.0, "Account activated"));
    }

    public void deactivateAccount() {
        this.isActive = false;
        addHistory(new Transaction("ACCOUNT_DEACTIVATE", 0.0, "Account deactivated"));
    }

    // Instance methods untuk business logic
    // calculateInterest(double rate)
    public double calculateInterest(double rate) {
        if (rate <= 0) return 0.0;
        return this.balance * rate;
    }

    // canWithdraw(double amount)
    public boolean canWithdraw(double amount) {
        return amount <= this.balance && this.isActive;
    }

    // getAccountStatus()
    public String getAccountStatus() {
        return isActive ? "Active" : "Inactive";
    }

    // Instance methods untuk formatting/display
    // displayBalance()
    public void displayBalance() {
        System.out.println("Account " + accountNumber + " (" + accountHolderName + ") - Balance: " + String.format("%.2f", balance));
    }

    // printStatement()
    public void printStatement() {
        System.out.println("Statement for account " + accountNumber + " (" + accountHolderName + "):");
        if (historyCount == 0) {
            System.out.println("  No transactions.");
            return;
        }
        for (int i = 0; i < historyCount; i++) {
            Transaction t = history[i];
            System.out.println("  " + t);
        }
        System.out.println("  CURRENT BALANCE: " + String.format("%.2f", balance));
    }

    // helper: safeWithdraw which uses canWithdraw internally
    public boolean safeWithdraw(double amount) {
        if (canWithdraw(amount)) {
            return withdraw(amount);
        } else {
            System.out.println("safeWithdraw: cannot withdraw " + amount + " from " + accountNumber);
            return false;
        }
    }

    // helper to add history and grow array
    private void addHistory(Transaction t) {
        if (historyCount >= history.length) {
            Transaction[] newHist = new Transaction[history.length * 2];
            for (int i = 0; i < history.length; i++) newHist[i] = history[i];
            history = newHist;
        }
        history[historyCount++] = t;
    }
}

// Implementasikan class Transaction (untuk history)
class Transaction {
    // transactionId, type, amount, timestamp, description
    private String transactionId;
    private String type;
    private double amount;
    private long timestampMillis;
    private String description;

    private static Random rnd = new Random();

    // Constructor dan methods
    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestampMillis = System.currentTimeMillis();
        this.transactionId = generateId();
    }

    private String generateId() {
        long t = timestampMillis;
        int r = Math.abs(rnd.nextInt()) % 900 + 100;
        return "TX" + (t % 1000000) + "-" + r;
    }

    @Override
    public String toString() {
        return "[" + transactionId + "] " + timestampMillis + " | " + type + " | " + String.format("%.2f", amount) + " | " + description;
    }
}

// Implementasikan class Customer
class Customer {
    // customerId, name, phone, email, accounts[]
    private String customerId;
    private String name;
    private String phone;
    private String email;
    private BankAccount[] accounts;
    private int accCount;

    // Methods untuk mengelola multiple accounts
    public Customer(String customerId, String name, String phone, String email) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.accounts = new BankAccount[4];
        this.accCount = 0;
    }

    private void ensureAccountCapacity() {
        if (accCount >= accounts.length) {
            BankAccount[] newArr = new BankAccount[accounts.length * 2];
            for (int i = 0; i < accounts.length; i++) newArr[i] = accounts[i];
            accounts = newArr;
        }
    }

    public void addAccount(BankAccount account) {
        if (account != null) {
            ensureAccountCapacity();
            accounts[accCount++] = account;
        }
    }

    public boolean removeAccount(String accountNumber) {
        for (int i = 0; i < accCount; i++) {
            if (accounts[i].getAccountInfo().startsWith(accountNumber)) {
                for (int j = i; j < accCount - 1; j++) accounts[j] = accounts[j+1];
                accounts[--accCount] = null;
                return true;
            }
        }
        return false;
    }

    public double getTotalBalance() {
        double total = 0.0;
        for (int i = 0; i < accCount; i++) total += accounts[i].getBalance();
        return total;
    }

    public BankAccount findAccount(String accountNumber) {
        for (int i = 0; i < accCount; i++) {
            if (accounts[i].getAccountInfo().startsWith(accountNumber)) return accounts[i];
        }
        return null;
    }

    public boolean transferBetweenAccounts(String fromAcc, String toAcc, double amount) {
        BankAccount src = findAccount(fromAcc);
        BankAccount dst = findAccount(toAcc);
        if (src == null || dst == null) {
            System.out.println("One of the accounts not found for customer " + name);
            return false;
        }
        return src.transfer(dst, amount);
    }

    public void printCustomerInfo() {
        System.out.println("Customer: " + customerId + " | " + name + " | " + phone + " | " + email);
        System.out.println("Accounts owned:");
        for (int i = 0; i < accCount; i++) {
            System.out.println("  - " + accounts[i].getAccountInfo());
        }
    }
}
