package ex1useaclass;

import static java.lang.Math.abs;

/*
 *    A class representing a rational number
 *
 *    NOTE: No IO here, this is just the logical concept.
 *
 *    Test the class by running Ex1TestRational.
 *
 * See:
 * - ex1classes/
 */

public class Rational {

    private int num;    // rational =  num / denom
    private int denom;

    // TODO


    public Rational(int num, int denom) {
        int div = GCD(num, denom);
        int sign = abs(denom)/denom;
        this.num = sign*num/div;
        this.denom = sign*denom/div;
    }

    public Rational(int num) {
        this.num = num;
        this.denom = 1;
    }

    public Rational(Rational rat){
        this.num = rat.num;
        this.denom = rat.denom;
    }

    public int getNum() {
        return num;
    }

    public int getDenom() {
        return denom;
    }

    private int GCD(int num, int denom){
        int rest;
        num = abs(num);
        denom = abs(denom);
        if (denom>=num) {
            int tmp = num;
            num = denom;
            denom = tmp;
        }
        if(denom == 0){
            return 1;
        }
        rest = num%denom;

        while(rest != 0){
            num = denom;
            denom = rest;
            rest = num%denom;
        }
        return denom;
    }

    Rational add(Rational rat){
        return new Rational((rat.num*this.denom+this.num*rat.denom), rat.denom*this.denom);
    }

    Rational sub(Rational rat){
        return new Rational((this.num*rat.denom-rat.num*this.denom), rat.denom*this.denom);
    }

    Rational mul(Rational rat){
        return new Rational(this.num*rat.num, this.denom*rat.denom);
    }

    Rational div(Rational rat){
        return new Rational(this.num*rat.denom, this.denom*rat.num);
    }

    boolean lessThan(Rational rat){
        return false;
    }

    boolean equals(Rational rat){
        return (this.num == rat.num && this.denom == rat.denom);
    }

    @Override
    public String toString() {
        return num + " / " + denom;
    }

}

