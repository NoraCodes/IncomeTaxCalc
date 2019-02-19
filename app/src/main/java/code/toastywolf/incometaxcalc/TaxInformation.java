package code.toastywolf.incometaxcalc;

public class TaxInformation {
    public static int FILING_SINGLE = 0;
    public static int FILING_JOINTLY = 1;
    public static int FILING_HEAD_OF_HOUSEHOLD = 2;
    public static int FILING_SEPERATELY = 3;

    private double income;
    private int filing_as;

    private double rate;
    private double amount;

    public TaxInformation(double income, int filing_as) {
        this.setIncome(income);
        this.setFiling_as(filing_as);
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
        this.update();
    }

    public int getFiling_as() {
        return filing_as;
    }

    public void setFiling_as(int filing_as) {
        this.filing_as = filing_as;
        this.update();
    }

    public double getRate() {
        return rate;
    }

    public double getAmount() {
        return amount;
    }

    private void update() {
        this.rate = TaxCalculator.rate(this);
        this.amount = TaxCalculator.amount(this);
    }
}
