# GildedRose-Refactoring-Java code by Giedrius Valancius

 Few comments regarding my code:

  - I think I have implemented all assignment's tasks, almost covered everything with tests.
    After starting the application some example items can be reached with these GET requests:
    (localhost:8080/items or localhost:8080/items{id} ).

  - Additionally it would be wise to implemented some Item/ItemEntity properties validation
    on saving them into the database (I skipped that part);

  - After refactoring GildedRose project I have moved Item class to the models folder and renamed GildedRose to
    GildedRoseUpdateService. That seemed more acceptable for the Spring Boot application.

    NOTE: GildedRoseUpdateService class now has two updateQuality methods:
    void updateQuality() is for the Items[] which have no id property and won't be saved into the database;
    List<ItemEntity> updateQuality(List<ItemEntity> items) is for the items which are passed with the method.
    The method returns items for the further work associated with the database.
    Ideally I would remake the original Item class and avoid this duplicate code but in the REFACTORING assignment
    it was said that the Item class cannot be changed so I created a new one, named it ItemEntity and used it
    for the additional tasks (2-3).
    All the update logic are in the CustomItem interface implementations so it can be easily maintainable from there.


 #The description of assignment

  1. Do GildedRose refactoring kata in Java (https://github.com/emilybache/GildedRose-Refactoring-Kata).
     Make code readable and maintainable.
  2. Upgrade item update algorithm to process items asynchronously.
  3. Implement GildedRose items rest service (only items list endpoint)
     in Spring Boot (https://projects.spring.io/spring-boot/) application using previously refactored code.
     Use Elasticsearch as database for Items (or MongoDB, or Redis). Items update must happen once a day.

  Non functional requirements
     - Source code should be provided in GIT repository (github.com, bitbucket.org, gitlab.com or other platform).
     - Do commit early and often.
     - Cover your solution with tests.
     - Use some ​Java 8​ features were it makes sense.
