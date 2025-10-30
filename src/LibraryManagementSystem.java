import java.time.LocalDate;
import java.util.ArrayList;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        /*
         * PRAKTIK HANDS-ON: Comprehensive OOP Project
         *
         * Instruksi: Implementasikan sistem manajemen perpustakaan lengkap
         * yang menerapkan semua konsep OOP yang telah dipelajari.
         */

        // ===== SETUP LIBRARY SYSTEM =====
        System.out.println("=== LIBRARY MANAGEMENT SYSTEM ===");

        // Latihan 1: Setup library dengan berbagai tipe buku dan member
        // Buat library instance
        // Tambahkan berbagai buku
        // Daftarkan beberapa member
        Library lib = new Library("Perpustakaan Kampus", 14, 5);
        lib.addBook(new Book("978-1111", "Pemrograman Java", "Andi", "Gramedia", 2019, BookCategory.SCIENCE));
        lib.addBook(new Book("978-2222", "Struktur Data", "Siti", "Nusantara", 2017, BookCategory.SCIENCE));
        lib.addBook(new Book("978-3333", "Sejarah Indonesia", "Budi", "Sejarah Press", 1995, BookCategory.HISTORY));
        lib.addBook(new Book("978-4444", "Biografi Tokoh", "Citra", "BioPub", 2005, BookCategory.BIOGRAPHY));
        lib.addBook(new Book("978-5555", "Novel Fiksi", "Dewi", "Cerita Kita", 2021, BookCategory.FICTION));

        Member m1 = lib.registerMember("Rina", "rina@mail.com", "081111111", "Jl. Mawar 1", MembershipType.STUDENT);
        Member m2 = lib.registerMember("Pak Joko", "joko@univ.ac.id", "082222222", "Jl. Melati 2", MembershipType.FACULTY);
        Member m3 = lib.registerMember("Ani", "ani@mail.com", "083333333", "Jl. Kenanga 3", MembershipType.PUBLIC);

        System.out.println("\n--- Initial library stats ---");
        lib.displayLibraryStats();

        // ===== BOOK OPERATIONS =====
        System.out.println("\n=== BOOK OPERATIONS ===");

        // Latihan 2: Operasi buku
        // Tambah buku baru
        Book bNew = new Book("978-6666", "Algoritma", "Eko", "TechPress", 2015, BookCategory.SCIENCE);
        lib.addBook(bNew);

        // Cari buku berdasarkan kriteria
        System.out.println("\nSearch by title 'Data':");
        for (Book b : lib.searchBooksByTitle("Data")) {
            b.displayBookInfo();
            System.out.println();
        }

        // Update informasi buku
        Book found = lib.getBookByISBN("978-3333");
        if (found != null) {
            found.setPublisher("Sejarah Press (ed.2)");
            System.out.println("\nUpdated book info:");
            found.displayBookInfo();
        }

        // ===== MEMBER OPERATIONS =====
        System.out.println("\n=== MEMBER OPERATIONS ===");

        // Latihan 3: Operasi member
        // Registrasi member baru (sudah dilakukan), update info member
        lib.updateMember(m3.getMemberId(), "Ani Lestari", "ani.lestari@mail.com", "083333333", "Jl. Kenanga 33");
        System.out.println("\nMember updated:");
        System.out.println(lib.getMemberById(m3.getMemberId()).getMemberInfo());

        // Cek status member
        System.out.println("\nActive members:");
        for (Member mm : lib.getActiveMembers()) {
            System.out.println("  - " + mm.getMemberInfo());
        }

        // ===== BORROWING SYSTEM =====
        System.out.println("\n=== BORROWING SYSTEM ===");

        // Latihan 4: Sistem peminjaman
        // Pinjam buku
        boolean r1 = lib.borrowBook(m1.getMemberId(), "978-1111"); // Rina borrows Pemrograman Java
        System.out.println("Rina borrow Pemrograman Java: " + (r1 ? "Success" : "Fail"));

        boolean r2 = lib.borrowBook(m2.getMemberId(), "978-1111"); // Pak Joko tries same book
        System.out.println("Pak Joko borrow Pemrograman Java: " + (r2 ? "Success" : "Fail"));

        // Kembalikan buku (fast-forward simulate by using record id)
        BorrowRecord rec = lib.findBorrowRecordByMemberAndISBN(m1.getMemberId(), "978-1111");
        if (rec != null) {
            // simulate returning after 16 days -> overdue if loanPeriod is 14
            rec.setReturnDate(rec.getBorrowDate().plusDays(16));
            double fine = rec.calculateFine(Library.FINE_PER_DAY);
            rec.applyReturn(); // mark returned and set fine internally
            lib.processReturn(rec);
            System.out.println("Return processed. Fine: " + String.format("%.2f", fine));
        }

        // Perpanjang peminjaman
        boolean extendOK = lib.extendLoan(m2.getMemberId(), "978-2222", 7); // extend by 7 days
        System.out.println("Extend loan result: " + extendOK);

        // ===== REPORTING =====
        System.out.println("\n=== REPORTING ===");

        // Latihan 5: Sistem reporting
        System.out.println("\nPopular books report:");
        for (Book b : lib.generatePopularBooksReport(5)) {
            System.out.println("  - " + b.getTitle() + " (borrowCount=" + b.getBorrowCount() + ")");
        }

        System.out.println("\nActive members report:");
        for (Member mm : lib.generateActiveMembersReport()) {
            System.out.println("  - " + mm.getMemberInfo() + " | borrowed=" + mm.getBorrowedCount());
        }

        System.out.println("\nOverdue report:");
        for (BorrowRecord overdue : lib.generateOverdueReport()) {
            System.out.println("  - recordId=" + overdue.getRecordId() + " member=" + overdue.getMemberId() + " isbn=" + overdue.getIsbn() + " due=" + LibraryUtils.formatDate(overdue.getDueDate()));
        }

        // ===== ADMIN FUNCTIONS =====
        System.out.println("\n=== ADMIN FUNCTIONS ===");

        // Latihan 6: Fungsi admin
        lib.backup();
        lib.maintenance();
    }
}

