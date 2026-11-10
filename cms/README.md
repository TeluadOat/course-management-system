# Student Course Management System

A simple text-based Java program for recording courses for a semester.

## Features

- Add courses with a code, title, and positive credit-unit value.
- View all saved courses, search by course code, and calculate total units.
- Save and load courses in `data/courses.txt`.
- Reject blank fields, invalid unit values, and duplicate course codes.
- Report file errors without discarding the current course list.

The menu options are printed using a small recursive method.

## Run

From the project directory, compile and start the application:

```sh
javac -d bin src/com/studentmanagement/*.java
java -cp bin com.studentmanagement.Main
```

Choose **Save to File** to write the current list. Choose **Load from File** to
replace the current list with the saved list. The data file is created when
first saved.
