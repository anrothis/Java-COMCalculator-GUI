package Claculator2;
import java.util.ArrayList;

public class Calculator {
    
    private ArrayList<String> memory;
    private ArrayList<String> history;
    
    public Calculator() {
        this.memory = new ArrayList<>();
        this.history = new ArrayList<>();
    }
    
    
    public void addOperation(String operation) {
        this.memory.add(operation);
        this.history.add(operation);
    }
    public void clearMemory() {
        memory.clear();
        history.clear();
    }

    public double execInception(int pos) {
        double temp;
        String op;
        double temp2; 
        String lookAhead = "";
        
        while (!"=".equals(this.memory.get(pos+1))) 
        {
            temp = Double.parseDouble(this.memory.get(pos));
            op = this.memory.get(pos+1);
            temp2 = Double.parseDouble(this.memory.get(pos+2));
            if (memory.size() > pos+3) 
            {
                lookAhead = this.memory.get(pos+3);
            }
            if(op != "*" && op != "/")
            {
                if (lookAhead == "*" || lookAhead == "/")
                { 
                    
                    double a = Double.parseDouble(this.memory.get(pos+2));
                    double b = Double.parseDouble(this.memory.get(pos+4));
                    double mid = 0;
                    if (lookAhead == "*") {
                        mid = a * b;
                    }
                    else if(lookAhead == "/")
                    {
                        mid = a / b;
                    }
                    this.memory.set(pos+2, Double.toString(mid));
                    this.memory.remove(pos+4);
                    this.memory.remove(pos+3);
                }
                else
                {
                    if (op == "+") {
                        this.memory.set(pos, Double.toString(temp + temp2));
                    }
                    else if (op == "-")
                    {
                        this.memory.set(pos, Double.toString(temp - temp2));
                    }

                    this.memory.remove(pos+2);
                    this.memory.remove(pos+1);
                }
            }

            if (op == "*") {
                this.memory.set(pos, Double.toString(temp * temp2));
                this.memory.remove(pos+2);
                this.memory.remove(pos+1);
            }
            else if (op == "/")
            {
                this.memory.set(pos, Double.toString(temp / temp2));
                this.memory.remove(pos+2);
                this.memory.remove(pos+1);
            }
        }
        return Double.parseDouble(this.memory.get(pos));
    }

    public ArrayList<String> getOperationList(){
        return memory;
    }
    public ArrayList<String> getOperationHist(){
        return history;
    }
    public String getOperationListAsString(){
        return memory.toString();
    }
    
    public String getLastOperation()
    {
        if (memory.size()>0)
        {
            return memory.get(memory.size()-1);
        }
        else return "";
    }
}
