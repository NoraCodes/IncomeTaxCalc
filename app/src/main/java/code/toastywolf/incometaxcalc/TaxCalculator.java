package code.toastywolf.incometaxcalc;

public class TaxCalculator {

    private static Bracket[] SINGLE_RATES = {
            new Bracket(0, 9525, 0.10),
            new Bracket(9525, 38700, 0.12),
            new Bracket(38700, 82500, 0.22),
            new Bracket(82500, 157500, 0.24),
            new Bracket(157500, 200000, 0.32),
            new Bracket(200000, 500000, 0.35),
            new Bracket(500000, Double.POSITIVE_INFINITY, 0.37)
    };

    private static Bracket[] SEPARATE_RATES = {
            new Bracket(0, 9525, 0.10),
            new Bracket(9525, 38700, 0.12),
            new Bracket(38700, 82500, 0.22),
            new Bracket(82500, 157500, 0.24),
            new Bracket(157500, 200000, 0.32),
            new Bracket(200000, 300000, 0.35),
            new Bracket(300000, Double.POSITIVE_INFINITY, 0.37)
    };

    private static Bracket[] JOINT_RATES = {
            new Bracket(0, 19050, 0.10),
            new Bracket(19050, 77400, 0.12),
            new Bracket(77400, 165000, 0.22),
            new Bracket(165000, 315000, 0.24),
            new Bracket(315000, 400000, 0.32),
            new Bracket(400000, 600000, 0.35),
            new Bracket(600000, Double.POSITIVE_INFINITY, 0.37)
    };

    private static Bracket[] HEAD_OF_HOUSEHOLD_RATES = {
            new Bracket(0, 13600, 0.10),
            new Bracket(13600, 51800, 0.12),
            new Bracket(51800, 82500, 0.22),
            new Bracket(82500, 157500, 0.24),
            new Bracket(157500, 200000, 0.32),
            new Bracket(200000, 500000, 0.35),
            new Bracket(500000, Double.POSITIVE_INFINITY, 0.37)
    };

    public static double rate(TaxInformation ti) {
        if (ti.getFiling_as() == TaxInformation.FILING_SINGLE) {
            return maxRateForRates(ti.getIncome(), SINGLE_RATES);
        }

        if (ti.getFiling_as() == TaxInformation.FILING_JOINTLY) {
            return maxRateForRates(ti.getIncome(), JOINT_RATES);
        }

        if (ti.getFiling_as() == TaxInformation.FILING_HEAD_OF_HOUSEHOLD) {
            return maxRateForRates(ti.getIncome(), HEAD_OF_HOUSEHOLD_RATES);
        }

        if (ti.getFiling_as() == TaxInformation.FILING_SEPERATELY) {
            return maxRateForRates(ti.getIncome(), SEPARATE_RATES);
        }

        return Float.NaN;
    }

    public static double amount(TaxInformation ti) {
        if (ti.getFiling_as() == TaxInformation.FILING_SINGLE) {
            return taxForRates(ti.getIncome(), SINGLE_RATES);
        }

        if (ti.getFiling_as() == TaxInformation.FILING_JOINTLY) {
            return taxForRates(ti.getIncome(), JOINT_RATES);
        }

        if (ti.getFiling_as() == TaxInformation.FILING_HEAD_OF_HOUSEHOLD) {
            return taxForRates(ti.getIncome(), HEAD_OF_HOUSEHOLD_RATES);
        }

        if (ti.getFiling_as() == TaxInformation.FILING_SEPERATELY) {
            return taxForRates(ti.getIncome(), SEPARATE_RATES);
        }

        return Float.NaN;
    }

    private static double maxRateForRates(double income, Bracket[] rates) {
        for (int i = 0; i < rates.length; i++) {
            if (income <= rates[i].maximum && income > rates[i].minimum) {
                return rates[i].rate;
            }
        }
        return Double.NaN;
    }

    private static double taxForRates(double income, Bracket[] rates) {
        double tax = 0.0;
        double income_processed = 0.0;
        for (int i = 0; i < rates.length; i++) {
            // If our income is over this, short-circuit all the addition and stuff and just
            // add in the maximum.
            if (income >= rates[i].maximum) {
                tax += (rates[i].maximum - income_processed) * rates[i].rate;
                income_processed += (rates[i].maximum - income_processed);
            } else if (income >= rates[i].minimum) {
                tax += (income - income_processed) * rates[i].rate;
                income_processed += (income - income_processed);
                assert(income - income_processed == 0);
            }

            if (income - income_processed == 0) {
                break;
            }
        }

        return tax;
    }
}

class Bracket {
    public double minimum;
    public double maximum;
    public double rate;

    public Bracket(double minimum, double maximum, double rate) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.rate = rate;
    }
}
