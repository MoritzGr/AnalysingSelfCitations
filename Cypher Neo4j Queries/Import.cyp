//Make Paper ids unique
CREATE CONSTRAINT ON (p:paper)
ASSERT p.idNo IS UNIQUE;

//Import papers from CSV
USING PERIODIC COMMIT LOAD CSV WITH HEADERS FROM "file:/papers.csv" AS csv
CREATE (:paper{idNo: csv.idNo, index: csv.index, year: csv.year, venue:csv.venue });

//Build index on papers
CREATE INDEX ON :paper(index);

//Import citation relations from CSV
USING PERIODIC COMMIT LOAD CSV WITH HEADERS FROM "file:/citations.csv" AS csv
MATCH (p:paper {idNo: csv.paperIdNo})
MATCH (q:paper {index: csv.citedIndex})
CREATE (p)-[:CITES]->(q);

//Make author names unique
CREATE CONSTRAINT ON (a:author)
ASSERT a.name IS UNIQUE;

//Import authors & writes relations from CSV
USING PERIODIC COMMIT LOAD CSV WITH HEADERS FROM "file:/authors.csv" AS csv
MATCH (p:paper{idNo: csv.paperIdNo})
MERGE (a:author{name: csv.author})
CREATE (a)-[:WRITES{authorPosition: toInteger(csv.authorPosition), self: false, duplicate: false}]->(p)

//Make venue names unique
CREATE CONSTRAINT ON (v:venue)
ASSERT v.name IS UNIQUE;

//Extract venue nodes from paper nodes
MATCH (p:paper)
MERGE (v:venue{name: p.venue})
CREATE (v)-[:PUBLISHES]->(p);

//Import CORE rankings from CSV
USING PERIODIC COMMIT LOAD CSV WITH HEADERS FROM "file:/core.csv" AS csv
MATCH (v:venue)
WHERE v.name = csv.name
OR v.name = csv.acronym
SET v.coreRank = csv.rank;

//Transfer CORE rank to paper for evaluation
MATCH (p:paper)<-[:PUBLISHES]-(v:venue)
WHERE EXISTS(v.coreRank)
SET p.coreRank = v.coreRank
RETURN COUNT(p);