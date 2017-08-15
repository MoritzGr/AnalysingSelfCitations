table <- YearInOut
library(ggplot2)


p <- ggplot(table, aes(x = p.year)) +geom_line(aes(y = table$`AVG(outgoingCitationCount)` , colour = "Average # Outgoing Citations")) + ylab("")

p <- p + scale_colour_manual("", breaks = c("Average # Outgoing Citations"), values = c("Average # Outgoing Citations" = "black"))


p + xlim(1975,2016) + xlab("Year of Publishing")