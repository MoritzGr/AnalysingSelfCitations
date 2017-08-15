/**
 * This class models a single line of the reformatted csv files
 */
public class CSVLine {
    //Characteristic value. i.e for Year: 2016
    String value;
    int paperCount, duplicatePaperCount, selfCitingPaperCount, selfCitingDuplicatePaperCount;

    /**
     * Constructs a new CSVLine with the given characteristic value
     *
     * @param value given characteristic value
     */
    public CSVLine(String value) {
        this.value = value;
    }

    /**
     * Checks if this line has the given value
     *
     * @param value given value to be checked against
     * @return True if this line has the value, false otherwise
     */
    public boolean hasValue(String value) {
        return this.value.equals(value);
    }

    /**
     * Adds a number to the count of Papers
     *
     * @param input number to be added
     */
    public void addPaperCount(int input) {
        paperCount += input;
    }

    /**
     * Adds a number to the count of duplicate Papers
     *
     * @param input number to be added
     */
    public void addDuplicatePaperCount(int input) {
        duplicatePaperCount += input;
    }

    /**
     * Adds a number to the count of self-citing Papers
     *
     * @param input number to be added
     */
    public void addSelfCitingPaperCount(int input) {
        selfCitingPaperCount += input;
    }

    /**
     * Adds a nubmer to the count of self-citing, duplicate Papers
     *
     * @param input
     */
    public void addSelfCitingDuplicatePaperCount(int input) {
        selfCitingDuplicatePaperCount += input;
    }

    /**
     * Writes this CSVLines information as a single csv line.
     *
     * @return the single csv line containing this lines information
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(value);
        sb.append(',');
        sb.append(selfCitingPaperCount);
        sb.append(',');
        sb.append(paperCount);
        sb.append(',');
        sb.append(selfCitingDuplicatePaperCount);
        sb.append(',');
        sb.append(duplicatePaperCount);

        return sb.toString();
    }

    /**
     * Writes the headers for the reformatted csv documents into a single csv line
     *
     * @return The csv header
     */
    public static String getHeaderString() {
        StringBuilder sb = new StringBuilder();

        sb.append("value");
        sb.append(',');
        sb.append("selfCitingPaperCount");
        sb.append(',');
        sb.append("paperCount");
        sb.append(',');
        sb.append("selfCitingDuplicatePaperCount");
        sb.append(',');
        sb.append("duplicatePaperCount");

        return sb.toString();
    }
}
