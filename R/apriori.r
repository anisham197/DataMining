install.packages("arules")
install.packages("arulesViz")

library(arules)
library(arulesViz)

#load data
data("Groceries")

#view data
summary(Groceries)
inspect(Groceries[1:5])

#View top 20 most frequently bought items
itemFrequencyPlot(Groceries,topN=20,type="absolute")

#Obtain and sort rules according to confidence
basket_rules <- apriori(Groceries,parameter = list(sup = 0.01, conf = 0.5,target="rules"))
basket_rules<-sort(basket_rules, by="confidence", decreasing=TRUE)

#View rules
summary(basket_rules)
inspect(basket_rules)

#View rules as a data frame
df_basket <- as(basket_rules,"data.frame")
View(df_basket)

# finding subsets of rules containing yogurt
yogurtrules <- subset(basket_rules, items %in% "yogurt")
inspect(yogurtrules)

# finding subsets of rules that succede yogurt purchases
yogurt_lhs_rules <- subset(basket_rules, lhs %pin% "yogurt")
inspect(yogurt_lhs_rules)

# finding subsets of rules that precede whole milk purchases
milk_rhs_rules <- subset(basket_rules, rhs %pin% "whole milk")
inspect(milk_rhs_rules)

#graph of the rules
plot(basket_rules,method = "graph")

