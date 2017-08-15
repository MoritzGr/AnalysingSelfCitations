table <- year_reformat
norm <- YearInOut
library(ggplot2)


p <- ggplot(table, aes(x = value, y = (selfCitingPaperCount-selfCitingDuplicatePaperCount)/(norm$`AVG(outgoingCitationCount)`*(paperCount-duplicatePaperCount)))) + geom_line(aes(color = "Normalized Frequency of Self-Citations [% of Papers]")) + ylab("") + scale_y_continuous(labels = scales::percent)


p <- p + geom_ribbon(alpha = 0.3, aes(fill = "Histogram [% of all Papers]", ymin =0, ymax = (table$paperCount-table$duplicatePaperCount)/sum(table$paperCount-table$duplicatePaperCount)))

p <- p + scale_fill_manual("", breaks = c("Histogram [% of all Papers]"), values = c("red"))
p <- p + scale_color_manual("", breaks = c("Normalized Frequency of Self-Citations [% of Papers]"), values = c("black"))


p + xlim(1975,2016) + xlab("Year of Publishing")