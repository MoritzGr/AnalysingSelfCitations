library(ggplot2)

using <- "complete.obs"
meth <- "pearson"

s <- rank_reformat
table <- data.frame(Value = c(s$value), NoDup = c((s$selfCitingPaperCount - s$selfCitingDuplicatePaperCount)/(s$paperCount-s$duplicatePaperCount)), Dup = c(s$selfCitingPaperCount/s$paperCount))

rank <- cor(table$Dup, table$NoDup, use = using, method = meth)

s <- authorPosition_reformat
table <- data.frame(Value = c(s$value), NoDup = c((s$selfCitingPaperCount - s$selfCitingDuplicatePaperCount)/(s$paperCount-s$duplicatePaperCount)), Dup = c(s$selfCitingPaperCount/s$paperCount))
table <- table[table$Value <= 25,]

authorPosition <- cor(table$Dup, table$NoDup, use = using, method = meth)

s <- authors_reformat
table <- data.frame(Value = c(s$value), NoDup = c((s$selfCitingPaperCount - s$selfCitingDuplicatePaperCount)/(s$paperCount-s$duplicatePaperCount)), Dup = c(s$selfCitingPaperCount/s$paperCount))
table <- table[table$Value <= 20,]

authors <- cor(table$Dup, table$NoDup, use = using, method = meth)

s <- incomingCitations_reformat
table <- data.frame(Value = c(s$value), NoDup = c((s$selfCitingPaperCount - s$selfCitingDuplicatePaperCount)/(s$paperCount-s$duplicatePaperCount)), Dup = c(s$selfCitingPaperCount/s$paperCount))
table <- table[table$Value <= 50,]

iCit <- cor(table$Dup, table$NoDup, use = using, method = meth)

s <- outgoingCitations_reformat
table <- data.frame(Value = c(s$value), NoDup = c((s$selfCitingPaperCount - s$selfCitingDuplicatePaperCount)/(s$paperCount-s$duplicatePaperCount)), Dup = c(s$selfCitingPaperCount/s$paperCount))
table <- table[table$Value <= 50,]

oCit <- cor(table$Dup, table$NoDup, use = using, method = meth)

s <- year_reformat
table <- data.frame(Value = c(s$value), NoDup = c((s$selfCitingPaperCount - s$selfCitingDuplicatePaperCount)/(s$paperCount-s$duplicatePaperCount)), Dup = c(s$selfCitingPaperCount/s$paperCount))
table <- table[table$Value >= 1970,]

year <- cor(table$Dup, table$NoDup, use = using, method = meth)


cors <- data.frame(Characteristic = c("Authors", "Author Position", "Outgoing Citations", "Publishing year", "Incoming Citations", "Conference Rank") , Correlation = c(authors, authorPosition, oCit, year, iCit, rank))

p <- ggplot(cors, aes(fill = Characteristic, x = Characteristic, y = Correlation)) + geom_bar(stat = "identity") + geom_text(aes(label = round(Correlation, digits = 3), vjust = 2))

p + guides(fill=FALSE) + xlab("Paper Characteristic") + ylab("Pearson Correlation")
