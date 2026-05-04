package Splitwise;

import java.util.HashMap;

public class ExpenseController {
    //SingleTon

    private ExpenseController(){}

    //Thread Safe
    private static volatile ExpenseController instance;
    
    public static ExpenseController getInstance(){
        if(instance==null){
            synchronized(ExpenseController.class){
                if(instance==null){
                    instance=new ExpenseController();
                }
            }
        }
        return instance;
    }
    HashMap<String,Expense> expenses=new HashMap<>();///all expenses
    public void  addExpense(Expense e){

        //validate expense
        SplitValidator splitValidator=SplitValidatorFactory.getSplitValidator(e.getType());

        if(!splitValidator.validateSplit(e.getSplits(), e.getAmount())){
            System.out.println(e.getId()+" Expense splits are not valid. Please check");

            //raise error
            return ;
        }


        expenses.put(e.getId(),e);

        //update balanceSheets 
        BalanceSheetControoler bsc=BalanceSheetControoler.getInstance();
        bsc.updateBalances(e);
    }
}
