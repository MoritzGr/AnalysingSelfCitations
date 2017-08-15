import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * This class takes the aminer citation graph source and parses it into three csv files
 */
public class CSVBuilder {

    /**
     * The path to the DBLP citation graph from aminer.
     * Here it is split into two files at an empty line for memory reasons.
     * May not be necessary for systems with better hardware.
     * <p>
     * Change paths accordingly.
     */
    static final String DBLP_SOURCE_1 = "D://B-Arbeit/dblp1.txt";
    static final String DBLP_SOURCE_2 = "D://B-Arbeit/dblp2.txt";

    /**
     * These are the paths to the output csv files.
     * <p>
     * Change paths accordingly.
     */
    static final String PAPERS_DEST = "D://B-Arbeit/papers.csv";
    static final String AUTHORS_DEST = "D://B-Arbeit/authors.csv";
    static final String CITATIONS_DEST = "D://B-Arbeit/citations.csv";


    /**
     * This main method starts the parsing process.
     *
     * @param args No Args supported
     * @throws IOException File read or write was unsuccessful.
     */
    public static void main(String[] args) throws IOException {
        //initialize files
        clearFiles();
        writeHeaders();

        //call writeFromSource once for each source partition
        //change the number of calls accordingly
        int processedPapers = writeFromSource(DBLP_SOURCE_1, 0);
        processedPapers = writeFromSource(DBLP_SOURCE_2, processedPapers);

        //done. report result
        System.out.println("Finished: Wrote " + processedPapers + " in total.");
    }

    /**
     * Deletes files that share a path with the output paths.
     *
     * @throws IOException
     */
    private static void clearFiles() throws IOException {
        Files.deleteIfExists(new File(PAPERS_DEST).toPath());
        Files.deleteIfExists(new File(AUTHORS_DEST).toPath());
        Files.deleteIfExists(new File(CITATIONS_DEST).toPath());
    }

    /**
     * Writes the csv header for each of the output files.
     * Header is determined by Paper class.
     *
     * @throws IOException file write was unsuccessful
     */
    private static void writeHeaders() throws IOException {
        //construct writers
        BufferedWriter paperWriter = new BufferedWriter(new FileWriter(PAPERS_DEST, true));
        BufferedWriter authorsWriter = new BufferedWriter(new FileWriter(AUTHORS_DEST, true));
        BufferedWriter citationsWriter = new BufferedWriter(new FileWriter(CITATIONS_DEST, true));

        //write headers
        paperWriter.write(Paper.headerPaperLine());
        authorsWriter.write(Paper.headerAuthorsLine());
        citationsWriter.write(Paper.headerCitationsLine());

        //close writers
        paperWriter.close();
        authorsWriter.close();
        citationsWriter.close();
    }

    /**
     * This method parses a dblp citation graph line by line and writes its contents to the csv files
     *
     * @param source    path to the source
     * @param startIdNo number at which ids are supposed to start
     * @return The number of handled papers
     * @throws IOException file write or read was unsuccessful
     */
    public static int writeFromSource(String source, int startIdNo) throws IOException {

        //consturct writers
        BufferedWriter paperWriter = new BufferedWriter(new FileWriter(PAPERS_DEST, true));
        BufferedWriter authorsWriter = new BufferedWriter(new FileWriter(AUTHORS_DEST, true));
        BufferedWriter citationsWriter = new BufferedWriter(new FileWriter(CITATIONS_DEST, true));
        //construct source scanner
        Scanner sc = new Scanner(new BufferedInputStream(new FileInputStream(source)));

        //initialize the count of processed papers
        int processedPapers = startIdNo;

        //monitoring information. every 10000 papers print the time in ms it took to process them
        int updateAfterPapers = 10000;
        long currTime = System.currentTimeMillis();


        if (sc != null) {

            //initialize loop variables
            Paper paper = null;
            boolean running = true;

            //build Paper Object for every entry and then write them to csv.
            while (running) {

                //get next line
                String line = sc.nextLine();

                //lines with less than 2 characters are empty and indicate the start of another paper
                if (line.length() > 2) {

                    paper = handleLine(line, paper, processedPapers);

                } else {

                    // line was empty, write current paper to csv if it exists
                    if (paper != null) {

                        paperWriter.write(paper.paperLine());
                        authorsWriter.write(paper.authorsLine());
                        citationsWriter.write(paper.citationsLine());

                        //reset paper object
                        paper = null;

                        //increase the number of processed papers and print monitoring information if update is wanted
                        if (++processedPapers % updateAfterPapers == 0) {
                            System.out.println(processedPapers + " after: " + (System.currentTimeMillis() - currTime) + " ms.");
                            currTime = System.currentTimeMillis();
                        }

                    }
                }

                //last line of the source
                if (!sc.hasNextLine()) {

                    //ensure current paper is written to csv.
                    if (paper != null) {

                        paperWriter.write(paper.paperLine());
                        authorsWriter.write(paper.authorsLine());
                        citationsWriter.write(paper.citationsLine());

                    }

                    //break loop
                    running = false;
                }
            }
        }

        //done
        sc.close();
        paperWriter.close();
        authorsWriter.close();
        citationsWriter.close();

        return processedPapers;
    }

    /**
     * Handles a single line within the dbpl citation graph source. Adds information to the paper object.
     *
     * @param line            Line to be handled
     * @param paper           Paper to be modified
     * @param processedPapers Nubmer of already processed papers. important for paper idNo
     * @return Either the modified input paper or a new one if the line denotes a new entry
     */
    public static Paper handleLine(String line, Paper paper, int processedPapers) {


        //each line of data stores the information of one property
        //the second character denotes which property
        char identifier = line.charAt(1);
        switch (identifier) {

            case '*': //Title identifier
                //Title is always the first entry of a paper.
                //Disregard title construct new paper to be filled with data in future lines.
                paper = new Paper(processedPapers);
                break;

            case '@': //Author identifier
                //Get Strings for all authors
                String[] authors = line.substring(2).split(",");

                //Add all authors to paper
                for (String author : authors) {
                    author = author.trim();
                    paper.addAuthor(author);
                }
                break;

            case 't': //Year identifier
                //Get year as number
                int year = Integer.parseInt(line.substring(2).trim());
                // add year to paper
                paper.setYear(year);
                break;

            case 'c': //Venue identifier
                //add venue to paper
                paper.setVenue(line.substring(2).trim());
                break;

            case 'i': //Index identifier
                //add index to paper
                paper.setIndex(line.substring(6).trim());
                break;

            case '%'://Citation identifier
                //add citation to paper
                paper.addCited(line.substring(2).trim());
                break;

            case '!'://Abstract identifier
                //Disregard abstract
                break;
        }

        //paper is modified or replaced. return it
        return paper;
    }
}
