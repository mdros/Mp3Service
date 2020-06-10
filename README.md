## Mp3Service - song web parser.

#### Description.

This simple program parses songs from a folder, puts them in a database and shows them on a local website.



#### Requirements.

For the program to work you will need PostgreSQL and Java.

#### 

#### Database setup.

[Download the latest version of PostgreSQL](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads) and install it. Do not change the port. Start PGAdmin 4. Here, create a database called **'songs'**. The username and password you entered in PGAdmin will be needed to connect to the database later.



#### Installation and usage.

1. Download the .jar and .yml files from the latest release. 

2. Put them **both** inside a folder from which you want to parse .mp3 files. Now you need to modify the config.yml. 

3. Edit the file using whatever text editor you want, and change values under **'DB_USER'** and **'DB_PASS'** to match your PGAdmin username and password. 

4. Now open Windows Powershell or Command Line and type in:

   **_java -jar mp3service.jar_**

   and press ENTER. You should see some output and a website will pop up with a list of your songs. If the website doesn't show up, check it manually under:
   **_http://localhost:8080/songs_**


#### Technologies used.

* Java, Servlets

* Gradle

* PostgreSQL
