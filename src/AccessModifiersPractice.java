public class AccessModifiersPractice {
    public static void main(String[] args) {
        /*
         * PRAKTIK HANDS-ON: Access Modifiers & Encapsulation
         *
         * Instruksi: Lengkapi semua latihan di bawah ini untuk menguasai
         * access modifiers, encapsulation, dan data protection.
         */

        // ===== MASALAH DENGAN PUBLIC VARIABLES =====
        System.out.println("=== MASALAH DENGAN PUBLIC VARIABLES ===");

        // Latihan 1: Tunjukkan masalah dengan public instance variables
        // Buat object dengan public variables
        // Tunjukkan bagaimana data bisa dirusak dari luar

        // Demonstrasikan masalah public variables
        BadExample bad = new BadExample("Andi", 20, 2500.0, "andi@example.com");
        System.out.printf("Before  : name=%s | age=%d | salary=%.2f | email=%s%n",
                bad.name, bad.age, bad.salary, bad.email);
        // ubah field langsung (tidak ada proteksi)
        bad.name = "Hacker";
        bad.salary = -99999.0;
        System.out.printf("After   : name=%s | age=%d | salary=%.2f | email=%s%n",
                bad.name, bad.age, bad.salary, bad.email);

        // buat pembatas yang rapi
        System.out.println("----------------------------------------");

        // ===== ENCAPSULATION SOLUTION =====
        System.out.println("=== ENCAPSULATION SOLUTION ===");

        // Latihan 2: Implementasi encapsulation
        // Gunakan private variables dengan getter/setter
        // Tambahkan validasi dalam setter

        // Demonstrasikan encapsulation dengan class yang proper
        GoodExample good = null;
        try {
            good = new GoodExample("Budi", 30, 10000.0, "budi@example.com");
            System.out.printf("GoodExample: name=%s | age=%d | salary=%.2f | email=%s%n",
                    good.getName(), good.getAge(), good.getSalary(), good.getEmail());
            try {
                good.setAge(16); // invalid
            } catch (IllegalArgumentException ex) {
                System.out.println("Gagal set age: " + ex.getMessage());
            }
            try {
                good.setEmail("not-an-email");
            } catch (IllegalArgumentException ex) {
                System.out.println("Gagal set email: " + ex.getMessage());
            }
            good.setSalary(12000.0);
            System.out.printf("Updated   : name=%s | age=%d | salary=%.2f | email=%s%n",
                    good.getName(), good.getAge(), good.getSalary(), good.getEmail());
            System.out.println("Is retirement age? " + good.isRetirementAge());
            System.out.println("Calculate tax: " + String.format("%.2f", good.calculateTax()));
        } catch (IllegalArgumentException ex) {
            System.out.println("Gagal membuat GoodExample: " + ex.getMessage());
        }

        System.out.println("----------------------------------------");

        // ===== ACCESS MODIFIER COMBINATIONS =====
        System.out.println("=== ACCESS MODIFIER COMBINATIONS ===");

        // Latihan 3: Berbagai kombinasi access modifiers
        // Tunjukkan perbedaan private, public, protected, default

        // Implementasikan class dengan berbagai access modifiers
        AccessModifierDemo demo = new AccessModifierDemo();
        demo.testAccess(); // sekarang testAccess mencetak ringkasan rapi

        System.out.println("----------------------------------------");

        // ===== GETTER/SETTER BEST PRACTICES =====
        System.out.println("=== GETTER/SETTER BEST PRACTICES ===");

        // Latihan 4: Implementasi getter/setter yang proper
        // Naming conventions
        // Validation dalam setter
        // Read-only dan write-only properties

        // Implementasikan getter/setter yang proper
        // Contoh read-only pada BankAccountSecure
        BankAccountSecure acct = new BankAccountSecure("9876543210", 500.0, "1234");
        System.out.println("Account number: " + acct.getAccountNumber() + " | status=" + acct.getAccountStatus());
        acct.deposit(200.0);
        try {
            System.out.println("Balance: " + String.format("%.2f", acct.checkBalance("1234")));
        } catch (RuntimeException ex) {
            System.out.println("Cannot check balance: " + ex.getMessage());
        }

        System.out.println("=== Demo selesai (output dirapikan) ===");
    }
}

// ===== CLASS DEFINITIONS =====

// Implementasikan class BadExample (dengan public variables)
class BadExample {
    // Semua variables public - tunjukkan masalahnya
    public String name;
    public int age;
    public double salary;
    public String email;

    // Constructor sederhana
    public BadExample(String name, int age, double salary, String email) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.email = email;
    }

    // Method untuk menunjukkan data corruption
    public void corruptData() {
        this.name = "CORRUPT";
        this.age = -1;
        this.salary = -1.0;
        this.email = "invalid";
    }

    @Override
    public String toString() {
        return String.format("BadExample{name='%s', age=%d, salary=%.2f, email='%s'}", name, age, salary, email);
    }
}

// Implementasikan class GoodExample (dengan encapsulation)
class GoodExample {
    // Private instance variables
    // private String name, age, salary, email
    private String name;
    private int age;
    private double salary;
    private String email;

    // Constructor
    // Constructor dengan parameter validation
    public GoodExample(String name, int age, double salary, String email) {
        setName(name);
        setAge(age);
        setSalary(salary);
        setEmail(email);
    }

    // Getter methods
    // Implementasikan getter methods yang proper
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getSalary() { return salary; }
    public String getEmail() { return email; }

