This project helps us to replace both name and content of files and folders (only applied for text file, not for binary file). 
And do other stuffs which maybe only me and god know what it is :D

Import into IntelliJ by gradle (you don't need to install gradle on your machine):
"File" > "New" > "Project from Existing Sources" > "Gradle" > Choose option "Use gradle wrapper task configuration" > Next...

Start application by running the file `ReplacingApplication.java`

# Known bugs with replacement and cloning service.
The old name should not be exactly the same as the prefix of the new name.
For example:
- The old name:   `/somefoler/somechildfolder/someoldname`
- The new name 1: `/somefoler/somechildfolder/someoldname-01` <-- this will cause a bug
- The new name 2: `/somefoler/somechildfolder/somename-01` <-- this will be fine.