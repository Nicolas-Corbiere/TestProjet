# KickStart for 'JustificationDiagram' project

First, [download the jar file](https://github.com/MireilleBF/JustificationDiagram/releases/tag/V1.2) in your own project.

I advice you to create a repertory 'justification' were you will put all your file '.jd' and a repertory 'output'.

For the example create this 'basic.jd' in this 'justification/'.

## basic.jd
```
@startuml

support JU = "JUnit test"
support BT = "Build Maven"
support JR = "Jacoco Report"
strategy EMP = "Evaluate Maven Project"
subconclusion M = "Maven ready"

support JA = "Jacoco Report Archivate"
strategy DA = "Data Archivate"
subconclusion A = "Archivees Data"

strategy V = "Valide Project"

conclusion C = "Software is ready for launch"

JU --> EMP
BT --> EMP
JR --> EMP
EMP --> M

M --> V

JA --> DA
DA --> A
A --> V

V --> C


@enduml
```

Then create a workflow file in the ".github\workflows" directory at the beginning of your project.
During CI, you will create an realization.txt file that contains all the labels of completed tasks that do not have children.
(this will be all the "support" for this example)
You will also create the diagrams and archive them in an artifact that you will find in your Action menu in github.

```
name: Java CI with Maven

on:
  push:
    branches: [ master ]
  pull_request:
    branches: [ master ]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 1.8
      uses: actions/setup-java@v1
      with:
        java-version: 1.8
        
    #I indicate that "JUnit test","Build Maven" and "Jacoco Report" is done 
    - name: Realization part1
      run: 
        echo -e "JUnit test" >> realization.txt;
        echo -e "Build Maven\nJacoco Report" >> realization.txt;
        
    #I indicate that "Jacoco Report Archivate" is done if 'justification/' and 'justification/outuput' exist
    - name: Realization part2
      run: echo -e "Jacoco Report Archivate!-!justification/outuput;justification!ref!jacoco" >> realization.txt
    
    #I generate 2 diagrams and a to-do list from "justification/basic.jd" to "justification/output/basic".
    #with the realization file 'realization.txt' which contains all the labels of the nodes done.
    - name: JD&TODO generation 
      run: java -jar JDGenerator-jar-with-dependencies.jar $(cat varInput.txt)basic.jd -o $(cat varOutput.txt)basic -rea realization.txt -svg -svgR -td 

```

You'll get this result :

### basic.svg 

![link to Google](https://github.com/Nicolas-Corbiere/TestProjet/blob/master/justification/output/basic.svg)


### basic.svg 

![link to Google](https://github.com/Nicolas-Corbiere/TestProjet/blob/master/justification/output/basic_REA.svg)


### basic.todo

```
Requirements list

[X]	JUnit test
[X]	Jacoco Report
[X]	Jacoco Report Archivate - references : jacoco
	[X] justification/
    [X] justification/output
[X]	Build Maven
[X]	Maven ready
[X]	Archivees Data
--------------------------------------------
[X]		Software is ready for launch
-----------------------------------------------

```

For more information, see this ![workflow](https://github.com/Nicolas-Corbiere/TestProjet/blob/master/.github/workflows/maven.yml)

