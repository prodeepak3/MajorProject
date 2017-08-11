#installing library
install.packages("e1071")
library(e1071)


ndata= rbind(train,test)
ndat<-ndata[,3:4]

# native bayes
matrix = create_matrix(ndat[,2], language = "english", removeStopwords = FALSE, 
                       removeNumbers = TRUE, stemWords = FALSE, tm::weightTfIdf)
mat = as.matrix(matrix)
classifier = naiveBayes(mat[1:nrow(c), ], as.factor(ndat[1:nrow(train), 1]))
predicted = predict(classifier, mat[16326:23322, ])
predicted
mean(ndat[16326:23322,1]!=predicted)


#predicting accuracy
recall_accuracy(ndat[16326:23322, 1], predicted)
