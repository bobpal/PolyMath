/* Robert Palagano
 * This program adds, multiplies, and prints polynomials
 */
package polymath;
import java.util.*;
public class Polymath{

    private static class Term{                  //Term a.k.a Node class
        private int coefficient;                //data
        private int exponent;                   //data
        
        public Term(int c, int e){              //constructor
            coefficient = c;
            exponent = e;
        }
    }

    static class Sorter implements Comparator<Term>{
        @Override
        public int compare(Term o1, Term o2){
            if(Math.abs(o1.exponent) < Math.abs(o2.exponent)){
                return 1; 
            }
            if(o1.exponent > o2.exponent){
                return -1; 
            }
            return 0;
        }
    }

    public static LinkedList add(LinkedList p, LinkedList q){   //adds polynomials together
        LinkedList<Term> r = new LinkedList<Term>();            //create new polynomial
        int c1 = 0;                             //p.coefficient
        int c2 = 0;                             //q.coefficient
        int c3 = 0;                             //r.coefficient
        int e1 = 0;                             //p.exponent
        int e2 = 0;                             //q.exponent
        int i = 0;                              //p counter
        int j = 0;                              //q counter

        int lsize = p.size() + q.size();        //# of terms in both lists combined
        while(i+j < lsize){                     //go till the end of both lists
            if(i < p.size()){
                Term x = (Term) p.get(i);
                c1 = x.coefficient;
                e1 = x.exponent;
            }    
            else{
                c1 = 0;
                e1 = 0;
            }
            
            if(j < q.size()){
                Term y = (Term) q.get(j);
                c2 = y.coefficient;
                e2 = y.exponent;
            }
            else{
                c2 = 0;
                e2 = 0;
            }
            
            if(e1 == e2){                           //if they have the same exponent
                c3 = c1 + c2;                       //add coefficients
                Term z = new Term(c3, e1);          //create new node
                r.addLast(z);                       //add new node to r
                i++;
                j++;
            }
            else if(Math.abs(e1) < Math.abs(e2)){   //if p.expo < q.expo
                Term z = new Term(c2, e2);          //put q term in r
                r.addLast(z);
                j++;
            }
            else{                                   //if p.expo > q.expo
                Term z = new Term(c1, e1);          //put p term in r
                r.addLast(z);                      
                i++;
            }
        }
        return r;                                   //return result
    }

    public static LinkedList multiply(LinkedList p, LinkedList q){  //multiplies polynomials together
        LinkedList<Term> r = new LinkedList<Term>();            //create new polynomial
        int c = 0;                                  //coefficient
        int e = 0;                                  //exponent

        for(int i=0; i<p.size(); i++){              //iterate through p
            Term x = (Term) p.get(i);

            for(int j=0; j<q.size(); j++){          //iterate through q
                Term y = (Term) q.get(j);
                c = x.coefficient * y.coefficient;  //multiply coefficients
                e = x.exponent + y.exponent;        //add exponents
                Term z = new Term(c, e);
                r.addLast(z);                       //put answer in an r node
            }
        }
        return r;                                   //return result
    }

    public static void combine(LinkedList list){
        //this method combines like terms, if two terms have same exponent, add them together
        for(int j=0; j<list.size(); j++){           //outer loop
            for(int i=0; i<list.size() - 1; i++){   //inner loop
                Term x = (Term) list.get(i);
                Term y = (Term) list.get(i + 1);
                if(x.exponent == y.exponent){       //if same exponent
                    x.coefficient += y.coefficient; //add coefficients together
                    list.remove(i + 1);             //delete term
                }
            }
        }
    }

    public static void print(LinkedList list){  //prints polynomials on console
        int i = 0;
        int c = 0;                              //coefficient variable
        int e = 0;                              //exponent variable

        Collections.sort(list, new Sorter());
        combine(list);

        Iterator itr = list.iterator();      //create iterator
        while(itr.hasNext()){                   //iterate through list
            Term n = (Term) itr.next();

            c = n.coefficient;
            e = n.exponent;

            if(i != 0 && c >= 0){               //if not first term & coefficient is not negative
                System.out.print(" + ");
            }
            if(i != 0 && c < 0){                //if not first term & coefficient is negative
                System.out.print(" - ");
            }
            if(i == 0 && c < 0){                //if first term & coefficient is negative
                System.out.print("-");
            }

            c = Math.abs(c);                    //replace with absolute value to simplify if statements

            if(e == 0){                         //if exponent is zero
                System.out.print(c);
            }
            else{                               //if exponent is not zero
                if(c == 1 && e != 1){           //if coefficient is one & exponent is not one
                    System.out.print("x^" + e);
                }
                if(c != 1 && e == 1){           //if coefficient is not one & exponent is one
                    System.out.print(c + "x");
                }
                if(c == 1 && e == 1){           //if coefficient is one & exponent is one
                    System.out.print("x");
                }
                if(c != 1 && e != 1){           //if neither coefficient or exponent is one
                    System.out.print(c + "x^" + e);
                }
            }
            i++;
        }
        System.out.println();
    }

    public static void main(String[] args){
        LinkedList<Term> p = new LinkedList<Term>();        //create list P
        Scanner input = new Scanner(System.in); //create scanner
        
        System.out.println("Enter the values for polynomial P");
        System.out.println("Enter a zero coefficient when done");
        int i = 1;                              //term counter
        
        while(true){                            //until done entering polynomial P
            System.out.print("Term " + i);
            System.out.println(":\nEnter coefficient of x");
            int coef = input.nextInt();         //get coefficient

            if(coef == 0) break;                //if coefficient is zero

            System.out.println("Enter exponent of x");
            int expo = input.nextInt();         //get exponent
            
            Term a = new Term(coef, expo);      //create new node with values
            p.addLast(a);                       //put node at end of list
            i++;                                //increment term counter
        }
        
        System.out.println("\nEnter the values for polynomial Q");
        System.out.println("Enter a zero coefficient when done");
        i = 1;                                  //set term counter back to one
        LinkedList<Term> q = new LinkedList<Term>();        //create polynomial Q
        
        while(true){                            //until done entering polynomial Q
            System.out.print("Term " + i);
            System.out.println(":\nEnter the coefficient of x");
            int coef = input.nextInt();         //get coefficient

            if(coef == 0) break;                //if coefficient is zero

            System.out.println("Enter the exponent of x");
            int expo = input.nextInt();         //get exponent
            
            Term b = new Term(coef, expo);      //create new node with values
            q.addLast(b);                       //put node at end of list
            i++;                                //increment term counter
        }
        
        System.out.println("Enter the menu number");
        System.out.println("1. Print polynomials");
        System.out.println("2. Add polynomials");
        System.out.println("3. Multiply polynomials");
        
        int menu = input.nextInt();             //get menu selection
        
        if(menu == 1){
            System.out.print("First Polynomial: ");
            print(p);
            System.out.print("\nSecond Polynomial: ");
            print(q);
        }
        
        if(menu == 2){
            LinkedList<Term> r = add(p, q);     //add polynomial p & q and store in r
            System.out.print("Sum of Polynomials: ");
            print(r);
        }
        if(menu == 3){
            LinkedList<Term> r = multiply(p, q);//multiply polynomial p by q and store in r
            System.out.print("Product of Polynomials: ");
            print(r);
        }
    }
}
