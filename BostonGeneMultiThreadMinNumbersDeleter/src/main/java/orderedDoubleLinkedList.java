public class orderedDoubleLinkedList {
    //variables for insert method
    private Link curr;
    private Link prev;

    private Link first;
    private Link last;
    private int numElements;
    //--------------------------------------------------------------------------------------------
    orderedDoubleLinkedList(){
        this.first = null;
        this.last = null;
        numElements=0;
    }
    //--------------------------------------------------------------------------------------------
    public Link insert(int value , String strValue) {
        prev = null;
        numElements++;
        if (first == null) {
            first = new Link(value,strValue);
            last = first;
            return first;  //return added Link
        }

        curr = first;
        while (curr != null && value >= curr.getValue()) {   //we need a start from first element value checking
            prev=curr; //be carefull, were bug: when i used global prev value it saves value from previous insert and prev always = first Link
            curr = curr.next;
        }
        if(prev == null){ //insert in begining of list
            first = new Link(value,strValue);
            first.next = curr;
            curr.prev = first;
            return first;  //return added Link
        }else {
            prev.next = new Link(value,strValue);
            prev.next.next = curr;
            prev.next.prev = prev;
            if(curr != null){ //If List has 1 element and we put newLink after them we dont need to acces to cure.prev because curr = null
                curr.prev = prev.next;
            }else { //if (curr == null)
                last = prev.next;              //Set Last Link;
            }

            return prev.next; //return added Link
        }

    }
    //--------------------------------------------------------------------------------------------
    public Link removeMinimum() {
        curr = first;
        if (curr == null) {
            return null;
        } else {
            numElements--;
            first = curr.next;
            if(curr.next == null){ //List contain 1 deleting Link
                last = null;
            }else {
                first.prev = null;
            }
            return curr;
        }
    }

    //--------------------------------------------------------------------------------------------
    public Link remove(int value){
        curr = search(value);
        if(curr == null){
            return null;
        }else{
            numElements--;
            if(curr.prev == null){ //Link in the begining of List
                first=curr.next;
                if(last == curr){ //List contain 1 deleting Link
                    last = null;
                }
                curr.next.prev = null;
                return curr;
            }else{              //Link is not begining if List
                if(last == curr){ //Link were last element of List
                    last = curr.prev;
                    curr.prev.next=null;
                }else {
                    curr.next.prev = curr.prev;
                    curr.prev.next = curr.next;
                }
                return curr;
            }
        }
    }
    //--------------------------------------------------------------------------------------------
    public Link search(int value){
        curr= first;
        while(curr != null && value >= curr.getValue()){
            if(curr.getValue() == value){
                System.out.println(value + " founded");
                return curr;
            }
            curr = curr.next;
        }
        System.out.println(value + " not founded");
        return null;
    }
    //--------------------------------------------------------------------------------------------
    public void showList(){
        curr=first;
        while(curr!=null){
            System.out.print(curr.getValue() + " ");
            curr=curr.next;
        }
    }
    //--------------------------------------------------------------------------------------------
    public int[] toArray(boolean backOrder){
        int[] array= new int[numElements];
        int index=0;

        if(backOrder){
            curr = last;
            while(curr!=null){
                array[index++]=curr.getValue();
                curr=curr.prev;
            }
        }else{
            curr = first;
            while(curr != null){
                array[index++]=curr.getValue();
                curr=curr.next;
            }
        }
        return array;
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Link{
    Link next;
    Link prev;
    private int value;

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    private String strValue;

    public int getValue() {
        return this.value;
    }

    Link(int value , String strValue){
        this.strValue = strValue;
        this.value = value;
        this.next = null;
        this.prev = null;
    }
}