#K-MEANS CLUSTERING

#plot initial data
ggplot(iris, aes(Petal.Length, Petal.Width, color = iris$Species)) + geom_point()

#kmeans
set.seed(20)
irisCluster <- kmeans(iris[, 3:4], 3, nstart = 20)
irisCluster

#obtain predicted class using kmeans
predict_class <- fitted(irisCluster, method = c("classes"))

#compare predicted class vs actual class
data <- data.frame(iris$Species, predict_class)
data
table(irisCluster$cluster, iris$Species)

#plot predicted data
irisCluster$cluster <- as.factor(irisCluster$cluster)
library(ggplot2)
ggplot(iris, aes(Petal.Length, Petal.Width, color = irisCluster$cluster)) + geom_point()



#HEIRARCHIAL CLUSTERING
clusters <- hclust(dist(iris[, 3:4]), method = 'average')
plot(clusters)

# cut tree into 3 clusters
rect.hclust(clusters, k=3)
clusterCut <- cutree(clusters, 3)
table(clusterCut, iris$Species)

clusterCut <- as.factor(clusterCut)
ggplot(iris, aes(Petal.Length, Petal.Width, color = clusterCut)) + geom_point()


