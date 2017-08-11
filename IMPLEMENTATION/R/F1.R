#importing rjson library to import dataset
install.packages("jsonlite")
library(jsonlite)

#importing dataset
df<-stream_in(file(description="Electronics_5.json"),)
View(df)

#filtering dataset
install.packages("dplyr")
library(dplyr)
a<-filter(df,overall==1|overall ==5)
b<-select(a,summary,reviewText,overall)


#implementing rjava
install.packages("rJava")
library(rJava)
.jinit(classpath = "C:/Users/deepak/Desktop/bar.jar")
jobject <- .jnew("main.Tagme")  ## call the constructor
jobject <- .jnew("main.Tagme")
for(i in 1:nrow(b)){
  tryCatch({
    
    b$VE[i]<-.jcall(jobject ,"S",method="Extract",toString(b$summary[i])); ## call a method
  }, error=function(e){
    b$VE[i]<-NA
  })
}
c<-b
#to see all java methods
.jmethods(jobject)
.jcall(jobject ,"S",method="Extract","works great")


