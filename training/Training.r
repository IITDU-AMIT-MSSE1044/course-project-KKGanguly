temp = list.files(pattern="*.csv")
trainingData<-""
data<-""
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
  content<-""
  if (is.na(linearModelRSq)||is.na(powModelRSq)) {
    selectedComplexity<-1
    content<-linearModel$fitted.values
  }
  else if(linearModelRSq>=powModelRSq){
    selectedComplexity<-1
    content<-linearModel$fitted.values
  }
  else{
    selectedComplexity<-max(as.numeric(unlist(powerModel$coefficients)))
    content<-powerModel$fitted.values
  }
  selectedComplexity<-round(selectedComplexity, digits = 0)
  dataToWrite<-paste(temp[i], selectedComplexity, sep = "\t")
  trainingData<-paste(trainingData, dataToWrite,"\n", sep = "")
  data<-paste(temp[i],data, content,"\n", sep = ",")
  write(trainingData,"complexity.txt")
  }


