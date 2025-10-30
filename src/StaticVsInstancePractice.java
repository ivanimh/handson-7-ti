public class StaticVsInstancePractice {
    public static void main(String[] args) {
        /*
         * PRAKTIK HANDS-ON: Static vs Instance
         *
         * Instruksi: Lengkapi semua latihan di bawah ini untuk menguasai
         * perbedaan static dan instance members, kapan menggunakan masing-masing.
         */

        // ===== STATIC VARIABLES DEMO =====
        System.out.println("=== STATIC VARIABLES DEMO ===");

        // Latihan 1: Static variables sebagai shared data
        // Buat beberapa object dari class yang memiliki static counter
        // Tunjukkan bahwa static variable di-share oleh semua object

        // Demonstrasikan static variable sharing
        Counter c1 = new Counter("C1");
        Counter c2 = new Counter("C2");
        Counter c3 = new Counter("C3");
        System.out.printf("App: %s | Global count = %d%n", Counter.APP_NAME, Counter.getGlobalCount());
        c1.incrementInstance();
        c1.incrementInstance();
        c2.incrementInstance();
        c1.displayCounterInfo();
        c2.displayCounterInfo();
        c3.displayCounterInfo();

        System.out.println("-- Reset global count --");
        Counter.resetGlobalCount();
        Counter.displayAppInfo();

        // ===== STATIC METHODS DEMO =====
        System.out.println("\n=== STATIC METHODS DEMO ===");

        // Latihan 2: Static methods sebagai utility functions
        // Implementasikan utility methods yang tidak butuh object
        // Tunjukkan cara memanggil static methods

        // Demonstrasikan static methods usage
        double area = MathUtils.calculateCircleArea(3.0);
        double dist = MathUtils.calculateDistance(0, 0, 3, 4);
        System.out.printf("Circle area (r=3): %.2f | Distance (0,0)->(3,4): %.2f%n", area, dist);
        System.out.println("Is 17 prime? " + MathUtils.isPrime(17));
        System.out.println("5! = " + MathUtils.factorial(5));
        System.out.println("2^10 = " + MathUtils.power(2, 10));

        // ===== STATIC VS INSTANCE COMPARISON =====
        System.out.println("\n=== STATIC VS INSTANCE COMPARISON ===");

        // Latihan 3: Perbandingan langsung static vs instance
        // Tunjukkan memory usage difference
        // Performance comparison

        // Implementasikan perbandingan
        System.out.println("Static members (shared) vs Instance members (per-object):");
        System.out.println(" Creating 3 new Counters to show global count increases but instance counters are separate.");
        Counter temp1 = new Counter("T1");
        Counter temp2 = new Counter("T2");
        Counter temp3 = new Counter("T3");
        System.out.println("Global count after creating temp counters: " + Counter.getGlobalCount());

        // ===== STATIC INITIALIZATION =====
        System.out.println("\n=== STATIC INITIALIZATION ===");

        // Latihan 4: Static initialization blocks
        // Tunjukkan kapan static variables diinisialisasi
        // Static blocks vs static variable initialization

        // Demonstrasikan static initialization
        System.out.println("DatabaseConnection initialized? " + DatabaseConnection.isInitialized());

        // ===== BEST PRACTICES =====
        System.out.println("\n=== BEST PRACTICES ===");

        // Latihan 5: Kapan menggunakan static vs instance
        // Constants (static final)
        // Utility methods (static)
        // Counters/global state (static)
        // Object-specific data (instance)

        // Implementasikan best practices examples
        System.out.println("MathUtils.PI = " + MathUtils.PI + " | MathUtils.E = " + MathUtils.E);

        // DatabaseConnection demo
        System.out.println("\n-- DatabaseConnection pool demo --");
        DatabaseConnection dbc1 = DatabaseConnection.getConnection("db1");
        DatabaseConnection dbc2 = DatabaseConnection.getConnection("db2");
        DatabaseConnection dbc3 = DatabaseConnection.getConnection("db3");
        System.out.println("Active connections: " + DatabaseConnection.getActiveConnectionCount());
        if (dbc1 != null) dbc1.executeQuery("SELECT * FROM users");
        if (dbc2 != null) dbc2.disconnect();
        System.out.println("Active connections after disconnecting one: " + DatabaseConnection.getActiveConnectionCount());

        // Student demo
        System.out.println("\n-- Student demo --");
        Student.setUniversityName("Teknik Negeri");
        Student s1 = new Student("S001", "Alya", "CS", 3.8);
        Student s2 = new Student("S002", "Budi", "EE", 3.2);
        s1.displayStudentInfo();
        s2.displayStudentInfo();
        System.out.println("Total students: " + Student.getTotalStudents());

        System.out.println("\n=== Demo selesai ===");
    }
}

// ===== CLASS DEFINITIONS =====

// Implementasikan class Counter dengan static dan instance counters
class Counter {
    // Static variables
    // static int globalCount
    // static final String APP_NAME
    public static int globalCount = 0;
    public static final String APP_NAME = "CounterApp";

    // Instance variables
    // int instanceCount
    // String counterName
    private int instanceCount = 0;
    private String counterName;

    // Static initialization block
    // static { ... }
    static {
        // sederhana: log saat kelas diload
        System.out.println("[Counter] static init: APP_NAME=" + APP_NAME);
    }

    // Constructor
    // Increment both static dan instance counters
    public Counter(String name) {
        this.counterName = name;
        globalCount++;
    }

