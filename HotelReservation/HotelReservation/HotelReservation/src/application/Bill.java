package application;

public class Bill {
    private int billID;
    private double amountToPay;

    public Bill(int billID, double amountToPay) {
        this.billID = billID;
        this.amountToPay = amountToPay;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }
}
