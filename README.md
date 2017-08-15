# AnalysingSelfCitations
This github repository contains the code and data created for the bachelor's thesis "Analysing Characteristics of Self-Citations in Computer Science"

The contents of this repository are the following:

- All the Cyhper queries used in the import, building, and export of the Neo4j database:
	- Evaluation.cyp: Queries used for the export of results
	- Identify duplicate index.cyp: Queries used to identify and mark papers with duplicate indices(Not neccessary if the bug has bin fixed)
	- Identify self citations.cyp: Queries used to identify and mark papers as self-citing
	- Import.cyp: Queries used in the import of the data

- The source code for all Java applications written for this thesis:
	- CSVBuilder: Parses the aminer citation graph into csv files
	- CSVReformator: Reformats neo4j output csv files.

- All the R scripts used to draw the plots displayed in the thesis

- All the results of the methodology of the thesis in its final format