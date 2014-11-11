package com.example.dragndropdrawing;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static int numTitle = 1;
	public static String curDate = "";
	public static String curText = "";
	private EditText mTitleText;
	private EditText mStoreText;
	private EditText mBodyText;
	private TextView mDateText;
	private Long mRowId;
	
	private Cursor note;
	
	private NotesDbAdapter mDbHelper;
	
	public static class LineEditText extends EditText{
		
		public LineEditText(Context context, AttributeSet attrs){
			super(context, attrs);
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.mDbHelper = new NotesDbAdapter(this);
		this.mDbHelper.open();
		
		setContentView(R.layout.activity_main);
		
		this.mStoreText = (EditText) findViewById(R.id.cityStoreTextField);
		this.mTitleText = (EditText) findViewById(R.id.nTitle);
		this.mBodyText = (EditText) findViewById(R.id.notepadTextField);
		this.mDateText = (EditText) findViewById(R.id.ndate);
		
		long msTime = System.currentTimeMillis();
		Date curDateTime = new Date(msTime);
	
		SimpleDateFormat formatter = new SimpleDateFormat("d'/'M'/'y");
		this.curDate = formatter.format(curDateTime);
		
		this.mDateText.setText(""+this.curDate);
		
		this.mRowId = (savedInstanceState == null) ? null :
			((Long) savedInstanceState.getSerializable(NotesDbAdapter.KEY_ROWID)).longValue();
		if(this.mRowId == null){
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(NotesDbAdapter.KEY_ROWID) : null;
		}
		populateFields();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		
		saveState();
		outState.putSerializable(NotesDbAdapter.KEY_ROWID, mRowId);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		
		saveState();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		populateFields();
	}
	
	private void saveState(){
		String title = this.mStoreText.getText().toString();
		String body = this.mBodyText.getText().toString();
		
		if(mRowId == null){
			long id = mDbHelper.createNote(title, body, curDate);
			if(id > 0){ mRowId = id; }
		} else {
			mDbHelper.updateNote(mRowId, title, body, curDate);
		}
	}
	
	private void populateFields(){
		if(mRowId != null){
			note = mDbHelper.fetchNote(mRowId);
			//startManagingCursor(note); depreciated
			
			this.mTitleText.setText(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_TITLE)));
			this.mBodyText.setText(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY)));
			this.curText = note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY));
			
		}
	}
}
