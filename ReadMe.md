# Context-Sensitive Delta Inference for Identifying Workload-Dependent Performance Bottlenecks

Software hang problems are one of the most common problems reported by users. Some software hang problems are constantly expensive, that is, these do not depend on the workload. On the other hand, some depend on workload which are called workload-dependent performance bottlenecks (WDPBs). The main goal of this project is to implement the mechanism to identify workload-dependent performance bottlenecks in a context sensitive way. The base paper is "Context-Sensitive Delta Inference for Identifying Workload-Dependent Performance Bottlenecks" by Xiao et al. [1](https://dl.acm.org/citation.cfm?id=2483784). The work is extended by considering multiple workload parameter rather than a single one.
## Methodology

In this approach, at first scenarios are chosen, for example, reading a file can be a scenario for performance analysis. Then, corresponding workload parameters are selected, for example, in the above case, number of lines can be a parameter. Then the representative value range (RVR) is chosen and workloads are generated by varying randomly from an initial point in the RVR. Then, the k-profile graph is buiilt. Regression is performed to predict execution count from workload. The complexity model where R-squared is more than a specific threshold is chosen. Then, model is validated generating workload from a validation range and comparing generated k-profile graph for this workload with the predicted count. If error is present, new workload is generated by taking average of the worst result workload and its close workload value from the validation range. This new workload is added to the workload for validation and this continues until error reaches a threshold or no more workload generation is possible. Then, WDPB candidates are identified by searching for complexity transitions where these complexities are calculated from regression prediction equations. The cost for these WDPB candidates are calculated.

The whole workflow is shown in the following figure.

![Alt text](https://github.com/IITDU-AMIT-MSSE1044/course-project-KKGanguly/blob/master/src/flow.jpg "Flow Diagram")

In the methodlogy, the training algorithm is single attribute. However, a multiparameter model is still necessary for more accurate prediction. This mutiparameter training model has been incorporated with the project. It has been seen that incorporating the multiparameter model results in better cost coverage.
### Prerequisites
* [srcML](http://www.srcml.org/) - Converts source code to XML and vice-versa
* [R](https://www.r-project.org/) - For Data Analysis (Training and Prediction)
* [Eclipse](https://www.eclipse.org/) - IDE for running Java Source Code
* [JDK8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) - Java Development Kit (Version 8 has been used)


### Executing the Code

* Make sure XML and R are added as environment variables
* Load the code in Eclipse and run Main.java 
* For running multiparameter mode input 1 and for running single parameter model input 2
* After first pass is complete go to the training folder and run the .bat file
* Now rerun the source code. For running multiparameter mode input 3 and for running single parameter model input 4
* The resuts including WDPB loop, cost coverage, total cost are generated into the corresponding results directory

## Authors

* **Kishan Kumar Ganguly** - *Initial work* - [KKGanguly](https://github.com/KKGanguly)



