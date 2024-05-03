# TapUniSelector
#### Author: Joe Pratt
This program calculates the amount of students who have passed the entrance examination of Tap University.

## Setup
### Input
This data must be supplied as 'stdin' in the following format:
```
N
t_1 e_1 m_1 s_1 j_1 g_1 
t_2 e_2 m_2 s_2 j_2 g_2
...
t_N e_N m_N s_N j_N g_N
```
It represents the subject scores and elected specialisations of each student.
- In the first line, an integer N representing the number of examinees is given.
- Of the following N lines, the i-th line (1 ≤ i ≤ N) contains..
  - t_i = character representing the examinee's specialisation. E.g.humanities, sciences.
  - e_i, m_i, s_i, j_i, g_i = integer representing scores. E.g English, mathematics, science, Japanese, geography, history.

### Courses Data File
This is a json file allowing courses, specialisations, and score thresholds to be configured.
- It will be generated in the same directory as the jar file on launch (if not already present).

Format:
```
{
  "subjects": [
    "english",
    "math",
    "science",
    "japanese",
    "geoHist"
  ],
  "globalMinScore": 350,
  "specialties": [
    {
      "key": "s",
      "name": "science",
      "minPass": 160,
      "subjects": [
        "math",
        "science"
      ]
    },
    {
      "key": "l",
      "name": "humanities",
      "minPass": 160,
      "subjects": [
        "japanese",
        "geoHist"
      ]
    }
  ]
}
```
- subjects[] is the list of subjects each student will be scored against
- globalMinScore is the minimum score the student must achieve overall
- specialties {}
  - `key` = character for the specialty 't' in the stdin input (explained above)
  - `minPass` = minimum score the student must achieve to pass the specialty
  - `subjects` = list of courses that comprise the specialty. Must be a subset of the subjects defined in root.

## How to build
***These instructions are for a bash shell***
```sh
# from project root
mvn clean install
```

## How to run
***These instructions are for a bash shell***
```sh
java -jar [path to jar file] < [path to .txt input file]
```
e.g.
```sh
# from project root
java -jar target/TapUniSelector-1.0-SNAPSHOT-jar-with-dependencies.jar < src/test/resources/ex2Input.txt
```

## How to run tests
***These instructions are for a bash shell***
```sh
# from project root
mvn test
```
