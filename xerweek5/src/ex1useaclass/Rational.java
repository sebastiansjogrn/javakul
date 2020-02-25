package ex1useaclass;

import java.util.Objects;

import static java.lang.Math.abs;
import static java.lang.System.*;

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

public class Rational implements Comparable<Rational> {

    private int num;    // rational =  num / denom
    private int denom;

    // TODO

    public Rational() {
        this.num = 0;
        this.denom = 0;
    }

    public Rational(int num, int denom) {
        int div = GCD(num, denom);
        int sign = abs(denom) / denom;
        this.num = sign * num / div;
        this.denom = sign * denom / div;
    }

    public Rational(int num) {
        this.num = num;
        this.denom = 1;
    }

    public Rational(Rational rat) {
        this.num = rat.num;
        this.denom = rat.denom;
    }

    public int getNum() {
        return num;
    }

    public int getDenom() {
        return denom;
    }

    private int GCD(int num, int denom) {
        int rest;
        num = abs(num);
        denom = abs(denom);
        if (denom >= num) {
            int tmp = num;
            num = denom;
            denom = tmp;
        }
        if (denom == 0) {
            return 1;
        }
        rest = num % denom;

        while (rest != 0) {
            num = denom;
            denom = rest;
            rest = num % denom;
        }
        return denom;
    }

    Rational add(Rational rat) {
        return new Rational((rat.num * this.denom + this.num * rat.denom), rat.denom * this.denom);
    }

    Rational sub(Rational rat) {
        return new Rational((this.num * rat.denom - rat.num * this.denom), rat.denom * this.denom);
    }

    Rational mul(Rational rat) {
        return new Rational(this.num * rat.num, this.denom * rat.denom);
    }

    Rational div(Rational rat) {
        return new Rational(this.num * rat.denom, this.denom * rat.num);
    }

    boolean lessThan(Rational rat) {
        Rational newRat = this.sub(rat);
        return newRat.num < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o.getClass() == (new Rational()).getClass()) {
            Rational rat = (Rational) o;
            return (this.num == rat.num && this.denom == rat.denom);
        }
        return false;
    }

    double toDouble() {
        return (double) this.num / this.denom;
    }

    boolean contains(Rational rat) {
        out.println(this.num);
        /*for(int i=0;i<this;i++){

        }*/
        return true;
    }

    public int compareTo(Rational rat) {
        if (this.equals(rat)) {
            return 0;
        } else if (this.lessThan(rat)) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return num + " / " + denom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, denom);
    }
}