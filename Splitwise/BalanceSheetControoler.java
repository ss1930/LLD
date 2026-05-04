package Splitwise;

public class BalanceSheetControoler {
    //SingleTon

    private BalanceSheetControoler(){}

    //Thread Safe
    private static volatile BalanceSheetControoler instance;
    
    public static BalanceSheetControoler getInstance(){
        if(instance==null){
            synchronized(BalanceSheetControoler.class){
                if(instance==null){
                    instance=new BalanceSheetControoler();
                }
            }
        }
        return instance;
    }

    public void updateBalances(Expense e){
        //update balances
    }
}
