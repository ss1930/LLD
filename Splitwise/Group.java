package Splitwise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Group {
    private String id;
    private String name;
    private List<User> members;

    private HashMap<String,Expense> expenses=new HashMap<>(); 

    public Group(String id){
        this.id=id;
        members = new ArrayList<>();
        expenses = new HashMap<>();
    }

    public void setName(String name){
        this.name=name;
    }

    public void addMember(User u){
        members.add(u);
    }

    public void addExpense(Expense e){
        ExpenseController ec=ExpenseController.getInstance();
        ec.addExpense(e);
        expenses.put(e.getId(),e);
    }

    
}
