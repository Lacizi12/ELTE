package time;

/**
 * en Shows the time of day reasonably, e.g. 12:34.
 * hu A napon belüli időt mutatja, pl. 12:34.
 */
public class Time {
    private int hour;
    private int min;

    /**
     * en For the sake of simplicity, we assume that we get a valid time.
     * hu Az egyszerűség kedvéért itt feltesszük, hogy érvényes időt kapunk.
     */
    public Time(int hour, int min) {
        this.hour = hour;
        this.min = min;
    }

    // fix me: change double to int
    public double getHour() {
        // fix me: remove the "+ 1" part
        return hour + 1;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

	/**
	 * en Our time or `that`, whichever is earlier in the day.
	 * hu A mi időnk és `that` közül az, amelyik korábban van.
	 */
    public Time getEarlier(Time that) {
        if (hour != that.hour)  return hour < that.hour ? this : that;
        if (min != that.min)  return min < that.min ? this : that;
        return this;
    }
}