// ===== CLASS DEFINITIONS =====

// Implementasikan class Book dengan encapsulation lengkap
class Book {
    // Private instance variables
    // isbn, title, author, publisher, yearPublished, category, isAvailable, borrowCount
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int yearPublished;
    private BookCategory category;
    private boolean isAvailable;
    private int borrowCount;

    // Static variables
    // static int totalBooks
    private static int totalBooks = 0;

    // Constructors dengan overloading
    // Multiple constructors
    public Book(String isbn, String title, String author, String publisher, int yearPublished, BookCategory category) {
        if (!LibraryUtils.isValidISBN(isbn)) throw new IllegalArgumentException("Invalid ISBN");
        this.isbn = isbn;
        this.title = title != null ? title : "Unknown";
        this.author = author != null ? author : "Unknown";
        this.publisher = publisher != null ? publisher : "Unknown";
        this.yearPublished = yearPublished;
        this.category = category == null ? BookCategory.OTHER : category;
        this.isAvailable = true;
        this.borrowCount = 0;
        totalBooks++;
    }

    public Book(String isbn, String title, String author) {
        this(isbn, title, author, "Unknown", LocalDate.now().getYear(), BookCategory.OTHER);
    }

    // Getter/Setter dengan validation
    public String getIsbn() { return isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { if (title != null && !title.trim().isEmpty()) this.title = title.trim(); }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { if (author != null && !author.trim().isEmpty()) this.author = author.trim(); }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { if (publisher != null && !publisher.trim().isEmpty()) this.publisher = publisher.trim(); }

    public int getYearPublished() { return yearPublished; }
    public void setYearPublished(int yearPublished) { this.yearPublished = yearPublished; }

    public BookCategory getCategory() { return category; }
    public void setCategory(BookCategory category) { this.category = category; }

    public boolean isAvailable() { return isAvailable; }

    public int getBorrowCount() { return borrowCount; }

    public static int getTotalBooks() { return totalBooks; }

    // Business methods
    public boolean borrowBook() {
        if (!isAvailable) return false;
        isAvailable = false;
        borrowCount++;
        return true;
    }

    public boolean returnBook() {
        if (isAvailable) return false;
        isAvailable = true;
        return true;
    }

    public double getPopularityScore() {
        // simple score: borrowCount normalized by book age (avoid divide by zero)
        int age = getAgeInYears();
        return age > 0 ? (double) borrowCount / age : borrowCount;
    }

    // Utility methods
    public void displayBookInfo() {
        System.out.println("ISBN : " + isbn);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Publisher: " + publisher);
        System.out.println("Year: " + yearPublished);
        System.out.println("Category: " + category);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
        System.out.println("Borrow count: " + borrowCount);
    }

    public boolean isClassic() {
        return getAgeInYears() >= 50;
    }

    public int getAgeInYears() {
        return LocalDate.now().getYear() - yearPublished;
    }
}

// Implementasikan class Member
class Member {
    // Private instance variables
    // memberId, name, email, phone, address, joinDate, membershipType, isActive
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate joinDate;
    private MembershipType membershipType;
    private boolean isActive;
    private int borrowedCount;

