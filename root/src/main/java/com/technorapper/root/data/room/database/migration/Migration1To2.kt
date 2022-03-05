package com.technorapper.root.data.room.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration1To2 : Migration(1,2) {
    override fun migrate(database: SupportSQLiteDatabase) {

        /*val TABLE_NAME_TEMP = "GameNew"
         // 1. Create new table
        database.execSQL("CREATE TABLE IF NOT EXISTS `$TABLE_NAME_TEMP` " +
                "(`game_name` TEXT NOT NULL, " +
                "PRIMARY KEY(`game_name`))")

        // 2. Copy the data
        database.execSQL("INSERT INTO $TABLE_NAME_TEMP (game_name) "
                + "SELECT game_name "
                + "FROM $TABLE_NAME")

        // 3. Remove the old table
        database.execSQL("DROP TABLE $TABLE_NAME")

        // 4. Change the table name to the correct one
        database.execSQL("ALTER TABLE $TABLE_NAME_TEMP RENAME TO $TABLE_NAME")*/

        database.execSQL("ALTER TABLE userMaster ADD COLUMN 'temp' TEXT")
    }
}