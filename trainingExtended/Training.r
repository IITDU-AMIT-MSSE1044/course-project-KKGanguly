temp = list.files(pattern="*.csv")
trainingData<-""
for (i in 1:length(temp)) assign(temp[i], read.csv(temp[i]))
write("","complexity.txt")
for (i in 1:length(temp)){
  data<-read.csv(temp[i],header=TRUE)
  data<-log10(data+1)
  print(data)
  powerModel<-lm(Cost~.,data = data);
  print(max(as.numeric(unlist(powerModel$coefficients))))
  sumPow<-summary(powerModel)
  powModelRSq<-sumPow$r.squared
  selectedComplexity<-0
  selectedComplexity<-max(as.numeric(unlist(powerModel$coefficients)))
  dataToWrite<-paste(temp[i], selectedComplexity, sep = "\t")
  trainingData<-paste(trainingData, dataToWrite,"\n", sep = "")
  write(trainingData,"complexity.txt")
}

