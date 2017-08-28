# Spark part 1

Working with file 000000

test program using virtualbox and aws

to run program:
``` sh
spark-submit --master local --deploy-mode client spark_p1-assembly-0.1.jar /tasks/task8/input/logs.txt /tasks/task8/output/total /tasks/task8/output/browsers
```
Where:
* ```/tasks/task8/input/logs.txt``` input file
* ```/tasks/task8/output/total``` first output directory
* ```/tasks/task8/output/browsers``` second output directory

Also you need to specify main class, when run application on aws using ```--class com.example.Main```
