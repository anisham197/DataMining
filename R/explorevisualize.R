data <- iris

#View the dataset
View(data)

#See dimensions, row names, head and tail records
dim(data)
names(data)
head(data)
tail(data)
str(data) #Species labels converted to factors

#Obtain a random sample of 5 records and view etails
index <- sample(1:nrow(data), 5)
data[index,]
data[index, "Sepal.Length"]
data[index,5]
data$Petal.Width[index]

#Check for unique values
sapply(data, function(x) length(unique(x)))

#Check for missing values
sapply(data,function(x) sum(is.na(x)))

#See statistics
summary(data)
mean(mydata$Sepal.Length)
median(mydata$Sepal.Width)
range(mydata$Petal.Length)
quantile(mydata$Petal.Width)
quantile(mydata$Petal.Width,c(0.1, 0.5, 0.65))
var(mydata$Sepal.Length) #variance

#covarience and correlation
cov(mydata$Sepal.Length, mydata$Petal.Length)
cor(mydata$Sepal.Length, mydata$Petal.Length)

#Plots
hist(data$Sepal.Length)
plot(density(data$Sepal.Length))
table(data$Species)
pie(table(data$Petal.Width))
barplot(table(data$Sepal.Width))


#boxplot: shows min, first quarile, median, third quartile and max
boxplot(Sepal.Length ~ Species, data=mydata, xlab="Species", ylab="Sepal.Length")

#scatter plot: drawn for 2 numeric values. col = colours in the plot, pch = shapes for plot
plot(data$Sepal.Length, data$Sepal.Width, col=data$Species, pch=c(15,16,17) )
legend("topleft", legend=levels(data$Species), pch=c(15,16,17))

#scatter plot matrix
pairs(data, col=data$Species, pch=c(15,16,17))