    // Static methods
    // static int getGlobalCount()
    // static void resetGlobalCount()
    // static void displayAppInfo()
    public static int getGlobalCount() { return globalCount; }
    public static void resetGlobalCount() { globalCount = 0; }
    public static void displayAppInfo() { System.out.println("App=" + APP_NAME + " | globalCount=" + globalCount); }

    // Instance methods
    // int getInstanceCount()
    // void incrementInstance()
    // void displayCounterInfo()
    public int getInstanceCount() { return instanceCount; }
    public void incrementInstance() { instanceCount++; }
    public void displayCounterInfo() {
        System.out.printf("Counter %s -> instanceCount=%d | globalCount=%d%n", counterName, instanceCount, globalCount);
    }
}

// Implementasikan class MathUtils dengan static utility methods
class MathUtils {
    // Constants
    // static final double PI
    // static final double E
    public static final double PI = Math.PI;
    public static final double E = Math.E;

    // Static utility methods
    // static double calculateCircleArea(double radius)
    // static double calculateDistance(double x1, double y1, double x2, double y2)
    // static boolean isPrime(int number)
    // static int factorial(int n)
    // static double power(double base, int exponent)
    public static double calculateCircleArea(double radius) {
        return PI * radius * radius;
    }

    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number <= 3) return true;
        if (number % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) return false;
        }
        return true;
    }

    public static int factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        int res = 1;
        for (int i = 2; i <= n; i++) res *= i;
        return res;
    }

    public static double power(double base, int exponent) {
        return Math.pow(base, exponent);
    }

    // Private constructor (utility class)
    private MathUtils() { /* no instantiation */ }
}

// Implementasikan class DatabaseConnection dengan static connection pool
class DatabaseConnection {
    // Static variables untuk connection pool
    // static int maxConnections
    // static int activeConnections
    // static boolean isInitialized
    private static final int maxConnections;
    private static int activeConnections = 0;
    private static boolean initialized = false;

    // Instance variables
    // String connectionId
    // boolean isConnected
    // String database
    private String connectionId;
    private boolean isConnected = false;
    private String database;

    // Static initialization
    // static block untuk setup connection pool
    static {
        maxConnections = 3; // keep small for demo
        initialized = true;
        System.out.println("[DatabaseConnection] static init: maxConnections=" + maxConnections);
    }

    private DatabaseConnection(String connectionId, String database) {
        this.connectionId = connectionId;
        this.database = database;
    }

    // Static methods untuk connection management
    // static DatabaseConnection getConnection()
    public static synchronized DatabaseConnection getConnection(String database) {
        if (!initialized) {
            System.out.println("Pool not initialized");
            return null;
        }
        if (activeConnections >= maxConnections) {
            System.out.println("No available connections (maxed out)");
            return null;
        }
        String id = "conn-" + (activeConnections + 1);
        DatabaseConnection conn = new DatabaseConnection(id, database);
        conn.connect();
        activeConnections++;
        return conn;
    }

    // static void closeAllConnections()
    public static synchronized void closeAllConnections() {
        activeConnections = 0;
        initialized = false;
        System.out.println("All connections closed and pool uninitialized");
    }

    // static int getActiveConnectionCount()
    public static synchronized int getActiveConnectionCount() {
        return activeConnections;
    }

    public static boolean isInitialized() { return initialized; }

    // Instance methods
    // void connect()
    public void connect() {
        this.isConnected = true;
        System.out.println("[" + connectionId + "] connected to " + database);
    }

    // void disconnect()
    public void disconnect() {
        if (this.isConnected) {
            this.isConnected = false;
            synchronized (DatabaseConnection.class) {
                if (activeConnections > 0) activeConnections--;
            }
            System.out.println("[" + connectionId + "] disconnected from " + database);
        }
    }

    // void executeQuery(String query)
    public void executeQuery(String query) {
        if (!isConnected) {
            System.out.println("[" + connectionId + "] not connected. Cannot execute query.");
            return;
        }
        System.out.println("[" + connectionId + "] executing: " + query);
    }
}

// Implementasikan class Student dengan mixed static/instance
class Student {
    // Static variables
    // static String universityName
    // static int totalStudents
    private static String universityName = "Default University";
    private static int totalStudents = 0;

    // Instance variables
    // String studentId, name, major
    // double gpa
    private String studentId;
    private String name;
    private String major;
    private double gpa;

    // Constructor
    // Increment totalStudents
    public Student(String studentId, String name, String major, double gpa) {
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.gpa = gpa;
        totalStudents++;
    }

    // Static methods
    // static int getTotalStudents()
    // static void setUniversityName(String name)
    // static String getUniversityName()
    public static int getTotalStudents() { return totalStudents; }
    public static void setUniversityName(String name) { if (name != null) universityName = name; }
    public static String getUniversityName() { return universityName; }

    // Instance methods
    // void updateGPA(double newGPA)
    // void displayStudentInfo()
    // boolean isHonorStudent()
    public void updateGPA(double newGPA) {
        if (newGPA < 0.0 || newGPA > 4.0) throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        this.gpa = newGPA;
    }

    public void displayStudentInfo() {
        System.out.printf("Student %s | %s | %s | GPA=%.2f | University=%s%n",
                studentId, name, major, gpa, universityName);
    }

    public boolean isHonorStudent() { return this.gpa >= 3.5; }
}