    // Static variables
    // static int totalMembers, static final int MAX_BOOKS_ALLOWED
    private static int totalMembers = 0;

    // Constructors
    // Constructor dengan validation
    public Member(String name, String email, String phone, String address, MembershipType membershipType) {
        this.memberId = "M" + (++totalMembers);
        this.name = (name == null || name.trim().isEmpty()) ? "Unknown" : name.trim();
        this.email = LibraryUtils.isValidEmail(email) ? email : "";
        this.phone = (phone == null) ? "" : phone;
        this.address = (address == null) ? "" : address;
        this.joinDate = LocalDate.now();
        this.membershipType = (membershipType == null) ? MembershipType.PUBLIC : membershipType;
        this.isActive = true;
        this.borrowedCount = 0;
    }

    // Getter/Setter
    public String getMemberId() { return memberId; }

    public String getName() { return name; }
    public boolean setName(String name) {
        if (name == null || name.trim().isEmpty()) return false;
        this.name = name.trim();
        return true;
    }

    public String getEmail() { return email; }
    public boolean setEmail(String email) {
        if (!LibraryUtils.isValidEmail(email)) return false;
        this.email = email;
        return true;
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDate getJoinDate() { return joinDate; }

    public MembershipType getMembershipType() { return membershipType; }
    public void setMembershipType(MembershipType membershipType) { this.membershipType = membershipType; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }

    public int getBorrowedCount() { return borrowedCount; }
    protected void incrementBorrowed() { this.borrowedCount++; }
    protected void decrementBorrowed() { if (this.borrowedCount > 0) this.borrowedCount--; }

    // Business methods
    public boolean canBorrowMore() {
        return borrowedCount < membershipType.getMaxBooksAllowed() && isActive;
    }

    public int calculateMembershipDuration() {
        return LocalDate.now().getYear() - joinDate.getYear();
    }

    public void upgradeMembership(MembershipType newType) {
        if (newType != null && newType.ordinal() > membershipType.ordinal()) {
            this.membershipType = newType;
        }
    }

    public String getMemberInfo() {
        return memberId + " | " + name + " | " + email + " | " + membershipType + " | Active: " + isActive;
    }

    public static int getTotalMembers() { return totalMembers; }
}

// Implementasikan class BorrowRecord
class BorrowRecord {
    // Private variables
    // recordId, memberId, isbn, borrowDate, dueDate, returnDate, fine
    private static int totalRecords = 0;
    private String recordId;
    private String memberId;
    private String isbn;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fine;
    private boolean returned;

