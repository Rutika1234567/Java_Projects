import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bank_Management_System {

    private List<Account> accounts = new ArrayList<>();


    //   ACCOUNT CLASS
    
    
    public static class Account {

        private static int nextAccountNumber = 1001;

        private final int accountNumber;
        private String holderName;
        private double balance;

        private List<Transaction> transactions = new ArrayList<>();

        public Account(String holderName, double initialDeposit) {
            this.accountNumber = nextAccountNumber++;
            this.holderName = holderName;
            this.balance = Math.max(0, initialDeposit);

            if (initialDeposit > 0) {
                transactions.add(new Transaction("Deposit", initialDeposit, "Initial deposit"));
            }
        }

        // getters
        public int getAccountNumber() { return accountNumber; }
        public String getHolderName() { return holderName; }
        public double getBalance() { return balance; }

        // deposit
        public boolean deposit(double amount) {
            if (amount <= 0) {
                System.out.println("Deposit amount must be positive.");
                return false;
            }

            balance += amount;
            transactions.add(new Transaction("Deposit", amount, "Deposit"));

            System.out.println("Deposited " + amount + ". New balance: " + balance);
            return true;
        }

        // withdraw
        public boolean withdraw(double amount) {
            if (amount <= 0) {
                System.out.println("Withdraw amount must be positive.");
                return false;
            }
            if (amount > balance) {
                System.out.println("Insufficient funds.");
                return false;
            }

            balance -= amount;
            transactions.add(new Transaction("Withdraw", amount, "Withdrawal"));

            System.out.println("Withdrew " + amount + ". New balance: " + balance);
            return true;
        }

        public void showTransactionHistory() {
            System.out.println("\n--- Transaction History for Account " + accountNumber + " ---");
            if (transactions.isEmpty()) {
                System.out.println("No transactions yet.");
                return;
            }
            for (Transaction t : transactions) {
                System.out.println(t.getType() + " : " + t.getAmount() +
                                   " (" + t.getDescription() + ")");
            }
        }

        protected void addTransaction(Transaction t) {
            transactions.add(t);
        }

        public void showDetails() {
            System.out.println("\n----- Account Details -----");
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Holder Name: " + holderName);
            System.out.println("Balance: " + balance);
        }
    }

   
    //      SAVINGS ACCOUNT
    // ===========================
    public static class SavingsAccount extends Account {

        private double interestRate;
        private final double MINIMUM_BALANCE = 100.0;

        public SavingsAccount(String name, double initialDeposit, double interestRate) {
            super(name, initialDeposit);
            this.interestRate = interestRate;
        }

        @Override
        public boolean withdraw(double amount) {
            if (amount <= 0) {
                System.out.println("Withdraw amount must be positive.");
                return false;
            }

            double allowedMax = getBalance() - MINIMUM_BALANCE;

            if (amount > allowedMax) {
                System.out.println("Cannot withdraw. Savings account must keep at least " + MINIMUM_BALANCE);
                return false;
            }

            boolean ok = super.withdraw(amount);

            if (ok) {
                addTransaction(new Transaction("Withdraw (Savings)", amount, "Savings withdrawal"));
            }

            return ok;
        }

        public void applyInterest() {
            double interest = getBalance() * interestRate;
            if (interest > 0) {
                deposit(interest);
                addTransaction(new Transaction("Interest", interest,
                                               "Interest applied at rate " + interestRate));
                System.out.println("Interest of " + interest + " applied.");
            }
        }
    }

   
    //      CURRENT ACCOUNT
   

    public static class CurrentAccount extends Account {

        private double overdraftLimit;

        public CurrentAccount(String name, double initialDeposit, double overdraftLimit) {
            super(name, initialDeposit);
            this.overdraftLimit = overdraftLimit;
        }

        @Override
        public boolean withdraw(double amount) {
            if (amount <= 0) {
                System.out.println("Withdraw amount must be positive.");
                return false;
            }

            if (amount > getBalance() + overdraftLimit) {
                System.out.println("Withdraw exceeds overdraft limit.");
                return false;
            }

            // Manually adjust balance because super.withdraw blocks overdraft
            double newBalance = getBalance() - amount;
            try {
                java.lang.reflect.Field f = Account.class.getDeclaredField("balance");
                f.setAccessible(true);
                f.set(this, newBalance);
            } catch (Exception e) { e.printStackTrace(); }

            addTransaction(new Transaction("Withdraw (Current)", amount, "Current account withdrawal"));

            System.out.println("Withdrew " + amount + ". New balance: " + newBalance);
            return true;
        }
    }

   

    //        TRANSACTION
   
    public static class Transaction {
        private String type;
        private double amount;
        private String description;

        public Transaction(String type, double amount, String description) {
            this.type = type;
            this.amount = amount;
            this.description = description;
        }

        public String getType() { return type; }
        public double getAmount() { return amount; }
        public String getDescription() { return description; }
    }

 
    //           MAIN
    
    public static void main(String[] args) {
        new Bank_Management_System().run();
    }

    public void run() {
        Scanner scan = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the Bank Management System!");

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Create Savings Account");
            System.out.println("2. Create Current Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Check Balance");
            System.out.println("6. Show Transaction History");
            System.out.println("7. Apply Interest");
            System.out.println("0. Exit");
            System.out.print("Enter a choice: ");

            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 1:
                     createSavings(scan);
                     break;
                case 2:
                     createCurrent(scan);
                      break;
                case 3 : 
                      deposit(scan);
                       break;
                case 4 : 
                      withdraw(scan);
                       break;
                case 5 : 
                      checkBalance(scan);
                       break;
                case 6 : 
                       showHistory(scan);
                        break;
                case 7 : 
                        applyInterest(scan);
                         break;
                case 0 :
                         running = false;
                          break;
                default : System.out.println("Invalid option.");
            }
        }

        System.out.println("Thank you for using the system!");
    }

   
    //      MENU FUNCTIONS
  
    private void createSavings(Scanner scan) {
        System.out.print("Holder name: ");
        String name = scan.nextLine();

        System.out.print("Initial deposit: ");
        double init = scan.nextDouble();

        System.out.print("Interest rate: ");
        double rate = scan.nextDouble();

        SavingsAccount sa = new SavingsAccount(name, init, rate);
        accounts.add(sa);

        System.out.println("Savings account created. Account number: " + sa.getAccountNumber());
    }

    private void createCurrent(Scanner scan) {
        System.out.print("Holder name: ");
        String name = scan.nextLine();

        System.out.print("Initial deposit: ");
        double init = scan.nextDouble();

        System.out.print("Overdraft limit: ");
        double od = scan.nextDouble();

        CurrentAccount ca = new CurrentAccount(name, init, od);
        accounts.add(ca);

        System.out.println("Current account created. Account number: " + ca.getAccountNumber());
    }

    private void deposit(Scanner scan) {
        System.out.print("Account number: ");
        int num = scan.nextInt();

        Account a = findAccount(num);
        if (a == null) return;

        System.out.print("Amount: ");
        double amt = scan.nextDouble();

        a.deposit(amt);
    }

    private void withdraw(Scanner scan) {
        System.out.print("Account number: ");
        int num = scan.nextInt();

        Account a = findAccount(num);
        if (a == null) return;

        System.out.print("Amount: ");
        double amt = scan.nextDouble();

        a.withdraw(amt);
    }

    private void checkBalance(Scanner scan) {
        System.out.print("Account number: ");
        int num = scan.nextInt();

        Account a = findAccount(num);
        if (a != null) a.showDetails();
    }

    private void showHistory(Scanner scan) {
        System.out.print("Account number: ");
        int num = scan.nextInt();

        Account a = findAccount(num);
        if (a != null) a.showTransactionHistory();
    }

    private void applyInterest(Scanner scan) {
        System.out.print("Account number: ");
        int num = scan.nextInt();

        Account a = findAccount(num);

        if (a instanceof SavingsAccount sa) {
            sa.applyInterest();
        } else {
            System.out.println("This is not a savings account.");
        }
    }

    private Account findAccount(int num) {
        for (Account a : accounts) {
            if (a.getAccountNumber() == num) return a;
        }
        System.out.println("Account not found.");
        return null;
    }
}
