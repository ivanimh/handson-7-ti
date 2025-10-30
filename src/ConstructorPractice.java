import java.time.LocalDate;

public class ConstructorPractice {
    public static void main(String[] args) {
        /*
         * PRAKTIK HANDS-ON: Constructors
         *
         * Instruksi: Lengkapi semua latihan di bawah ini untuk menguasai
         * constructors, constructor overloading, dan keyword this.
         */

        // ===== DEFAULT VS CUSTOM CONSTRUCTOR =====
        System.out.println("=== DEFAULT VS CUSTOM CONSTRUCTOR ===");

        // Latihan 1: Memahami default constructor
        // Buat class dengan dan tanpa custom constructor
        // Tunjukkan perbedaan penggunaan default constructor

        // Demonstrasikan penggunaan default constructor
        SimpleClass s1 = new SimpleClass(); // uses implicit default constructor
        System.out.println("SimpleClass instance created. defaultValue = " + s1.defaultValue);

        // ===== CONSTRUCTOR OVERLOADING =====
        System.out.println("\n=== CONSTRUCTOR OVERLOADING ===");

        // Latihan 2: Multiple constructors untuk berbagai skenario
        // Implementasikan class Product dengan multiple constructors
        // Setiap constructor untuk kasus penggunaan yang berbeda

        // Buat object Product menggunakan berbagai constructors
        Product pDefault = new Product(); // default
        Product pEssential = new Product("P100", "Bola", 29.99); // essential
        Product pFull = new Product("P200", "Sepatu", "Sepatu lari nyaman", 499.99, "Olahraga", 20, "PT Sepatu Nusantara");
        Product pCopy = new Product(pFull); // copy constructor

        System.out.println("\n--- Produk yang dibuat ---");
        pDefault.displayProductInfo();
        System.out.println();
        pEssential.displayProductInfo();
        System.out.println();
        pFull.displayProductInfo();
        System.out.println();
        pCopy.displayProductInfo();

        // coba update stock dan diskon
        pFull.updateStock(5);
        pFull.applyDiscount(10); // 10%
        System.out.println("\nSetelah update stock dan diskon pada pFull:");
        pFull.displayProductInfo();

        // ===== KEYWORD THIS =====
        System.out.println("\n=== KEYWORD THIS ===");

        // Latihan 3: Penggunaan this dalam constructor dan methods
        // Demonstrasikan this untuk menghindari name collision
        // Gunakan this untuk memanggil constructor lain
        // Gunakan this untuk mereferensikan current object

        // Demonstrasikan berbagai penggunaan this
        Product pChaining = new Product("P300", "Kaos", 79.0);
        pChaining.displayProductInfo();

        // ===== CONSTRUCTOR CHAINING =====
        System.out.println("\n=== CONSTRUCTOR CHAINING ===");

        // Latihan 4: Constructor yang memanggil constructor lain
        // Implementasikan constructor chaining dengan this()
        // Tunjukkan keuntungan constructor chaining

        Employee e1 = new Employee("E001", "Dian", "Wahyuni");
        Employee e2 = new Employee("E002", "Rudi", "Santoso", "IT");
        Employee e3 = new Employee("E003", "Sinta", "Putri", "HR", "Recruiter", 7500000.0, LocalDate.of(2018, 5, 21));

        System.out.println("\n--- Employee Info ---");
        System.out.println(e1.getEmployeeInfo());
        System.out.println(e2.getEmployeeInfo());
        System.out.println(e3.getEmployeeInfo());

        // give raise and years of service
        e3.giveRaise(5.0); // 5%
        System.out.println("After 5% raise: " + e3.getEmployeeInfo());
        System.out.println("Years of service for e3: " + e3.calculateYearsOfService());

        // ===== INITIALIZATION ORDER =====
        System.out.println("\n=== INITIALIZATION ORDER ===");

        // Latihan 5: Urutan inisialisasi object
        // Tunjukkan urutan eksekusi saat object dibuat
        // Instance variable initialization vs constructor

        // Demonstrasikan urutan inisialisasi
        System.out.println("Membuat InitializationDemo instance:");
        InitializationDemo idemo = new InitializationDemo();
        idemo.displayState();
    }
}

// ===== CLASS DEFINITIONS =====

// Implementasikan class SimpleClass (tanpa custom constructor)
class SimpleClass {
    // Hanya instance variables, tanpa constructor
    public String defaultValue = "ini nilai default (dideklarasikan langsung)";

    // contoh method
    public void sayHello() {
        System.out.println("Halo dari SimpleClass!");
    }
}

// Implementasikan class Product dengan constructor overloading
class Product {
    // Instance variables
    // productId, name, description, price, category, inStock, supplier
    private String productId;
    private String name;
    private String description;
    private double price;
    private String category;
    private int inStock;
    private String supplier;

