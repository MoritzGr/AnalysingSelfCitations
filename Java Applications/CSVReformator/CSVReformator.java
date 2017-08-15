import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * This class reformats the multi lined export from the neo4j database into single lined csv files
 */
public class CSVReformator {

    /**
     * Paths to all the exported csvs.
     * <p>
     * Change accordingly
     */
    static final String AUTHOR_POSITON = "D://B-Arbeit/Eval/authorPosition.csv";
    static final String AUTHORS = "D://B-Arbeit/Eval/authors.csv";
    static final String INCOMING_CITATIONS = "D://B-Arbeit/Eval/incomingCitations.csv";
    static final String OUTGOING_CITATIONS = "D://B-Arbeit/Eval/outgoingCitations.csv";
    static final String RANK = "D://B-Arbeit/Eval/rank.csv";
    static final String YEAR = "D://B-Arbeit/Eval/year.csv";

    /**
     * This method initializes the process for every exported file.
     *
     * @param args no args
     * @throws IOException file read or write was unsuccessful
     */
    public static void main(String[] args) throws IOException {
        reformat(AUTHOR_POSITON);
        reformat(AUTHORS);
        reformat(INCOMING_CITATIONS);
        reformat(OUTGOING_CITATIONS);
        reformat(RANK);
        reformat(YEAR);
    }

    /**
     * This method read a single csv file in the export format and reformats it into a single lined csv
     *
     * @param filePath path to the export csv
     * @throws IOException file read or write was unsuccessful
     */
    private static void reformat(String filePath) throws IOException {
        //Determine where to put output and reset that file
        String outputPath = getOutputPath(filePath);
        Files.deleteIfExists(new File(outputPath).toPath());
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputPath));

        //Write header
        outputWriter.write(CSVLine.getHeaderString());

        //Scanner for input file
        Scanner sc = new Scanner(new BufferedInputStream(new FileInputStream(filePath)));

        //discard header of input file
        sc.nextLine();

        // initialize csvLine object which will be built up by every line of the same data value
        CSVLine csvLine = null;

        //iterate over lines
        while (sc.hasNextLine()) {

            //get line
            String line = sc.nextLine();

            //reformat line and change csvLine object. Save output in new variable
            CSVLine reformatedLine = handleLine(line, csvLine);

            // If csvLine object has changed characteristic values, print the previous csvLine
            if (csvLine != null && reformatedLine != csvLine) {
                outputWriter.write("\n");
                outputWriter.write(csvLine.toString());
            }

            //update csvLine
            csvLine = reformatedLine;
        }

        //last line. Ensure last previous line was written
        if (csvLine != null) {
            outputWriter.write("\n");
            outputWriter.write(csvLine.toString());
        }

        //done
        sc.close();
        outputWriter.close();
    }

    /**
     * This method handles a line in the export format and changes a CSVLine object accordingly
     *
     * @param line    line in the export format
     * @param csvLine current CSVLine object to modify or replace
     * @return modified or replaced CSVLine object
     */
    private static CSVLine handleLine(String line, CSVLine csvLine) {

        //Split line at unquoted commas to see how many columns it has
        String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        //Author position has 4 columns, no labels, since it derives from a relationship, not a node
        boolean hasLabels = values.length == 3;

        // get characteristic value. i.e. for Year: 2016
        String value = values[0];

        //create output object to be modified
        CSVLine output = csvLine;

        //new characteristic value, does not belong in the passed csvLine object, create new one.
        if (csvLine == null || !csvLine.hasValue(value)) {
            output = new CSVLine(value);
        }

        //The count of papers that this export format line is talking about
        int count;
        //What type of papers this export format line is talking about.
        boolean selfCiting, duplicate;

        //Fill the variables depending on whether this line has labels
        if (hasLabels) {

            count = Integer.parseInt(values[2]);

            selfCiting = values[1].contains("selfCitingPaper");
            duplicate = values[1].contains("duplicatePaper");

        } else {
            selfCiting = Boolean.valueOf(values[1]);
            duplicate = Boolean.valueOf(values[2]);

            count = Integer.parseInt(values[3]);
        }

        //update the paperCount for all types of papers that this line is talking about.
        output.addPaperCount(count);

        if (selfCiting) {
            output.addSelfCitingPaperCount(count);
        }
        if (duplicate) {
            output.addDuplicatePaperCount(count);
        }
        if (selfCiting && duplicate) {
            output.addSelfCitingDuplicatePaperCount(count);
        }

        //done
        return output;
    }

    /**
     * This method derives the output path from a given input path by inserting the string _reformat before .csv
     *
     * @param filePath the given input path
     * @return the derived output path
     */
    private static String getOutputPath(String filePath) {
        StringBuilder output = new StringBuilder(filePath);
        output.insert(output.length() - ".csv".length(), "_reformat");

        return output.toString();
    }
}
