package com.example.dragndropdrawing;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ScratchMain extends Activity{
	
	private static final int DELETE_ID = Menu.FIRST;
	private int mNoteNumber = 1;
	
	private NotesDbAdapter mDbHelper;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.root_main);
		
		mDbHelper = new NotesDbAdapter(this);
		mDbHelper.open();
		fillData();
		registerForContextMenu(findViewById(R.id.listView1).getRootView());
		
		Button addNote = (Button)findViewById(R.id.addNotePadd);
		addNote.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ScratchMain.this, MainActivity.class);
				startActivity(i);
			}
		});
		
	}
		
	@SuppressWarnings({ "deprecation", "unchecked" })
	private void fillData() {
		Cursor notesCursor = mDbHelper.fetchAllNotes();
		//startManagingCursor(notesCursor); depreciated
		
		String[] from = new String[] {NotesDbAdapter.KEY_TITLE, NotesDbAdapter.KEY_DATE};
		int[] to = new int[] {R.id.notepadTextField};
		
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.id.notepadTextField, notesCursor, from, to);
		//((ListActivity) findViewById(R.id.listView1).getRootView()).setListAdapter(notes);
		
		getLoaderManager().initLoader(0, null, (LoaderCallbacks<listOfNotePads>) this);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		super.onActivityResult(requestCode, resultCode, intent);
		
		fillData();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		
		Intent i = new Intent(this, MainActivity.class);
		i.putExtra(NotesDbAdapter.KEY_ROWID, id);
		startActivity(i);
	}
}
