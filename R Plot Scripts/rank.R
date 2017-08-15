table <- rank_reformat
table$value <- factor(table$value, levels = c("A*","A", "B", "C", "Australasian", "National", "L", "Unranked", "Unranked*" ))
library(ggplot2)


p <- ggplot(table, aes( x = value, y = (selfCitingPaperCount-selfCitingDuplicatePaperCount)/(paperCount-duplicatePaperCount))) + geom_bar( alpha = 0.3 , stat = "identity", aes(fill = "Frequency of Self-Citations [% of Papers]")) + ylab("")+ scale_y_continuous(labels = scales::percent)


p <- p + geom_bar( stat = "identity", alpha = 0.3, aes(fill = "Histogram [% of all Papers]", y = (table$paperCount-table$duplicatePaperCount)/sum(table$paperCount-table$duplicatePaperCount)))

p <- p + scale_fill_manual("", breaks = c("Frequency of Self-Citations [% of Papers]","Histogram [% of all Papers]"), values = c("Frequency of Self-Citations [% of Papers]" = "black","Histogram [% of all Papers]" = "red"))

p + xlab("CORE Conference Rank")