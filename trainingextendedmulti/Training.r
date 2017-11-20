temp = list.files(pattern="*.csv")
trainingData<-""
for (i in 1:length(temp)) assign(temp[i], read.csv(temp[i]))
write("","complexity.txt")
for (i in 1:length(temp)){
  data<-read.csv(temp[i],header=TRUE)
  
  linearAvgRSq<-0
  linearModel<-lm(Count~workload1,data = data)
  sumLin<-summary(linearModel)
  linearModelRSq<-sumLin$r.squared
  linearAvgRSq<-linearAvgRSq+linearModelRSq
  linearModel<-lm(Count~workload2,data = data)
  sumLin<-summary(linearModel)
  linearModelRSq<-sumLin$r.squared
  linearAvgRSq<-linearAvgRSq+linearModelRSq
  data<-log10(data+1)
  powAvgRSq<-0
  avgComplexity<-0
  powerModel<-lm(Count~workload1,data = data)
  sumPow<-summary(powerModel)
  powModelRSq<-sumPow$r.squared
  powAvgRSq<-powAvgRSq+powModelRSq
  avgComplexity<-avgComplexity+max(as.numeric(unlist(powerModel$coefficients)))
  powerModel<-lm(Count~workload2,data = data)
  sumPow<-summary(powerModel)
  powModelRSq<-sumPow$r.squared
  powAvgRSq<-powAvgRSq+powModelRSq
  avgComplexity<-avgComplexity+max(as.numeric(unlist(powerModel$coefficients)))
  
  linearAvgRSq<-linearAvgRSq/(ncol(data)-1)
  powAvgRSq<-powAvgRSq/(ncol(data)-1)
  avgComplexity<-avgComplexity/(ncol(data)-1)
  selectedComplexity<-0
  if(linearAvgRSq>=powAvgRSq){
    selectedComplexity<-1
  }
  else{
    selectedComplexity<-avgComplexity
  }
  selectedComplexity<-round(selectedComplexity, digits = 0)
  dataToWrite<-paste(temp[i], selectedComplexity, sep = "\t")
  trainingData<-paste(trainingData, dataToWrite,"\n", sep = "")
  write(trainingData,"complexity.txt")
}

