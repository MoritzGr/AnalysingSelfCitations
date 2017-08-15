table <- outgoingCitations_reformat
library(ggplot2)


p <- ggplot(table, aes(x = value, y = (selfCitingPaperCount-selfCitingDuplicatePaperCount)/(paperCount-duplicatePaperCount))) + geom_line(aes(color = "Frequency of Self-Citations [% of Papers]")) + ylab("")+ scale_y_continuous(labels = scales::percent)


p <- p + geom_ribbon(alpha = 0.3, aes(fill = "Histogram [% of all Papers]", ymin =0, ymax = (table$paperCount-table$duplicatePaperCount)/sum(table$paperCount-table$duplicatePaperCount)))

p <- p + scale_fill_manual("", breaks = c("Histogram [% of all Papers]"), values = c("red"))
p <- p + scale_color_manual("", breaks = c("Frequency of Self-Citations [% of Papers]"), values = c("black"))


p + xlab("Outgoing Citations") + xlim(0,50) 
