## 75% of the sample size
smp_size <- floor(0.70 * nrow(c))

## set the seed to make your partition reproductible
set.seed(123)
train_ind <- sample(seq_len(nrow(c)), size = smp_size)

#splitting data into training and testing set
train <- c[train_ind, ]
test <- c[-train_ind, ]

install.packages("RTextTools")
library(RTextTools)

# Create the document term matrix
dtMatrix <- create_matrix(train$VE)
View(dtMatrix)
trace("create_matrix",edit=T)

# Configure the training data
container <- create_container(dtMatrix, train$overall, trainSize=1:smp_size, virgin=FALSE)

# train a SVM Model
model <- train_model(container, "SVM", kernel="radial", cost=1)

# create a prediction document term matrix
predMatrix <- create_matrix(test$VE,originalMatrix = dtMatrix)

# create the corresponding container
predSize = nrow(test);
predictionContainer <- create_container(predMatrix, labels=rep(0,predSize), testSize=1:predSize, virgin=FALSE)

# predict
results <- classify_model(predictionContainer, model)
View(results)

#accuracy of SVM
d<-cbind(test,results)
mean(d$overall==d$SVM_LABEL)