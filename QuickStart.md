# Quick Start for 'JustificationDiagram' project

First, [download the jar file](https://github.com/MireilleBF/JustificationDiagram/releases/tag/V1.2) in your own project.

I advice you to create a repertory 'justification' were you will put all your file '.jd' and a repertory 'output'.

For the example, create this justification pattern "basic.jd" in the "justification/" directory.

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


support CRM = "Creation of the README"
support CRQ = "Creation of the quick start"
strategy PDOC = "Project documented"
subconclusion DOCR = "Documentation ready"

CRM --> PDOC 
CRQ --> PDOC 
PDOC --> DOCR 
DOCR --> V

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


## action.json

You will also need an action file which will contain all the additional information and information to be checked for each of your steps

For the example create this 'action.json' in 'justification/'.


```

[  
    { 
        "Node":{
	    "@comment": "The node labeled 'Jacoco Repor' has 'jacoco' for reference...",
            "Label":"Jacoco Report",
            "Reference":"jacoco",
	    "@comment2": "...the total coverage need to be superior to 10%... ",   
            "Actions": [
                "CheckCoverage target/site/jacoco/index.html >= 10", 
            ],
            "FilesNumber": [  
                {   
         	    "@comment3": "...need to check if of the repertory 'target/site/jacoco/' have 6 files" ,
                    "Path":"target/site/jacoco/",
                    "Number":"6"     
                },
            ]
        }
    },
    {
        "Node":{
	    "@comment": "The node labeled 'Documentation ready' is optional.",     
            "Label":"Documentation ready",
            "Optional":"true", 
        }
    },
    {
        "Node":{
	    "@comment": "The node labeled 'Creation of the README'...",     
            "Label":"Creation of the README",
            "@comment2": "...need to check the exitance of the file 'README.md'." ,
            "Files": [  
                "README.md"
            ] 
        }
    }
    
    
    
]

```


Then create a workflow file in the ".github/workflows/" directory at the beginning of your project.

During CI, you will create a realization.txt file that contains all the labels of completed tasks that do not have children
(this will be all the "support" for this example).

You will also create the diagrams and archive them in an artifact that you will find in your Action menu in github.

### .github/workflows/maven.yml

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
      run: echo -e "Jacoco Report Archivate" >> realization.txt
    
    #I generate 2 diagrams and a to-do list from "justification/basic.jd" to "justification/output/basic".
    #with the realization file 'realization.txt' which contains all the labels of the nodes done.
    - name: JD&TODO generation 
      run: java -jar JDGenerator-jar-with-dependencies.jar justification/basic.jd -o justification/output/basic -rea realization.txt -info justification/action.jd  -svg -svgR -td 
      
    #I archive my diagrams create during the CI in 'GeneratedJD' artifact
    - name: Archive JD&TODO
      uses: actions/upload-artifact@v2
      with: 
        name: GeneratedJD
        path: justification/output/
    #I archive my 'realization.txt' file create during the CI in 'GeneratedJD' artifact
    - name: Archive realization.txt
      uses: actions/upload-artifact@v2
      with: 
        name: GeneratedJD
        path: realization.txt

```

## Result 

You'll get this result in your artifact 'GeneratedJD' :

### realizeation.txt

```
JUnit test
Build Maven
Jacoco Report
Jacoco Report Archivate
```


### basic.svg 

![link to Google](https://github.com/Nicolas-Corbiere/TestProjet/blob/master/justification/output/basic.svg)


### basic.svg 

![link to Google](https://github.com/Nicolas-Corbiere/TestProjet/blob/master/justification/output/basic_REA.svg)


### basic.todo

```
Requirements list

[X]	Creation of the README
	[X] README.md
[X]	Jacoco Report Archivate
[X]	Build Maven
[X]	JUnit test
[X]	Jacoco Report - reference : jacoco
	[x] target/site/jacoco/ (6 Files found)
	[x] Current coverage is 18, it's >= 10
[X]	Maven ready
[ ]	Creation of the quick start
[ ]	Documentation ready (optional) 
[X]	Archivees Data
-----------------------------------------------
[X]		Software is ready for launch
-----------------------------------------------

```
## More information

For a more concrete example, see [TestProjet](https://github.com/Nicolas-Corbiere/TestProjet). Especially its [workflow](https://github.com/Nicolas-Corbiere/TestProjet/blob/master/.github/workflows/maven.yml) who used the same 'basic.jd'.


For more information, see the project [JustificationDiagram](https://github.com/MireilleBF/JustificationDiagram). 