    // Constructor
    // Constructor dengan auto-calculation due date
    public BorrowRecord(String memberId, String isbn, int loanDays) {
        this.recordId = "R" + (++totalRecords);
        this.memberId = memberId;
        this.isbn = isbn;
        this.borrowDate = LocalDate.now();
        this.dueDate = this.borrowDate.plusDays(Math.max(1, loanDays));
        this.returnDate = null;
        this.fine = 0.0;
        this.returned = false;
    }

    // Methods
    public String getRecordId() { return recordId; }
    public String getMemberId() { return memberId; }
    public String getIsbn() { return isbn; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public double getFine() { return fine; }
    public boolean isReturned() { return returned; }

    public long daysOverdue(LocalDate onDate) {
        long diff = onDate.toEpochDay() - dueDate.toEpochDay();
        return diff > 0 ? diff : 0;
    }

    public double calculateFine(double finePerDay) {
        if (returnDate == null) return 0.0;
        long overdue = returnDate.toEpochDay() - dueDate.toEpochDay();
        if (overdue <= 0) return 0.0;
        this.fine = overdue * finePerDay;
        return this.fine;
    }

    public boolean isOverdue() {
        if (returned) return false;
        return LocalDate.now().toEpochDay() > dueDate.toEpochDay();
    }

    // mark returned with returnDate set previously (caller may set returnDate to simulate)
    public void applyReturn() {
        if (this.returnDate == null) this.returnDate = LocalDate.now();
        calculateFine(Library.FINE_PER_DAY);
        this.returned = true;
    }

    // allow external set of returnDate (for simulation/testing)
    public void setReturnDate(LocalDate date) {
        this.returnDate = date;
    }

    // extend loan
    public boolean extendLoan(int extraDays) {
        if (returned) return false;
        this.dueDate = this.dueDate.plusDays(Math.max(1, extraDays));
        return true;
    }
}

// Implementasikan class Library
class Library {
    // Private variables untuk collections
    // ArrayList<Book> books, ArrayList<Member> members, ArrayList<BorrowRecord> borrowRecords
    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private ArrayList<BorrowRecord> borrowRecords;

    // Static variables
    // static String libraryName, static final double FINE_PER_DAY
    public static String libraryName;
    public static final double FINE_PER_DAY = 1.0; // currency units per day

    // Private variables untuk business logic
    // maxBooksPerMember, loanPeriodDays
    private int maxBooksPerMember;
    private int loanPeriodDays;

    // Constructor
    // Initialize collections dan set library parameters
    public Library(String name, int loanPeriodDays, int maxBooksPerMember) {
        Library.libraryName = name;
        this.books = new ArrayList<Book>();
        this.members = new ArrayList<Member>();
        this.borrowRecords = new ArrayList<BorrowRecord>();
        this.loanPeriodDays = Math.max(1, loanPeriodDays);
        this.maxBooksPerMember = Math.max(1, maxBooksPerMember);
    }

    // Book management methods
    public boolean addBook(Book b) {
        if (b == null) return false;
        if (getBookByISBN(b.getIsbn()) != null) return false;
        books.add(b);
        return true;
    }

    public boolean removeBook(String isbn) {
        Book b = getBookByISBN(isbn);
        if (b == null) return false;
        return books.remove(b);
    }

    public ArrayList<Book> searchBooksByTitle(String titlePart) {
        ArrayList<Book> res = new ArrayList<Book>();
        if (titlePart == null) return res;
        String key = titlePart.toLowerCase();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(key)) res.add(b);
        }
        return res;
    }

