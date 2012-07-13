package additional;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class pairRate {

	double rateMinusAvg1;
	double rateMinusAvg2;

	public double getx() {
		return rateMinusAvg1;
	}

	public double gety() {
		return rateMinusAvg2;
	}

	public pairRate() {
	}

	public pairRate(double rateminusavg1, double rateminusavg2) {
		this.rateMinusAvg1 = rateminusavg1;
		this.rateMinusAvg2 = rateminusavg2;
	}

	@Override
	public String toString() {
		return (rateMinusAvg1 + " " + rateMinusAvg2 + " ");
	}

	public void setRatings(int r1, int r2) {
		this.rateMinusAvg1 = r1;
		this.rateMinusAvg2 = r2;
	}

	public void write(DataOutput out) throws IOException {
		out.writeDouble(rateMinusAvg1);
		out.writeDouble(rateMinusAvg2);
	}

	public void readFields(DataInput in) throws IOException {
		this.rateMinusAvg1 = in.readDouble();
		this.rateMinusAvg2 = in.readDouble();
	}

	public int compareTo(pairRate other) {
		if (this.rateMinusAvg1 < other.rateMinusAvg1)
			return -1;
		else if (this.rateMinusAvg1 > other.rateMinusAvg1)
			return 1;
		else {
			if (this.rateMinusAvg2 < other.rateMinusAvg2)
				return -1;
			else if (this.rateMinusAvg2 > other.rateMinusAvg2)
				return 1;
			return 0;
		}
	}
}
