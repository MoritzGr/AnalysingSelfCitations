//Identify papers with duplicate index and flag them
MATCH (p:paper),(q:paper)
WHERE p.index = q.index
AND NOT p = q
SET p:duplicatePaper
SET q:duplicatePaper
RETURN COUNT(p);

//Flag author relations involving duplicate papers
MATCH (p:duplicatePaper)-[w:WRITES]-()
SET w.duplicate = true;