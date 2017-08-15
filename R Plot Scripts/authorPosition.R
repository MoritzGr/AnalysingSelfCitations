table <- authorPosition_reformat
library(ggplot2)


p <- ggplot(table, aes(x = value, y = (selfCitingPaperCount-selfCitingDuplicatePaperCount)/(paperCount-duplicatePaperCount))) + geom_line(aes(color = "Frequency of Self-Citations [% of Authorships]")) + ylab("")+ scale_y_continuous(labels = scales::percent)


p <- p + geom_ribbon(alpha = 0.3, aes(fill = "Histogram [% of all Authorships]", ymin =0, ymax = (table$paperCount-table$duplicatePaperCount)/sum(table$paperCount-table$duplicatePaperCount)))

p <- p + scale_fill_manual("", breaks = c("Histogram [% of all Authorships]"), values = c("red"))
p <- p + scale_color_manual("", breaks = c("Frequency of Self-Citations [% of Authorships]"), values = c("black"))


p + xlab("Author Position") + xlim(1,25)