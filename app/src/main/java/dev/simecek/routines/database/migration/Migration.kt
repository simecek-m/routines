package dev.simecek.routines.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object: Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // create new table named User
        database.execSQL("CREATE TABLE IF NOT EXISTS User (name TEXT PRIMARY KEY NOT NULL, avatar TEXT NOT NULL)")

        // insert first default user into newly created User table
        database.execSQL("INSERT INTO User (name, avatar) VALUES ('Default', 'avatar_1')")

        // rename Routine table to Routine_original
        database.execSQL("ALTER TABLE Routine RENAME TO Routine_original")

        // create updated Routine table wih owner_name foreign key field
        database.execSQL("CREATE TABLE IF NOT EXISTS Routine (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title TEXT NOT NULL, icon INTEGER NOT NULL, reminder TEXT NOT NULL, last_finished TEXT, owner_name TEXT NOT NULL, FOREIGN KEY(owner_name) REFERENCES User(name) ON UPDATE NO ACTION ON DELETE CASCADE )" )

        // copy original routines to new table (set default owner as owner)
        database.execSQL("INSERT INTO Routine (id, title, icon, reminder, last_finished, owner_name) SELECT *, 'Default' FROM Routine_original")

        // delete the original Routine table
        database.execSQL("DROP TABLE Routine_original")
    }
}

val MIGRATION_2_3 = object: Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Routine ADD COLUMN soft_deleted INTEGER NOT NULL DEFAULT 0")
    }

}
