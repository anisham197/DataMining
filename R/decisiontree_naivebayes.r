#installation of packages
install.packages("party")
install.packages("caret")
install.packages("e1071")

#load libraries
library("MASS") #Contains the breast cancer dataset
library(party)  #Contains the decision tree functions
library(caret)  #Contains confusion matrix functions
library(e1071)  #Contains the naive bayes functions

#View the datset documentation
?biopsy

#Load the dataset
data <- biopsy
View(data)

#Split dataset into test and train
index <- sample(2, nrow(data), replace=TRUE, prob=c(0.7,0.3))
train <- data[index==1,]
test <- data[index==2,]

#Select the dependent and independent features
features <- class ~ V1+V2+V3+V4+V5+V6+V7+V8+V9


#DECISION TREE

#Obtain a decision tree model
model <- ctree(features, data=train)

#Plot the model
plot(model)

#Evaluate model on test data
test_predictions <- predict(model, newdata=test)
confusionMatrix(test_predictions, test$class, positive="malignant")

#NAIVE BAYES CLASSIFIER

#Obtain a naive bayes model
model2 <- naiveBayes(features, data=train)

#Model Summary
print(model2)

#Evaluate model on test data
test_predictions2 <- predict(model2, newdata = test)
confusionMatrix(test_predictions2, test$class, positive="malignant")




