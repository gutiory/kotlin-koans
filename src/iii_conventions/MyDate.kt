package iii_conventions


data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override fun compareTo(other: MyDate): Int {
        if (year.compareTo(other.year) == 0) {
            if (month.compareTo(other.month) == 0) {
                return dayOfMonth.compareTo(other.dayOfMonth)
            }
            return month.compareTo(other.month)
        }
        return year.compareTo(other.year)
    }



}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(timeInterval: TimeInterval) : MyDate = this.addTimeIntervals(timeInterval, 1)

operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval) : MyDate =
        this.addTimeIntervals(repeatedTimeInterval.timeInterval, repeatedTimeInterval.n)


operator fun TimeInterval.times(n:Int) = RepeatedTimeInterval(this, n)

    enum class TimeInterval {
        DAY,
        WEEK,
        YEAR
    }

    class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate>{
        override fun iterator(): Iterator<MyDate> {
            return object : Iterator<MyDate> {

                var current = start

                override fun hasNext(): Boolean {
                    val res = current <= endInclusive
                    return res
                }

                override fun next(): MyDate {
                    val res = current
                    current = current.nextDay()
                    return res
                }

            }
        }

        operator fun contains(d: MyDate) : Boolean {
            return (start <= d && d <= endInclusive)
        }
    }

class RepeatedTimeInterval(val timeInterval: TimeInterval,val n: Int)
