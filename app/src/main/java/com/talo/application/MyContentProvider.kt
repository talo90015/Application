package com.talo.application

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class MyContentProvider : ContentProvider() {

    private lateinit var dbrw : SQLiteDatabase

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
       val name = selection ?: return 0
        return dbrw.delete("isDatabase","book='${name}'", null)
    }

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val book = values ?: return null
        val rowId = dbrw.insert("isDatabase", null, book)
        return  Uri.parse("content://com.talo.Demo/$rowId")
    }

    override fun onCreate(): Boolean {
        val context = context?: return false
        dbrw = MyDBHelper(context).writableDatabase
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val queryString = if (selection == null) null else "book='${selection}'"
        return dbrw.query("isDatabase", null, queryString, null, null, null, null)
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val name = selection ?: return 0
        val price = values ?: return 0
        return  dbrw.update("isDatabase", price, "book='${name}'", null)
    }
}