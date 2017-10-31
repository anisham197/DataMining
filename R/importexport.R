#Create a data frame of 5 columns, 15 rows
grade <- sample(c('A','B','C','D'), 15, replace=TRUE)
name<- sample(c('Alice', 'Steve', 'Joe' , 'Louis', 'Kathy'), 15, replace=TRUE)
percent <- sample(c(0.9, 0.8, 0.7, 0.6, 0.5), 15, replace = TRUE)
level <- sample( 1:6, 15, replace = TRUE)
label <- sample(c(TRUE, FALSE), 15, replace=TRUE, prob=c(0.6,0.4))
data <- data.frame(grade, name, percent, num, label)

#Write to and read from csv file
write.csv(data, "./data.csv", row.names = FALSE)
data1 <- read.csv("./data.csv")
data1

#Write to and read from tab separated text file
write.table(data, "./data.txt", sep="\t")
data2 <- read.table("./data.txt")
data2

#write to and read from R data file formats
saveRDS(data, file = "./data.rds")
data3<-readRDS(file="./data.rds")
data3
 
save(data, file="./data.Rdata") #Saved in home directory
rm(data)
load(file = "./data.Rdata")
data

#import inbuilt dataset
data5 <- iris
data5
