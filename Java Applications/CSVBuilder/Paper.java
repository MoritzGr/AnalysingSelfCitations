import java.util.LinkedList;
import java.util.List;

/**
 * Objects of this Class represent a single paper entry within the citation graph.
 */
public class Paper {

    /**
     * Paper properties in the citation graph
     */
    private int year = -1;
    private List<String> authors;
    private String index = "null";
    private List<String> cited;
    private String venue = "null";

    /**
     * Own id Numbers since aminer index is bugged
     */
    private int idNo;

    /**
     * Constructs a new paper with given idNo
     *
     * @param idNo idNo of paper.
     */
    public Paper(int idNo) {
        this.idNo = idNo;
        authors = new LinkedList<>();
        cited = new LinkedList<>();
    }

    /**
     * Sets this papers index
     *
     * @param index the new index
     */
    public void setIndex(String index) {
        this.index = cleanString(index);
    }

    /**
     * Sets this papers venue
     *
     * @param venue the new venue
     */
    public void setVenue(String venue) {
        this.venue = cleanString(venue);
    }

    /**
     * Sets this papers publishing year
     *
     * @param year the new publishing year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Adds an author to this papers list of authors
     *
     * @param author the new author
     */
    public void addAuthor(String author) {
        authors.add(cleanString(author));
    }

    /**
     * Adds a citation to this papers list of citations
     *
     * @param cited the new citation
     */
    public void addCited(String cited) {
        this.cited.add(cleanString(cited));
    }

    /**
     * Cleans a string by replacing its quotes with other chars. Prevents database issues.
     *
     * @param string string to be cleaned
     * @return the cleaned string
     */
    private String cleanString(String string) {
        return string.replace('\'', '´').replace('"', '#');
    }

    /**
     * For debugging print all info about this paper.
     *
     * @return
     */
    @Override
    public String toString() {
        return paperLine() + authorsLine() + citationsLine();
    }

    /**
     * This method prints the information pertaining to the paper as a csv line
     *
     * @return the csv line
     */
    public String paperLine() {
        StringBuilder sb = new StringBuilder();
        sb.append(idNo);
        sb.append(',');
        sb.append(index);
        sb.append(',');
        sb.append(year);
        sb.append(',');
        sb.append('"');
        sb.append(venue);
        sb.append('"');
        sb.append('\n');
        return sb.toString();
    }

    /**
     * This method prints the header pertaining to the paper as a csv line
     *
     * @return the csv header
     */
    static String headerPaperLine() {
        StringBuilder sb = new StringBuilder();
        sb.append("idNo");
        sb.append(',');
        sb.append("index");
        sb.append(',');
        sb.append("year");
        sb.append(',');
        sb.append("venue");
        sb.append('\n');
        return sb.toString();
    }

    /**
     * This method prints the information pertaining to the authors as a csv line
     *
     * @return the csv line
     */
    public String authorsLine() {
        StringBuilder sb = new StringBuilder();
        int authorPosition = 1;
        for (String author : authors) {
            sb.append(idNo);
            sb.append(',');
            sb.append('"');
            sb.append(author);
            sb.append('"');
            sb.append(',');
            sb.append(authorPosition++);
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * This method prints the header pertaining to the authors as a csv line
     *
     * @return the csv header
     */
    static String headerAuthorsLine() {
        StringBuilder sb = new StringBuilder();
        sb.append("paperIdNo");
        sb.append(',');
        sb.append("author");
        sb.append(',');
        sb.append("authorPosition");
        sb.append('\n');
        return sb.toString();
    }

    /**
     * This method prints the information pertaining to the citations as a csv line
     *
     * @return the csv line
     */
    public String citationsLine() {
        StringBuilder sb = new StringBuilder();
        for (String citedID : cited) {
            sb.append(idNo);
            sb.append(',');
            sb.append(citedID);
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * This method prints the header pertaining to the citations as a csv line
     *
     * @return the csv header
     */
    static String headerCitationsLine() {
        StringBuilder sb = new StringBuilder();
        sb.append("paperIdNo");
        sb.append(',');
        sb.append("citedIndex");
        sb.append('\n');
        return sb.toString();
    }
}
