temp = list.files(pattern="*.csv")
trainingData<-""
for (i in 1:length(temp)) assign(temp[i], read.csv(temp[i]))
write("","complexity.txt")
for (i in 1:length(temp)){
  data<-read.csv(temp[i],header=TRUE)
  linearModel<-lm(Count~workload,data = data);
  powerModel<-lm(log10(Count+1)~log10(workload+1),data = data);
  print(linearModel)
  print(powerModel)
  sumLin<-summary(linearModel)
  sumPow<-summary(powerModel)
  linearModelRSq<-sumLin$r.squared
  powModelRSq<-sumPow$r.squared
  selectedComplexity<-0
  if(linearModelRSq>=powModelRSq){
    selectedComplexity<-max(as.numeric(unlist(linearModel$coefficients)))
  }
  else{
    selectedComplexity<-max(as.numeric(unlist(powerModel$coefficients)))
  }
  dataToWrite<-paste(temp[i], selectedComplexity, sep = "\t")
  trainingData<-paste(trainingData, dataToWrite,"\n", sep = "")
  write(trainingData,"complexity.txt")
}

