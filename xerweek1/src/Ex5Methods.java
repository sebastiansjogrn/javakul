import static java.lang.System.out;

/*
 *  Implement methods to make program produce correct output (normally print true)
 *
 * See:
 * - ex4methods
 */
public class Ex5Methods {

    public static void main(String[] args) {
        new Ex5Methods().program();
    }

    void program() {
        // All, except last,  should print true

        // Uncomment one at the time
        out.println(sumTo(5) == 15);     // 1 + 2 + ... + 5 = 15
        out.println(sumTo(23) == 276);
        out.println(factorial(3) == 6);    // 3 * 2 * 1 = 6
        out.println(factorial(5) == 120);
        //out.println(digitSum(1111) == 4);   // 1 + 1 + 1 + 1 = 4
        //out.println(digitSum(12345) == 15);

        String winner = "Olle";
        // A special case, should print: "Winner is Olle" (or whatever name)
        //winnerMsg(winner);
    }

    // ------ Write methods below this  -----------

   // TODO
    int sumTo(int i){
        int s = 0;
        for(int n=1;n<=i;n++){
            s += n;
        }
        return s;
    }
    int factorial(int i) {
        int s = 1;
        for (int n = 1; n <= i; n++) {
            s *= n;
        }
        return s;
    }
    int digitsum(int i){
        toString()
        return ;
    }

}