    // Setter methods dengan validation
    // setName() - tidak boleh null/kosong
    // setAge() - harus antara 17-65
    // setSalary() - harus positif
    // setEmail() - format email yang valid
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name tidak boleh kosong");
        this.name = name.trim();
    }

    public void setAge(int age) {
        if (age < 17 || age > 65) throw new IllegalArgumentException("Age harus antara 17-65");
        this.age = age;
    }

    public void setSalary(double salary) {
        if (salary < 0) throw new IllegalArgumentException("Salary harus positif");
        this.salary = salary;
    }

    public void setEmail(String email) {
        if (email == null || !validateEmail(email)) throw new IllegalArgumentException("Email tidak valid");
        this.email = email.trim();
    }

    // Business methods
    // validateEmail(String email)
    public static boolean validateEmail(String email) {
        if (email == null) return false;
        String e = email.trim();
        return e.contains("@") && e.contains(".");
    }

    // isRetirementAge()
    public boolean isRetirementAge() {
        return this.age >= 60;
    }

    // calculateTax()
    public double calculateTax() {
        // contoh sederhana: 10% flat
        return this.salary * 0.10;
    }

    @Override
    public String toString() {
        return String.format("GoodExample{name='%s', age=%d, salary=%.2f, email='%s'}", name, age, salary, email);
    }
}

// Implementasikan class BankAccountSecure
class BankAccountSecure {
    // Private variables
    // accountNumber, balance, pin, isLocked
    private final String accountNumber;
    private double balance;
    private String pin; // for demo only
    private boolean isLocked = false;
    private int failedAttempts = 0;
    private int securityLevel = 1; // write-only example

    // Constructor
    // Constructor dengan validation
    public BankAccountSecure(String accountNumber, double initialBalance, String pin) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) throw new IllegalArgumentException("Account number tidak boleh kosong");
        if (initialBalance < 0) throw new IllegalArgumentException("Initial balance harus >= 0");
        if (pin == null || !pin.matches("\\d{4}")) throw new IllegalArgumentException("Pin harus 4 digit");
        this.accountNumber = accountNumber.trim();
        this.balance = initialBalance;
        this.pin = pin;
    }

    // Public interface methods
    // deposit(double amount)
    public synchronized void deposit(double amount) {
        if (isLocked) throw new RuntimeException("Akun terkunci");
        if (!isValidAmount(amount)) throw new IllegalArgumentException("Amount harus > 0");
        this.balance += amount;
    }

    // withdraw(double amount, String pin)
    public synchronized void withdraw(double amount, String pinAttempt) {
        if (isLocked) throw new RuntimeException("Akun terkunci");
        if (!validatePin(pinAttempt)) throw new RuntimeException("PIN salah");
        if (!isValidAmount(amount)) throw new IllegalArgumentException("Amount harus > 0");
        if (amount > balance) throw new IllegalArgumentException("Saldo tidak mencukupi");
        this.balance -= amount;
    }

    // checkBalance(String pin)
    public synchronized double checkBalance(String pinAttempt) {
        if (isLocked) throw new RuntimeException("Akun terkunci");
        if (!validatePin(pinAttempt)) throw new RuntimeException("PIN salah");
        return this.balance;
    }

    // changePin(String oldPin, String newPin)
    public synchronized void changePin(String oldPin, String newPin) {
        if (isLocked) throw new RuntimeException("Akun terkunci");
        if (!validatePin(oldPin)) throw new RuntimeException("PIN lama salah");
        if (newPin == null || !newPin.matches("\\d{4}")) throw new IllegalArgumentException("PIN baru harus 4 digit");
        this.pin = newPin;
        this.failedAttempts = 0;
    }

    // Private helper methods
    // private boolean validatePin(String pin)
    private boolean validatePin(String pinAttempt) {
        if (pinAttempt == null) return false;
        if (this.pin.equals(pinAttempt)) {
            // sukses -> reset
            this.failedAttempts = 0;
            return true;
        }
        this.failedAttempts++;
        if (this.failedAttempts >= 3) lockAccount();
        return false;
    }

    // private void lockAccount()
    private void lockAccount() {
        this.isLocked = true;
    }

    // private boolean isValidAmount(double amount)
    private boolean isValidAmount(double amount) {
        return amount > 0;
    }

    // Read-only properties
    // getAccountNumber() - tanpa setter
    public String getAccountNumber() { return accountNumber; }

    // getAccountStatus()
    public String getAccountStatus() { return isLocked ? "LOCKED" : "ACTIVE"; }

    // Write-only properties (jarang digunakan)
    // setSecurityLevel(int level) - tanpa getter
    public void setSecurityLevel(int level) {
        if (level < 1 || level > 5) throw new IllegalArgumentException("Security level 1..5");
        this.securityLevel = level;
    }
}

// Implementasikan class AccessModifierDemo
class AccessModifierDemo {
    private String privateField = "Private";
    protected String protectedField = "Protected";
    String defaultField = "Default";
    public String publicField = "Public";

    private void privateMethod() {
    }

    protected void protectedMethod() {
    }

    void defaultMethod() {
    }

    public void publicMethod(){
    }

    // Method untuk test accessibility dari dalam class
    public void testAccess() {
        // Panggil semua methods dan akses semua fields
        System.out.println("AccessModifierDemo summary:");
        System.out.printf(" Fields: private=%s | protected=%s | default=%s | public=%s%n",
                privateField, protectedField, defaultField, publicField);
        // invoke methods (silent) just to show they are callable from inside
        privateMethod();
        protectedMethod();
        defaultMethod();
        publicMethod();
        System.out.println(" Methods invoked internally (silent) — see class-level access permitted from inside class.");
    }
}