    // Constructor 1: Full parameters
    // public Product(String productId, String name, String description, double price, String category, int inStock, String supplier)
    public Product(String productId, String name, String description, double price, String category, int inStock, String supplier) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.inStock = inStock;
        this.supplier = supplier;
    }

    // Constructor 2: Essential parameters only
    // public Product(String productId, String name, double price)
    public Product(String productId, String name, double price) {
        // gunakan constructor full dengan nilai default untuk sisanya
        this(productId, name, "Deskripsi belum tersedia", price, "Umum", 0, "Supplier belum tersedia");
    }

    // Constructor 3: Copy constructor
    // public Product(Product other)
    public Product(Product other) {
        if (other == null) {
            this.productId = "UNKNOWN";
            this.name = "UNKNOWN";
            this.description = "";
            this.price = 0.0;
            this.category = "Umum";
            this.inStock = 0;
            this.supplier = "";
        } else {
            this.productId = other.productId;
            this.name = other.name;
            this.description = other.description;
            this.price = other.price;
            this.category = other.category;
            this.inStock = other.inStock;
            this.supplier = other.supplier;
        }
    }

    // Constructor 4: Default constructor with default values
    // public Product()
    public Product() {
        this("P000", "Produk Default", "Produk contoh", 0.0, "Umum", 0, "Tanpa Supplier");
    }

    // Methods
    // displayProductInfo()
    public void displayProductInfo() {
        System.out.println("Product ID: " + productId);
        System.out.println("Name      : " + name);
        System.out.println("Desc      : " + description);
        System.out.println("Price     : " + String.format("%.2f", price));
        System.out.println("Category  : " + category);
        System.out.println("InStock   : " + inStock);
        System.out.println("Supplier  : " + supplier);
    }

    // updateStock(int quantity)
    public void updateStock(int quantity) {
        this.inStock += quantity;
    }

    // applyDiscount(double percentage)
    public void applyDiscount(double percentage) {
        if (percentage <= 0 || percentage >= 100) return;
        double factor = (100.0 - percentage) / 100.0;
        this.price = this.price * factor;
    }

    // isAvailable()
    public boolean isAvailable() {
        return this.inStock > 0;
    }

    // getters (opsional)
    public String getProductId() { return productId; }
    public double getPrice() { return price; }
}

// Implementasikan class Employee dengan constructor chaining
class Employee {
    // Instance variables
    // employeeId, firstName, lastName, department, position, salary, hireDate
    private String employeeId;
    private String firstName;
    private String lastName;
    private String department;
    private String position;
    private double salary;
    private LocalDate hireDate;

    // Constructor chaining examples
    // Buat multiple constructors yang saling memanggil dengan this()

    // minimal constructor: id + names
    public Employee(String employeeId, String firstName, String lastName) {
        this(employeeId, firstName, lastName, "General"); // panggil constructor lain
    }

    // with department
    public Employee(String employeeId, String firstName, String lastName, String department) {
        this(employeeId, firstName, lastName, department, "Staff"); // panggil lagi
    }

    // with position
    public Employee(String employeeId, String firstName, String lastName, String department, String position) {
        this(employeeId, firstName, lastName, department, position, 0.0, LocalDate.now());
    }

    // full constructor
    public Employee(String employeeId, String firstName, String lastName, String department, String position, double salary, LocalDate hireDate) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.hireDate = (hireDate == null ? LocalDate.now() : hireDate);
    }

    // Methods
    // getFullName()
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // calculateYearsOfService()
    public int calculateYearsOfService() {
        LocalDate now = LocalDate.now();
        if (hireDate != null && now.isAfter(hireDate)) {
            return now.getYear() - hireDate.getYear() - (now.getDayOfYear() < hireDate.getDayOfYear() ? 1 : 0);
        }
        return 0;
    }

    // getEmployeeInfo()
    public String getEmployeeInfo() {
        return employeeId + " | " + getFullName() + " | " + department + " | " + position + " | Salary: " + String.format("%.2f", salary) + " | Hired: " + hireDate;
    }

    // giveRaise(double percentage)
    public void giveRaise(double percentage) {
        if (percentage <= 0) return;
        this.salary = this.salary * (1.0 + (percentage / 100.0));
    }
}

// Implementasikan class InitializationDemo
class InitializationDemo {
    // Tunjukkan instance variable initialization
    // Tunjukkan urutan eksekusi constructor
    // Tambahkan System.out.println di berbagai tempat untuk tracking

    // static initializer
    static {
        System.out.println("Static initializer: class InitializationDemo loaded.");
    }

    // instance field initializer (method call to show order)
    private String field = initializeField();

    // instance initializer block
    {
        System.out.println("Instance initializer block executed.");
    }

    // constructor
    public InitializationDemo() {
        System.out.println("Constructor of InitializationDemo executed.");
    }

    private String initializeField() {
        System.out.println("Field initializer method called.");
        return "field-initialized";
    }

    public void displayState() {
        System.out.println("displayState: field = " + field);
    }
}
