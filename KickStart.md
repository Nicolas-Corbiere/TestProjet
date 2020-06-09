# KickStart for 'JustificationDiagram' project

First, [download the jar file](https://github.com/MireilleBF/JustificationDiagram/releases/tag/V1.2).

I advice you to create a repertory 'justification' were you will put your file '.jd' and a repertory 'output'.

For the example create 'basic.jd' in this repertory.

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

next, create a realization file in the repertory 'realization'


To create your first Diagramm, run this :

```
java -jar JDGenerator-jar-with-dependencies.jar $(cat varInput.txt)basic.jd -o $(cat varOutput.txt)basic -rea realization.txt -svg -svgR -td 

```

