package code.toastywolf.incometaxcalc;

public class TaxCalculator {
    public static double rate(TaxInformation ti) {
        if (ti.getFiling_as() == TaxInformation.FILING_SINGLE) {
            return getSingleRate(ti.getIncome());
        }

        if (ti.getFiling_as() == TaxInformation.FILING_JOINTLY) {
            return getJointRate(ti.getIncome());
        }

        if (ti.getFiling_as() == TaxInformation.FILING_HEAD_OF_HOUSEHOLD) {
            return getHeadOfHouseholdRate(ti.getIncome());
        }

        if (ti.getFiling_as() == TaxInformation.FILING_SEPERATELY) {
            return getSeperateRate(ti.getIncome());
        }

        return Float.NaN;
    }

    public static double amount(TaxInformation ti) {
        double rate = rate(ti);
        return ti.getIncome() * rate;
    }

    private static double getSingleRate(double income) {
        if (income <= 9525) {
            return 0.10;
        } else if (income <= 38700) {
            return 0.12;
        } else if (income <= 82500) {
            return 0.22;
        } else if (income <= 157500) {
            return 0.24;
        } else if (income <= 200000) {
            return 0.32;
        } else if (income <= 500000) {
            return 0.35;
        } else {
            return 0.37;
        }
    }

    private static double getSeperateRate(double income) {
        if (income <= 9525) {
            return 0.10;
        } else if (income <= 38700) {
            return 0.12;
        } else if (income <= 82500) {
            return 0.22;
        } else if (income <= 157500) {
            return 0.24;
        } else if (income <= 200000) {
            return 0.32;
        } else if (income <= 300000) {
            return 0.35;
        } else {
            return 0.37;
        }
    }

    private static double getJointRate(double income) {
        if (income <= 19050) {
            return 0.10;
        } else if (income <= 77400) {
            return 0.12;
        } else if (income <= 165000) {
            return 0.22;
        } else if (income <= 315000) {
            return 0.24;
        } else if (income <= 400000) {
            return 0.32;
        } else if (income <= 600000) {
            return 0.35;
        } else {
            return 0.37;
        }
    }

    private static double getHeadOfHouseholdRate(double income) {
        if (income <= 13600) {
            return 0.10;
        } else if (income <= 51800) {
            return 0.12;
        } else if (income <= 82500) {
            return 0.22;
        } else if (income <= 157500) {
            return 0.24;
        } else if (income <= 200000) {
            return 0.32;
        } else if (income <= 500000) {
            return 0.35;
        } else {
            return 0.37;
        }
    }
}
