package code.toastywolf.incometaxcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RadioGroup filingSelector;
    private EditText incomeEditText;
    private TextView rateView;
    private TextView amountView;
    private TaxInformation model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.filingSelector = findViewById(R.id.filingSelectorRadioGroup);
        this.incomeEditText = findViewById(R.id.incomeEditText);
        this.rateView = findViewById(R.id.rateView);
        this.amountView = findViewById(R.id.amountView);

        if (savedInstanceState != null) {
            // Get the saved state
            int filing_as = savedInstanceState.getInt("filing_as");
            double income = savedInstanceState.getFloat("income");

            // Restore it to the UI
            this.incomeEditText.setText(String.format("%.2f", income));
            if (filing_as == TaxInformation.FILING_HEAD_OF_HOUSEHOLD ) {
                this.filingSelector.check(R.id.filingHeadOfHousehold);
            } else if (filing_as == TaxInformation.FILING_JOINTLY) {
                this.filingSelector.check(R.id.filingMarriedJointly);
            } else if (filing_as == TaxInformation.FILING_SEPERATELY) {
                this.filingSelector.check(R.id.filingMarriedSeperately);
            } else {
                this.filingSelector.check(R.id.filingSingle);
            }

            // Update the model
            this.model = new TaxInformation(income, filing_as);
        } else {
            this.model = new TaxInformation(11000, TaxInformation.FILING_SINGLE);
            this.setModelFilingMode();
            this.setModelIncome();
        }

        this.calculate(null);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("income", (float) model.getIncome());
        outState.putInt("filing_as", model.getFiling_as());
    }

    private boolean amountParsesAsFloat() {
        try {
            Double.parseDouble(incomeEditText.getText().toString());
            return true;
        } catch (NumberFormatException _) {
            return false;
        }
    }

    private void setModelFilingMode() {
        int filingModeId = filingSelector.getCheckedRadioButtonId();
        if (filingModeId == R.id.filingHeadOfHousehold) {
            model.setFiling_as(TaxInformation.FILING_HEAD_OF_HOUSEHOLD);
        } else if (filingModeId == R.id.filingMarriedJointly) {
            model.setFiling_as(TaxInformation.FILING_JOINTLY);
        } else if (filingModeId == R.id.filingMarriedSeperately) {
            model.setFiling_as(TaxInformation.FILING_SEPERATELY);
        } else {
            // This case catches either a user selecting single or any bug where there is
            // no selection, so as a belt-and-suspenders approach, set the filing selector to check
            // the default.
            model.setFiling_as(TaxInformation.FILING_SINGLE);
            filingSelector.check(R.id.filingSingle);
        }
    }

    private void setModelIncome() {
        if (this.amountParsesAsFloat()) {
            model.setIncome(Double.parseDouble(incomeEditText.getText().toString()));
        } else {
            // This case catches the user entering a non-number value, so set it to a default
            // valid value.
            model.setIncome(11000);
            this.incomeEditText.setText("11000");
        }
    }

    public void calculate(View _) {
        this.setModelFilingMode();
        this.setModelIncome();
        this.rateView.setText(String.format(Locale.ENGLISH, "%.0f%%", model.getRate()*100));
        this.amountView.setText(String.format(Locale.ENGLISH, "$%.2f", model.getAmount()));
    }
}