    public ArrayList<Book> searchBooksByAuthor(String authorPart) {
        ArrayList<Book> res = new ArrayList<Book>();
        if (authorPart == null) return res;
        String key = authorPart.toLowerCase();
        for (Book b : books) {
            if (b.getAuthor().toLowerCase().contains(key)) res.add(b);
        }
        return res;
    }

    public Book getBookByISBN(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) return b;
        }
        return null;
    }

    public ArrayList<Book> getAvailableBooks() {
        ArrayList<Book> res = new ArrayList<Book>();
        for (Book b : books) if (b.isAvailable()) res.add(b);
        return res;
    }

    // Member management methods
    public Member registerMember(String name, String email, String phone, String address, MembershipType type) {
        Member m = new Member(name, email, phone, address, type);
        members.add(m);
        return m;
    }

    public boolean updateMember(String memberId, String name, String email, String phone, String address) {
        Member m = getMemberById(memberId);
        if (m == null) return false;
        if (name != null && !name.trim().isEmpty()) m.setName(name);
        if (email != null && LibraryUtils.isValidEmail(email)) m.setEmail(email);
        if (phone != null) m.setPhone(phone);
        if (address != null) m.setAddress(address);
        return true;
    }

    public Member getMemberById(String memberId) {
        for (Member m : members) {
            if (m.getMemberId().equals(memberId)) return m;
        }
        return null;
    }

    public ArrayList<Member> getActiveMembers() {
        ArrayList<Member> res = new ArrayList<Member>();
        for (Member m : members) if (m.isActive()) res.add(m);
        return res;
    }

    // Borrowing methods
    public boolean borrowBook(String memberId, String isbn) {
        Member m = getMemberById(memberId);
        Book b = getBookByISBN(isbn);
        if (m == null || b == null) return false;
        if (!m.canBorrowMore()) return false;
        if (!b.isAvailable()) return false;
        // perform borrow
        boolean ok = b.borrowBook();
        if (!ok) return false;
        BorrowRecord rec = new BorrowRecord(memberId, isbn, loanPeriodDays);
        borrowRecords.add(rec);
        m.incrementBorrowed();
        return true;
    }

    public boolean returnBook(String memberId, String isbn) {
        BorrowRecord rec = findActiveBorrowRecord(memberId, isbn);
        if (rec == null) return false;
        rec.setReturnDate(LocalDate.now());
        rec.applyReturn();
        processReturn(rec);
        return true;
    }

    // helper to be called after rec returned to update member/book
    public void processReturn(BorrowRecord rec) {
        if (rec == null) return;
        Book b = getBookByISBN(rec.getIsbn());
        Member m = getMemberById(rec.getMemberId());
        if (b != null) b.returnBook();
        if (m != null) m.decrementBorrowed();
        // fine is already calculated in rec.applyReturn()
    }

    public BorrowRecord findActiveBorrowRecord(String memberId, String isbn) {
        for (BorrowRecord r : borrowRecords) {
            if (!r.isReturned() && r.getMemberId().equals(memberId) && r.getIsbn().equals(isbn)) return r;
        }
        return null;
    }

    public BorrowRecord findBorrowRecordByMemberAndISBN(String memberId, String isbn) {
        for (BorrowRecord r : borrowRecords) {
            if (r.getMemberId().equals(memberId) && r.getIsbn().equals(isbn)) return r;
        }
        return null;
    }

    public boolean extendLoan(String memberId, String isbn, int extraDays) {
        BorrowRecord rec = findActiveBorrowRecord(memberId, isbn);
        if (rec == null) return false;
        return rec.extendLoan(extraDays);
    }

    public double calculateFine(String memberId) {
        double total = 0.0;
        for (BorrowRecord r : borrowRecords) {
            if (r.getMemberId().equals(memberId) && !r.isReturned()) {
                long overdue = r.daysOverdue(LocalDate.now());
                total += overdue * FINE_PER_DAY;
            }
        }
        return total;
    }

    // Reporting methods
    public ArrayList<Book> generatePopularBooksReport(int topN) {
        ArrayList<Book> copy = new ArrayList<Book>(books);
        // simple selection sort-like by borrowCount descending
        for (int i = 0; i < copy.size(); i++) {
            int best = i;
            for (int j = i + 1; j < copy.size(); j++) {
                if (copy.get(j).getBorrowCount() > copy.get(best).getBorrowCount()) best = j;
            }
            Book tmp = copy.get(i);
            copy.set(i, copy.get(best));
            copy.set(best, tmp);
        }
        ArrayList<Book> res = new ArrayList<Book>();
        for (int i = 0; i < topN && i < copy.size(); i++) res.add(copy.get(i));
        return res;
    }

    public ArrayList<Member> generateActiveMembersReport() {
        return getActiveMembers();
    }

    public ArrayList<BorrowRecord> generateOverdueReport() {
        ArrayList<BorrowRecord> res = new ArrayList<BorrowRecord>();
        for (BorrowRecord r : borrowRecords) {
            if (!r.isReturned() && r.isOverdue()) res.add(r);
        }
        return res;
    }

    // Utility methods
    public void displayLibraryStats() {
        System.out.println("Library: " + libraryName);
        System.out.println("Total books: " + books.size());
        System.out.println("Available books: " + getAvailableBooks().size());
        System.out.println("Total members: " + members.size());
        System.out.println("Active borrow records: " + activeBorrowCount());
    }

    public int activeBorrowCount() {
        int c = 0;
        for (BorrowRecord r : borrowRecords) if (!r.isReturned()) c++;
        return c;
    }

    public void backup() {
        System.out.println("Backup: saving data snapshot... (simulated)");
    }

    public void maintenance() {
        System.out.println("Maintenance: running maintenance tasks... (simulated)");
    }

    // Private helper methods
    // private boolean isValidMember(), private boolean isBookAvailable()
    private boolean isValidMember(String memberId) {
        return getMemberById(memberId) != null;
    }

    private boolean isBookAvailable(String isbn) {
        Book b = getBookByISBN(isbn);
        return b != null && b.isAvailable();
    }
}

