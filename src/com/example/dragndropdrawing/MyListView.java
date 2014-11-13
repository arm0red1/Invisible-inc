package com.example.dragndropdrawing;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class MyListView extends ListView{
	
	private static final int DELETE_ID = Menu.FIRST;
	private int mNoteNumber = 1;
	
	private NotesDbAdapter mDbHelper;

	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	private void fillData() {
		Cursor notesCursor = mDbHelper.fetchAllNotes();
		//startManagingCursor(notesCursor); depreciated
		
		String[] from = new String[] {NotesDbAdapter.KEY_TITLE, NotesDbAdapter.KEY_DATE};
		int[] to = new int[] {R.id.notepadTextField};
		
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.id.notepadTextField, notesCursor, from, to);
		setListAdapter(notes);
		
		getLoaderManager().initLoader(0, null, (LoaderCallbacks<listOfNotePads>) this);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		super.onActivityResult(requestCode, resultCode, intent);
		
		fillData();
	}

}
