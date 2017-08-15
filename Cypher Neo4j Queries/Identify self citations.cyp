//Identify self citations and flag paper and citation
MATCH (a:author)-[w1:WRITES]->(p:paper)-[c:CITES]->(q:paper)<-[:WRITES]-(a)
SET p:selfCitingPaper
SET c.self = true
SET w1.self = true
RETURN count(c);