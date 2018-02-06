package edu.uoregon.teamwon;

/**
 * @author Yehui Zhang
 * @date Jan 29
 */
public class CheckPoint {

    private Person prev;
    private Person curr;
    private CPStatus status;
    private int index;



    /**
     * Constructor for Add and Delete operations
     * @param person
     * @param status

     */
    public CheckPoint(Person person, CPStatus status){
        this.status = status;
        if (CPStatus.Delete.equals(status))
            this.prev = person;
        else if(CPStatus.Add.equals(status))
            this.prev = person;
        else
            System.err.println("Error Found in Check Point!");
    }

    /**
     * Concstructor for Edition operation
     * @param prev
     * @param curr
     * @param status
     */
    public CheckPoint(Person prev, Person curr, CPStatus status) {
        this.status = status;
        if(CPStatus.Edit.equals(status)){
            this.prev = prev;
            this.curr = curr;
            this.index = index;
        }
        else{
            System.err.println("Error Found in Check Point!");
        }
    }

    public Person getPrev() {
        return prev;
    }

    public Person getCurr() {
        return curr;
    }

    public CPStatus getStatus() {
        return status;
    }

    public int getIndex() {
        return index;
    }
}

enum CPStatus{
    Add, Edit, Delete
}