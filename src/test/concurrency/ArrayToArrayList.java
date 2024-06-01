package test.concurrency;

import java.util.*;
import java.util.stream.Collectors;

interface MyList{
    void convert(String[] a);
    void replace(int idx);
    ArrayList<String> compact();
}

/*
Write the implementation of the InvalidStringException and the ArrayToList classes
Model strings for cut and paste:
I have added the string: {string} at the index: {index}
I have replaced the string: {string} with a null string
*/

class InvalidStringException extends Exception{
    InvalidStringException(String msg){
        super(msg);
    }
}

class ArrayToList implements MyList{
    List<String> list;
    ArrayToList(){
        list = new ArrayList<>();
    }

    @Override
    public void convert(String[] a){
        int n = a.length;
        for (int i = 0; i < n; i++){
            list.add(a[i]);
            System.out.println("I have added the string: " + a[i] + " at the index: " + i);
        }
    }

    @Override
    public void replace(int idx){
        String s = list.get(idx);
        list.set(idx, "");
        System.out.println("I have replaced the string: " + s + " with a null string");
    }

    @Override
    public ArrayList<String> compact(){
        ArrayList<String> result = new ArrayList<String>();
        for (String str : list) {
            if (str != null && !str.isEmpty()) {
                result.add(str);
            }
        }
        return result;
    }
}

public class ArrayToArrayList{

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        Random rand = new Random(0);

        int n = Integer.parseInt(sc.nextLine());
        String[] a = new String[n];

        for(int i = 0; i < n; i++)
            a[i] = sc.nextLine();

        MyList obj = new ArrayToList();

        obj.convert(a);
        int x = rand.nextInt(n);
        for(int i = 0; i < x; i++)
            obj.replace(rand.nextInt(n));


        ArrayList<String> s = obj.compact();



        for (int i = 0; i < s.size(); i++){
            if(s.get(i).charAt(0) >= 97 && s.get(i).charAt(0) <= 122){
                try{
                    throw new InvalidStringException("This is an invalid string");
                }
                catch(InvalidStringException e){System.out.println(e.getMessage());}
            }
        }


    }
}