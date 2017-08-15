// Paper labels count by rank
MATCH (p:paper)
RETURN
DISTINCT p.coreRank AS rank,
labels(p) AS type,
count(p) AS count
ORDER BY rank,type;

// Paper labels count by year
MATCH (p:paper)
RETURN
DISTINCT p.year AS year,
labels(p) AS type,
count(p) AS count
ORDER BY year,type;

// Paper labels count by number of authors
MATCH (p:paper)
WITH p, SIZE((p)<-[:WRITES]-(:author)) as authorCount
RETURN
DISTINCT authorCount as authors,
labels(p) AS type,
count(p) AS count
ORDER BY authors,type;

//Paper labels by self cited author position
MATCH (:author)-[w:WRITES]->(:paper)
RETURN
DISTINCT w.authorPosition AS authorPosition,
w.self AS selfCiting,
w.duplicate AS duplicate,
count(w) AS count
ORDER BY authorPosition,selfCiting,duplicate;

// Paper labels count by number of incoming citations
MATCH (p:paper)
WITH p, SIZE((p)<-[:CITES]-(:paper)) as incomingCitationCount
RETURN
DISTINCT incomingCitationCount as incomingCitationCount,
labels(p) AS type,
count(p) AS count
ORDER BY incomingCitationCount,type;

// Paper labels count by number of outgoing citations
MATCH (p:paper)
WITH p, SIZE((p)-[:CITES]->(:paper)) as outgoingCitationCount
RETURN
DISTINCT outgoingCitationCount as outgoingCitationCount,
labels(p) AS type,
count(p) AS count
ORDER BY outgoingCitationCount,type;

// Paper labels count by number of outgoing citations
MATCH (p:paper)
WITH p, SIZE((p)-[:CITES]->(:paper)) as outgoingCitationCount, SIZE((p)<-[:CITES]-(:paper)) as incomingCitationCount
RETURN
DISTINCT p.year, AVG(outgoingCitationCount), AVG(incomingCitationCount)
ORDER BY p.year;