package Splitwise;

public class User {
    private String id;
    private String name;

    private BalanceSheet balanceSheet;

    public User(String id,String name){
        this.id=id;
        this.name=name;

        this.balanceSheet=new BalanceSheet();
    }

    public BalanceSheet getBalanceSheet(){
        return this.balanceSheet;
    }
}