// Implementasikan class LibraryUtils (static utility class)
class LibraryUtils {
    // Constants
    // static final variables untuk berbagai konstanta
    public static final int DEFAULT_LOAN_DAYS = 14;
    public static final double DEFAULT_FINE_PER_DAY = 1.0;

    // Static utility methods
    // static boolean isValidISBN(), static boolean isValidEmail()
    // static String formatDate(), static double calculateLateFee()
    public static boolean isValidISBN(String isbn) {
        if (isbn == null) return false;
        String s = isbn.replaceAll("-", "").trim();
        return s.length() >= 5; // simple check: length at least 5
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String t = email.trim();
        int at = t.indexOf('@');
        if (at <= 0) return false;
        int dot = t.indexOf('.', at);
        return dot > at + 1;
    }

    public static String formatDate(LocalDate date) {
        if (date == null) return "";
        return date.toString();
    }

    public static double calculateLateFee(long overdueDays, double finePerDay) {
        if (overdueDays <= 0) return 0.0;
        return overdueDays * finePerDay;
    }

    // Private constructor
    private LibraryUtils() { }
}

// Implementasikan enum untuk BookCategory
enum BookCategory {
    FICTION, NON_FICTION, SCIENCE, HISTORY, BIOGRAPHY, OTHER
}

// Implementasikan enum untuk MembershipType
enum MembershipType {
    STUDENT(3, 14),
    FACULTY(10, 28),
    PUBLIC(5, 14);

    private int maxBooksAllowed;
    private int loanDays;

    MembershipType(int maxBooksAllowed, int loanDays) {
        this.maxBooksAllowed = maxBooksAllowed;
        this.loanDays = loanDays;
    }

    public int getMaxBooksAllowed() { return maxBooksAllowed; }
    public int getLoanDays() { return loanDays; }
}
